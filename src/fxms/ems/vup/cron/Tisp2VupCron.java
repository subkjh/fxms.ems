package fxms.ems.vup.cron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.CodeApi;
import fxms.bas.api.FxApi;
import fxms.bas.api.MappingApi;
import fxms.bas.api.MoApi;
import fxms.bas.api.PsApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.dbo.all.FX_CF_INLO;
import fxms.bas.impl.dbo.all.FX_MO;
import fxms.bas.mo.Mo;
import fxms.bas.mo.NodeMo;
import fxms.bas.vo.Code;
import fxms.bas.vo.Inlo;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.mapp.MappData;
import fxms.ems.bas.dbo.FE_ENG_BAS;
import fxms.ems.bas.dbo.FE_ENG_RT_BAS;
import fxms.ems.bas.dbo.FE_ENG_RT_PATH;
import fxms.ems.bas.dbo.FE_FAC_BAS;
import fxms.ems.bas.dbo.FE_FAC_PIPE;
import fxms.ems.bas.dbo.FE_FAC_PIPE_PATH;
import fxms.ems.bas.vo.PlantVo;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dbo.all.VUP_COMM_COMPLEX;
import fxms.ems.vup.dbo.all.VUP_COMM_DEVICE;
import fxms.ems.vup.dbo.all.VUP_COMM_FACILITY;
import fxms.ems.vup.dbo.all.VUP_COMM_PIPE;
import fxms.ems.vup.dbo.all.VUP_COMM_PLANT;
import fxms.ems.vup.dbo.all.VUP_COMM_PLC;
import fxms.ems.vup.dpo.MakeEnergyRouteDpo;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

