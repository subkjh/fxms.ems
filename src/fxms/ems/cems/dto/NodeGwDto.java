package fxms.ems.cems.dto;

import java.util.ArrayList;
import java.util.List;

import fxms.bas.fxo.FxAttr;

public class NodeGwDto {

	@FxAttr(description = "GW IP주소", example = "10.0.0.1")
	private String nodeIpAddr;

	@FxAttr(description = "GW 포트", example = "4840", required = false)
	private int commPortNo;

	@FxAttr(description = "GW 접속 포트", example = "1000")
	private List<NodeDto> nodes;

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

	public String getNodeIpAddr() {
		return nodeIpAddr;
	}

	public void setNodeIpAddr(String nodeIpAddr) {
		this.nodeIpAddr = nodeIpAddr;
	}

	public int getCommPortNo() {
		return commPortNo;
	}

	public void setCommPortNo(int commPortNo) {
		this.commPortNo = commPortNo;
	}

}
