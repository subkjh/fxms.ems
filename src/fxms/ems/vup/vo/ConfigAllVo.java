package fxms.ems.vup.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigAllVo extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1600851215147275914L;

	

	
	

	@SuppressWarnings("unchecked")
	public void addComplex(ComplexVo complex) {
		Object val = this.get("complex_list");
		List<Map<String, Object>> list;
		if (val == null) {
			list = new ArrayList<Map<String, Object>>();
			this.put("complex_list", list);
		} else {
			list = (List<Map<String, Object>>) val;
		}

		list.add(complex.getComplex());
	}

	public void addEnergy(List<Map<String, Object>> engeryList) {
		this.put("energy_list", engeryList);
	}
}
