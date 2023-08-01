package fxms.ems.bas.dpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.VarApi;
import fxms.bas.exp.NotFoundException;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_CF_INLO;
import fxms.bas.impl.dbo.all.FX_CO_DONG;
import fxms.bas.impl.dbo.all.FX_CO_WEATHER;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import subkjh.bas.co.lang.Lang;
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

	private final String VAR_NAME = SyncDateDfo.VAR_NAME;

	public static void main(String[] args) {

		SyncWeatherDfo dfo = new SyncWeatherDfo();
		try {
			System.out.println(dfo.syncWeather("20230705"));
//			System.out.println(FxmsUtil.toJson(dfo.getDongs()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Void data) throws Exception {
		return syncWeather(DateUtil.getYmdStr());
	}

	/**
	 * 에너지 정보 확인 성능항목 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public int syncWeather(String yyyymmdd) throws Exception {

		int count = 0;
		String site = VarApi.getApi().getVarValue(VAR_NAME, null);
		if (site == null) {
			throw new NotFoundException("conf", VAR_NAME, Lang.get("Environment variable not set.", VAR_NAME));
		}

		StringBuffer url = new StringBuffer();
		url.append(site).append("/api/tisp/02").append("?tm=").append(yyyymmdd);

		List<FX_CO_DONG> list = getDongs();

		for (FX_CO_DONG dong : list) {
			List<Map<String, Object>> datas = getData(url.toString() + "&areaNum=" + dong.getAreaNum());
			if (datas != null) {
				save(datas);
				count++;
			}
		}

		return count;
	}

	private void fillDong(ClassDaoEx dao, List<FX_CO_DONG> list, FX_CO_DONG dong) throws Exception {
		FX_CO_DONG upper = dao.selectData(FX_CO_DONG.class, FxApi.makePara("areaNum", dong.getUpperAreaNum()));
		if (upper != null) {
			list.add(upper);
			fillDong(dao, list, upper);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String, Object>> getData(String url) throws Exception {

		Logger.logger.debug("url : {}", url);

		UrlClientGet client = new UrlClientGet();
		String body = client.get(url.toString());

		Logger.logger.debug("body : \n{}", body);

		Map<String, Object> map = FxmsUtil.toMapFromJson(body);
		Object obj = map.get("data");
		if (obj instanceof List) {
			return (List) obj;
		} else {
			return null;
		}
	}

	private List<FX_CO_DONG> getDongs() throws Exception {

		ClassDaoEx dao = ClassDaoEx.open();
		List<FX_CO_DONG> ret = new ArrayList<>();

		// 등록된 주소 목록 조회
		List<FX_CF_INLO> list = dao.selectDatas(FX_CF_INLO.class,
				FxApi.makePara("DEL_YN", "N", "AREA_NUM is not", null));

		// 법정동 목록 조회
		for (FX_CF_INLO inlo : list) {
			if (inlo.getAreaNum() != null) {
				FX_CO_DONG dong = dao.selectData(FX_CO_DONG.class, FxApi.makePara("areaNum", inlo.getAreaNum()));
				if (dong != null) {
					ret.add(dong);
					fillDong(dao, ret, dong);
				}
			}
		}

		dao.close();

		return ret;
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