package fxms.ems.vup.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.InloApi;
import fxms.bas.api.MappingApi;
import fxms.bas.api.MoApi;
import fxms.bas.api.ModelApi;
import fxms.bas.api.PsApi;
import fxms.bas.api.ServiceApi;
import fxms.bas.exp.FxServiceNotFoundException;
import fxms.bas.exp.InloNotFoundException;
import fxms.bas.exp.NotFoundException;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.fxo.service.ValueService;
import fxms.bas.impl.dbo.all.FX_CF_DATA;
import fxms.bas.impl.dbo.all.FX_MAPP_ETC;
import fxms.bas.impl.dpo.ao.AlCfgMap;
import fxms.bas.impl.dpo.inlo.InloDpo;
import fxms.bas.mo.Mo;
import fxms.bas.signal.ReloadSignal.ReloadType;
import fxms.bas.vo.Inlo;
import fxms.bas.vo.MoModel;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.mapp.MappData;
import fxms.bas.vo.mapp.MappMoPs;
import fxms.ems.bas.dbo.FE_ENG_BAS;
import fxms.ems.bas.dbo.FE_FAC_BAS;
import fxms.ems.bas.dbo.FE_FAC_PIPE;
import fxms.ems.bas.dbo.FE_FAC_PIPE_PATH;
import fxms.ems.bas.dpo.PlantSelectDfo;
import fxms.ems.bas.vo.PlantVo;
import fxms.ems.vup.dbo.all.VUP_COMM_COMPLEX;
import fxms.ems.vup.dbo.all.VUP_COMM_DEVICE;
import fxms.ems.vup.dbo.all.VUP_COMM_FACILITY;
import fxms.ems.vup.dbo.all.VUP_COMM_PLANT;
import fxms.ems.vup.dto.Alarm01Dto;
import fxms.ems.vup.dto.Conf01Dto;
import fxms.ems.vup.dto.Value01Dto;
import fxms.ems.vup.enitt.AlarmParser;
import fxms.ems.vup.enitt.ConfParser;
import fxms.ems.vup.enitt.ValueParser;
import subkjh.bas.co.log.LOG_LEVEL;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

public class VupApi extends FxApi {

	public enum ENG_ID {
		/** 압공 */
		E01,
		/** 스팀 */
		E02,
		/** 냉수 */
		E03
	}

	public enum INLO_CL_CD {
		COMPLEX, COMPANY, PLANT;
	}

	public enum PsType {
		pressure, instance, accumulate, temperature
	}

	public enum VUP_PS_ITEM {
		/** 전력순시값 */
		ePower(PsType.instance),
		/** 전력적산값 */
		ePowerAccum(PsType.accumulate),

		/** 압공 압력계값 */
		E01V3(PsType.pressure),
		/** 압공 유량계 순시값 */
		E01V4(PsType.instance),
		/** 압공 유량계 적산값 */
		E01V5(PsType.accumulate),

		/** 스팀 압력계값 */
		E02V3(PsType.pressure),
		/** 스팀 유량계 순시값 */
		E02V4(PsType.instance),
		/** 스팀 유량계 적산값 */
		E02V5(PsType.accumulate),

		/** 압공 온도계 온도값 */
		V04G3V1(PsType.temperature),
		/** 스팀 온도계 온도값 */
		V04G3V2(PsType.temperature);

		private PsType type;

		private VUP_PS_ITEM(PsType type) {
			this.type = type;
		}

		public PsType getPsType() {
			return this.type;
		}
	}

	/** use DBM */
	public static VupApi api;
	/** 계측기 MO분류 */
	public static final String SENSOR_MO_CLASS = "SENSOR";
	/** PLC MO분류 */
	public static final String PLC_MO_CLASS = "NODE";
	/** PLC MO유형 */
	public static final String PLC_MO_TYPE = "PLC";
	/** 가상MO MO분류 */
	public static final String VIRTUAL_MO_CLASS = "MO";

	/** 가상MO MO유형 */
	public static final String VIRTUAL_MO_TYPE = "VIRTUAL";

	/** 공장전력계 MO유형 */
	public static final String VIRTUAL_POWER_MO_TYPE = "VIRTUAL_EPOWER";
	public static final String VIRTUAL_COMPRESS_DRYER_MO_TYPE = "COMPRESS/DRYER";

