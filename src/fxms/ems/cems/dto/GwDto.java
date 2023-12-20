package fxms.ems.cems.dto;

import fxms.bas.fxo.FxAttr;

public class GwDto {

	@FxAttr(description = "GW IP주소", example = "10.0.0.1", required = true)
	public String nodeIpAddr;

	@FxAttr(description = "GW 포트", example = "4840", required = false)
	public int commPortNo;

	@FxAttr(description = "통신프로토콜", example = "opcua", required = false)
	public String commProtc;

	@FxAttr(description = "관리대상번호", example = "10000", required = true)
	public long moNo;

	@FxAttr(description = "상태(1:정상,2:통신안됨,3:데이터미수신)", example = "1", required = true)
	public int state;

}
