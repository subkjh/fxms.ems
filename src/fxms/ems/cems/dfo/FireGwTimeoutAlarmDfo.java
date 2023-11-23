package fxms.ems.cems.dfo;

import java.util.List;

import fxms.api.FxApi;
import fxms.api.ao.AlarmApi;
import fxms.api.ao.AlarmApiDfo;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.bas.co.ALARM_CODE;
import fxms.bas.exp.MoNotFoundException;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.mo.Mo;
import fxms.bas.mo.NodeMo;
import fxms.bas.vo.Alarm;
/**
 * 
 * @author subkjh
 *
 */
public class FireGwTimeoutAlarmDfo implements FxDfo<String, Alarm> {

	public static void main(String[] args) {

		MoApi.api = new MoApiDfo();
		AlarmApi.api = new AlarmApiDfo();

		FireGwTimeoutAlarmDfo dfo = new FireGwTimeoutAlarmDfo();
		try {
			System.out.println(dfo.fireAlarm("170.30.2.13"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Alarm call(FxFact fact, String ipAddr) throws Exception {
		return fireAlarm(ipAddr);
	}

	public Alarm fireAlarm(String ipAddr) throws Exception {

		List<Mo> list = MoApi.getApi().getMos(FxApi.makePara("nodeIpAddr", ipAddr, "moClass", NodeMo.MO_CLASS));
		if (list != null && list.size() > 0) {
			List<Alarm> alarms = AlarmApi.getApi().fireAlarms(list.get(0).getMoNo(), null,
					ALARM_CODE.mo_timeout.getAlcdNo());
			return alarms != null && alarms.size() > 0 ? alarms.get(0) : null;
		}

		throw new MoNotFoundException("ipAddr is " + ipAddr);
	}

}
