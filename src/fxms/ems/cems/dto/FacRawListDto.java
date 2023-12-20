package fxms.ems.cems.dto;

import java.util.List;

import fxms.bas.fxo.FxAttr;

public class FacRawListDto {

	@FxAttr(description = "설비목록")
	private List<FacRawDto> datas;

	public List<FacRawDto> getDatas() {
		return datas;
	}

	public void setDatas(List<FacRawDto> datas) {
		this.datas = datas;
	}

}
