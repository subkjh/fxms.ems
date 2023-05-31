package fxms.ems.vup.vo;

import java.util.List;

import fxms.bas.vo.PsValue;
import fxms.bas.vo.PsValues;

public class VupPsValues {

	private final String point_pid;

	private final String device_pid;

	private final List<PsValue> values;

	public VupPsValues(String device_pid, String point_pid, PsValues values) {
		this.device_pid = device_pid;
		this.point_pid = point_pid;
		this.values = values.getValues();
	}

	public String getPoint_pid() {
		return point_pid;
	}

	public String getDevice_pid() {
		return device_pid;
	}

	public List<PsValue> getValues() {
		return values;
	}

}
