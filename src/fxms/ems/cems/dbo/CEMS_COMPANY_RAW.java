package fxms.ems.cems.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.12.19 17:04
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "CEMS_COMPANY_RAW", comment = "CEMS회사원천내역")
@FxIndex(name = "CEMS_COMPANY_RAW__PK", type = INDEX_TYPE.PK, columns = {"COMPLEX_NAME","COMPANY_NAME","PLANT_NAME"})
public class CEMS_COMPANY_RAW  {

public CEMS_COMPANY_RAW() {
 }

    @FxColumn(name = "COMPLEX_NAME", size = 200, comment = "산업단지명")
    private String complexName;

    @FxColumn(name = "COMPANY_NAME", size = 200, comment = "업체명")
    private String companyName;

    @FxColumn(name = "PLANT_NAME", size = 200, comment = "공장명")
    private String plantName;

    @FxColumn(name = "SECTOR_CL_CDS", size = 200, nullable = true, comment = "업종")
    private String sectorClCds;

    @FxColumn(name = "REP_SECTOR_CL_CD", size = 200, nullable = true, comment = "대표업종번호")
    private String repSectorClCd;

    @FxColumn(name = "CEO_NAME", size = 200, nullable = true, comment = "대표자")
    private String ceoName;

    @FxColumn(name = "BIZ_REG_NUM", size = 200, nullable = true, comment = "사업자등록번호")
    private String bizRegNum;

    @FxColumn(name = "CORP_REG_NUM", size = 200, nullable = true, comment = "법인등록번호")
    private String corpRegNum;

    @FxColumn(name = "ADDR", size = 200, nullable = true, comment = "업체주소")
    private String addr;

    @FxColumn(name = "CNTCR_NAME", size = 200, nullable = true, comment = "사업자담당자이름")
    private String cntcrName;

    @FxColumn(name = "TEL_NUM", size = 200, nullable = true, comment = "사업자담당자연락처")
    private String telNum;

    @FxColumn(name = "CNTCR_EMAIL", size = 200, nullable = true, comment = "사업자담당자이메일")
    private String cntcrEmail;

    @FxColumn(name = "KEPCO_CUST_NUM", size = 200, nullable = true, comment = "한전고객번호")
    private String kepcoCustNum;

    @FxColumn(name = "KEPCO_PP_ID", size = 200, nullable = true, comment = "한전ID")
    private String kepcoPpId;

    @FxColumn(name = "KEPCO_PP_PWD", size = 200, nullable = true, comment = "한전PW")
    private String kepcoPpPwd;

    @FxColumn(name = "USER_ID", size = 200, comment = "고유번호")
    private String userId;

    @FxColumn(name = "COMPLEX_INLO_NO", size = 9, nullable = true, comment = "산단설치위치번호")
    private Integer complexInloNo;

    @FxColumn(name = "COMPANY_INLO_NO", size = 9, nullable = true, comment = "회사설치위치번호")
    private Integer companyInloNo;

