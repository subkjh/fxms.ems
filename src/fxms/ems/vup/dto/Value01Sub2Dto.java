package fxms.ems.vup.dto;

import java.util.List;

public class Value01Sub2Dto {

	public String device_pid;
	public int device_state;
	public String collected_dt;
	public List<Value01Sub3Dto> data;

	public List<Value01Sub3Dto> getData() {
		return data;
	}

	public void setData(List<Value01Sub3Dto> data) {
		this.data = data;
	}

}
