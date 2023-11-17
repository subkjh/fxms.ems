package fxms.ems.cems.cron;

import java.util.ArrayList;
import java.util.List;

import fxms.api.FxApi;
import fxms.api.fo.AppApi;
import fxms.api.fo.AppApiDfo;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.api.vo.ValueApi;
import fxms.api.vo.ValueApiDfo;
import fxms.bas.cron.CronResult;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.vo.PsKind;
import fxms.bas.vo.PsStatReqVo;
import subkjh.bas.co.log.LOG_LEVEL;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "TSDB를 이용하여 15분 단위 통계를 생성 요청한다.")
public class PsTsdb2RdbReqCron extends Crontab {

	public static void main(String[] args) {

		Logger.logger.setLevel(LOG_LEVEL.info);
		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();
		AppApi.api = new AppApiDfo();

		PsTsdb2RdbReqCron cron = new PsTsdb2RdbReqCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "1,16,21,46 * * * *")
	private String schedule;

	@Override
	public CronResult start() throws Exception {

		List<PsStatReqVo> list = new ArrayList<>();

		PsKind kind = AppApi.getApi().getPsKind(PsKind.PSKIND_15M);
		long psDate = kind.getHstimeStart(DateUtil.getDtm(System.currentTimeMillis() - 3600000L));

		PsStatReqVo vo = new PsStatReqVo("FX_V_EPWR", PsKind.PSKIND_15M, psDate);
		list.add(vo);

		boolean bret = AppApi.getApi().requestMakeStat(list);

		return new CronResult(FxApi.makePara("date", psDate), FxApi.makePara("bret", bret));

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

}
