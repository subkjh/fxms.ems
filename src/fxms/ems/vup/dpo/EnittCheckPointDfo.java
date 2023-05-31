package fxms.ems.vup.dpo;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import fxms.bas.impl.dbo.all.FX_CF_DATA;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.mo.Mo;
import fxms.bas.vo.Inlo;
import fxms.ems.bas.dbo.FE_FAC_BAS;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;

/**
 * 애니트의 관제점 데이터를 확인한다.
 * 
 * @author subkjh
 *
 */
public class EnittCheckPointDfo implements FxDfo<List<VUP_COMM_ENITT_POINT>, Boolean> {

	private List<FX_CF_DATA> pointCfList;
	private List<FX_CF_DATA> deviceCfList;

	public EnittCheckPointDfo() throws Exception {
		this.pointCfList = VupApi.getApi().selectDatas("vup.point.tag");
		this.deviceCfList = VupApi.getApi().selectDatas("vup.device.tag");

	}

	@Override
	public Boolean call(FxFact fact, List<VUP_COMM_ENITT_POINT> data) throws Exception {
		return check(data);
	}

	public boolean check(List<VUP_COMM_ENITT_POINT> points) throws Exception {

		Map<String, Mo> moTidMap = VupApi.getApi().getMoTidMap();
		Map<String, FE_FAC_BAS> facMap = VupApi.getApi().getFacTidMap();
		Inlo inlo;
		Mo mo;
		FX_CF_DATA cfData;

		for (VUP_COMM_ENITT_POINT point : points) {

			point.setPsId(null);
			point.setMoNo(null);
			point.setMoName(null);
			point.setMoType(null);
			point.setFacNo(null);
			point.setPlantInloNo(null);

			mo = moTidMap.get(point.getDevicePid());

			if (mo != null) {
				point.setMoNo(mo.getMoNo());
				point.setMoName(mo.getMoName());
				point.setMoType(mo.getMoType());

				cfData = getPointCfData(point);
				if (cfData != null) {
					point.setPsId(cfData.getDataName1());
				}

			} else {

				cfData = getDeviceCfData(point);
				if (cfData != null) {
					point.setMoType(cfData.getDataName2());
				}

			}

			// 설비연결
			FE_FAC_BAS fac = facMap.get(point.getDevicePid());
			if (fac != null) {
				point.setFacNo(fac.getFacNo().intValue());
			}

			// 공장연결
			try {
				inlo = VupApi.getApi().getPlant(point.getContainerPid());
				point.setPlantInloNo(inlo.getInloNo());
			} catch (Exception e) {
			}
		}

		return true;
	}

	protected FX_CF_DATA getPointCfData(VUP_COMM_ENITT_POINT point) {

		String ss[] = point.getPointPid().split("-");

		for (FX_CF_DATA item : this.pointCfList) {
			
			if (Pattern.matches(item.getDataValue(), ss[1])) {
				
//System.out.println(item.getDataValue()  +"\t" + ss[1]);				

				if ("*".equals(item.getDataType())) {
					return item;
				}

				if (("E01".equalsIgnoreCase(item.getDataType()) && "압공(PC004)".equalsIgnoreCase(point.getPointClass()))
						|| ("E02".equalsIgnoreCase(item.getDataType())
								&& "스팀(PC003)".equalsIgnoreCase(point.getPointClass()))) {
					return item;
				}
			}
		}

		return null;
	}

	protected FX_CF_DATA getDeviceCfData(VUP_COMM_ENITT_POINT point) {

		String ss[] = point.getDevicePid().split("-");

		for (FX_CF_DATA item : this.deviceCfList) {
			if (Pattern.matches(item.getDataValue(), ss[1])) {

				if ("*".equals(item.getDataType())) {
					return item;
				}

				if (("E01".equalsIgnoreCase(item.getDataType()) && "압공(PC004)".equalsIgnoreCase(point.getPointClass()))
						|| ("E02".equalsIgnoreCase(item.getDataType())
								&& "스팀(PC003)".equalsIgnoreCase(point.getPointClass()))) {
					return item;
				}
			}
		}

		return null;
	}
}
