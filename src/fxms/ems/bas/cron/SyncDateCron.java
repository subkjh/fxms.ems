package fxms.ems.bas.cron;

import fxms.bas.api.MoApi;
import fxms.bas.api.ValueApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.ems.bas.dpo.SyncDateDfo;

@FxAdapterInfo(service = "AppService", descr = "요일정보를 TISP-DATA에서 가져와 동기화한다.")
public class SyncDateCron extends Crontab {

	public static void main(String[] args) {

		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();

		SyncDateCron cron = new SyncDateCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "8 12 * * *")
	private String schedule;

	public SyncDateCron() {

	}

	@Override
	public String getGroup() {
		return "FeMS";
	}

	@Override
	public void start() throws Exception {

		SyncDateDfo dfo = new SyncDateDfo();
		dfo.syncDate();

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}
}
