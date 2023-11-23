package fxms.ems.cems.dfo;

import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.EpwrChargeQid;
import subkjh.bas.BasCfg;
import subkjh.dao.QidDaoEx;

/**
 * 월간 전기 요금
 * 
 * @author subkjh
 *
 */

public class MakeEpwrMonthlyChargeDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		MakeEpwrMonthlyChargeDfo dfo = new MakeEpwrMonthlyChargeDfo();
		try {
			System.out.println(dfo.makeEpwrCharge("202311"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, String yyyymm) throws Exception {
		return makeEpwrCharge(yyyymm);
	}

	public int makeEpwrCharge(String yyyymm) throws Exception {

		EpwrChargeQid QID = new EpwrChargeQid();
		Map<String, Object> para = FxApi.makePara("yyyymm", yyyymm);
		return QidDaoEx.open(BasCfg.getHome(EpwrChargeQid.QUERY_XML_FILE)) //
				.execute(QID.make_epwr_charge_monthly, para) //
				.close() //
				.getProcessedCount();

	}

}
