package fxms.ems.cems.cron;

import java.util.HashMap;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.fo.AppApi;
import fxms.bas.cron.CronResult;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.cems.dfo.MakeEpwrMonthlyChargeDfo;
import fxms.ems.cems.dfo.MakeEpwrMonthlyChargeLoadDfo;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "월 전력부하별 전기요금 생성하기")
public class MakeEpwrMonthlyChargeCron extends Crontab {

	public static void main(String[] args) {
		MakeEpwrMonthlyChargeCron cron = new MakeEpwrMonthlyChargeCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "5 3 * * *")
	private String schedule;
	private final String VAR_NAME = "fems.epwr.charge.monthly.load";
	private final String PROC_NAME_LOAD = "cems:epwr.charge.monthly.load";
	private final String PROC_NAME_CHARGE = "cems:epwr.charge.monthly";

	@Override
	public CronResult start() throws Exception {

		String month = null;
		int count = 0;
		PsKind monthly = FemsApi.kindMonthly;

		// 최종 처리일시 가져옴.
		long mstime;
		String preMonth = AppApi.getApi().getVar(VAR_NAME, (String) null);
		if (preMonth == null) {
			initVar();
			mstime = monthly.getMstimeStart(System.currentTimeMillis());
		} else {
			mstime = monthly.getMstimeStart(DateUtil.toMstime(preMonth + "01000000"));
		}

		MakeEpwrMonthlyChargeLoadDfo loadDfo = new MakeEpwrMonthlyChargeLoadDfo();
		MakeEpwrMonthlyChargeDfo monthDfo = new MakeEpwrMonthlyChargeDfo();

		for (long ms = mstime; ms < System.currentTimeMillis(); ms = monthly.getMstimeNext(ms, 1)) {

			month = DateUtil.getYyyymm(ms, 0);
			long ptime = System.currentTimeMillis();
			try {
				count = loadDfo.makeEpwrCharge(month);
				FxApi.getApi().logProc(PROC_NAME_LOAD, month, ptime, true, count,
						"worker=" + Thread.currentThread().getName());
			} catch (Exception e) {
				FxApi.getApi().logProc(PROC_NAME_LOAD, month, ptime, false, 0, e.getMessage());
				throw e;
			}

			ptime = System.currentTimeMillis();
			try {
				count = monthDfo.makeEpwrCharge(month);
				FxApi.getApi().logProc(PROC_NAME_CHARGE, month, ptime, true, count,
						"worker=" + Thread.currentThread().getName());
			} catch (Exception e) {
				FxApi.getApi().logProc(PROC_NAME_CHARGE, month, ptime, false, 0, e.getMessage());
				throw e;
			}

			// 처리 내역 기록
			AppApi.getApi().setVar(VAR_NAME, month);

		}

		return new CronResult(FxApi.makePara("month", month), FxApi.makePara("count", count));
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	private void initVar() {
		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "MONTH");
		varInfo.put("varDispName", "월단위 부하별 전기요금 생성일시");
		varInfo.put("varDesc", "월단위 부하별 전기요금을 생성한 일시를 나타낸다.");
		try {
			AppApi.getApi().setVar(VAR_NAME, varInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
