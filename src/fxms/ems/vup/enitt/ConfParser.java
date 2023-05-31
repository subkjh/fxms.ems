package fxms.ems.vup.enitt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.InloApi;
import fxms.bas.api.MappingApi;
import fxms.bas.api.MoApi;
import fxms.bas.api.ModelApi;
import fxms.bas.exp.NotFoundException;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_MAPP_PS;
import fxms.bas.mo.Mo;
import fxms.bas.mo.NodeMo;
import fxms.bas.vo.Inlo;
import fxms.bas.vo.MoModel;
import fxms.bas.vo.mapp.MappData;
import fxms.ems.bas.vo.PlantVo;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dto.Conf01Dto;
import fxms.ems.vup.dto.Conf01Sub1Dto;
import fxms.ems.vup.dto.Conf01Sub2Dto;
import fxms.ems.vup.dto.Conf01Sub3Dto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.bas.co.utils.StringUtil;
import subkjh.dao.util.FxTableMaker;

/**
 * 애니트에서 보내는 구성을 기록한다.
 * 
 * @author subkjh
 *
 */
public class ConfParser {

	class FACTORY {
		String factory_pid;
		String factory_nm;
		String factory_desc;
		Integer use_yn;
		int inloNo;
	}

	class DEVICE {
		String factory_pid;
		String device_pid;
		String device_nm;
		// String device_tag;
		String device_desc;
		String device_type;
		String device_loc;
		String model;
		String model_no;
		String manufacturer;
		String specification;
		String conn_adr;

		long moNo;
		String moName;
		boolean facility;
		String facNo;
	}

	class FACILITY {
		String factory_pid;
		String device_pid;
		String device_nm;
		// String device_tag;
		String device_desc;
		String device_group;
		String device_type;
		String device_loc;
		String specification;
		String model;
		String manufacturer;
		long facNo;
	}

	class MODEL {
		String model;
		String model_no;
		String manufacturer;
		String specification;
		int modelNo;
	}

	class PLC {
		String conn_protocol;
		String conn_adr;
		String conn_port;
		long moNo;
	}

	class POINT {
		String device_pid;
		String point_pid;
		String point_nm;
		String psId;
	}

	class PS {
		String point_nm;
		String unit;
		String psId;
	}

	private final Map<String, PlantVo> inloMap;
	private final Map<String, NodeMo> plcMap;
	private final Map<String, Mo> sensorMap;

	public ConfParser() throws Exception {
		this.inloMap = VupApi.getApi().getInlos(null);

		plcMap = new HashMap<String, NodeMo>();
		List<Mo> molist = MoApi.getApi()
				.getMoList(FxApi.makePara("moClass", VupApi.PLC_MO_CLASS, "moType", VupApi.PLC_MO_TYPE));
		for (Mo mo : molist) {
			NodeMo node = (NodeMo) mo;
			if (node.getNodeIpAddr() != null) {
				plcMap.put(node.getNodeIpAddr(), node);
			}
		}

		sensorMap = new HashMap<String, Mo>();
		molist = MoApi.getApi().getMoList(FxApi.makePara("moClass", VupApi.SENSOR_MO_CLASS));
		for (Mo mo : molist) {
			if (mo.getMoTid() != null) {
				sensorMap.put(mo.getMoTid(), mo);
			}
		}

	}

	private String getFacClCd(String s) {
		if (s.contains("압공기")) {
			return "압공기";
		} else if (s.contains("드라이어")) {
			return "드라이어";
		} else if (s.contains("보일러")) {
			return "보일러";
		} else {
			return s;
		}
	}

