package fxms.ems.bas.cron;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.MoApi;
import fxms.bas.api.PsApi;
import fxms.bas.api.ValueApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.dpo.MakeEnergyRawDfo;
import fxms.ems.bas.dpo.SelectEnergyPsIdDfo;
import fxms.ems.bas.vo.EngPsVo;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "에너지 계측 데이터 정리")
public class MakeEnergyRawCron extends Crontab {

	public static void main(String[] args) {

		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();

		MakeEnergyRawCron cron = new MakeEnergyRawCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "2,17,32,47 * * * *")
	private String schedule;


	public MakeEnergyRawCron() {

	}

	@Override
	public String getGroup() {
		return "FeMS";
	}

	@Override
	public void start() throws Exception {
		
		final long MIN15 = 15 * 60000L;

		try {

			String VAR_NAME = "fems-energy-made-time-raw";
			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "TIME");
			varInfo.put("varDispName", "에너지 계측 원천 생성일시");
			varInfo.put("varDesc", "에너지 계측 원천 생성일시를 나타낸다.");
			VarApi.getApi().updateVarInfo(VAR_NAME, varInfo);

			// 최종 처리일시 가져옴.

			PsKind psKind = PsApi.getApi().getPsKind(PsKind.PSKIND_15M);
			long psDtm, mstime;
			long dtm = VarApi.getApi().getVarValue(VAR_NAME, 0L);
			if (dtm == 0) {
				mstime = psKind.getMstimeStart(System.currentTimeMillis());
			} else {
				// 혹시 데이터가 늦게 들어올 것을 감안하여 1시간 전부터 처리한다.
				mstime = psKind.toMstime(psKind.getHstimeStart(dtm)) - 3600000L;
			}

			MakeEnergyRawDfo dfo = new MakeEnergyRawDfo();
			List<EngPsVo> psList = new SelectEnergyPsIdDfo().selectEnergyPsId();

			// 최종 처리일시 이후부터 현재에서 5분전까지 처리함.
			for (long ms = mstime; ms < System.currentTimeMillis(); ms += MIN15) {

				// 15분 데이터 생성
				psDtm = DateUtil.toHstime(ms);
				dfo.makeEnergyRaws(psKind, psDtm, psList);

				// 처리 내역 기록
				VarApi.getApi().setVarValue(VAR_NAME, DateUtil.toHstime(ms), false);
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
