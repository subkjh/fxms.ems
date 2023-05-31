package fxms.ems.bas.vo;

public class EngPsVo {

	public enum PsType {
		pressure, instance, accumulate, temperature
	}

	public final String engId;
	public final String psId;
	public final PsType psType;

	public EngPsVo(String engId, String psId, PsType psType) {
		this.engId = engId;
		this.psId = psId;
		this.psType = psType;
	}
}
