package fxms.ems.vup.dpo;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_RT_BAS;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

/**
 * 에너지 거래 정보를 가져온다.
 * 
 * @author subkjh
 *
 */
public class EngRtSelectDfo implements FxDfo<String, FE_ENG_RT_BAS> {

	public EngRtSelectDfo() {

	}

	@Override
	public FE_ENG_RT_BAS call(FxFact fact, String engRtId) throws Exception {
		int complexInloNo = fact.getInt("complexInloNo");

		return select(engRtId, complexInloNo);
	}

	public FE_ENG_RT_BAS select(String engRtId, int complexInloNo) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();
			return tran.selectOne(FE_ENG_RT_BAS.class, FxApi.makePara("engRtId", engRtId, "inloNo", complexInloNo));
		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}

	}
}
