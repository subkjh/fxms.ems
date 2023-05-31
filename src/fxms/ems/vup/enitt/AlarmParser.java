package fxms.ems.vup.enitt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.AlarmApi;
import fxms.bas.api.FxApi;
import fxms.bas.api.MoApi;
import fxms.bas.co.CoCode.ALARM_LEVEL;
import fxms.bas.co.CoCode.ALARM_RLSE_RSN_CD;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_AL_ALARM_HST;
import fxms.bas.impl.dbo.all.FX_MAPP_AL;
import fxms.bas.impl.dbo.all.FX_MAPP_PS;
import fxms.bas.mo.Mo;
import fxms.bas.vo.Alarm;
import fxms.bas.vo.ExtraAlarm;
import fxms.bas.vo.mapp.MappData;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dto.Alarm01Dto;
import fxms.ems.vup.dto.Alarm01Sub1Dto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.user.User;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.bas.co.utils.StringUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

/**
 * 애니트에서 보내는 알람을 기록한다.
 * 
 * @author subkjh
 *
 */
public class AlarmParser {

	class ALARM {
		String factory_id;
		String notify_id;
		String notify_type;
		String notify_state;
		String notify_count;
		String message;
		String logs;
		String created_dt;
		String updated_dt;

		String device_pid;
		String point_pid;
		String rule_id;
		String rule_nm;
		String rule_desc;
		String rule_message;
		String severity;

		long moNo;
		String psId;
		int alcdNo;
		String alarmKey;
		FX_AL_ALARM_HST alarm;
	}

	public Map<String, Object> parse(Alarm01Dto dto) throws Exception {

		int occurCnt = 0, clearCnt = 0;
		Map<String, ALARM> map = new HashMap<String, ALARM>();
		ALARM c;
		for (Alarm01Sub1Dto alarm : dto.getNotifications()) {
			c = new ALARM();
			ObjectUtil.toObject(ObjectUtil.toMap(alarm), c);
			map.put(c.notify_id, c);
			c.alarmKey = makeAlarmKeyForEnitt(c.notify_id);
		}

		try {
			init_fxms(map);
		} catch (Exception e1) {
			Logger.logger.error(e1);
			throw e1;
		}

		for (ALARM a : map.values()) {

			try {

				if ("PROCESSED".equals(a.notify_state)) {

					if (a.alarm == null) {
						// 발생후 해제된 알람인데 FXMS에 없으면
						Alarm alarm = fireAlarm(a);
						if (alarm != null) {
							clearAlarm(a);
							occurCnt++;
							clearCnt++;
						}
					} else {
						if (a.alarm.getRlseDtm() == 0) {
							clearAlarm(a);
							clearCnt++;
						}
					}

				} else if ("NEW".equals(a.notify_state)) {
					fireAlarm(a);
					occurCnt++;
				}

			} catch (Exception e) {
				Logger.logger.error(e);
				continue;
			}

		}

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("occurCnt", occurCnt);
		ret.put("clearCnt", clearCnt);
		return ret;

	}

	private void clearAlarm(ALARM a) {

		Logger.logger.info("CLEAR : {}", FxmsUtil.toJson(a));

		Alarm alarm = AlarmApi.getApi().getCurAlarm(a.alarmKey);

		if (alarm != null) {
			try {
				AlarmApi.getApi().clearAlarm(alarm, ALARM_RLSE_RSN_CD.Release, "", toMstime(a.updated_dt),
						User.USER_NO_SYSTEM);
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}
	}

	private Alarm fireAlarm(ALARM a) {

		if (a.moNo <= 0 || a.alcdNo <= 0 || a.psId == null) {
			Logger.logger.fail("not found target : {} ", FxmsUtil.toJson(a));
			return null;
		}

		ALARM_LEVEL level = getAlarmLevel(a);

		ExtraAlarm ea = new ExtraAlarm();
		ea.setAlarmKey(a.alarmKey);
		ea.setAlarmLevel(level);
		ea.setPsId(a.psId);
		ea.setOccurCnt(StringUtil.toInt(a.notify_count, 1));
		ea.setRecvEventMstime(System.currentTimeMillis());
		ea.setEventMstime(toMstime(a.created_dt));

		Logger.logger.info("FIRE : {}", FxmsUtil.toJson(ea));

		try {
			Mo mo = MoApi.getApi().getMo(a.moNo);
			AlarmApi.getApi().fireAlarm(mo, null, a.alcdNo, level, a.message, ea);
			return AlarmApi.getApi().getCurAlarm(mo, null, a.alcdNo);
		} catch (Exception e) {
			Logger.logger.error(e);
			return null;
		}

	}

	private ALARM_LEVEL getAlarmLevel(ALARM a) {
		return ALARM_LEVEL.Minor;
	}

	private void init_fxms(Map<String, ALARM> map) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();

			MappData alData, psData;

			for (ALARM a : map.values()) {

				alData = VupApi.getApi().makeMappAlarm(a.point_pid, a.point_pid);
				psData = VupApi.getApi().makeMappPs(a.point_pid, a.point_pid);

				FX_MAPP_AL al = tran.selectOne(FX_MAPP_AL.class,
						FxApi.makePara("mngDiv", alData.getMngDiv(), "mappData", alData.getMappData()));
				FX_MAPP_PS ps = tran.selectOne(FX_MAPP_PS.class,
						FxApi.makePara("mngDiv", psData.getMngDiv(), "psData", alData.getMappData()));

				FX_AL_ALARM_HST alarm = tran.selectOne(FX_AL_ALARM_HST.class, FxApi.makePara("alarmKey", a.alarmKey));

				if (ps != null) {
					a.moNo = ps.getMoNo();
					a.psId = ps.getPsId();
				}

				if (al != null) {
					a.alcdNo = al.getAlcdNo();
				}

				a.alarm = alarm;
			}

		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

	private String makeAlarmKeyForEnitt(String id) {
		return "ENITT:LOC:NOTI_ID:" + id;
	}

	private long toMstime(String s) {

		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(s);
			return date1.getTime();
		} catch (ParseException e) {
			return System.currentTimeMillis();
		}
	}

}
