package fxms.ems.cems.cron;

import fxms.api.FxApi;
import fxms.bas.cron.CronResult;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.cems.dfo.CheckStatePowerMeterDfo;
import subkjh.bas.co.utils.DateUtil;

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

		int pCnt = 0, gCnt = 0, hCnt = 0;
		long ptime;

		ptime = System.currentTimeMillis();
		CheckStatePowerMeterDfo dfo = new CheckStatePowerMeterDfo();
		pCnt = dfo.call(null, null);
		FxApi.getApi().logProc(dfo.getClass().getSimpleName(), DateUtil.getDtm(), ptime, true, pCnt, null);

//		ptime = System.currentTimeMillis();
//		CheckStateGasMeterDfo gasDfo = new CheckStateGasMeterDfo();
//		gCnt = gasDfo.call(null, null);
//		FxApi.getApi().logProc(gasDfo.getClass().getSimpleName(), DateUtil.getDtm(), ptime, true, gCnt, null);
//
//		ptime = System.currentTimeMillis();
//		CheckStateHeatMeterDfo heatDfo = new CheckStateHeatMeterDfo();
//		hCnt = heatDfo.call(null, null);
//		FxApi.getApi().logProc(heatDfo.getClass().getSimpleName(), DateUtil.getDtm(), ptime, true, hCnt, null);

		return new CronResult(null, FxApi.makePara("power", pCnt, "gas", gCnt, "heat", hCnt));
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

}
