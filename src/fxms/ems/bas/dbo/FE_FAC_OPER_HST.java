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


@FxTable(name = "FE_FAC_OPER_HST", comment = "공정설비운영이력테이블")
@FxIndex(name = "FE_FAC_OPER_HST__PK", type = INDEX_TYPE.PK, columns = {"FAC_OPER_NO"})
@FxIndex(name = "FE_FAC_OPER_HST__FK1", type = INDEX_TYPE.FK, columns = {"FAC_NO"}, fkTable = "FE_FAC_BAS", fkColumn = "FAC_NO")
public class FE_FAC_OPER_HST  {

public FE_FAC_OPER_HST() {
 }

public static final String FX_SEQ_FACOPERNO  = "FX_SEQ_FACOPERNO"; 
@FxColumn(name = "FAC_OPER_NO", size = 12, comment = "설비운영번호", sequence = "FX_SEQ_FACOPERNO")
private Long facOperNo;


@FxColumn(name = "FAC_NO", size = 14, comment = "설비번호")
private Long facNo;


@FxColumn(name = "OPER_DATE", size = 8, comment = "운영일자")
private String operDate;


@FxColumn(name = "DOW_CD", size = 4, nullable = true, comment = "요일코드")
private String dowCd;


@FxColumn(name = "OPER_TIME_SERIES", size = 24, comment = "운영시간시리즈", defValue = "000000001110111110000000")
private String operTimeSeries = "000000001110111110000000";


@FxColumn(name = "OPER_HOURS", size = 2, comment = "운영시간", defValue = "8")
private Integer operHours = 8;


@FxColumn(name = "OPER_DESCR", size = 400, nullable = true, comment = "운용설명")
private String operDescr;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


/**
* 설비운영번호
* @return 설비운영번호
*/
public Long getFacOperNo() {
return facOperNo;
}
/**
* 설비운영번호
*@param facOperNo 설비운영번호
*/
public void setFacOperNo(Long facOperNo) {
	this.facOperNo = facOperNo;
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
* 운영일자
* @return 운영일자
*/
public String getOperDate() {
return operDate;
}
/**
* 운영일자
*@param operDate 운영일자
*/
public void setOperDate(String operDate) {
	this.operDate = operDate;
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
* 운용설명
* @return 운용설명
*/
public String getOperDescr() {
return operDescr;
}
/**
* 운용설명
*@param operDescr 운용설명
*/
public void setOperDescr(String operDescr) {
	this.operDescr = operDescr;
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
