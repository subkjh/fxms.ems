package fxms.ems.bas.dbo;


import java.io.Serializable;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.13 15:08
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_FAC_PIPE", comment = "설비배관테이블")
@FxIndex(name = "FE_FAC_PIPE__PK", type = INDEX_TYPE.PK, columns = {"PIPE_ID"})
@FxIndex(name = "FE_FAC_PIPE__UK", type = INDEX_TYPE.UK, columns = {"PIPE_NAME"})
@FxIndex(name = "FE_FAC_PIPE__FK1", type = INDEX_TYPE.FK, columns = {"ENG_ID"}, fkTable = "FE_ENG_BAS", fkColumn = "ENG_ID")
public class FE_FAC_PIPE implements Serializable {

public FE_FAC_PIPE() {
 }

@FxColumn(name = "PIPE_ID", size = 30, comment = "배관ID")
private String pipeId;


@FxColumn(name = "PIPE_NAME", size = 100, comment = "배관명")
private String pipeName;


@FxColumn(name = "PIPE_CL_CD", size = 10, comment = "배관구분코드", defValue = "NONE")
private String pipeClCd = "NONE";


@FxColumn(name = "PIPE_DESCR", size = 200, nullable = true, comment = "배관설명")
private String pipeDescr;


@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID", defValue = "NONE")
private String engId = "NONE";


@FxColumn(name = "PIPE_CAPA", size = 20, nullable = true, comment = "배관용량")
private String pipeCapa;


@FxColumn(name = "INST_DATE", size = 8, nullable = true, comment = "설치일자")
private String instDate;


@FxColumn(name = "MNG_INLO_NO", size = 9, comment = "관리설치위치번호", defValue = "-1")
private Integer mngInloNo = -1;


@FxColumn(name = "LINK_INLO_NO", size = 9, comment = "연결설치위치번호", defValue = "-1")
private Integer linkInloNo = -1;


@FxColumn(name = "USE_EXPR_DATE", size = 8, nullable = true, comment = "사용만료일자")
private String useExprDate;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


/**
* 배관ID
* @return 배관ID
*/
public String getPipeId() {
return pipeId;
}
/**
* 배관ID
*@param pipeId 배관ID
*/
public void setPipeId(String pipeId) {
	this.pipeId = pipeId;
}
/**
* 배관명
* @return 배관명
*/
public String getPipeName() {
return pipeName;
}
/**
* 배관명
*@param pipeName 배관명
*/
public void setPipeName(String pipeName) {
	this.pipeName = pipeName;
}
/**
* 배관구분코드
* @return 배관구분코드
*/
public String getPipeClCd() {
return pipeClCd;
}
/**
* 배관구분코드
*@param pipeClCd 배관구분코드
*/
public void setPipeClCd(String pipeClCd) {
	this.pipeClCd = pipeClCd;
}
/**
* 배관설명
* @return 배관설명
*/
public String getPipeDescr() {
return pipeDescr;
}
/**
* 배관설명
*@param pipeDescr 배관설명
*/
public void setPipeDescr(String pipeDescr) {
	this.pipeDescr = pipeDescr;
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
* 배관용량
* @return 배관용량
*/
public String getPipeCapa() {
return pipeCapa;
}
/**
* 배관용량
*@param pipeCapa 배관용량
*/
public void setPipeCapa(String pipeCapa) {
	this.pipeCapa = pipeCapa;
}
/**
* 설치일자
* @return 설치일자
*/
public String getInstDate() {
return instDate;
}
/**
* 설치일자
*@param instDate 설치일자
*/
public void setInstDate(String instDate) {
	this.instDate = instDate;
}
/**
* 관리설치위치번호
* @return 관리설치위치번호
*/
public Integer getMngInloNo() {
return mngInloNo;
}
/**
* 관리설치위치번호
*@param mngInloNo 관리설치위치번호
*/
public void setMngInloNo(Integer mngInloNo) {
	this.mngInloNo = mngInloNo;
}
/**
* 연결설치위치번호
* @return 연결설치위치번호
*/
public Integer getLinkInloNo() {
return linkInloNo;
}
/**
* 연결설치위치번호
*@param linkInloNo 연결설치위치번호
*/
public void setLinkInloNo(Integer linkInloNo) {
	this.linkInloNo = linkInloNo;
}
/**
* 사용만료일자
* @return 사용만료일자
*/
public String getUseExprDate() {
return useExprDate;
}
/**
* 사용만료일자
*@param useExprDate 사용만료일자
*/
public void setUseExprDate(String useExprDate) {
	this.useExprDate = useExprDate;
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
