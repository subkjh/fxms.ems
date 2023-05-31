package fxms.ems.vup.para;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxTable;

@FxTable(name = "FE_FAC_PIPE", comment = "설비배관테이블")
public class SelectPipePara {

	@FxColumn(name = "PIPE_ID", size = 15, comment = "배관ID")
	private String pipeId;

	public String getPipeId() {
		return pipeId;
	}

	public void setPipeId(String pipeId) {
		this.pipeId = pipeId;
	}
	
	

}
