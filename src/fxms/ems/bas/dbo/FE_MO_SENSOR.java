package fxms.ems.bas.dbo;

import java.io.Serializable;

import fxms.bas.impl.dbo.all.FX_MO;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
 * @since 2023.03.15 14:57
 * @author subkjh autometic create by subkjh.dao
 *
 */

@FxTable(name = "FE_MO_SENSOR", comment = "MO센서테이블")
@FxIndex(name = "FE_MO_SNSR__PK", type = INDEX_TYPE.PK, columns = { "MO_NO" })
@FxIndex(name = "FE_MO_SNSR__FK_MO", type = INDEX_TYPE.FK, columns = { "MO_NO" }, fkTable = "FX_MO", fkColumn = "MO_NO")
public class FE_MO_SENSOR extends FX_MO implements Serializable {

	public FE_MO_SENSOR() {
	}

	@FxColumn(name = "SENSR_NAME", size = 100, comment = "센서명")
	private String sensrName;

	@FxColumn(name = "SENSR_DESC", size = 200, nullable = true, comment = "센서설명")
	private String sensrDesc;

	@FxColumn(name = "SENSR_IP_ADDR", size = 39, nullable = true, comment = "센서IP주소")
	private String sensrIpAddr;

	@FxColumn(name = "COMM_PROTC", size = 50, nullable = true, comment = "통신방식")
	private String commProtc;

	@FxColumn(name = "ENG_ID", size = 20, nullable = true, comment = "에너지ID")
	private String engId;

	@FxColumn(name = "PIPE_ID", size = 30, nullable = true, comment = "배관ID")
	private String pipeId;

	@FxColumn(name = "FAC_NO", size = 14, nullable = true, comment = "설비번호", defValue = "0")
	private Long facNo = 0L;

	@FxColumn(name = "PLC_MO_NO", size = 19, nullable = true, comment = "PLCMO번호")
	private Long plcMoNo;

	@FxColumn(name = "MIN_VAL", size = 14, nullable = true, comment = "최소값", defValue = "0")
	private Double minVal = 0D;

	@FxColumn(name = "MAX_VAL", size = 14, nullable = true, comment = "최대값", defValue = "0")
	private Double maxVal = 0D;

	@FxColumn(name = "BUY_DATE", size = 8, nullable = true, comment = "구입일")
	private String buyDate;

	@FxColumn(name = "CALIB_DATE", size = 8, nullable = true, comment = "검교정일")
	private String calibDate;

	@FxColumn(name = "MANFT_SN", size = 50, nullable = true, comment = "제조일련번호")
	private String manftSn;

	/**
	 * 센서명
	 * 
	 * @return 센서명
	 */
	public String getSensrName() {
		return sensrName;
	}

	/**
	 * 센서명
	 * 
	 * @param sensrName 센서명
	 */
	public void setSensrName(String sensrName) {
		this.sensrName = sensrName;
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
	 * 센서설명
	 * 
	 * @param sensrDesc 센서설명
	 */
	public void setSensrDesc(String sensrDesc) {
		this.sensrDesc = sensrDesc;
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
	 * 센서IP주소
	 * 
	 * @param sensrIpAddr 센서IP주소
	 */
	public void setSensrIpAddr(String sensrIpAddr) {
		this.sensrIpAddr = sensrIpAddr;
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
	 * @return 에너지ID
	 */
	public String getEngId() {
		return engId;
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
	 * 배관ID
	 * 
	 * @return 배관ID
	 */
	public String getPipeId() {
		return pipeId;
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
	 * 설비번호
	 * 
	 * @return 설비번호
	 */
	public Long getFacNo() {
		return facNo;
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
	 * PLCMO번호
	 * 
	 * @return PLCMO번호
	 */
	public Long getPlcMoNo() {
		return plcMoNo;
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
	 * 최소값
	 * 
	 * @return 최소값
	 */
	public Double getMinVal() {
		return minVal;
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
	 * 최대값
	 * 
	 * @return 최대값
	 */
	public Double getMaxVal() {
		return maxVal;
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
	 * 구입일
	 * 
	 * @return 구입일
	 */
	public String getBuyDate() {
		return buyDate;
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
	 * @return 검교정일
	 */
	public String getCalibDate() {
		return calibDate;
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
	 * 제조일련번호
	 * 
	 * @return 제조일련번호
	 */
	public String getManftSn() {
		return manftSn;
	}

	/**
	 * 제조일련번호
	 * 
	 * @param manftSn 제조일련번호
	 */
	public void setManftSn(String manftSn) {
		this.manftSn = manftSn;
	}
}
