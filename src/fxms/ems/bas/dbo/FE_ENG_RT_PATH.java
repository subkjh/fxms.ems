package fxms.ems.bas.dbo;


import java.io.Serializable;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.04.05 12:45
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_RT_PATH", comment = "에너지루트경로테이블")
@FxIndex(name = "FE_ENG_RT_PATH__PK", type = INDEX_TYPE.PK, columns = {"ENG_RT_ID", "RT_SEQ"})
@FxIndex(name = "FE_ENG_RT_PATH__FK1", type = INDEX_TYPE.FK, columns = {"ENG_RT_ID"}, fkTable = "FE_ENG_RT_BAS", fkColumn = "ENG_RT_ID")
@FxIndex(name = "FE_ENG_RT_PATH__FK2", type = INDEX_TYPE.FK, columns = {"PIPE_ID"}, fkTable = "FE_FAC_PIPE", fkColumn = "PIPE_ID")
public class FE_ENG_RT_PATH implements Serializable {

public FE_ENG_RT_PATH() {
 }

@FxColumn(name = "ENG_RT_ID", size = 30, comment = "에너지루트ID")
private String engRtId;


@FxColumn(name = "RT_SEQ", size = 5, comment = "라우팅순서")
private Integer rtSeq;


@FxColumn(name = "PIPE_ID", size = 30, comment = "배관ID")
private String pipeId;


@FxColumn(name = "PIPE_DESCR", size = 400, nullable = true, comment = "배관설명")
private String pipeDescr;


@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
private Integer regUserNo = 0;


@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
private Long regDtm;


@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
private Integer chgUserNo = 0;


@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
private Long chgDtm;


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
* 라우팅순서
* @return 라우팅순서
*/
public Integer getRtSeq() {
return rtSeq;
}
/**
* 라우팅순서
*@param rtSeq 라우팅순서
*/
public void setRtSeq(Integer rtSeq) {
	this.rtSeq = rtSeq;
}
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
