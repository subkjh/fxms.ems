package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

public class Tran04Dto {

	@FxAttr(description = "공장PID", example = "F010001")
	public String factory_pid;

	@FxAttr(description = "계측기PID", example = "F010001-FT1204", required = false)
	public String device_pid;

	@FxAttr(description = "에너지코드", example = "E01")
	public String energy_code;

	@FxAttr(description = "일자", example = "20201010")
	public String date;
	
	@FxAttr(text="시간(15분단위)", description = "15분단위로 예측값을 기록할 때 사용한다.", example = "10:15", required = false)
	public String time;

	@FxAttr(description = "생산예측값", example = "12345")
	public float expAmt;
}