	/** VUP 관리구분 */
	public static final String VUP_MNG_DIV = "VUP";
	public static final String VUP_MODEL_VENDOR = "VUP";

	/**
	 * 사용할 DBM를 제공합니다.
	 *
	 * @return DBM
	 */
	public synchronized static VupApi getApi() {
		if (api != null)
			return api;

		api = new VupApi();
		try {
			api.onCreated();
		} catch (Exception e) {

		}

		return api;
	}

	public static String getTid(String plantId, String pid) {
		return plantId.trim() + "-" + pid.replaceAll("-", "").trim();
	}

	private final List<FE_ENG_BAS> engList;
	private final Map<String, PlantVo> inloTidMap;
	private final AlCfgMap cfgMap;

	public VupApi() {

		this.inloTidMap = new HashMap<>();
		this.engList = new ArrayList<>();
		this.cfgMap = AlCfgMap.getInstance();

		try {
			this.engList.addAll(loadEngIds());
		} catch (Exception e) {
			Logger.logger.error(e);
		}
	}

	public Map<String, Object> checkAlarm(Alarm01Dto dto) throws Exception {
		return new AlarmParser().parse(dto);
	}

	public Map<String, Object> checkConf(Conf01Dto data) throws Exception {
		return new ConfParser().parse(data);
	}

	public Map<String, Object> checkValue(Value01Dto data) throws Exception {
		return new ValueParser().parse(data);
	}

	public int getAlarmCfgNo(String moClass, String moType) {
		return cfgMap.getAlarmCfgNo(moClass, moType);
	}

	/**
	 * 
	 * @param engTid
	 * @return
	 * @throws Exception
	 */
	public String getEngId(String engTid) throws Exception {

		for (FE_ENG_BAS e : this.engList) {
			if (engTid.equalsIgnoreCase(e.getEngTid())) {
				return e.getEngId();
			}
		}

		for (FE_ENG_BAS e : this.engList) {
			if (engTid.equalsIgnoreCase(e.getEngName())) {
				return e.getEngId();
			}
		}

		throw new NotFoundException("Energy Id", engTid);

	}

	public FE_FAC_BAS getFac4MappData(String mngDiv, Object mappId) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();

