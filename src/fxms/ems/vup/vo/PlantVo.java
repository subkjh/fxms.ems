package fxms.ems.vup.vo;

import java.util.Map;

public class PlantVo extends VupVo {

	private Map<String, Object> plant;

	public PlantVo(Map<String, Object> plant) {
		this.plant = plant;
	}

	public void addSensor(Map<String, Object> sensor) {
		addList(this.plant, "sensor_list", sensor);
	}

	public void addUtFacility(Map<String, Object> ut) {
		addList(this.plant, "ut_facility_list", ut);
	}

	public Map<String, Object> getPlant() {
		return plant;
	}

	public String getPlantId() {
		return this.plant.get("factory_pid").toString();
	}

}
