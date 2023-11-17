package fxms.ems.cems.cron;

import java.util.HashMap;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.fo.AppApi;
import fxms.bas.cron.CronResult;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.cems.dpo.MakeEpwr1HChargeDfo;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "1시간 단위 전기요금 생성하기")
public class MakeEpwr1HChargeCron extends Crontab {

	public static void main(String[] args) {

		MakeEpwr1HChargeCron cron = new MakeEpwr1HChargeCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "5 * * * *")
	private String schedule;
	private final String VAR_NAME = "fems.epwr.charge.1h.date";
	private final String PROC_NAME = "cems:epwr.charge.1h";

	@Override
	public CronResult start() throws Exception {
		String date = null;
		int count = 0;

		// 최종 처리일시 가져옴.
		long mstime;
		String preDate = AppApi.getApi().getVar(VAR_NAME, (String) null);

		if (preDate == null) {
			initVar();
			mstime = System.currentTimeMillis();
		} else {
			mstime = DateUtil.toMstime(preDate + "000000");
		}

		Logger.logger.debug("date={}", preDate);

		// 최종 처리일시 이후부터 현재에서 5분전까지 처리함.
		MakeEpwr1HChargeDfo dfo = new MakeEpwr1HChargeDfo();

		for (long ms = mstime; ms < System.currentTimeMillis(); ms += 86400000L) {

			date = DateUtil.getYyyymmdd(ms, 0);
			long ptime = System.currentTimeMillis();

			try {
				count = dfo.makeEpwrCharge(date);
				FxApi.getApi().logProc(PROC_NAME, date, ptime, true, count,
						"worker=" + Thread.currentThread().getName());
			} catch (Exception e) {
				FxApi.getApi().logProc(PROC_NAME, date, ptime, false, 0, e.getMessage());
				throw e;
			}

			// 처리 내역 기록
			AppApi.getApi().setVar(VAR_NAME, date);

		}

		return new CronResult(FxApi.makePara("date", date), FxApi.makePara("count", count));

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	private void initVar() {
		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "DATE");
		varInfo.put("varDispName", "1시간 단위 전기요금 생성일시");
		varInfo.put("varDesc", "1시간 단위 전기요금을 생성한 일시를 나타낸다.");
		try {
			AppApi.getApi().setVar(VAR_NAME, varInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
