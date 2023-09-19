package fxms.ems.vup.enitt;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.ValueApi;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.co.ToCheckDfo;
import fxms.bas.vo.PsVoRaw;
import fxms.bas.vo.PsVoRawList;
import fxms.bas.vo.mapp.MappMoPs;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dto.Value01Dto;
import fxms.ems.vup.dto.Value01Sub1Dto;
import fxms.ems.vup.dto.Value01Sub2Dto;
import fxms.ems.vup.dto.Value01Sub3Dto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

/**
 * 애니트에서 1분 간격으로 보내는 값을 기록한다.
 * 
 * @author subkjh
 *
 */
public class ValueParser {

	public Map<String, Object> parse(Value01Dto dto) throws Exception {

		long mstime;
		Map<Long, PsVoRawList> valMap = new HashMap<Long, PsVoRawList>();
		Map<String, MappMoPs> map = VupApi.getApi().getMappPsAll();
		PsVoRawList valList;
		int notFoundPointTag = 0;

		for (Value01Sub1Dto sub1 : dto.getFacilities()) {

			for (Value01Sub2Dto dev : sub1.getDevices()) {

				mstime = DateUtil.toMstime(dev.collected_dt);

				valList = valMap.get(mstime);
				if (valList == null) {
					valList = new PsVoRawList(getClass().getSimpleName(), mstime);
					valMap.put(mstime, valList);
				}

				for (Value01Sub3Dto point : dev.getData()) {

					try {
						MappMoPs ps = map.get(point.point_pid);
						
						if (ps == null) {
							try {
								new ToCheckDfo().toCheck("관제점", point.point_pid,
										sub1.factory_pid + "/" + dev.device_pid + " 관제점 등록 안됨");
							} catch (Exception e) {
								Logger.logger.error(e);
							}
						}
						if (ps != null && ps.getMoNo() > 0 && point.value != null) {
							valList.add(new PsVoRaw(ps.getMoNo(), ps.getPsId(), (Number) point.value));
						} else {
							notFoundPointTag++;

						}
					} catch (Exception e) {
						Logger.logger.error(e);
					}
				}

			}
		}

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("unregistered_point_pid", notFoundPointTag);

		// 시간기준으로 등록 처리
		for (Long key : valMap.keySet()) {
			valList = valMap.get(key);
			ret.put("date_" + DateUtil.toHstime(key) + "_count", valList.size());
			
			
			System.out.println(FxmsUtil.toJson(valList));
			
			try {
				ValueApi.getApi().addValue(valList, false);
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}

		return ret;
	}

}
