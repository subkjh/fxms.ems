package fxms.ems.vup.dpo;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_RT_BAS;
import fxms.ems.bas.dbo.FE_ENG_TRANS_BAS;
import fxms.ems.bas.dbo.FE_ENG_TRANS_RT;
import fxms.ems.bas.vo.PlantVo;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dto.Tran01Dto;
import fxms.ems.vup.dto.Tran06Dto;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

/**
 * 
 * @author subkjh
 *
 */
public class MakeEnergyTransactionDfo implements FxDfo<Void, Boolean> {

	public MakeEnergyTransactionDfo() {

	}

	@Override
	public Boolean call(FxFact fact, Void data) throws Exception {
		Tran01Dto bas = fact.getObject(Tran01Dto.class, "bas");
		Tran06Dto rt = fact.getObject(Tran06Dto.class, "route");
		return setEnergyTransaction(bas, rt);
	}

	/**
	 * 에너지 거래 정보 설정
	 * 
	 * @param basDto 거래정보
	 * @param rtDto  에너지경로
	 * @return
	 * @throws Exception
	 */
	public Boolean setEnergyTransaction(Tran01Dto basDto, Tran06Dto rtDto) throws Exception {
		return this.setEnergyTransaction(null, basDto, rtDto);
	}

	public Boolean setEnergyTransaction(FE_ENG_TRANS_BAS bas, FE_ENG_TRANS_RT rt) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		FE_ENG_TRANS_BAS old;

		try {
			tran.start();

			old = tran.selectOne(FE_ENG_TRANS_BAS.class, FxApi.makePara("engId", bas.getEngId(), "inloNo",
					bas.getInloNo(), "sellInloNo", bas.getSellInloNo(), "buyInloNo", bas.getBuyInloNo()));

			FxTableMaker.initRegChg(0, bas);
			if (old != null) {
				bas.setTrnsNo(old.getTrnsNo());
				tran.updateOfClass(FE_ENG_TRANS_BAS.class, bas);
			} else {
				bas.setTrnsNo(tran.getNextVal(FE_ENG_TRANS_BAS.FX_SEQ_TRNSNO, Long.class));
				tran.insertOfClass(FE_ENG_TRANS_BAS.class, bas);
			}

			// 에너지 경로는 변경된 부분이 누적되어야 한다.
			if (rt != null) {
				rt.setTrnsNo(bas.getTrnsNo());
				FxTableMaker.initRegChg(0, rt);
				tran.insertOfClass(FE_ENG_TRANS_RT.class, rt);
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

	/**
	 * 
	 * @param trnsNo
	 * @param basDto
	 * @return
	 * @throws Exception
	 */
	public Boolean setEnergyTransaction(long trnsNo, Tran01Dto basDto) throws Exception {
		return setEnergyTransaction(trnsNo, basDto, null);
	}

	/**
	 * 
	 * @param trnsNo
	 * @param basDto
	 * @param rtDto
	 * @return
	 * @throws Exception
	 */
	public Boolean setEnergyTransaction(Long trnsNo, Tran01Dto basDto, Tran06Dto rtDto) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		FE_ENG_TRANS_BAS old;
		FE_ENG_TRANS_BAS bas = ObjectUtil.toObject(basDto, FE_ENG_TRANS_BAS.class);

		PlantVo source = VupApi.getApi().getPlant(basDto.getSourcePlantPid());
		if (source != null) {
			bas.setSellInloNo(source.getInloNo());
			bas.setInloNo(source.getComplexInloNo());
		}
		bas.setEngId(VupApi.getApi().getEngId(basDto.getEngTid()));
		bas.setBuyInloNo(VupApi.getApi().getPlant(basDto.getSinkPlantPid()).getInloNo());

		if (bas.getTrnsFnshDtm() == null || bas.getTrnsFnshDtm() <= 0) {
			bas.setTrnsStCd("1"); // 계약유지
		} else if (bas.getTrnsFnshDtm() != null && bas.getTrnsFnshDtm() < DateUtil.getDtm()) {
			bas.setTrnsStCd("2"); // 계약해지
		}

		try {
			tran.start();

			if (trnsNo != null) {
				old = tran.selectOne(FE_ENG_TRANS_BAS.class, FxApi.makePara("trnsNo", trnsNo));
			} else {
				old = tran.selectOne(FE_ENG_TRANS_BAS.class, FxApi.makePara("engId", bas.getEngId(), "inloNo",
						bas.getInloNo(), "sellInloNo", bas.getSellInloNo(), "buyInloNo", bas.getBuyInloNo()));
			}

			FxTableMaker.initRegChg(0, bas);
			if (old != null) {
				bas.setTrnsNo(old.getTrnsNo());
				tran.updateOfClass(FE_ENG_TRANS_BAS.class, bas);
			} else {
				bas.setTrnsNo(tran.getNextVal(FE_ENG_TRANS_BAS.FX_SEQ_TRNSNO, Long.class));
				tran.insertOfClass(FE_ENG_TRANS_BAS.class, bas);
			}

			// 에너지 경로는 변경된 부분이 누적되어야 한다.
			FE_ENG_TRANS_RT rt = null;
			if (rtDto != null) {
				rt = ObjectUtil.toObject(rtDto, FE_ENG_TRANS_RT.class);
			} else {
				FE_ENG_RT_BAS rtBas = tran.selectOne(FE_ENG_RT_BAS.class, FxApi.makePara("engId", bas.getEngId(),
						"strtInloNo", bas.getSellInloNo(), "fnshInloNo", bas.getBuyInloNo()));
				if (rtBas != null) {
					rt = new FE_ENG_TRANS_RT();
					rt.setEngRtId(rtBas.getEngRtId());
					rt.setEngRtUseYn("Y");
					rt.setRtMemo("존재하는 경로 중 하나 넣음");
				}
			}
			if (rt != null) {
				rt.setTrnsNo(bas.getTrnsNo());
				rt.setRtStrtDtm(DateUtil.getDtm());
				rt.setRtFnshDtm(99991231000000L);
				FxTableMaker.initRegChg(0, rt);
				tran.insertOfClass(FE_ENG_TRANS_RT.class, rt);
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
