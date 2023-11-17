package fxms.ems.cems.api;

import fxms.api.fo.AppApiDfo;
import fxms.bas.vo.PsKind;
import fxms.ems.cems.dpo.PsStatMakeInflux2RdbDfo;
import subkjh.bas.co.log.Logger;

/**
 * CEMS용 통계 생성
 * 
 * @author subkjh
 *
 */
public class AppApiCems extends AppApiDfo {

	@Override
	public int generateStatistics(String psTbl, String psKindName, long psDtm) throws Exception {

		// TSDB 데이터를 이용하여 15분 데이터를 생성한다.

		Logger.logger.info("psTbl={}, psKindName={}, psDtm={}", psTbl, psKindName, psDtm);

		if ("FX_V_EPWR".equals(psTbl) && PsKind.PSKIND_15M.equals(psKindName)) {
			return new PsStatMakeInflux2RdbDfo().generateStatistics(psTbl, psKindName, psDtm);
		} else {
			return super.generateStatistics(psTbl, psKindName, psDtm);
		}

	}
}
