package fxms.ems.cems.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fxms.bas.fxo.FxAttr;

public class NodeGwDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6334687373274573101L;

	@FxAttr(description = "GW IP주소", example = "10.0.0.1")
	public String nodeIpAddr;

	@FxAttr(description = "GW 포트", example = "4840", required = false)
	public int commPortNo;

	@FxAttr(description = "GW 포트", example = "100000", required = false)
	public Long moNo;
	
	public String moTid;

	@FxAttr(description = "GW 접속 포트", example = "1000")
	private List<NodeDto> nodes;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("moNo=").append(moNo);
		sb.append(", ").append(nodeIpAddr).append(":").append(commPortNo);
		return sb.toString();
	}

	/** nodeId가 없는 노드를 가지고 있는지 여부 */
	public boolean hasNullNodeId = false;

	public List<NodeDto> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeDto> nodes) {
		this.nodes = nodes;
	}

	public void addNode(NodeDto node) throws Exception {
		if (this.nodes == null) {
			this.nodes = new ArrayList<>();
		}
		this.nodes.add(node);
	}

}
