package fxms.ems.vup.dpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.MoApi;
import fxms.bas.api.ModelApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.MoModel;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;

/**
 * 애니트의 관제점 데이터를 확인한다.
 * 
 * @author subkjh
 *
 */
public class EnittSetMoDfo implements FxDfo<VUP_COMM_ENITT_POINT, Map<String, Object>> {

	public EnittSetMoDfo() {
	}

	@Override
	public Map<String, Object> call(FxFact fact, VUP_COMM_ENITT_POINT data) throws Exception {
		return make(data, null);
	}

	public boolean setMo(List<VUP_COMM_ENITT_POINT> points) throws Exception {

		Map<String, MoModel> modelMap = ModelApi.toNameMap(ModelApi.getApi().getModels(null));

//		Map<String, Mo> moMap = VupApi.getApi().getMoTidMap();

		Map<String, Object> moData;

		for (VUP_COMM_ENITT_POINT point : points) {

			moData = make(point, modelMap.get(point.getMoType()));

			if (point.getMoNo() == null) {
				MoApi.getApi().addMo(0, VupApi.SENSOR_MO_CLASS, moData, "ENITT", false);
			} else {
				MoApi.getApi().updateMo(0, point.getMoNo(), moData, false);
			}
		}

		return false;
	}

	public Map<String, Object> make(VUP_COMM_ENITT_POINT data, MoModel model) throws Exception {

		Map<String, Object> map = new HashMap<>();

		if (data.getMoNo() != null) {

			map.put("moNo", data.getMoNo());

		} else {

			map.put("moDispName", data.getDeviceNm());
			map.put("moClass", VupApi.SENSOR_MO_CLASS);
			map.put("sensrDesc", data.getDeviceNm());

		}

		map.put("sensrName", data.getDeviceNm());
		map.put("moTid", data.getDevicePid());
		map.put("moName", data.getDeviceNm());
		map.put("moType", data.getMoType());
		map.put("inloNo", data.getPlantInloNo());

		if (model == null)
			model = ModelApi.getApi().getModel(data.getMoType(), "VUP");

		if (model != null) {
			map.put("modelNo", model.getModelNo());
		}
		return map;
	}
}
