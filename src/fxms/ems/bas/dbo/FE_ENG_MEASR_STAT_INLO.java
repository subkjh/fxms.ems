package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.08.03 10:17
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_MEASR_STAT_INLO", comment = "에너지계측량통계설치위치별테이블")
@FxIndex(name = "FE_ENG_MEASR_STAT_INLO__PK", type = INDEX_TYPE.PK, columns = {"ENG_DATE","ENG_ID","INLO_NO"})
@FxIndex(name = "FE_ENG_MEASR_STAT_INLO__FK1", type = INDEX_TYPE.FK, columns = {"ENG_ID"}, fkTable = "FE_ENG_BAS", fkColumn = "ENG_ID")
public class FE_ENG_MEASR_STAT_INLO  {

public FE_ENG_MEASR_STAT_INLO() {
 }

    @FxColumn(name = "ENG_DATE", size = 8, comment = "에너지일자")
    private String engDate;

    @FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
    private String engId;

    @FxColumn(name = "INLO_NO", size = 9, comment = "설치위치번호")
    private int inloNo;

    @FxColumn(name = "EXP_CONS_AMT", size = 14, nullable = true, comment = "예상소비량")
    private double expConsAmt;

    @FxColumn(name = "CONS_AMT", size = 14, nullable = true, comment = "소비량")
    private double consAmt;

    @FxColumn(name = "EXP_PROD_AMT", size = 14, nullable = true, comment = "예상생산량")
    private double expProdAmt;

    @FxColumn(name = "PROD_AMT", size = 14, nullable = true, comment = "생산량")
    private double prodAmt;

    @FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
    private int regUserNo = 0;

    @FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
    private long regDtm;

    @FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
    private int chgUserNo = 0;

    @FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
    private long chgDtm;

/**
 * @return 에너지일자
*/
public String getEngDate() { 
    return engDate;
}
/**
 * @param engDate 에너지일자
*/
public void setEngDate(String engDate) { 
    this.engDate = engDate;
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
 * @return 설치위치번호
*/
public int getInloNo() { 
    return inloNo;
}
/**
 * @param inloNo 설치위치번호
*/
public void setInloNo(int inloNo) { 
    this.inloNo = inloNo;
}
/**
 * @return 예상소비량
*/
public double getExpConsAmt() { 
    return expConsAmt;
}
/**
 * @param expConsAmt 예상소비량
*/
public void setExpConsAmt(double expConsAmt) { 
    this.expConsAmt = expConsAmt;
}
/**
 * @return 소비량
*/
public double getConsAmt() { 
    return consAmt;
}
/**
 * @param consAmt 소비량
*/
public void setConsAmt(double consAmt) { 
    this.consAmt = consAmt;
}
/**
 * @return 예상생산량
*/
public double getExpProdAmt() { 
    return expProdAmt;
}
/**
 * @param expProdAmt 예상생산량
*/
public void setExpProdAmt(double expProdAmt) { 
    this.expProdAmt = expProdAmt;
}
/**
 * @return 생산량
*/
public double getProdAmt() { 
    return prodAmt;
}
/**
 * @param prodAmt 생산량
*/
public void setProdAmt(double prodAmt) { 
    this.prodAmt = prodAmt;
}
/**
 * @return 등록사용자번호
*/
public int getRegUserNo() { 
    return regUserNo;
}
/**
 * @param regUserNo 등록사용자번호
*/
public void setRegUserNo(int regUserNo) { 
    this.regUserNo = regUserNo;
}
/**
 * @return 등록일시
*/
public long getRegDtm() { 
    return regDtm;
}
/**
 * @param regDtm 등록일시
*/
public void setRegDtm(long regDtm) { 
    this.regDtm = regDtm;
}
/**
 * @return 수정사용자번호
*/
public int getChgUserNo() { 
    return chgUserNo;
}
/**
 * @param chgUserNo 수정사용자번호
*/
public void setChgUserNo(int chgUserNo) { 
    this.chgUserNo = chgUserNo;
}
/**
 * @return 수정일시
*/
public long getChgDtm() { 
    return chgDtm;
}
/**
 * @param chgDtm 수정일시
*/
public void setChgDtm(long chgDtm) { 
    this.chgDtm = chgDtm;
}
}
