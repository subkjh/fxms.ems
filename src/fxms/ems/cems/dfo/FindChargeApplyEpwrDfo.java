package fxms.ems.cems.dfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsKind;
import fxms.ems.bas.api.FemsApi;
import fxms.ems.cems.dao.EpwrChargeQid;
import fxms.ems.cems.dto.EpwrPeakDto;
import subkjh.bas.co.log.Logger;
import subkjh.dao.QidDaoEx;

/**
 * 요금산정일로 적용되는지 여부 ( 12, 1, 2, 7, 8, 9, 당월 )에서 최대 사용 전력
 * 
 * @author subkjh
 *
 */

public class FindChargeApplyEpwrDfo implements FxDfo<String, Integer> {

	public static void main(String[] args) {

		FindChargeApplyEpwrDfo dfo = new FindChargeApplyEpwrDfo();
		try {
			System.out.println(dfo.makeEpwrCharge("202210"));
			System.out.println(dfo.makeEpwrCharge("202211"));
			System.out.println(dfo.makeEpwrCharge("202212"));
			System.out.println(dfo.makeEpwrCharge("202301"));
			System.out.println(dfo.makeEpwrCharge("202302"));
			System.out.println(dfo.makeEpwrCharge("202303"));
			System.out.println(dfo.makeEpwrCharge("202304"));
			System.out.println(dfo.makeEpwrCharge("202305"));
			System.out.println(dfo.makeEpwrCharge("202306"));
			System.out.println(dfo.makeEpwrCharge("202307"));
			System.out.println(dfo.makeEpwrCharge("202308"));
			System.out.println(dfo.makeEpwrCharge("202309"));
			System.out.println(dfo.makeEpwrCharge("202310"));
			System.out.println(dfo.makeEpwrCharge("202311"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, String yyyymm) throws Exception {
		return makeEpwrCharge(yyyymm);
	}

	@SuppressWarnings("unchecked")
	public int makeEpwrCharge(String yyyymm) throws Exception {

		int matchCnt = 0;
		long ptime = System.currentTimeMillis();
		List<String> months = getMonths(yyyymm);

		Map<Integer, EpwrPeakDto> map = new HashMap<>();
		EpwrPeakDto oldDto;
		EpwrChargeQid QID = new EpwrChargeQid();
		QidDaoEx dao = QidDaoEx.open(EpwrChargeQid.QUERY_XML_FILE);

		for (String m : months) {

			Logger.logger.debug("month={}", m);

			matchCnt = 0;
			
			List<EpwrPeakDto> list = dao.selectQid2Res(QID.select_peak_epwr_dtm,
					FxApi.makePara("yyyymm", yyyymm, "startDtm", m + "01000000", "endDtm", m + "31235959"));
			for (EpwrPeakDto v : list) {
				oldDto = map.get(v.inloNo);
				if (oldDto == null) {
					map.put(v.inloNo, v);
					v.yyyyMm = yyyymm;
					matchCnt++;
				}
			}

			Logger.logger.debug("month={}, datas={}, matched={}", m, list.size(), matchCnt);

		}

		Logger.logger.debug("datas = {} - {}ms", map.size(), (System.currentTimeMillis() - ptime));

		dao.execute(QID.update_charge_apply_dtm, map.values());
		int cnt = dao.getProcessedCount();
		dao.close();

		return cnt;
	}

	private List<String> getMonths(String yyyymm) {
		PsKind kind = FemsApi.kindMonthly;
		long hstime = Long.parseLong(yyyymm + "01000000");
		long endHstime = kind.getHstimeNext(hstime, -12);

		String months[] = new String[] { "12", "01", "02", "07", "08", "09", yyyymm.substring(4, 6) };
		String mm;
		List<String> yyyymms = new ArrayList<>();

		for (long dtm = hstime; dtm > endHstime; dtm = kind.getHstimeNext(dtm, -1)) {
			mm = String.valueOf(dtm).substring(4, 6);
			for (String m : months) {
				if (mm.equals(m)) {
					yyyymms.add(String.valueOf(dtm).substring(0, 6));
				}
			}
		}

		return yyyymms;
	}
}
