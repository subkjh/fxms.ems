package fxms.ems.bas.dbo;


import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.04.28 14:44
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "FE_ENG_RT_BAS", comment = "에너지루트기본테이블")
@FxIndex(name = "FE_ENG_RT_BAS__PK", type = INDEX_TYPE.PK, columns = {"ENG_RT_ID"})
@FxIndex(name = "FE_ENG_RT_BAS__FK1", type = INDEX_TYPE.FK, columns = {"ENG_ID"}, fkTable = "FE_ENG_BAS", fkColumn = "ENG_ID")
public class FE_ENG_RT_BAS  {

public FE_ENG_RT_BAS() {
 }

@FxColumn(name = "ENG_RT_ID", size = 30, comment = "에너지루트ID")
private String engRtId;


@FxColumn(name = "ENG_ID", size = 20, comment = "에너지ID")
private String engId;


@FxColumn(name = "ENG_RT_DESCR", size = 400, nullable = true, comment = "에너지루트설명")
private String engRtDescr;


@FxColumn(name = "INLO_NO", size = 9, nullable = true, comment = "설치위치(산단)")
private Integer inloNo;


@FxColumn(name = "INLO_NAME", size = 100, nullable = true, comment = "설치위치명")
private String inloName;


@FxColumn(name = "STRT_INLO_NO", size = 9, nullable = true, comment = "시작설치위치번호")
private Integer strtInloNo;


@FxColumn(name = "STRT_INLO_NAME", size = 100, nullable = true, comment = "시작설치위치명")
private String strtInloName;


@FxColumn(name = "STRT_FAC_NO", size = 14, nullable = true, comment = "시작설비번호", defValue = "-1")
private Long strtFacNo = -1L;


@FxColumn(name = "STRT_FAC_NAME", size = 100, nullable = true, comment = "시작설비명")
private String strtFacName;


@FxColumn(name = "FNSH_INLO_NO", size = 9, nullable = true, comment = "종료설치위치번호")
private Integer fnshInloNo;


@FxColumn(name = "FNSH_INLO_NAME", size = 100, nullable = true, comment = "종료설치위치명")
private String fnshInloName;


@FxColumn(name = "FNSH_FAC_NO", size = 14, nullable = true, comment = "종료설비번호", defValue = "-1")
private Long fnshFacNo = -1L;


@FxColumn(name = "FNSH_FAC_NAME", size = 100, nullable = true, comment = "종료설비명")
private String fnshFacName;


@FxColumn(name = "SENSOR_MO_NO", size = 19, nullable = true, comment = "계측기MO번호")
private Long sensorMoNo;


@FxColumn(name = "USE_YN", size = 1, comment = "사용여부", defValue = "Y")
private String useYn = "Y";


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
* 에너지루트설명
* @return 에너지루트설명
*/
public String getEngRtDescr() {
return engRtDescr;
}
/**
* 에너지루트설명
*@param engRtDescr 에너지루트설명
*/
public void setEngRtDescr(String engRtDescr) {
	this.engRtDescr = engRtDescr;
}
/**
* 설치위치(산단)
* @return 설치위치(산단)
*/
public Integer getInloNo() {
return inloNo;
}
/**
* 설치위치(산단)
*@param inloNo 설치위치(산단)
*/
public void setInloNo(Integer inloNo) {
	this.inloNo = inloNo;
}
/**
* 설치위치명
* @return 설치위치명
*/
public String getInloName() {
return inloName;
}
/**
* 설치위치명
*@param inloName 설치위치명
*/
public void setInloName(String inloName) {
	this.inloName = inloName;
}
/**
* 시작설치위치번호
* @return 시작설치위치번호
*/
public Integer getStrtInloNo() {
return strtInloNo;
}
/**
* 시작설치위치번호
*@param strtInloNo 시작설치위치번호
*/
public void setStrtInloNo(Integer strtInloNo) {
	this.strtInloNo = strtInloNo;
}
/**
* 시작설치위치명
* @return 시작설치위치명
*/
public String getStrtInloName() {
return strtInloName;
}
/**
* 시작설치위치명
*@param strtInloName 시작설치위치명
*/
public void setStrtInloName(String strtInloName) {
	this.strtInloName = strtInloName;
}
/**
* 시작설비번호
* @return 시작설비번호
*/
public Long getStrtFacNo() {
return strtFacNo;
}
/**
* 시작설비번호
*@param strtFacNo 시작설비번호
*/
public void setStrtFacNo(Long strtFacNo) {
	this.strtFacNo = strtFacNo;
}
/**
* 시작설비명
* @return 시작설비명
*/
public String getStrtFacName() {
return strtFacName;
}
/**
* 시작설비명
*@param strtFacName 시작설비명
*/
public void setStrtFacName(String strtFacName) {
	this.strtFacName = strtFacName;
}
/**
* 종료설치위치번호
* @return 종료설치위치번호
*/
public Integer getFnshInloNo() {
return fnshInloNo;
}
/**
* 종료설치위치번호
*@param fnshInloNo 종료설치위치번호
*/
public void setFnshInloNo(Integer fnshInloNo) {
	this.fnshInloNo = fnshInloNo;
}
/**
* 종료설치위치명
* @return 종료설치위치명
*/
public String getFnshInloName() {
return fnshInloName;
}
/**
* 종료설치위치명
*@param fnshInloName 종료설치위치명
*/
public void setFnshInloName(String fnshInloName) {
	this.fnshInloName = fnshInloName;
}
/**
* 종료설비번호
* @return 종료설비번호
*/
public Long getFnshFacNo() {
return fnshFacNo;
}
/**
* 종료설비번호
*@param fnshFacNo 종료설비번호
*/
public void setFnshFacNo(Long fnshFacNo) {
	this.fnshFacNo = fnshFacNo;
}
/**
* 종료설비명
* @return 종료설비명
*/
public String getFnshFacName() {
return fnshFacName;
}
/**
* 종료설비명
*@param fnshFacName 종료설비명
*/
public void setFnshFacName(String fnshFacName) {
	this.fnshFacName = fnshFacName;
}
/**
* 계측기MO번호
* @return 계측기MO번호
*/
public Long getSensorMoNo() {
return sensorMoNo;
}
/**
* 계측기MO번호
*@param sensorMoNo 계측기MO번호
*/
public void setSensorMoNo(Long sensorMoNo) {
	this.sensorMoNo = sensorMoNo;
}
/**
* 사용여부
* @return 사용여부
*/
public String isUseYn() {
return useYn;
}
/**
* 사용여부
*@param useYn 사용여부
*/
public void setUseYn(String useYn) {
	this.useYn = useYn;
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
