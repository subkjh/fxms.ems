package fxms.ems.bas.vo;

/**
 * 수집항목과 에너지 정보를 갖는다.
 * 
 * @author subkjh
 *
 */
public class EngPsVo {

	public enum PsType {
		pressure, instance, accumulate, temperature
	}

	/** 에너지ID */
	public final String engId;
	/** 수집항목ID */
	public final String psId;
	/** 수집데이터유형 */
	public final PsType psType;

	public EngPsVo(String engId, String psId, PsType psType) {
		this.engId = engId;
		this.psId = psId;
		this.psType = psType;
	}
}
