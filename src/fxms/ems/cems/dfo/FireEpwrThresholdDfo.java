package fxms.ems.cems.dfo;

import java.util.List;

import fxms.api.ao.AlarmApi;
import fxms.api.ao.AlarmApiDfo;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.bas.co.CoCode.ALARM_LEVEL;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dpo.SelectEnergyThresholdDfo;
import fxms.ems.bas.vo.EnergyThresholdVo;
import fxms.ems.cems.CemsApi;
import subkjh.bas.co.log.Logger;

/**
 * 
 * @author subkjh
 *
 */
public class FireEpwrThresholdDfo implements FxDfo<Long, Integer> {

	public static void main(String[] args) {

		MoApi.api = new MoApiDfo();
		AlarmApi.api = new AlarmApiDfo();

		FireEpwrThresholdDfo dfo = new FireEpwrThresholdDfo();
		try {
			System.out.println(dfo.checkThreshold(20231025000000L));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer call(FxFact fact, Long psDtm) throws Exception {
		return checkThreshold(psDtm);
	}

	public int checkThreshold(long psDtm) throws Exception {

		long ptime = System.currentTimeMillis();
		int ret = 0;

		SelectEnergyThresholdDfo dfo = new SelectEnergyThresholdDfo();
		List<EnergyThresholdVo> list = dfo.selectEnergyThreshold(psDtm, "E11", "피크전력계");

		ALARM_LEVEL alarmLevel;
		for (EnergyThresholdVo vo : list) {
			alarmLevel = vo.getAlarmLevel();
			if (alarmLevel != null && vo.getMoNo() != null) {
//				System.out.println(FxmsUtil.toJson(vo) + " : " + alarmLevel);
				AlarmApi.getApi().fireAlarm(vo.getMoNo(), psDtm, CemsApi.ALCD_EPOWER_THRESHOLD, alarmLevel,
						vo.getPeakBaseEpwr() + " : " + vo.getEngUsedAmt(), null);
			}
		}

		Logger.logger.info("CheckEpwrThreshold : {} data(s) executed - {}ms", ret,
				(System.currentTimeMillis() - ptime));

		return ret;

	}

}
