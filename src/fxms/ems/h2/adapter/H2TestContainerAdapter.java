package fxms.ems.h2.adapter;

import java.util.ArrayList;
import java.util.List;

import fxms.bas.api.MoApi;
import fxms.bas.api.PsApi;
import fxms.bas.exp.FxNotMatchException;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.exp.MoNotFoundException;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.fxo.adapter.FxGetValueAdapter;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.dpo.vo.MakeTestPsVoDfo;
import fxms.bas.mo.Mo;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsVoRaw;

/**
 * 테스트 아답터
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(pollCycle = 60, moJson = "{\"moClass\":\"MO\",\"moType\":\"CONTAINER\"}", service = "MoService", descr = "테스트 아답터로 주기적으로 임의 데이터를 생성한다.")
public class H2TestContainerAdapter extends FxGetValueAdapter {

	public static void main(String[] args) throws MoNotFoundException, Exception {
		MoApi.api = new MoApiDfo();
		H2TestContainerAdapter a = new H2TestContainerAdapter();
		Mo mo = MoApi.getApi().getMo(10001);
		System.out.println(FxmsUtil.toJson(a.getValue(mo)));
	}

	@Override
	public List<PsVoRaw> getValue(Mo targetMo) throws FxNotMatchException, FxTimeoutException, Exception {

		Mo mo = targetMo;
		List<PsVoRaw> psList = new ArrayList<PsVoRaw>();
		PsItem item = PsApi.getApi().getPsItem(PsApi.MO_STATUS_PS_ID);

		MakeTestPsVoDfo dfo = new MakeTestPsVoDfo();
		PsVoRaw value = dfo.testOnOffValue(mo, item, 0.05d);
		psList.add(value);

		return psList;

	}

}