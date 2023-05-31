package fxms.ems.vup.dbo.all;



import java.io.Serializable;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.28 13:50
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "VUP_COMM_DEVICE", comment = "관제테이블")
@FxIndex(name = "VUP_COMM_DEVICE__PK", type = INDEX_TYPE.PK, columns = {"DEVICE_PID"})
public class VUP_COMM_DEVICE implements Serializable {

public VUP_COMM_DEVICE() {
 }

@FxColumn(name = "DEVICE_PID", size = 10, comment = "관제점PID")
private String devicePid;


@FxColumn(name = "DEVICE_NAME", size = 50, comment = "관제점명")
private String deviceName;


@FxColumn(name = "DEVICE_DESCR", size = 200, comment = "관제점설명")
private String deviceDescr;


@FxColumn(name = "DEVICE_UNIT", size = 50, nullable = true, comment = "관제점단위")
private String deviceUnit;


@FxColumn(name = "DEVICE_UNIT_DESCR", size = 200, nullable = true, comment = "관제점단위표시")
private String deviceUnitDescr;


@FxColumn(name = "ENERGY_ID", size = 20, comment = "에너지ID")
private String energyId;


@FxColumn(name = "PIPE_ID", size = 30, nullable = true, comment = "배관ID")
private String pipeId;


@FxColumn(name = "PLANT_PID", size = 10, comment = "공장PID")
private String plantPid;


@FxColumn(name = "FAC_PID", size = 200, nullable = true, comment = "설비PID")
private String facPid;


@FxColumn(name = "COMM_TYPE", size = 50, nullable = true, comment = "통신유형")
private String commType;


@FxColumn(name = "PS_IDS", size = 400, nullable = true, comment = "관제목록")
private String psIds;


@FxColumn(name = "DEVICE_TYPE", size = 50, nullable = true, comment = "관제검유형")
private String deviceType;


/**
* 관제점PID
* @return 관제점PID
*/
public String getDevicePid() {
return devicePid;
}
/**
* 관제점PID
*@param devicePid 관제점PID
*/
public void setDevicePid(String devicePid) {
	this.devicePid = devicePid;
}
/**
* 관제점명
* @return 관제점명
*/
public String getDeviceName() {
return deviceName;
}
/**
* 관제점명
*@param deviceName 관제점명
*/
public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
}
/**
* 관제점설명
* @return 관제점설명
*/
public String getDeviceDescr() {
return deviceDescr;
}
/**
* 관제점설명
*@param deviceDescr 관제점설명
*/
public void setDeviceDescr(String deviceDescr) {
	this.deviceDescr = deviceDescr;
}
/**
* 관제점단위
* @return 관제점단위
*/
public String getDeviceUnit() {
return deviceUnit;
}
/**
* 관제점단위
*@param deviceUnit 관제점단위
*/
public void setDeviceUnit(String deviceUnit) {
	this.deviceUnit = deviceUnit;
}
/**
* 관제점단위표시
* @return 관제점단위표시
*/
public String getDeviceUnitDescr() {
return deviceUnitDescr;
}
/**
* 관제점단위표시
*@param deviceUnitDescr 관제점단위표시
*/
public void setDeviceUnitDescr(String deviceUnitDescr) {
	this.deviceUnitDescr = deviceUnitDescr;
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
* 설비PID
* @return 설비PID
*/
public String getFacPid() {
return facPid;
}
/**
* 설비PID
*@param facPid 설비PID
*/
public void setFacPid(String facPid) {
	this.facPid = facPid;
}
/**
* 통신유형
* @return 통신유형
*/
public String getCommType() {
return commType;
}
/**
* 통신유형
*@param commType 통신유형
*/
public void setCommType(String commType) {
	this.commType = commType;
}
/**
* 관제목록
* @return 관제목록
*/
public String getPsIds() {
return psIds;
}
/**
* 관제목록
*@param psIds 관제목록
*/
public void setPsIds(String psIds) {
	this.psIds = psIds;
}
/**
* 관제검유형
* @return 관제검유형
*/
public String getDeviceType() {
return deviceType;
}
/**
* 관제검유형
*@param deviceType 관제검유형
*/
public void setDeviceType(String deviceType) {
	this.deviceType = deviceType;
}
}
