package fxms.ems.bas.dpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dao.EmsQid;
import fxms.ems.bas.vo.PlantVo;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * 산단, 회사, 공장 정보를 모두 갖는 설치위치 정보 조회
 * 
 * @author subkjh
 *
 */
public class PlantSelectDfo implements FxDfo<Map<String, Object>, List<PlantVo>> {

	public static void main(String[] args) {

		PlantSelectDfo dfo = new PlantSelectDfo();
		try {
//			Map<String, FactoryVo> map = dfo.selectFactoryMap(FxApi.makePara("inloClCd", "COMPLEX"));
			Map<String, PlantVo> map = dfo.selectFactoryMap(null);
			System.out.println(FxmsUtil.toJson(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PlantVo> call(FxFact fact, Map<String, Object> data) throws Exception {
		return selectFactory(data);
	}

	@SuppressWarnings("unchecked")
	public List<PlantVo> selectFactory(Map<String, Object> para) throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(EmsQid.QUERY_XML_FILE));

		EmsQid qid = new EmsQid();

		try {
			tran.start();

			List<PlantVo> ret = new ArrayList<>();
			List<Map<String, Object>> list = tran.selectQid2Res(qid.select_factory_list, para);
			PlantVo data;
			for (Map<String, Object> map : list) {
				try {
					data = new PlantVo(map);
					ret.add(data);
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			}
			return ret;

		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, PlantVo> selectFactoryMap(Map<String, Object> para) throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(EmsQid.QUERY_XML_FILE));

		EmsQid qid = new EmsQid();

		try {
			tran.start();

			Map<String, PlantVo> ret = new HashMap<>();
			List<Map<String, Object>> list = tran.selectQid2Res(qid.select_factory_list, para);
			PlantVo data;
			for (Map<String, Object> map : list) {
				try {
					data = new PlantVo(map);
					if (data.getInloTid() != null) {
						ret.put(data.getInloTid(), data);
					}
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			}
			return ret;

		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

}