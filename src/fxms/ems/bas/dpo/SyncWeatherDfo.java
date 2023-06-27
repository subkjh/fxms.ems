package fxms.ems.bas.dpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_CF_INLO;
import fxms.bas.impl.dbo.all.FX_CO_WEATHER;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.user.User;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.net.url.UrlClientGet;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.util.FxTableMaker;

/**
 * 주소를 동기화 한다.
 * 
 * @author subkjh
 *
 */
public class SyncWeatherDfo implements FxDfo<Void, Integer> {

	public static void main(String[] args) {

		SyncWeatherDfo dfo = new SyncWeatherDfo();
		try {
			System.out.println(dfo.syncWeather("20230620"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Void data) throws Exception {
		return syncWeather("20");
	}

	/**
	 * 에너지 정보 확인 성능항목 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public int syncWeather(String yyyymmdd) throws Exception {

		int count = 0;
		String site = FxCfg.getCfg().getString("tisp.data.url", null);

		StringBuffer url = new StringBuffer();
		url.append(site).append("/api/tisp/02").append("?tm=").append(yyyymmdd);

		List<FX_CF_INLO> list = ClassDaoEx.SelectDatas(FX_CF_INLO.class,
				FxApi.makePara("DEL_YN", "N", "AREA_NUM is not", null));

		for (FX_CF_INLO inlo : list) {
			List<Map<String, Object>> datas = getData(url.toString() + "&areaNum=" + inlo.getAreaNum());
			if (datas != null) {
				save(datas);
				count++;
			}
		}

		return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String, Object>> getData(String url) throws Exception {

		UrlClientGet client = new UrlClientGet();
		String body = client.get(url.toString());
		
		Logger.logger.debug("{}\n{}", url, body);

		Map<String, Object> map = FxmsUtil.toMapFromJson(body);
		Object obj = map.get("data");
		if (obj instanceof List) {
			return (List) obj;
		} else {
			return null;
		}
	}

	private int save(List<Map<String, Object>> datas) throws Exception {

		ClassDaoEx dao = ClassDaoEx.open();
		Map<String, Object> para = new HashMap<>();

		for (Map<String, Object> data : datas) {

			// 시간
			FxTableMaker.initRegChgMap(User.USER_NO_SYSTEM, data);
			data.put("fcstDataYn", "Y");

			// 조회조건
			para.put("areaNum", data.get("areaNum"));
			para.put("ymdHh", data.get("ymdHh"));
			para.put("fcstDataYn", "Y");

			dao.setOfClass(FX_CO_WEATHER.class, para, data);
		}

		dao.close();

		return datas.size();
	}

}