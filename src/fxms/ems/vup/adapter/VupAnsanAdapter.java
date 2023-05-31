package fxms.ems.vup.adapter;

import java.util.ArrayList;
import java.util.List;

import fxms.bas.exp.FxNotMatchException;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.fxo.adapter.FxGetValueAdapter;
import fxms.bas.mo.FxMo;
import fxms.bas.mo.Mo;
import fxms.bas.vo.PsVoRaw;
import subkjh.bas.co.log.LOG_LEVEL;
import subkjh.bas.co.log.Logger;

/**
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(pollCycle = 300, moJson = "{\"moClass\":\"NODE\",\"moType\":\"PLC\"}", service = "VupService", descr = "안산산단의 전력데이터를 주기적으로 가져온다.")
public class VupAnsanAdapter extends FxGetValueAdapter {

	public static void main(String[] args) {

		Logger.logger.setLevel(LOG_LEVEL.trace);
		VupAnsanAdapter a = new VupAnsanAdapter();
		try {
			FxMo mo = new FxMo();
			mo.setMoNo(101001);
			System.out.println(a.getValue(mo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PsVoRaw> getValue(Mo targetMo) throws FxNotMatchException, FxTimeoutException, Exception {
		Logger.logger.info("TODO");
		List<PsVoRaw> psList = new ArrayList<PsVoRaw>();
		return psList;
	}

}