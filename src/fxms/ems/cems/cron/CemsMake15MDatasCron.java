package fxms.ems.cems.cron;

import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.api.vo.ValueApi;
import fxms.api.vo.ValueApiDfo;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.bas.cron.FemsMake15MDatasCron;
import fxms.ems.cems.dpo.FireEpwrThresholdDfo;
import fxms.ems.cems.dpo.MakeEpwrUnbalDfo;

@FxAdapterInfo(service = "AppService", descr = "15분 단위 데이터 생성하기")
public class CemsMake15MDatasCron extends FemsMake15MDatasCron {

	public static void main(String[] args) {

		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();

		CemsMake15MDatasCron cron = new CemsMake15MDatasCron();
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
				return "cems:15m";
			}
		});

		// 전류, 전압 불평균율 생성하기
		// (2023.10.27)속도 및 사용 여부가 불확실하여 주석 처리함

		count += execute(psDtm, new WorkRunner() {

			@Override
			public int run() throws Exception {
				MakeEpwrUnbalDfo epwrUnbal = new MakeEpwrUnbalDfo();
				return epwrUnbal.make(psDtm);
			}

			@Override
			public String getProcName() {
				return "cems:epwr.unbal";
			}
		});

		// 피크전력알람
		count += execute(psDtm, new WorkRunner() {
			@Override
			public int run() throws Exception {
				FireEpwrThresholdDfo dfo = new FireEpwrThresholdDfo();
				return dfo.checkThreshold(psDtm);
			}

			@Override
			public String getProcName() {
				return "cems:epwr.threshold";
			}
		});

		return count;
	}
}
