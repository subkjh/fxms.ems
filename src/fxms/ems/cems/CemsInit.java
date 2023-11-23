package fxms.ems.cems;

import java.util.HashMap;
import java.util.Map;

import fxms.api.fo.AppApi;
import fxms.api.mo.dfo.inlo.InloMemSetDfo;
import fxms.bas.impl.cron.TestCron;
import fxms.bas.impl.dpo.InsertAdapterDfo;
import fxms.bas.signal.ReloadSignal.ReloadType;
import fxms.ems.cems.cron.CemsCheckSensorStateCron;
import fxms.ems.cems.cron.CemsGwNodeCheckCron;
import fxms.ems.cems.cron.CemsMake15MDatasCron;
import fxms.ems.cems.cron.CemsMake1DDatasCron;
import fxms.ems.cems.cron.CemsMake1HDatasCron;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.database.MySql;
import subkjh.dao.def.Column;
import subkjh.dao.util.SqlTool;

public class CemsInit {

	public static void main(String[] args) throws Exception {
		CemsInit init = new CemsInit();
//		init.initAdapter();
//		init.initVar();
		init.makeSource();
//		init.initInloMem();
//		init.test();
	}

	public void initAdapter() {

		InsertAdapterDfo api = new InsertAdapterDfo();

		try {

//			api.insert(TestAdapter.class);
//			api.insert(MakeEnergyRawCron.class);
//			api.insert(MakeEnergyAmtStatCron.class);
//			api.insert(MakeEnergyConsProdAmtCron.class);

//			api.insert(MakeEpwr1HChargeCron.class);
//			api.insert(MakeEpwrMonthlyChargeCron.class);
//			api.insert(PsTsdb2RdbRawCron.class);
			
//			api.insert(CemsMake15MDatasCron.class);
//			api.insert(CemsMake1HDatasCron.class);
//			api.insert(CemsMake1DDatasCron.class);
//			api.insert(TestCron.class);
//			api.insert(CemsGwNodeCheckCron.class);

			api.insert(CemsCheckSensorStateCron.class);

//			api.insert(SyncAddrCron.class);
//			api.insert(SyncWeatherCron.class);
//			api.insert(SyncDateCron.class);
//
//			api.insert(IqrCron.class);
//			api.insert(CheckACron.class);
//			api.insert(MoSyncCron.class);
//			api.insert(AlarmStatDailyCron.class);
//			api.insert(AlarmStatHourlyCron.class);
//
//			api.insert(SmsBizppurioAlarmAfterAdapter.class);
//			api.insert(AlarmAfterLogAdapter.class);
//			api.insert(AlarmAfterMailAdapter.class);
//
//			api.insert(AlarmReleaseCron.class);
//			api.insert(PsStatMakeCron.class);
//
//			api.insert(IqrCron.class);

//			AppApi.getApi().setVar(AppApi.UPDATED_TIME_VAR + ReloadType.Adapter, DateUtil.getDtm());
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {

			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "TIME");
			varInfo.put("varDispName", "Adapter 적용 최종시간");
			varInfo.put("varDesc", "아답터 데이터가 최종 업데이트 된 시간을 나타낸다.");
			String varName = AppApi.UPDATED_TIME_VAR + ReloadType.Adapter;
			AppApi.getApi().setVar(varName, varInfo);

			AppApi.getApi().setVar(varName, DateUtil.getDtm());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void makeSource() throws Exception {

		SqlTool tool = new SqlTool();

		Column.JAVA_FIELD_STYLE_OBJECT = true;

//		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/ems.xml", "fxms.ems.bas.dao.EmsQid", "src/fxms/ems/bas/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/cems.xml", "fxms.ems.cems.dao.CemsQid",
				"src/fxms/ems/cems/dao");
//		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/raw2fac.xml", "fxms.ems.cems.dao.Raw2FacQid",				"src/fxms/ems/cems/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/CemsVUnbal.xml", "fxms.ems.cems.dao.CemsVUnbalQid",
				"src/fxms/ems/cems/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/EpwrInloPrice.xml", "fxms.ems.cems.dao.EpwrInloPriceQid",
				"src/fxms/ems/cems/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/EpwrCharge.xml", "fxms.ems.cems.dao.EpwrChargeQid",
				"src/fxms/ems/cems/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/EpwrChargeTime.xml", "fxms.ems.cems.dao.EpwrChargeTimeQid",
				"src/fxms/ems/cems/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/EpwrChargeLoad.xml", "fxms.ems.cems.dao.EpwrChargeLoadQid",
				"src/fxms/ems/cems/dao");

		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/EngMeasrAmtInlo.xml",
				"fxms.ems.cems.dao.EngMeasrAmtInloQid", "src/fxms/ems/cems/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/cems/dfo/CheckSensorStateDfo.xml",
				"fxms.ems.cems.dao.dfo.CheckSensorStateDfoQid", "src/fxms/ems/cems/dao/dfo");

//		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/epwr.xml", "fxms.ems.cems.dao.EpwrQid", "src/fxms/ems/cems/dao");
		tool.printInsertSql(new MySql(), "datas/datas.txt");
//		tool.printCreateSql(new MySql(), "datas/tables.txt");
		tool.printAddColumnSql(new MySql(), "datas/tables.txt");

//		tool.makeInsertSampleSql(new MySql(), "datas/tables.txt");
//		tool.makeJpaSource(new File("datas/tables.txt"), "fxms.ems.cems.dbo", "tmp");
//		tool.makeJavaSource(new File("datas/tables.txt"), "fxms.ems.cems.dbo", "tmp");
	}

	public void initInloMem() throws Exception {
		new InloMemSetDfo().setInitMemAll();
	}

	public void initVar() throws Exception {
		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "URL");
		varInfo.put("varDispName", "FEMS 노드 정보 조회");
		varInfo.put("varDesc", "FEMS로부터 노드 정보를 조회하는 URL");
		varInfo.put("varVal", "https://tisp.integrict-cems.com/browseNode?ip=$ip&port=$port");
		// https://tisp.integrict-cems.com/browseNode?ip=172.29.29.100&port=4840
		AppApi.getApi().setVar(CemsApi.VAR_NAME_cems_url_node_browse, varInfo);
	}
}
