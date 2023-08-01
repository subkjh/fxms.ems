package fxms.ems.bas.dbo;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
 * @since 2023.04.28 16:49
 * @author subkjh autometic create by subkjh.dao
 *
 */

@FxTable(name = "FE_ENG_TRANS_RT", comment = "에너지거래라우팅테이블")
@FxIndex(name = "FE_ENG_TRANS_RT__PK", type = INDEX_TYPE.PK, columns = { "TRNS_NO", "RT_STRT_DTM" })
@FxIndex(name = "FE_ENG_TRANS_RT__KEY1", type = INDEX_TYPE.KEY, columns = { "TRNS_NO", "ENG_RT_USE_YN" })
@FxIndex(name = "FE_ENG_TRANS_RT__FK1", type = INDEX_TYPE.FK, columns = {
		"ENG_RT_ID" }, fkTable = "FE_ENG_RT_BAS", fkColumn = "ENG_RT_ID")
@FxIndex(name = "FE_ENG_TRANS_RT__FK2", type = INDEX_TYPE.FK, columns = {
		"TRNS_NO" }, fkTable = "FE_ENG_TRANS_BAS", fkColumn = "TRNS_NO")
public class FE_ENG_TRANS_RT {

	public FE_ENG_TRANS_RT() {
	}

	@FxColumn(name = "TRNS_NO", size = 14, comment = "거래번호")
	private Long trnsNo;

	@FxColumn(name = "RT_STRT_DTM", size = 14, comment = "라우팅시작일시")
	private Long rtStrtDtm;

	@FxColumn(name = "RT_FNSH_DTM", size = 14, comment = "라우팅종료일시")
	private Long rtFnshDtm = 99991231000000L;

	@FxColumn(name = "ENG_RT_ID", size = 30, comment = "에너지루트ID")
	private String engRtId;

	@FxColumn(name = "ENG_RT_USE_YN", size = 1, comment = "에너지루트사용여부", defValue = "Y")
	private String engRtUseYn = "Y";

	@FxColumn(name = "RT_MEMO", size = 400, nullable = true, comment = "라우팅메모")
	private String rtMemo;

	@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
	private Integer regUserNo = 0;

	@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
	private Long regDtm;

	@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
	private Integer chgUserNo = 0;

	@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
	private Long chgDtm;

	/**
	 * 거래번호
	 * 
	 * @return 거래번호
	 */
	public Long getTrnsNo() {
		return trnsNo;
	}

	/**
	 * 거래번호
	 * 
	 * @param trnsNo 거래번호
	 */
	public void setTrnsNo(Long trnsNo) {
		this.trnsNo = trnsNo;
	}

	/**
	 * 라우팅시작일시
	 * 
	 * @return 라우팅시작일시
	 */
	public Long getRtStrtDtm() {
		return rtStrtDtm;
	}

	/**
	 * 라우팅시작일시
	 * 
	 * @param rtStrtDtm 라우팅시작일시
	 */
	public void setRtStrtDtm(Long rtStrtDtm) {
		this.rtStrtDtm = rtStrtDtm;
	}

	/**
	 * 에너지루트ID
	 * 
	 * @return 에너지루트ID
	 */
	public String getEngRtId() {
		return engRtId;
	}

	/**
	 * 에너지루트ID
	 * 
	 * @param engRtId 에너지루트ID
	 */
	public void setEngRtId(String engRtId) {
		this.engRtId = engRtId;
	}

	/**
	 * 에너지루트사용여부
	 * 
	 * @return 에너지루트사용여부
	 */
	public String isEngRtUseYn() {
		return engRtUseYn;
	}

	/**
	 * 에너지루트사용여부
	 * 
	 * @param engRtUseYn 에너지루트사용여부
	 */
	public void setEngRtUseYn(String engRtUseYn) {
		this.engRtUseYn = engRtUseYn;
	}

	/**
	 * 라우팅메모
	 * 
	 * @return 라우팅메모
	 */
	public String getRtMemo() {
		return rtMemo;
	}

	/**
	 * 라우팅메모
	 * 
	 * @param rtMemo 라우팅메모
	 */
	public void setRtMemo(String rtMemo) {
		this.rtMemo = rtMemo;
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

	public Long getRtFnshDtm() {
		return rtFnshDtm;
	}

	public void setRtFnshDtm(Long rtFnshDtm) {
		this.rtFnshDtm = rtFnshDtm;
	}

}
