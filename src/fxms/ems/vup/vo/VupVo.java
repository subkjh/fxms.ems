package fxms.ems.vup.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VupVo {

	public static final String COMPLEX_ID = "complex_pid";
	public static final String FACTORY_ID = "factory_pid";
	public static final String DEVICE_ID = "device_pid";

	@SuppressWarnings("unchecked")
	protected void addList(Map<String, Object> me, String name, Map<String, Object> data) {
		Object val = me.get(name);
		List<Map<String, Object>> list;
		if (val == null) {
			list = new ArrayList<Map<String, Object>>();
			me.put(name, list);
		} else {
			list = (List<Map<String, Object>>) val;
		}

		list.add(data);
	}

}