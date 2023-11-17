package fxms.ems.cems.dbo;


import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.10.04 16:10
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "CEMS_FAC_RAW", comment = "CEMS설비원천내역")
@FxIndex(name = "CEMS_FAC_RAW__PK", type = INDEX_TYPE.PK, columns = {"COMPLEX_NAME","COMPANY_NAME","PLANT_NAME","OPC_METER_ID"})
public class CEMS_FAC_RAW  {

public CEMS_FAC_RAW() {
 }

    @FxColumn(name = "COMPLEX_NAME", size = 100, nullable = true, comment = "산단명")
    private String complexName;

    @FxColumn(name = "COMPANY_NAME", size = 100, nullable = true, comment = "회사명")
    private String companyName;

    @FxColumn(name = "PLANT_NAME", size = 100, nullable = true, comment = "공장")
    private String plantName;

    @FxColumn(name = "PROCESS_NAME", size = 200, nullable = true, comment = "공정")
    private String processName;

    @FxColumn(name = "FAC_TYPE", size = 200, nullable = true, comment = "설비유형")
    private String facType;

    @FxColumn(name = "FAC_NAME", size = 200, nullable = true, comment = "설비명")
    private String facName;

    @FxColumn(name = "TAG", size = 200, nullable = true, comment = "구분")
    private String tag;

    @FxColumn(name = "VOLTAGE", size = 200, nullable = true, comment = "전압(V)")
    private String voltage;

    @FxColumn(name = "WIRING", size = 200, nullable = true, comment = "배선")
    private String wiring;

    @FxColumn(name = "CIR_BRK_CAPA", size = 200, nullable = true, comment = "차단기용량(A)")
    private String cirBrkCapa;

    @FxColumn(name = "GW_IP", size = 200, nullable = true, comment = "GW IP")
    private String gwIp;

    @FxColumn(name = "OPC_METER_ID", size = 50, nullable = true, comment = "OPC Meter ID")
    private String opcMeterId;

    @FxColumn(name = "COMPLEX_INLO_NO", size = 9, nullable = true, comment = "산단설치위치번호")
    private Integer complexInloNo;

    @FxColumn(name = "COMPANY_INLO_NO", size = 9, nullable = true, comment = "회사설치위치번호")
    private Integer companyInloNo;

    @FxColumn(name = "PLANT_INLO_NO", size = 9, nullable = true, comment = "공장설치위치번호")
    private Integer plantInloNo;

    @FxColumn(name = "PROC_TYPE_CD", size = 20, nullable = true, comment = "공정유형코드")
    private String procTypeCd;

    @FxColumn(name = "FAC_CL_CD", size = 20, nullable = true, comment = "설비분류코드")
    private String facClCd;

    @FxColumn(name = "FAC_TID", size = 100, nullable = true, comment = "설비TID")
    private String facTid;

    @FxColumn(name = "MO_TID", size = 100, nullable = true, comment = "관리대상TID")
    private String moTid;

    @FxColumn(name = "MO_TYPE", size = 30, nullable = true, comment = "관리대상유형")
    private String moType;

/**
 * @return 산단명
*/
public String getComplexName() { 
    return complexName;
}
/**
 * @param complexName 산단명
*/
public void setComplexName(String complexName) { 
    this.complexName = complexName;
}
/**
 * @return 회사명
*/
public String getCompanyName() { 
    return companyName;
}
/**
 * @param companyName 회사명
*/
public void setCompanyName(String companyName) { 
    this.companyName = companyName;
}
/**
 * @return 공장
*/
public String getPlantName() { 
    return plantName;
}
/**
 * @param plantName 공장
*/
public void setPlantName(String plantName) { 
    this.plantName = plantName;
}
/**
 * @return 공정
*/
public String getProcessName() { 
    return processName;
}
/**
 * @param processName 공정
*/
public void setProcessName(String processName) { 
    this.processName = processName;
}
/**
 * @return 설비유형
*/
public String getFacType() { 
    return facType;
}
/**
 * @param facType 설비유형
*/
public void setFacType(String facType) { 
    this.facType = facType;
}
/**
 * @return 설비명
*/
public String getFacName() { 
    return facName;
}
/**
 * @param facName 설비명
*/
public void setFacName(String facName) { 
    this.facName = facName;
}
/**
 * @return 구분
*/
public String getTag() { 
    return tag;
}
/**
 * @param tag 구분
*/
public void setTag(String tag) { 
    this.tag = tag;
}
/**
 * @return 전압(V)
*/
public String getVoltage() { 
    return voltage;
}
/**
 * @param voltage 전압(V)
*/
public void setVoltage(String voltage) { 
    this.voltage = voltage;
}
/**
 * @return 배선
*/
public String getWiring() { 
    return wiring;
}
/**
 * @param wiring 배선
*/
public void setWiring(String wiring) { 
    this.wiring = wiring;
}
/**
 * @return 차단기용량(A)
*/
public String getCirBrkCapa() { 
    return cirBrkCapa;
}
/**
 * @param cirBrkCapa 차단기용량(A)
*/
public void setCirBrkCapa(String cirBrkCapa) { 
    this.cirBrkCapa = cirBrkCapa;
}
/**
 * @return GW IP
*/
public String getGwIp() { 
    return gwIp;
}
/**
 * @param gwIp GW IP
*/
public void setGwIp(String gwIp) { 
    this.gwIp = gwIp;
}
/**
 * @return OPC Meter ID
*/
public String getOpcMeterId() { 
    return opcMeterId;
}
/**
 * @param opcMeterId OPC Meter ID
*/
public void setOpcMeterId(String opcMeterId) { 
    this.opcMeterId = opcMeterId;
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
/**
 * @return 공정유형코드
*/
public String getProcTypeCd() { 
    return procTypeCd;
}
/**
 * @param procTypeCd 공정유형코드
*/
public void setProcTypeCd(String procTypeCd) { 
    this.procTypeCd = procTypeCd;
}
/**
 * @return 설비분류코드
*/
public String getFacClCd() { 
    return facClCd;
}
/**
 * @param facClCd 설비분류코드
*/
public void setFacClCd(String facClCd) { 
    this.facClCd = facClCd;
}
/**
 * @return 설비TID
*/
public String getFacTid() { 
    return facTid;
}
/**
 * @param facTid 설비TID
*/
public void setFacTid(String facTid) { 
    this.facTid = facTid;
}
/**
 * @return 관리대상TID
*/
public String getMoTid() { 
    return moTid;
}
/**
 * @param moTid 관리대상TID
*/
public void setMoTid(String moTid) { 
    this.moTid = moTid;
}
/**
 * @return 관리대상유형
*/
public String getMoType() { 
    return moType;
}
/**
 * @param moType 관리대상유형
*/
public void setMoType(String moType) { 
    this.moType = moType;
}
}
