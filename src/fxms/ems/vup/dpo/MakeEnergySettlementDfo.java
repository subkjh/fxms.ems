package fxms.ems.vup.dpo;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_TRANS_CALC;
import fxms.ems.vup.dbo.all.VUP_ENG_TRANS_CALC;
import fxms.ems.vup.dto.FeEngTransCalcDto;
import fxms.ems.vup.dto.VupEngTransCalcDto;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

/**
 * 
 * @author subkjh
 *
 */
public class MakeEnergySettlementDfo implements FxDfo<VupEngTransCalcDto, Boolean> {

	public MakeEnergySettlementDfo() {

	}

	@Override
	public Boolean call(FxFact fact, VupEngTransCalcDto data) throws Exception {
		return setEnergySettlement(data);
	}

	public Boolean setEnergySettlementByTrnsNo(long trnsNo, FeEngTransCalcDto data) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		FE_ENG_TRANS_CALC old;
		FE_ENG_TRANS_CALC bas = ObjectUtil.toObject(data, FE_ENG_TRANS_CALC.class);

		try {
			tran.start();

			old = tran.selectOne(FE_ENG_TRANS_CALC.class, FxApi.makePara("trnsNo", trnsNo));

			FxTableMaker.initRegChg(0, bas);
			if (old != null) {
				tran.updateOfClass(FE_ENG_TRANS_CALC.class, bas);
			} else {
				tran.insertOfClass(FE_ENG_TRANS_CALC.class, bas);
			}

			tran.commit();

			return true;

		} catch (Exception e) {

			tran.rollback();
			throw e;

		} finally {
			tran.stop();
		}

	}

	public Boolean setEnergySettlement(VupEngTransCalcDto data) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		VUP_ENG_TRANS_CALC old;
		VUP_ENG_TRANS_CALC bas = ObjectUtil.toObject(data, VUP_ENG_TRANS_CALC.class);

		try {
			tran.start();

			old = tran.selectOne(VUP_ENG_TRANS_CALC.class, FxApi.makePara("plantPid", data.getPlantPid(), "engTid",
					data.getEngTid(), "calcYm", data.getCalcYm(), "sinkSource", data.getSinkSource()));

			FxTableMaker.initRegChg(0, bas);
			if (old != null) {
				tran.updateOfClass(VUP_ENG_TRANS_CALC.class, bas);
			} else {
				tran.insertOfClass(VUP_ENG_TRANS_CALC.class, bas);
			}

			tran.commit();

			return true;

		} catch (Exception e) {

			tran.rollback();
			throw e;

		} finally {
			tran.stop();
		}

	}

}
