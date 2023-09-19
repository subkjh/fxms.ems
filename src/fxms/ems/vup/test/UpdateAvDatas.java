package fxms.ems.vup.test;

import java.util.List;
import java.util.Map;

import fxms.bas.fxo.FxCfg;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

public class UpdateAvDatas {

	public static void main(String[] args) {
		UpdateAvDatas c = new UpdateAvDatas();
		try {
			c.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 음수로 된 값 변경
	 */
	private void update() throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		long moNo = 1000561L;
		long preValue = 0, preDate = 0;
		long date, value;
		String sql;
		int count;

		try {

			// 2. 사용을 시작하겠다는 의미
			tran.start();


			while (true) {

				List<Object[]> datas = tran.selectSql("select * from FX_V_EPWR_RAW a where a.MO_NO = 1000767 and  order by a.PS_DATE desc"
						, null);

				count = 0;
				for (Object[] data : datas) {

					date = ((Number) data[0]).longValue();
					value = ((Number) data[1]).longValue();

					if (preValue > value) {
						System.out.println(preDate + "=" + preValue + " >> " + date + "=" + value);
//						tran.executeSql("update FX_V_E01_15M set v5_max = " + preValue + " where mo_no = " + moNo
//								+ " and ps_date = " + date);
//						count++;
					} else {
						preValue = value;
						preDate = date;
					}

				}

				if (count == 0)
					break;
			}

		} catch (Exception e) {

			// 오류 발생시 로그 기록
			Logger.logger.error(e);

			// 롤백
			tran.rollback();

			// Exception 보내기
			throw e;
		} finally {

			// 사용을 종료한다.
			tran.stop();
		}

	}
}
