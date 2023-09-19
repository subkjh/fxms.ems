package fxms.ems;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.VarApi;
import fxms.bas.impl.api.AdapterApiDfo;
import fxms.bas.impl.dpo.ao.iqr.IqrCron;
import fxms.bas.signal.ReloadSignal.ReloadType;
import fxms.ems.bas.cron.MakeEnergyAmtStatCron;
import fxms.ems.bas.cron.MakeEnergyRawCron;
import fxms.ems.bas.cron.SyncAddrCron;
import fxms.ems.bas.cron.SyncDateCron;
import fxms.ems.bas.cron.SyncWeatherCron;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.util.SqlTool;

public class FxmsEmsInit {

	public static void main(String[] args) throws Exception {
		FxmsEmsInit init = new FxmsEmsInit();
//		init.initAdapter();
		init.makeSource();
	}

	public void initAdapter() {

		AdapterApiDfo api = new AdapterApiDfo();

		try {

//			api.insert(TestAdapter.class);
//			api.insert(VupAnsanAdapter.class);
//			api.insert(EPowerStatAdapter.class);
//
//			api.insert(MakeExpAmtCron.class);
//
			api.insert(MakeEnergyRawCron.class);
			api.insert(MakeEnergyAmtStatCron.class);
//			api.insert(AnsanEPowerCron.class);
//			api.insert(CalcTrnsChrgCron.class);
//
//			api.insert(GemvaxDbCron.class);

			api.insert(SyncAddrCron.class);
			api.insert(SyncWeatherCron.class);
			api.insert(SyncDateCron.class);
			api.insert(IqrCron.class);

			VarApi.getApi().setTimeUpdated(ReloadType.Adapter, DateUtil.getDtm());
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

	public void makeSource() throws Exception {
		SqlTool tool = new SqlTool();
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/ems.xml", "fxms.ems.bas.dao.EmsQid", "src/fxms/ems/bas/dao");
//		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/iae.xml", "fxms.ems.vup.dao.IaeQid", "src/fxms/ems/vup/dao");
//		tool.printCreateSql(new MySql(), "datas/tables.txt");
//		tool.printAddColumnSql(new MySql(), "datas/tables.txt");
//		tool.makeJavaSource(new File("datas/tables.txt"), "fxms.ems.bas.dbo", "tmp");
	}
}
