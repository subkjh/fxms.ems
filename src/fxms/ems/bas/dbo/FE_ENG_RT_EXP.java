package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.07.24 16:43
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_RT_EXP", comment = "에너지루트예측테이블")
@FxIndex(name = "FE_ENG_RT_EXP__PK", type = INDEX_TYPE.PK, columns = {"ENG_RT_ID","EXP_DTM"})
@FxIndex(name = "FE_ENG_RT_EXP__FK1", type = INDEX_TYPE.FK, columns = {"ENG_RT_ID"}, fkTable = "FE_ENG_RT_BAS", fkColumn = "ENG_RT_ID")
public class FE_ENG_RT_EXP  {

public FE_ENG_RT_EXP() {
 }

    @FxColumn(name = "ENG_RT_ID", size = 30, comment = "에너지루트ID")
    private String engRtId;

    @FxColumn(name = "EXP_DTM", size = 14, comment = "예상일시")
    private Long expDtm;

    @FxColumn(name = "EXP_AMT", size = 14, comment = "예상량")
    private Double expAmt;

    @FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
    private Integer regUserNo = 0;

    @FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
    private Long regDtm;

    @FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
    private Integer chgUserNo = 0;

    @FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
    private Long chgDtm;

/**
 * @return 에너지루트ID
*/
public String getEngRtId() { 
    return engRtId;
}
/**
 * @param engRtId 에너지루트ID
*/
public void setEngRtId(String engRtId) { 
    this.engRtId = engRtId;
}
/**
 * @return 예상일시
*/
public Long getExpDtm() { 
    return expDtm;
}
/**
 * @param expDtm 예상일시
*/
public void setExpDtm(Long expDtm) { 
    this.expDtm = expDtm;
}
/**
 * @return 예상량
*/
public Double getExpAmt() { 
    return expAmt;
}
/**
 * @param expAmt 예상량
*/
public void setExpAmt(Double expAmt) { 
    this.expAmt = expAmt;
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
