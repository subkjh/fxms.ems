package fxms.ems.cems.dto;

import fxms.bas.fxo.FxAttr;

public class GwPollingDto {

	@FxAttr(description = "산단 PID", example = "BS", required = true)
	private String complex;

	@FxAttr(description = "폴링 시간", example = "20231031102900", required = true)
	private String datetime;

	@FxAttr(description = "처리건수", example = "1234")
	private int procCnt;

	@FxAttr(description = "성공여부", example = "true")
	private boolean isOk;

	@FxAttr(description = "메시지")
	private String message;

	public String getDatetime() {
		return datetime;
	}

	public String getComplex() {
		return complex;
	}

	public int getProcCnt() {
		return procCnt;
	}

	public boolean isOk() {
		return isOk;
	}

	public String getMessage() {
		return message;
	}


	
}
