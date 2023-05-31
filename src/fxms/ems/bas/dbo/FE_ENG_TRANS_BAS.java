package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.05.02 15:12
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_TRANS_BAS", comment = "에너지거래기본테이블")
@FxIndex(name = "FE_ENG_TRANS_BAS__PK", type = INDEX_TYPE.PK, columns = {"TRNS_NO"})
@FxIndex(name = "FE_ENG_TRANS_BAS__FK1", type = INDEX_TYPE.FK, columns = {"ENG_ID"}, fkTable = "FE_ENG_BAS", fkColumn = "ENG_ID")
@FxIndex(name = "FE_ENG_TRANS_BAS__FK2", type = INDEX_TYPE.FK, columns = {"UNIT_PRICE_TBL_ID"}, fkTable = "FE_ENG_TRANS_PRICE", fkColumn = "UNIT_PRICE_TBL_ID")
@FxIndex(name = "FE_ENG_TRANS_BAS__KEY1", type = INDEX_TYPE.KEY, columns = {"TRNS_STRT_DTM", "SELL_INLO_NO"})
@FxIndex(name = "FE_ENG_TRANS_BAS__KEY2", type = INDEX_TYPE.KEY, columns = {"TRNS_ST_CD"})
public class FE_ENG_TRANS_BAS  {

public FE_ENG_TRANS_BAS() {
 }

public static final String FX_SEQ_TRNSNO  = "FX_SEQ_TRNSNO"; 
@FxColumn(name = "TRNS_NO", size = 14, comment = "거래번호", sequence = "FX_SEQ_TRNSNO")
    private Long trnsNo;

@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
    private String engId;

@FxColumn(name = "INLO_NO", size = 9, comment = "설치위치(산단)")
    private Integer inloNo;

@FxColumn(name = "SELL_INLO_NO", size = 9, comment = "판매설치위치번호")
    private Integer sellInloNo;

@FxColumn(name = "BUY_INLO_NO", size = 9, comment = "구입설치위치번호")
    private Integer buyInloNo;

@FxColumn(name = "TRNS_STRT_DTM", size = 14, nullable = true, comment = "거래시작일시")
    private Long trnsStrtDtm;

@FxColumn(name = "TRNS_FNSH_DTM", size = 14, nullable = true, comment = "거래종료일시")
    private Long trnsFnshDtm;

@FxColumn(name = "MONTH_CNTRT_TRNS_VOL", size = 14, nullable = true, comment = "월계약거래량")
    private Long monthCntrtTrnsVol;

@FxColumn(name = "HOURLY_MAX_CNTRT_TRNS_VOL", size = 14, nullable = true, comment = "시간당최대계약거래량")
    private Long hourlyMaxCntrtTrnsVol;

@FxColumn(name = "TRNS_ST_CD", size = 10, comment = "거래상태코드", defValue = "NONE")
    private String trnsStCd = "NONE";

@FxColumn(name = "TRNS_METHD_CD", size = 10, comment = "거래방식코드", defValue = "NONE")
    private String trnsMethdCd = "NONE";

@FxColumn(name = "UNIT_PRICE_TBL_ID", size = 10, nullable = true, comment = "단가표ID")
    private String unitPriceTblId;

@FxColumn(name = "CNTRT_DOC_NUM", size = 30, nullable = true, comment = "계약문서번호")
    private String cntrtDocNum;

@FxColumn(name = "TRNS_DESCR", size = 400, nullable = true, comment = "거래설명")
    private String trnsDescr;

@FxColumn(name = "TRNS_CHRG", size = 14, nullable = true, comment = "거래요금(삭제예정)")
    private Long trnsChrg;

@FxColumn(name = "TRNS_AMT", size = 14, nullable = true, comment = "거래량(삭제예정)")
    private Long trnsAmt;

@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
    private Integer regUserNo = 0;

@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
    private Long regDtm;

@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
    private Integer chgUserNo = 0;

@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
    private Long chgDtm;

/**
 * @return 거래번호
*/
public Long getTrnsNo() { 
    return trnsNo;
}
/**
 * @param trnsNo 거래번호
*/
public void setTrnsNo(Long trnsNo) { 
    this.trnsNo = trnsNo;
}
/**
 * @return 에너지ID
*/
public String getEngId() { 
    return engId;
}
/**
 * @param engId 에너지ID
*/
public void setEngId(String engId) { 
    this.engId = engId;
}
/**
 * @return 설치위치(산단)
*/
public Integer getInloNo() { 
    return inloNo;
}
/**
 * @param inloNo 설치위치(산단)
*/
public void setInloNo(Integer inloNo) { 
    this.inloNo = inloNo;
}
/**
 * @return 판매설치위치번호
*/
public Integer getSellInloNo() { 
    return sellInloNo;
}
/**
 * @param sellInloNo 판매설치위치번호
*/
public void setSellInloNo(Integer sellInloNo) { 
    this.sellInloNo = sellInloNo;
}
/**
 * @return 구입설치위치번호
*/
public Integer getBuyInloNo() { 
    return buyInloNo;
}
/**
 * @param buyInloNo 구입설치위치번호
*/
public void setBuyInloNo(Integer buyInloNo) { 
    this.buyInloNo = buyInloNo;
}
/**
 * @return 거래시작일시
*/
public Long getTrnsStrtDtm() { 
    return trnsStrtDtm;
}
/**
 * @param trnsStrtDtm 거래시작일시
*/
public void setTrnsStrtDtm(Long trnsStrtDtm) { 
    this.trnsStrtDtm = trnsStrtDtm;
}
/**
 * @return 거래종료일시
*/
public Long getTrnsFnshDtm() { 
    return trnsFnshDtm;
}
/**
 * @param trnsFnshDtm 거래종료일시
*/
public void setTrnsFnshDtm(Long trnsFnshDtm) { 
    this.trnsFnshDtm = trnsFnshDtm;
}
/**
 * @return 월계약거래량
*/
public Long getMonthCntrtTrnsVol() { 
    return monthCntrtTrnsVol;
}
/**
 * @param monthCntrtTrnsVol 월계약거래량
*/
public void setMonthCntrtTrnsVol(Long monthCntrtTrnsVol) { 
    this.monthCntrtTrnsVol = monthCntrtTrnsVol;
}
/**
 * @return 시간당최대계약거래량
*/
public Long getHourlyMaxCntrtTrnsVol() { 
    return hourlyMaxCntrtTrnsVol;
}
/**
 * @param hourlyMaxCntrtTrnsVol 시간당최대계약거래량
*/
public void setHourlyMaxCntrtTrnsVol(Long hourlyMaxCntrtTrnsVol) { 
    this.hourlyMaxCntrtTrnsVol = hourlyMaxCntrtTrnsVol;
}
/**
 * @return 거래상태코드
*/
public String getTrnsStCd() { 
    return trnsStCd;
}
/**
 * @param trnsStCd 거래상태코드
*/
public void setTrnsStCd(String trnsStCd) { 
    this.trnsStCd = trnsStCd;
}
/**
 * @return 거래방식코드
*/
public String getTrnsMethdCd() { 
    return trnsMethdCd;
}
/**
 * @param trnsMethdCd 거래방식코드
*/
public void setTrnsMethdCd(String trnsMethdCd) { 
    this.trnsMethdCd = trnsMethdCd;
}
/**
 * @return 단가표ID
*/
public String getUnitPriceTblId() { 
    return unitPriceTblId;
}
/**
 * @param unitPriceTblId 단가표ID
*/
public void setUnitPriceTblId(String unitPriceTblId) { 
    this.unitPriceTblId = unitPriceTblId;
}
/**
 * @return 계약문서번호
*/
public String getCntrtDocNum() { 
    return cntrtDocNum;
}
/**
 * @param cntrtDocNum 계약문서번호
*/
public void setCntrtDocNum(String cntrtDocNum) { 
    this.cntrtDocNum = cntrtDocNum;
}
/**
 * @return 거래설명
*/
public String getTrnsDescr() { 
    return trnsDescr;
}
/**
 * @param trnsDescr 거래설명
*/
public void setTrnsDescr(String trnsDescr) { 
    this.trnsDescr = trnsDescr;
}
/**
 * @return 거래요금(삭제예정)
*/
public Long getTrnsChrg() { 
    return trnsChrg;
}
/**
 * @param trnsChrg 거래요금(삭제예정)
*/
public void setTrnsChrg(Long trnsChrg) { 
    this.trnsChrg = trnsChrg;
}
/**
 * @return 거래량(삭제예정)
*/
public Long getTrnsAmt() { 
    return trnsAmt;
}
/**
 * @param trnsAmt 거래량(삭제예정)
*/
public void setTrnsAmt(Long trnsAmt) { 
    this.trnsAmt = trnsAmt;
}
/**
 * @return 등록사용자번호
*/
public Integer getRegUserNo() { 
    return regUserNo;
}
/**
 * @param regUserNo 등록사용자번호
*/
public void setRegUserNo(Integer regUserNo) { 
    this.regUserNo = regUserNo;
}
/**
 * @return 등록일시
*/
public Long getRegDtm() { 
    return regDtm;
}
/**
 * @param regDtm 등록일시
*/
public void setRegDtm(Long regDtm) { 
    this.regDtm = regDtm;
}
/**
 * @return 수정사용자번호
*/
public Integer getChgUserNo() { 
    return chgUserNo;
}
/**
 * @param chgUserNo 수정사용자번호
*/
public void setChgUserNo(Integer chgUserNo) { 
    this.chgUserNo = chgUserNo;
}
/**
 * @return 수정일시
*/
public Long getChgDtm() { 
    return chgDtm;
}
/**
 * @param chgDtm 수정일시
*/
public void setChgDtm(Long chgDtm) { 
    this.chgDtm = chgDtm;
}
}
