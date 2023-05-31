package fxms.ems.bas.mo;

import fxms.bas.mo.FxMo;
import subkjh.dao.def.FxColumn;

public class SensorMo extends FxMo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1855587370412054092L;

	public static final String MO_CLASS = "SENSOR";

	/** 센서명 */
	private String sensrName;

	/** 센서설명 */
	private String sensrDesc;

	@FxColumn(name = "SENSR_IP_ADDR", size = 39, comment = "센서IP주소")
	private String sensrIpAddr;

	@FxColumn(name = "COMM_PROTC", size = 50, nullable = true, comment = "통신방식")
	private String commProtc;

	@FxColumn(name = "ENG_ID", size = 20, nullable = true, comment = "에너지ID")
	private String engId;

	@FxColumn(name = "PIPE_ID", size = 30, nullable = true, comment = "배관ID")
	private String pipeId;

	@FxColumn(name = "FAC_NO", size = 14, comment = "설비번호", defValue = "0")
	private Long facNo = 0L;

	@FxColumn(name = "PLC_MO_NO", size = 19, nullable = true, comment = "PLCMO번호")
	private Long plcMoNo;

	@FxColumn(name = "MIN_VAL", size = 14, nullable = true, comment = "최소값")
	private Double minVal;

	@FxColumn(name = "MAX_VAL", size = 14, nullable = true, comment = "최대값")
	private Double maxVal;

	@FxColumn(name = "BUY_DATE", size = 8, nullable = true, comment = "구입일")
	private String buyDate;

	@FxColumn(name = "CALIB_DATE", size = 8, nullable = true, comment = "검교정일")
	private String calibDate;

	@FxColumn(name = "MANFT_SN", size = 50, nullable = true, comment = "제조일련번호")
	private String manftSn;

	public SensorMo() {
		setMoClass(MO_CLASS);
	}

	public SensorMo(String sensorName, String sensorType) {
		setMoName(sensorName);
		setMoDispName(sensorName);
		setMoClass(MO_CLASS);
		setSensrName(sensorName);
		setSensrDesc(sensorType);
		setMoType(sensorType);
	}

	/**
	 * 구입일
	 * 
	 * @return 구입일
	 */
	public String getBuyDate() {
		return buyDate;
	}

	/**
	 * 검교정일
	 * 
	 * @return 검교정일
	 */
	public String getCalibDate() {
		return calibDate;
	}

	/**
	 * 통신방식
	 * 
	 * @return 통신방식
	 */
	public String getCommProtc() {
		return commProtc;
	}

	/**
	 * 에너지ID
	 * 
	 * @return 에너지ID
	 */
	public String getEngId() {
		return engId;
	}

	/**
	 * 설비번호
	 * 
	 * @return 설비번호
	 */
	public Long getFacNo() {
		return facNo;
	}

	/**
	 * 제조일련번호
	 * 
	 * @return 제조일련번호
	 */
	public String getManftSn() {
		return manftSn;
	}

	/**
	 * 최대값
	 * 
	 * @return 최대값
	 */
	public Double getMaxVal() {
		return maxVal;
	}

	/**
	 * 최소값
	 * 
	 * @return 최소값
	 */
	public Double getMinVal() {
		return minVal;
	}

	/**
	 * 배관ID
	 * 
	 * @return 배관ID
	 */
	public String getPipeId() {
		return pipeId;
	}

	/**
	 * PLCMO번호
	 * 
	 * @return PLCMO번호
	 */
	public Long getPlcMoNo() {
		return plcMoNo;
	}

	/**
	 * 센서설명
	 * 
	 * @return 센서설명
	 */
	public String getSensrDesc() {
		return sensrDesc;
	}

	/**
	 * 센서IP주소
	 * 
	 * @return 센서IP주소
	 */
	public String getSensrIpAddr() {
		return sensrIpAddr;
	}

	/**
	 * 센서명
	 * 
	 * @return 센서명
	 */
	public String getSensrName() {
		return sensrName;
	}

	/**
	 * 구입일
	 * 
	 * @param buyDate 구입일
	 */
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	/**
	 * 검교정일
	 * 
	 * @param calibDate 검교정일
	 */
	public void setCalibDate(String calibDate) {
		this.calibDate = calibDate;
	}

	/**
	 * 통신방식
	 * 
	 * @param commProtc 통신방식
	 */
	public void setCommProtc(String commProtc) {
		this.commProtc = commProtc;
	}

	/**
	 * 에너지ID
	 * 
	 * @param engId 에너지ID
	 */
	public void setEngId(String engId) {
		this.engId = engId;
	}

	/**
	 * 설비번호
	 * 
	 * @param facNo 설비번호
	 */
	public void setFacNo(Long facNo) {
		this.facNo = facNo;
	}

	/**
	 * 제조일련번호
	 * 
	 * @param manftSn 제조일련번호
	 */
	public void setManftSn(String manftSn) {
		this.manftSn = manftSn;
	}

	/**
	 * 최대값
	 * 
	 * @param maxVal 최대값
	 */
	public void setMaxVal(Double maxVal) {
		this.maxVal = maxVal;
	}

	/**
	 * 최소값
	 * 
	 * @param minVal 최소값
	 */
	public void setMinVal(Double minVal) {
		this.minVal = minVal;
	}

	/**
	 * 배관ID
	 * 
	 * @param pipeId 배관ID
	 */
	public void setPipeId(String pipeId) {
		this.pipeId = pipeId;
	}

	/**
	 * PLCMO번호
	 * 
	 * @param plcMoNo PLCMO번호
	 */
	public void setPlcMoNo(Long plcMoNo) {
		this.plcMoNo = plcMoNo;
	}

	/**
	 * 센서설명
	 * 
	 * @param sensrDescr 센서설명
	 */
	public void setSensrDesc(String sensrDesc) {
		this.sensrDesc = sensrDesc;
	}

	/**
	 * 센서IP주소
	 * 
	 * @param sensrIpAddr 센서IP주소
	 */
	public void setSensrIpAddr(String sensrIpAddr) {
		this.sensrIpAddr = sensrIpAddr;
	}

	/**
	 * 센서명
	 * 
	 * @param sensrName 센서명
	 */
	public void setSensrName(String sensrName) {
		this.sensrName = sensrName;
	}

}
