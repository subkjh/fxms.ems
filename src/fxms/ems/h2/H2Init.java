package fxms.ems.h2;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.VarApi;
import fxms.bas.cron.AlarmStatDailyCron;
import fxms.bas.cron.AlarmStatHourlyCron;
import fxms.bas.cron.MoSyncCron;
import fxms.bas.fxo.adapter.AlarmAfterLogAdapter;
import fxms.bas.fxo.adapter.AlarmAfterMailAdapter;
import fxms.bas.fxo.cron.CheckACron;
import fxms.bas.impl.adapter.SmsBizppurioAlarmAfterAdapter;
import fxms.bas.impl.api.AdapterApiDfo;
import fxms.bas.impl.cron.AlarmReleaseCron;
import fxms.bas.impl.cron.PsStatMakeCron;
import fxms.bas.signal.ReloadSignal.ReloadType;
import fxms.ems.bas.adapter.TestAdapter;
import fxms.ems.bas.cron.MakeEnergyConsProdAmtCron;
import fxms.ems.bas.cron.MakeEnergyRawCron;
import fxms.ems.h2.adapter.H2TestAdapter;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.FileUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.def.DaoListener;
import subkjh.dao.util.SqlTool;

public class H2Init {

	public static void main(String[] args) {
		H2Init h2 = new H2Init();

		try {
			h2.initAdapter();
//			h2.backup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final String FILENAME = "datas/h21.dat";

	public void backup() throws Exception {

		FileUtil.writeToFile(FILENAME, "", false);

		String tables[] = new String[] { "FX_PS_STAT_KIND" };

		QidDao dao = DBManager.getMgr().getDataBase("VUPDEVDB").createQidDao();

		try {

			dao.start();

			for (String table : tables) {

				DaoListener listener = new DaoListener() {

					@Override
					public void onExecuted(Object arg0, Exception arg1) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void onFinish(Exception arg0) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSelected(int no, Object datas) throws Exception {

						Object rows[] = (Object[]) datas;

						StringBuffer sb = new StringBuffer();
						for (Object col : rows) {
							if (sb.length() > 0) {
								sb.append("\t");
							}
							sb.append(col);
						}
						sb.append("\n");

						FileUtil.writeToFile(FILENAME, sb.toString(), true);

					}

					@Override
					public void onStart(String[] columns) throws Exception {
						StringBuffer sb = new StringBuffer();
						for (String col : columns) {
							if (sb.length() > 0) {
								sb.append("\t");
							}
							sb.append(col);
						}
						sb.append("\n");

						FileUtil.writeToFile(FILENAME, sb.toString(), true);
					}

				};

				StringBuffer sb = new StringBuffer();
				sb.append("\n");
				sb.append("Table Name").append("\t").append(table).append("\n");
				FileUtil.writeToFile(FILENAME, sb.toString(), true);

				int cnt = dao.selectSql("select * from " + table, null, listener);
				System.out.println(table + "=" + cnt);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dao.stop();
		}

	}

	public void initAdapter() {

		AdapterApiDfo api = new AdapterApiDfo();

		try {

			api.insert(TestAdapter.class);
			api.insert(H2TestAdapter.class);
			api.insert(MakeEnergyRawCron.class);
			api.insert(MakeEnergyConsProdAmtCron.class);
			
			api.insert(CheckACron.class);
			api.insert(MoSyncCron.class);
			api.insert(AlarmStatDailyCron.class);
			api.insert(AlarmStatHourlyCron.class);

			api.insert(SmsBizppurioAlarmAfterAdapter.class);
			api.insert(AlarmAfterLogAdapter.class);
			api.insert(AlarmAfterMailAdapter.class);

			api.insert(AlarmReleaseCron.class);
			api.insert(PsStatMakeCron.class);

//			VarApi.getApi().setTimeUpdated(ReloadType.Adapter, DateUtil.getDtm());
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {

			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "TIME");
			varInfo.put("varDispName", "Adapter 적용 최종시간");
			varInfo.put("varDesc", "아답터 데이터가 최종 업데이트 된 시간을 나타낸다.");
			String varName = VarApi.UPDATED_TIME_VAR + ReloadType.Adapter;
			VarApi.getApi().updateVarInfo(varName, varInfo);

			VarApi.getApi().setTimeUpdated(ReloadType.Adapter, DateUtil.getDtm());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void makeSource() {
		SqlTool tool = new SqlTool();
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/ems.xml", "fxms.ems.bas.dao.EmsQid", "src/fxms/ems/bas/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/h2/h2.xml", "fxms.ems.h2.dao.H2Qid", "src/fxms/ems/h2/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/vup.xml", "fxms.ems.vup.dao.VupQid", "src/fxms/ems/vup/dao");
//		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/vup_ui.xml", "fxms.ems.vup.dao.VupUiQid", "src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/vup_test.xml", "fxms.ems.vup.dao.VupTestQid",
				"src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/enitt_loc.xml", "fxms.ems.vup.dao.EnittLocQid",
				"src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/gemvax.xml", "fxms.ems.vup.dao.GemvaxQid",
				"src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/Tisp2VupCron.xml", "fxms.ems.vup.dao.Tisp2VupCronQid",
				"src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/VupHandler.xml", "fxms.ems.vup.dao.VupHandlerQid",
				"src/fxms/ems/vup/dao");
	}

}
