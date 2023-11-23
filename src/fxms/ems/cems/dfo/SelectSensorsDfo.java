package fxms.ems.cems.dfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_MX_MO_ATTR;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.mo.NodeMo;
import fxms.ems.cems.dao.CemsQid;
import fxms.ems.cems.dto.NodeDto;
import fxms.ems.cems.dto.NodeGwDto;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.QidDaoEx;

public class SelectSensorsDfo implements FxDfo<String, List<NodeGwDto>> {

	public static void main(String[] args) {
		MoApi.api = new MoApiDfo();

		SelectSensorsDfo dfo = new SelectSensorsDfo();
		try {
			System.out.println(FxmsUtil.toJson(dfo.select("BS")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<NodeGwDto> call(FxFact arg0, String complexTid) throws Exception {
		return select(complexTid);
	}

	public List<NodeGwDto> select(String complexTid) throws Exception {
		return make(selectNodes(complexTid));
	}

	private Map<Long, NodeMo> getNodeMap() throws Exception {
		Map<Long, NodeMo> ret = new HashMap<>();
		List<NodeMo> nodes = MoApi.getApi().getMos(FxApi.makePara("moClass", NodeMo.MO_CLASS), NodeMo.class);
		for (NodeMo node : nodes) {
			ret.put(node.getMoNo(), node);
		}
		return ret;
	}

	private List<NodeGwDto> make(List<NodeDto> nodes) throws Exception {

		NodeGwDto dto;
		NodeMo gw;
		Map<String, NodeGwDto> ret = new HashMap<>();
		Map<Long, NodeMo> nodeMap = getNodeMap();

		for (NodeDto node : nodes) {
			gw = nodeMap.get(node.getGwMoNo());
			if (gw != null) {
				dto = ret.get(gw.getNodeIpAddr());
				if (dto == null) {
					dto = new NodeGwDto();
					dto.setNodeIpAddr(gw.getNodeIpAddr());
					dto.setCommPortNo(gw.getCommPortNo());
					ret.put(dto.getNodeIpAddr(), dto);
				}
				dto.addNode(node);
			}
		}
		return new ArrayList<>(ret.values());
	}

	@SuppressWarnings("unchecked")
	public List<NodeDto> selectNodes(String complexTid) throws Exception {

		// 센서 목록
		CemsQid QID = new CemsQid();
		QidDaoEx dao = QidDaoEx.open(CemsQid.QUERY_XML_FILE);
		List<NodeDto> nodes = dao.selectQid2Res(QID.select_NODE__BY_COMPLEX, FxApi.makePara("inloTid", complexTid));
		dao.close();

		Map<Long, NodeDto> map = new HashMap<>();
		for (NodeDto node : nodes) {
			map.put(node.getMoNo(), node);
		}

		// 센서 속성
		List<FX_MX_MO_ATTR> attrs = ClassDaoEx.SelectDatas(FX_MX_MO_ATTR.class,
				FxApi.makePara("attrName", new String[] { "nodeId", "namespaceIndex" }));
		NodeDto node;
		for (FX_MX_MO_ATTR attr : attrs) {
			node = map.get(attr.getMoNo());
			if (node != null && "nodeId".equals(attr.getAttrName())) {
				node.nodeId = attr.getAttrValue();
			} else if (node != null && "namespaceIndex".equals(attr.getAttrName())) {
				node.namespaceIndex = Float.valueOf(attr.getAttrValue()).intValue();
			}
		}

		return nodes;

	}
}
