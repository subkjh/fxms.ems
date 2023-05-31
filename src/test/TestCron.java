package test;

import fxms.bas.fxo.cron.CheckACron;
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
		test.testQid();
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
}
