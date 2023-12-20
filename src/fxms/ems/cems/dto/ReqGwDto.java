package fxms.ems.cems.dto;

import fxms.bas.fxo.FxAttr;

public class ReqGwDto {

	@FxAttr(description = "GW IP주소", example = "10.0.0.1")
	public String nodeIpAddr;

	@FxAttr(description = "GW 포트", example = "4840", required = false)
	public int commPortNo;

	@FxAttr(description = "산단명", example = "daejeon")
	public String complex;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(complex).append(".").append(nodeIpAddr);
		return sb.toString();
	}

}
