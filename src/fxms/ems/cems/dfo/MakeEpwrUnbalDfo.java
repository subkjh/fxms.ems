package fxms.ems.cems.dfo;

import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.cems.dao.CemsVUnbalQid;
import fxms.ems.kepco.KepcoUtil;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.LOG_LEVEL;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDaoEx;

/**
 * 월간 부하별 전기 요금
 * 
 * @author subkjh
 *
 */
public class MakeEpwrUnbalDfo implements FxDfo<Long, Integer> {

	public static void main(String[] args) {

		Logger.logger.setLevel(LOG_LEVEL.info);
		MakeEpwrUnbalDfo dfo = new MakeEpwrUnbalDfo();
		try {
			PsKind pskind = FemsApi.kind15M;
			long mstime = DateUtil.toMstime("20231109200000");
			for (long ms = mstime; ms < System.currentTimeMillis(); ms = pskind.getMstimeNext(ms, 1)) {
				System.out.println(DateUtil.toHstime(ms) + " = " + dfo.make(DateUtil.toHstime(ms)));
			}
			
//			System.out.println(dfo.make(20231101000000L));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Long psDate) throws Exception {
		return make(psDate);
	}

	@SuppressWarnings("unchecked")
	public int make(long psDate) throws Exception {

		long ptime = System.currentTimeMillis();

		CemsVUnbalQid QID = new CemsVUnbalQid();
		Map<String, Object> para = FxApi.makePara("psDate", psDate);
		QidDaoEx dao = QidDaoEx.open(BasCfg.getHome(CemsVUnbalQid.QUERY_XML_FILE));

		List<Map<String, Object>> datas = dao.selectQid2Res(QID.select_CEMS_V_UNBAL, para);
		if (datas == null || datas.size() == 0)
			return 0;

		float values[];

		for (Map<String, Object> map : datas) {
			values = new float[] { ((Number) map.get("iaVal")).floatValue() //
					, ((Number) map.get("ibVal")).floatValue() //
					, ((Number) map.get("icVal")).floatValue() };

			map.put("iunbalVal", KepcoUtil.getUnbalanceRate(values));

			values = new float[] { ((Number) map.get("vaVal")).floatValue() //
					, ((Number) map.get("vbVal")).floatValue() //
					, ((Number) map.get("vcVal")).floatValue() };

			map.put("vunbalVal", KepcoUtil.getUnbalanceRate(values));

		}

		dao.execute(QID.make_CEMS_V_UNBAL, datas);
		int ret = dao.getProcessedCount();

		dao.close();

		Logger.logger.info("EpwrUnbal : {} data(s) executed - {}ms", ret, (System.currentTimeMillis() - ptime));

		return ret;

	}

}
