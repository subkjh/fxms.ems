package fxms.ems.bas.dbo;


import java.io.Serializable;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.21 10:36
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_PRICE", comment = "에너지단가테이블")
@FxIndex(name = "FE_ENG_PRICE__PK", type = INDEX_TYPE.PK, columns = {"INLO_NO", "ENG_ID", "PRICE_TYPE"})
public class FE_ENG_PRICE implements Serializable {

public FE_ENG_PRICE() {
 }

@FxColumn(name = "INLO_NO", size = 9, comment = "설치위치번호", defValue = "0")
private Integer inloNo = 0;


@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
private String engId;


@FxColumn(name = "PRICE_TYPE", size = 20, comment = "단가유형")
private String priceType;


@FxColumn(name = "RANGE_STRT_VAL", size = 14, comment = "범위시작값")
private Double rangeStrtVal;


@FxColumn(name = "RANGE_FNSH_VAL", size = 14, comment = "범위종료값")
private Double rangeFnshVal;


@FxColumn(name = "ENG_PRICE_UNIT", size = 10, comment = "에너지단가단위")
private Double engPriceUnit;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


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
* 단가유형
* @return 단가유형
*/
public String getPriceType() {
return priceType;
}
/**
* 단가유형
*@param priceType 단가유형
*/
public void setPriceType(String priceType) {
	this.priceType = priceType;
}
/**
* 범위시작값
* @return 범위시작값
*/
public Double getRangeStrtVal() {
return rangeStrtVal;
}
/**
* 범위시작값
*@param rangeStrtVal 범위시작값
*/
public void setRangeStrtVal(Double rangeStrtVal) {
	this.rangeStrtVal = rangeStrtVal;
}
/**
* 범위종료값
* @return 범위종료값
*/
public Double getRangeFnshVal() {
return rangeFnshVal;
}
/**
* 범위종료값
*@param rangeFnshVal 범위종료값
*/
public void setRangeFnshVal(Double rangeFnshVal) {
	this.rangeFnshVal = rangeFnshVal;
}
/**
* 에너지단가단위
* @return 에너지단가단위
*/
public Double getEngPriceUnit() {
return engPriceUnit;
}
/**
* 에너지단가단위
*@param engPriceUnit 에너지단가단위
*/
public void setEngPriceUnit(Double engPriceUnit) {
	this.engPriceUnit = engPriceUnit;
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
