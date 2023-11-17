package fxms.ems.cems;

import java.util.HashMap;
import java.util.Map;

public class CemsApi {

	public static final String VAR_NAME_cems_url_node_browse = "cems.url.node.browse";

	/** 전력사용피크근접 */
	public static final int ALCD_EPOWER_THRESHOLD = 90001;

	public static CemsApi api;

	/**
	 * 사용할 DBM를 제공합니다.
	 * 
	 * @return DBM
	 */
	public synchronized static CemsApi getApi() {
		if (api != null)
			return api;

		api = new CemsApi();

		return api;
	}

	private final Map<Long, Long> moLastValueTimeMap = new HashMap<>();

	private CemsApi() {

	}

	/**
	 * 동일 시간의 데이터를 방지하기 위해 시간을 비교한다.
	 * 
	 * @param moNo
	 * @param time
	 * @return
	 */
	public boolean isGtTime(long moNo, long time) {

		Long lastTime = moLastValueTimeMap.get(moNo);
		if (lastTime == null) {
			moLastValueTimeMap.put(moNo, time);
			return true;
		}

		if (time > lastTime.longValue()) {
			moLastValueTimeMap.put(moNo, time);
			return true;
		}

		return false;
	}

}
