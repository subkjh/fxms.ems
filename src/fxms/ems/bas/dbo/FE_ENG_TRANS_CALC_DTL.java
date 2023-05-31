package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.04.28 17:45
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_TRANS_CALC_DTL", comment = "에너지거래정산상세테이블")
@FxIndex(name = "FE_ENG_TRANS_CALC_DTL__PK", type = INDEX_TYPE.PK, columns = {"TRNS_NO", "CALC_YM", "CALC_DTM"})
@FxIndex(name = "FE_ENG_TRANS_CALC_DTL__FK1", type = INDEX_TYPE.FK, columns = {"TRNS_NO"}, fkTable = "FE_ENG_TRANS_BAS", fkColumn = "TRNS_NO")
public class FE_ENG_TRANS_CALC_DTL  {

public FE_ENG_TRANS_CALC_DTL() {
 }

@FxColumn(name = "TRNS_NO", size = 14, comment = "거래번호")
private Long trnsNo;


@FxColumn(name = "CALC_YM", size = 6, comment = "정산년월")
private String calcYm;


@FxColumn(name = "CALC_DTM", size = 14, comment = "정산일시")
private Long calcDtm;


@FxColumn(name = "TRNS_VOL", size = 9, comment = "거래량")
private Integer trnsVol;


@FxColumn(name = "TRNS_VOL_CHRG", size = 14, comment = "거래량요금")
private Long trnsVolChrg;


@FxColumn(name = "UNIT_PRICE", size = 12, comment = "단가")
private Double unitPrice;


@FxColumn(name = "INLO_NO", size = 9, comment = "설치위치(산단)")
private Integer inloNo;


@FxColumn(name = "SELL_INLO_NO", size = 9, comment = "판매설치위치번호")
private Integer sellInloNo;


@FxColumn(name = "BUY_INLO_NO", size = 9, comment = "구입설치위치번호")
private Integer buyInloNo;


@FxColumn(name = "ENG_RT_ID", size = 30, comment = "에너지루트ID")
private String engRtId;


@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
private String engId;


@FxColumn(name = "SENSOR_MO_NO", size = 19, comment = "계측기MO번호")
private Long sensorMoNo;


@FxColumn(name = "STRT_MEASR_DTM", size = 14, comment = "시작계측일시")
private Long strtMeasrDtm;


@FxColumn(name = "STRT_MEASR_VAL", size = 14, comment = "시작계측값")
private Double strtMeasrVal;


@FxColumn(name = "FNSH_MEASR_DTM", size = 14, comment = "종료계측일시")
private Long fnshMeasrDtm;


@FxColumn(name = "FNSH_MEASR_VAL", size = 14, comment = "종료계측값")
private Double fnshMeasrVal;


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
* @return 거래번호
*/
public Long getTrnsNo() {
return trnsNo;
}
/**
* 거래번호
*@param trnsNo 거래번호
*/
public void setTrnsNo(Long trnsNo) {
	this.trnsNo = trnsNo;
}
/**
* 정산년월
* @return 정산년월
*/
public String getCalcYm() {
return calcYm;
}
/**
* 정산년월
*@param calcYm 정산년월
*/
public void setCalcYm(String calcYm) {
	this.calcYm = calcYm;
}
/**
* 정산일시
* @return 정산일시
*/
public Long getCalcDtm() {
return calcDtm;
}
/**
* 정산일시
*@param calcDtm 정산일시
*/
public void setCalcDtm(Long calcDtm) {
	this.calcDtm = calcDtm;
}
/**
* 거래량
* @return 거래량
*/
public Integer getTrnsVol() {
return trnsVol;
}
/**
* 거래량
*@param trnsVol 거래량
*/
public void setTrnsVol(Integer trnsVol) {
	this.trnsVol = trnsVol;
}
/**
* 거래량요금
* @return 거래량요금
*/
public Long getTrnsVolChrg() {
return trnsVolChrg;
}
/**
* 거래량요금
*@param trnsVolChrg 거래량요금
*/
public void setTrnsVolChrg(Long trnsVolChrg) {
	this.trnsVolChrg = trnsVolChrg;
}
/**
* 단가
* @return 단가
*/
public Double getUnitPrice() {
return unitPrice;
}
/**
* 단가
*@param unitPrice 단가
*/
public void setUnitPrice(Double unitPrice) {
	this.unitPrice = unitPrice;
}
/**
* 설치위치(산단)
* @return 설치위치(산단)
*/
public Integer getInloNo() {
return inloNo;
}
/**
* 설치위치(산단)
*@param inloNo 설치위치(산단)
*/
public void setInloNo(Integer inloNo) {
	this.inloNo = inloNo;
}
/**
* 판매설치위치번호
* @return 판매설치위치번호
*/
public Integer getSellInloNo() {
return sellInloNo;
}
/**
* 판매설치위치번호
*@param sellInloNo 판매설치위치번호
*/
public void setSellInloNo(Integer sellInloNo) {
	this.sellInloNo = sellInloNo;
}
/**
* 구입설치위치번호
* @return 구입설치위치번호
*/
public Integer getBuyInloNo() {
return buyInloNo;
}
/**
* 구입설치위치번호
*@param buyInloNo 구입설치위치번호
*/
public void setBuyInloNo(Integer buyInloNo) {
	this.buyInloNo = buyInloNo;
}
/**
* 에너지루트ID
* @return 에너지루트ID
*/
public String getEngRtId() {
return engRtId;
}
/**
* 에너지루트ID
*@param engRtId 에너지루트ID
*/
public void setEngRtId(String engRtId) {
	this.engRtId = engRtId;
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
* 계측기MO번호
* @return 계측기MO번호
*/
public Long getSensorMoNo() {
return sensorMoNo;
}
/**
* 계측기MO번호
*@param sensorMoNo 계측기MO번호
*/
public void setSensorMoNo(Long sensorMoNo) {
	this.sensorMoNo = sensorMoNo;
}
/**
* 시작계측일시
* @return 시작계측일시
*/
public Long getStrtMeasrDtm() {
return strtMeasrDtm;
}
/**
* 시작계측일시
*@param strtMeasrDtm 시작계측일시
*/
public void setStrtMeasrDtm(Long strtMeasrDtm) {
	this.strtMeasrDtm = strtMeasrDtm;
}
/**
* 시작계측값
* @return 시작계측값
*/
public Double getStrtMeasrVal() {
return strtMeasrVal;
}
/**
* 시작계측값
*@param strtMeasrVal 시작계측값
*/
public void setStrtMeasrVal(Double strtMeasrVal) {
	this.strtMeasrVal = strtMeasrVal;
}
/**
* 종료계측일시
* @return 종료계측일시
*/
public Long getFnshMeasrDtm() {
return fnshMeasrDtm;
}
/**
* 종료계측일시
*@param fnshMeasrDtm 종료계측일시
*/
public void setFnshMeasrDtm(Long fnshMeasrDtm) {
	this.fnshMeasrDtm = fnshMeasrDtm;
}
/**
* 종료계측값
* @return 종료계측값
*/
public Double getFnshMeasrVal() {
return fnshMeasrVal;
}
/**
* 종료계측값
*@param fnshMeasrVal 종료계측값
*/
public void setFnshMeasrVal(Double fnshMeasrVal) {
	this.fnshMeasrVal = fnshMeasrVal;
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
