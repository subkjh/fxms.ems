package fxms.ems.vup.dbo.all;


import java.io.Serializable;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.04.11 18:26
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "VUP_COMM_FACILITY", comment = "설비테이블")
@FxIndex(name = "VUP_COMM_FACILITY__PK", type = INDEX_TYPE.PK, columns = {"FAC_PID"})
public class VUP_COMM_FACILITY implements Serializable {

public VUP_COMM_FACILITY() {
 }

@FxColumn(name = "FAC_PID", size = 200, comment = "설비PID")
private String facPid;


@FxColumn(name = "FAC_NAME", size = 200, comment = "설비명")
private String facName;


@FxColumn(name = "FAC_TYPE", size = 200, comment = "설비유형")
private String facType;


@FxColumn(name = "FAC_SIZE_CAPACITY", size = 400, nullable = true, comment = "설비크기")
private String facSizeCapacity;


@FxColumn(name = "OPER_PRESS", size = 200, nullable = true, comment = "운용압력")
private String operPress;


@FxColumn(name = "OPER_TEMP", size = 200, nullable = true, comment = "운용온도")
private String operTemp;


@FxColumn(name = "DESG_PRESS", size = 200, nullable = true, comment = "설계압력")
private String desgPress;


@FxColumn(name = "DESG_TEMP", size = 200, nullable = true, comment = "설계온도")
private String desgTemp;


@FxColumn(name = "FAC_MEMO", size = 400, nullable = true, comment = "설비메모")
private String facMemo;


@FxColumn(name = "PLANT_NAME", size = 200, nullable = true, comment = "공장명")
private String plantName;


@FxColumn(name = "ENERGY_ID", size = 20, comment = "에너지ID")
private String energyId;


@FxColumn(name = "POINT_IDS", size = 400, nullable = true, comment = "관제점목록")
private String pointIds;


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
* 설비명
* @return 설비명
*/
public String getFacName() {
return facName;
}
/**
* 설비명
*@param facName 설비명
*/
public void setFacName(String facName) {
	this.facName = facName;
}
/**
* 설비유형
* @return 설비유형
*/
public String getFacType() {
return facType;
}
/**
* 설비유형
*@param facType 설비유형
*/
public void setFacType(String facType) {
	this.facType = facType;
}
/**
* 설비크기
* @return 설비크기
*/
public String getFacSizeCapacity() {
return facSizeCapacity;
}
/**
* 설비크기
*@param facSizeCapacity 설비크기
*/
public void setFacSizeCapacity(String facSizeCapacity) {
	this.facSizeCapacity = facSizeCapacity;
}
/**
* 운용압력
* @return 운용압력
*/
public String getOperPress() {
return operPress;
}
/**
* 운용압력
*@param operPress 운용압력
*/
public void setOperPress(String operPress) {
	this.operPress = operPress;
}
/**
* 운용온도
* @return 운용온도
*/
public String getOperTemp() {
return operTemp;
}
/**
* 운용온도
*@param operTemp 운용온도
*/
public void setOperTemp(String operTemp) {
	this.operTemp = operTemp;
}
/**
* 설계압력
* @return 설계압력
*/
public String getDesgPress() {
return desgPress;
}
/**
* 설계압력
*@param desgPress 설계압력
*/
public void setDesgPress(String desgPress) {
	this.desgPress = desgPress;
}
/**
* 설계온도
* @return 설계온도
*/
public String getDesgTemp() {
return desgTemp;
}
/**
* 설계온도
*@param desgTemp 설계온도
*/
public void setDesgTemp(String desgTemp) {
	this.desgTemp = desgTemp;
}
/**
* 설비메모
* @return 설비메모
*/
public String getFacMemo() {
return facMemo;
}
/**
* 설비메모
*@param facMemo 설비메모
*/
public void setFacMemo(String facMemo) {
	this.facMemo = facMemo;
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
* 관제점목록
* @return 관제점목록
*/
public String getPointIds() {
return pointIds;
}
/**
* 관제점목록
*@param pointIds 관제점목록
*/
public void setPointIds(String pointIds) {
	this.pointIds = pointIds;
}
}
