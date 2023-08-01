package fxms.ems.bas.dbo;


import java.io.Serializable;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.28 18:56
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_PROD_AMT", comment = "에너지생산량테이블")
@FxIndex(name = "FE_ENG_PROD_AMT__PK", type = INDEX_TYPE.PK, columns = {"PROD_DTM", "DTM_TYPE", "INLO_NO", "ENG_ID", "FAC_NO"})
@FxIndex(name = "FE_ENG_PROD_AMT__FK1", type = INDEX_TYPE.FK, columns = {"ENG_ID"}, fkTable = "FE_ENG_BAS", fkColumn = "ENG_ID")
public class FE_ENG_PROD_AMT implements Serializable {

public FE_ENG_PROD_AMT() {
 }

@FxColumn(name = "PROD_DTM", size = 14, comment = "생산일시")
private Long prodDtm;


@FxColumn(name = "DTM_TYPE", size = 5, comment = "일시유형", defValue = "H1")
private String dtmType = "H1";


@FxColumn(name = "INLO_NO", size = 9, comment = "설치위치번호", defValue = "-1")
private Integer inloNo = -1;


@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
private String engId;


@FxColumn(name = "FAC_NO", size = 14, comment = "설비번호", defValue = "-1")
private Long facNo = -1L;


@FxColumn(name = "EXP_PROD_AMT", size = 14, nullable = true, comment = "예상생산량", defValue = "-1")
private Double expProdAmt;


@FxColumn(name = "PROD_AMT", size = 14, nullable = true, comment = "생산량", defValue = "-1")
private Double prodAmt;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


/**
* 생산일시
* @return 생산일시
*/
public Long getProdDtm() {
return prodDtm;
}
/**
* 생산일시
*@param prodDtm 생산일시
*/
public void setProdDtm(Long prodDtm) {
	this.prodDtm = prodDtm;
}
/**
* 일시유형
* @return 일시유형
*/
public String getDtmType() {
return dtmType;
}
/**
* 일시유형
*@param dtmType 일시유형
*/
public void setDtmType(String dtmType) {
	this.dtmType = dtmType;
}
/**
* 설치위치번호
* @return 설치위치번호
*/
public Integer getInloNo() {
return inloNo;
}
/**
* 설치위치번호
*@param inloNo 설치위치번호
*/
public void setInloNo(Integer inloNo) {
	this.inloNo = inloNo;
}
/**
* 에너지ID
* @return 에너지ID
*/
public String getEngId() {
return engId;
}
/**
* 에너지ID
*@param engId 에너지ID
*/
public void setEngId(String engId) {
	this.engId = engId;
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
* 예상생산량
* @return 예상생산량
*/
public Double getExpProdAmt() {
return expProdAmt;
}
/**
* 예상생산량
*@param expProdAmt 예상생산량
*/
public void setExpProdAmt(Double expProdAmt) {
	this.expProdAmt = expProdAmt;
}
/**
* 생산량
* @return 생산량
*/
public Double getProdAmt() {
return prodAmt;
}
/**
* 생산량
*@param prodAmt 생산량
*/
public void setProdAmt(Double prodAmt) {
	this.prodAmt = prodAmt;
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
