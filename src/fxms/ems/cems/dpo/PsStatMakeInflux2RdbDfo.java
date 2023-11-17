package fxms.ems.cems.dpo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
//import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import fxms.api.FxApi;
import fxms.api.fo.AppApi;
import fxms.api.fo.dfo.ps.PsDpo;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.api.vo.ValueApi;
import fxms.api.vo.ValueApiDfo;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dbo.all.FX_PS_STAT_CRE;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.mo.SensorMo;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.database.DBManager;
import subkjh.dao.database.DataBase;

/**
 * InfluxDb를 이용하여 통계를 생성한다.
 * 
 * @author subkjh
 *
 */
public class PsStatMakeInflux2RdbDfo extends PsDpo implements FxDfo<FX_PS_STAT_CRE, Integer> {

	class Data {
		String sql;
		int colSize;

		Data(String sql, int colSize) {
			this.sql = sql;
			this.colSize = colSize;
		}
	}

	public static void main(String[] args) throws Exception {
		ValueApi.api = new ValueApiDfo();
		MoApi.api = new MoApiDfo();
		PsStatMakeInflux2RdbDfo dfo = new PsStatMakeInflux2RdbDfo();
		dfo.generateStatistics("FX_V_EPWR", PsKind.PSKIND_15M, 20230906141500L);
	}

	private final int IDX_MO = 0;
	private final int IDX_PSDATE = 1;
	private final int IDX_DATA_COUNT = 2;
	private final int IDX_INSDATE = 3;
	private final String TAG = "GateWay.Device";
	private final String MEASUREMENTS[] = new String[] { "busan", "ulsan", "gunsan" };

	@Override
	public Integer call(FxFact fact, FX_PS_STAT_CRE data) throws Exception {
		return generateStatistics(data);
	}

	public int generateStatistics(FX_PS_STAT_CRE req) throws Exception {
		return generateStatistics(req.getPsTbl(), req.getPsDataCd(), req.getPsDtm());
	}

	public int generateStatistics(String psTbl, String psKindName, long psDtm) throws Exception {

		String selectSql;
		String delSql;
		Data insertSql;
		PsKind psKindDst;
		List<PsItem> itemList = AppApi.getApi().getPsItemList(psTbl);
		int count = 0;

		psKindDst = AppApi.getApi().getPsKind(psKindName); // 대상
		insertSql = getSqlInsert(psTbl, psKindDst, psDtm); // insert문
		delSql = getSqlDelete(psTbl, psKindDst, psDtm); // delete문

		ClassDaoEx.open(FxCfg.DB_PSVALUE).executeSql(delSql).close();

		for (String measurement : MEASUREMENTS) {

			selectSql = getSqlSelect(itemList, measurement, TAG, psKindDst, psDtm); // influxDB select문

			Collection<Object[]> datas = getDatas(selectSql, insertSql.colSize, DateUtil.getDtm(), measurement);

			ClassDaoEx dao = ClassDaoEx.open(FxCfg.DB_PSVALUE).executeSql(insertSql.sql, datas).close();

			count += dao.getProcessedCount();
		}

		return count;

	}

	private List<Object[]> getDatas(String sql, int size, long insDate, String measurement) throws Exception {

		Map<String, SensorMo> sensors = getSensorMap();

		final List<Object[]> ret = new ArrayList<>();
		Object[] row;
		final DataBase database = DBManager.getMgr().getDataBase("FXMSTSDB");
		final InfluxDB influxDB = InfluxDBFactory.connect(database.getUrl(), database.getUser(),
				database.getPassword());
		influxDB.setDatabase(database.getDbName());

		Logger.logger.debug("\n{}", sql);

		try {
			Query query = new Query(sql);
			QueryResult queryResult = influxDB.query(query, TimeUnit.MILLISECONDS);

			Logger.logger.info("{}", queryResult.getError());

			String moTid;
			SensorMo sensor;

			for (Result r : queryResult.getResults()) {
				if (r.getSeries() != null) {
					for (Series s : r.getSeries()) {
						moTid = measurement + "." + s.getTags().get(TAG);
						sensor = sensors.get(moTid);
						if (sensor != null) {
							row = new Object[size];
							row[IDX_MO] = sensor.getMoNo(); // 관리대상
							row[IDX_INSDATE] = insDate; // 등록일시
							for (List<Object> list : s.getValues()) {
								if (list != null) {
									row[IDX_PSDATE] = DateUtil.toHstime(((Number) list.get(0)).longValue()); // 시간
									row[IDX_DATA_COUNT] = ((Number) list.get(1)).intValue(); // 데이터건수
									for (int i = 2; i < list.size(); i++) {
										row[IDX_INSDATE + (i - 1)] = list.get(i); // 통계값
									}
								}
							}
							ret.add(row);
						}
					}
				}
			}

			Logger.logger.info("datas = {}", ret.size());

			return ret;
		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			influxDB.close();
		}

	}

