package fxms.ems.cems.dpo;

import java.util.List;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.EpwrChargeQid;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDaoEx;

/**
 * 한전 전기요금 데이터를 CEMS 전기요금 테이블로 복사한다.
 * 
 * @author subkjh
 *
 */

public class CopyKepco2EpwrChargeDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		CopyKepco2EpwrChargeDfo dfo = new CopyKepco2EpwrChargeDfo();
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

		String lastMonth = DateUtil.getYyyymm(System.currentTimeMillis(), -1);
		String thisMonth = DateUtil.getYyyymm(System.currentTimeMillis(), 0);

		EpwrChargeQid QID = new EpwrChargeQid();
		QidDaoEx dao = QidDaoEx.open(EpwrChargeQid.QUERY_XML_FILE) //
				.execute(QID.update_KEPCO_CHARGE, FxApi.makePara("yyyymm", lastMonth)) //
				.execute(QID.update_KEPCO_CHARGE, FxApi.makePara("yyyymm", thisMonth)) //
				.close();

		List<Integer> ret = dao.getProcessedCountList();

		return ret.get(1);
	}

}
