package fxms.ems.bas.dto;

import java.util.List;

import fxms.bas.fxo.FxAttr;

public class PointValuesDto {

	@FxAttr(description = "관제점", example = "[]")
	private List<PointValueDto> datas;

	public List<PointValueDto> getDatas() {
		return datas;
	}

}
