package fxms.ems.h2.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.MoApi;
import fxms.bas.api.PsApi;
import fxms.bas.exp.FxNotMatchException;
import fxms.bas.exp.FxTimeoutException;
import fxms.bas.exp.MoNotFoundException;
import fxms.bas.exp.PsItemNotFoundException;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.fxo.adapter.FxGetValueAdapter;
import fxms.bas.fxo.thread.FxThread;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.dpo.vo.MakeTestPsVoDfo;
import fxms.bas.mo.Mo;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.PsVoRaw;
import fxms.ems.h2.dao.H2Qid;
import subkjh.bas.co.log.Logger;
import subkjh.dao.QidDaoEx;

/**
 * 테스트 아답터
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(pollCycle = 60, moJson = "{\"moClass\":\"MO\",\"moType\":\"FACILITY\"}", service = "MoService", descr = "테스트 아답터로 주기적으로 임의 데이터를 생성한다.")
public class H2TestAdapter extends FxGetValueAdapter {

	class Data {
		final String moInstance;
		final String psId;

		Data(Object moInstance, Object psId) {
			this.moInstance = moInstance == null ? null : moInstance.toString();
			this.psId = psId.toString();
		}
		
		@Override
		public String toString() {
			return psId + "," + moInstance;
		}
	}

	public static void main(String[] args) throws MoNotFoundException, Exception {
		MoApi.api = new MoApiDfo();
		H2TestAdapter a = new H2TestAdapter();
		Mo mo = MoApi.getApi().getMo(10002);
		System.out.println(FxmsUtil.toJson(a.getValue(mo)));
		
		Thread ths[] = new Thread[Thread.activeCount()];
		Thread.enumerate(ths);
		for (Thread th : ths) {
			System.out.println(th);
			if (th instanceof FxThread) {
				((FxThread) th).stop("AAA");
			}
		}
	}

	@Override
	public List<PsVoRaw> getValue(Mo targetMo) throws FxNotMatchException, FxTimeoutException, Exception {

		Mo mo = targetMo;
		List<PsVoRaw> psList = new ArrayList<PsVoRaw>();
		List<Data> list = getDatas(mo);

		if (list.size() == 0) {
			return psList;
		}

		Logger.logger.debug("{} : {}", mo.getMoName(), list);

		MakeTestPsVoDfo dfo = new MakeTestPsVoDfo();

		for (Data data : list) {
			try {
				PsItem item = PsApi.getApi().getPsItem(data.psId);
				psList.add(dfo.makeTestValue(mo, item));
			} catch (PsItemNotFoundException e) {
				continue;
			}
		}

		return psList;

	}

	@SuppressWarnings("unchecked")
	private List<Data> getDatas(Mo mo) throws Exception {
		H2Qid QID = new H2Qid();
		QidDaoEx dao = QidDaoEx.open(FxCfg.getHome(H2Qid.QUERY_XML_FILE));
		List<Map<String, Object>> datas = dao.selectQid2Res(QID.select_test_mo_psid,
				FxApi.makePara("moTid", mo.getMoTid()));
		dao.close();

		List<Data> ret = new ArrayList<>();
		for (Map<String, Object> data : datas) {
			ret.add(new Data(data.get("moInstance"), data.get("psId")));
		}

		return ret;
	}

}