package fxms.ems.cems.dfo;

import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dao.Raw2FacQid;
import subkjh.bas.BasCfg;
import subkjh.dao.QidDaoEx;

/**
 * CEMS_FAC_RAW 내용을 이용하여 설비(FE_FAC_BAS)와 Gateway(FX_MO, FX_MO_NODE), 센서(FX_MO,
 * FE_MO_SENSOR)을 등록한다.
 * 
 * @author subkjh
 *
 */
public class FacMakeFromRawDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		FacMakeFromRawDfo dfo = new FacMakeFromRawDfo();
		try {
			System.out.println(dfo.makeFacilities());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, String date) throws Exception {
		return makeFacilities();
	}

	public int makeFacilities() throws Exception {

		Raw2FacQid QID = new Raw2FacQid();
		return QidDaoEx.open(BasCfg.getHome(Raw2FacQid.QUERY_XML_FILE)) //
				.execute(QID.update_complex_inlo_no__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_company_inlo_no__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_plant_inlo_no__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_fac_cl_cd__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_proc_type_cd_1__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_proc_type_cd_2__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_proc_type_cd_3__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_mo_type_1__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_mo_type_2__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_mo_type_3__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_mo_tid__on_CEMS_FAC_RAW, null) //
				.execute(QID.update_fac_tid__on_CEMS_FAC_RAW, null) //
				.execute(QID.insert_FE_FAC_BAS, null) //
				.execute(QID.update_fac_no__on_CEMS_FAC_RAW, null) //
				.execute(QID.insert_gateway__into_FX_MO, null) //
				.execute(QID.insert_FX_MO_NODE, null) //
				.execute(QID.update_gw_mo_no__on_CEMS_FAC_RAW, null) //
				.execute(QID.insert_sensor__into_FX_MO, null) //
				.execute(QID.insert_FE_MO_SENSOR, null) //
				.execute(QID.update_sensor_mo_no__on_CEMS_FAC_RAW, null) //
				.close().getProcessedCount();

	}

}
