package fxms.ems.cems.dto;

import java.util.List;
import java.util.Map;

import fxms.ems.bas.mo.SensorMo;

public class TsdbDto {

	public String psTable;

	public String energy;

	public List<String> measurements;

	public PsRangeDto psRange;

	public Map<String, SensorMo> sensorMap;

}
