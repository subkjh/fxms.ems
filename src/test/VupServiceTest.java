package test;

import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.service.FxServiceImpl;
import fxms.ems.vup.VupService;
import fxms.ems.vup.VupServiceImpl;
import subkjh.bas.co.log.LOG_LEVEL;

public class VupServiceTest {
	public static void main(String[] args) {
		FxServiceImpl.start(VupService.class.getSimpleName(), VupServiceImpl.class,
				new String[] { ".", "fxservice.port=0" });
		FxServiceImpl.logger.setLevel(LOG_LEVEL.info);
		
		System.out.println(FxmsUtil.toJson(args));
	}
}
