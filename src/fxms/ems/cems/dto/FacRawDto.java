package fxms.ems.cems.dto;

import fxms.bas.fxo.FxAttr;

public class FacRawDto {

	@FxAttr(description = "산단명")
	public String complexName;

	@FxAttr(description = "회사명")
	public String companyName;

	@FxAttr(description = "공장")
	public String plantName;

	@FxAttr(description = "공정")
	public String processName;

	@FxAttr(description = "설비유형")
	public String facType;

	@FxAttr(description = "설비명")
	public String facName;

	@FxAttr(description = "구분")
	public String tag;

	@FxAttr(description = "전압(V)")
	public String voltage;

	@FxAttr(description = "배선")
	public String wiring;

	@FxAttr(description = "차단기용량(A)")
	public String cirBrkCapa;

	@FxAttr(description = "GW IP")
	public String gwIp;

	@FxAttr(description = "OPC Meter ID")
	public String opcMeterId;

}
