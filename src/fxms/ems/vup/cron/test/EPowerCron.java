package fxms.ems.vup.cron.test;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.PsApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsKind;
import fxms.ems.vup.dao.VupQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.exp.TableNotFoundException;

@FxAdapterInfo(service = "VupService", descr = "전력적산량을 이용하여 사용량을 계산한다.")
public class EPowerCron extends Crontab {



	@FxAttr(name = "schedule", description = "실행계획", value = "2,17,32,47 * * * *")
	private String schedule;

	private final VupQid QID = new VupQid();

	public static void main(String[] args) {
		EPowerCron cron = new EPowerCron();
		try {
			cron.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final long MIN15 = 15 * 60000L;

	@Override
	public void start() throws Exception {
		try {

			// 최종 처리일시 가져옴.
			long dtm = VarApi.getApi().getVarValue("vup-epower-made-time", 20221231235500L);
			PsKind psKind = PsApi.getApi().getPsKind("MIN15");
			long mstime = psKind.toMstime(psKind.getHstimeStart(dtm));

			// 최종 처리일시 이후부터 현재에서 5분전까지 처리함.
			for (long ms = mstime; ms < System.currentTimeMillis(); ms += MIN15) {

				// 5분 데이터 생성
				makeEPowerCons(ms);

				// 1시간 데이터 처리
				// 병합하므로 계속 처리해도 된다.
				makeHour(mstime);

				// 처리 내역 기록
				// 다음에 시작할 때 현재, 다음꺼를 실행하기 위해 5분을 뺌
				VarApi.getApi().setVarValue("vup-epower-made-time", DateUtil.toHstime(ms - MIN15), false);
			}

		} catch (Exception e) {
			Logger.logger.error(e);
		}
	}

	/**
	 * 전력소비량 등록
	 * 
	 * @throws FxTimeoutException
	 * @throws Exception
	 */
	private int makeEPowerCons(long mstime) throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		try {
			tran.start();

			PsItem item = PsApi.getApi().getPsItem("ePowerAmt");
			PsKind kind = PsApi.getApi().getPsKind("MIN15");
			if (item != null && kind != null && item.existKindCol("SUM")) {
				long hstime = kind.toHstime(mstime);

				Map<String, Object> para = new HashMap<String, Object>();
				para.put("TABLE", kind.getTableName(item, hstime));
				para.put("COLUMN", item.makeColumn(kind, "SUM").getName());
				para.put("psDateStart", kind.getHstimeStart(hstime));
				para.put("psDateEnd", kind.getHstimeEnd(hstime));
				para.put("engId", "EPWR");
				para.put("dtmType", "M15");

				int ret = tran.execute(QID.insert_epower_used, para);
				tran.commit();
				return ret;
			} else {
				return 0;
			}

		} catch (TableNotFoundException e) {
			// 테이블이 없으면 무시
			return 0;
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	private int makeHour(long mstime) throws FxTimeoutException, Exception {

		QidDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		try {
			tran.start();

			PsKind kind = PsApi.getApi().getPsKind("HOUR1");
			if (kind != null) {
				long hstime = kind.toHstime(mstime);

				Map<String, Object> para = new HashMap<String, Object>();
				para.put("psDateStart", kind.getHstimeStart(hstime));
				para.put("psDateEnd", kind.getHstimeEnd(hstime));
				para.put("engId", "EPWR");
				para.put("dtmType", "M15");

				int ret = tran.execute(QID.make_epower_cons_amt_h1_from_m15, para);
				tran.commit();
				return ret;
			} else {
				return 0;
			}

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
