package fxms.ems.vup.dpo;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_TRANS_BAS;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

/**
 * 에너지 거래 정보를 가져온다.
 * 
 * @author subkjh
 *
 */
public class EngTransSelectDfo implements FxDfo<Long, FE_ENG_TRANS_BAS> {

	public EngTransSelectDfo() {

	}

	@Override
	public FE_ENG_TRANS_BAS call(FxFact fact, Long trnsNo) throws Exception {
		return select(trnsNo);
	}

	public FE_ENG_TRANS_BAS select(long trnsNo) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();
			return tran.selectOne(FE_ENG_TRANS_BAS.class, FxApi.makePara("trnsNo", trnsNo));
		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}

	}
}
