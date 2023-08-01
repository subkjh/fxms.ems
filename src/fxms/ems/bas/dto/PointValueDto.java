package fxms.ems.bas.dto;

import fxms.bas.fxo.FxAttr;

public class PointValueDto {

	@FxAttr(description = "관제점", example = "MT0001010101")
	private String pointId;

	@FxAttr(description = "수집값", example = "123")
	private Number value;

	@FxAttr(description = "수집일시", example = "20200101000000")
	private String date;

	public String getPointId() {
		return pointId;
	}

	public Number getValue() {
		return value;
	}

	public String getDate() {
		return date;
	}

	
}