	private Map<String, SensorMo> getSensorMap() throws Exception {

		List<SensorMo> sensorList = MoApi.getApi().getMos(FxApi.makePara("moClass", "SENSOR"), SensorMo.class);

		Map<String, SensorMo> sensorMap = new HashMap<>();
		for (SensorMo node : sensorList) {
			sensorMap.put(node.getMoTid(), node);
		}
		return sensorMap;
	}

	private String getSqlDelete(String psTable, PsKind psKind, long psDate) {

		StringBuffer sql = new StringBuffer();

		sql.append("delete from ");
		sql.append(psKind.getTableName(psTable, psDate));
		sql.append(" where " + PsDpo.PS_DATE.getName() + "=" + psKind.getHstimeStart(psDate));

		return sql.toString();
	}

	@SuppressWarnings("unused")
	private Data getSqlInsert(String psTable, PsKind psKindDst, long psDtm) throws Exception {

		int colSize = 4;
		List<PsItem> itemList = AppApi.getApi().getPsItemList(psTable);
		StringBuffer dest = new StringBuffer();
		String destTable = psKindDst.getTableName(psTable, psDtm);

		dest.append("insert into ").append(destTable).append(" ( ").append("\n");
		dest.append(PsDpo.MO_NO.getName()).append("\n");
		dest.append(", ").append(PsDpo.PS_DATE.getName()).append("\n");
		dest.append(", ").append(PsDpo.DATA_COUNT.getName()).append("\n");
		dest.append(", ").append(PsDpo.INS_DATE.getName()).append("\n");
		for (PsItem item : itemList) {
			for (String func : item.getStatFuncIds()) {
				String colName = item.getPsColumn() + "_" + func;
				dest.append(", ").append(colName).append("\n");
			}
		}
		dest.append(") values ( ?, ?, ?, ?");
		for (PsItem item : itemList) {
			for (String func : item.getStatFuncIds()) {
				dest.append(", ?");
				colSize++;
			}
		}
		dest.append(")");

		return new Data(dest.toString(), colSize);

	}

	private String getSqlSelect(List<PsItem> itemList, String psTable, String tag, PsKind psKindDst, long psDtm) {

		String colName;
		long mstimeStart = DateUtil.toMstime(psKindDst.getHstimeStart(psDtm));
		long mstimeEnd = DateUtil.toMstime(psKindDst.getHstimeEnd(psDtm)) + 1000;

		String startTime = String.valueOf(mstimeStart) + "000000"; // microseconds
		String endTime = String.valueOf(mstimeEnd) + "000000";
		String interval = psKindDst.getInterval();

		Logger.logger.debug(">= {} and < {}", DateUtil.toHstime(mstimeStart), DateUtil.toHstime(mstimeEnd));
		Logger.logger.debug(">= {} and < {}", mstimeStart, mstimeEnd);

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("\n");
		sql.append("count(").append(itemList.get(0).getPsColumn()).append(") as ").append("DATA_COUNT").append("\n");

		// 각 성능항목에 대한 통계 함수를 이용한 컬럼을 추가한다.
		for (PsItem item : itemList) {

			for (String func : item.getStatFuncIds()) {
				String newFunc = subkjh.dao.database.InfluxDB.toFunction(func);
				colName = item.getPsColumn() + "_" + func;

				sql.append(", ").append(newFunc).append("(").append(item.getPsColumn()).append(") as \"")
						.append(colName).append("\"\n");
			}
		}
		sql.append("from ").append(psTable).append("\n");
		sql.append("WHERE time >= ").append(startTime).append(" and time < ").append(endTime).append("\n");
		sql.append("GROUP BY time(").append(interval).append("), \"").append(tag).append("\" FILL(null)");

		return sql.toString();

	}

	void print(Collection<Object[]> datas) {
		for (Object[] o : datas) {
			for (Object e : o) {
				System.out.print(e + "\t");
			}
			System.out.println();
		}
	}
}
