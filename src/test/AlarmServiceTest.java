package test;

import fxms.api.ao.AlarmService;
import fxms.api.ao.AlarmServiceImpl;
import fxms.bas.fxo.service.FxServiceImpl;

public class AlarmServiceTest {
	public static void main(String[] args) {
		FxServiceImpl.start(AlarmService.class.getSimpleName(), AlarmServiceImpl.class,
				new String[] { ".", "fxservice.port=0" });
	}
}
