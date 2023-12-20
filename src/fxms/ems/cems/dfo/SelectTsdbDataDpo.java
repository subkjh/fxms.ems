package fxms.ems.cems.dfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.mo.MoApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDpo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.bas.mo.SensorMo;
import fxms.ems.cems.dto.PsRangeDto;
import fxms.ems.cems.dto.TsdbDto;
import subkjh.bas.co.utils.DateUtil;

/**
 * 
 * @author subkjh
 *
 */
public class SelectTsdbDataDpo implements FxDpo<PsRangeDto, List<PsVoRawList>> {

	public static void main(String[] args) {
		PsRangeDto dto = new PsRangeDto();
		long mstime = System.currentTimeMillis() - 600000L;
		dto.startDtm = FemsApi.kind1M.getHstimeStart(DateUtil.getDtm(mstime));
		dto.endDtm = FemsApi.kind1M.getHstimeNext(dto.startDtm, 5);

		SelectTsdbDataDpo dpo = new SelectTsdbDataDpo();
		try {
			System.out.println(dpo.run(null, dto));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PsVoRawList> run(FxFact fact, PsRangeDto range) throws Exception {

		List<TsdbDto> list = new ArrayList<>();
		TsdbDto data;

		data = new TsdbDto();
		data.energy = "epwr";
		data.psTable = "FX_V_EPWR";
		data.measurements = getMeasurements(data.energy);
		list.add(data);

		data = new TsdbDto();
		data.energy = "gas";
		data.psTable = "FX_V_GAS";
		data.measurements = getMeasurements(data.energy);
		list.add(data);

		data = new TsdbDto();
		data.energy = "heat";
		data.psTable = "FX_V_HEAT";
		data.measurements = getMeasurements(data.energy);

		list.add(data);

		Map<String, SensorMo> sensorMap = new HashMap<>();
		List<SensorMo> sensors = MoApi.getApi().getMos(FxApi.makePara("moClass", "SENSOR"), SensorMo.class);
		for (SensorMo sensor : sensors) {
			sensorMap.put(sensor.getMoTid(), sensor);
		}

		List<PsVoRawList> values = new ArrayList<>();
		SelectTsdbDataDfo dfo = new SelectTsdbDataDfo();
		for (TsdbDto o : list) {

			o.sensorMap = sensorMap;
			o.psRange = range;

			List<PsVoRawList> val = dfo.call(null, o);
			if (val != null) {
				values.addAll(val);
			}
		}

		return values;
	}

	private List<String> getMeasurements(String energy) throws Exception {

		List<String> ret = new ArrayList<>();
		String varName = "tsdb.measurements." + energy;

		String val = FxCfg.getCfg().getString(varName, null);
		if (val == null) {
			return new ArrayList<>();
		} else {
			String ss[] = val.split(",");
			for (String s : ss) {
				ret.add(s.trim());
			}
		}

		return ret;
	}

}
