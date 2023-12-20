package fxms.ems.cems.dfo;

import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.EpwrInloPriceQid;
import subkjh.dao.QidDaoEx;

/**
 * 한전에서 가져온 요금정보를 CEMS 테이블에 기록한다.
 * 
 * @author subkjh
 *
 */

public class CopyPriceInfoDfo implements FxDfo<Void, Integer> {

	public static void main(String[] args) {

		CopyPriceInfoDfo dfo = new CopyPriceInfoDfo();
		try {
			System.out.println(dfo.call(null, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Void data) throws Exception {

		EpwrInloPriceQid QID = new EpwrInloPriceQid();
		return QidDaoEx.ExecuteQid(EpwrInloPriceQid.QUERY_XML_FILE, QID.update_EPWR_INLO_PRICE, null);

	}

}
