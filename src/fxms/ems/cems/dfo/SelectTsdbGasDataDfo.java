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

import fxms.api.FxApi;
import fxms.api.fo.AppApi;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.bas.exp.NotDefineException;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.bas.mo.SensorMo;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.database.DBManager;
import subkjh.dao.database.DataBase;

/**
 * @deprecated
 * @author subkjh
 *
 */
public class SelectTsdbGasDataDfo implements FxDfo<Void, List<PsVoRawList>> {

	public static void main(String[] args) {
		MoApi.api = new MoApiDfo();

		SelectTsdbGasDataDfo dfo = new SelectTsdbGasDataDfo();
		try {
			dfo.initDatas();
			List<PsVoRawList> list = dfo.call(null, null);
			for (PsVoRawList raw : list) {
				System.out.println(FxmsUtil.toJson(raw));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<String, SensorMo> sensorMap = new HashMap<>();
	private List<PsItem> psItems;
	private List<String> measurements = new ArrayList<>();

	@Override
	public List<PsVoRawList> call(FxFact arg0, Void arg1) throws Exception {

		initDatas();

		long mstime = System.currentTimeMillis() - 3600000L;
		long startDtm = FemsApi.kind1M.getHstimeStart(DateUtil.getDtm(mstime));
		long endDtm = FemsApi.kind1M.getHstimeNext(startDtm, 1);

		return selectData(startDtm, endDtm);
	}

	public void initDatas() throws Exception {
		
		String val = FxCfg.getCfg().getString("tsdb.measurements", null);
		if (val == null) {
			throw new NotDefineException("tsdb.measurements");
		} else {
			String ss[] = val.split(",");
			for (String s : ss) {
				measurements.add(s.trim());
			}
		}
		

		this.psItems = AppApi.getApi().getPsItemList("FX_V_EPWR");

		List<SensorMo> sensorList = MoApi.getApi().getMos(FxApi.makePara("moClass", "SENSOR"), SensorMo.class);
		for (SensorMo node : sensorList) {
			this.sensorMap.put(node.getMoTid(), node);
		}

		Logger.logger.debug("psitem={}, sensor={}", this.psItems.size(), this.sensorMap.size());

	}

	public List<PsVoRawList> selectData(long startDtm, long endDtm) throws Exception {

		if (sensorMap.size() == 0) {
			initDatas();
		}

		Map<Long, PsVoRawList> values = new HashMap<>();

		for (String measurement : measurements) {

			try {
				String sql = getSqlSelect(measurement, startDtm, endDtm);
				selectTsdbDatas(measurement, sql, values);
			} catch (Exception e) {
				Logger.logger.error(e);
			}

		}

		return new ArrayList<>(values.values());
	}

	private void selectTsdbDatas(String measurement, String sql, Map<Long, PsVoRawList> values) throws Exception {

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

							moTid = measurement + "." + list.get(1).toString();
							mo = this.sensorMap.get(moTid);

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

	private String getSqlSelect(String measurement, long startDtm, long endDtm) {

		long mstimeStart = DateUtil.toMstime(startDtm);
		long mstimeEnd = DateUtil.toMstime(endDtm);

		String startTime = String.valueOf(mstimeStart) + "000000"; // microseconds
		String endTime = String.valueOf(mstimeEnd) + "000000";

		Logger.logger.debug(">= {} and < {}", DateUtil.toHstime(mstimeStart), DateUtil.toHstime(mstimeEnd));
		Logger.logger.debug(">= {} and < {}", mstimeStart, mstimeEnd);

		StringBuffer sql = new StringBuffer();

		sql.append("select GateWay.Device ");
		for (PsItem psItem : this.psItems) {
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
