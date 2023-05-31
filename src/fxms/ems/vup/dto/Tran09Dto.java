package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Tran09Dto {
	
	@FxAttr(description = "SOURCE 공장P&ID 또는 설비P&ID", name = "source_pid")
	public String source_pid;

	@FxAttr(description = "SINK 공장P&ID", name = "sink_pid")
	public String sink_pid;

	@FxAttr(description = "에너지코드", name = "energy_code")
	public String energy_code;
}
