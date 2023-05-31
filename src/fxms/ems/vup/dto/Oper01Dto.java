package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Oper01Dto {

	@FxAttr(description = "산단 P&ID", example="F01")
	public String complex_pid;

	@FxAttr(description = "공장 P&ID", example="F010001")
	public String factory_pid;

	@FxAttr(description = "유지보수예약일자", required = true)
	public String mntn_resv_date;

}
