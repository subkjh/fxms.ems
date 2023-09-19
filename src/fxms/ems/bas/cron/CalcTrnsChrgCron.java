package fxms.ems.bas.cron;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.MoApi;
import fxms.bas.api.ValueApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.ems.bas.dpo.CalculateDpo;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "에너지요금정산")
public class CalcTrnsChrgCron extends Crontab {

	public static void main(String[] args) {

		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();

		CalcTrnsChrgCron cron = new CalcTrnsChrgCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "4,19,34,49 * * * *")
	private String schedule;

	private final long DAY = 86400000L;

	public CalcTrnsChrgCron() {

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	@Override
	public String getThreadGroup() {
		return "FeMS";
	}

	@Override
	public void start() throws Exception {

		try {

			String varName = "fems-trns-chrg-date";
			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "DATE");
			varInfo.put("varDispName", "에너지 정산일자");
			varInfo.put("varDesc", "에너지 최근 정산일자를 나타낸다.");
			VarApi.getApi().updateVarInfo(varName, varInfo);

			// 최종 처리일시 가져옴.
			String date = VarApi.getApi().getVarValue(varName, DateUtil.getYmdStr(System.currentTimeMillis()));
			long mstime = DateUtil.toMstime(date + "000000") - DAY;

			CalculateDpo dpo = new CalculateDpo();

			// 최종 처리일시 이후부터 현재에서 5분전까지 처리함.
			for (long ms = mstime; ms < System.currentTimeMillis(); ms += DAY) {

				date = DateUtil.getYmdStr(ms);

				dpo.make(date);

				// 처리 내역 기록
				// 다음에 시작할 때 이전 내용을 다시해도 상관 없고 혹시 데이터가 늦게 들어올 것을 감안하여 1시간으로 돌려놓는다.
				VarApi.getApi().setVarValue(varName, date, false);

			}

		} catch (Exception e) {
			Logger.logger.error(e);
		}

	}
}
