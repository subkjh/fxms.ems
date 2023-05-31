package fxms.ems.vup.dto;

import java.util.List;

public class Conf01Sub2Dto {

	public String device_pid;

	public String device_group;
	public String device_nm;
	public String device_desc;
	public String device_loc;
	public String device_tag;
	public String model;
	public String model_no;
	public String manufacturer;
	public String specification;
	public String conn_protocol;
	public String conn_adr;
	public String conn_port;
	public Integer interval;
	public Integer use_yn;

	private List<Conf01Sub3Dto> points;
	
	public List<Conf01Sub3Dto> getPoints() {
		return points;
	}
	public void setPoints(List<Conf01Sub3Dto> points) {
		this.points = points;
	}
	
	
}
