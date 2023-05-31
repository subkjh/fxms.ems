package fxms.ems.vup.dbo.all;


import java.io.Serializable;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.23 16:07
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "VUP_COMM_PIPE", comment = "배관테이블")
@FxIndex(name = "VUP_COMM_PIPE__PK", type = INDEX_TYPE.PK, columns = {"PIPE_ID"})
@FxIndex(name = "VUP_COMM_PIPE__FK1", type = INDEX_TYPE.FK, columns = {"COMPLEX_PID"}, fkTable = "VUP_COMM_COMPLEX", fkColumn = "COMPLEX_PID")
public class VUP_COMM_PIPE implements Serializable {

public VUP_COMM_PIPE() {
 }

@FxColumn(name = "PIPE_ID", size = 30, comment = "배관ID")
private String pipeId;


@FxColumn(name = "PIPE_NAME", size = 100, comment = "배관명")
private String pipeName;


@FxColumn(name = "PIPE_DESCR", size = 200, nullable = true, comment = "배관설명")
private String pipeDescr;


@FxColumn(name = "COMPLEX_PID", size = 10, comment = "산단PID")
private String complexPid;


@FxColumn(name = "PLANT_PID", size = 10, nullable = true, comment = "공장PID", defValue = "NONE")
private String plantPid = "NONE";


@FxColumn(name = "ENERGY_ID", size = 10, comment = "에너지ID")
private String energyId;


@FxColumn(name = "PUBLIC_YN", size = 1, comment = "공용여부", defValue = "Y")
private String publicYn = "Y";


@FxColumn(name = "TRANS_ID", size = 10, comment = "거래ID")
private String transId;


@FxColumn(name = "PIPE_PATHS", size = 2000, nullable = true, comment = "배관경로")
private String pipePaths;


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
* 에너지ID
* @return 에너지ID
*/
public String getEnergyId() {
return energyId;
}
/**
* 에너지ID
*@param energyId 에너지ID
*/
public void setEnergyId(String energyId) {
	this.energyId = energyId;
}
/**
* 공용여부
* @return 공용여부
*/
public String isPublicYn() {
return publicYn;
}
/**
* 공용여부
*@param publicYn 공용여부
*/
public void setPublicYn(String publicYn) {
	this.publicYn = publicYn;
}
/**
* 거래ID
* @return 거래ID
*/
public String getTransId() {
return transId;
}
/**
* 거래ID
*@param transId 거래ID
*/
public void setTransId(String transId) {
	this.transId = transId;
}
/**
* 배관경로
* @return 배관경로
*/
public String getPipePaths() {
return pipePaths;
}
/**
* 배관경로
*@param pipePaths 배관경로
*/
public void setPipePaths(String pipePaths) {
	this.pipePaths = pipePaths;
}
}
