package fxms.ems.bas.adapter;

import java.util.ArrayList;
import java.util.List;

import fxms.bas.api.FxApi;
import fxms.bas.api.PsApi;
import fxms.bas.api.ValueApi;
import fxms.bas.exp.FxNotMatchException;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.fxo.adapter.FxGetValueAdapter;
import fxms.bas.mo.Mo;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsItem.PS_VAL_TYPE;
import fxms.bas.vo.PsValueComp;
import fxms.bas.vo.PsVoRaw;
import subkjh.bas.co.log.Logger;

/**
 * 테스트 아답터
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(pollCycle = 60, moJson = "{\"moClass\":\"SENSOR\"}", service = "VupService", descr = "테스트 아답터로 주기적으로 임의 데이터를 생성한다.")
public class TestAdapter extends FxGetValueAdapter {

	public static void main(String[] args) {
		System.out.println(FxmsUtil.toJson(FxApi.makePara("moClass", "SENSOR")));
	}

	@Override
	public List<PsVoRaw> getValue(Mo targetMo) throws FxNotMatchException, FxTimeoutException, Exception {

		Mo mo = targetMo;
		List<PsVoRaw> psList = new ArrayList<PsVoRaw>();
		List<PsItem> itemList = PsApi.getApi().getPsItemList(mo.getMoClass(), mo.getMoType());

		if (itemList.size() == 0) {
			return psList;
		}

		Logger.logger.debug("{}, {}, {} : {}", mo.getMoName(), mo.getMoClass(), mo.getMoType(), itemList);

		int value;
		int min, max;

		for (PsItem item : itemList) {

			// offline으로 테스트한다.
			if (Math.random() <= 0.05d) {
				return null;
			}

			PsValueComp vo = null;
			try {
				vo = ValueApi.getApi().getCurValue(mo.getMoNo(), null, item.getPsId());
			} catch (Exception e) {
			}

			// 누적값이면 이전보다 크게 한다.
			if (item.getPsValType() == PS_VAL_TYPE.AV) {
				float value2 = 100;
				if (vo != null && vo.getCurValue() != null) {
					value2 = vo.getCurValue().floatValue();
					value2 += (Math.random() * 20);
				} else {
					value2 = 100;
				}
				psList.add(new PsVoRaw(mo.getMoNo(), item.getPsId(), value2));
				continue;
			}

			min = item.getMinVal() == null ? 0 : item.getMinVal().intValue();
			max = item.getMaxVal() == null ? 100 : item.getMaxVal().intValue();

			if (vo != null) {

				value = vo.getCurValue().intValue();

				if (Math.random() >= 0.5d) {
					value += (Math.random() * -100);
				} else {
					value += (Math.random() * 100);
				}

				if (value < min) {
					value = min;
				} else if (value > max) {
					value = max;
				}

			} else {
				value = (int) (Math.random() * max);
				if (value < min) {
					value = min;
				}
			}

			psList.add(new PsVoRaw(mo.getMoNo(), item.getPsId(), value));
		}

		return psList;

	}

}