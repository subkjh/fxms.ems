package fxms.ems.vup.cron.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.bas.dbo.FE_ENG_RT_EXP;
import fxms.ems.vup.dao.IaeQid;
import subkjh.bas.BasCfg;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * 
 * @author subkjh
 * @since 2023.07.24
 */

@FxAdapterInfo(service = "VupService", descr = "고기원 예측 데이터를 가져온다.")
public class IaeDbCron extends Crontab {

	public static void main(String[] args) {
		IaeDbCron cron = new IaeDbCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IaeQid QID = new IaeQid();

	@FxAttr(name = "schedule", description = "실행계획", value = "1,16,31,46 * * * *")
	private String schedule;

	private final String VAR_NAME_STEAM = "vup-iae-steam-history-id";
	private final String VAR_NAME_AIR = "vup-iae-air-history-id";

	@Override
	public void start() throws Exception {

		syncSteam();

		syncAir();

	}

	/**
	 * 압공 동기화
	 * 
	 * @throws Exception
	 */
	public void syncAir() throws Exception {

		long historyId = this.getHistoryId(VAR_NAME_AIR, "압공");
		List<Map<String, Object>> list = this.getAirPrediction(historyId);

		if (list.size() > 0) {
			Map<String, Object> last = list.get(list.size() - 1);
			long lastHistoryId = ((Number) last.get("historyId")).longValue();
			ClassDaoEx.open().setOfClass(FE_ENG_RT_EXP.class, list).close();
			setHistoryId(VAR_NAME_AIR, lastHistoryId);
		}

	}

	/**
	 * 가져온 예측값을 FE_ENG_CONS_xxx, FE_ENG_PROD_xxx 소비, 생산을 구분하여 넣는다.
	 */
	public void makeExpAmt() {
		// TODO
	}

	/**
	 * 스팀 동기화
	 * 
	 * @throws Exception
	 */
	public void syncSteam() throws Exception {

		long historyId = this.getHistoryId(VAR_NAME_STEAM, "스팀");
		List<Map<String, Object>> list = this.getSteamPrediction(historyId);

		if (list.size() > 0) {
			Map<String, Object> last = list.get(list.size() - 1);
			long lastHistoryId = ((Number) last.get("historyId")).longValue();
			ClassDaoEx.open().setOfClass(FE_ENG_RT_EXP.class, list).close();
			setHistoryId(VAR_NAME_STEAM, lastHistoryId);
		}

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	/**
	 * 압공 예측값 가져오기
	 * 
	 * @param historyId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getAirPrediction(long historyId) throws Exception {

		Map<String, Object> para = FxApi.makePara("history_id", historyId);

		QidDao tran = DBManager.getMgr().getDataBase("IAEDB").createQidDao(BasCfg.getHome(IaeQid.QUERY_XML_FILE));
		try {
			tran.start();
			return tran.selectQid2Res(QID.select_iae_air_compressed_prediction, para);
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	/**
	 * 최종 가져온 데이터 번호
	 * 
	 * @param var
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private long getHistoryId(String var, String name) throws Exception {

		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "TIME");
		varInfo.put("varDispName", "고기원 예측데이터 VUP적용 최종시간" + "(" + name + ")");
		varInfo.put("varDesc", "고기원 예측 데이터를 VUP에 넣은 최종시간을 나타낸다.");
		varInfo.put("valVal", "0");

		VarApi.getApi().updateVarInfo(var, varInfo);

		return VarApi.getApi().getVarValue(var, 0L);
	}

	/**
	 * 스팀 예측값 가져오기
	 * 
	 * @param historyId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getSteamPrediction(long historyId) throws Exception {

		Map<String, Object> para = FxApi.makePara("history_id", historyId);

		QidDao tran = DBManager.getMgr().getDataBase("IAEDB").createQidDao(BasCfg.getHome(IaeQid.QUERY_XML_FILE));
		try {
			tran.start();
			return tran.selectQid2Res(QID.select_iae_steam_prediction, para);
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	/**
	 * 최종 가져온 데이터 기록
	 * 
	 * @param var
	 * @param historyId
	 * @throws Exception
	 */
	private void setHistoryId(String var, long historyId) throws Exception {
		VarApi.getApi().setVarValue(var, historyId, false);
	}
}
