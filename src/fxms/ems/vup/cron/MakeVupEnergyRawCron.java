package fxms.ems.vup.cron;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.PsApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.vo.PsKind;
import fxms.ems.vup.dao.VupQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;
import subkjh.dao.QidDaoEx;
import subkjh.dao.database.DBManager;

@FxAdapterInfo(service = "VupService", descr = "에너지 공급/소비 원천 데이터 생성하기")
public class MakeVupEnergyRawCron extends Crontab {

	enum SOURCE_SINK {
		source, sink;
	}

	public static void main(String[] args) {
		MakeVupEnergyRawCron cron = new MakeVupEnergyRawCron();
		try {
			cron.start();
//			Map<Long, SOURCE_SINK> map = cron.selectSensorSourceSink();
//			System.out.println(FxmsUtil.toJson(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "3,18,33,48 * * * *")
	private String schedule;
	private final VupQid QID = new VupQid();

	private final long MIN15 = 15 * 60000L;

	public MakeVupEnergyRawCron() {

	}

	@Override
	public String getGroup() {
		return "FeMS";
	}

	@Override
	public void start() throws Exception {
		try {

			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "TIME");
			varInfo.put("varDispName", "VUP에너지최근생성일시");
			varInfo.put("varDesc", "VUP에너지 최근 생성일시를 나타낸다.");
			VarApi.getApi().updateVarInfo("vup-energy-made-time", varInfo);

			// 최종 처리일시 가져옴.
			long dtm = VarApi.getApi().getVarValue("vup-energy-made-time", 20230404000000L);
			
			PsKind psKind = PsApi.getApi().getPsKind(PsKind.PSKIND_15M);
			long mstime = psKind.toMstime(psKind.getHstimeStart(dtm));
			long hstime;

			// 최종 처리일시 이후부터 현재에서 5분전까지 처리함.
			for (long ms = mstime; ms < System.currentTimeMillis(); ms += MIN15) {

				hstime = DateUtil.toHstime(mstime);

				// 15분 데이터 생성
				this.makeVupFromEnergy(hstime); // FEMS 원천을 VUP 원천으로
				this.makeVupToAmt(hstime); // VUP 원천으로 생산/소비량 15분
				this.makeAmt15MTo1H(hstime); // VUP 15분으로 1시간 데이터
				this.makeAmt1HToStat(hstime); // VUP 1시간 데이터로 1일 데이터

				// 처리 내역 기록
				// 다음에 시작할 때 이전 내용을 다시해도 상관 없고 혹시 데이터가 늦게 들어올 것을 감안하여 1시간으로 돌려놓는다.
				VarApi.getApi().setVarValue("vup-energy-made-time", DateUtil.toHstime(ms - MIN15 * 4), false);
			}

		} catch (Exception e) {
			Logger.logger.error(e);
		}
	}

	/**
	 * 15분 데이터를 이용하여 1시간 데이터 생성
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	void makeAmt15MTo1H(long hstime) throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		try {
			tran.start();
			String dateHh = String.valueOf(hstime).substring(0, 10);

			tran.execute(QID.make_cons_h1_from_m15, dateHh);
			tran.execute(QID.make_prod_h1_from_m15, dateHh);

			tran.commit();

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	/**
	 * 1시간 데이터를 이용하여 일 데이터 생성
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	void makeAmt1HToStat(long hstime) throws Exception {

		String date = String.valueOf(hstime).substring(0, 8);

		QidDaoEx.open(BasCfg.getHome(VupQid.QUERY_XML_FILE))//
				.execute(QID.make_cons_stat_from_h1, date) //
				.execute(QID.make_prod_stat_from_h1, date)//
				.close();

	}

	void makeVupFromEnergy(long measrDtm) throws Exception {

		Map<String, Object> consPara = FxApi.makePara("vupEngTable", "VUP_ENG_CONS_RAW", "measrDtm", measrDtm,
				"measrDtmName", "CONS_DTM", "sinkSource", "SINK");

		Map<String, Object> prodPara = FxApi.makePara("vupEngTable", "VUP_ENG_PROD_RAW", "measrDtm", measrDtm,
				"measrDtmName", "PROD_DTM", "sinkSource", "SOURCE");

		Map<String, Object> epwrPara = FxApi.makePara("measrDtm", measrDtm);

		QidDaoEx.open(BasCfg.getHome(VupQid.QUERY_XML_FILE))//
				.execute(QID.make_vup_raw_from_energy_raw, consPara) // sink 계측 내용
				.commit() //
				.execute(QID.make_vup_raw_from_energy_raw, prodPara) // source 계측 내용
				.commit() //
				.execute(QID.make_vup_raw_from_energy_raw_epower, epwrPara) // 전력은 거래가 되지 않은 관계로 별도 계산해야 한다.
				.close();

	}

	/**
	 * VUP 원천 데이터를 이용하여 생산, 소비 데이터 채우기
	 * 
	 * @param hstime
	 * @throws Exception
	 */
	void makeVupToAmt(long hstime) throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));
		PsKind kind = PsApi.getApi().getPsKind(PsKind.PSKIND_15M);

		Map<String, Object> para = new HashMap<String, Object>();
		para.put("psDate", kind.getHstimeStart(hstime));
		try {
			tran.start();
			tran.execute(QID.insert_cons_amt_from_vup, para);
			tran.execute(QID.insert_prod_amt_from_vup, para);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			tran.stop();
		}

	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	/*
	private void analyze(String engId, VUP_PS_ITEM type, List<PsValues> list, Map<String, VUP_ENG_CONS_RAW> map,
			Map<String, VUP_ENG_PROD_RAW> map2, Map<Long, SOURCE_SINK> sinkMoMap, boolean onlyCons) {

		VUP_ENG_CONS_RAW cons;
		VUP_ENG_PROD_RAW prod;
		PsValue prev, cur;
		String key;

		for (PsValues mo : list) {

			// 데이터가 2개 이상인 경우만 처리한다.
			if (mo.getValues().size() < 2) {
				continue;
			}

			if (sinkMoMap.get(mo.getMoNo()) == SOURCE_SINK.source && onlyCons == false) {

				// 생산
				prev = mo.getValues().get(0);
				cur = mo.getValues().get(mo.getValues().size() - 1);

				key = cur.getPsDtm() + ":" + engId + ":" + mo.getMo().getInloNo();

				prod = map2.get(key);
				if (prod == null) {
					prod = new VUP_ENG_PROD_RAW();
					prod.setProdDtm(cur.getPsDtm());
					prod.setEngId(engId);
					prod.setInloNo(mo.getMo().getInloNo());
					prod.setMemo("CRON");
					map2.put(key, prod);
				}

				if (type.getPsType() == PsType.accumulate) {
					prod.setPrevIntgVal(prev.getValue().doubleValue());
					prod.setCurIntgVal(cur.getValue().doubleValue());
					prod.setDiffVal(cur.getValue().doubleValue() - prev.getValue().doubleValue());
				} else if (type.getPsType() == PsType.instance) {
					prod.setInstVal(round(cur.getValue().doubleValue()));
				} else if (type.getPsType() == PsType.pressure) {
					prod.setPresVal(round(cur.getValue().doubleValue()));
				} else if (type.getPsType() == PsType.temperature) {
					prod.setTempVal(round(cur.getValue().doubleValue()));
				}

			} else {

				// 소비
				prev = mo.getValues().get(0);
				cur = mo.getValues().get(mo.getValues().size() - 1);
				key = cur.getPsDtm() + ":" + engId + ":" + mo.getMo().getInloNo();
				cons = map.get(key);
				if (cons == null) {
					cons = new VUP_ENG_CONS_RAW();
					cons.setConsDtm(cur.getPsDtm());
					cons.setEngId(engId);
					cons.setInloNo(mo.getMo().getInloNo());
					cons.setMemo("CRON");
					map.put(key, cons);
				}

				if (type.getPsType() == PsType.accumulate) {
					cons.setPrevIntgVal(prev.getValue().doubleValue());
					cons.setCurIntgVal(cur.getValue().doubleValue());
					cons.setDiffVal(cur.getValue().doubleValue() - prev.getValue().doubleValue());
				} else if (type.getPsType() == PsType.instance) {
					cons.setInstVal(round(cur.getValue().doubleValue()));
				} else if (type.getPsType() == PsType.pressure) {
					cons.setPresVal(round(cur.getValue().doubleValue()));
				} else if (type.getPsType() == PsType.temperature) {
					cons.setTempVal(round(cur.getValue().doubleValue()));
				}
			}

//			System.out.println(key + "," + item.getPsId() + "\t" + mo.getMo().getMoName() + "\t" + FxmsUtil.toJson(prev)
//					+ " > " + FxmsUtil.toJson(cur));
		}
	}

	
	private int makeVupRaws(PsKind psKind, long mstime, Map<Long, SOURCE_SINK> sinkMoMap) throws Exception {

		// ValueService service = VupApi.getApi().getValueService();

		long hstime = DateUtil.toHstime(mstime);
		long startTime = psKind.getHstimeStart(hstime);
		long endTime = psKind.getHstimeEnd(psKind.getHstimeNext(hstime, 1));
		Map<String, VUP_ENG_CONS_RAW> map = new HashMap<String, VUP_ENG_CONS_RAW>();
		Map<String, VUP_ENG_PROD_RAW> map2 = new HashMap<String, VUP_ENG_PROD_RAW>();
		List<PsValues> list;
		String engId;
		PsItem psItem;

		engId = VupApi.getApi().getEngId(VupApi.ENG_ID.E01.name());
		for (VUP_PS_ITEM psId : new VUP_PS_ITEM[] { VUP_PS_ITEM.E01V3, VUP_PS_ITEM.E01V4, VUP_PS_ITEM.E01V5,
				VUP_PS_ITEM.V04G3V1 }) {
			psItem = PsApi.getApi().getPsItem(psId.name());
			list = ValueApi.getApi().getValues(psItem.getPsId(), psKind.getPsKindName(), psItem.getDefKindCol(),
					startTime, endTime);
			analyze(engId, psId, list, map, map2, sinkMoMap, false);
		}

		engId = VupApi.getApi().getEngId(VupApi.ENG_ID.E02.name());
		for (VUP_PS_ITEM psId : new VUP_PS_ITEM[] { VUP_PS_ITEM.E02V3, VUP_PS_ITEM.E02V4, VUP_PS_ITEM.E02V5,
				VUP_PS_ITEM.V04G3V2 }) {
			psItem = PsApi.getApi().getPsItem(psId.name());
			list = ValueApi.getApi().getValues(psItem.getPsId(), psKind.getPsKindName(), psItem.getDefKindCol(),
					startTime, endTime);
			analyze(engId, psId, list, map, map2, sinkMoMap, false);
		}

		engId = "EPWR";
		for (VUP_PS_ITEM psId : new VUP_PS_ITEM[] { VUP_PS_ITEM.ePower, VUP_PS_ITEM.ePowerAccum }) {
			psItem = PsApi.getApi().getPsItem(psId.name());
			list = ValueApi.getApi().getValues(psItem.getPsId(), psKind.getPsKindName(), psItem.getDefKindCol(),
					startTime, endTime);
			analyze(engId, psId, list, map, map2, sinkMoMap, true);
		}

		save(map, map2);

		return map.size();

	}

	private double round(double val) {
		long v = (long) (val * 100L);
		return v / 100d;
	}

	
	private void save(Map<String, VUP_ENG_CONS_RAW> map, Map<String, VUP_ENG_PROD_RAW> map2) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();
			for (VUP_ENG_CONS_RAW raw : map.values()) {
				FxTableMaker.initRegChg(0, raw);
				if (tran.selectOne(VUP_ENG_CONS_RAW.class, FxApi.makePara("consDtm", raw.getConsDtm(), "inloNo",
						raw.getInloNo(), "engId", raw.getEngId())) == null) {
					tran.insertOfClass(VUP_ENG_CONS_RAW.class, raw);
				} else {
					tran.updateOfClass(VUP_ENG_CONS_RAW.class, raw);
				}
			}
			for (VUP_ENG_PROD_RAW raw : map2.values()) {
				FxTableMaker.initRegChg(0, raw);
				if (tran.selectOne(VUP_ENG_PROD_RAW.class, FxApi.makePara("prodDtm", raw.getProdDtm(), "inloNo",
						raw.getInloNo(), "engId", raw.getEngId())) == null) {
					tran.insertOfClass(VUP_ENG_PROD_RAW.class, raw);
				} else {
					tran.updateOfClass(VUP_ENG_PROD_RAW.class, raw);
				}
			}

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

	@SuppressWarnings("unchecked")
	private Map<Long, SOURCE_SINK> selectSensorSourceSink() throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));
		List<Map<String, Object>> list = null;
		Map<Long, SOURCE_SINK> ret = new HashMap<>();
		try {
			tran.start();
			list = tran.selectQid2Res(QID.select_sensor_source_sink, null);
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

		for (Map<String, Object> map : list) {
			String pipeClCd = map.get("PIPE_CL_CD").toString();
			Long moNo = ((Number) map.get("MO_NO")).longValue();
			ret.put(moNo, "sink".equalsIgnoreCase(pipeClCd) ? SOURCE_SINK.sink : SOURCE_SINK.source);
		}

		return ret;
	}
*/
}
