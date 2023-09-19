package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.08.09 10:40
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_FAC_BAS", comment = "설비기본테이블")
@FxIndex(name = "FE_FAC_BAS__PK", type = INDEX_TYPE.PK, columns = {"FAC_NO"})
@FxIndex(name = "FE_FAC_BAS__KEY1", type = INDEX_TYPE.KEY, columns = {"FAC_CL_CD"})
@FxIndex(name = "FE_FAC_BAS__KEY2", type = INDEX_TYPE.KEY, columns = {"FAC_TID"})
@FxIndex(name = "FE_FAC_BAS__FK1", type = INDEX_TYPE.FK, columns = {"ENG_ID"}, fkTable = "FE_ENG_BAS", fkColumn = "ENG_ID")
public class FE_FAC_BAS  {

public FE_FAC_BAS() {
 }

public static final String FX_SEQ_FACNO  = "FX_SEQ_FACNO"; 
    @FxColumn(name = "FAC_NO", size = 14, comment = "설비번호", sequence = "FX_SEQ_FACNO")
    private Long facNo;

    @FxColumn(name = "FAC_NAME", size = 100, comment = "설비명")
    private String facName;

    @FxColumn(name = "FAC_CL_CD", size = 20, comment = "설비분류코드", defValue = "NONE")
    private String facClCd = "NONE";

    @FxColumn(name = "FAC_TID", size = 50, nullable = true, comment = "설비TID")
    private String facTid;

    @FxColumn(name = "FAC_TYPE", size = 30, nullable = true, comment = "설비유형")
    private String facType;

    @FxColumn(name = "FAC_DESCR", size = 400, nullable = true, comment = "설비설명")
    private String facDescr;

    @FxColumn(name = "OWNER_FAC_NO", size = 19, comment = "소유설비번호", defValue = "0")
    private Long ownerFacNo = 0L;

    @FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID", defValue = "NONE")
    private String engId = "NONE";

    @FxColumn(name = "PIPE_ID", size = 30, nullable = true, comment = "배관ID")
    private String pipeId;

    @FxColumn(name = "INLO_NO", size = 9, comment = "설치위치번호", defValue = "0")
    private Integer inloNo = 0;

    @FxColumn(name = "PROC_TYPE_CD", size = 20, comment = "공정유형코드", defValue = "NONE")
    private String procTypeCd = "NONE";

    @FxColumn(name = "PROD_ITEM_CD", size = 20, comment = "생산항목코드", defValue = "NONE")
    private String prodItemCd = "NONE";

    @FxColumn(name = "FNSH_PROC_YN", size = 1, comment = "종료공정여부", defValue = "N")
    private String fnshProcYn = "N";

    @FxColumn(name = "ENG_EFF_LVL_CD", size = 20, comment = "에너지효율등급코드", defValue = "NONE")
    private String engEffLvlCd = "NONE";

    @FxColumn(name = "INST_YMD", size = 8, nullable = true, comment = "설치년월일")
    private String instYmd;

    @FxColumn(name = "DESGN_CAPA", size = 200, nullable = true, comment = "설계용량")
    private String desgnCapa;

    @FxColumn(name = "OPER_CAPA", size = 200, nullable = true, comment = "운영용량")
    private String operCapa;

    @FxColumn(name = "CAPA_UNIT", size = 30, nullable = true, comment = "용량단위")
    private String capaUnit;

    @FxColumn(name = "FAC_FMT", size = 100, nullable = true, comment = "설비형식")
    private String facFmt;

    @FxColumn(name = "VENDR_NAME", size = 100, nullable = true, comment = "제조사명")
    private String vendrName;

    @FxColumn(name = "MODEL_NAME", size = 100, nullable = true, comment = "모델명")
    private String modelName;

    @FxColumn(name = "MNTN_CMPY_NAME", size = 100, nullable = true, comment = "유지보수회사명")
    private String mntnCmpyName;

    @FxColumn(name = "MNTN_ADDR", size = 100, nullable = true, comment = "유지보수소재지")
    private String mntnAddr;

    @FxColumn(name = "MNTN_CNTC_NAME", size = 100, nullable = true, comment = "유지보수연락처명")
    private String mntnCntcName;

    @FxColumn(name = "MNTN_CNTC_INFO", size = 100, nullable = true, comment = "유지보수연락처정보")
    private String mntnCntcInfo;

    @FxColumn(name = "FAC_SIZE", size = 200, nullable = true, comment = "설비크기")
    private String facSize;

    @FxColumn(name = "FAC_POWER", size = 200, nullable = true, comment = "설비전력")
    private String facPower;

    @FxColumn(name = "FAC_ST_CD", size = 5, comment = "설비상태코드", defValue = "NONE")
    private String facStCd = "NONE";

    @FxColumn(name = "FAC_ST_CHG_DTM", size = 14, nullable = true, comment = "설비상태변경일시")
    private Long facStChgDtm;

    @FxColumn(name = "DEL_YN", size = 1, comment = "삭제여부", defValue = "N")
    private String delYn = "N";

    @FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
    private Integer regUserNo = 0;

    @FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
    private Long regDtm;

    @FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
    private Integer chgUserNo = 0;

