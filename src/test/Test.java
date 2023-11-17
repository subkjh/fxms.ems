package test;

import java.util.Comparator;
import java.util.List;

import fxms.api.FxDataApi;
import fxms.api.ao.AlarmApi;
import fxms.api.ao.AlarmApiDfo;
import fxms.api.fo.AppApi;
import fxms.api.fo.AppApiDfo;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.api.vo.ValueApi;
import fxms.api.vo.ValueApiDfo;
import fxms.bas.fxo.FxActorParser;
import fxms.bas.handler.vo.HandlerVo;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.dpo.MakeMeasrRawDfo;
import fxms.ems.bas.dpo.SelectEnergyPsIdDfo;
import fxms.ems.bas.vo.EngPsVo;

public class Test {

	public static void main(String[] args) throws Exception {

		Test test = new Test();

		test.testHandler();

//		test.불균형(new float[] { 226.3f, 225.7f, 225.2f });
//		test.불균형(new float[] { 220f, 230f, 210f }); // 4.55
//		test.불균형(new float[] { 225.1f, 224.6f, 226.1f }); // 0.402
//		test.불균형(new float[] { 9.947f, 9.809f, 9.695f }); // 1.676

//        "ia": 9.947000503540039,
//        "ib": 9.809000015258789,
//        "equipmentName": "텐타 #2",
//        "ic": 9.695000648498535,
//        "iuf": 1.676233322982645,
	}

	void 불균형(float values[]) {

		StringBuffer sb = new StringBuffer();
		float sum = 0, avg, maxDeviation = 0, unbalance, unbalance2;
		float min = values[0], max = values[0];
		float 중성선전류값;

		for (float v : values) {
			sum += v;
		}

		avg = sum / values.length;

		for (float v : values) {
			if (sb.length() == 0) {
				sb.append("(");
			} else {
				sb.append(",");
			}
			sb.append(v);
			if (Math.abs(v - avg) > maxDeviation) {
				maxDeviation = Math.abs(v - avg);
			}

			if (min > v)
				min = v;
			if (max < v)
				max = v;
		}

		중성선전류값 = (max - min) * 0.9f;
		unbalance = (maxDeviation / avg) * 100;
		unbalance2 = (중성선전류값) * 100 / avg;

		sb.append(") avg=").append(avg);
		sb.append(", maxDeviation=").append(maxDeviation);
		sb.append(", max-min=").append(max - min);
		sb.append(", unbalance=").append(unbalance);
		sb.append(", unbalance2=").append(unbalance2);

		System.out.println(sb.toString());

	}

	public void test2() throws Exception {

		AppApi.api = new AppApiDfo();
		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();
		AlarmApi.api = new AlarmApiDfo();

		List<EngPsVo> psList = new SelectEnergyPsIdDfo().selectEnergyPsId();

		MakeMeasrRawDfo dfo = new MakeMeasrRawDfo();
		try {
			int ret = dfo.makeEnergyRaws(AppApi.getApi().getPsKind(PsKind.PSKIND_15M), 20231019000000L, psList);
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void testHandler() {
		FxDataApi.serviceName = "WebService";
		List<HandlerVo> handlers = FxActorParser.getParser().getActorList(HandlerVo.class);

		// 0을 제일 뒤로 보낸다.
		handlers.sort(new Comparator<HandlerVo>() {
			@Override
			public int compare(HandlerVo o1, HandlerVo o2) {
				return o1.getPort() > o2.getPort() ? -1 : 1;
			}
		});

		System.out.println(handlers.size());

		for (HandlerVo vo : handlers) {
			System.out.println(vo.getPort());
		}
	}
}
