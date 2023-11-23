package fxms.ems.cems.dfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.ao.AlarmApi;
import fxms.api.ao.AlarmApiDfo;
import fxms.api.fo.AppApi;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.bas.co.ALARM_CODE;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.handler.vo.FxResponse;
import fxms.bas.impl.dbo.all.FX_MX_MO_ATTR;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.mo.Mo;
import fxms.ems.bas.mo.SensorMo;
import fxms.ems.cems.CemsApi;
import fxms.ems.cems.dto.GwDto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.ClassDaoEx;

/**
 * CEMS 게이트웨이로부터 관리하는 노드 목록을 가져와 이미 등록되어 있는 경우 nodeId, namespaceIndex를 업데이트하고
 * 등록되어 있지 않으면 TO_CHECK 및 알람으로 통보한다.
 * 
 * @author subkjh
 *
 */
public class CallGwNodesDfo implements FxDfo<Void, Integer> {

	class Data {
		GwDto gw;
		FxResponse response;

		Data(GwDto gw, FxResponse response) {
			this.gw = gw;
			this.response = response;
		}
	}

	public static void main(String[] args) {

		System.out.println(System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism"));
		System.out.println(System.getenv("java.util.concurrent.ForkJoinPool.common.parallelism"));

		MoApi.api = new MoApiDfo();
		AlarmApi.api = new AlarmApiDfo();

		CallGwNodesDfo dfo = new CallGwNodesDfo();
		try {
			dfo.checkNodes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact arg0, Void arg1) throws Exception {
		return checkNodes();
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer checkNodes() throws Exception {

		List<Data> datas = callUrlParallel();

		return parse(datas);

	}

	List<Data> callUrl() throws Exception {

		String URL = AppApi.getApi().getVar(CemsApi.VAR_NAME_cems_url_node_browse,
				"https://tisp.integrict-cems.com/browseNode?ip=$ip&port=$port");

		List<Data> ret = new ArrayList<>();
		List<GwDto> list = new SelectGwsDfo().select();
		String url;
		String port;
		FxResponse response;

		for (GwDto gw : list) {

			if (gw.getCommPortNo() <= 0) {
				port = "4840";
			} else {
				port = String.valueOf(gw.getCommPortNo());
			}

			url = URL.replaceFirst("\\$ip", gw.getNodeIpAddr());
			url = url.replaceFirst("\\$port", port);

			response = get(url, 30);
			ret.add(new Data(gw, response));
			if (response.getCode() == 200) {
				break;
			}
		}

		return ret;

	}

	List<Data> callUrlParallel() throws Exception {

		Map<String, Mo> retMap = getMoTidMap();

		String URL = AppApi.getApi().getVar(CemsApi.VAR_NAME_cems_url_node_browse,
				"https://tisp.integrict-cems.com/browseNode?ip=$ip&port=$port");

		List<GwDto> list = new SelectGwsDfo().select();
		List<Data> ret = new ArrayList<>();

		list.parallelStream().forEach(gw -> {

			String url;
			String port;
			FxResponse response;
			Data data;

			if (gw.getCommPortNo() <= 0) {
				port = "4840";
			} else {
				port = String.valueOf(gw.getCommPortNo());
			}

			url = URL.replaceFirst("\\$ip", gw.getNodeIpAddr());
			url = url.replaceFirst("\\$port", port);

			try {
				response = get(url, 30);
				data = new Data(gw, response);
//				ret.add(new Data(gw, response));
			} catch (Exception e) {
				data = new Data(gw, null);
//				ret.add(new Data(gw, null));
			}

			try {
				parse(data, retMap);
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		});

		return ret;

	}

	private FxResponse get(String site, int timeout) throws Exception {
		try {

			Logger.logger.debug("site={}", site);
			URL url = new URL(site);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			try {
				conn.setReadTimeout(timeout * 1000);
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					return new FxResponse(conn.getResponseCode(), null, null);
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				StringBuffer sb = new StringBuffer();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}

				Logger.logger.debug("url={}, \n*** body ***\n{}\n*** body end ***", site, sb.toString());

				Map<String, Object> datas = FxmsUtil.toMapFromJson(sb.toString());

				return new FxResponse(conn.getResponseCode(), null, datas);

			} catch (Exception e) {
				throw e;
			} finally {
				conn.disconnect();
			}

		} catch (Exception e) {
			Logger.logger.error(e);
			return new FxResponse(-1, e.getMessage(), null);
		} finally {

		}

	}

	private Map<String, Mo> getMoTidMap() throws Exception {
		Map<String, Mo> retMap = new HashMap<>();
		List<Mo> mos = MoApi.getApi()
				.getMos(FxApi.makePara("moClass", SensorMo.MO_CLASS, "delYn", "N", "moTid is not", null));
		for (Mo mo : mos) {
			retMap.put(mo.getMoTid(), mo);
		}
		return retMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int parse(List<Data> datas) throws Exception {

		Map<String, Mo> moMap = getMoTidMap();
		Mo mo;
		String moTid;
		Integer nsIndex, nodeId;
		Integer nsIndex2, nodeId2;
		Map<String, Object> moData;
		List<FX_MX_MO_ATTR> updateDatas = new ArrayList<>();
		long chgDtm = DateUtil.getDtm();

		for (Data data : datas) {

			if (data.response == null) {

				try {
					// 응답이 없으면 타임아웃 이벤트 발생
					AlarmApi.getApi().fireAlarms(data.gw.getMoNo(), null, ALARM_CODE.mo_timeout.getAlcdNo());
				} catch (Exception e) {
					Logger.logger.error(e);
				}

			} else if (data.response.getCode() != 200) {

				try {
					// 정상적인 응답이 아니면 응답 오류 이벤트 발생
					AlarmApi.getApi().fireAlarms(data.gw.getMoNo(), null, ALARM_CODE.value_error_response.getAlcdNo());
				} catch (Exception e) {
					Logger.logger.error(e);
				}

			} else {

				// 정상적이면 기존 알람 해제
				AlarmApi.getApi().clearAlarms(data.gw.getMoNo(), null //
						, ALARM_CODE.mo_timeout.getAlcdNo() //
						, ALARM_CODE.value_error_response.getAlcdNo());

				Object result = data.response.getData("result");
				if (result instanceof List) {

					List<Map<String, Object>> nodes = (List) result;

					for (Map<String, Object> node : nodes) {

						moData = new HashMap<>();

						moTid = node.get("displayName").toString();
						mo = moMap.remove(moTid); // 남아 있는건 미등록된 노드로 처리함.
						if (mo == null) {
							try {
								AlarmApi.getApi().fireAlarms(Mo.PROJECT_MO_NO, moTid,
										ALARM_CODE.mo_notfound.getAlcdNo());
								FxApi.getApi().toCheck(true, "mo.tid", moTid, "Not found on databases.");
							} catch (Exception e) {
								Logger.logger.error(e);
							}
						} else {

							nsIndex = FxmsUtil.toInt(mo.get("namespaceIndex"));
							nsIndex2 = FxmsUtil.toInt(node.get("namespaceIndex"));

							nodeId = FxmsUtil.toInt(mo.get("nodeId"));
							nodeId2 = FxmsUtil.toInt(node.get("nodeId"));

							if (FxmsUtil.isSame(nsIndex, nsIndex2) == false) {
								updateDatas.add(makeAttr(mo.getMoNo(), "namespaceIndex", nsIndex2, chgDtm));
//								moData.put("namespaceIndex", nsIndex2);
							}

							if (FxmsUtil.isSame(nodeId, nodeId2) == false) {
								updateDatas.add(makeAttr(mo.getMoNo(), "nodeId", nodeId2, chgDtm));
//								moData.put("nodeId", nodeId2);
							}

//							if (moData.size() > 0) {
//								moData.put("moNo", mo.getMoNo());
//								updateDatas.add(moData);
//							}

						}
					}
				}
			}

		}

		try {
			ClassDaoEx.MergeOfClass(FX_MX_MO_ATTR.class, updateDatas, "attrValue", "chgDtm");
		} catch (Exception e) {
			Logger.logger.error(e);
		}

//		MoApi.getApi().updateMos(0, updateDatas);

		return updateDatas.size();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int parse(Data data, Map<String, Mo> moMap) throws Exception {

		Mo mo;
		String moTid;
		Integer nsIndex, nodeId;
		Integer nsIndex2, nodeId2;
		Map<String, Object> moData;
		List<FX_MX_MO_ATTR> updateDatas = new ArrayList<>();
		long chgDtm = DateUtil.getDtm();

		if (data.response == null) {

			try {
				// 응답이 없으면 타임아웃 이벤트 발생
				AlarmApi.getApi().fireAlarms(data.gw.getMoNo(), null, ALARM_CODE.mo_timeout.getAlcdNo());
			} catch (Exception e) {
				Logger.logger.error(e);
			}

		} else if (data.response.getCode() != 200) {

			try {
				// 정상적인 응답이 아니면 응답 오류 이벤트 발생
				AlarmApi.getApi().fireAlarms(data.gw.getMoNo(), null, ALARM_CODE.value_error_response.getAlcdNo());
			} catch (Exception e) {
				Logger.logger.error(e);
			}

		} else {

			// 정상적이면 기존 알람 해제
			AlarmApi.getApi().clearAlarms(data.gw.getMoNo(), null //
					, ALARM_CODE.mo_timeout.getAlcdNo() //
					, ALARM_CODE.value_error_response.getAlcdNo());

			Object result = data.response.getData("result");
			if (result instanceof List) {

				List<Map<String, Object>> nodes = (List) result;

				for (Map<String, Object> node : nodes) {

					moData = new HashMap<>();

					moTid = node.get("displayName").toString();
					mo = moMap.remove(moTid); // 남아 있는건 미등록된 노드로 처리함.
					if (mo == null) {
						try {
							AlarmApi.getApi().fireAlarms(Mo.PROJECT_MO_NO, moTid, ALARM_CODE.mo_notfound.getAlcdNo());
							FxApi.getApi().toCheck(true, "mo.tid", moTid, "Not found on databases.");
						} catch (Exception e) {
							Logger.logger.error(e);
						}
					} else {

						nsIndex = FxmsUtil.toInt(mo.get("namespaceIndex"));
						nsIndex2 = FxmsUtil.toInt(node.get("namespaceIndex"));

						nodeId = FxmsUtil.toInt(mo.get("nodeId"));
						nodeId2 = FxmsUtil.toInt(node.get("nodeId"));

//						if (FxmsUtil.isSame(nsIndex, nsIndex2) == false) {
						updateDatas.add(makeAttr(mo.getMoNo(), "namespaceIndex", nsIndex2, chgDtm));
//								moData.put("namespaceIndex", nsIndex2);
//						}

//						if (FxmsUtil.isSame(nodeId, nodeId2) == false) {
						updateDatas.add(makeAttr(mo.getMoNo(), "nodeId", nodeId2, chgDtm));
//								moData.put("nodeId", nodeId2);
//						}

//							if (moData.size() > 0) {
//								moData.put("moNo", mo.getMoNo());
//								updateDatas.add(moData);
//							}

					}
				}
			}
		}

		try {
			ClassDaoEx.MergeOfClass(FX_MX_MO_ATTR.class, updateDatas, "attrValue", "chgDtm");
		} catch (Exception e) {
			Logger.logger.error(e);
		}

//		MoApi.getApi().updateMos(0, updateDatas);

		return updateDatas.size();

	}

	private Map<String, String> getAttrs() throws Exception {
		Map<String, String> map = new HashMap<>();
		List<FX_MX_MO_ATTR> list = ClassDaoEx.SelectDatas(FX_MX_MO_ATTR.class,
				FxApi.makePara("attrName", new String[] { "namespaceIndex", "nodeId" }));
		for (FX_MX_MO_ATTR attr : list) {
			map.put(attr.getMoNo() + ":" + attr.getAttrName(), attr.getAttrValue());
		}
		return map;
	}

	private FX_MX_MO_ATTR makeAttr(long moNo, String attrName, Object attrValue, long chgDtm) {
		FX_MX_MO_ATTR ret = new FX_MX_MO_ATTR();
		ret.setMoNo(moNo);
		ret.setAttrName(attrName);
		ret.setAttrValue(attrValue.toString());
		ret.setChgDtm(chgDtm);
		return ret;
	}
}
