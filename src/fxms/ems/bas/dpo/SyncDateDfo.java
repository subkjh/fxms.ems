package fxms.ems.bas.dpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.VarApi;
import fxms.bas.exp.NotFoundException;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_CO_DATE;
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
 * 요일 정보를 동기화한다.
 * 
 * @author subkjh
 *
 */
public class SyncDateDfo implements FxDfo<Void, Integer> {

	public static final String VAR_NAME = "tisp.data.url";

	public static void main(String[] args) {

		Map<String, Object> varInfo = new HashMap<String, Object>();
		varInfo.put("varGrpName", "CONF");
		varInfo.put("varDispName", "TISP데이터조회URL");
		varInfo.put("varDesc", "TISP데이터를 조회할 URL을 설정한다.");
		varInfo.put("varVal", "http://10.0.1.11:33194/");
		try {
			VarApi.getApi().updateVarInfo(VAR_NAME, varInfo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SyncDateDfo dfo = new SyncDateDfo();
		try {
			System.out.println(dfo.syncDate());
		} catch (Exception e) {
		}
	}

	@Override
	public Integer call(FxFact fact, Void data) throws Exception {
		return syncDate();
	}

	/**
	 * 에너지 정보 확인 성능항목 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public int syncDate() throws Exception {

		String year = DateUtil.getYmdStr().substring(0, 4);

		int count = syncDate(year); // 올해
		count = syncDate(String.valueOf(Integer.parseInt(year) + 1)); // 내년
		syncDate(String.valueOf(Integer.parseInt(year) + 2)); // 내후년

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

			// java field 형식의 맵이 아니면 변환한다.
			if (data.get("dowCd") == null) {
				data.put("seanCd", data.get("sean_cd"));
				data.put("hldayYn", data.get("hlday_yn"));
				data.put("ymdDesc", data.get("ymd_desc"));
				data.put("dowCd", data.get("dow_cd"));
			}

			// 시간
			FxTableMaker.initRegChgMap(User.USER_NO_SYSTEM, data);

			// 조회조건
			para.put("ymd", data.get("ymd"));

			dao.setOfClass(FX_CO_DATE.class, para, data);
		}

		dao.close();

		return datas.size();
	}

	private int syncDate(String yyyy) throws Exception {

		int count = 0;
		String site = VarApi.getApi().getVarValue(VAR_NAME, null);
		if (site == null) {
			throw new NotFoundException("conf", VAR_NAME, Lang.get("Environment variable not set.", VAR_NAME));
		}

		StringBuffer url = new StringBuffer();
		url.append(site).append("/api/tisp/06").append("?date=").append(yyyy);
		List<Map<String, Object>> datas = getData(url.toString());
		if (datas != null) {
			count = save(datas);
		}

		return count;
	}

}