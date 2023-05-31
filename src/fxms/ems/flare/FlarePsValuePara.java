package fxms.ems.flare;

import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxTable;

@FxTable(name = "VIRTUAL_TABLE", comment = "가상테이블")
public class FlarePsValuePara {

	@FxColumn(name = "SENSOR_NAME", size = 100, comment = "센서명")
	private String sensorName;

	@FxColumn(name = "SENSOR_TYPE", size = 100, comment = "센서종류")
	private String sensorType;

	@FxColumn(name = "DTM", size = 100, comment = "수집일시")
	private String dtm;

	@FxColumn(name = "VALUE", size = 100, comment = "수집값")
	private float value;

	@FxColumn(name = "PS_ID", size = 100, comment = "수집내용")
	private String psId;

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getDtm() {
		return dtm;
	}

	public void setDtm(String dtm) {
		this.dtm = dtm;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getPsId() {
		return psId;
	}

	public void setPsId(String psId) {
		this.psId = psId;
	}

}
