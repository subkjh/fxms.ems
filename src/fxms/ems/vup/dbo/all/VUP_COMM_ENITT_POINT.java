package fxms.ems.vup.dbo.all;


import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.04.11 17:23
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "VUP_COMM_ENITT_POINT", comment = "애니트관제테이블")
@FxIndex(name = "VUP_COMM_ENITT_POINT__PK", type = INDEX_TYPE.PK, columns = {"POINT_PID"})
public class VUP_COMM_ENITT_POINT  {

public VUP_COMM_ENITT_POINT() {
 }

@FxColumn(name = "CONTAINER_NM", size = 200, nullable = true, comment = "컨테이너 이름")
private String containerNm;


@FxColumn(name = "CONTAINER_PID", size = 200, nullable = true, comment = "컨테이너 태그명(P&ID)")
private String containerPid;


@FxColumn(name = "DEVICE_NM", size = 200, nullable = true, comment = "설비명")
private String deviceNm;


@FxColumn(name = "DEVICE_PID", size = 200, nullable = true, comment = "설비 태그명(P&ID)")
private String devicePid;


@FxColumn(name = "POINT_NM", size = 200, nullable = true, comment = "관제점 명")
private String pointNm;


@FxColumn(name = "POINT_PID", size = 200, comment = "관제점 태그명 (P&ID)")
private String pointPid;


@FxColumn(name = "POINT_DESC", size = 200, nullable = true, comment = "관제점 설명")
private String pointDesc;


@FxColumn(name = "POINT_CLASS", size = 200, nullable = true, comment = "관제점 분류 (코드)")
private String pointClass;


@FxColumn(name = "POINT_TYPE", size = 200, nullable = true, comment = "관제 타입 (코드)")
private String pointType;


@FxColumn(name = "CONTROL_TYPE", size = 200, nullable = true, comment = "컨트롤 타입 (코드)")
private String controlType;


@FxColumn(name = "CONTROL_DATA", size = 200, nullable = true, comment = "컨트롤 데이터 (JSON)")
private String controlData;


@FxColumn(name = "USE_YN", size = 200, nullable = true, comment = "관제점 사용여부")
private String useYn;


@FxColumn(name = "MONITOR_YN", size = 200, nullable = true, comment = "실시간 모니터링 여부")
private String monitorYn;


@FxColumn(name = "PLANT_INLO_NO", size = 9, nullable = true, comment = "플랫폼공장번호")
private Integer plantInloNo;


@FxColumn(name = "MO_NO", size = 14, nullable = true, comment = "플랫폼관리대상번호")
private Long moNo;


@FxColumn(name = "MO_NAME", size = 200, nullable = true, comment = "플랫폼관리대상명")
private String moName;


@FxColumn(name = "MO_TYPE", size = 200, nullable = true, comment = "플랫폼관리대상종류")
private String moType;


@FxColumn(name = "FAC_NO", size = 9, nullable = true, comment = "플랫폼설비번호")
private Integer facNo;


@FxColumn(name = "PS_ID", size = 50, nullable = true, comment = "플랫폼성능항목ID")
private String psId;


/**
* 컨테이너 이름
* @return 컨테이너 이름
*/
public String getContainerNm() {
return containerNm;
}
/**
* 컨테이너 이름
*@param containerNm 컨테이너 이름
*/
public void setContainerNm(String containerNm) {
	this.containerNm = containerNm;
}
/**
* 컨테이너 태그명(P&ID)
* @return 컨테이너 태그명(P&ID)
*/
public String getContainerPid() {
return containerPid;
}
/**
* 컨테이너 태그명(P&ID)
*@param containerPid 컨테이너 태그명(P&ID)
*/
public void setContainerPid(String containerPid) {
	this.containerPid = containerPid;
}
/**
* 설비명
* @return 설비명
*/
public String getDeviceNm() {
return deviceNm;
}
/**
* 설비명
*@param deviceNm 설비명
*/
public void setDeviceNm(String deviceNm) {
	this.deviceNm = deviceNm;
}
/**
* 설비 태그명(P&ID)
* @return 설비 태그명(P&ID)
*/
public String getDevicePid() {
return devicePid;
}
/**
* 설비 태그명(P&ID)
*@param devicePid 설비 태그명(P&ID)
*/
public void setDevicePid(String devicePid) {
	this.devicePid = devicePid;
}
/**
* 관제점 명
* @return 관제점 명
*/
public String getPointNm() {
return pointNm;
}
/**
* 관제점 명
*@param pointNm 관제점 명
*/
public void setPointNm(String pointNm) {
	this.pointNm = pointNm;
}
/**
* 관제점 태그명 (P&ID)
* @return 관제점 태그명 (P&ID)
*/
public String getPointPid() {
return pointPid;
}
/**
* 관제점 태그명 (P&ID)
*@param pointPid 관제점 태그명 (P&ID)
*/
public void setPointPid(String pointPid) {
	this.pointPid = pointPid;
}
/**
* 관제점 설명
* @return 관제점 설명
*/
public String getPointDesc() {
return pointDesc;
}
/**
* 관제점 설명
*@param pointDesc 관제점 설명
*/
public void setPointDesc(String pointDesc) {
	this.pointDesc = pointDesc;
}
/**
* 관제점 분류 (코드)
* @return 관제점 분류 (코드)
*/
public String getPointClass() {
return pointClass;
}
/**
* 관제점 분류 (코드)
*@param pointClass 관제점 분류 (코드)
*/
public void setPointClass(String pointClass) {
	this.pointClass = pointClass;
}
/**
* 관제 타입 (코드)
* @return 관제 타입 (코드)
*/
public String getPointType() {
return pointType;
}
/**
* 관제 타입 (코드)
*@param pointType 관제 타입 (코드)
*/
public void setPointType(String pointType) {
	this.pointType = pointType;
}
/**
* 컨트롤 타입 (코드)
* @return 컨트롤 타입 (코드)
*/
public String getControlType() {
return controlType;
}
/**
* 컨트롤 타입 (코드)
*@param controlType 컨트롤 타입 (코드)
*/
public void setControlType(String controlType) {
	this.controlType = controlType;
}
/**
* 컨트롤 데이터 (JSON)
* @return 컨트롤 데이터 (JSON)
*/
public String getControlData() {
return controlData;
}
/**
* 컨트롤 데이터 (JSON)
*@param controlData 컨트롤 데이터 (JSON)
*/
public void setControlData(String controlData) {
	this.controlData = controlData;
}
/**
* 관제점 사용여부
* @return 관제점 사용여부
*/
public String isUseYn() {
return useYn;
}
/**
* 관제점 사용여부
*@param useYn 관제점 사용여부
*/
public void setUseYn(String useYn) {
	this.useYn = useYn;
}
/**
* 실시간 모니터링 여부
* @return 실시간 모니터링 여부
*/
public String isMonitorYn() {
return monitorYn;
}
/**
* 실시간 모니터링 여부
*@param monitorYn 실시간 모니터링 여부
*/
public void setMonitorYn(String monitorYn) {
	this.monitorYn = monitorYn;
}
/**
* 플랫폼공장번호
* @return 플랫폼공장번호
*/
public Integer getPlantInloNo() {
return plantInloNo;
}
/**
* 플랫폼공장번호
*@param plantInloNo 플랫폼공장번호
*/
public void setPlantInloNo(Integer plantInloNo) {
	this.plantInloNo = plantInloNo;
}
/**
* 플랫폼관리대상번호
* @return 플랫폼관리대상번호
*/
public Long getMoNo() {
return moNo;
}
/**
* 플랫폼관리대상번호
*@param moNo 플랫폼관리대상번호
*/
public void setMoNo(Long moNo) {
	this.moNo = moNo;
}
/**
* 플랫폼관리대상명
* @return 플랫폼관리대상명
*/
public String getMoName() {
return moName;
}
/**
* 플랫폼관리대상명
*@param moName 플랫폼관리대상명
*/
public void setMoName(String moName) {
	this.moName = moName;
}
/**
* 플랫폼관리대상종류
* @return 플랫폼관리대상종류
*/
public String getMoType() {
return moType;
}
/**
* 플랫폼관리대상종류
*@param moType 플랫폼관리대상종류
*/
public void setMoType(String moType) {
	this.moType = moType;
}
/**
* 플랫폼설비번호
* @return 플랫폼설비번호
*/
public Integer getFacNo() {
return facNo;
}
/**
* 플랫폼설비번호
*@param facNo 플랫폼설비번호
*/
public void setFacNo(Integer facNo) {
	this.facNo = facNo;
}
/**
* 플랫폼성능항목ID
* @return 플랫폼성능항목ID
*/
public String getPsId() {
return psId;
}
/**
* 플랫폼성능항목ID
*@param psId 플랫폼성능항목ID
*/
public void setPsId(String psId) {
	this.psId = psId;
}
}
