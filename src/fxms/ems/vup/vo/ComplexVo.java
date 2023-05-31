package fxms.ems.vup.vo;

import java.util.Map;

public class ComplexVo extends VupVo {

	private Map<String, Object> complex;

	public ComplexVo(Map<String, Object> complex) {
		this.complex = complex;
	}

	public void addPipe(Map<String, Object> pipe) {
		this.addList(this.complex, "pipe_list", pipe);
	}

	public void addPlant(Map<String, Object> plant) {
		this.addList(this.complex, "factory_list", plant);
	}

	public Map<String, Object> getComplex() {
		return complex;
	}

	public String getComplexId() {
		return this.complex.get("complex_pid").toString();
	}

}
