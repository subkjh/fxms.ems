package fxms.ems.cems.dpo;

import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.EpwrChargeLoadQid;
import subkjh.bas.BasCfg;
import subkjh.dao.QidDaoEx;

/**
 * 월간 부하별 전기 요금
 * 
 * @author subkjh
 *
 */
public class MakeEpwrMonthlyChargeLoadDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		MakeEpwrMonthlyChargeLoadDfo dfo = new MakeEpwrMonthlyChargeLoadDfo();
		try {
			System.out.println(dfo.makeEpwrCharge("202308"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, String yyyymm) throws Exception {
		return makeEpwrCharge(yyyymm);
	}

	public int makeEpwrCharge(String yyyymm) throws Exception {

		EpwrChargeLoadQid QID = new EpwrChargeLoadQid();
		Map<String, Object> para = FxApi.makePara("yyyymm", yyyymm);
		return QidDaoEx.open(BasCfg.getHome(EpwrChargeLoadQid.QUERY_XML_FILE))//
				.execute(QID.make_epwr_charge_load, para) //
				.close()//
				.getProcessedCount();

	}

}
