package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Val09Dto {

	@FxAttr(description = "산단 PID", example = "F01")
	public String complex_pid;

	@FxAttr(description = "공장 PID", example = "F010001", required = false)
	public String factory_pid;

}
