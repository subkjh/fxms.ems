package fxms.ems.cems.cron;

import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.api.vo.ValueApi;
import fxms.api.vo.ValueApiDfo;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.bas.cron.FemsMake15MDatasCron;
import fxms.ems.cems.dfo.FireEpwrThresholdDfo;
import fxms.ems.cems.dfo.MakeE13_15MDfo;
import fxms.ems.cems.dfo.MakeE14_15MDfo;
import fxms.ems.cems.dfo.MakeE41_15MDfo;
import fxms.ems.cems.dfo.MakeEpwrUnbalDfo;

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

	public CemsMake15MDatasCron() {
		// 한전의 사용데이터를 고려하여 1시간 이전부터 재생성한다.
		this.remakeNumber = 4;
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

		// 한전 사용 전력을 에너지 계측 테이블에 넣는다. ( E13 )
		count += execute(psDtm, new WorkRunner() {
			@Override
			public int run() throws Exception {
				MakeE13_15MDfo dfo = new MakeE13_15MDfo();
				return dfo.make(psDtm);
			}

			@Override
			public String getProcName() {
				return "cems:15m.E13";
			}
		});

		// 설비 전력을 에너지 계측 테이블에 넣는다. ( E14 )
		count += execute(psDtm, new WorkRunner() {
			@Override
			public int run() throws Exception {
				MakeE14_15MDfo dfo = new MakeE14_15MDfo();
				return dfo.make(psDtm);
			}

			@Override
			public String getProcName() {
				return "cems:15m.E14";
			}
		});

		// 석유환산톤 데이터 생성. ( E41 )
		count += execute(psDtm, new WorkRunner() {
			@Override
			public int run() throws Exception {
				MakeE41_15MDfo dfo = new MakeE41_15MDfo();
				return dfo.make(psDtm);
			}

			@Override
			public String getProcName() {
				return "cems:15m.E41";
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
