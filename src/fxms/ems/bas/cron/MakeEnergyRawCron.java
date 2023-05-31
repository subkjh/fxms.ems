package fxms.ems.bas.cron;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.MoApi;
import fxms.bas.api.PsApi;
import fxms.bas.api.ValueApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsKind;
import fxms.bas.vo.PsValue;
import fxms.bas.vo.PsValues;
import fxms.ems.bas.dbo.FE_ENG_MEASR_RAW;
import fxms.ems.bas.dpo.SelectEnergyPsIdDfo;
import fxms.ems.bas.vo.EngPsVo;
import fxms.ems.bas.vo.EngPsVo.PsType;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

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

	private final long MIN15 = 15 * 60000L;

	public MakeEnergyRawCron() {

	}

	private void analyze(EngPsVo engPs, List<PsValues> list, Map<String, FE_ENG_MEASR_RAW> map) {

		FE_ENG_MEASR_RAW raw;
		PsValue prev, cur;
		String key;

		for (PsValues mo : list) {

			// 데이터가 2개 이상인 경우만 처리한다.
			if (mo.getValues().size() < 2) {
				continue;
			}

			prev = mo.getValues().get(0);
			cur = mo.getValues().get(mo.getValues().size() - 1);
			key = cur.getPsDtm() + ":" + mo.getMo().getInloNo() + ":" + mo.getMoNo();
			raw = map.get(key);

			if (raw == null) {

				raw = new FE_ENG_MEASR_RAW();
				raw.setMeasrDtm(cur.getPsDtm());
				raw.setMoNo(mo.getMoNo());
				raw.setInloNo(mo.getMo().getInloNo());

				raw.setEngId(engPs.engId);
				raw.setMemo(mo.getMo().getMoName());
				map.put(key, raw);
			}

			if (engPs.psType == PsType.accumulate) {
				raw.setPrevIntgVal(prev.getValue().doubleValue());
				raw.setCurIntgVal(cur.getValue().doubleValue());
				raw.setDiffVal(cur.getValue().doubleValue() - prev.getValue().doubleValue());
			} else if (engPs.psType == PsType.instance) {
				raw.setInstVal(round(cur.getValue().doubleValue()));
			} else if (engPs.psType == PsType.pressure) {
				raw.setPresVal(round(cur.getValue().doubleValue()));
			} else if (engPs.psType == PsType.temperature) {
				raw.setTempVal(round(cur.getValue().doubleValue()));
			}

		}
	}

	/**
	 * 적산값을 이용하여 사용량 추출하기
	 * 
	 * @throws FxTimeoutException
	 * @throws Exception
	 */
	private int makeRaws(PsKind psKind, long mstime, List<EngPsVo> psList) throws Exception {

		// ValueService service = VupApi.getApi().getValueService();

		long hstime = DateUtil.toHstime(mstime);
		long startTime = psKind.getHstimeStart(hstime);
		long endTime = psKind.getHstimeEnd(psKind.getHstimeNext(hstime, 1));
		Map<String, FE_ENG_MEASR_RAW> map = new HashMap<String, FE_ENG_MEASR_RAW>();
		List<PsValues> list;
		PsItem psItem;

		for (EngPsVo ps : psList) {
			try {
				psItem = PsApi.getApi().getPsItem(ps.psId);
				list = ValueApi.getApi().getValues(psItem.getPsId(), psKind.getPsKindName(), psItem.getDefKindCol(),
						startTime, endTime);
				analyze(ps, list, map);

			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}

		save(map);

		return map.size();

	}

	private double round(double val) {
		long v = (long) (val * 100L);
		return v / 100d;
	}

	/**
	 * VUP 원천 데이터 기록하기
	 * 
	 * @param map
	 * @param map2
	 * @throws Exception
	 */
	private void save(Map<String, FE_ENG_MEASR_RAW> map) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();

			FE_ENG_MEASR_RAW old;

			for (FE_ENG_MEASR_RAW raw : map.values()) {

				old = tran.selectOne(FE_ENG_MEASR_RAW.class, FxApi.makePara("measrDtm", raw.getMeasrDtm(), "inloNo",
						raw.getInloNo(), "moNo", raw.getMoNo()));

				FxTableMaker.initRegChg(0, raw);

				if (old == null) {
					tran.insertOfClass(FE_ENG_MEASR_RAW.class, raw);
				} else {
					tran.updateOfClass(FE_ENG_MEASR_RAW.class, raw);
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

	@Override
	protected String getSchedule() {
		return schedule;
	}

	@Override
	public void start() throws Exception {
		try {

			String varName = "fems-energy-made-time";
			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "TIME");
			varInfo.put("varDispName", "에너지최근생성일시");
			varInfo.put("varDesc", "에너지 최근 생성일시를 나타낸다.");
			VarApi.getApi().updateVarInfo(varName, varInfo);

			// 최종 처리일시 가져옴.
			long dtm = VarApi.getApi().getVarValue(varName, 20230404000000L);

			PsKind psKind = PsApi.getApi().getPsKind("MIN15");
			long mstime = psKind.toMstime(psKind.getHstimeStart(dtm));

			List<EngPsVo> psList = new SelectEnergyPsIdDfo().selectEnergyPsId();

			// 최종 처리일시 이후부터 현재에서 5분전까지 처리함.
			for (long ms = mstime; ms < System.currentTimeMillis(); ms += MIN15) {

				// 15분 데이터 생성
				makeRaws(psKind, ms, psList);

				// 처리 내역 기록
				// 다음에 시작할 때 이전 내용을 다시해도 상관 없고 혹시 데이터가 늦게 들어올 것을 감안하여 1시간으로 돌려놓는다.
				VarApi.getApi().setVarValue(varName, DateUtil.toHstime(ms - MIN15 * 4), false);

			}

		} catch (Exception e) {
			Logger.logger.error(e);
		}
	}

	@Override
	public String getGroup() {
		return "FeMS";
	}
}
