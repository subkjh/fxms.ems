package fxms.ems.vup.dto;

import java.util.List;

public class Alarm01Dto {

	public String factory_id;

	private List<Alarm01Sub1Dto> notifications;

	public List<Alarm01Sub1Dto> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Alarm01Sub1Dto> notifications) {
		this.notifications = notifications;
	}
	
	
}
