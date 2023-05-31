package fxms.ems.vup.cron.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.vup.dao.GemvaxQid;
import fxms.ems.vup.dpo.MakeEnergySettlementDfo;
import fxms.ems.vup.dpo.MakeEnergyTransactionDfo;
import fxms.ems.vup.dto.FeEngTransCalcDto;
import fxms.ems.vup.dto.Tran01Dto;
import fxms.ems.vup.dto.VupEngTransCalcDto;
import subkjh.bas.BasCfg;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

@FxAdapterInfo(service = "VupService", descr = "젬벡스 거래 데이터를 가져온다.")
public class GemvaxDbCron extends Crontab {

	public static void main(String[] args) {
		GemvaxDbCron cron = new GemvaxDbCron();
		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private GemvaxQid QID = new GemvaxQid();

	@FxAttr(name = "schedule", description = "실행계획", value = "5 2 * * *")
	private String schedule;

	private final String varNameTrading = "vup-gemvex-trading-seqno";
	private final String varNameSettlement = "vup-gemvex-settlement-yyyymm";

	private long getLastTrnsNo() throws Exception {
		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "TIME");
		varInfo.put("varDispName", "젬벡스거래데이터 VUP적용 최종시간");
		varInfo.put("varDesc", "젬벡스거래데이터를 VUP에 넣은 최종시간을 나타낸다.");

		VarApi.getApi().updateVarInfo(varNameTrading, varInfo);

		long seqno = VarApi.getApi().getVarValue(varNameTrading, 0L);
		return seqno;

	}

	private String getSettlementYyyymmdd() throws Exception {
		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "TIME");
		varInfo.put("varDispName", "젬벡스정산데이터 VUP적용 최종시간");
		varInfo.put("varDesc", "젬벡스정산데이터를 VUP에 넣은 최종시간을 나타낸다.");
		varInfo.put("valVal", "202001");

		VarApi.getApi().updateVarInfo(varNameSettlement, varInfo);

		String yyyymmdd = VarApi.getApi().getVarValue(varNameSettlement, "202001");
		return yyyymmdd;

	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getNewEnergyTransaction(long trnsNo) throws Exception {
		QidDao tran = DBManager.getMgr().getDataBase("GEMVAXDB")
				.createQidDao(BasCfg.getHome(GemvaxQid.QUERY_XML_FILE));
		try {
			tran.start();
			return tran.selectQid2Res(QID.select_trade_list, FxApi.makePara("trnsNo", trnsNo));
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getSettlement(String calcYm) throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase("GEMVAXDB")
				.createQidDao(BasCfg.getHome(GemvaxQid.QUERY_XML_FILE));
		try {
			tran.start();

			List<Map<String, Object>> list1 = tran.selectQid2Res(QID.select_settlement_buy_list,
					FxApi.makePara("calcYm", calcYm));
			List<Map<String, Object>> list2 = tran.selectQid2Res(QID.select_settlement_sale_list,
					FxApi.makePara("calcYm", calcYm));

			list1.addAll(list2);

			return list1;

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	@SuppressWarnings("unchecked")
	List<Map<String, Object>> getSettlementByTrnsNo(String calcYm) throws Exception {
		QidDao tran = DBManager.getMgr().getDataBase("GEMVAXDB")
				.createQidDao(BasCfg.getHome(GemvaxQid.QUERY_XML_FILE));
		try {
			tran.start();
			return tran.selectQid2Res(QID.select_settlement_list, FxApi.makePara("calcYm", calcYm));
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	private void setLastTrnsNo(long trnsNo) throws Exception {
		VarApi.getApi().setVarValue(varNameTrading, trnsNo, false);
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	@Override
	public void start() throws Exception {

		syncTrading();

		syncSettlement();

	}

	public void syncTrading() throws Exception {

		long trnsNo = getLastTrnsNo();
		trnsNo = 0;

		List<Map<String, Object>> list = getNewEnergyTransaction(trnsNo);
		MakeEnergyTransactionDfo dfo = new MakeEnergyTransactionDfo();
		Tran01Dto dto;

		for (Map<String, Object> data : list) {

			dto = new Tran01Dto();
			ObjectUtil.toObject(data, dto);
			dto.setTrnsDescr("젬벡스DB내용");

			dto.setSourcePlantPid(data.get("sellInloTid").toString());
			dto.setSinkPlantPid(data.get("buyInloTid").toString());

			dfo.setEnergyTransaction(dto.getTrnsNo(), dto);

			if (trnsNo < dto.getTrnsNo()) {
				trnsNo = dto.getTrnsNo();
			}
		}

		setLastTrnsNo(trnsNo);

	}

	public void syncSettlement() throws Exception {

		String clacYm = getSettlementYyyymmdd();

		List<Map<String, Object>> list = getSettlement(clacYm);
		MakeEnergySettlementDfo dfo = new MakeEnergySettlementDfo();
		VupEngTransCalcDto dto;

		for (Map<String, Object> data : list) {

			dto = new VupEngTransCalcDto();
			ObjectUtil.toObject(data, dto);

			dfo.setEnergySettlement(dto);

			if (clacYm.compareTo(dto.getCalcYm()) > 0) {
				clacYm = dto.getCalcYm();
			}
		}

//		setLastTrnsNo(trnsNo);

	}

	public void syncSettlementByTrnsNo() throws Exception {

		String clacYm = getSettlementYyyymmdd();

		List<Map<String, Object>> list = getSettlement(clacYm);
		MakeEnergySettlementDfo dfo = new MakeEnergySettlementDfo();
		FeEngTransCalcDto dto;

		for (Map<String, Object> data : list) {

			dto = new FeEngTransCalcDto();
			ObjectUtil.toObject(data, dto);

			System.out.println(FxmsUtil.toJson(dto));

			dfo.setEnergySettlementByTrnsNo(dto.getTrnsNo(), dto);

			if (clacYm.compareTo(dto.getCalcYm()) > 0) {
				clacYm = dto.getCalcYm();
			}
		}

		System.out.println(clacYm);

	}
}
