package fxms.ems.vup.dto;

import java.util.List;

public class Value01Sub1Dto {

	public String factory_pid;

	public List<Value01Sub2Dto> devices;

	public List<Value01Sub2Dto> getDevices() {
		return devices;
	}

	public void setDevices(List<Value01Sub2Dto> devices) {
		this.devices = devices;
	}

}
