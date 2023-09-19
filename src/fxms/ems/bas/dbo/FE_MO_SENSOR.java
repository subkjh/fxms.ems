package fxms.ems.bas.dbo;

import fxms.bas.impl.dbo.all.FX_MO;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
 * @since 2023.08.03 16:47
 * @author subkjh autometic create by subkjh.dao
 *
 */

@FxTable(name = "FE_MO_SENSOR", comment = "MO센서테이블")
@FxIndex(name = "FE_MO_SNSR__PK", type = INDEX_TYPE.PK, columns = { "MO_NO" })
@FxIndex(name = "FE_MO_SNSR__FK_MO", type = INDEX_TYPE.FK, columns = { "MO_NO" }, fkTable = "FX_MO", fkColumn = "MO_NO")
public class FE_MO_SENSOR extends FX_MO {

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

	@FxColumn(name = "PLC_MO_NO", size = 19, nullable = true, comment = "PLC_MO번호")
	private Long plcMoNo;

	@FxColumn(name = "MIN_VAL", size = 14, nullable = true, comment = "측정최소값")
	private Double minVal;

	@FxColumn(name = "MAX_VAL", size = 14, nullable = true, comment = "측정최대값")
	private Double maxVal;

	@FxColumn(name = "BUY_DATE", size = 8, nullable = true, comment = "구입일")
	private String buyDate;

	@FxColumn(name = "CALIB_DATE", size = 8, nullable = true, comment = "검교정일")
	private String calibDate;

	@FxColumn(name = "MANFT_SN", size = 50, nullable = true, comment = "제조일련번호")
	private String manftSn;

	@FxColumn(name = "IN_OUT_TAG", size = 4, nullable = true, comment = "입출력태그", defValue = "NONE")
	private String inOutTag = "NONE";

	@FxColumn(name = "OP_ST_VAL", size = 3, nullable = true, comment = "운영상태값")
	private Integer opStVal;

	@FxColumn(name = "OP_ST_VAL_CHG_DTM", size = 14, nullable = true, comment = "운영상태값변경일시")
	private Long opStValChgDtm;

	/**
	 * @return 센서명
	 */
	public String getSensrName() {
		return sensrName;
	}

	/**
	 * @param sensrName 센서명
	 */
	public void setSensrName(String sensrName) {
		this.sensrName = sensrName;
	}

	/**
	 * @return 센서설명
	 */
	public String getSensrDesc() {
		return sensrDesc;
	}

	/**
	 * @param sensrDesc 센서설명
	 */
	public void setSensrDesc(String sensrDesc) {
		this.sensrDesc = sensrDesc;
	}

	/**
	 * @return 센서IP주소
	 */
	public String getSensrIpAddr() {
		return sensrIpAddr;
	}

	/**
	 * @param sensrIpAddr 센서IP주소
	 */
	public void setSensrIpAddr(String sensrIpAddr) {
		this.sensrIpAddr = sensrIpAddr;
	}

	/**
	 * @return 통신방식
	 */
	public String getCommProtc() {
		return commProtc;
	}

	/**
	 * @param commProtc 통신방식
	 */
	public void setCommProtc(String commProtc) {
		this.commProtc = commProtc;
	}

	/**
	 * @return 에너지ID
	 */
	public String getEngId() {
		return engId;
	}

	/**
	 * @param engId 에너지ID
	 */
	public void setEngId(String engId) {
		this.engId = engId;
	}

	/**
	 * @return 배관ID
	 */
	public String getPipeId() {
		return pipeId;
	}

	/**
	 * @param pipeId 배관ID
	 */
	public void setPipeId(String pipeId) {
		this.pipeId = pipeId;
	}

	/**
	 * @return 설비번호
	 */
	public Long getFacNo() {
		return facNo;
	}

	/**
	 * @param facNo 설비번호
	 */
	public void setFacNo(Long facNo) {
		this.facNo = facNo;
	}

	/**
	 * @return PLC_MO번호
	 */
	public Long getPlcMoNo() {
		return plcMoNo;
	}

	/**
	 * @param plcMoNo PLC_MO번호
	 */
	public void setPlcMoNo(Long plcMoNo) {
		this.plcMoNo = plcMoNo;
	}

	/**
	 * @return 측정최소값
	 */
	public Double getMinVal() {
		return minVal;
	}

	/**
	 * @param minVal 측정최소값
	 */
	public void setMinVal(Double minVal) {
		this.minVal = minVal;
	}

	/**
	 * @return 측정최대값
	 */
	public Double getMaxVal() {
		return maxVal;
	}

	/**
	 * @param maxVal 측정최대값
	 */
	public void setMaxVal(Double maxVal) {
		this.maxVal = maxVal;
	}

	/**
	 * @return 구입일
	 */
	public String getBuyDate() {
		return buyDate;
	}

	/**
	 * @param buyDate 구입일
	 */
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	/**
	 * @return 검교정일
	 */
	public String getCalibDate() {
		return calibDate;
	}

	/**
	 * @param calibDate 검교정일
	 */
	public void setCalibDate(String calibDate) {
		this.calibDate = calibDate;
	}

	/**
	 * @return 제조일련번호
	 */
	public String getManftSn() {
		return manftSn;
	}

	/**
	 * @param manftSn 제조일련번호
	 */
	public void setManftSn(String manftSn) {
		this.manftSn = manftSn;
	}

	/**
	 * @return 입출력태그
	 */
	public String getInOutTag() {
		return inOutTag;
	}

	/**
	 * @param inOutTag 입출력태그
	 */
	public void setInOutTag(String inOutTag) {
		this.inOutTag = inOutTag;
	}

	/**
	 * @return 운영상태값
	 */
	public Integer getOpStVal() {
		return opStVal;
	}

	/**
	 * @param opStVal 운영상태값
	 */
	public void setOpStVal(Integer opStVal) {
		this.opStVal = opStVal;
	}

	/**
	 * @return 운영상태값변경일시
	 */
	public Long getOpStValChgDtm() {
		return opStValChgDtm;
	}

	/**
	 * @param opStValChgDtm 운영상태값변경일시
	 */
	public void setOpStValChgDtm(Long opStValChgDtm) {
		this.opStValChgDtm = opStValChgDtm;
	}
}
