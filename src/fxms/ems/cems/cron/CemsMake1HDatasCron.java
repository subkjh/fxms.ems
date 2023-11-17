package fxms.ems.cems.cron;

import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.bas.cron.FemsMake1HDatasCron;

@FxAdapterInfo(service = "AppService", descr = "1시간 단위 데이터 생성하기")
public class CemsMake1HDatasCron extends FemsMake1HDatasCron {

	public static void main(String[] args) {
		CemsMake1HDatasCron cron = new CemsMake1HDatasCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected int makeDatas(long psDtm) throws Exception {

		int count = super.makeDatas(psDtm);

		// 로깅용
		execute(psDtm, new WorkRunner() {
			@Override
			public int run() throws Exception {
				return 0;
			}

			@Override
			public String getProcName() {
				return "cems:1h";
			}
		});

		return count;
	}
}
