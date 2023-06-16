package fxms.ems.bas.cron;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.PsApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.dpo.MakeEnergyAmtDfo;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "에너지 공급/소비 원천 데이터 생성하기")
public class MakeEnergyConsProdAmtCron extends Crontab {

	@FxAttr(name = "schedule", description = "실행계획", value = "3,18,33,48 * * * *")
	private String schedule;

	public MakeEnergyConsProdAmtCron() {

	}

	@Override
	public String getGroup() {
		return "FEMS";
	}

	@Override
	public void start() throws Exception {

		final long MIN15 = 15 * 60000L;
		final String VAR_NAME = "fems-energy-made-time-amt";

		try {

			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "TIME");
			varInfo.put("varDispName", "에너지 생산/소비량 생성일시");
			varInfo.put("varDesc", "에너지 생산/소비량 생성일시를 나타낸다.");
			VarApi.getApi().updateVarInfo(VAR_NAME, varInfo);

			// 최종 처리일시 가져옴.
			long dtm = VarApi.getApi().getVarValue(VAR_NAME, 20230404000000L);

			PsKind psKind = PsApi.getApi().getPsKind("MIN15");
			long mstime = psKind.toMstime(psKind.getHstimeStart(dtm));
			long psDtm;

			// 최종 처리일시 이후부터 현재에서 5분전까지 처리함.
			for (long ms = mstime; ms < System.currentTimeMillis(); ms += MIN15) {

				psDtm = DateUtil.toHstime(ms);

				new MakeEnergyAmtDfo().makeDatas(psDtm);

				// 처리 내역 기록
				// 다음에 시작할 때 이전 내용을 다시해도 상관 없고 혹시 데이터가 늦게 들어올 것을 감안하여 1시간으로 돌려놓는다.
				VarApi.getApi().setVarValue(VAR_NAME, DateUtil.toHstime(ms - MIN15 * 4), false);
			}

		} catch (Exception e) {
			Logger.logger.error(e);
		}
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

}
