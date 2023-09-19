package fxms.ems.bas.dpo;

import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.bas.dao.EmsQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
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
			dfo.makeDatas(DateUtil.getDtm(System.currentTimeMillis() - 7200000L));
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
		List<Integer> cntM15 = this.make15M(measrDtm); // 원천으로 생산/소비량 15분
		List<Integer> cntH1 = this.make1H(measrDtm); // 15분으로 1시간 데이터
		List<Integer> cntD1 = this.make1D(measrDtm); // 1시간 데이터로 1일 데이터

		Logger.logger.info("{} cons/prod : Raw={}/{}, 15M={}/{}, 1H={}/{}, 1D={}/{}", measrDtm, cntRaw.get(0),
				cntRaw.get(1), cntM15.get(0), cntM15.get(1), cntH1.get(0), cntH1.get(1), cntD1.get(0), cntD1.get(1));

	}

	/**
	 * FX_ENG_AMT_xxx 기준으로 새롭게 추가된 테이블에 데이터를 기록한다.
	 * 
	 * @param measrDtm
	 * @throws Exception
	 */
	public void makeNewDatas(long measrDtm) throws Exception {

		int cnt15M = this.makeRaw2InloFac(measrDtm, FemsApi.kind15M); // 측정데이터를 15분 데이터로
		int cnt1H = this.makeRaw2InloFac(measrDtm, FemsApi.kind1H); // 측정데이터를 1시간 데이터로
		int cnt1D = this.makeRaw2InloFacStat(measrDtm, FemsApi.kind1D); // 측정데이터를 1일 데이터로

		Logger.logger.info("{} : 15M={}, 1H={}, 1D={}", measrDtm, cnt15M, cnt1H, cnt1D);
	}

	/**
	 * 원천 데이터를 이용하여 생산, 소비 15분 데이터 채우기
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	protected List<Integer> make15M(long measrDtm) throws Exception {

		PsKind kind = FemsApi.kind15M;

		Map<String, Object> para = FxApi.makePara("psDate", kind.getHstimeStart(measrDtm));

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.insert_cons_amt_m15_from_raw, para) //
				.commit() //
				.execute(QID.insert_prod_amt_m15_from_raw, para) //
				.close() //
				.getProcessedCountList();

	}

	/**
	 * 1시간 데이터를 이용하여 일 데이터 생성
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	private List<Integer> make1D(long measrDtm) throws Exception {

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
	private List<Integer> make1H(long measrDtm) throws Exception {

		Map<String, Object> para = FxApi.makePara("dateHh", String.valueOf(measrDtm).substring(0, 10));

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.make_cons_h1_from_m15, para) //
				.commit() //
				.execute(QID.make_prod_h1_from_m15, para) //
				.close() //
				.getProcessedCountList();
	}

	/**
	 * 
	 * @param measrDtm
	 * @throws Exception
	 */
	private List<Integer> makeRaw(long measrDtm) throws Exception {

		PsKind kind = FemsApi.kind15M;

		long measrDtmStart = kind.getHstimeStart(measrDtm);
		long measrDtmEnd = kind.getHstimeEnd(measrDtm);

		Map<String, Object> para = FxApi.makePara("measrDtm", measrDtmStart, "measrDtmStart", measrDtmStart,
				"measrDtmEnd", measrDtmEnd);

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.make_cons_raw_from_energy_raw, para) // sink 계측 내용
				.commit() //
				.execute(QID.make_prod_raw_from_energy_raw, para) // source 계측 내용
				.close() //
				.getProcessedCountList();

	}

	/**
	 * 측정 데이터를 기준으로 분, 시간 단위 통계를 생성한다.
	 * 
	 * @param measrDtm
	 * @param psKind
	 * @return
	 * @throws Exception
	 */
	private int makeRaw2InloFac(long measrDtm, PsKind psKind) throws Exception {

		long measrDtmStart = psKind.getHstimeStart(measrDtm);
		long measrDtmEnd = psKind.getHstimeEnd(measrDtmStart);

		Map<String, Object> consPara = FxApi.makePara("measrDtmStart", measrDtmStart, "measrDtmEnd", measrDtmEnd,
				"dtmType", psKind.getPsKindName());

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.make_energy_raw_to_inlo, consPara) // 설치위치
				.execute(QID.make_energy_raw_to_fac, consPara) // 설비
				.close() //
				.getProcessedCount();

	}

	/**
	 * 측정 데이터를 기준으로 일단위 통계를 생성한다.
	 * 
	 * @param measrDtm
	 * @param psKind
	 * @return
	 * @throws Exception
	 */
	private int makeRaw2InloFacStat(long measrDtm, PsKind psKind) throws Exception {

		long measrDtmStart = psKind.getHstimeStart(measrDtm);
		long measrDtmEnd = psKind.getHstimeEnd(measrDtmStart);
		String engDate = String.valueOf(measrDtmStart).substring(0, 8);

		Map<String, Object> consPara = FxApi.makePara("measrDtmStart", measrDtmStart, "measrDtmEnd", measrDtmEnd,
				"engDate", engDate);

		return QidDaoEx.open(BasCfg.getHome(EmsQid.QUERY_XML_FILE))//
				.execute(QID.make_energy_raw_to_inlo_stat, consPara) // 설치위치
				.execute(QID.make_energy_raw_to_fac_stat, consPara) // 설비
				.close() //
				.getProcessedCount();

	}

}
