package fxms.ems.cems.dfo;

import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dbo.CEMS_FAC_RAW;
import fxms.ems.cems.dto.FacRawListDto;
import subkjh.dao.ClassDaoEx;

public class InsertFacRawDfo implements FxDfo<FacRawListDto, Integer> {

	@Override
	public Integer call(FxFact fact, FacRawListDto dto) throws Exception {
		return ClassDaoEx.MergeOfClass(CEMS_FAC_RAW.class, dto.getDatas(), "processName", "facType", "facName", "tag",
				"voltage", "wiring", "gwIp");

	}

}