			FX_MAPP_ETC mapp = tran.selectOne(FX_MAPP_ETC.class,
					FxApi.makePara("mngDiv", VUP_MNG_DIV, "mappId", mappId));
			if (mapp != null) {
				return tran.selectOne(FE_FAC_BAS.class, FxApi.makePara("facNo", mapp.getObjData()));
			} else {
				return null;
			}

		} catch (Exception e) {
			Logger.logger.error(e);
			throw e;
		} finally {
			tran.stop();
		}
	}

	public FE_FAC_BAS getFacility(String facilityPid) {

		try {
			List<FE_FAC_BAS> list = this.selectList(FE_FAC_BAS.class, makePara("delYn", "N", "facTid", facilityPid));
			return list.size() == 1 ? list.get(0) : null;
		} catch (Exception e) {
			Logger.logger.error(e);
			return null;
		}
	}

	public long getFacNo(String devicePid) throws Exception {

		try {
			MappData mapp = makeMappFac(devicePid, null);

			FE_FAC_BAS data = this.getFac4MappData(mapp.getMngDiv(), mapp.getMappId());
			return data.getFacNo();
		} catch (Exception e) {
			throw new NotFoundException("facility", devicePid);
		}
	}

	/**
	 * TID 키로 설비 목록을 조회한다.
	 * 
	 * @return
	 */
	public Map<String, FE_FAC_BAS> getFacTidMap() throws Exception {
		Map<String, FE_FAC_BAS> map = new HashMap<String, FE_FAC_BAS>();
		List<FE_FAC_BAS> list = this.selectList(FE_FAC_BAS.class, makePara("delYn", "N"));
		for (FE_FAC_BAS bas : list) {
			if (bas.getFacTid() != null) {
				map.put(bas.getFacTid(), bas);
			}
		}
		return map;
	}

	/**
	 * 
	 * @param inloClCd
	 * @return
	 * @throws Exception
	 */
	public Map<String, PlantVo> getInlos(INLO_CL_CD inloClCd) throws Exception {
		return new PlantSelectDfo()
				.selectFactoryMap(inloClCd != null ? FxApi.makePara("inloClCd", inloClCd.name()) : null);
	}

	/**
	 * 
	 * @param pointPid
	 * @return
	 */
	public MappMoPs getMappMoPs(String pointPid) {
		try {
			return MappingApi.getApi().getMappMoPs(VUP_MNG_DIV, pointPid);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * mappId를 키로 성능 매핑데이터를 조회한다.
	 * 
	 * @return
	 */
	public Map<String, MappMoPs> getMappPsAll() {
		return MappingApi.getApi().getMoPsAll(VUP_MNG_DIV);
	}

	/**
	 * 
	 * @param devicePid
	 * @return
	 */
	public Mo getMoByDeviceTag(String devicePid) {
		try {
			List<Mo> list = MoApi.getApi().getMoList(makePara("moTid", devicePid));
			return list.size() == 1 ? list.get(0) : null;
		} catch (Exception e) {
			Logger.logger.error(e);
			return null;
		}
	}

	/**
	 * 계측기, 설비가상관리대상을 TID키로 제공한다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Mo> getMoTidMap() throws Exception {
		MoApi api = MoApi.getApi();

		Map<String, Mo> moMap = MoApi.toMoTidMap(api.getMoList(makePara("moClass", SENSOR_MO_CLASS)));
		moMap.putAll(MoApi.toMoTidMap(api.getMoList(makePara("moClass", VIRTUAL_MO_CLASS, "moType", VIRTUAL_MO_TYPE))));
		moMap.putAll(MoApi
				.toMoTidMap(api.getMoList(makePara("moClass", VIRTUAL_MO_CLASS, "moType", VIRTUAL_POWER_MO_TYPE))));
		moMap.putAll(MoApi.toMoTidMap(
				api.getMoList(makePara("moClass", VIRTUAL_MO_CLASS, "moType", VIRTUAL_COMPRESS_DRYER_MO_TYPE))));
		return moMap;
	}

	public PlantVo getPlant(Object tid) throws InloNotFoundException {
		synchronized (this.inloTidMap) {
			PlantVo inlo = this.inloTidMap.get(String.valueOf(tid));
			if (inlo != null)
				return inlo;

			throw new InloNotFoundException(tid);
		}
	}

	@Override
	public String getState(LOG_LEVEL level) {
		return null;
	}

	public String getTid(VUP_COMM_DEVICE device) {
		return getTid(device.getPlantPid(), device.getDevicePid());
	}

	public String getTid(VUP_COMM_FACILITY fac) {
		return getTid(fac.getPlantName(), fac.getFacPid());
	}

	public ValueService getValueService() throws FxServiceNotFoundException, Exception {
		return ServiceApi.getApi().getService(ValueService.class);
	}

	/**
	 * VUP 프로젝트의 설치위치 정보를 제공한다.<br>
	 * 
	 * @return
	 */
	public Inlo getVupProjectInlo() {
		return InloApi.getApi().getInlo(VUP_MNG_DIV, "PROJECT");
	}

	public MappData makeMappAlarm(String tag, String tagName) {
		return new MappData(VUP_MNG_DIV, "POINT-TAG:" + tag, tagName, tag);
	}

	public MappData makeMappDevice(String devicePid, String deviceName) {
		return new MappData(VUP_MNG_DIV, "DEVICE-TAG:" + devicePid, deviceName, devicePid);
	}

	public MappData makeMappFac(String devicePid, String deviceNm) {
		return new MappData(VUP_MNG_DIV, "FACILITY-TAG:" + devicePid, deviceNm, devicePid);
	}

	public MappData makeMappPlant(String plantPid, String plantName) {
		return new MappData(VUP_MNG_DIV, "PLANT-TAG:" + plantPid, plantName, plantPid);
	}

	/**
	 * 관제점 매핑 만들기
	 * 
	 * @param tag
	 * @param tagName
	 * @return
	 */
	public MappData makeMappPs(String tag, String tagName) {
		return new MappData(VUP_MNG_DIV, "POINT-TAG:" + tag, tagName, tag);
	}

	public Map<String, Object> makeNode(String ip, String protocol, String port, String name) {
		if (name == null) {
			name = ip;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("moClass", PLC_MO_CLASS);
		data.put("moName", name);
		data.put("moDispName", name);
		data.put("moMemo", "VUP내용 적용");
		data.put("moType", PLC_MO_TYPE);
		data.put("nodeIpAddr", ip);
		data.put("commProtc", protocol);
		data.put("commPortNo", port);

		return data;
	}

	@Override
	public void onCreated() throws Exception {
		reloadAll();
	}

	@Override
	public void reload(Enum<?> type) throws Exception {

		if (type == ReloadType.Mo || type == ReloadType.All || type == ReloadType.MappData) {

			cfgMap.load();

			reloadAll();

		}

	}

	public List<FX_CF_DATA> selectDatas(String dataClass) throws Exception {
		List<FX_CF_DATA> list = selectList(FX_CF_DATA.class, makePara("dataClass", dataClass));

		list.sort(new Comparator<FX_CF_DATA>() {

			@Override
			public int compare(FX_CF_DATA o1, FX_CF_DATA o2) {
				if (o1.getSortSeq() != null && o2.getSortSeq() != null) {
					return o1.getSortSeq().intValue() - o2.getSortSeq().intValue();
				}
				return 0;
			}
		});

		return list;
	}

	public void setFac(String facTid, Map<String, Object> para) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();
			setFac(tran, makePara("facTid", facTid, "delYn", "N"), para);
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	public void setFacWithName(String facName, Map<String, Object> para) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();
			setFac(tran, makePara("facName", facName, "delYn", "N"), para);
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	public void setPipe(String pipeId, Map<String, Object> para) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();
			List<FE_FAC_PIPE> list = tran.select(FE_FAC_PIPE.class, makePara("pipeId", pipeId));
			if (list.size() == 0) {
				FE_FAC_PIPE data = new FE_FAC_PIPE();
				ObjectUtil.toObject(para, data);
				FxTableMaker.initRegChg(0, data);
				tran.insertOfClass(FE_FAC_PIPE.class, data);
			} else if (list.size() == 1) {
				FE_FAC_PIPE data = list.get(0);
				ObjectUtil.toObject(para, data);
				FxTableMaker.initRegChg(0, data);
				tran.updateOfClass(FE_FAC_PIPE.class, data);
			}
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	public void setPipePath(String pipeId, List<FE_FAC_PIPE_PATH> paths) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();

			for (FE_FAC_PIPE_PATH e : paths) {
				FxTableMaker.initRegChg(0, e);
				e.setPipeId(pipeId);
			}

			tran.deleteOfClass(FE_FAC_PIPE_PATH.class, FxApi.makePara("pipeId", pipeId));
			tran.insertOfClass(FE_FAC_PIPE_PATH.class, paths);

			tran.commit();
		} catch (Exception e) {
			System.out.println("'" + pipeId + "'");
			tran.rollback();
			throw e;
		} finally {
			tran.stop();
		}

	}

	public void updateComplexs(List<VUP_COMM_COMPLEX> complexList) throws Exception {

		Inlo vup = VupApi.getApi().getVupProjectInlo();
		Map<String, PlantVo> inloMap = getInlos(INLO_CL_CD.COMPLEX);

		for (VUP_COMM_COMPLEX c : complexList) {
			Inlo inlo = inloMap.get(c.getComplexPid());
			try {
				if (inlo == null) {
					InloApi.getApi().addInlo(0, makeComplex(c, vup.getInloNo()));
				} else {
					InloApi.getApi().updateInlo(0, inlo.getInloNo(), makeComplex(c, vup.getInloNo()));
				}
			} catch (Exception e) {
				Logger.logger.error(e);
			}

		}
	}

	public void updateDeviceCompressorAndDryer(VUP_COMM_FACILITY fac) throws Exception {

		Map<String, Object> data = makeMoFacility(fac.getPlantName(), fac.getFacPid(), fac.getFacName(),
				fac.getPointIds());
		String moTid = data.get("moTid").toString();
		Mo mo = getMoByDeviceTag(moTid);

		setMo(mo, data);
	}

	public void updateDevices(List<VUP_COMM_DEVICE> devices) throws Exception {

		Map<String, Mo> moMap = getMoTidMap();

		for (VUP_COMM_DEVICE c : devices) {
			try {
				if ("virtual".equalsIgnoreCase(c.getDeviceType()) == false
						|| (c.getPsIds() != null && c.getPsIds().length() > 0)) {
					if (c.getDevicePid().startsWith("W-F0")) {
						updatePower(c, moMap.get(getTid(c)));
					} else {
						updateDevice(c, moMap.get(getTid(c)));
					}
				}
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}
	}

	/**
	 * 모델 업데이트
	 * 
	 * @param devices
	 * @throws Exception
	 */
	public void updateModels(List<VUP_COMM_DEVICE> devices) throws Exception {

		// 종류 추출
		List<String> deviceTypes = new ArrayList<String>();
		for (VUP_COMM_DEVICE c : devices) {
			if (deviceTypes.contains(c.getDeviceType()) == false) {
				deviceTypes.add(c.getDeviceType());
			}
		}

		MoModel model;
		Map<String, MoModel> modelMap = ModelApi.toNameMap(ModelApi.getApi().getModels(null));
		for (String deviceType : deviceTypes) {
			model = modelMap.get(deviceType);
			if (model == null) {
				ModelApi.getApi().addModel(0, makeModel(deviceType));
			} else {
				ModelApi.getApi().updateModel(0, model.getModelNo(), makeModel(deviceType));
			}
		}
	}

	public void updatePlants(List<VUP_COMM_PLANT> plantList) throws Exception {

		Inlo plant, complex, company;
		InloApi api = InloApi.getApi();
		Map<String, PlantVo> inloMap = getInlos(INLO_CL_CD.PLANT);

		for (VUP_COMM_PLANT c : plantList) {

			try {

				plant = inloMap.get(c.getPlantPid());
				complex = inloMap.get(c.getComplexPid());

				if (complex != null) {
					if (plant == null) {
						// 회사 추가
						int inloNo = api.addInlo(0, makeCompany(c, complex));
						company = api.getInlo(inloNo);

						// 공장 추가
						inloNo = api.addInlo(0, makePlant(c, company));
						Logger.logger.fail("added plant: {}.{} {}", inloNo, c.getPlantPid(), c.getPlantName());

					} else {
						company = inloMap.get(makeCompanyPid(c.getPlantPid()));
						if (company == null) {
							int inloNo = api.addInlo(0, makeCompany(c, complex));
							company = api.getInlo(inloNo);
							api.updateInlo(0, plant.getInloNo(), makePlant(c, company));
						} else {
							api.updateInlo(0, company.getInloNo(), makeCompany(c, complex));
							api.updateInlo(0, plant.getInloNo(), makePlant(c, company));
						}

						Logger.logger.fail("updated plant: {}.{} {}", plant.getInloNo(), c.getPlantPid(),
								c.getPlantName());
					}
				} else {
					Logger.logger.fail("not found complex: tid={}", c.getComplexPid());
				}
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}
	}

	private List<FE_ENG_BAS> loadEngIds() throws Exception {
		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();

		try {
			tran.start();

			return tran.select(FE_ENG_BAS.class, FxApi.makePara("useYn", "Y"));

		} catch (Exception e) {
			Logger.logger.error(e);

			throw e;
		} finally {
			tran.stop();
		}
	}

	private Map<String, Object> makeCompany(VUP_COMM_PLANT c, Inlo complex) {
		return FxApi.makePara("inloName", c.getPlantName(), "inloClCd", "COMPANY", "inloTid",
				makeCompanyPid(c.getPlantPid()), "inloDesc", c.getPlantDescr(), "upperInloNo", complex.getInloNo(),
				"mngDiv", "vup", "inloAllName", InloDpo.getAllName(complex.getInloAllName(), c.getPlantName()));
	}

	private String makeCompanyPid(String plantPid) {
		return "C" + plantPid;
	}

	private Map<String, Object> makeComplex(VUP_COMM_COMPLEX c, int upperInloNo) {
		return FxApi.makePara("inloName", c.getComplexName(), "inloClCd", "COMPLEX", "inloTid", c.getComplexPid(),
				"inloDesc", c.getComplexDescr(), "upperInloNo", upperInloNo, "mngDiv", "vup", "inloAllName",
				c.getComplexName());
	}

	private Map<String, Object> makeModel(String deviceType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelName", deviceType);
		map.put("vendrName", "VUP");
		map.put("modelClCd", "NONE");
		map.put("modelDescr", "자동동록");
		if (deviceType.endsWith("계")) {
			map.put("moClass", "SENSOR");
		}
		return map;
	}

	private Map<String, Object> makeMoFacility(String plantPid, String facPid, String facName, String pointIds) {

		Map<String, Object> data = new HashMap<String, Object>();

		String moTid = getTid(plantPid, facPid);

		data.put("moName", facName);
		data.put("moDispName", facName);
		data.put("moMemo", facPid);
		data.put("moType", VIRTUAL_COMPRESS_DRYER_MO_TYPE);
		data.put("moTid", moTid);
		data.put("alarmCfgNo", getAlarmCfgNo(VIRTUAL_MO_CLASS, VIRTUAL_COMPRESS_DRYER_MO_TYPE));
		data.put("pollCycle", 0); // 1분 단위에 LOC에서 데이터가 오기 때문에 지정해 놓아야 데이터가 들어오지 않으면 알람을 발생한다.
		data.put("moClass", VIRTUAL_MO_CLASS);

		data.put("pointIds", pointIds);
		data.put("facilityPid", facPid);

		try {
			Inlo inlo = getPlant(plantPid);
			data.put("inloNo", inlo.getInloNo());
		} catch (InloNotFoundException e) {
		}

		return data;

	}

	private Map<String, Object> makeMoPower(String plantPid, String devicePid, String deviceName, String deviceType) {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("moName", deviceName);
		data.put("moDispName", deviceName);
		data.put("moMemo", deviceName);
		data.put("moType", deviceType);
		data.put("moTid", getTid(plantPid, devicePid));
		data.put("alarmCfgNo", getAlarmCfgNo(VIRTUAL_MO_CLASS, VIRTUAL_POWER_MO_TYPE));
		data.put("pollCycle", 0); // 1분 단위에 LOC에서 데이터가 오기 때문에 지정해 놓아야 데이터가 들어오지 않으면 알람을 발생한다.
		data.put("moClass", VIRTUAL_MO_CLASS);
		data.put("moType", VIRTUAL_POWER_MO_TYPE);

		try {
			Inlo inlo = getPlant(plantPid);
			data.put("inloNo", inlo.getInloNo());
		} catch (InloNotFoundException e) {
		}

		MoModel model = ModelApi.getApi().getModel(deviceType, VUP_MODEL_VENDOR);
		if (model != null) {
			data.put("modelNo", model.getModelNo());
		}

		return data;
	}

	private Map<String, Object> makeMoSensor(VUP_COMM_DEVICE device) throws Exception {

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("moName", device.getDeviceName());
		data.put("moDispName", device.getDevicePid());
		data.put("moMemo", device.getDeviceName());
		data.put("moType", device.getDeviceType());
		data.put("moTid", getTid(device));

		data.put("alarmCfgNo", getAlarmCfgNo(SENSOR_MO_CLASS, null));
		data.put("pollCycle", 60); // 1분 단위에 LOC에서 데이터가 오기 때문에 지정해 놓아야 데이터가 들어오지 않으면 알람을 발생한다.
		data.put("moClass", SENSOR_MO_CLASS);

		data.put("sensrName", device.getDeviceName());
		data.put("sensrDesc", device.getDeviceUnit());
		data.put("manftSn", "");
		data.put("commProtc", device.getCommType());
		data.put("engId", getEngId(device.getEnergyId()));

		data.put("pipeId", device.getPipeId());
		data.put("buyDate", "20230101");
		data.put("calibDate", "20230319");

		MoModel model = ModelApi.getApi().getModel(device.getDeviceType(), VUP_MODEL_VENDOR);
		if (model != null)
			data.put("modelNo", model.getModelNo());

		if (isNotEmpty(device.getFacPid()))
			data.put("moMemo", "설비 : " + device.getFacPid());
		else if (isNotEmpty(device.getPipeId()))
			data.put("moMemo", "배관 : " + device.getPipeId());

		if (device.getFacPid() != null) {
			FE_FAC_BAS bas = this.getFacility(device.getFacPid());
			if (bas != null) {
				data.put("facNo", bas.getFacNo());
			}
		}

		try {
			Inlo inlo = getPlant(device.getPlantPid());
			data.put("inloNo", inlo.getInloNo());
		} catch (Exception e) {
		}

		try {
			if (device.getPsIds() != null) {
				String sensrPsIds = "";
				String ss[] = device.getPsIds().split(",");
				for (String s : ss) {
					PsItem item = PsApi.getApi().findItemByName(s);
					if (item != null) {
						if (sensrPsIds.length() > 0)
							sensrPsIds += ",";
						sensrPsIds += item.getPsId();
					}
				}
				data.put("sensrPsId", sensrPsIds);
			}
		} catch (Exception e) {
			Logger.logger.error(e);
		}
		return data;
	}

	private Map<String, Object> makeMoVirtual(VUP_COMM_DEVICE device) throws Exception {

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("moName", device.getDeviceName());
		data.put("moDispName", device.getDevicePid());
		data.put("moMemo", device.getDeviceName());
		data.put("moType", device.getDeviceType());
		data.put("moTid", getTid(device));

		data.put("alarmCfgNo", getAlarmCfgNo(VIRTUAL_MO_CLASS, VIRTUAL_MO_TYPE));
		data.put("pollCycle", 0); // 1분 단위에 LOC에서 데이터가 오기 때문에 지정해 놓아야 데이터가 들어오지 않으면 알람을 발생한다.
		data.put("moClass", VIRTUAL_MO_CLASS);
		data.put("moType", VIRTUAL_MO_TYPE);

		return data;

	}

	private Map<String, Object> makePlant(VUP_COMM_PLANT c, Inlo company) {
		return FxApi.makePara("inloName", c.getPlantName(), "inloClCd", "PLANT", "inloTid", c.getPlantPid(), "inloDesc",
				c.getPlantDescr(), "upperInloNo", company.getInloNo(), "mngDiv", "vup", "inloAllName",
				InloDpo.getAllName(company.getInloAllName(), c.getPlantName()));
	}

	private void reloadAll() {
		synchronized (this.inloTidMap) {
			this.inloTidMap.clear();
			try {
				this.inloTidMap.putAll(getInlos(null));
			} catch (Exception e) {
				Logger.logger.error(e);
			}
		}

	}

	private <T> List<T> selectList(Class<T> classOfT, Object para) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();
			return tran.select(classOfT, para);
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	private void setFac(ClassDao tran, Map<String, Object> wherePara, Map<String, Object> para) throws Exception {

		List<FE_FAC_BAS> list = tran.select(FE_FAC_BAS.class, wherePara);
		if (list.size() == 0) {
			FE_FAC_BAS data = new FE_FAC_BAS();
			ObjectUtil.toObject(para, data);
			FxTableMaker.initRegChg(0, data);
			data.setFacNo(tran.getNextVal(FE_FAC_BAS.FX_SEQ_FACNO, Long.class));
			tran.insertOfClass(FE_FAC_BAS.class, data);
		} else if (list.size() == 1) {
			FE_FAC_BAS data = list.get(0);
			ObjectUtil.toObject(para, data);
			FxTableMaker.initRegChg(0, data);
			tran.updateOfClass(FE_FAC_BAS.class, data);
		}
		tran.commit();

	}

	private void setMo(Mo mo, Map<String, Object> data) throws Exception {
		if (mo != null) {
			MoApi.getApi().updateMo(0, mo.getMoNo(), data, false);
			Logger.logger.info("update : {}", FxmsUtil.toJson(data));
		} else {
			mo = MoApi.getApi().addMo(0, data.get("moClass").toString(), data, "comm2tisp", false);
			Logger.logger.info("insert : {}", FxmsUtil.toJson(data));
		}
	}

	private void updateDevice(VUP_COMM_DEVICE device, Mo mo) throws Exception {

		Map<String, Object> data;
		if ("VIRTUAL".equals(device.getDeviceType()) == false) {
			data = makeMoSensor(device);
		} else {
			data = makeMoVirtual(device);
		}

		setMo(mo, data);
	}

	private void updatePower(VUP_COMM_DEVICE device, Mo mo) throws Exception {

		Map<String, Object> data = makeMoPower(device.getPlantPid(), device.getDevicePid(), device.getDeviceName(),
				device.getDeviceType());

		setMo(mo, data);

	}
}
