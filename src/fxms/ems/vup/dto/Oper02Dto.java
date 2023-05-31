package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Oper02Dto {

	@FxAttr(description = "산단 P&ID", example = "F01")
	public String complex_pid;

	@FxAttr(description = "공장 P&ID", example = "F010001", required = false)
	public String factory_pid;

}