	private Map<String, PLC> init_conn_adr(Conf01Dto dto) {
		MoApi api = MoApi.getApi();
		Map<String, PLC> map = new HashMap<String, PLC>();
		PLC c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new PLC();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);
				map.put(c.conn_adr, c);
			}
		}

		Map<String, Object> data;
		NodeMo node;
		for (PLC o : map.values()) {
			data = VupApi.getApi().makeNode(o.conn_adr, o.conn_protocol, o.conn_port, o.conn_adr);

			node = plcMap.get(o.conn_adr);
			if (node != null) {
				o.moNo = node.getMoNo();
				try {
					api.updateMo(0, o.moNo, data, false);
					Logger.logger.info("{}", Logger.makeString("plc.update", o.conn_adr));
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			} else {
				try {
					Mo mo = api.addMo(0, NodeMo.MO_CLASS, data, "LOC연동", false);
					o.moNo = mo.getMoNo();
					Logger.logger.info("{}", Logger.makeString("plc.add", o.conn_adr));
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			}
		}

		return map;
	}

	private Map<String, PLC> getPlc(Conf01Dto dto) {
		Map<String, PLC> map = new HashMap<String, PLC>();
		PLC c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new PLC();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);
				map.put(c.conn_adr, c);
			}
		}
		return map;
	}

	/**
	 * 설치위치 정보 조회
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private Map<String, FACTORY> init_factory_pid(Conf01Dto data) throws Exception {

		InloApi api = InloApi.getApi();
		FACTORY o;
		Map<String, FACTORY> map = new HashMap<String, FACTORY>();

		for (Conf01Sub1Dto fac : data.getFacilities()) {
			o = new FACTORY();
			ObjectUtil.toObject(ObjectUtil.toMap(fac), o);
			map.put(o.factory_pid, o);

			Inlo inlo = inloMap.get(o.factory_pid);

			if (inlo != null) {
				o.inloNo = inlo.getInloNo();
				try {
					api.updateInlo(0, o.inloNo, FxApi.makePara("inloTid", o.factory_pid));
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			} else {

				try {
					Map<String, Object> etc = new HashMap<String, Object>();
					etc.put("locTid", o.factory_pid);
					etc.put("inloName", o.factory_nm);
					etc.put("inloDescr", "LOC 존재로 자동등록");
					etc.put("inloClCd", "PLANT");
					etc.put("upperInloNo", 0);
					int inloNo = api.addInlo(0, etc);
					Logger.logger.info("{}", Logger.makeString("inlo.add " + inloNo, o.factory_pid));
				} catch (Exception e) {
					Logger.logger.error(e);
				}

			}
		}

		Logger.logger.info("\n{}", FxmsUtil.toJson(map));
		return map;
	}

	private Map<String, FACTORY> getFactory(Conf01Dto data) throws Exception {

		FACTORY o;
		Map<String, FACTORY> map = new HashMap<String, FACTORY>();

		for (Conf01Sub1Dto fac : data.getFacilities()) {
			o = new FACTORY();
			ObjectUtil.toObject(ObjectUtil.toMap(fac), o);
			map.put(o.factory_pid, o);
		}

		return map;
	}

	/**
	 * 센서 목록를 가져온다.
	 * 
	 * @param list
	 * @param conMap
	 * @param modelMap
	 * @return
	 */
	private Map<String, DEVICE> init_device_id(Conf01Dto dto, Map<String, FACTORY> conMap, Map<String, MODEL> modelMap,
			Map<String, PLC> plcMap, Map<String, FACILITY> facilityMap) {

		MoApi api = MoApi.getApi();
		Map<String, DEVICE> map = new HashMap<String, DEVICE>();
		DEVICE c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new DEVICE();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);
				c.factory_pid = fac.factory_pid;

				// 배관라인에 있는건 센서 그외는 설비로 등록한다.
				if ("배관 라인".equals(c.device_loc)) {
					c.facility = false;
					map.put(c.device_pid, c);
				} else {
					c.facility = true;
					map.put(c.device_pid, c);
				}
			}
		}

		PLC plc;
		MODEL model;
		FACTORY container;
		Map<String, Object> data;

		for (DEVICE o : map.values()) {

			data = new HashMap<String, Object>();
			data.put("moName", o.device_desc);
			data.put("moDispName", o.device_nm);
			data.put("moMemo", o.device_desc);
			data.put("moType", StringUtil.isNotEmpty(o.device_type) ? o.device_type : o.device_nm);
			data.put("moTid", o.device_pid);

			if (o.facility) {
				data.put("moClass", VupApi.VIRTUAL_MO_CLASS);
				data.put("moType", VupApi.VIRTUAL_MO_TYPE);
				data.put("moMemo", "설비관제을 위한 가상 MO");
				FACILITY fac = facilityMap.get(o.device_pid);
				if (fac != null)
					o.facNo = fac.facNo + "";

			} else {
				data.put("moClass", VupApi.SENSOR_MO_CLASS);
				data.put("sensrName", o.device_nm);
				data.put("sensrDesc", o.device_desc);
				data.put("manftSn", o.model_no);
			}

			container = conMap.get(o.factory_pid);
			if (container != null) {
				data.put("inloNo", container.inloNo);
			}

			model = modelMap.get(o.model);
			if (model != null) {
				data.put("modelNo", model.modelNo);
			}

			plc = plcMap.get(o.conn_adr);
			if (plc != null) {
				data.put("plcMoNo", plc.moNo);
			}

			data.put("moAddJson", FxmsUtil.toJson(o));

			Mo mo = sensorMap.get(o.device_pid);
			if (mo != null) {
				try {
					o.moNo = mo.getMoNo();
					o.moName = mo.getMoName();
					api.updateMo(0, o.moNo, data, false);
					Logger.logger.info("{}", Logger.makeString("sensor.update", o.device_pid));
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			} else {
				try {
					mo = api.addMo(0, data.get("moClass").toString(), data, "LOC연동", false);
					o.moNo = mo.getMoNo();
					o.moName = mo.getMoName();
					Logger.logger.info("{}", Logger.makeString("sensor.add", o.device_pid));
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			}
		}

		return map;
	}

	private Map<String, DEVICE> getDevices(Conf01Dto dto) {

		Map<String, DEVICE> map = new HashMap<String, DEVICE>();
		DEVICE c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new DEVICE();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);
				c.factory_pid = fac.factory_pid;
			}
		}

		return map;
	}

	/**
	 * 설치 장비가 배관 라인이 아닌 데이터를 설비로 판단하여 가져온다.
	 * 
	 * @param list
	 * @param conMap
	 * @return
	 */
	private Map<String, FACILITY> init_device_id_facility(Conf01Dto dto, Map<String, FACTORY> conMap) {

		Map<String, FACILITY> map = new HashMap<String, FACILITY>();
		FACILITY c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new FACILITY();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);

				// 배관라인에 있는건 센서 그외는 설비로 등록한다.
				if ("배관 라인".equals(c.device_loc) == false) {
					map.put(c.device_pid, c);
				}
			}
		}

		FACTORY container;
		Map<String, Object> data;

		for (FACILITY o : map.values()) {

			data = new HashMap<String, Object>();
			data.put("facName", o.device_nm);
			data.put("facClCd", getFacClCd(o.device_nm));
			data.put("facDescr", o.device_desc);
			data.put("facType", o.device_desc);
			data.put("desgnCapa", o.specification);
			data.put("facTid", o.device_pid);
			data.put("modelName", o.model);
			data.put("vendrName", o.manufacturer);
			data.put("engId", getEngId(o.device_group));

			container = conMap.get(o.factory_pid);
			if (container != null) {
				data.put("inloNo", container.inloNo);
			}

			try {
				VupApi.getApi().setFac(o.device_pid, data);
				Logger.logger.info("{}", Logger.makeString("facility.update", o.device_pid));
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}

		return map;
	}

	private Map<String, FACILITY> getFacilities(Conf01Dto dto) {

		Map<String, FACILITY> map = new HashMap<String, FACILITY>();
		FACILITY c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new FACILITY();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);
				map.put(c.device_pid, c);
			}
		}

		return map;
	}

	/**
	 * 센서의 모델 정보를 가져온다.
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private Map<String, MODEL> init_model(Conf01Dto data) throws Exception {
		ModelApi api = ModelApi.getApi();

		Map<String, MODEL> map = new HashMap<String, MODEL>();
		MODEL c;
		for (Conf01Sub1Dto fac : data.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new MODEL();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);
				map.put(c.model, c);
			}
		}

		MappData mappData;
		Map<String, Integer> modelMap = api.getMappInloAll("ENITT:MODEL");

		for (MODEL o : map.values()) {

			mappData = new MappData("ENITT:MODEL", "MODEL:" + o.model + ":" + o.manufacturer,
					o.model + ":" + o.manufacturer, o.model + ":" + o.manufacturer);

			Integer modelNo = modelMap.get(mappData.getMappId());
			MoModel model = null;
			try {
				model = api.getModel(modelNo);				
			} catch (NotFoundException e) {
			}

//			여기 작업해야 합니다.

			if (model != null) {
				o.modelNo = model.getModelNo();
				api.updateModel(0, model.getModelNo(),
						FxApi.makePara("modelDescr", o.specification, "vendrName", o.manufacturer));
			} else {
				model = api.getModel(o.model, o.manufacturer);
				try {
					if (model != null) {
						o.modelNo = model.getModelNo();
						api.updateModel(0, model.getModelNo(),
								FxApi.makePara("modelDescr", o.specification, "vendrName", o.manufacturer));
					} else {
						MoModel newModel = api.addModel(0, FxApi.makePara("modelName", o.model, "modelDescr",
								o.specification, "vendrName", o.manufacturer));
						o.modelNo = newModel.getModelNo();
					}
					Logger.logger.info("{}", Logger.makeString("model.mapping", mappData.getMappData()));
				} catch (Exception e) {
					Logger.logger.error(e);
				}
			}
		}

		Logger.logger.info("\n{}", FxmsUtil.toJson(map));
		return map;
	}

	private Map<String, MODEL> getModel(Conf01Dto data) throws Exception {

		Map<String, MODEL> map = new HashMap<String, MODEL>();
		MODEL c;
		for (Conf01Sub1Dto fac : data.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				c = new MODEL();
				ObjectUtil.toObject(ObjectUtil.toMap(dev), c);
				map.put(c.model, c);
			}
		}
		return map;
	}

	private int init_point_id(Conf01Dto dto, Map<String, PS> psMap, Map<String, DEVICE> deviceMap) throws Exception {

		Map<String, POINT> map = new HashMap<String, POINT>();
		POINT c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				for (Conf01Sub3Dto point : dev.getPoints()) {
					c = new POINT();
					ObjectUtil.toObject(ObjectUtil.toMap(point), c);
					c.device_pid = dev.device_pid;
					map.put(c.point_pid, c);
				}
			}
		}

		long moNo;
		String moName;
		String psId;
		String psName;

		for (POINT point : map.values()) {

			FX_MAPP_PS mapp = new FX_MAPP_PS();
			MappData data = VupApi.getApi().makeMappPs(point.point_pid, point.point_pid);
			mapp.setMngDiv(data.getMngDiv());
			mapp.setMappData(data.getMappData());
			mapp.setMappDescr(data.getMappDescr());
			mapp.setMappId(String.valueOf(data.getMappId()));
			FxTableMaker.initRegChg(0, mapp);

			PS ps = psMap.get(point.point_nm);
			psId = ps.psId == null ? "-" : ps.psId;
			psName = "";

			DEVICE device = deviceMap.get(point.device_pid);
			if (device != null) {
				moNo = device.moNo;
				moName = device.moName;
			} else {
				moNo = -1;
				moName = "";
			}

			MappingApi.getApi().setMappPs(0, data, moNo, moName, psId, psName);

		}

		return map.size();

	}

	private Map<String, POINT> getPoints(Conf01Dto dto) throws Exception {

		Map<String, POINT> map = new HashMap<String, POINT>();
		POINT c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				for (Conf01Sub3Dto point : dev.getPoints()) {
					c = new POINT();
					ObjectUtil.toObject(ObjectUtil.toMap(point), c);
					c.device_pid = dev.device_pid;
					map.put(c.point_pid, c);
				}
			}
		}

		return map;
	}

	/**
	 * point_nm에 해당되는 성능ID를 매핑한다.
	 * 
	 * @param psMap
	 * @throws Exception
	 */
	private Map<String, PS> init_point_nm(Conf01Dto dto) throws Exception {

		Map<String, PS> psMap = new HashMap<String, PS>();
		PS c;
		for (Conf01Sub1Dto fac : dto.getFacilities()) {
			for (Conf01Sub2Dto dev : fac.getDevices()) {
				for (Conf01Sub3Dto point : dev.getPoints()) {
					c = new PS();
					ObjectUtil.toObject(ObjectUtil.toMap(point), c);
					psMap.put(c.point_nm, c);
				}
			}
		}

		return psMap;
	}

	public Map<String, Object> parse(Conf01Dto data) throws Exception {

		Map<String, FACTORY> conMap = init_factory_pid(data);

		Map<String, MODEL> modelMap = init_model(data);

		Map<String, FACILITY> facilityMap = init_device_id_facility(data, conMap);

		Map<String, PLC> plcMap = init_conn_adr(data);

		Map<String, DEVICE> deviceMap = init_device_id(data, conMap, modelMap, plcMap, facilityMap);

		Map<String, PS> psMap = init_point_nm(data);

		 init_point_id(data, psMap, deviceMap);

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("factory", conMap.size());
		ret.put("plc", plcMap.size());
		ret.put("device", deviceMap.size());
		ret.put("point", 0);

		return ret;
	}

	public void test(Conf01Dto data) throws Exception {

		Map<String, FACTORY> factoryMap = getFactory(data);

		Map<String, MODEL> modelMap = getModel(data);

		Map<String, FACILITY> facilityMap = getFacilities(data);

		Map<String, PLC> plcMap = getPlc(data);

		Map<String, DEVICE> deviceMap = getDevices(data);

		Map<String, POINT> psMap = getPoints(data);

		System.out.println(FxmsUtil.toJson(factoryMap));
		System.out.println(FxmsUtil.toJson(modelMap));
		System.out.println(FxmsUtil.toJson(facilityMap));
		System.out.println(FxmsUtil.toJson(plcMap));
		System.out.println(FxmsUtil.toJson(deviceMap));
		System.out.println(FxmsUtil.toJson(psMap));
	}

	private String getEngId(String device_group) {
		if ("E01".equals(device_group)) {
			return "STEAM";
		} else if ("E02".equals(device_group)) {
			return "AIR";
		} else if ("E03".equals(device_group)) {
			return "COOLWATER";
		} else {
			return "NONE";
		}
	}
}
