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
			System.out.println(dfo.updatePriceInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Void data) throws Exception {
		return updatePriceInfo();
	}

	public int updatePriceInfo() throws Exception {

		EpwrInloPriceQid QID = new EpwrInloPriceQid();

		QidDaoEx dao = QidDaoEx.open(EpwrInloPriceQid.QUERY_XML_FILE) //
				.execute(QID.update_EPWR_INLO_PRICE, null) //
				.close();

		return dao.getProcessedCount();
	}

}
