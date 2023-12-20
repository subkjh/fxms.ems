package test;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.handler.vo.FxResponse;
import fxms.bas.ws.handler.client.FxHttpClient;
import subkjh.bas.co.utils.DateUtil;

public class WebServiceClientTest {

	public static void main(String[] args) {

		FxHttpClient c = new FxHttpClient("localhost", 10005);

		FxResponse response;
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("psKindName", "15M");
		para.put("psId", "MoStatus");
		para.put("startDate", DateUtil.getDtm(System.currentTimeMillis() - 86400000L));
		para.put("endDate", DateUtil.getDtm());

		try {

			c.login("h2", "1111");

			response = c.call("value/get-values", para);

			System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
