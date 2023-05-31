package fxms.ems.bas.dpo;

import java.util.List;

import fxms.bas.api.FxApi;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDpo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dao.EmsQid;
import fxms.ems.bas.dbo.FE_ENG_TRANS_CALC_DTL;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

/**
 * 에너지 사용량 금액 계산 프로세스
 * 
 * @author subkjh
 *
 */
public class CalculateDpo implements FxDpo<String, Boolean> {

	public static void main(String[] args) {
		CalculateDpo dfp = new CalculateDpo();
		try {
			String date = DateUtil.getYmdStr();
			dfp.make(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final EmsQid QID = new EmsQid();

	private void calculate(String date, List<FE_ENG_TRANS_CALC_DTL> list) {

		long calcDtm = Long.parseLong(date + "000000");
		String calcYm = date.substring(0, 6);

		for (FE_ENG_TRANS_CALC_DTL dtl : list) {
			dtl.setCalcDtm(calcDtm);
			dtl.setCalcYm(calcYm);

			// TODO
			dtl.setUnitPrice(1.5d);
			dtl.setTrnsVolChrg((long) (dtl.getTrnsVol() * dtl.getUnitPrice()));
		}
	}

	@SuppressWarnings("unchecked")
	private List<FE_ENG_TRANS_CALC_DTL> getTransVolume(String date) throws FxTimeoutException, Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(EmsQid.QUERY_XML_FILE));

		try {
			tran.start();
			List<FE_ENG_TRANS_CALC_DTL> list = tran.selectQid2Res(QID.select_calculate_amount,
					FxApi.makePara("measrDtmStart", date + "000000", "measrDtmEnd", date + "235959"));
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	private boolean save(List<FE_ENG_TRANS_CALC_DTL> list) throws FxTimeoutException, Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();

			FE_ENG_TRANS_CALC_DTL old;
			for (FE_ENG_TRANS_CALC_DTL dtl : list) {
				old = tran.selectOne(FE_ENG_TRANS_CALC_DTL.class, FxApi.makePara("trnsNo", dtl.getTrnsNo(), "calcYm",
						dtl.getCalcYm(), "calcDtm", dtl.getCalcDtm()));
				FxTableMaker.initRegChg(0, dtl);
				if (old == null) {
					tran.insertOfClass(FE_ENG_TRANS_CALC_DTL.class, dtl);
				} else {
					tran.updateOfClass(FE_ENG_TRANS_CALC_DTL.class, dtl);
				}
			}
			tran.commit();
			return true;
		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

	/**
	 * 에너지 사용량 금액 계산
	 * 
	 * @param date 일자
	 * @return
	 * @throws Exception
	 */
	public boolean make(String date) throws Exception {

		// 거래량 가져오기
		List<FE_ENG_TRANS_CALC_DTL> list = getTransVolume(date);

		// 계산하기
		calculate(date, list);

		// 기록하기
		return save(list);

	}

	@Override
	public Boolean run(FxFact fact, String date) throws Exception {
		return make(date);
	}
}
