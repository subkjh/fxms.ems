package fxms.ems.vup.dbo.all;


import java.io.Serializable;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.13 14:02
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "VUP_COMM_COMPLEX", comment = "산단테이블")
@FxIndex(name = "VUP_COMM_COMPLEX__PK", type = INDEX_TYPE.PK, columns = {"COMPLEX_PID"})
public class VUP_COMM_COMPLEX implements Serializable {

public VUP_COMM_COMPLEX() {
 }

@FxColumn(name = "COMPLEX_PID", size = 10, comment = "산단PID")
private String complexPid;


@FxColumn(name = "COMPLEX_NAME", size = 50, comment = "산단명")
private String complexName;


@FxColumn(name = "COMPLEX_DESCR", size = 200, nullable = true, comment = "산단설명")
private String complexDescr;


/**
* 산단PID
* @return 산단PID
*/
public String getComplexPid() {
return complexPid;
}
/**
* 산단PID
*@param complexPid 산단PID
*/
public void setComplexPid(String complexPid) {
	this.complexPid = complexPid;
}
/**
* 산단명
* @return 산단명
*/
public String getComplexName() {
return complexName;
}
/**
* 산단명
*@param complexName 산단명
*/
public void setComplexName(String complexName) {
	this.complexName = complexName;
}
/**
* 산단설명
* @return 산단설명
*/
public String getComplexDescr() {
return complexDescr;
}
/**
* 산단설명
*@param complexDescr 산단설명
*/
public void setComplexDescr(String complexDescr) {
	this.complexDescr = complexDescr;
}
}
