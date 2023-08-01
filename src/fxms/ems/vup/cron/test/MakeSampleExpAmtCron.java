package fxms.ems.vup.cron.test;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.vup.dao.VupTestQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * 생산, 소비량 예측 데이터 만들기
 * 
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(service = "VupService", descr = "에너지 생산 소비 예측데이터 가상으로 만들기")
public class MakeSampleExpAmtCron extends Crontab {

	@FxAttr(description = "실행계획", value = "5 0 * * *")
	private String schedule;

	public static void main(String[] args) {
		MakeSampleExpAmtCron cron = new MakeSampleExpAmtCron();

		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final String VAR_NAME = "vup-test-exp-amt-made-time";

	@Override
	public void start() throws Exception {

		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "TEST");
		varInfo.put("varDispName", "VUP예측생산소비테스트데이터최근생성일시");
		varInfo.put("varDesc", "VUP샘플데이터 최근 생성일시를 나타낸다.");
		VarApi.getApi().updateVarInfo(VAR_NAME, varInfo);

		// 마지막 생성 일시
		int lastDate = VarApi.getApi().getVarValue(VAR_NAME, 20230401);
		lastDate = 20230418;
		long mstime = DateUtil.toMstime(lastDate + "000000") - 86400000L;
		long lastMstime = System.currentTimeMillis() + (8 * 86400000L);

		// 어제에서 오늘까지 만듬
		for (long m = mstime; m <= lastMstime; m += 86400000L) {

			String date = DateUtil.getYmd(m) + "";
			String startDate = DateUtil.getDtmStr(m - 14 * 86400000L);
			String endDate = DateUtil.getYmd(m - 86400000L) + "235959";
			Map<String, Object> para = FxApi.makePara("date", date, "startDate", startDate, "endDate", endDate);

			this.makeConsDataM15(para);

			VarApi.getApi().setVarValue(VAR_NAME, date, false);

		}

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	private void makeConsDataM15(Map<String, Object> para) throws FxTimeoutException, Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupTestQid.QUERY_XML_FILE));
		VupTestQid QID = new VupTestQid();

		try {
			tran.start();
			tran.execute(QID.make_cons_exp_amt, para);
			tran.execute(QID.make_prod_exp_amt, para);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;

		} finally {
			tran.stop();
		}
	}
}
