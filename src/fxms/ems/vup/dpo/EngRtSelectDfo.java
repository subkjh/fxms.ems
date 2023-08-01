package fxms.ems.vup.dpo;

import fxms.bas.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_RT_BAS;
import subkjh.dao.ClassDaoEx;

/**
 * 에너지 거래 정보를 가져온다.
 * 
 * @author subkjh
 *
 */
public class EngRtSelectDfo implements FxDfo<String, FE_ENG_RT_BAS> {

	public EngRtSelectDfo() {

	}

	@Override
	public FE_ENG_RT_BAS call(FxFact fact, String engRtId) throws Exception {
		int complexInloNo = fact.getInt("complexInloNo");

		return select(engRtId, complexInloNo);
	}

	public FE_ENG_RT_BAS select(String engRtId, int complexInloNo) throws Exception {
		return ClassDaoEx.SelectData(FE_ENG_RT_BAS.class, FxApi.makePara("engRtId", engRtId, "inloNo", complexInloNo));
	}
}
