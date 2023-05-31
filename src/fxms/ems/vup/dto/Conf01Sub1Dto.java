package fxms.ems.vup.dto;

import java.util.List;

public class Conf01Sub1Dto {

	public String factory_pid;
	public String factory_nm;
	public String factory_desc;
	public Integer use_yn;

	private List<Conf01Sub2Dto> devices;

	public List<Conf01Sub2Dto> getDevices() {
		return devices;
	}

	public void setDevices(List<Conf01Sub2Dto> devices) {
		this.devices = devices;
	}

}
