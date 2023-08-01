package fxms.ems.vup.gemvax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.InloApi;
import fxms.bas.api.VarApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.Inlo;
import fxms.ems.bas.dbo.FE_ENG_TRANS_BAS;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dao.GemvaxQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

/**
 * 젬벡스 거래 내용 동기화
 * 
 * @author subkjh
 * @since 2023.07.26
 */
public class SyncTradingDfo implements FxDfo<Void, Integer> {

	public static void main(String[] args) {
		SyncTradingDfo dfo = new SyncTradingDfo();
		try {
			dfo.sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final String VAR = "vup-gemvex-last-trading-date";

	public SyncTradingDfo() {

	}

	@Override
	public Integer call(FxFact fact, Void data) throws Exception {
		return sync();
	}

	public int sync() throws Exception {

		List<FE_ENG_TRANS_BAS> tradeList = getNewTradeList();

		return insertTrade(tradeList);

	}

	private long getLastTradingDate() throws Exception {
		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "TIME");
		varInfo.put("varDispName", "젬벡스거데이터 VUP적용일시");
		varInfo.put("varDesc", "젬벡스거래데이터를 VUP에 넣은 최종시간을 나타낸다.");

		VarApi.getApi().updateVarInfo(VAR, varInfo);

		long date = VarApi.getApi().getVarValue(VAR, DateUtil.getDtm(System.currentTimeMillis() - 7 * 86400000L));
		return date;
	}

	private List<FE_ENG_TRANS_BAS> getNewTradeList() throws Exception {

		Map<String, Inlo> inloMap = InloApi.getApi().getInloTidMap();

		long tradingDate = getLastTradingDate();

		List<Map<String, Object>> datas = selectTradingList(tradingDate);
		List<FE_ENG_TRANS_BAS> tradeList = new ArrayList<>();
		Inlo inlo;
		FE_ENG_TRANS_BAS bas;
		for (Map<String, Object> data : datas) {
			bas = ObjectUtil.toObject(data, FE_ENG_TRANS_BAS.class);

			inlo = inloMap.get(data.get("complexTid").toString());
			if (inlo != null) {
				bas.setInloNo(inlo.getInloNo());
			}
			inlo = inloMap.get(data.get("sellPlantTid").toString());
			if (inlo != null) {
				bas.setSellInloNo(inlo.getInloNo());
			}

			inlo = inloMap.get(data.get("buyPlantTid").toString());
			if (inlo != null) {
				bas.setBuyInloNo(inlo.getInloNo());
			}

			bas.setEngId(VupApi.getApi().getEngId(data.get("engTid").toString()));

			tradeList.add(bas);
		}

		return tradeList;
	}

	private int insertTrade(List<FE_ENG_TRANS_BAS> tradeList) throws Exception {

		ClassDaoEx dao = ClassDaoEx.open();

		int ret = 0;
		Map<String, Object> para = new HashMap<>();
		FE_ENG_TRANS_BAS old;
		long lastTradingDate = 0L;
		long nowDate = DateUtil.getDtm();

		for (FE_ENG_TRANS_BAS bas : tradeList) {

			if (bas.getTrnsFnshDtm() < nowDate) {
//				TRNS_ST_CD	0	계약전
//				TRNS_ST_CD	1	계약유지
//				TRNS_ST_CD	2	계약해지
				bas.setTrnsStCd("2");
			} else {
				bas.setTrnsStCd("1");
			}

			para.put("cntrtDocNum", bas.getCntrtDocNum());
			old = dao.selectData(FE_ENG_TRANS_BAS.class, para);

			if (old == null) {

				bas.setTrnsNo(dao.getNextVal(FE_ENG_TRANS_BAS.FX_SEQ_TRNSNO, Long.class));
				FxTableMaker.initRegChg(0, bas);
				dao.insertOfClass(FE_ENG_TRANS_BAS.class, bas);

				ret++;

			} else {
				// update는 하지 않는다.
			}

			if (lastTradingDate < bas.getTrnsDtm()) {
				lastTradingDate = bas.getTrnsDtm();
			}
		}

		dao.close();

		setLastTradingDate(lastTradingDate);

		return ret;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> selectTradingList(long tradingDate) throws Exception {
		QidDao tran = DBManager.getMgr().getDataBase("GEMVAXDB").createQidDao(BasCfg.getHome(GemvaxQid.QUERY_XML_FILE));
		GemvaxQid QID = new GemvaxQid();
		try {
			tran.start();
			return tran.selectQid2Res(QID.select_trading_list, FxApi.makePara("tradingDate", tradingDate));
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	private void setLastTradingDate(long date) throws Exception {
		VarApi.getApi().setVarValue(VAR, date, false);
	}
}
