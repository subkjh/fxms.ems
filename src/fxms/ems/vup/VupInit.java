package fxms.ems.vup;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.VarApi;
import fxms.bas.impl.api.AdapterApiDfo;
import fxms.bas.signal.ReloadSignal.ReloadType;
import fxms.ems.vup.cron.AnsanEPowerCron;
import fxms.ems.vup.cron.MakeVupEnergyRawCron;
import fxms.ems.vup.cron.Tisp2VupCron;
import fxms.ems.vup.cron.test.EPowerCron;
import fxms.ems.vup.cron.test.EnittLocAlarmCron;
import fxms.ems.vup.cron.test.EnittLocConfCron;
import fxms.ems.vup.cron.test.EnittLocValueCron;
import fxms.ems.vup.cron.test.IaeDbCron;
import fxms.ems.vup.cron.test.MakeSampleExpAmtCron;
import fxms.ems.vup.gemvax.GemvaxDbCron;
import fxms.ems.vup.gemvax.GemvaxTradingSyncCron;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.util.SqlTool;

public class VupInit {

	public static void main(String[] args) {
		VupInit vup = new VupInit();

		try {
			vup.makeSource();
//			vup.initAdapter();
//			h2.backup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initAdapter() {

		AdapterApiDfo api = new AdapterApiDfo();

		try {

			// 실제 사용
			api.insert(MakeVupEnergyRawCron.class);
			api.insert(GemvaxTradingSyncCron.class);
			api.insert(EPowerCron.class);
			api.insert(IaeDbCron.class);

			// 아래는 테스트용
			api.insert(GemvaxDbCron.class);
			api.insert(Tisp2VupCron.class);
			api.insert(MakeSampleExpAmtCron.class);
			api.insert(AnsanEPowerCron.class);
			api.insert(EnittLocAlarmCron.class);
			api.insert(EnittLocConfCron.class);
			api.insert(EnittLocValueCron.class);

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

	public void makeSource() {
		SqlTool tool = new SqlTool();
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/h2/h2.xml", "fxms.ems.h2.dao.H2Qid", "src/fxms/ems/h2/dao");
		tool.makeXml2JavaQid("deploy/conf/sql/fxms/ems/vup/gemvax.xml", "fxms.ems.vup.dao.GemvaxQid",
				"src/fxms/ems/vup/dao");
	}

}
