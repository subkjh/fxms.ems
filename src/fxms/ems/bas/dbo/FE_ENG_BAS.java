package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.06.16 10:02
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_BAS", comment = "에너지기본테이블")
@FxIndex(name = "FE_ENG_BAS__PK", type = INDEX_TYPE.PK, columns = {"ENG_ID"})
@FxIndex(name = "FE_ENG_BAS__UK", type = INDEX_TYPE.UK, columns = {"ENG_NAME"})
public class FE_ENG_BAS  {

public FE_ENG_BAS() {
 }

    @FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
    private String engId;

    @FxColumn(name = "ENG_NAME", size = 50, comment = "에너지명")
    private String engName;

    @FxColumn(name = "ENG_DESCR", size = 200, comment = "에너지설명")
    private String engDescr;

    @FxColumn(name = "ENG_UNIT", size = 20, comment = "에너지단위")
    private String engUnit;

    @FxColumn(name = "ENG_ABBR", size = 20, nullable = true, comment = "에너지약어")
    private String engAbbr;

    @FxColumn(name = "ENG_TID", size = 20, nullable = true, comment = "에너지TID")
    private String engTid;

    @FxColumn(name = "INST_PS_ID", size = 50, nullable = true, comment = "순시성능ID")
    private String instPsId;

    @FxColumn(name = "INTG_PS_ID", size = 50, nullable = true, comment = "적산성능ID")
    private String intgPsId;

    @FxColumn(name = "PRES_PS_ID", size = 50, nullable = true, comment = "압력성능ID")
    private String presPsId;

    @FxColumn(name = "TEMP_PS_ID", size = 50, nullable = true, comment = "온도성능ID")
    private String tempPsId;

    @FxColumn(name = "USED_PS_ID", size = 50, nullable = true, comment = "사용량성능ID")
    private String usedPsId;

    @FxColumn(name = "USE_YN", size = 1, comment = "사용여부")
    private String useYn;

    @FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
    private Integer regUserNo = 0;

    @FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
    private Long regDtm;

    @FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
    private Integer chgUserNo = 0;

    @FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
    private Long chgDtm;

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
 * @return 에너지명
*/
public String getEngName() { 
    return engName;
}
/**
 * @param engName 에너지명
*/
public void setEngName(String engName) { 
    this.engName = engName;
}
/**
 * @return 에너지설명
*/
public String getEngDescr() { 
    return engDescr;
}
/**
 * @param engDescr 에너지설명
*/
public void setEngDescr(String engDescr) { 
    this.engDescr = engDescr;
}
/**
 * @return 에너지단위
*/
public String getEngUnit() { 
    return engUnit;
}
/**
 * @param engUnit 에너지단위
*/
public void setEngUnit(String engUnit) { 
    this.engUnit = engUnit;
}
/**
 * @return 에너지약어
*/
public String getEngAbbr() { 
    return engAbbr;
}
/**
 * @param engAbbr 에너지약어
*/
public void setEngAbbr(String engAbbr) { 
    this.engAbbr = engAbbr;
}
/**
 * @return 에너지TID
*/
public String getEngTid() { 
    return engTid;
}
/**
 * @param engTid 에너지TID
*/
public void setEngTid(String engTid) { 
    this.engTid = engTid;
}
/**
 * @return 순시성능ID
*/
public String getInstPsId() { 
    return instPsId;
}
/**
 * @param instPsId 순시성능ID
*/
public void setInstPsId(String instPsId) { 
    this.instPsId = instPsId;
}
/**
 * @return 적산성능ID
*/
public String getIntgPsId() { 
    return intgPsId;
}
/**
 * @param intgPsId 적산성능ID
*/
public void setIntgPsId(String intgPsId) { 
    this.intgPsId = intgPsId;
}
/**
 * @return 압력성능ID
*/
public String getPresPsId() { 
    return presPsId;
}
/**
 * @param presPsId 압력성능ID
*/
public void setPresPsId(String presPsId) { 
    this.presPsId = presPsId;
}
/**
 * @return 온도성능ID
*/
public String getTempPsId() { 
    return tempPsId;
}
/**
 * @param tempPsId 온도성능ID
*/
public void setTempPsId(String tempPsId) { 
    this.tempPsId = tempPsId;
}
/**
 * @return 사용량성능ID
*/
public String getUsedPsId() { 
    return usedPsId;
}
/**
 * @param usedPsId 사용량성능ID
*/
public void setUsedPsId(String usedPsId) { 
    this.usedPsId = usedPsId;
}
/**
 * @return 사용여부
*/
public String isUseYn() { 
    return useYn;
}
/**
 * @param useYn 사용여부
*/
public void setUseYn(String useYn) { 
    this.useYn = useYn;
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