/**
 * 수집된 에너지를 에너지소비량테이블로 이관하는 클래스
 * 
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(service = "VupService", descr = "TISP에서 처리하는 로직을 VUP공통 테이블이 기록한다.")
public class Tisp2VupCron extends Crontab {

	public static void main(String[] args) {
		MoApi.api = new MoApiDfo();
		Tisp2VupCron cron = new Tisp2VupCron();

		try {
			cron.start();
//			cron.makeRoute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "* * * * *")
	private String schedule;

	@Override
	public void start() throws Exception {

		try {
//			this.syncComplex();
//			this.syncPlant();
//			this.syncFacility();
//			this.syncModel();
//			this.syncPlc();
//			this.syncDevice();
			this.syncDevicePs();
//			this.syncPipe();

		} catch (Exception e) {
			Logger.logger.error(e);
		}
	}

	void makeRoute() throws Exception {
		MakeEnergyRouteDpo dpo = new MakeEnergyRouteDpo();
		dpo.makeEnergrRoute();
	}

	void makeRoute2() throws Exception {

		List<FE_FAC_PIPE> list;
		Map<String, FE_ENG_BAS> engMap = new HashMap<>();
		Map<Integer, FX_CF_INLO> inloMap = new HashMap<>();
		Map<String, List<FE_FAC_PIPE_PATH>> pathsMap = new HashMap<>();

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 11000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 12000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 13000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 14000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 11000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 12000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 13000));
			list = tran.selectDatas(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 14000));
//			list = tran.select(FE_FAC_PIPE.class, null);
			List<FE_FAC_PIPE_PATH> paths = tran.selectDatas(FE_FAC_PIPE_PATH.class, null);
			for (FE_FAC_PIPE_PATH path : paths) {
				List<FE_FAC_PIPE_PATH> entry = pathsMap.get(path.getPipeId());
				if (entry == null) {
					entry = new ArrayList<FE_FAC_PIPE_PATH>();
					pathsMap.put(path.getPipeId(), entry);
				}
				entry.add(path);
			}

			List<FE_ENG_BAS> list2 = tran.selectDatas(FE_ENG_BAS.class, null);
			for (FE_ENG_BAS eng : list2) {
				engMap.put(eng.getEngId(), eng);
			}
			List<FX_CF_INLO> list3 = tran.selectDatas(FX_CF_INLO.class, null);
			for (FX_CF_INLO eng : list3) {
				inloMap.put(eng.getInloNo(), eng);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

		List<FE_FAC_PIPE> srcList = new ArrayList<FE_FAC_PIPE>();
		List<FE_FAC_PIPE> sinkList = new ArrayList<FE_FAC_PIPE>();
		for (FE_FAC_PIPE pipe : list) {
			if (pipe.getPipeClCd().equals("SINK")) {
				sinkList.add(pipe);
			} else if (pipe.getPipeClCd().equals("SOURCE")) {
				srcList.add(pipe);
			} else if (pipe.getPipeClCd().equals("PUBLIC")) {
				srcList.add(pipe);
			}
		}

		for (FE_FAC_PIPE src : srcList) {
			for (FE_FAC_PIPE sink : sinkList) {

//				System.out.println(FxmsUtil.toJson(src));
//				System.out.println(FxmsUtil.toJson(sink));

				if (src.getEngId().equals(sink.getEngId())
						&& src.getMngInloNo().intValue() == sink.getMngInloNo().intValue()
						&& src.getLinkInloNo().intValue() != sink.getLinkInloNo().intValue()) {

					List<FE_FAC_PIPE_PATH> srcPaths = pathsMap.get(src.getPipeId());
					List<FE_FAC_PIPE_PATH> sinkPaths = pathsMap.get(sink.getPipeId());
					if (isLinked(srcPaths, sinkPaths)) {

						FE_ENG_BAS eng = engMap.get(src.getEngId());
						FX_CF_INLO srcInlo = inloMap.get(src.getLinkInloNo());
						FX_CF_INLO sinkInlo = inloMap.get(sink.getLinkInloNo());

						String rtId = eng.getEngTid() + "-" + srcInlo.getInloTid() + "-" + sinkInlo.getInloTid();
						FE_ENG_RT_BAS rt = new FE_ENG_RT_BAS();
						rt.setEngId(eng.getEngId());
						rt.setEngRtDescr(
								"(" + eng.getEngName() + ") " + srcInlo.getInloName() + " - " + sinkInlo.getInloName());
						rt.setEngRtId(rtId);
						rt.setFnshInloNo(sinkInlo.getInloNo());
						rt.setFnshInloName(sinkInlo.getInloName());
						rt.setStrtInloNo(srcInlo.getInloNo());
						rt.setStrtInloName(srcInlo.getInloName());

						System.out.println(FxmsUtil.toJson(rt));
						System.out.println(FxmsUtil.toJson(getPath(rtId, srcPaths, sinkPaths)));
					}
				}
			}
		}
	}

	/**
	 * 산단 동기화
	 * 
	 * @throws Exception
	 */
	void syncComplex() throws Exception {

		List<VUP_COMM_COMPLEX> complexList = select(VUP_COMM_COMPLEX.class);
		VupApi.getApi().updateComplexs(complexList);
	}

	/**
	 * 디바이스 동기화
	 * 
	 * @throws Exception
	 */
	void syncDevice() throws Exception {

		List<VUP_COMM_DEVICE> deviceList = select(VUP_COMM_DEVICE.class);

		VupApi.getApi().updateDevices(deviceList);

	}

	/**
	 * 관제점 동기화
	 * 
	 * @throws Exception
	 */
	void syncDevicePs() throws Exception {

		List<VUP_COMM_DEVICE> deviceList = select(VUP_COMM_DEVICE.class);

		Map<String, PsItem> psMap = PsApi.toIdMap(PsApi.getApi().getPsItemsIncludeNotUse());
		Map<String, Mo> map = VupApi.getApi().getMoTidMap();
		String tid;
		Mo mo;
		for (VUP_COMM_DEVICE c : deviceList) {
			try {

				tid = VupApi.getApi().getTid(c);
				mo = map.get(tid);

				if (mo != null) {
					String ss[] = c.getPsIds().split(",");
					for (String s : ss) {
						String ss2[] = s.split(":");
						if (ss2.length < 2)
							continue;
						String tag = c.getPlantPid() + "-" + ss2[0].replaceAll("-", "");
						String psId = ss2[1];

						MappData mappData = VupApi.getApi().makeMappPs(tag, tag);
						PsItem psItem = psMap.get(psId);
						if (psItem != null) {

							MappingApi.getApi().setMappPs(0, mappData, mo.getMoNo(), mo.getMoName(), psItem.getPsId(),
									psItem.getPsName());

						} else {
							Logger.logger.fail("not found psItem '{}'", psId);
						}

					}
				} else {
					Logger.logger.fail("not found mo '{}'", c.getDevicePid());
				}
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}
	}

	/**
	 * 설비 동기화
	 * 
	 * @throws Exception
	 */
	void syncFacility() throws Exception {

		VupApi api = VupApi.getApi();

		List<VUP_COMM_FACILITY> list = select(VUP_COMM_FACILITY.class);
		Map<String, Code> cdMap = CodeApi.getApi().getCodeNameMap("FAC_CL_CD");

		Code code;
		Inlo inlo;
		String facTid;
		Map<String, Object> para = new HashMap<String, Object>();

		for (VUP_COMM_FACILITY c : list) {

			facTid = VupApi.getTid(c.getPlantName(), c.getFacPid());

			para.clear();
			para.put("facName", c.getFacName());
			para.put("facTid", facTid);
			para.put("facType", c.getFacType());
			para.put("facDescr", "");
			para.put("engId", api.getEngId(c.getEnergyId()));
			para.put("instYmd", "20230301");
			para.put("procTypeCd", "ENERGY");

			code = cdMap.get(c.getFacType());
			if (code != null)
				para.put("facClCd", code.getCdCode());

			try {
				inlo = VupApi.getApi().getPlant(c.getPlantName());
				para.put("inloNo", inlo.getInloNo());
			} catch (Exception e) {
			}

			para.put("desgnCapa", makeCapa(c.getDesgPress(), c.getDesgTemp()));
			para.put("operCapa", makeCapa(c.getOperPress(), c.getOperTemp()));
			para.put("facFmt", "");
			para.put("facPower", "");

			if (c.getFacSizeCapacity() != null) {
				String ss[] = c.getFacSizeCapacity().split(",");
				for (String s : ss) {
					String ss1[] = s.split(":");
					if (ss1.length == 2) {
						String name = ss1[0].trim();
						String value = ss1[1].trim();
						if (name.equalsIgnoreCase("SIZE")) {
							para.put("facSize", value);
						} else if (name.equalsIgnoreCase("POWER")) {
							para.put("facPower", value);
						} else if (name.equalsIgnoreCase("CAPACITY")) {
							para.put("desgnCapa", value + ", " + makeCapa(c.getDesgPress(), c.getDesgTemp()));
						}
					}
				}
			}

			try {
				VupApi.getApi().setFac(facTid, para);
				if (FxApi.isNotEmpty(c.getPointIds())) {
					VupApi.getApi().updateDeviceCompressorAndDryer(c);
				}
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}
	}

	/**
	 * 모델 동기화
	 * 
	 * @throws Exception
	 */
	void syncModel() throws Exception {

		List<VUP_COMM_DEVICE> deviceList = select(VUP_COMM_DEVICE.class);

		VupApi.getApi().updateModels(deviceList);

	}

	/**
	 * 배관 동기화
	 * 
	 * @throws Exception
	 */
	void syncPipe() throws Exception {

		List<VUP_COMM_PIPE> pipeList = select(VUP_COMM_PIPE.class);

		VupApi api = VupApi.getApi();
		Map<String, PlantVo> inloMap = VupApi.getApi().getInlos(null);
		Map<String, Object> para = new HashMap<String, Object>();
		String pipeId, pipeClCd;
		Inlo complex, plant;

		for (VUP_COMM_PIPE c : pipeList) {

			complex = inloMap.get(c.getComplexPid());
			plant = inloMap.get(c.getPlantPid());

			pipeId = c.getPipeId();
			para.put("pipeId", pipeId);
			para.put("pipeName", c.getPipeName());
			para.put("pipeDescr", c.getPipeDescr());
			para.put("engId", api.getEngId(c.getEnergyId()));
			para.put("instDate", "20230101");
			if (complex != null)
				para.put("mngInloNo", complex.getInloNo());
			if (plant != null)
				para.put("linkInloNo", plant.getInloNo());

			String ss[] = pipeId.split("-");
			if (ss[0].contains("A") || ss[0].contains("A")) {
				para.put("pipeCapa", ss[0]);
			} else {
				para.put("pipeCapa", "");
			}

			pipeClCd = "PUBLIC";
			if ("Y".equalsIgnoreCase(c.isPublicYn())) {
				if (c.getPipeName().contains("연결") || c.getPipeDescr().contains("POINT")) {
					pipeClCd = "POINT";
				} else {
					pipeClCd = "PUBLIC";
				}
			} else if ("t02".equalsIgnoreCase(c.getTransId())) {
				pipeClCd = "SINK";

			} else if ("t01".equalsIgnoreCase(c.getTransId())) {
				pipeClCd = "SOURCE";
			}

			para.put("pipeClCd", pipeClCd);

			try {
				api.setPipe(pipeId, para);
			} catch (Exception e) {
				e.printStackTrace();
				Logger.logger.error(e);
			}

		}

		Map<String, Object> map = loadDataForPipe();
		List<FE_FAC_PIPE_PATH> paths;
		for (VUP_COMM_PIPE c : pipeList) {
			paths = getPaths(map, c);
			if (paths != null) {
				api.setPipePath(c.getPipeId(), paths);
			}
		}

	}

	/**
	 * 공장 동기화
	 * 
	 * @throws Exception
	 */
	void syncPlant() throws Exception {

		List<VUP_COMM_PLANT> plantList = select(VUP_COMM_PLANT.class);

		VupApi.getApi().updatePlants(plantList);

	}

	/**
	 * PLC 동기화
	 * 
	 * @throws Exception
	 */
	void syncPlc() throws Exception {

		List<VUP_COMM_PLC> deviceList = select(VUP_COMM_PLC.class);

		Map<String, PlantVo> inloMap = VupApi.getApi().getInlos(null);

		// P
		List<NodeMo> moList = MoApi.getApi().getMoList(null, NodeMo.class);
		Map<String, NodeMo> nameMap = new HashMap<String, NodeMo>();
		for (NodeMo mo : moList) {
			if (mo.getMoName() != null) {
				nameMap.put(mo.getMoName(), mo);
			}
		}

		Inlo inlo;
		NodeMo node;
		MoApi moApi = MoApi.getApi();
		Map<String, Object> data;

		for (VUP_COMM_PLC c : deviceList) {
			try {
				node = nameMap.get(c.getPlcName());
				data = VupApi.getApi().makeNode(c.getIpAddr(), null, null, c.getPlcName());
				inlo = inloMap.get(c.getPlantName());

				data.put("moType", getPlcMoType(c));
				data.put("inloNo", inlo == null ? 0 : inlo.getInloNo());
				data.put("instYmd", 20230301);
				data.put("pollCycle", 60); // 1분 단위에 LOC에서 데이터가 오기 때문에 지정해 놓아야 데이터가 들어오지 않으면 알람을 발생한다.
				data.put("alarmCfgNo", VupApi.getApi().getAlarmCfgNo(VupApi.PLC_MO_CLASS, VupApi.PLC_MO_TYPE));

				if (node == null) {
					moApi.addMo(1, NodeMo.MO_CLASS, data, "vup2tisp", false);
				} else {
					moApi.updateMo(1, node.getMoNo(), data, false);
				}
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}
	}

	@Override
	protected String getSchedule() {
		return schedule;
	}

	private List<FE_ENG_RT_PATH> getPath(String engRtId, List<FE_FAC_PIPE_PATH> srcPaths,
			List<FE_FAC_PIPE_PATH> sinkPaths) {
		List<FE_ENG_RT_PATH> ret = new ArrayList<FE_ENG_RT_PATH>();
		FE_FAC_PIPE_PATH sink;

		for (FE_FAC_PIPE_PATH src : srcPaths) {

			// SOURCE에서 SINK까지 가는 배관을 추가한다.
			if ("PIPE".equals(src.getLinkObjClCd())) {
				ret.add(makeRtPath(engRtId, src.getLinkObjId(), src.getLinkObjName()));

				// SINK에 연결고리를 찾으면 SINK에서 배관만 추출하여 추가한다.
				for (int i = 0; i < sinkPaths.size(); i++) {
					sink = sinkPaths.get(i);
					if (FxApi.isSame(src.getLinkObjId(), sink.getLinkObjId())) {
						for (int n = i + 1; n < sinkPaths.size(); n++) {
							sink = sinkPaths.get(n);
							if ("PIPE".equals(sink.getLinkObjClCd())) {
								// ret.add(makeRtPath(engRtId, src));
							}
						}
						return ret;
					}
				}
			}
		}
		return ret;
	}

	private List<FE_FAC_PIPE_PATH> getPaths(Map<String, Object> map, VUP_COMM_PIPE pipe) {

		if (pipe.getPipePaths() == null || pipe.getPipePaths().trim().length() == 0) {
			return null;
		}

		FE_FAC_PIPE_PATH c;
		List<FE_FAC_PIPE_PATH> list = new ArrayList<FE_FAC_PIPE_PATH>();
		String ss[] = pipe.getPipePaths().split(",");
		Object obj;

		for (String s : ss) {
			String name = s.split(":")[0];

			if ("PVALVE".equalsIgnoreCase(name)) {
				c = new FE_FAC_PIPE_PATH();
				c.setPipeId(pipe.getPipeId());
				c.setLinkSeq(list.size() + 1);
				c.setLinkDescr("");
				c.setLinkObjClCd("PVALUE");
				c.setLinkObjId("");
				c.setLinkObjName("");
				list.add(c);
				continue;
			}
			obj = map.get(name);
			if (obj != null) {
				c = new FE_FAC_PIPE_PATH();
				c.setPipeId(pipe.getPipeId());

				c.setLinkSeq(list.size() + 1);
				if (obj instanceof FE_FAC_PIPE) {
					FE_FAC_PIPE o = (FE_FAC_PIPE) obj;
					c.setLinkDescr("");
					c.setLinkObjClCd("PIPE");
					c.setLinkObjId(o.getPipeId());
					c.setLinkObjName(o.getPipeName());
				} else if (obj instanceof FE_FAC_BAS) {
					FE_FAC_BAS o = (FE_FAC_BAS) obj;
					c.setLinkDescr("");
					c.setLinkObjClCd("FAC");
					c.setLinkObjId(o.getFacTid());
					c.setLinkObjName(o.getFacName());
				} else if (obj instanceof FX_MO) {
					FX_MO o = (FX_MO) obj;
					c.setLinkDescr("");
					c.setLinkObjClCd("MO");
					c.setLinkObjId(o.getMoTid());
					c.setLinkObjName(o.getMoName());
				}

				list.add(c);
			} else {
				System.out.println(name);
			}
		}

		return list;
	}

	private String getPlcMoType(VUP_COMM_PLC c) {
		if (c.getPlcName().contains("전용망")) {
			return "전용망";
		} else if (c.getPlcName().contains("공유기")) {
			return "공유기";
		} else if (c.getPlcName().contains("LOC")) {
			return "LOC Server";
		} else if (c.getPlcName().contains("DB")) {
			return "PLC DB Server";
		}

		return VupApi.PLC_MO_TYPE;
	}

	/**
	 * 동일 파이프가 존재하면 연결 가능하다.
	 * 
	 * @param srcPaths
	 * @param sinkPaths
	 * @return
	 */
	private boolean isLinked(List<FE_FAC_PIPE_PATH> srcPaths, List<FE_FAC_PIPE_PATH> sinkPaths) {
		for (FE_FAC_PIPE_PATH src : srcPaths) {
			if ("PIPE".equals(src.getLinkObjClCd())) {
				for (FE_FAC_PIPE_PATH sink : sinkPaths) {
					if (FxApi.isSame(src.getLinkObjId(), sink.getLinkObjId())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private Map<String, Object> loadDataForPipe() throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();
			List<FE_FAC_PIPE> pipeList = tran.selectDatas(FE_FAC_PIPE.class, null);
			List<FX_MO> sensorList = tran.selectDatas(FX_MO.class,
					FxApi.makePara("delYn", "N", "moClass", VupApi.SENSOR_MO_CLASS));
			List<FE_FAC_BAS> facList = tran.selectDatas(FE_FAC_BAS.class, null);

			for (FE_FAC_PIPE obj : pipeList) {
				retMap.put(obj.getPipeId(), obj);
			}
			for (FX_MO obj : sensorList) {
				if (obj.getMoDispName() != null) {
					retMap.put(obj.getMoDispName().trim(), obj);
				}
			}
			for (FE_FAC_BAS obj : facList) {
				retMap.put(obj.getFacTid(), obj);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

		return retMap;
	}

	private String makeCapa(String press, String temp) {
		StringBuffer sb = new StringBuffer();
		if (press != null && press.trim().length() > 0) {
			sb.append("PRESS:").append(press.trim());
		}
		if (temp != null && temp.trim().length() > 0) {
			if (sb.length() > 0)
				sb.append(",");
			sb.append(" TEMP:").append(temp.trim());
		}
		return sb.toString().trim();
	}

	private FE_ENG_RT_PATH makeRtPath(String engRtId, String pipeId, String pipeDescr) {
		FE_ENG_RT_PATH ret = new FE_ENG_RT_PATH();
		ret.setEngRtId(engRtId);
		ret.setPipeDescr(pipeDescr);
		ret.setPipeId(pipeId);
		return ret;
	}

	private <T> List<T> select(Class<T> classOfT) throws Exception {
		ClassDao tran = DBManager.getMgr().getDataBase("VUPCOMMDB").createClassDao();
		try {
			tran.start();
			return tran.selectDatas(classOfT, null);
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

}
