package fxms.ems.vup.dpo;

import java.util.List;

import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

/**
 * 애니트의 관제점 데이터를 확인한다.
 * 
 * @author subkjh
 *
 */
public class EnittSelectPointDfo implements FxDfo<Void, List<VUP_COMM_ENITT_POINT>> {

	public EnittSelectPointDfo() {
	}

	@Override
	public List<VUP_COMM_ENITT_POINT> call(FxFact fact, Void data) throws Exception {
		return select();
	}

	public List<VUP_COMM_ENITT_POINT> select() throws Exception {
		ClassDao tran = DBManager.getMgr().getDataBase("VUPCOMMDB").createClassDao();

		try {
			tran.start();
			return tran.select(VUP_COMM_ENITT_POINT.class, null);
		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}
}
