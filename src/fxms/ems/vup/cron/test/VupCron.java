package fxms.ems.vup.cron.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.ems.bas.dbo.FE_ENG_CONS_AMT;
import fxms.ems.bas.dbo.FE_ENG_PROD_AMT;
import fxms.ems.vup.dao.VupQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * 수집된 에너지를 에너지소비량테이블로 이관하는 클래스
 * 
 * 
 * @author subkjh
 * @deprecated 
 */

@FxAdapterInfo(service = "VupService", descr = "에너지 생산 소비 가상으로 만들기")
public class VupCron extends Crontab {

	@FxAttr(name = "schedule", description = "실행계획", value = "0,15,30,45 * * * *")
	private String schedule;

	private final VupQid QID = new VupQid();
	private final long MIN15 = 15 * 60 * 1000L;

	public static void main(String[] args) {
		VupCron cron = new VupCron();

		try {
			cron.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() throws Exception {

		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "TIME");
		varInfo.put("varDispName", "VUP샘플최근생성일시");
		varInfo.put("varDesc", "VUP샘플데이터 최근 생성일시를 나타낸다.");
		VarApi.getApi().updateVarInfo("vup-sample-energy-data-made-time", varInfo);

		// 마지막 생성 일시
		long hstime = VarApi.getApi().getVarValue("vup-sample-energy-data-made-time", 20230301000000L);
		long mstime = DateUtil.toMstime(hstime);
		mstime = (mstime / MIN15);
		mstime *= MIN15;
		mstime += MIN15; // 마지막 다음 기준으로 생성함

		// 어제에서 오늘까지 만듬
		for (long m = mstime; m <= System.currentTimeMillis(); m += MIN15) {
			long dtm = DateUtil.getDtm(m);
			String date = DateUtil.getYmd(m) + "";
			String dateHh = date + DateUtil.getHH(m);

			Logger.logger.info("dtm={}, dateHh={}, date={}", dtm, dateHh, date);

			try {
				save(FE_ENG_PROD_AMT.class, makeProdDataM15(dtm, 1000));
				save(FE_ENG_CONS_AMT.class, makeConsDataM15(dtm, 1000));

//				makeAmtH1(dateHh);
//				makeStat(date);

			} catch (Exception e) {
				Logger.logger.error(e);
			}

			VarApi.getApi().setVarValue("vup-sample-energy-data-made-time", dtm, false);

		}

	}

	@SuppressWarnings("unchecked")
	private List<FE_ENG_CONS_AMT> makeConsDataM15(long dtm, int exp) throws FxTimeoutException, Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		List<FE_ENG_CONS_AMT> consList = new ArrayList<FE_ENG_CONS_AMT>();

		try {
			tran.start();

			FE_ENG_CONS_AMT cons;
			List<Map<String, Object>> list = tran.selectQid2Res(QID.select_plant_energy, null);
			for (Map<String, Object> obj : list) {

				cons = new FE_ENG_CONS_AMT();
				cons.setConsDtm(dtm);
				cons.setDtmType("M15");
				cons.setEngId(obj.get("ENG_ID").toString());
				cons.setFacNo(((Number) obj.get("INLO_NO")).longValue());
				cons.setInloNo(((Number) obj.get("INLO_NO")).intValue());
				cons.setExpConsAmt((double) exp);
				cons.setConsAmt((double) (Math.random() * exp));
				cons.setChgDtm(DateUtil.getDtm());
				cons.setChgUserNo(0);
				cons.setRegDtm(DateUtil.getDtm());
				cons.setRegUserNo(0);

				consList.add(cons);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

		return consList;
	}

	@SuppressWarnings("unchecked")
	private List<FE_ENG_PROD_AMT> makeProdDataM15(long dtm, int exp) throws FxTimeoutException, Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		List<FE_ENG_PROD_AMT> prodList = new ArrayList<FE_ENG_PROD_AMT>();

		try {
			tran.start();

			FE_ENG_PROD_AMT prod;

			// 공장, 에너지만 조회
			List<Map<String, Object>> list = tran.selectQid2Res(QID.select_plant_energy, null);

			for (Map<String, Object> obj : list) {

				// 각 공장, 에너지에 대한 생산량 임의로 생성
				prod = new FE_ENG_PROD_AMT();
				prod.setProdDtm(dtm);
				prod.setDtmType("M15");
				prod.setEngId(obj.get("ENG_ID").toString());
				prod.setFacNo(((Number) obj.get("INLO_NO")).longValue());
				prod.setInloNo(((Number) obj.get("INLO_NO")).intValue());
				prod.setExpProdAmt((double) exp);
				prod.setProdAmt((double) (Math.random() * exp));
				prod.setChgDtm(DateUtil.getDtm());
				prod.setChgUserNo(0);
				prod.setRegDtm(DateUtil.getDtm());
				prod.setRegUserNo(0);

				prodList.add(prod);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

		return prodList;
	}

	private void save(Class<?> classOf, List<?> list) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();

			tran.insertOfClass(classOf, list);

			tran.commit();

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	private void makeAmtH1(String dateHh) throws FxTimeoutException, Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		try {
			tran.start();

			tran.execute(QID.make_cons_h1_from_m15, dateHh);
			tran.execute(QID.make_prod_h1_from_m15, dateHh);

			tran.commit();

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	private void makeStat(String date) throws FxTimeoutException, Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		try {
			tran.start();

			tran.execute(QID.make_cons_stat_from_h1, date);
			tran.execute(QID.make_prod_stat_from_h1, date);

			tran.commit();

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

}
