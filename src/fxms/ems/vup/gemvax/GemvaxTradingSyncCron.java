package fxms.ems.vup.gemvax;

import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;

/**
 * 젬벡스DB에서 거래 정보를 가져와 기록한다.
 * 
 * @author subkjh
 * @since 2023.07.26
 */
@FxAdapterInfo(service = "VupService", descr = "젬벡스 거래 데이터를 가져온다.")
public class GemvaxTradingSyncCron extends Crontab {

	public static void main(String[] args) {
		GemvaxTradingSyncCron cron = new GemvaxTradingSyncCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "2 * * * *")
	private String schedule;

	@Override
	public void start() throws Exception {

		new SyncTradingDfo().sync();
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}
}
