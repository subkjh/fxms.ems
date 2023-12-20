package fxms.ems.cems.dto;

import java.io.Serializable;

import fxms.bas.fxo.FxAttr;

public class NodeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6325315261706614573L;

	@FxAttr(description = "노드 ID", example = "54111")
	public String nodeId;

	@FxAttr(description = "노드 네임스페이스 인덱스", example = "0")
	public int namespaceIndex;

	@FxAttr(description = "노드 이름", example = "busan.172_29_29_100.DX01001")
	public String displayName;

	public long moNo;

	public long gwMoNo;

	public String moType;

}
