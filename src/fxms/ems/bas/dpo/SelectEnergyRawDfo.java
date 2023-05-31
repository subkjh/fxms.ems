package fxms.ems.bas.dpo;

import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_MEASR_RAW;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

public class SelectEnergyRawDfo implements FxDfo<Map<String, Object>, List<FE_ENG_MEASR_RAW>> {

	public static void main(String[] args) {

		SelectEnergyRawDfo dfo = new SelectEnergyRawDfo();
		try {
			System.out.println(FxmsUtil.toJson(dfo.selectEnergyRaw(FxApi.makePara("measrDtm >=", 20230427000000L))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<FE_ENG_MEASR_RAW> call(FxFact fact, Map<String, Object> data) throws Exception {
		return selectEnergyRaw(data);
	}

	public List<FE_ENG_MEASR_RAW> selectEnergyRaw(Map<String, Object> para) throws Exception {
		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();

			List<FE_ENG_MEASR_RAW> list = tran.select(FE_ENG_MEASR_RAW.class, para);

			return list;

		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

}