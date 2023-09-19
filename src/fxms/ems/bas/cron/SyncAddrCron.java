package fxms.ems.bas.cron;

import fxms.bas.api.MoApi;
import fxms.bas.api.ValueApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.ems.bas.dpo.SyncAddrDfo;

@FxAdapterInfo(service = "AppService", descr = "법정동을 TISP-DATA에서 가져와 동기화한다.")
public class SyncAddrCron extends Crontab {

	public static void main(String[] args) {

		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();

		SyncAddrCron cron = new SyncAddrCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "5 5 * * *")
	private String schedule;

	public SyncAddrCron() {

	}

	@Override
	public String getThreadGroup() {
		return "FeMS";
	}

	@Override
	public void start() throws Exception {
		new SyncAddrDfo().syncAddr();
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}
}
