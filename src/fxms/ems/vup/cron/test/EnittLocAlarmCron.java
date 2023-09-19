package fxms.ems.vup.cron.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.vup.dao.EnittLocQid;
import fxms.ems.vup.dto.Alarm01Dto;
import fxms.ems.vup.dto.Alarm01Sub1Dto;
import fxms.ems.vup.enitt.AlarmParser;
import subkjh.bas.BasCfg;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

@FxAdapterInfo(service = "VupService", descr = "애니트에서 개발한 LOC에 직접 접속해서 알람데이터 가져오는 아답터")
public class EnittLocAlarmCron extends Crontab {

	public static void main(String[] args) {
		EnittLocAlarmCron cron = new EnittLocAlarmCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long lastNotiId = 0;

	@FxAttr(name = "schedule", description = "실행계획", value = "* * * * *")
	private String schedule;

	@Override
	public Object getInPara() {
		return FxApi.makePara("lastNotiId", lastNotiId);
	}

	@Override
	public String getName() {
		return "애니트알람동기화";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start() throws Exception {

		// LOC가 RestAPI를 이용하여 PUT한 내용으로 간주한다.
		Map<String, Object> map = getMap();
		Object value = map.get("datas");

		if (value instanceof List) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) value;
			Map<String, Alarm01Dto> dtoMap = convert(list);

			for (Alarm01Dto dto : dtoMap.values()) {
				System.out.println(FxmsUtil.toJson(dto));
				new AlarmParser().parse(dto);
			}

		}
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	private Map<String, Alarm01Dto> convert(List<Map<String, Object>> list) {
		Alarm01Dto dto;
		Alarm01Sub1Dto alarm;

		Map<String, Alarm01Dto> dtoMap = new HashMap<String, Alarm01Dto>();

		// 공장
		for (Map<String, Object> map : list) {

			alarm = new Alarm01Sub1Dto();
			ObjectUtil.toObject(map, alarm);
			dto = dtoMap.get(map.get("factory_pid"));
			if (dto == null) {
				dto = new Alarm01Dto();
				dto.factory_id = map.get("factory_pid").toString();
				dtoMap.put(dto.factory_id, dto);
				dto.setNotifications(new ArrayList<Alarm01Sub1Dto>());
			}
			dto.getNotifications().add(alarm);
		}

//		System.out.println(FxmsUtil.toJson(facMap));
		// System.out.println(FxmsUtil.toJson(devMap));
		// System.out.println(FxmsUtil.toJson(pointMap));

		return dtoMap;
	}

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

		// 현재까지 적재된 번호를 가져온다.
		this.lastNotiId = VarApi.getApi().getVarValue("enitt_loc.select-alarm-list", -1L);
		long nowNotiId;

		try {
			tran.start();
			List<Map<String, Object>> mapList = tran.selectQid2Res(qid.select_alarm_list, getInPara());
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

			if (mapList.size() > 0) {
				Map<String, Object> newMap;
				for (Map<String, Object> map : mapList) {

					nowNotiId = ((Number) map.get("notify_id")).longValue();
					if (nowNotiId > lastNotiId)
						lastNotiId = nowNotiId;

					newMap = new HashMap<String, Object>();
					for (String key : map.keySet()) {
						newMap.put(key.toLowerCase(), map.get(key));
					}
					retList.add(newMap);
				}

				// 최근 노티ID를 기록한다.
				VarApi.getApi().setVarValue("enitt_loc.select-alarm-list", lastNotiId, false);
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

}
