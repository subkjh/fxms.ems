package test;

import fxms.api.FxApi;
import fxms.api.fo.dfo.code.InitLangDpo;
import fxms.api.uo.UserApi;
import fxms.api.uo.UserApiDfo;
import fxms.api.uo.UserService;
import fxms.bas.fxo.service.FxServiceImpl;
import fxms.bas.fxo.service.WebService;
import fxms.bas.fxo.service.WebServiceImpl;

public class WebServiceTest {

	public static void main(String[] args) {

		new InitLangDpo().initLang();

		UserApi.api = new UserApiDfo();

		FxApi.getApi().setServiceUrl(UserService.class, "rmi://10.0.1.11:63810/" + UserService.class.getSimpleName());

		// ServiceApi.getApi().setServiceUrl(ValueService.class,
		// "rmi://10.0.1.11:63810/" + ValueService.class.getSimpleName());

		FxServiceImpl.start(WebService.class.getSimpleName(), WebServiceImpl.class,
				new String[] { ".", "fxservice.port=0" });
	}
}