    @FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
    private Long chgDtm;

/**
 * @return 설비번호
*/
public Long getFacNo() { 
    return facNo;
}
/**
 * @param facNo 설비번호
*/
public void setFacNo(Long facNo) { 
    this.facNo = facNo;
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
 * @return 설비설명
*/
public String getFacDescr() { 
    return facDescr;
}
/**
 * @param facDescr 설비설명
*/
public void setFacDescr(String facDescr) { 
    this.facDescr = facDescr;
}
/**
 * @return 소유설비번호
*/
public Long getOwnerFacNo() { 
    return ownerFacNo;
}
/**
 * @param ownerFacNo 소유설비번호
*/
public void setOwnerFacNo(Long ownerFacNo) { 
    this.ownerFacNo = ownerFacNo;
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
 * @return 배관ID
*/
public String getPipeId() { 
    return pipeId;
}
/**
 * @param pipeId 배관ID
*/
public void setPipeId(String pipeId) { 
    this.pipeId = pipeId;
}
/**
 * @return 설치위치번호
*/
public Integer getInloNo() { 
    return inloNo;
}
/**
 * @param inloNo 설치위치번호
*/
public void setInloNo(Integer inloNo) { 
    this.inloNo = inloNo;
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
 * @return 생산항목코드
*/
public String getProdItemCd() { 
    return prodItemCd;
}
/**
 * @param prodItemCd 생산항목코드
*/
public void setProdItemCd(String prodItemCd) { 
    this.prodItemCd = prodItemCd;
}
/**
 * @return 종료공정여부
*/
public String isFnshProcYn() { 
    return fnshProcYn;
}
/**
 * @param fnshProcYn 종료공정여부
*/
public void setFnshProcYn(String fnshProcYn) { 
    this.fnshProcYn = fnshProcYn;
}
/**
 * @return 에너지효율등급코드
*/
public String getEngEffLvlCd() { 
    return engEffLvlCd;
}
/**
 * @param engEffLvlCd 에너지효율등급코드
*/
public void setEngEffLvlCd(String engEffLvlCd) { 
    this.engEffLvlCd = engEffLvlCd;
}
/**
 * @return 설치년월일
*/
public String getInstYmd() { 
    return instYmd;
}
/**
 * @param instYmd 설치년월일
*/
public void setInstYmd(String instYmd) { 
    this.instYmd = instYmd;
}
/**
 * @return 설계용량
*/
public String getDesgnCapa() { 
    return desgnCapa;
}
/**
 * @param desgnCapa 설계용량
*/
public void setDesgnCapa(String desgnCapa) { 
    this.desgnCapa = desgnCapa;
}
/**
 * @return 운영용량
*/
public String getOperCapa() { 
    return operCapa;
}
/**
 * @param operCapa 운영용량
*/
public void setOperCapa(String operCapa) { 
    this.operCapa = operCapa;
}
/**
 * @return 용량단위
*/
public String getCapaUnit() { 
    return capaUnit;
}
/**
 * @param capaUnit 용량단위
*/
public void setCapaUnit(String capaUnit) { 
    this.capaUnit = capaUnit;
}
/**
 * @return 설비형식
*/
public String getFacFmt() { 
    return facFmt;
}
/**
 * @param facFmt 설비형식
*/
public void setFacFmt(String facFmt) { 
    this.facFmt = facFmt;
}
/**
 * @return 제조사명
*/
public String getVendrName() { 
    return vendrName;
}
/**
 * @param vendrName 제조사명
*/
public void setVendrName(String vendrName) { 
    this.vendrName = vendrName;
}
/**
 * @return 모델명
*/
public String getModelName() { 
    return modelName;
}
/**
 * @param modelName 모델명
*/
public void setModelName(String modelName) { 
    this.modelName = modelName;
}
/**
 * @return 유지보수회사명
*/
public String getMntnCmpyName() { 
    return mntnCmpyName;
}
/**
 * @param mntnCmpyName 유지보수회사명
*/
public void setMntnCmpyName(String mntnCmpyName) { 
    this.mntnCmpyName = mntnCmpyName;
}
/**
 * @return 유지보수소재지
*/
public String getMntnAddr() { 
    return mntnAddr;
}
/**
 * @param mntnAddr 유지보수소재지
*/
public void setMntnAddr(String mntnAddr) { 
    this.mntnAddr = mntnAddr;
}
/**
 * @return 유지보수연락처명
*/
public String getMntnCntcName() { 
    return mntnCntcName;
}
/**
 * @param mntnCntcName 유지보수연락처명
*/
public void setMntnCntcName(String mntnCntcName) { 
    this.mntnCntcName = mntnCntcName;
}
/**
 * @return 유지보수연락처정보
*/
public String getMntnCntcInfo() { 
    return mntnCntcInfo;
}
/**
 * @param mntnCntcInfo 유지보수연락처정보
*/
public void setMntnCntcInfo(String mntnCntcInfo) { 
    this.mntnCntcInfo = mntnCntcInfo;
}
/**
 * @return 설비크기
*/
public String getFacSize() { 
    return facSize;
}
/**
 * @param facSize 설비크기
*/
public void setFacSize(String facSize) { 
    this.facSize = facSize;
}
/**
 * @return 설비전력
*/
public String getFacPower() { 
    return facPower;
}
/**
 * @param facPower 설비전력
*/
public void setFacPower(String facPower) { 
    this.facPower = facPower;
}
/**
 * @return 설비상태코드
*/
public String getFacStCd() { 
    return facStCd;
}
/**
 * @param facStCd 설비상태코드
*/
public void setFacStCd(String facStCd) { 
    this.facStCd = facStCd;
}
/**
 * @return 설비상태변경일시
*/
public Long getFacStChgDtm() { 
    return facStChgDtm;
}
/**
 * @param facStChgDtm 설비상태변경일시
*/
public void setFacStChgDtm(Long facStChgDtm) { 
    this.facStChgDtm = facStChgDtm;
}
/**
 * @return 삭제여부
*/
public String isDelYn() { 
    return delYn;
}
/**
 * @param delYn 삭제여부
*/
public void setDelYn(String delYn) { 
    this.delYn = delYn;
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
