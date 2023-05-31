package fxms.ems.vup.cron.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dao.EnittLocQid;
import fxms.ems.vup.dto.Conf01Dto;
import fxms.ems.vup.dto.Conf01Sub1Dto;
import fxms.ems.vup.dto.Conf01Sub2Dto;
import fxms.ems.vup.dto.Conf01Sub3Dto;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * 
 * 애니트에서 들어오는 설비, 관제를 TISP 맞게 처리하는 아텁터<br>
 * 1. CONTAINER 정보를 이용하여 설치위치를 등록, 수정한다.<br>
 * 2. 센서를 위한 모델 정보를 등록, 수정한다.<br>
 * 3. 설비를 등록, 수정한다.<br>
 * 4. 센서를 관리하는 PLC 등록, 수정한다.<br>
 * 5. 센서를 등록, 수정한다.<br>
 * 6. 관제점에 대한 데이터를 등록, 수정한다.<br>
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(service = "VupService", descr = "애니트에서 개발한 LOC에 직접 접속해서 데이터를 가져오는 아답터")
public class EnittLocConfCron extends Crontab {

	public static void main(String[] args) {
//		 Logger.logger.setLevel(LOG_LEVEL.info);
		EnittLocConfCron cron = new EnittLocConfCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "0 3 * * *")
	private String schedule;

	/**
	 * LOC에서 보낸 데이터, 지금은 DB에서 직접 읽는다.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getMap() throws Exception {
		QidDao tran = DBManager.getMgr().getDataBase("ENITTDB")
				.createQidDao(BasCfg.getHome(EnittLocQid.QUERY_XML_FILE));

		EnittLocQid qid = new EnittLocQid();

		try {
			tran.start();
			List<Map<String, Object>> mapList = tran.selectQid2Res(qid.select_conf_list, null);
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
			Map<String, Object> newMap;
			for (Map<String, Object> map : mapList) {
				newMap = new HashMap<String, Object>();
				for (String key : map.keySet()) {
					newMap.put(key.toLowerCase(), map.get(key));
				}
				retList.add(newMap);
			}

			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("datas", retList);
			return ret;
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	@Override
	public String getName() {
		return "애니트설비동기화";
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start() throws Exception {

		// LOC가 RestAPI를 이용하여 PUT한 내용으로 간주한다.
		Map<String, Object> map = getMap();
		Object value = map.get("datas");

		if (value instanceof List) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) value;
			Conf01Dto dto = convert(list);

			Map<String, Object> ret = new VupApi().checkConf(dto);
			Logger.logger.info("{}", ret);
		}

	}

	private Conf01Dto convert(List<Map<String, Object>> list) {
		Conf01Dto dto = new Conf01Dto();
		Map<String, Conf01Sub1Dto> facMap = new HashMap<String, Conf01Sub1Dto>();
		Map<String, Conf01Sub2Dto> devMap = new HashMap<String, Conf01Sub2Dto>();
		Map<String, Conf01Sub3Dto> pointMap = new HashMap<String, Conf01Sub3Dto>();
		Conf01Sub1Dto fac;
		Conf01Sub2Dto dev;
		Conf01Sub3Dto point;

		// 공장
		for (Map<String, Object> map : list) {
			map.put("factory_pid", map.get("container_id"));
			map.put("factory_nm", map.get("container_nm"));
			map.put("factory_desc", map.get("container_desc"));
			map.put("device_pid", map.get("device_id"));
			map.put("point_pid", map.get("point_id"));

			fac = new Conf01Sub1Dto();
			ObjectUtil.toObject(map, fac);
			if (facMap.get(fac.factory_pid) == null) {
				facMap.put(fac.factory_pid, fac);
				if (dto.getFacilities() == null) {
					dto.setFacilities(new ArrayList<Conf01Sub1Dto>());
				}
				dto.getFacilities().add(fac);
			}
		}

		// 디바이스
		for (Map<String, Object> map : list) {

			fac = facMap.get(map.get("factory_pid"));
			if (fac != null) {

				dev = new Conf01Sub2Dto();
				ObjectUtil.toObject(map, dev);
				if (devMap.get(dev.device_pid) == null) {
					if (fac.getDevices() == null) {
						fac.setDevices(new ArrayList<Conf01Sub2Dto>());
					}
					fac.getDevices().add(dev);
					devMap.put(dev.device_pid, dev);
				}
			}
		}

		// 포인트
		for (Map<String, Object> map : list) {

			dev = devMap.get(map.get("device_pid"));
			if (dev != null) {

				point = new Conf01Sub3Dto();
				ObjectUtil.toObject(map, point);
				if (pointMap.get(point.point_pid) == null) {
					if (dev.getPoints() == null) {
						dev.setPoints(new ArrayList<Conf01Sub3Dto>());
					}
					dev.getPoints().add(point);
					pointMap.put(point.point_pid, point);
				}
			}
		}

//		System.out.println(FxmsUtil.toJson(facMap));
		// System.out.println(FxmsUtil.toJson(devMap));
		// System.out.println(FxmsUtil.toJson(pointMap));

		return dto;
	}

}
