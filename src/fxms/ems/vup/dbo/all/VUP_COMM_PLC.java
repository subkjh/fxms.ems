package fxms.ems.vup.dbo.all;


import java.io.Serializable;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
* @since 2023.03.30 15:03
* @author subkjh 
* autometic create by subkjh.dao 
*
*/


@FxTable(name = "VUP_COMM_PLC", comment = "PLC테이블")
@FxIndex(name = "VUP_COMM_PLC__PK", type = INDEX_TYPE.PK, columns = {"PLC_NAME"})
public class VUP_COMM_PLC implements Serializable {

public VUP_COMM_PLC() {
 }

@FxColumn(name = "PLC_NAME", size = 100, comment = "PLC명")
private String plcName;


@FxColumn(name = "IP_ADDR", size = 50, nullable = true, comment = "IP주소")
private String ipAddr;


@FxColumn(name = "PLC_TYPE", size = 50, nullable = true, comment = "PLC유형")
private String plcType;


@FxColumn(name = "COMPLEX_PID", size = 10, comment = "산단PID")
private String complexPid;


@FxColumn(name = "PLANT_NAME", size = 100, comment = "공장명")
private String plantName;


@FxColumn(name = "LET_IP_ADDR", size = 50, nullable = true, comment = "LTE_IP주소")
private String letIpAddr;


@FxColumn(name = "PLC_NET_TYPE", size = 20, nullable = true, comment = "PLC망유형")
private String plcNetType;


@FxColumn(name = "PLC_MEMO", size = 200, nullable = true, comment = "PLC메모")
private String plcMemo;


/**
* PLC명
* @return PLC명
*/
public String getPlcName() {
return plcName;
}
/**
* PLC명
*@param plcName PLC명
*/
public void setPlcName(String plcName) {
	this.plcName = plcName;
}
/**
* IP주소
* @return IP주소
*/
public String getIpAddr() {
return ipAddr;
}
/**
* IP주소
*@param ipAddr IP주소
*/
public void setIpAddr(String ipAddr) {
	this.ipAddr = ipAddr;
}
/**
* PLC유형
* @return PLC유형
*/
public String getPlcType() {
return plcType;
}
/**
* PLC유형
*@param plcType PLC유형
*/
public void setPlcType(String plcType) {
	this.plcType = plcType;
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
* LTE_IP주소
* @return LTE_IP주소
*/
public String getLetIpAddr() {
return letIpAddr;
}
/**
* LTE_IP주소
*@param letIpAddr LTE_IP주소
*/
public void setLetIpAddr(String letIpAddr) {
	this.letIpAddr = letIpAddr;
}
/**
* PLC망유형
* @return PLC망유형
*/
public String getPlcNetType() {
return plcNetType;
}
/**
* PLC망유형
*@param plcNetType PLC망유형
*/
public void setPlcNetType(String plcNetType) {
	this.plcNetType = plcNetType;
}
/**
* PLC메모
* @return PLC메모
*/
public String getPlcMemo() {
return plcMemo;
}
/**
* PLC메모
*@param plcMemo PLC메모
*/
public void setPlcMemo(String plcMemo) {
	this.plcMemo = plcMemo;
}
}
