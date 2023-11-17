package test;

import java.util.List;

import fxms.bas.fxo.cron.CheckACron;
import fxms.ems.bas.dpo.SelectAreaNumDfo;
import fxms.ems.bas.dpo.SyncWeatherDfo;
import subkjh.bas.co.log.LOG_LEVEL;
import subkjh.bas.co.log.Logger;
import subkjh.dao.database.DBManager;
import subkjh.dao.def.Table;
import subkjh.dao.util.SqlUtil;

public class TestCron {

	public static void main(String[] args) throws Exception {
		Logger.logger.setLevel(LOG_LEVEL.trace);

		TestCron test = new TestCron();

//		test.testCheckA();
		test.testWeather();
	}

	void testCheckA() throws Exception {
		CheckACron c = new CheckACron();
		c.analysis();
	}

	void testQid() throws Exception {
		SqlUtil util = new SqlUtil();
		for (Table table : util.getTableAll(DBManager.getMgr().getDataBase("FXMSDB"))) {
			System.out.println(table.getName());
		}
	}

	void testWeather() throws Exception {

		List<String> areaNums = new SelectAreaNumDfo().getAreaNums();
		SyncWeatherDfo dfo = new SyncWeatherDfo();
		try {
			System.out.println(dfo.syncWeather(areaNums, "20230920"));
//				System.out.println(FxmsUtil.toJson(dfo.getDongs()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
