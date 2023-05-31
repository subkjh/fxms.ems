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


@FxTable(name = "VUP_COMM_ENERGY", comment = "에너지코드테이블")
@FxIndex(name = "VUP_COMM_ENERGY__PK", type = INDEX_TYPE.PK, columns = {"ENERGY_ID"})
public class VUP_COMM_ENERGY implements Serializable {

public VUP_COMM_ENERGY() {
 }

@FxColumn(name = "ENERGY_ID", size = 10, comment = "에너지ID")
private String energyId;


@FxColumn(name = "ENERGY_NAME", size = 50, comment = "에너지명")
private String energyName;


@FxColumn(name = "ENERGY_DESCR", size = 200, nullable = true, comment = "에너지설명")
private String energyDescr;


@FxColumn(name = "ENERGY_UNIT", size = 20, comment = "에너지단위")
private String energyUnit;


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
* 에너지명
* @return 에너지명
*/
public String getEnergyName() {
return energyName;
}
/**
* 에너지명
*@param energyName 에너지명
*/
public void setEnergyName(String energyName) {
	this.energyName = energyName;
}
/**
* 에너지설명
* @return 에너지설명
*/
public String getEnergyDescr() {
return energyDescr;
}
/**
* 에너지설명
*@param energyDescr 에너지설명
*/
public void setEnergyDescr(String energyDescr) {
	this.energyDescr = energyDescr;
}
/**
* 에너지단위
* @return 에너지단위
*/
public String getEnergyUnit() {
return energyUnit;
}
/**
* 에너지단위
*@param energyUnit 에너지단위
*/
public void setEnergyUnit(String energyUnit) {
	this.energyUnit = energyUnit;
}
}
