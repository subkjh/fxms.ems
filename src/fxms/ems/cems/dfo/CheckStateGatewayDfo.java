package fxms.ems.cems.dfo;

import java.util.List;

import fxms.api.FxApi;
import fxms.api.ao.AlarmApi;
import fxms.api.mo.MoApi;
import fxms.bas.co.ALARM_CODE;
import fxms.bas.exp.MoNotFoundException;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.mo.Mo;
import fxms.bas.mo.NodeMo;
import fxms.bas.vo.Alarm;
import fxms.ems.cems.dto.GwDto;

/**
 * 게이트웨이 상태에 대한 알람을 확인한다.
 * 
 * @author subkjh
 *
 */
public class CheckStateGatewayDfo implements FxDfo<GwDto, Alarm> {

	@Override
	public Alarm call(FxFact fact, GwDto dto) throws Exception {

		if (dto.state != 1) {

			Mo mo = null;

			if (dto.moNo > 0) {
				mo = MoApi.getApi().getMo(dto.moNo);
			} else {
				List<Mo> list = MoApi.getApi()
						.getMos(FxApi.makePara("nodeIpAddr", dto.nodeIpAddr, "moClass", NodeMo.MO_CLASS));

				if (list != null && list.size() > 0) {
					mo = list.get(0);
				}

				if (mo == null)
					throw new MoNotFoundException("ipAddr is " + dto.nodeIpAddr);

			}

			List<Alarm> alarms;
			if (dto.state == 2) {
				alarms = AlarmApi.getApi().fireAlarms(mo.getMoNo(), null, ALARM_CODE.mo_timeout.getAlcdNo());
			} else {
				alarms = AlarmApi.getApi().fireAlarms(mo.getMoNo(), null, ALARM_CODE.value_not_collected.getAlcdNo());
			}
			return alarms != null && alarms.size() > 0 ? alarms.get(0) : null;

		} else {

			AlarmApi.getApi().clearAlarms(dto.moNo, null, ALARM_CODE.mo_timeout.getAlcdNo(),
					ALARM_CODE.value_not_collected.getAlcdNo());

			return null;
		}

	}

}
