package fxms.ems.vup.handler;

import fxms.bas.fxo.FxCfg;
import fxms.ems.bas.dbo.FE_ENG_CONS_STAT;
import fxms.ems.bas.dbo.FE_ENG_PROD_STAT;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dao.VupHandlerQid;
import fxms.ems.vup.dto.Tran04Dto;
import fxms.ems.vup.dto.Tran05Dto;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

public class VupDao {

	private final VupHandlerQid QID = new VupHandlerQid();

	protected QidDao getTran() throws Exception {
		return DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupHandlerQid.QUERY_XML_FILE));
	}

	public Boolean updateTran04(Tran04Dto dto) throws Exception {

		QidDao tran = getTran();

		try {
			tran.start();

			FE_ENG_PROD_STAT data;
			data = new FE_ENG_PROD_STAT();
			data.setEngId(VupApi.getApi().getEngId(dto.energy_code));
			data.setExpProdAmt((double) dto.expAmt);
			try {
				data.setFacNo(VupApi.getApi().getFacNo(dto.device_pid));
			} catch (Exception e2) {
				data.setFacNo(-1L);
			}
			data.setInloNo(VupApi.getApi().getPlant(dto.factory_pid).getInloNo());
			data.setProdDate(DateUtil.checkDate(dto.date));

			tran.execute(QID.INSERT_FE_ENG_PROD_STAT, data);

			tran.commit();

			return true;

		} catch (Exception ex) {
			Logger.logger.error(ex);
			tran.rollback();
			throw ex;
		} finally {
			tran.stop();
		}
	}

	public Boolean updateTran05(Tran05Dto dto) throws Exception {
		QidDao tran = getTran();

		try {
			tran.start();

			FE_ENG_CONS_STAT data;
			data = new FE_ENG_CONS_STAT();
			data.setEngId(VupApi.getApi().getEngId(dto.energy_code));
			data.setExpConsAmt((double) dto.expAmt);
			try {
				data.setFacNo(VupApi.getApi().getFacNo(dto.device_pid));
			} catch (Exception e2) {
				data.setFacNo(-1L);
			}
			data.setInloNo(VupApi.getApi().getPlant(dto.factory_pid).getInloNo());
			data.setConsDate(DateUtil.checkDate(dto.date));

			tran.execute(QID.INSERT_FE_ENG_CONS_STAT, data);

			tran.commit();

			return true;

		} catch (Exception ex) {
			Logger.logger.error(ex);
			tran.rollback();
			throw ex;
		} finally {
			tran.stop();
		}
	}
}
