package fxms.ems.bas.cron;

import fxms.bas.api.MoApi;
import fxms.bas.api.ValueApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.ems.bas.dpo.SyncWeatherDfo;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "날씨정보를 TISP-DATA에서 가져와 동기화한다.")
public class SyncWeatherCron extends Crontab {

	public static void main(String[] args) {

		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();

		SyncWeatherCron cron = new SyncWeatherCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "8 * * * *")
	private String schedule;

	public SyncWeatherCron() {

	}

	@Override
	public String getGroup() {
		return "FeMS";
	}

	@Override
	public void start() throws Exception {

		SyncWeatherDfo dfo = new SyncWeatherDfo();

		dfo.syncWeather(DateUtil.getYmdStr(System.currentTimeMillis() - 86400000L)); // 어제

		dfo.syncWeather(DateUtil.getYmdStr()); // 오늘

		dfo.syncWeather(DateUtil.getYmdStr(System.currentTimeMillis() + 86400000L)); // 내일

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}
}
