package fxms.ems.cems.dfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import fxms.api.fo.AppApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.bas.mo.SensorMo;
import fxms.ems.cems.dto.TsdbDto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.database.DBManager;
import subkjh.dao.database.DataBase;

public class SelectTsdbDataDfo implements FxDfo<TsdbDto, List<PsVoRawList>> {

	public List<PsVoRawList> call(FxFact arg0, TsdbDto data) throws Exception {

		List<PsItem> psItems = AppApi.getApi().getPsItemList(data.psTable);
		if (psItems == null || psItems.size() == 0)
			return new ArrayList<>();

		Map<Long, PsVoRawList> values = new HashMap<>();

		for (String measurement : data.measurements) {
			try {
				String sql = makeSqlSelect(measurement, psItems, data.psRange.startDtm, data.psRange.endDtm);
				selectTsdbDatas(measurement, psItems, sql, values, data.sensorMap);
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}

		return new ArrayList<>(values.values());
	}

	private void selectTsdbDatas(String measurement, List<PsItem> psItems, String sql, Map<Long, PsVoRawList> values,
			Map<String, SensorMo> sensorMap) throws Exception {

		final DataBase database = DBManager.getMgr().getDataBase("FXMSTSDB");
		final InfluxDB influxDB = InfluxDBFactory.connect(database.getUrl(), database.getUser(),
				database.getPassword());
		influxDB.setDatabase(database.getDbName());

		Logger.logger.debug("{}\n{}", measurement, sql);

		long ptime = System.currentTimeMillis();
		long time;
		PsVoRawList raw;
		String moTid;
		SensorMo mo;

		try {

			Query query = new Query(sql);
			QueryResult queryResult = influxDB.query(query, TimeUnit.MILLISECONDS);

			if (queryResult.getError() != null) {
				Logger.logger.debug("{}", queryResult.getError());
			}

			for (Result r : queryResult.getResults()) {
				if (r.getSeries() != null) {
					for (Series s : r.getSeries()) {

						Logger.logger.debug("{} : {} row(s) fetched - {}ms", measurement, s.getValues().size(),
								(System.currentTimeMillis() - ptime));

						for (List<Object> list : s.getValues()) {
							time = ((Number) list.get(0)).longValue();
							raw = values.get(time);
							if (raw == null) {
								raw = new PsVoRawList("OPCUA", time);
								values.put(raw.getMstime(), raw);
							}

							// siheung_gas -> siheung
							moTid = measurement.split("_")[0] + "." + list.get(1).toString();
							mo = sensorMap.get(moTid);

							if (mo != null) {

								// 이전 데이터보다 시간이 이후인 경우만 취한다.
//								if (CemsApi.getApi().isGtTime(mo.getMoNo(), time)) {

								for (int i = 2; i < list.size(); i++) {
									raw.add(mo.getMoNo(), psItems.get(i - 2).getPsId(), (Number) list.get(i));
								}

//								}
							}
						}
					}
				}
			}

			Logger.logger.debug("{} : {} row(s) fetched - {}ms", measurement, values.size(),
					(System.currentTimeMillis() - ptime));

		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			influxDB.close();
		}

	}

	private String makeSqlSelect(String measurement, List<PsItem> psItems, long startDtm, long endDtm) {

		long mstimeStart = DateUtil.toMstime(startDtm);
		long mstimeEnd = DateUtil.toMstime(endDtm);

		String startTime = String.valueOf(mstimeStart) + "000000"; // microseconds
		String endTime = String.valueOf(mstimeEnd) + "000000";

		Logger.logger.debug(">= {} and < {}", DateUtil.toHstime(mstimeStart), DateUtil.toHstime(mstimeEnd));
		Logger.logger.debug(">= {} and < {}", mstimeStart, mstimeEnd);

		StringBuffer sql = new StringBuffer();

		sql.append("select GateWay.Device ");
		for (PsItem psItem : psItems) {
			sql.append(", ").append(psItem.getPsId());
		}
		sql.append("\n from ").append(measurement).append("\n");
		sql.append("\n where time >= ").append(startTime);
		sql.append("\n and time < ").append(endTime);
//		sql.append("and \"GateWay.Device\" = '172_29_11_100.DX01001'");

//		sql.append("group by time(").append(interval).append("), moNo fill(null)");

		return sql.toString();

	}

}
