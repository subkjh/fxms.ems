package fxms.ems.cems.dfo;

import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.EpwrChargeTimeQid;
import subkjh.bas.BasCfg;
import subkjh.dao.QidDaoEx;

/**
 * 1시간 단위 요금 계산
 * 
 * @author subkjh
 *
 */
public class MakeEpwr1HChargeDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		MakeEpwr1HChargeDfo dfo = new MakeEpwr1HChargeDfo();
		try {
			for (int yyyymmdd = 20231001; yyyymmdd <= 20231031; yyyymmdd++) {
				System.out.println(dfo.makeEpwrCharge(String.valueOf(yyyymmdd)));
			}
			for (int yyyymmdd = 20231101; yyyymmdd <= 20231127; yyyymmdd++) {
				System.out.println(dfo.makeEpwrCharge(String.valueOf(yyyymmdd)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, String date) throws Exception {
		return makeEpwrCharge(date);
	}

	public int makeEpwrCharge(String date) throws Exception {

		EpwrChargeTimeQid QID = new EpwrChargeTimeQid();

		Map<String, Object> para = FxApi.makePara("date", date);

		return QidDaoEx.open(BasCfg.getHome(EpwrChargeTimeQid.QUERY_XML_FILE))//
				.execute(QID.make_epwr_charge_time, para) //
				.execute(QID.make_epwr_charge_peak_time, para) //
				.close()//
				.getProcessedCount();

	}

}
