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


@FxTable(name = "FE_FAC_OPER_PLAN", comment = "공정설비운영계획테이블")
@FxIndex(name = "FE_FAC_OPER_PLAN__PK", type = INDEX_TYPE.PK, columns = {"FAC_NO"})
@FxIndex(name = "FE_FAC_OPER_PLAN__FK1", type = INDEX_TYPE.FK, columns = {"FAC_NO"}, fkTable = "FE_FAC_BAS", fkColumn = "FAC_NO")
public class FE_FAC_OPER_PLAN  {

public FE_FAC_OPER_PLAN() {
 }

@FxColumn(name = "FAC_NO", size = 14, comment = "설비번호")
private Long facNo;


@FxColumn(name = "IN_PEOPLE_NUM", size = 5, nullable = true, comment = "투입인원수", defValue = "0")
private Integer inPeopleNum = 0;


@FxColumn(name = "OPER_STRT_DATE", size = 8, nullable = true, comment = "운영시작일자")
private String operStrtDate;


@FxColumn(name = "OPER_FNSH_DATE", size = 8, nullable = true, comment = "운용종료일자", defValue = "99999999")
private String operFnshDate = "99999999";


@FxColumn(name = "DAILY_OPER_YN", size = 1, comment = "매일가동여부", defValue = "N")
private String dailyOperYn = "N";


@FxColumn(name = "OPER_PLAN_DESCR", size = 400, nullable = true, comment = "운용계획설명")
private String operPlanDescr;


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
* 투입인원수
* @return 투입인원수
*/
public Integer getInPeopleNum() {
return inPeopleNum;
}
/**
* 투입인원수
*@param inPeopleNum 투입인원수
*/
public void setInPeopleNum(Integer inPeopleNum) {
	this.inPeopleNum = inPeopleNum;
}
/**
* 운영시작일자
* @return 운영시작일자
*/
public String getOperStrtDate() {
return operStrtDate;
}
/**
* 운영시작일자
*@param operStrtDate 운영시작일자
*/
public void setOperStrtDate(String operStrtDate) {
	this.operStrtDate = operStrtDate;
}
/**
* 운용종료일자
* @return 운용종료일자
*/
public String getOperFnshDate() {
return operFnshDate;
}
/**
* 운용종료일자
*@param operFnshDate 운용종료일자
*/
public void setOperFnshDate(String operFnshDate) {
	this.operFnshDate = operFnshDate;
}
/**
* 매일가동여부
* @return 매일가동여부
*/
public String isDailyOperYn() {
return dailyOperYn;
}
/**
* 매일가동여부
*@param dailyOperYn 매일가동여부
*/
public void setDailyOperYn(String dailyOperYn) {
	this.dailyOperYn = dailyOperYn;
}
/**
* 운용계획설명
* @return 운용계획설명
*/
public String getOperPlanDescr() {
return operPlanDescr;
}
/**
* 운용계획설명
*@param operPlanDescr 운용계획설명
*/
public void setOperPlanDescr(String operPlanDescr) {
	this.operPlanDescr = operPlanDescr;
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
