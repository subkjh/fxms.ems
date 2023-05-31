package fxms.ems.vup.dpo;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_TRANS_RT;
import fxms.ems.vup.dto.Tran06Dto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

/**
 * 에너지 거래 정보를 가져온다.
 * 
 * @author subkjh
 *
 */
public class EngTransRtInsertDfo implements FxDfo<Tran06Dto, Boolean> {

	public EngTransRtInsertDfo() {

	}

	@Override
	public Boolean call(FxFact fact, Tran06Dto data) throws Exception {
		return insert(data);
	}

	public boolean insert(Tran06Dto data) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		FE_ENG_TRANS_RT dbo = ObjectUtil.toObject(data, FE_ENG_TRANS_RT.class);
		FxTableMaker.initRegChg(0, dbo);
		dbo.setRtStrtDtm(DateUtil.getDtm());

		try {
			tran.start();

			FE_ENG_TRANS_RT old = tran.selectOne(FE_ENG_TRANS_RT.class,
					FxApi.makePara("trnsNo", data.getTrnsNo(), "engRtUseYn", "Y"));
			if (old != null && old.getEngRtId().equals(data.getEngRtId())) {
				throw new Exception("동일한 에너지 라우팅을 사용중입니다.");
			}

			// 기존은 사용하지 않음으로 변경
			tran.updateOfClass(FE_ENG_TRANS_RT.class, FxApi.makePara("trnsNo", data.getTrnsNo(), "engRtUseYn", "N"));

			// 새로운 라우팅 추가
			tran.insertOfClass(FE_ENG_TRANS_RT.class, dbo);

			tran.commit();
			return true;

		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}

	}
}
