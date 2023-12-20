package fxms.ems.cems.dfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.ao.AlarmApi;
import fxms.api.mo.MoApi;
import fxms.bas.co.ALARM_CODE;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.handler.vo.FxResponse;
import fxms.bas.impl.dbo.all.FX_MX_MO_ATTR;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.mo.Mo;
import fxms.bas.ws.handler.client.FxHttpClient;
import fxms.ems.bas.mo.SensorMo;
import fxms.ems.cems.dto.ReqGwDto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.ClassDaoEx;

/**
 * CEMS 게이트웨이로부터 관리하는 노드 목록을 가져와 이미 등록되어 있는 경우 nodeId, namespaceIndex를 업데이트하고
 * 등록되어 있지 않으면 TO_CHECK 및 알람으로 통보한다.
 * 
 * @author subkjh
 *
 */
public class CallGwNodes2Dfo implements FxDfo<ReqGwDto, Integer> {

	public static void main(String[] args) {
		CallGwNodes2Dfo dfo = new CallGwNodes2Dfo();

		try {
			ReqGwDto dto = new ReqGwDto();
			dto.nodeIpAddr = args[0];
			dto.commPortNo = Integer.parseInt(args[1]);
			dto.complex = args[2];
			dfo.call(null, dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer call(FxFact arg0, ReqGwDto data) throws Exception {

		String crawler = FxCfg.getCfg().getString("opcua.crawler", "http://localhost:10006");
		URL url = new URL(crawler);

		FxHttpClient c = new FxHttpClient(url.getHost(), url.getPort());

		FxResponse response;
		Map<String, Object> para = ObjectUtil.toMap(data);

		try {
			response = c.call("browse/browse", para);
			List<Map<String, Object>> datas = (List<Map<String, Object>>) response.getDatas();
			return parse(datas);
		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
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

	private FX_MX_MO_ATTR makeAttr(long moNo, String attrName, Object attrValue, long chgDtm) {
		FX_MX_MO_ATTR ret = new FX_MX_MO_ATTR();
		ret.setMoNo(moNo);
		ret.setAttrName(attrName);
		ret.setAttrValue(attrValue.toString());
		ret.setChgDtm(chgDtm);
		return ret;
	}

	private int parse(List<Map<String, Object>> datas) throws Exception {

		Map<String, Mo> moMap = getMoTidMap();
		Mo mo;
		String moTid;
		Integer nsIndex;
		String nodeId;
		List<FX_MX_MO_ATTR> updateDatas = new ArrayList<>();
		long chgDtm = DateUtil.getDtm();

		for (Map<String, Object> node : datas) {

			Logger.logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n {}", node);

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

				nsIndex = FxmsUtil.toInt(node.get("namespaceIndex"));
				nodeId = node.get("nodeId").toString();
				updateDatas.add(makeAttr(mo.getMoNo(), "namespaceIndex", nsIndex, chgDtm));
				updateDatas.add(makeAttr(mo.getMoNo(), "nodeId", nodeId, chgDtm));
			}
		}

		try {
			ClassDaoEx.MergeOfClass(FX_MX_MO_ATTR.class, updateDatas, "attrValue", "chgDtm");
		} catch (Exception e) {
			Logger.logger.error(e);
		}

		return updateDatas.size();

	}
}
