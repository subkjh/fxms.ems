package fxms.ems.cems.cron;

import fxms.api.FxApi;
import fxms.bas.cron.CronResult;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.cems.dfo.CheckSensorStateDfo;

@FxAdapterInfo(service = "AppService", descr = "전력계 데이터 수신 여부 확인")
public class CemsCheckSensorStateCron extends Crontab {

	public static void main(String[] args) {
		CemsCheckSensorStateCron cron = new CemsCheckSensorStateCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "* * * * *")
	private String schedule;

	@Override
	public CronResult start() throws Exception {

		CheckSensorStateDfo dfo = new CheckSensorStateDfo();
		int checkSize = dfo.checkSensorState();

		return new CronResult(null, FxApi.makePara("check-size", checkSize));
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

}
