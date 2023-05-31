package fxms.ems.bas.dbo;


import java.io.Serializable;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.13 19:00
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_FAC_PIPE_PATH", comment = "설비배관경로테이블")
@FxIndex(name = "FE_FAC_PIPE_PATH__PK", type = INDEX_TYPE.PK, columns = {"PIPE_ID", "LINK_SEQ"})
@FxIndex(name = "FE_FAC_PIPE_PATH__FK1", type = INDEX_TYPE.FK, columns = {"PIPE_ID"}, fkTable = "FE_FAC_PIPE", fkColumn = "PIPE_ID")
public class FE_FAC_PIPE_PATH implements Serializable {

public FE_FAC_PIPE_PATH() {
 }

@FxColumn(name = "PIPE_ID", size = 30, comment = "배관ID")
private String pipeId;


@FxColumn(name = "LINK_SEQ", size = 5, comment = "연결순서")
private Integer linkSeq;


@FxColumn(name = "LINK_OBJ_CL_CD", size = 10, comment = "연결객체구분코드", defValue = "NONE")
private String linkObjClCd = "NONE";


@FxColumn(name = "LINK_OBJ_ID", size = 100, nullable = true, comment = "연결객체ID")
private String linkObjId;


@FxColumn(name = "LINK_OBJ_NAME", size = 100, nullable = true, comment = "연결객체명")
private String linkObjName;


@FxColumn(name = "LINK_DESCR", size = 200, nullable = true, comment = "연결설명")
private String linkDescr;


@FxColumn(name = "LOC_LTD", size = 12, nullable = true, comment = "위치위도", defValue = "-1")
private Double locLtd = -1D;


@FxColumn(name = "LOC_LND", size = 12, nullable = true, comment = "위치경도", defValue = "-1")
private Double locLnd = -1D;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "-1")
private Integer regUserNo = -1;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "-1")
private Integer chgUserNo = -1;


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
* 연결순서
* @return 연결순서
*/
public Integer getLinkSeq() {
return linkSeq;
}
/**
* 연결순서
*@param linkSeq 연결순서
*/
public void setLinkSeq(Integer linkSeq) {
	this.linkSeq = linkSeq;
}
/**
* 연결객체구분코드
* @return 연결객체구분코드
*/
public String getLinkObjClCd() {
return linkObjClCd;
}
/**
* 연결객체구분코드
*@param linkObjClCd 연결객체구분코드
*/
public void setLinkObjClCd(String linkObjClCd) {
	this.linkObjClCd = linkObjClCd;
}
/**
* 연결객체ID
* @return 연결객체ID
*/
public String getLinkObjId() {
return linkObjId;
}
/**
* 연결객체ID
*@param linkObjId 연결객체ID
*/
public void setLinkObjId(String linkObjId) {
	this.linkObjId = linkObjId;
}
/**
* 연결객체명
* @return 연결객체명
*/
public String getLinkObjName() {
return linkObjName;
}
/**
* 연결객체명
*@param linkObjName 연결객체명
*/
public void setLinkObjName(String linkObjName) {
	this.linkObjName = linkObjName;
}
/**
* 연결설명
* @return 연결설명
*/
public String getLinkDescr() {
return linkDescr;
}
/**
* 연결설명
*@param linkDescr 연결설명
*/
public void setLinkDescr(String linkDescr) {
	this.linkDescr = linkDescr;
}
/**
* 위치위도
* @return 위치위도
*/
public Double getLocLtd() {
return locLtd;
}
/**
* 위치위도
*@param locLtd 위치위도
*/
public void setLocLtd(Double locLtd) {
	this.locLtd = locLtd;
}
/**
* 위치경도
* @return 위치경도
*/
public Double getLocLnd() {
return locLnd;
}
/**
* 위치경도
*@param locLnd 위치경도
*/
public void setLocLnd(Double locLnd) {
	this.locLnd = locLnd;
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
