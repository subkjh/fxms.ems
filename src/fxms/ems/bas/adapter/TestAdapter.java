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
import fxms.bas.impl.dpo.vo.MakeTestPsVoDfo;
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

		MakeTestPsVoDfo dfo = new MakeTestPsVoDfo();

		for (PsItem item : itemList) {
			psList.add(dfo.makeTestValue(mo, null, item));
		}

		return psList;

	}

}