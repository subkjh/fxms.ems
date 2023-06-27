package fxms.ems.bas.dpo;

import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.PsApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.dao.EmsQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.dao.QidDaoEx;

/**
 * 에너지 원천 데이터를 이용하여 소비, 생산량을 계산한다.
 * 
 * @author subkjh
 *
 */
public class MakeEnergyAmtDfo implements FxDfo<Long, Void> {

	public static void main(String[] args) throws Exception {

		MakeEnergyAmtDfo dfo = new MakeEnergyAmtDfo();
		try {
			dfo.makeDatas(20230616114500L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final EmsQid QID = new EmsQid();

	@Override
	public Void call(FxFact fact, Long psDtm) throws Exception {
		makeDatas(psDtm);
		return null;
	}

	public void makeDatas(long measrDtm) throws Exception {
		
		List<Integer> cntRaw = this.makeRaw(measrDtm); // 수집데이터로 원천 데이터
		List<Integer> cntM15 = this.makeM15(measrDtm); // 원천으로 생산/소비량 15분
		List<Integer> cntH1 = this.makeH1(measrDtm); // 15분으로 1시간 데이터
		List<Integer> cntD1 = this.makeD1(measrDtm); // 1시간 데이터로 1일 데이터

		Logger.logger.info("{} : Raw={}/{}, M15={}/{}, H1={}/{}, D1={}/{}", measrDtm, cntRaw.get(0), cntRaw.get(1), cntM15.get(0),
				cntM15.get(1), cntH1.get(0), cntH1.get(1), cntD1.get(0), cntD1.get(1));
	}

	/**
	 * 1시간 데이터를 이용하여 일 데이터 생성
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	private List<Integer> makeD1(long measrDtm) throws Exception {

		Map<String, Object> para = FxApi.makePara("date", String.valueOf(measrDtm).substring(0, 8));

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.make_cons_stat_from_h1, para) //
				.execute(QID.make_prod_stat_from_h1, para)//
				.close() //
				.getProcessedCountList();

	}

	/**
	 * 15분 데이터를 이용하여 1시간 데이터 생성
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	private List<Integer> makeH1(long measrDtm) throws Exception {

		Map<String, Object> para = FxApi.makePara("dateHh", String.valueOf(measrDtm).substring(0, 10));

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.make_cons_h1_from_m15, para) //
				.commit() //
				.execute(QID.make_prod_h1_from_m15, para) //
				.close() //
				.getProcessedCountList();
	}

	/**
	 * 원천 데이터를 이용하여 생산, 소비 15분 데이터 채우기
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	private List<Integer> makeM15(long measrDtm) throws Exception {

		PsKind kind = PsApi.getApi().getPsKind(PsKind.PSKIND_15M);

		Map<String, Object> para = FxApi.makePara("psDate", kind.getHstimeStart(measrDtm));

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.insert_cons_amt_m15_from_raw, para) //
				.commit() //
				.execute(QID.insert_prod_amt_m15_from_raw, para) //
				.close() //
				.getProcessedCountList();

	}

	/**
	 * 
	 * @param measrDtm
	 * @throws Exception
	 */
	private List<Integer> makeRaw(long measrDtm) throws Exception {

		Map<String, Object> consPara = FxApi.makePara("engRawTable", "FE_ENG_CONS_RAW", "measrDtm", measrDtm,
				"measrDtmName", "CONS_DTM", "moUsageClCd", "IN");

		Map<String, Object> prodPara = FxApi.makePara("engRawTable", "FE_ENG_PROD_RAW", "measrDtm", measrDtm,
				"measrDtmName", "PROD_DTM", "moUsageClCd", "OUT");

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.make_cons_prod_raw_from_energy_raw, consPara) // sink 계측 내용
				.commit() //
				.execute(QID.make_cons_prod_raw_from_energy_raw, prodPara) // source 계측 내용
				.close() //
				.getProcessedCountList();

	}

}
