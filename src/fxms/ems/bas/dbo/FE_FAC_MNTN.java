package fxms.ems.bas.dbo;


import java.io.Serializable;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.16 13:55
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_FAC_MNTN", comment = "설비유지보수테이블")
@FxIndex(name = "FE_FAC_MNTN__PK", type = INDEX_TYPE.PK, columns = {"MNTN_NO"})
@FxIndex(name = "FE_FAC_MNTN__KEY1", type = INDEX_TYPE.KEY, columns = {"FAC_NO"})
@FxIndex(name = "FE_FAC_MNTN__KEY2", type = INDEX_TYPE.KEY, columns = {"MNTN_RESV_DATE"})
@FxIndex(name = "FE_FAC_MNTN__FK1", type = INDEX_TYPE.FK, columns = {"FAC_NO"}, fkTable = "FE_FAC_BAS", fkColumn = "FAC_NO")
public class FE_FAC_MNTN implements Serializable {

public FE_FAC_MNTN() {
 }

public static final String FX_SEQ_MNTN_NO  = "FX_SEQ_MNTN_NO"; 
@FxColumn(name = "MNTN_NO", size = 14, comment = "유지보수번호", sequence = "FX_SEQ_MNTN_NO")
private Long mntnNo;


@FxColumn(name = "MNTN_TITLE", size = 100, nullable = true, comment = "유지보수제목")
private String mntnTitle;


@FxColumn(name = "FAC_NO", size = 14, nullable = true, comment = "설비번호")
private Long facNo;


@FxColumn(name = "FAC_TID", size = 50, nullable = true, comment = "설비TID")
private String facTid;


@FxColumn(name = "MNTN_RESV_DATE", size = 8, comment = "유지보수예약일자")
private String mntnResvDate;


@FxColumn(name = "MNTN_STRT_DATE", size = 8, comment = "유지보수시작일자")
private String mntnStrtDate;


@FxColumn(name = "MNTN_FNSH_DATE", size = 8, nullable = true, comment = "유지보수종료일자")
private String mntnFnshDate;


@FxColumn(name = "MNTN_CL_CD", size = 2, comment = "유지보수구분코드")
private String mntnClCd;


@FxColumn(name = "MNTN_TYPE_CD", size = 2, comment = "유지보수유형코드")
private String mntnTypeCd;


@FxColumn(name = "MNTN_CNTC_NAME", size = 100, nullable = true, comment = "유지보수연락처명")
private String mntnCntcName;


@FxColumn(name = "MNTN_CNTC_INFO", size = 100, nullable = true, comment = "유지보수연락처정보")
private String mntnCntcInfo;


@FxColumn(name = "MNTN_RESV_CNTS", size = 200, nullable = true, comment = "유지보수예정내용")
private String mntnResvCnts;


@FxColumn(name = "MNTN_CNTS", size = 200, nullable = true, comment = "유지보수내용")
private String mntnCnts;


@FxColumn(name = "MNTN_FNSH_YN", size = 1, comment = "유지보수종료여부", defValue = "0")
private String mntnFnshYn = "0";


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


/**
* 유지보수번호
* @return 유지보수번호
*/
public Long getMntnNo() {
return mntnNo;
}
/**
* 유지보수번호
*@param mntnNo 유지보수번호
*/
public void setMntnNo(Long mntnNo) {
	this.mntnNo = mntnNo;
}
/**
* 유지보수제목
* @return 유지보수제목
*/
public String getMntnTitle() {
return mntnTitle;
}
/**
* 유지보수제목
*@param mntnTitle 유지보수제목
*/
public void setMntnTitle(String mntnTitle) {
	this.mntnTitle = mntnTitle;
}
/**
* 설비번호
* @return 설비번호
*/
public Long getFacNo() {
return facNo;
}
/**
* 설비번호
*@param facNo 설비번호
*/
public void setFacNo(Long facNo) {
	this.facNo = facNo;
}
/**
* 설비TID
* @return 설비TID
*/
public String getFacTid() {
return facTid;
}
/**
* 설비TID
*@param facTid 설비TID
*/
public void setFacTid(String facTid) {
	this.facTid = facTid;
}
/**
* 유지보수예약일자
* @return 유지보수예약일자
*/
public String getMntnResvDate() {
return mntnResvDate;
}
/**
* 유지보수예약일자
*@param mntnResvDate 유지보수예약일자
*/
public void setMntnResvDate(String mntnResvDate) {
	this.mntnResvDate = mntnResvDate;
}
/**
* 유지보수시작일자
* @return 유지보수시작일자
*/
public String getMntnStrtDate() {
return mntnStrtDate;
}
/**
* 유지보수시작일자
*@param mntnStrtDate 유지보수시작일자
*/
public void setMntnStrtDate(String mntnStrtDate) {
	this.mntnStrtDate = mntnStrtDate;
}
/**
* 유지보수종료일자
* @return 유지보수종료일자
*/
public String getMntnFnshDate() {
return mntnFnshDate;
}
/**
* 유지보수종료일자
*@param mntnFnshDate 유지보수종료일자
*/
public void setMntnFnshDate(String mntnFnshDate) {
	this.mntnFnshDate = mntnFnshDate;
}
/**
* 유지보수구분코드
* @return 유지보수구분코드
*/
public String getMntnClCd() {
return mntnClCd;
}
/**
* 유지보수구분코드
*@param mntnClCd 유지보수구분코드
*/
public void setMntnClCd(String mntnClCd) {
	this.mntnClCd = mntnClCd;
}
/**
* 유지보수유형코드
* @return 유지보수유형코드
*/
public String getMntnTypeCd() {
return mntnTypeCd;
}
/**
* 유지보수유형코드
*@param mntnTypeCd 유지보수유형코드
*/
public void setMntnTypeCd(String mntnTypeCd) {
	this.mntnTypeCd = mntnTypeCd;
}
/**
* 유지보수연락처명
* @return 유지보수연락처명
*/
public String getMntnCntcName() {
return mntnCntcName;
}
/**
* 유지보수연락처명
*@param mntnCntcName 유지보수연락처명
*/
public void setMntnCntcName(String mntnCntcName) {
	this.mntnCntcName = mntnCntcName;
}
/**
* 유지보수연락처정보
* @return 유지보수연락처정보
*/
public String getMntnCntcInfo() {
return mntnCntcInfo;
}
/**
* 유지보수연락처정보
*@param mntnCntcInfo 유지보수연락처정보
*/
public void setMntnCntcInfo(String mntnCntcInfo) {
	this.mntnCntcInfo = mntnCntcInfo;
}
/**
* 유지보수예정내용
* @return 유지보수예정내용
*/
public String getMntnResvCnts() {
return mntnResvCnts;
}
/**
* 유지보수예정내용
*@param mntnResvCnts 유지보수예정내용
*/
public void setMntnResvCnts(String mntnResvCnts) {
	this.mntnResvCnts = mntnResvCnts;
}
/**
* 유지보수내용
* @return 유지보수내용
*/
public String getMntnCnts() {
return mntnCnts;
}
/**
* 유지보수내용
*@param mntnCnts 유지보수내용
*/
public void setMntnCnts(String mntnCnts) {
	this.mntnCnts = mntnCnts;
}
/**
* 유지보수종료여부
* @return 유지보수종료여부
*/
public String isMntnFnshYn() {
return mntnFnshYn;
}
/**
* 유지보수종료여부
*@param mntnFnshYn 유지보수종료여부
*/
public void setMntnFnshYn(String mntnFnshYn) {
	this.mntnFnshYn = mntnFnshYn;
}
/**
* 등록사용자번호
* @return 등록사용자번호
*/
public Integer getRegUserNo() {
return regUserNo;
}
/**
* 등록사용자번호
*@param regUserNo 등록사용자번호
*/
public void setRegUserNo(Integer regUserNo) {
	this.regUserNo = regUserNo;
}
/**
* 등록일시
* @return 등록일시
*/
public Long getRegDtm() {
return regDtm;
}
/**
* 등록일시
*@param regDtm 등록일시
*/
public void setRegDtm(Long regDtm) {
	this.regDtm = regDtm;
}
/**
* 수정사용자번호
* @return 수정사용자번호
*/
public Integer getChgUserNo() {
return chgUserNo;
}
/**
* 수정사용자번호
*@param chgUserNo 수정사용자번호
*/
public void setChgUserNo(Integer chgUserNo) {
	this.chgUserNo = chgUserNo;
}
/**
* 수정일시
* @return 수정일시
*/
public Long getChgDtm() {
return chgDtm;
}
/**
* 수정일시
*@param chgDtm 수정일시
*/
public void setChgDtm(Long chgDtm) {
	this.chgDtm = chgDtm;
}
}
