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


@FxTable(name = "VUP_COMM_PLANT", comment = "공장테이블")
@FxIndex(name = "VUP_COMM_PLANT__PK", type = INDEX_TYPE.PK, columns = {"PLANT_PID"})
@FxIndex(name = "VUP_COMM_PLANT__FK1", type = INDEX_TYPE.FK, columns = {"COMPLEX_PID"}, fkTable = "VUP_COMM_COMPLEX", fkColumn = "COMPLEX_PID")
public class VUP_COMM_PLANT implements Serializable {

public VUP_COMM_PLANT() {
 }

@FxColumn(name = "PLANT_PID", size = 10, comment = "공장PID")
private String plantPid;


@FxColumn(name = "PLANT_NAME", size = 50, comment = "공장명")
private String plantName;


@FxColumn(name = "PLANT_DESCR", size = 200, nullable = true, comment = "공장설명")
private String plantDescr;


@FxColumn(name = "COMPLEX_PID", size = 10, comment = "산단PID")
private String complexPid;


/**
* 공장PID
* @return 공장PID
*/
public String getPlantPid() {
return plantPid;
}
/**
* 공장PID
*@param plantPid 공장PID
*/
public void setPlantPid(String plantPid) {
	this.plantPid = plantPid;
}
/**
* 공장명
* @return 공장명
*/
public String getPlantName() {
return plantName;
}
/**
* 공장명
*@param plantName 공장명
*/
public void setPlantName(String plantName) {
	this.plantName = plantName;
}
/**
* 공장설명
* @return 공장설명
*/
public String getPlantDescr() {
return plantDescr;
}
/**
* 공장설명
*@param plantDescr 공장설명
*/
public void setPlantDescr(String plantDescr) {
	this.plantDescr = plantDescr;
}
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
}
