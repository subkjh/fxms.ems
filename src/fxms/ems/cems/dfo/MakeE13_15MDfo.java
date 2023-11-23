package fxms.ems.cems.dfo;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.EngMeasrAmtInloQid;
import subkjh.dao.QidDaoEx;

/**
 * 한전 전기요금 데이터를 CEMS 전기요금 테이블로 복사한다.
 * 
 * @author subkjh
 *
 */

public class MakeE13_15MDfo implements FxDfo<Long, Integer> {

	public static void main(String[] args) {

		MakeE13_15MDfo dfo = new MakeE13_15MDfo();
		try {
			System.out.println(dfo.make(20231122000000L));
			System.out.println(dfo.make(20231122001500L));
			System.out.println(dfo.make(20231122003000L));
			System.out.println(dfo.make(20231122004500L));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Long psDtm) throws Exception {
		return make(psDtm);
	}

	public int make(long psDtm) throws Exception {

		EngMeasrAmtInloQid QID = new EngMeasrAmtInloQid();
		
		QidDaoEx dao = QidDaoEx.open(EngMeasrAmtInloQid.QUERY_XML_FILE) //
				.execute(QID.make_E13_energy_amt_inlo, FxApi.makePara("engDtm", psDtm)) //
				.close();

		return dao.getProcessedCount();
	}

}
