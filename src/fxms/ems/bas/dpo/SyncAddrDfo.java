package fxms.ems.bas.dpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.VarApi;
import fxms.bas.exp.NotFoundException;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_CO_DONG;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import subkjh.bas.co.lang.Lang;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.user.User;
import subkjh.bas.net.url.UrlClientGet;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.util.FxTableMaker;

/**
 * 주소를 동기화 한다.
 * 
 * @author subkjh
 *
 */
public class SyncAddrDfo implements FxDfo<Void, Integer> {

	private final String VAR_NAME = SyncDateDfo.VAR_NAME;

	public static void main(String[] args) {

		SyncAddrDfo dfo = new SyncAddrDfo();
		try {
			System.out.println(dfo.syncAddr());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Void data) throws Exception {
		return syncAddr();
	}

	/**
	 * 에너지 정보 확인 성능항목 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public int syncAddr() throws Exception {

		String site = VarApi.getApi().getVarValue(VAR_NAME, null);
		if (site == null) {
			throw new NotFoundException("conf", VAR_NAME, Lang.get("Environment variable not set.", VAR_NAME));
		}

		int siDocnt = syncSido(site);
		int siGunGu = syncSigungu(site);
		int umd = syncUmd(site);

		Logger.logger.info("sido={}, sigungu={}, umd={}", siDocnt, siGunGu, umd);

		return siDocnt + siGunGu + umd;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Map<String, Object>> getData(String url) throws Exception {
		UrlClientGet client = new UrlClientGet();
		String body = client.get(url.toString());

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

			if (data.get("geoFence") == null) {
				data.put("geoFence", data.get("geometry"));
			}

			// 시간
			FxTableMaker.initRegChgMap(User.USER_NO_SYSTEM, data);

			// 조회조건
			para.put("areaNum", data.get("areaNum"));

			dao.setOfClass(FX_CO_DONG.class, para, data);
		}

		dao.close();

		return datas.size();
	}

	private int syncSido(String site) throws Exception {
		StringBuffer url = new StringBuffer();
		url.append(site).append("/api/tisp/03");
		List<Map<String, Object>> datas = getData(url.toString());
		return save(datas);
	}

	private int syncSigungu(String site) throws Exception {
		StringBuffer url = new StringBuffer();
		url.append(site).append("/api/tisp/04");
		List<Map<String, Object>> datas = getData(url.toString());
		return save(datas);
	}

	private int syncUmd(String site) throws Exception {
		StringBuffer url = new StringBuffer();
		url.append(site).append("/api/tisp/05");
		List<Map<String, Object>> datas = getData(url.toString());
		return save(datas);
	}

}