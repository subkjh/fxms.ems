package fxms.ems.bas.dpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.AlarmApi;
import fxms.bas.api.AppApi;
import fxms.bas.api.FxApi;
import fxms.bas.api.MoApi;
import fxms.bas.api.PsApi;
import fxms.bas.api.ValueApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.api.AlarmApiDfo;
import fxms.bas.impl.api.AppApiDfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsKind;
import fxms.bas.vo.PsValue;
import fxms.bas.vo.PsValues;
import fxms.ems.bas.dbo.FE_ENG_MEASR_RAW;
import fxms.ems.bas.vo.EngPsVo;
import fxms.ems.bas.vo.EngPsVo.PsType;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

/**
 * 수집데이터를 이용하여 에너지 량을 계산한다.
 * 
 * @author subkjh
 *
 */
public class MakeEnergyRawDfo implements FxDfo<List<EngPsVo>, Integer> {

	public static void main(String[] args) throws Exception {
		
		AppApi.api = new AppApiDfo();
		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();
		AlarmApi.api = new AlarmApiDfo();

		List<EngPsVo> psList = new SelectEnergyPsIdDfo().selectEnergyPsId();

		MakeEnergyRawDfo dfo = new MakeEnergyRawDfo();
		try {
			dfo.makeEnergyRaws(PsApi.getApi().getPsKind(PsKind.PSKIND_15M), 20230616144500L, psList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, List<EngPsVo> psList) throws Exception {
		PsKind psKind = fact.getObject(PsKind.class);
		long psDtm = fact.getLong("psDtm");
		return makeEnergyRaws(psKind, psDtm, psList);
	}

	/**
	 * 
	 * @param psKind 데이터주기
	 * @param psDtm  일시
	 * @param psList 성능
	 * @return
	 * @throws Exception
	 */
	public int makeEnergyRaws(PsKind psKind, long psDtm, List<EngPsVo> psList) throws Exception {

		long hstime = psDtm;
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

		int size = save(map);

		Logger.logger.info("EnergyRawData : date={}, size={}", psDtm, size);

		return size;

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
			} else if (engPs.psType == PsType.used) {
				raw.setDiffVal(round(cur.getValue().doubleValue()));
			}

		}
	}

	private double round(double val) {
		long v = (long) (val * 100L);
		return v / 100d;
	}

	/**
	 * 2. 계측기 기준으로 수집한 데이터를 이용하여 소비 또는 생산량을 구한다.
	 * 
	 * @param map
	 * @param map2
	 * @throws Exception
	 */
	private int save(Map<String, FE_ENG_MEASR_RAW> map) throws Exception {

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

			return map.size();

		} catch (Exception e) {
			tran.rollback();
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}
}
