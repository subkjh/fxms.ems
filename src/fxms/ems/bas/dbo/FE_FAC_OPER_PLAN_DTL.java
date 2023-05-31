package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.04.27 17:47
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_FAC_OPER_PLAN_DTL", comment = "공정설비운영계획상세테이블")
@FxIndex(name = "FE_FAC_OPER_PLAN_DTL__PK", type = INDEX_TYPE.PK, columns = {"FAC_NO", "DOW_CD"})
@FxIndex(name = "FE_FAC_OPER_PLAN_DTL__FK1", type = INDEX_TYPE.FK, columns = {"FAC_NO"}, fkTable = "FE_FAC_BAS", fkColumn = "FAC_NO")
public class FE_FAC_OPER_PLAN_DTL  {

public FE_FAC_OPER_PLAN_DTL() {
 }

@FxColumn(name = "FAC_NO", size = 14, comment = "설비번호")
private Long facNo;


@FxColumn(name = "DOW_CD", size = 4, comment = "요일코드")
private String dowCd;


@FxColumn(name = "OPER_TIME_SERIES", size = 24, comment = "운영시간시리즈", defValue = "000000001110111110000000")
private String operTimeSeries = "000000001110111110000000";


@FxColumn(name = "OPER_HOURS", size = 2, comment = "운영시간", defValue = "8")
private Integer operHours = 8;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


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
* 요일코드
* @return 요일코드
*/
public String getDowCd() {
return dowCd;
}
/**
* 요일코드
*@param dowCd 요일코드
*/
public void setDowCd(String dowCd) {
	this.dowCd = dowCd;
}
/**
* 운영시간시리즈
* @return 운영시간시리즈
*/
public String getOperTimeSeries() {
return operTimeSeries;
}
/**
* 운영시간시리즈
*@param operTimeSeries 운영시간시리즈
*/
public void setOperTimeSeries(String operTimeSeries) {
	this.operTimeSeries = operTimeSeries;
}
/**
* 운영시간
* @return 운영시간
*/
public Integer getOperHours() {
return operHours;
}
/**
* 운영시간
*@param operHours 운영시간
*/
public void setOperHours(Integer operHours) {
	this.operHours = operHours;
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
