package fxms.ems.cems.dfo;

import fxms.api.FxApi;
import fxms.bas.impl.dbo.all.FX_MO;
import fxms.bas.impl.dbo.all.FX_MO_NODE;
import fxms.bas.impl.dpo.FxDpo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.cems.dto.ReqGwDto;
import subkjh.dao.ClassDaoEx;

/**
 * CEMS 게이트웨이로부터 관리하는 노드 목록을 가져와 이미 등록되어 있는 경우 nodeId, namespaceIndex를 업데이트하고
 * 등록되어 있지 않으면 TO_CHECK 및 알람으로 통보한다.
 * 
 * @author subkjh
 *
 */
public class CallCrawlerDpo implements FxDpo<Long, Integer> {

	@Override
	public Integer run(FxFact fact, Long moNo) throws Exception {

		FX_MO_NODE node = ClassDaoEx.SelectData(FX_MO_NODE.class, FxApi.makePara("moNo", moNo, "delYn", "N"));
		FX_MO nodemo = ClassDaoEx.SelectData(FX_MO.class, FxApi.makePara("moNo", moNo, "delYn", "N"));
		if (node != null) {
			ReqGwDto dto = new ReqGwDto();
			dto.commPortNo = node.getCommPortNo();
			dto.nodeIpAddr = node.getNodeIpAddr();
			dto.complex = nodemo.getMoTid().split("\\.")[0];

			return new CallGwNodes2Dfo().call(fact, dto);
		}

		return -1;

	}
}
