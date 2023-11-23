package fxms.ems.cems.cron;

import fxms.api.FxApi;
import fxms.bas.cron.CronResult;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.cems.dfo.CallGwNodesDfo;

@FxAdapterInfo(service = "AppService", descr = "게이트웨이 등록된 센서 목록 확인")
public class CemsGwNodeCheckCron extends Crontab {

	public static void main(String[] args) {
		CemsGwNodeCheckCron cron = new CemsGwNodeCheckCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "5 4 * * *")
	private String schedule;

	@Override
	public CronResult start() throws Exception {

		CallGwNodesDfo dfo = new CallGwNodesDfo();
		int updatedCount = dfo.checkNodes();

		return new CronResult(null, FxApi.makePara("updatedCount", updatedCount));
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

}
