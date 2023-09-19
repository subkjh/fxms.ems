package fxms.ems.bas.dbo;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
 * @since 2023.04.27 14:23
 * @author subkjh autometic create by subkjh.dao
 *
 */

@FxTable(name = "FE_ENG_MEASR_RAW", comment = "에너측정원천테이블")
@FxIndex(name = "FE_ENG_MEASR_RAW__PK", type = INDEX_TYPE.PK, columns = { "MEASR_DTM", "MO_NO", "ENG_ID" })
public class FE_ENG_MEASR_RAW {

	public FE_ENG_MEASR_RAW() {
	}

	@FxColumn(name = "MEASR_DTM", size = 14, comment = "측정일시")
	private Long measrDtm;

	@FxColumn(name = "MO_NO", size = 19, comment = "MO번호")
	private Long moNo;

	@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
	private String engId;

	@FxColumn(name = "INST_VAL", size = 14, nullable = true, comment = "순시값")
	private Double instVal;

	@FxColumn(name = "PREV_INTG_VAL", size = 14, nullable = true, comment = "이전적산값")
	private Double prevIntgVal;

	@FxColumn(name = "CUR_INTG_VAL", size = 14, nullable = true, comment = "현재적산값")
	private Double curIntgVal;

	@FxColumn(name = "DIFF_VAL", size = 14, nullable = true, comment = "차이값")
	private Double diffVal;

	@FxColumn(name = "PRES_VAL", size = 14, nullable = true, comment = "압력값")
	private Double presVal;

	@FxColumn(name = "TEMP_VAL", size = 14, nullable = true, comment = "온도값")
	private Double tempVal;

	@FxColumn(name = "MEMO", size = 400, nullable = true, comment = "메모")
	private String memo;

	@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
	private Integer regUserNo = 0;

	@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
	private Long regDtm;

	@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
	private Integer chgUserNo = 0;

	@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
	private Long chgDtm;

	/**
	 * 측정일시
	 * 
	 * @return 측정일시
	 */
	public Long getMeasrDtm() {
		return measrDtm;
	}

	/**
	 * 측정일시
	 * 
	 * @param measrDtm 측정일시
	 */
	public void setMeasrDtm(Long measrDtm) {
		this.measrDtm = measrDtm;
	}

	/**
	 * MO번호
	 * 
	 * @return MO번호
	 */
	public Long getMoNo() {
		return moNo;
	}

	/**
	 * MO번호
	 * 
	 * @param moNo MO번호
	 */
	public void setMoNo(Long moNo) {
		this.moNo = moNo;
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
	 * 순시값
	 * 
	 * @return 순시값
	 */
	public Double getInstVal() {
		return instVal;
	}

	/**
	 * 순시값
	 * 
	 * @param instVal 순시값
	 */
	public void setInstVal(Double instVal) {
		this.instVal = instVal;
	}

	/**
	 * 이전적산값
	 * 
	 * @return 이전적산값
	 */
	public Double getPrevIntgVal() {
		return prevIntgVal;
	}

	/**
	 * 이전적산값
	 * 
	 * @param prevIntgVal 이전적산값
	 */
	public void setPrevIntgVal(Double prevIntgVal) {
		this.prevIntgVal = prevIntgVal;
	}

	/**
	 * 현재적산값
	 * 
	 * @return 현재적산값
	 */
	public Double getCurIntgVal() {
		return curIntgVal;
	}

	/**
	 * 현재적산값
	 * 
	 * @param curIntgVal 현재적산값
	 */
	public void setCurIntgVal(Double curIntgVal) {
		this.curIntgVal = curIntgVal;
	}

	/**
	 * 차이값
	 * 
	 * @return 차이값
	 */
	public Double getDiffVal() {
		return diffVal;
	}

	/**
	 * 차이값
	 * 
	 * @param diffVal 차이값
	 */
	public void setDiffVal(Double diffVal) {
		this.diffVal = diffVal;
	}

	/**
	 * 압력값
	 * 
	 * @return 압력값
	 */
	public Double getPresVal() {
		return presVal;
	}

	/**
	 * 압력값
	 * 
	 * @param presVal 압력값
	 */
	public void setPresVal(Double presVal) {
		this.presVal = presVal;
	}

	/**
	 * 온도값
	 * 
	 * @return 온도값
	 */
	public Double getTempVal() {
		return tempVal;
	}

	/**
	 * 온도값
	 * 
	 * @param tempVal 온도값
	 */
	public void setTempVal(Double tempVal) {
		this.tempVal = tempVal;
	}

	/**
	 * 메모
	 * 
	 * @return 메모
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 메모
	 * 
	 * @param memo 메모
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 등록사용자번호
	 * 
	 * @return 등록사용자번호
	 */
	public Integer getRegUserNo() {
		return regUserNo;
	}

	/**
	 * 등록사용자번호
	 * 
	 * @param regUserNo 등록사용자번호
	 */
	public void setRegUserNo(Integer regUserNo) {
		this.regUserNo = regUserNo;
	}

	/**
	 * 등록일시
	 * 
	 * @return 등록일시
	 */
	public Long getRegDtm() {
		return regDtm;
	}

	/**
	 * 등록일시
	 * 
	 * @param regDtm 등록일시
	 */
	public void setRegDtm(Long regDtm) {
		this.regDtm = regDtm;
	}

	/**
	 * 수정사용자번호
	 * 
	 * @return 수정사용자번호
	 */
	public Integer getChgUserNo() {
		return chgUserNo;
	}

	/**
	 * 수정사용자번호
	 * 
	 * @param chgUserNo 수정사용자번호
	 */
	public void setChgUserNo(Integer chgUserNo) {
		this.chgUserNo = chgUserNo;
	}

	/**
	 * 수정일시
	 * 
	 * @return 수정일시
	 */
	public Long getChgDtm() {
		return chgDtm;
	}

	/**
	 * 수정일시
	 * 
	 * @param chgDtm 수정일시
	 */
	public void setChgDtm(Long chgDtm) {
		this.chgDtm = chgDtm;
	}
}
