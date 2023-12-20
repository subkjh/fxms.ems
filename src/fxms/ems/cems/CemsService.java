package fxms.ems.cems;

import fxms.bas.fxo.service.FxService;
import fxms.ems.cems.dto.NodeGwDto;

public interface CemsService extends FxService {

	/**
	 * NodeID가 설정되지 않은 게이트웨이에 대해서 설정한다.
	 * 
	 * @param gw
	 * @throws Exception
	 */
	public void addCrawler(NodeGwDto gw) throws Exception;

}
