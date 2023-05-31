package fxms.ems.bas.vo;

import java.util.Map;

import fxms.bas.fxo.FxmsUtil;
import fxms.bas.vo.Inlo;

public class PlantVo extends Inlo {

	public PlantVo(Map<String, Object> datas) throws Exception {
		super(datas);

		this.complexInloNo = FxmsUtil.getInt(datas, "complexInloNo", -1);
		this.companyInloNo = FxmsUtil.getInt(datas, "companyInloNo", -1);
		this.plantInloNo = FxmsUtil.getInt(datas, "plantInloNo", -1);

		this.complexName = FxmsUtil.getString(datas, "complexName", null);
		this.companyName = FxmsUtil.getString(datas, "companyName", null);
		this.plantName = FxmsUtil.getString(datas, "plantName", null);

		this.complexTid = FxmsUtil.getString(datas, "complexTid", null);
		this.companyTid = FxmsUtil.getString(datas, "companyTid", null);
		this.plantTid = FxmsUtil.getString(datas, "plantTid", null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4167641630715970740L;

	private final int complexInloNo;
	private final int companyInloNo;
	private final int plantInloNo;

	private final String complexTid;
	private final String companyTid;
	private final String plantTid;

	private final String complexName;
	private final String companyName;
	private final String plantName;

	public int getComplexInloNo() {
		return complexInloNo;
	}

	public int getCompanyInloNo() {
		return companyInloNo;
	}

	public int getPlantInloNo() {
		return plantInloNo;
	}

	public String getComplexTid() {
		return complexTid;
	}

	public String getCompanyTid() {
		return companyTid;
	}

	public String getPlantTid() {
		return plantTid;
	}

	public String getComplexName() {
		return complexName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getPlantName() {
		return plantName;
	}

}
