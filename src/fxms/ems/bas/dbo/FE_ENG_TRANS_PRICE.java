package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.04.28 14:02
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_TRANS_PRICE", comment = "에너지거래단가테이블")
@FxIndex(name = "FE_ENG_TRANS_PRICE__PK", type = INDEX_TYPE.PK, columns = {"UNIT_PRICE_TBL_ID"})
public class FE_ENG_TRANS_PRICE  {

public FE_ENG_TRANS_PRICE() {
 }

@FxColumn(name = "UNIT_PRICE_TBL_ID", size = 10, comment = "단가표ID")
private String unitPriceTblId;


@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
private String engId;


@FxColumn(name = "CHRG_CALC_METHD_CD", size = 10, comment = "요금계산방식코드")
private String chrgCalcMethdCd;


@FxColumn(name = "BASE_UNIT", size = 9, nullable = true, comment = "기준단위", defValue = "1")
private Float baseUnit = 1f;


@FxColumn(name = "BASE_UNIT_PRICE", size = 9, nullable = true, comment = "기준단가")
private Integer baseUnitPrice;


@FxColumn(name = "FIXED_CHRG", size = 12, nullable = true, comment = "고정요금")
private Double fixedChrg;


@FxColumn(name = "REG_MEMO", size = 400, nullable = true, comment = "등록메모")
private String regMemo;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


/**
* 단가표ID
* @return 단가표ID
*/
public String getUnitPriceTblId() {
return unitPriceTblId;
}
/**
* 단가표ID
*@param unitPriceTblId 단가표ID
*/
public void setUnitPriceTblId(String unitPriceTblId) {
	this.unitPriceTblId = unitPriceTblId;
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
* 요금계산방식코드
* @return 요금계산방식코드
*/
public String getChrgCalcMethdCd() {
return chrgCalcMethdCd;
}
/**
* 요금계산방식코드
*@param chrgCalcMethdCd 요금계산방식코드
*/
public void setChrgCalcMethdCd(String chrgCalcMethdCd) {
	this.chrgCalcMethdCd = chrgCalcMethdCd;
}
/**
* 기준단위
* @return 기준단위
*/
public Float getBaseUnit() {
return baseUnit;
}
/**
* 기준단위
*@param baseUnit 기준단위
*/
public void setBaseUnit(Float baseUnit) {
	this.baseUnit = baseUnit;
}
/**
* 기준단가
* @return 기준단가
*/
public Integer getBaseUnitPrice() {
return baseUnitPrice;
}
/**
* 기준단가
*@param baseUnitPrice 기준단가
*/
public void setBaseUnitPrice(Integer baseUnitPrice) {
	this.baseUnitPrice = baseUnitPrice;
}
/**
* 고정요금
* @return 고정요금
*/
public Double getFixedChrg() {
return fixedChrg;
}
/**
* 고정요금
*@param fixedChrg 고정요금
*/
public void setFixedChrg(Double fixedChrg) {
	this.fixedChrg = fixedChrg;
}
/**
* 등록메모
* @return 등록메모
*/
public String getRegMemo() {
return regMemo;
}
/**
* 등록메모
*@param regMemo 등록메모
*/
public void setRegMemo(String regMemo) {
	this.regMemo = regMemo;
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
