package fxms.ems.cems.dto;

import fxms.bas.fxo.FxAttr;

public class GwDto {

	@FxAttr(description = "GW IP주소", example = "10.0.0.1")
	private String nodeIpAddr;

	@FxAttr(description = "GW 포트", example = "4840", required = false)
	private int commPortNo;

	@FxAttr(description = "통신프로토콜", example = "opcua", required = false)
	private String commProtc;

	@FxAttr(description = "관리대상번호", example = "10000", required = false)
	private long moNo;

	public String getNodeIpAddr() {
		return nodeIpAddr;
	}

	public int getCommPortNo() {
		return commPortNo;
	}

	public String getCommProtc() {
		return commProtc;
	}

	public long getMoNo() {
		return moNo;
	}

}
