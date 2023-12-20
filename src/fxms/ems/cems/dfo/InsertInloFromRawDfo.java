package fxms.ems.cems.dfo;

import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.Raw2FacQid;
import fxms.ems.cems.dao.Raw2InloQid;
import subkjh.bas.BasCfg;
import subkjh.dao.QidDaoEx;

/**
 * CEMS_FAC_RAW 내용을 이용하여 설비(FE_FAC_BAS)와 Gateway(FX_MO, FX_MO_NODE), 센서(FX_MO,
 * FE_MO_SENSOR)을 등록한다.
 * 
 * @author subkjh
 *
 */
public class InsertInloFromRawDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		InsertInloFromRawDfo dfo = new InsertInloFromRawDfo();
		try {
			System.out.println(dfo.call(null, "TBD"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, String complexName) throws Exception {

		Map<String, Object> para = FxApi.makePara("complexName", complexName);

		// TODO
		// UPPER_MO_NO에 MO_NO + 1 들어가지 않게 확인해야함.

		Raw2InloQid QID = new Raw2InloQid();
		return QidDaoEx.open(BasCfg.getHome(Raw2InloQid.QUERY_XML_FILE)) //
				.execute(QID.insert_complex__on_CEMS_COMPANY_RAW, para) //
				.execute(QID.update_complex_inlo_no_on_CEMS_COMPANY_RAW, para) //
				.execute(QID.insert_company__on_CEMS_COMPANY_RAW, para) //
				.execute(QID.update_company_inlo_no_on_CEMS_COMPANY_RAW, para) //
				.execute(QID.insert_plant__on_CEMS_COMPANY_RAW, para) //
				.execute(QID.update_plant_inlo_no_on_CEMS_COMPANY_RAW, para) //
				.close().getProcessedCount();

	}

}
