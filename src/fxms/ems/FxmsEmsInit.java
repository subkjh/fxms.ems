package fxms.ems;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.VarApi;
import fxms.bas.impl.api.AdapterApiDfo;
import fxms.bas.signal.ReloadSignal.ReloadType;
import fxms.ems.bas.adapter.TestAdapter;
import fxms.ems.bas.cron.CalcTrnsChrgCron;
import fxms.ems.bas.cron.MakeEnergyRawCron;
import fxms.ems.vup.adapter.EPowerStatAdapter;
import fxms.ems.vup.adapter.VupAnsanAdapter;
import fxms.ems.vup.cron.AnsanEPowerCron;
import fxms.ems.vup.cron.MakeVupEnergyRawCron;
import fxms.ems.vup.cron.test.GemvaxDbCron;
import fxms.ems.vup.cron.test.MakeExpAmtCron;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.util.SqlTool;

public class FxmsEmsInit {

	public static void main(String[] args) {
		FxmsEmsInit init = new FxmsEmsInit();
//		init.initAdapter();
		init.makeSource();
	}

	public void initAdapter() {

		AdapterApiDfo api = new AdapterApiDfo();

		try {

			api.insert(TestAdapter.class);
			api.insert(VupAnsanAdapter.class);
			api.insert(EPowerStatAdapter.class);

			api.insert(MakeExpAmtCron.class);

			api.insert(MakeEnergyRawCron.class);
			api.insert(MakeVupEnergyRawCron.class);
			api.insert(AnsanEPowerCron.class);
			api.insert(CalcTrnsChrgCron.class);

			api.insert(GemvaxDbCron.class);

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
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/vup_test.xml", "fxms.ems.vup.dao.VupTestQid", "src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/enitt_loc.xml", "fxms.ems.vup.dao.EnittLocQid", "src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/gemvax.xml", "fxms.ems.vup.dao.GemvaxQid", "src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/Tisp2VupCron.xml", "fxms.ems.vup.dao.Tisp2VupCronQid", "src/fxms/ems/vup/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/VupHandler.xml", "fxms.ems.vup.dao.VupHandlerQid", "src/fxms/ems/vup/dao");
	}
}
