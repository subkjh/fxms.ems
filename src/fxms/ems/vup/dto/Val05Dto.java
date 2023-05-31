package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Val05Dto {

	@FxAttr(description = "산단 PID", example = "F01")
	public String complex_pid;

	@FxAttr(description = "공장 PID", example = "F010001", required=false)
	public String factory_pid;

	@FxAttr(description = "에너지 코드", example = "E01")
	public String energy_code;

	@FxAttr(description = "조회시작일자", example = "20230301")
	public String start_date;

	@FxAttr(description = "조회종료일자", example = "20230301")
	public String end_date;
}
