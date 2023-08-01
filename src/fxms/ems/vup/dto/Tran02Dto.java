package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Tran02Dto {

	@FxAttr(description = "SOURCE 공장P&ID 또는 설비P&ID", example = "F010011", required = false)
	public String source_pid;

	@FxAttr(description = "SINK 공장P&ID", example = "F010001", required = false)
	public String sink_pid;

	@FxAttr(description = "에너지코드", example = "E01", required = false)
	public String energy_code;

	@FxAttr(description = "거래월", example = "202304")
	public String trns_month;
}
