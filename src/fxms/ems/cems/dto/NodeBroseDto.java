package fxms.ems.cems.dto;

import fxms.bas.fxo.FxAttr;

public class NodeBroseDto {

	@FxAttr(description = "GW IP주소", example = "10.0.0.1")
	private String ip;

	@FxAttr(description = "GW 접속 포트", example = "1000")
	private int port;
	
	
}
