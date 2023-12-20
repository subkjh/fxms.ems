package fxms.ems.cems.dfo;

import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.cems.dao.EngMeasrAmtFacQid;
import fxms.ems.cems.dao.EngMeasrAmtInloQid;
import subkjh.bas.BasCfg;
import subkjh.dao.QidDaoEx;

/**
 * TOE(E41) 데이터 생성
 * 
 * @author subkjh
 *
 */
public class MakeE41_15MDfo implements FxDfo<Long, Integer> {

	public static void main(String[] args) {

		MakeE41_15MDfo dfo = new MakeE41_15MDfo();
		try {
			System.out.println(dfo.make(20231124000000L));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Long psDtm) throws Exception {
		return make(psDtm);
	}

	public int make(Long psDtm) throws Exception {

		EngMeasrAmtInloQid QID = new EngMeasrAmtInloQid();
		EngMeasrAmtFacQid QID2 = new EngMeasrAmtFacQid();

		PsKind psKind = FemsApi.kind15M;

		long measrDtmStart = psKind.getHstimeStart(psDtm);
		long measrDtmEnd = psKind.getHstimeEnd(measrDtmStart);
		Map<String, Object> para = FxApi.makePara("measrDtmStart", measrDtmStart, "measrDtmEnd", measrDtmEnd, "dtmType",
				psKind.getPsKindName());

		return QidDaoEx
				.open(BasCfg.getHome(EngMeasrAmtInloQid.QUERY_XML_FILE),
						BasCfg.getHome(EngMeasrAmtFacQid.QUERY_XML_FILE))//
				.execute(QID.make_E41_energy_amt_inlo, para) //
				.execute(QID.make_E43_energy_amt_inlo, para) //
				.execute(QID2.make_E41_energy_amt_fac, para) //
				.close()//
				.getProcessedCount();
	}

}
