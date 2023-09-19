package fxms.ems.vup.cron.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dao.EnittLocQid;
import fxms.ems.vup.dto.Value01Dto;
import fxms.ems.vup.dto.Value01Sub1Dto;
import fxms.ems.vup.dto.Value01Sub2Dto;
import fxms.ems.vup.dto.Value01Sub3Dto;
import subkjh.bas.BasCfg;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

@FxAdapterInfo(service = "VupService", descr = "애니트에서 개발한 LOC에 직접 접속해서 센싱데이터를 가져오는 아답터")
public class EnittLocValueCron extends Crontab {

	class DEVICE {
		String data_id;
		String device_id;
		String col_date;
		String point_id;
		Number value;
		long mstime;
	}

	public static void main(String[] args) {
		EnittLocValueCron cron = new EnittLocValueCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long collectedDt;

	@FxAttr(name = "schedule", description = "실행계획", value = "* * * * *")
	private String schedule;

	@Override
	public Object getInPara() {
		return FxApi.makePara("collectedDt", collectedDt);
	}

	@Override
	public String getName() {
		return "애니트센싱값동기화";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start() throws Exception {

		// LOC가 RestAPI를 이용하여 PUT한 내용으로 간주한다.
		Map<String, Object> map = getMap();
		Object value = map.get("datas");

		if (value instanceof List) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) value;
			Value01Dto dto = convert(list);
			System.out.println(FxmsUtil.toJson(dto));
			new VupApi().checkValue(dto);
		}
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	private Value01Dto convert(List<Map<String, Object>> list) {

		Value01Dto dto = new Value01Dto();
		dto.setFacilities(new ArrayList<Value01Sub1Dto>());

		Value01Sub1Dto sub0 = new Value01Sub1Dto();
		Value01Sub2Dto sub1;
		Value01Sub3Dto sub2;

		// 공장
		for (Map<String, Object> map : list) {

			sub1 = new Value01Sub2Dto();
			sub1.collected_dt = map.get("col_date").toString();
			sub1.device_pid = map.get("device_pid").toString();
			sub1.device_state = 1;
			sub1.setData(new ArrayList<Value01Sub3Dto>());

			sub0.getDevices().add(sub1);

			for (String point_pid : map.keySet()) {
				sub2 = new Value01Sub3Dto();
				sub2.point_pid = point_pid.toUpperCase();
				sub2.value =(Number) map.get(point_pid);
				sub1.getData().add(sub2);
			}

		}

		return dto;
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

		this.collectedDt = VarApi.getApi().getVarValue("enitt_loc.select-value-list", 19701104000000L);

		long nowCollectedDt = collectedDt;
		try {
			tran.start();

			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> mapList = tran.selectQid2Res(qid.select_value_list, getInPara());

			if (mapList.size() > 0) {

				Map<String, Object> newMap;

				for (Map<String, Object> map : mapList) {

					nowCollectedDt = toHstime(map.get("COL_DATE").toString());
					if (nowCollectedDt > collectedDt)
						collectedDt = nowCollectedDt;

					// 애니트 API에서 소문자를 사용하여 키를 소문자로 변경한다.
					newMap = new HashMap<String, Object>();
					for (String key : map.keySet()) {
						newMap.put(key.toLowerCase(), map.get(key));
					}
					retList.add(newMap);
				}

				// 최근 노티ID를 기록한다.
				VarApi.getApi().setVarValue("enitt_loc.select-value-list", collectedDt, false);
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

	private long toHstime(String s) {

		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
			return DateUtil.toHstime(date1.getTime());
		} catch (ParseException e) {
			return System.currentTimeMillis();
		}
	}
}
