package fxms.ems.vup.adapter;

import java.util.Map;

import fxms.bas.api.PsApi;
import fxms.bas.api.ValueApi;
import fxms.bas.api.ValueApi.StatFunction;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.fxo.adapter.PsStatAfterAdapter;
import fxms.bas.impl.api.ValueApiDfo;
import fxms.bas.vo.PsKind;
import fxms.bas.vo.PsVoRaw;
import fxms.bas.vo.PsVoRawList;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;

@FxAdapterInfo(service = "AppService", descr = "전력적산량을 이용하여 사용량을 계산한다.")
public class EPowerStatAdapter extends PsStatAfterAdapter {

	@FxAttr(name = "psTable", description = "생성되는 성능그룹", value = "FX_V_EPWR")
	private String psTable;

	@FxAttr(name = "psKindName", description = "성능종류", value = "MIN15")
	private String psKindName;

	@FxAttr(name = "psIdAccum", description = "누적성능항목", value = "ePowerAccum")
	private String psIdAccum;

	@FxAttr(name = "psIdVal", description = "계산된성능항목", value = "ePowerAmt")
	private String psIdVal;

	@Override
	public String toString() {
		return FxmsUtil.toJson(this);
	}

	public static void main(String[] args) {

		ValueApi.api = new ValueApiDfo();

		long psDate = DateUtil.getDtm();

		EPowerStatAdapter a = new EPowerStatAdapter();
		try {
			a.psIdAccum = "ePowerAccum";
			a.psIdVal = "ePowerAmt";
			a.afterStat("FX_V_EPWR", "MIN15", psDate);
			PsVoRawList list = a.makeMin15EPower(psDate);
			System.out.println(FxmsUtil.toJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterStat(String psTable, String psKindName, long psDate) throws Exception {

		PsVoRawList valueList = null;

		if (psTable.equals(this.psTable) && psKindName.equals(this.psKindName) && this.psIdAccum != null
				&& this.psIdVal != null) {
			valueList = makeMin15EPower(psDate);
		}

		if (valueList != null && valueList.size() > 0) {
			ValueApi.getApi().addValue(valueList, true);
		}

	}

	private PsVoRawList makeMin15EPower(long psDate) throws Exception {

		PsVoRawList valueList = new PsVoRawList("2nd", DateUtil.toMstime(psDate));

		Logger.logger.info("psTable={}:{}, psKind={}:{}, psDate={}, psId={},{}", this.psTable, psTable, this.psKindName,
				psKindName, psDate, this.psIdAccum, this.psIdVal);

		PsKind psKind = PsApi.getApi().getPsKind("MIN15");
		PsKind rawKind = PsApi.getApi().getPsKindRaw();

		if (psKind != null) {

			float value;
			Number prevValue, curValue;

			// 현재
			long startDtm = psKind.getHstimeNext(psDate, -1);
			long endDtm = psKind.getHstimeEnd(startDtm);
			Map<Long, Number> curMap = ValueApi.getApi().getStatValue(this.psIdAccum, rawKind, null, startDtm, endDtm,
					StatFunction.Max);

			startDtm = psKind.getHstimeNext(psDate, -2);
			endDtm = psKind.getHstimeEnd(startDtm);

			Map<Long, Number> prevMap = ValueApi.getApi().getStatValue(this.psIdAccum, rawKind, null, startDtm, endDtm,
					StatFunction.Max);

			for (Long moNo : curMap.keySet()) {

				curValue = curMap.get(moNo);
				prevValue = prevMap.get(moNo);

				value = curValue.floatValue() - prevValue.floatValue();
				valueList.add(new PsVoRaw(moNo, this.psIdVal, value));
			}
		}

		return valueList;
	}
}
