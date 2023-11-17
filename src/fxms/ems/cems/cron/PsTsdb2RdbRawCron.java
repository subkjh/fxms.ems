package fxms.ems.cems.cron;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import fxms.bas.vo.PsVoRawList;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.cems.dpo.SelectTsdbDataDfo;
import subkjh.bas.co.log.LOG_LEVEL;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "CemsService", descr = "TSDB의 내용을 RDB로 복사한다.")
public class PsTsdb2RdbRawCron extends Crontab {

	public static void main(String[] args) {

		Logger.logger.setLevel(LOG_LEVEL.info);
		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();
		AppApi.api = new AppApiDfo();

		PsTsdb2RdbRawCron cron = new PsTsdb2RdbRawCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getThreadGroup() {
		return "TSDB2RDB_RAW";
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "* * * * *")
	private String schedule;
	private final String VAR_NAME = "cems.tsdb.to.rdb.time";

	@Override
	public CronResult start() throws Exception {

		String dates = null;
		PsKind min = FemsApi.kind1M;
		int count = 0;
		long mstime; // 최종 처리일시 가져옴.
		long endMstime = System.currentTimeMillis() - 180000L; // 2분전
		long hstime = 0, startDtm, endDtm;
		long preDtm = AppApi.getApi().getVar(VAR_NAME, 0L);
		if (preDtm == 0L) {
			initVar();
			return null;
		} else {
			mstime = min.getMstimeStart(DateUtil.toMstime(preDtm));
			// 기록된 시간까지 처리했으므로 이후 처리 내용을 변경한다.
			mstime = min.getMstimeNext(mstime, 1);
		}

		SelectTsdbDataDfo dfo = new SelectTsdbDataDfo();
		dfo.initDatas();

		dates = DateUtil.toHstime(mstime) + "~";

		for (long ms = mstime; ms < endMstime; ms = min.getMstimeNext(ms, 1)) {

			long ptime = System.currentTimeMillis();
			int cnt = 0;
			;

			hstime = DateUtil.toHstime(ms);
			startDtm = min.getHstimeStart(hstime);
			endDtm = min.getHstimeEnd(hstime);

			// 1분 단위로 데이터를 가져옴
			List<PsVoRawList> values = dfo.selectData(startDtm, endDtm);

			if (values != null && values.size() > 0) {
				PsVoRawList raw = mergeTest(ms, values);
				if (raw != null) {
					ValueApi.getApi().addValues(raw, false);
					count += raw.size();
					cnt = raw.size();
				}
			}

			Logger.logger.info("time={}:{}~{}, datas={}", hstime, startDtm, endDtm, values.size());

			FxApi.getApi().logProc("cems:tsdb > rdb", hstime, ptime, true, cnt, null);

			// 처리 내역 기록
			AppApi.getApi().setVar(VAR_NAME, hstime);

		}

		if (hstime != 0) {
			dates += hstime;
		} else {
			dates = "";
		}

		return new CronResult(FxApi.makePara("date", dates), FxApi.makePara("count", count));
	}

	/**
	 * 조회 시작 시간으로 병합한다.
	 * 
	 * @param values
	 * @return
	 */
	private PsVoRawList mergeTest(long ms, List<PsVoRawList> values) {

		if (values.size() == 0) {
			return null;
		}

		PsVoRawList ret = new PsVoRawList("TSDB", ms);
		for (PsVoRawList raw : values) {
			ret.add(raw);
		}
		return ret;
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	private void initVar() {
		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "TIME");
		varInfo.put("varDispName", "TSDB복사 시간");
		varInfo.put("varDesc", "TSDB 데이터를 RDB로 복사한다.");
		varInfo.put("varVal", DateUtil.toHstime(System.currentTimeMillis() - 300000L));
		try {
			AppApi.getApi().setVar(VAR_NAME, varInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
