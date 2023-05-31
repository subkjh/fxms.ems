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


@FxTable(name = "VUP_COMM_TRANS", comment = "거래테이블")
@FxIndex(name = "VUP_COMM_TRANS__PK", type = INDEX_TYPE.PK, columns = {"TRANS_ID"})
public class VUP_COMM_TRANS implements Serializable {

public VUP_COMM_TRANS() {
 }

@FxColumn(name = "TRANS_ID", size = 10, comment = "거래ID")
private String transId;


@FxColumn(name = "TRANS_NAME", size = 50, comment = "거래명")
private String transName;


@FxColumn(name = "TRANS_DESCR", size = 200, comment = "거래설명")
private String transDescr;


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
* 거래명
* @return 거래명
*/
public String getTransName() {
return transName;
}
/**
* 거래명
*@param transName 거래명
*/
public void setTransName(String transName) {
	this.transName = transName;
}
/**
* 거래설명
* @return 거래설명
*/
public String getTransDescr() {
return transDescr;
}
/**
* 거래설명
*@param transDescr 거래설명
*/
public void setTransDescr(String transDescr) {
	this.transDescr = transDescr;
}
}
