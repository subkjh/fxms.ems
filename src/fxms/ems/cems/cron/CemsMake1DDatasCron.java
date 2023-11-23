package fxms.ems.cems.cron;

import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.bas.cron.FemsMake1DDatasCron;
import fxms.ems.cems.dfo.CopyKepcoEpwrChargeDfo;
import fxms.ems.cems.dfo.CopyPriceInfoDfo;

@FxAdapterInfo(service = "AppService", descr = "일 단위 데이터 생성하기")
public class CemsMake1DDatasCron extends FemsMake1DDatasCron {

	public static void main(String[] args) {
		CemsMake1DDatasCron cron = new CemsMake1DDatasCron();
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
				return "cems:1d";
			}
		});

		// 전기요금 복사
		count += execute(psDtm, new WorkRunner() {
			@Override
			public int run() throws Exception {
				return new CopyKepcoEpwrChargeDfo().makeEpwrCharge(String.valueOf(psDtm).substring(0, 6));
			}

			@Override
			public String getProcName() {
				return "cems:kepco > epwr.charge";
			}
		});

		// 계약정보 복사
		count += execute(psDtm, new WorkRunner() {
			@Override
			public int run() throws Exception {
				return new CopyPriceInfoDfo().updatePriceInfo();
			}

			@Override
			public String getProcName() {
				return "cems:kepco > epwr.inlo.price";
			}
		});

		return count;
	}
}