    @FxColumn(name = "PLANT_INLO_NO", size = 9, nullable = true, comment = "공장설치위치번호")
    private Integer plantInloNo;

/**
 * @return 산업단지명
*/
public String getComplexName() { 
    return complexName;
}
/**
 * @param complexName 산업단지명
*/
public void setComplexName(String complexName) { 
    this.complexName = complexName;
}
/**
 * @return 업체명
*/
public String getCompanyName() { 
    return companyName;
}
/**
 * @param companyName 업체명
*/
public void setCompanyName(String companyName) { 
    this.companyName = companyName;
}
/**
 * @return 공장명
*/
public String getPlantName() { 
    return plantName;
}
/**
 * @param plantName 공장명
*/
public void setPlantName(String plantName) { 
    this.plantName = plantName;
}
/**
 * @return 업종
*/
public String getSectorClCds() { 
    return sectorClCds;
}
/**
 * @param sectorClCds 업종
*/
public void setSectorClCds(String sectorClCds) { 
    this.sectorClCds = sectorClCds;
}
/**
 * @return 대표업종번호
*/
public String getRepSectorClCd() { 
    return repSectorClCd;
}
/**
 * @param repSectorClCd 대표업종번호
*/
public void setRepSectorClCd(String repSectorClCd) { 
    this.repSectorClCd = repSectorClCd;
}
/**
 * @return 대표자
*/
public String getCeoName() { 
    return ceoName;
}
/**
 * @param ceoName 대표자
*/
public void setCeoName(String ceoName) { 
    this.ceoName = ceoName;
}
/**
 * @return 사업자등록번호
*/
public String getBizRegNum() { 
    return bizRegNum;
}
/**
 * @param bizRegNum 사업자등록번호
*/
public void setBizRegNum(String bizRegNum) { 
    this.bizRegNum = bizRegNum;
}
/**
 * @return 법인등록번호
*/
public String getCorpRegNum() { 
    return corpRegNum;
}
/**
 * @param corpRegNum 법인등록번호
*/
public void setCorpRegNum(String corpRegNum) { 
    this.corpRegNum = corpRegNum;
}
/**
 * @return 업체주소
*/
public String getAddr() { 
    return addr;
}
/**
 * @param addr 업체주소
*/
public void setAddr(String addr) { 
    this.addr = addr;
}
/**
 * @return 사업자담당자이름
*/
public String getCntcrName() { 
    return cntcrName;
}
/**
 * @param cntcrName 사업자담당자이름
*/
public void setCntcrName(String cntcrName) { 
    this.cntcrName = cntcrName;
}
/**
 * @return 사업자담당자연락처
*/
public String getTelNum() { 
    return telNum;
}
/**
 * @param telNum 사업자담당자연락처
*/
public void setTelNum(String telNum) { 
    this.telNum = telNum;
}
/**
 * @return 사업자담당자이메일
*/
public String getCntcrEmail() { 
    return cntcrEmail;
}
/**
 * @param cntcrEmail 사업자담당자이메일
*/
public void setCntcrEmail(String cntcrEmail) { 
    this.cntcrEmail = cntcrEmail;
}
/**
 * @return 한전고객번호
*/
public String getKepcoCustNum() { 
    return kepcoCustNum;
}
/**
 * @param kepcoCustNum 한전고객번호
*/
public void setKepcoCustNum(String kepcoCustNum) { 
    this.kepcoCustNum = kepcoCustNum;
}
/**
 * @return 한전ID
*/
public String getKepcoPpId() { 
    return kepcoPpId;
}
/**
 * @param kepcoPpId 한전ID
*/
public void setKepcoPpId(String kepcoPpId) { 
    this.kepcoPpId = kepcoPpId;
}
/**
 * @return 한전PW
*/
public String getKepcoPpPwd() { 
    return kepcoPpPwd;
}
/**
 * @param kepcoPpPwd 한전PW
*/
public void setKepcoPpPwd(String kepcoPpPwd) { 
    this.kepcoPpPwd = kepcoPpPwd;
}
/**
 * @return 고유번호
*/
public String getUserId() { 
    return userId;
}
/**
 * @param userId 고유번호
*/
public void setUserId(String userId) { 
    this.userId = userId;
}
/**
 * @return 산단설치위치번호
*/
public Integer getComplexInloNo() { 
    return complexInloNo;
}
/**
 * @param complexInloNo 산단설치위치번호
*/
public void setComplexInloNo(Integer complexInloNo) { 
    this.complexInloNo = complexInloNo;
}
/**
 * @return 회사설치위치번호
*/
public Integer getCompanyInloNo() { 
    return companyInloNo;
}
/**
 * @param companyInloNo 회사설치위치번호
*/
public void setCompanyInloNo(Integer companyInloNo) { 
    this.companyInloNo = companyInloNo;
}
/**
 * @return 공장설치위치번호
*/
public Integer getPlantInloNo() { 
    return plantInloNo;
}
/**
 * @param plantInloNo 공장설치위치번호
*/
public void setPlantInloNo(Integer plantInloNo) { 
    this.plantInloNo = plantInloNo;
}
}
