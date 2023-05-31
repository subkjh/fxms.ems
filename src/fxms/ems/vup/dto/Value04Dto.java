package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Value04Dto {

	@FxAttr(name = "device_pid", description = "설비 P&ID")
	public String device_pid;

	@FxAttr(name = "point_pid", description = "관제점 P&ID", required = false)
	public String point_pid;

	@FxAttr(name = "collected_dt_start", description = "조회시작수집일자", example = "20230227")
	public String collected_date_start;

	@FxAttr(name = "collected_date_end", description = "조회종료수집일자", example = "20230227", required = false)
	public String collected_date_end;

	@FxAttr(name = "ps_kind_name", description = "수집종류이름", required = false)
	public String ps_kind_name = "RAW";

}
