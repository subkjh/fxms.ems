package fxms.ems.cems.dfo;

import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.cems.dao.EngMeasrAmtInloQid;
import subkjh.bas.BasCfg;
import subkjh.dao.QidDaoEx;

/**
 * 설비합산전력량(E14)을 생성한다.
 * 
 * @author subkjh
 *
 */
public class MakeE14_15MDfo implements FxDfo<Long, Integer> {

	public static void main(String[] args) {

		MakeE14_15MDfo dfo = new MakeE14_15MDfo();
		try {
			System.out.println(dfo.make(20231122000000L));
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

		PsKind psKind = FemsApi.kind15M;

		long measrDtmStart = psKind.getHstimeStart(psDtm);
		long measrDtmEnd = psKind.getHstimeEnd(measrDtmStart);
		Map<String, Object> para = FxApi.makePara("measrDtmStart", measrDtmStart, "measrDtmEnd", measrDtmEnd, "dtmType",
				psKind.getPsKindName());

		return QidDaoEx.open(BasCfg.getHome(EngMeasrAmtInloQid.QUERY_XML_FILE))//
				.execute(QID.make_E14_energy_amt_inlo, para) //
				.close()//
				.getProcessedCount();

	}

}
