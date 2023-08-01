package fxms.ems.vup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import fxms.bas.api.MoApi;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.dbo.all.FX_CF_DATA;
import fxms.bas.mo.Mo;
import fxms.bas.vo.mapp.MappMoPs;
import fxms.ems.bas.dbo.FE_FAC_BAS;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;
import fxms.ems.vup.dpo.EnittSyncPointDpo;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.database.DBManager;

/**
 * C:\Thingspire\02. 사내프로젝트\2201. VUP(과제)\1. 제공자료\애니트 폴더에서<br>
 * 20230411_에니트_시화도금단지_정리본(전체).xlsx 파일의 데이터를 <br>
 * VUP_COMM_ENITT_POINT 테이블에 기록한다.<br>
 * 
 * @author subkjh
 *
 */
public class 애니트관제점확인 {

	public static void main(String[] args) {

		MoApi.api = new MoApiDfo();

		try {
			애니트관제점확인 test = new 애니트관제점확인();
			test.updatePoint2MoPs2();
//			test.updatePoint2MoPs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public 애니트관제점확인() throws Exception {
		this.dataList = VupApi.getApi().selectDatas("vup.point.tag");
		this.typeList = VupApi.getApi().selectDatas("vup.device.tag");
	}

	private final List<FX_CF_DATA> dataList;
	private final List<FX_CF_DATA> typeList;

	/**
	 * 관제점을 이용하여 관리대상, 성능 등록한다.
	 * 
	 * @throws Exception
	 */
	void updatePoint2MoPs() throws Exception {
		List<VUP_COMM_ENITT_POINT> list = getDatas();
		List<VUP_COMM_ENITT_POINT> updateList = new ArrayList<>();

		Map<String, Mo> moTidMap = VupApi.getApi().getMoTidMap();
		Map<String, MappMoPs> map = VupApi.getApi().getMappPsAll();
		Map<String, FE_FAC_BAS> facMap = VupApi.getApi().getFacTidMap();
		MappMoPs mops;
		Mo mo;

		for (VUP_COMM_ENITT_POINT point : list) {
			mo = null;
			mops = map.get(point.getPointPid());
			if (mops != null) {
				point.setMoNo(mops.getMoNo());
				point.setPsId(mops.getPsId());
			}

			if (mo == null)
				mo = moTidMap.get(point.getDevicePid());

			if (mo != null) {
				point.setMoNo(mo.getMoNo());
				point.setMoName(mo.getMoName());
				point.setMoType(mo.getMoType());
				updateList.add(point);
			}

			FE_FAC_BAS fac = facMap.get(point.getDevicePid());
			if (fac != null) {
				point.setFacNo(fac.getFacNo().intValue());
			}

			System.out.println(FxmsUtil.toJson(point));
		}

		if (updateList.size() > 0) {
			updateMoPs(updateList);
		}
	}

	void checkMoPs() throws Exception {

		VupApi api = VupApi.getApi();

		List<VUP_COMM_ENITT_POINT> pointList = getDatas();
		Map<String, Mo> moMap = api.getMoTidMap();
		List<String> okStrs = new ArrayList<>();
		List<String> erStrs = new ArrayList<>();

		FX_CF_DATA cfPoint, cfDevice;
		String ss[];
		for (VUP_COMM_ENITT_POINT point : pointList) {

			ss = point.getPointPid().split("-");

			cfPoint = null;
			for (FX_CF_DATA item : dataList) {
				if (Pattern.matches(item.getDataValue(), ss[1])) {
					if (("E01".equalsIgnoreCase(item.getDataType())
							&& "압공(PC004)".equalsIgnoreCase(point.getPointClass()))
							|| ("E02".equalsIgnoreCase(item.getDataType())
									&& "스팀(PC003)".equalsIgnoreCase(point.getPointClass()))) {
						cfPoint = item;
						break;
					}
				}
			}

			ss = point.getDevicePid().split("-");
			cfDevice = null;
			for (FX_CF_DATA item : typeList) {
				if (Pattern.matches(item.getDataValue(), ss[1])) {
					if (("E01".equalsIgnoreCase(item.getDataType())
							&& "압공(PC004)".equalsIgnoreCase(point.getPointClass()))
							|| ("E02".equalsIgnoreCase(item.getDataType())
									&& "스팀(PC003)".equalsIgnoreCase(point.getPointClass()))) {
						cfDevice = item;
						break;
					}
				}
			}

			Mo mo = moMap.get(point.getDevicePid());

			StringBuffer sb = new StringBuffer();
			sb.append(point.getDevicePid()).append(",").append(point.getPointPid()).append(" :: ");

			if (mo != null) {
				sb.append(" mo = ['").append(mo.getMoNo()).append("' '").append(mo.getMoName()).append("']");
			} else {
				sb.append(" mo = 등록안됨");
			}

			if (cfDevice != null) {
				sb.append(" dev = ['").append(cfDevice.getDataName1()).append("' '").append(cfDevice.getDataName2())
						.append("']");
			} else {
				sb.append(" dev = 패턴없음 ");
			}

			if (cfPoint != null) {
				sb.append(" point = ['").append(cfPoint.getDataName1()).append("' '").append(cfPoint.getDataName2())
						.append("']");
			} else {
				sb.append(" point = 패턴없음 ");
			}

			if (mo != null && cfPoint != null) {
//				MappData mappData = VupApi.getApi().makeMappPs(point.getPointPid(), point.getPointDesc());
//				DATA_STATUS status = MappingApi.getApi().setMappPs(0, mappData, mo.getMoNo(), mo.getMoName(),
//						cfPoint.getDataName1(), cfPoint.getDataName2());
//				sb.append(" mappId = ").append(mappData.getMappId()).append(",").append(status);
				okStrs.add(sb.toString());
			} else {
				erStrs.add(sb.toString());
			}

		}

		System.out.println("\n\n");
		System.out.println("--------------------------------------------------------------------------");

		for (String s : okStrs) {
			System.out.println(" ok " + s);
		}
		System.out.println("--------------------------------------------------------------------------");
		for (String s : erStrs) {
			System.out.println(" er " + s);
		}

	}

	List<VUP_COMM_ENITT_POINT> getDatas() throws Exception {
		return ClassDaoEx.SelectDatas(VUP_COMM_ENITT_POINT.class, null);
	}

	void updateMoPs(List<VUP_COMM_ENITT_POINT> list) throws Exception {
		ClassDao tran = DBManager.getMgr().getDataBase("VUPCOMMDB").createClassDao();

		try {
			tran.start();
			for (VUP_COMM_ENITT_POINT o : list)
				tran.updateOfClass(VUP_COMM_ENITT_POINT.class, o);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

	/**
	 * FX_CF_DATA 테이블 내용을 이용하여 VUP_COMM_ENITT_POINT 관제점을 수정한다.
	 * 
	 * @throws Exception
	 */
	void updatePoint2MoPs2() throws Exception {

		EnittSyncPointDpo dpo = new EnittSyncPointDpo();
		dpo.sync();

	}

	FX_CF_DATA getPointCfData(VUP_COMM_ENITT_POINT point) {

		String ss[] = point.getPointPid().split("-");

		for (FX_CF_DATA item : dataList) {
			if (Pattern.matches(item.getDataValue(), ss[1])) {
				if (("E01".equalsIgnoreCase(item.getDataType()) && "압공(PC004)".equalsIgnoreCase(point.getPointClass()))
						|| ("E02".equalsIgnoreCase(item.getDataType())
								&& "스팀(PC003)".equalsIgnoreCase(point.getPointClass()))) {
					return item;
				}
			}
		}

		return null;
	}

	FX_CF_DATA getDeviceCfData(VUP_COMM_ENITT_POINT point) {

		String ss[] = point.getDevicePid().split("-");

		for (FX_CF_DATA item : typeList) {
			if (Pattern.matches(item.getDataValue(), ss[1])) {
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
