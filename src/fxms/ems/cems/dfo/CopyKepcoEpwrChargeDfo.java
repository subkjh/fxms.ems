package fxms.ems.cems.dfo;

import java.util.List;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.cems.dao.EpwrChargeQid;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDaoEx;

/**
 * 한전 전기요금 데이터를 CEMS 전기요금 테이블로 복사한다.
 * 
 * @author subkjh
 *
 */

public class CopyKepcoEpwrChargeDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		CopyKepcoEpwrChargeDfo dfo = new CopyKepcoEpwrChargeDfo();
		try {
			PsKind psKind = FemsApi.kindMonthly;
			long mstime = DateUtil.toMstime(20200101000000L);
			for (long ms = mstime; ms < System.currentTimeMillis(); ms = psKind.getMstimeNext(ms, 1)) {
				System.out.println(dfo.call(null, DateUtil.getYyyymm(ms, 0)));
			}

//			System.out.println(dfo.makeEpwrCharge("202308"));
//			System.out.println(dfo.makeEpwrCharge("202309"));
//			System.out.println(dfo.makeEpwrCharge("202310"));
//			System.out.println(dfo.makeEpwrCharge("202311"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, String yyyymm) throws Exception {

		EpwrChargeQid QID = new EpwrChargeQid();
		QidDaoEx dao = QidDaoEx.open(EpwrChargeQid.QUERY_XML_FILE) //
				.execute(QID.update_KEPCO_CHARGE, FxApi.makePara("yyyymm", yyyymm)) //
				.execute(QID.update_BEST_INFO, FxApi.makePara("yyyymm", yyyymm)) //
				.close();

		List<Integer> ret = dao.getProcessedCountList();

		return ret.get(1);
	}

}
