package fxms.ems.flare.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.MoApi;
import fxms.ems.bas.mo.SensorMo;
import subkjh.bas.co.log.LOG_LEVEL;

public class FlareApi extends FxApi {

	/** use DBM */
	public static FlareApi api;

	/**
	 * 사용할 DBM를 제공합니다.
	 *
	 * @return DBM
	 */
	public synchronized static FlareApi getApi() {
		if (api != null)
			return api;

		api = new FlareApi();
		try {
			api.onCreated();
		} catch (Exception e) {

		}

		return api;
	}

	private final Map<String, SensorMo> sensorNameMap;

	public FlareApi() {
		this.sensorNameMap = new HashMap<String, SensorMo>();
	}

	@Override
	public String getState(LOG_LEVEL level) {
		return null;
	}

	@Override
	public void onCreated() throws Exception {

		List<SensorMo> list = MoApi.getApi().getMoList(null, SensorMo.class);
		for (SensorMo mo : list) {
			sensorNameMap.put(mo.getSensrName(), mo);
		}
	}

	public void putSensor(SensorMo mo) {
		if (mo != null) {
			sensorNameMap.put(mo.getSensrName(), mo);
		}
	}

	public SensorMo getSensor(String name) {
		return this.sensorNameMap.get(name);
	}

	@Override
	public void reload(Enum<?> type) throws Exception {
		// TODO Auto-generated method stub

	}

}
