package fxms.ems.vup.para;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

@FxTable(name = "FE_FAC_PIPE", comment = "설비배관테이블")
@FxIndex(name = "FE_FAC_PIPE__PK", type = INDEX_TYPE.PK, columns = { "PIPE_ID" })
public class UpdatePipeDiagNoPara {

	@FxColumn(name = "PIPE_ID", size = 15, comment = "배관ID")
	private String pipeId;

	@FxColumn(name = "DIAG_NO", size = 9, comment = "다이아그램번호")
	private int diagNo = -1;

	@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
	private int chgUserNo = 0;

	@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
	private long chgDtm;

}
