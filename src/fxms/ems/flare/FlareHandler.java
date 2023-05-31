package fxms.ems.flare;

import java.util.Map;

import fxms.bas.api.MoApi;
import fxms.bas.api.ValueApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.handler.BaseHandler;
import fxms.bas.handler.vo.SessionVo;
import fxms.bas.mo.Mo;
import fxms.bas.vo.PsVoRaw;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.bas.mo.SensorMo;
import fxms.ems.flare.api.FlareApi;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

public class FlareHandler extends BaseHandler {

	public Object addValues(SessionVo session, FlarePsValuePara obj) throws Exception {

		Mo mo = FlareApi.getApi().getSensor(obj.getSensorName());
		if (mo == null) {
			Map<String, Object> datas = ObjectUtil.toMap(obj);
			datas.put("moClass", "SENSOR");
			datas.put("moType", obj.getSensorType());
			mo = MoApi.getApi().addMo(0, "SENSOR", datas, "flare", false);
			if (mo != null) {
				FlareApi.getApi().putSensor((SensorMo) mo);
			}
		}

		PsVoRaw vo = new PsVoRaw(mo.getMoNo(), obj.getPsId(), obj.getValue());
		long pollMsdate = DateUtil.toMstime(obj.getDtm());
		PsVoRawList valueList = new PsVoRawList("flare", pollMsdate);
		valueList.add(vo);

		ValueApi.getApi().addValue(valueList, true);

		return obj;

	}
	
	@Override
	protected QidDao getQidDao() throws Exception {
		return DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createQidDao();
	}

}
