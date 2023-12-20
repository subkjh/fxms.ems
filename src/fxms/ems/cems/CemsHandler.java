package fxms.ems.cems;

import java.util.List;

import fxms.api.FxApi;
import fxms.bas.handler.BaseHandler;
import fxms.bas.handler.MethodDescr;
import fxms.bas.handler.vo.SessionVo;
import fxms.ems.cems.dfo.CheckStateGatewayDfo;
import fxms.ems.cems.dfo.InsertFacRawDfo;
import fxms.ems.cems.dfo.SelectGwsDfo;
import fxms.ems.cems.dfo.SelectSensorsDfo;
import fxms.ems.cems.dto.ComplexDto;
import fxms.ems.cems.dto.FacRawListDto;
import fxms.ems.cems.dto.GwDto;
import fxms.ems.cems.dto.GwPollingDto;
import fxms.ems.cems.dto.NodeGwDto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;

/**
 * 
 * @author subkjh
 *
 */
public class CemsHandler extends BaseHandler {

	public CemsHandler() {
	}

	@MethodDescr(name = "OPCUA-GW-LIST-API) 산단의 GW목록 조회", description = "산단의 게이트웨이 목록을 조회한다.")
	public Object getGwList(SessionVo session, ComplexDto dto) throws Exception {
		return new SelectGwsDfo().select(dto.complex);
	}

	@MethodDescr(name = "OPCUA-NODE-LIST-API) 산단의 센서목록 조회", description = "산단의 센서 목록을 조회한다.")
	public Object getNodeList(SessionVo session, ComplexDto dto) throws Exception {

		List<NodeGwDto> nodes = new SelectSensorsDfo().call(null, dto.complex);

		// nodeId가 설정되어 있지 않으면 요청한다.
		for (NodeGwDto n : nodes) {
			if (n.hasNullNodeId) {
				try {
					CemsService service = FxApi.getApi().getService(CemsService.class);
					service.addCrawler(n);
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			}
		}

		return nodes;
	}

	@MethodDescr(name = "OPCUA-NOTIFY-TIMEOUT-GW-API) GW 타임아웃", description = "FEMS 연동중에 GW가 응답이 없으면 호출한다."
			+ "이 경우 알람을 발생하며 알람을 30분 후 자동 해제 되므로 GW가 응답이 있어도 호출할 필요는 없다.")
	public Object notifyTimeoutGw(SessionVo session, GwDto dto) throws Exception {
		return new CheckStateGatewayDfo().call(null, dto);
	}

	@MethodDescr(name = "OPCUA-NOTIFY-POLLING-API) 한 주기의 폴링 종료 통보", description = "1분 주기로 폴링이 종료됨을 알림")
	public Object notifyPolling(SessionVo session, GwPollingDto dto) throws Exception {
		FxApi.getApi().logProc("cems:polling", dto.getComplex() + ":" + dto.getDatetime(),
				DateUtil.toMstime(dto.getDatetime()), dto.isOk(), dto.getProcCnt(), dto.getMessage());
		return dto;
	}

	@MethodDescr(name = "CEMS-CONF-API-001) 설비목록 등록", description = "설비 목록을 등록한다.")
	public Object addFacility(SessionVo session, FacRawListDto dto) throws Exception {
		return new InsertFacRawDfo().call(null, dto);
	}

	@Override
	protected QidDao getQidDao() throws Exception {
		return null;
	}

}
