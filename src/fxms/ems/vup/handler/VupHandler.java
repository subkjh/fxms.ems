package fxms.ems.vup.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.MappingApi;
import fxms.bas.api.PsApi;
import fxms.bas.api.ValueApi;
import fxms.bas.exp.NotFoundException;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.handler.BaseHandler;
import fxms.bas.handler.MethodDescr;
import fxms.bas.handler.vo.SessionVo;
import fxms.bas.mo.Mo;
import fxms.bas.vo.Inlo;
import fxms.bas.vo.PsValues;
import fxms.bas.vo.mapp.MappMoPs;
import fxms.ems.bas.dbo.FE_ENG_RT_BAS;
import fxms.ems.bas.dbo.FE_ENG_TRANS_BAS;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dao.VupHandlerQid;
import fxms.ems.vup.dpo.EngRtSelectDfo;
import fxms.ems.vup.dpo.EngTransRtInsertDfo;
import fxms.ems.vup.dpo.EngTransSelectDfo;
import fxms.ems.vup.dpo.MakeEnergySettlementDfo;
import fxms.ems.vup.dpo.MakeEnergyTransactionDfo;
import fxms.ems.vup.dto.Alarm01Dto;
import fxms.ems.vup.dto.Alm02Dto;
import fxms.ems.vup.dto.Conf01Dto;
import fxms.ems.vup.dto.Conf03Dto;
import fxms.ems.vup.dto.Conf04Dto;
import fxms.ems.vup.dto.Conf05Dto;
import fxms.ems.vup.dto.Conf06Dto;
import fxms.ems.vup.dto.Oper01Dto;
import fxms.ems.vup.dto.Oper02Dto;
import fxms.ems.vup.dto.Oper03Dto;
import fxms.ems.vup.dto.Tran01Dto;
import fxms.ems.vup.dto.Tran02Dto;
import fxms.ems.vup.dto.Tran03Dto;
import fxms.ems.vup.dto.Tran04Dto;
import fxms.ems.vup.dto.Tran05Dto;
import fxms.ems.vup.dto.Tran06Dto;
import fxms.ems.vup.dto.Tran07Dto;
import fxms.ems.vup.dto.Tran08Dto;
import fxms.ems.vup.dto.Tran09Dto;
import fxms.ems.vup.dto.Val05Dto;
import fxms.ems.vup.dto.Val06Dto;
import fxms.ems.vup.dto.Val07Dto;
import fxms.ems.vup.dto.Val08Dto;
import fxms.ems.vup.dto.Val09Dto;
import fxms.ems.vup.dto.Value01Dto;
import fxms.ems.vup.dto.Value04Dto;
import fxms.ems.vup.dto.VupEngTransCalcDto;
import fxms.ems.vup.enitt.ConfParser;
import fxms.ems.vup.vo.ComplexVo;
import fxms.ems.vup.vo.ConfigAllVo;
import fxms.ems.vup.vo.PipeVo;
import fxms.ems.vup.vo.PlantVo;
import fxms.ems.vup.vo.VupPsValues;
import subkjh.bas.BasCfg;
import subkjh.bas.co.user.User.USER_TYPE_CD;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * VUP 제공용 API
 * 
 * @author subkjh
 *
 */
public class VupHandler extends BaseHandler {

	public static void main(String[] args) throws Exception {
		VupHandler handler = new VupHandler();
		Map<String, Object> parameters = new HashMap<String, Object>();
		SessionVo session = new SessionVo("test", 102, "SOIL", "test", USER_TYPE_CD.Operator, 102, 2000);
		parameters.put("complex_pid", "F0101");

//		parameters.put("pipeId", "VUP-ICCA-01-000");
		Object ret = handler.conf07(session);
		System.out.println(FxmsUtil.toJson(ret));
	}

	private final VupHandlerQid QID = new VupHandlerQid();

	private final VupApi api;

	public VupHandler() {
		this.api = VupApi.getApi();
	}

	@MethodDescr(name = "VUP-API-ALM-01) 발생된 알람, 해제된 알람, 미전송된 알람 모두 전송", description = "고기원에서 예측한 에너지 소비량을 기록한다.")
	public Object alm01(SessionVo session, String json) throws Exception {

		Alarm01Dto data = FxmsUtil.toObject(json, Alarm01Dto.class);

		if (data.getNotifications() != null && data.getNotifications().size() > 0) {
			return VupApi.getApi().checkAlarm(data);
		} else {
			throw new NotFoundException("data", "not found notifications");
		}

	}

	@MethodDescr(name = "VUP-API-ALM-02) 공장에서 발생된 알람 제공", description = "고기원에서 예측한 에너지 소비량을 기록한다.")
	public Object alm02(SessionVo session, Alm02Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_alm_02, para);
	}

	@MethodDescr(name = "API-VUP-CONF-03) 공장 목록 정보 조회", description = "등록된 공장 정보를 조회하여 제공한다.")
	public Object conf03(SessionVo session, Conf03Dto dto) throws Exception {
		Inlo inlo = api.getPlant(dto.complex_pid);
		return this.selectListQid(QID.select_factory_list, FxApi.makePara("inloNo", inlo.getInloNo()));
	}

	@MethodDescr(name = "API-VUP-CONF-04) 산단 배관 정보 조회", description = "등록된 산단의 배관 정보를 조회하여 제공한다.")
	public Object conf04(SessionVo session, Conf04Dto dto) throws Exception {
		Inlo inlo = api.getPlant(dto.complex_pid);
		QidDao tran = getQidDao();
		try {
			tran.start();
			return selectPipeAll(tran, FxApi.makePara("inloNo", inlo.getInloNo()));
		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	@MethodDescr(name = "API-VUP-CONF-05) 공장 UT 설비 조회", description = "공장에 설치된 UT 설비를 조회하여 제공한다.")
	public Object conf05(SessionVo session, Conf05Dto dto) throws Exception {
		Inlo inlo = api.getPlant(dto.factory_pid);
		return this.selectListQid(QID.select_ut_facility_list, FxApi.makePara("inloNo", inlo.getInloNo()));
	}

	@MethodDescr(name = "API-VUP-CONF-06) 공장 공정 설비 조회", description = "공장에 설치된 공정 설비를 조회하여 제공한다.")
	public Object conf06(SessionVo session, Conf06Dto dto) throws Exception {
		Inlo inlo = api.getPlant(dto.factory_pid);
		return this.selectListQid(QID.select_prod_facility_list, FxApi.makePara("inloNo", inlo.getInloNo()));
	}

	@MethodDescr(name = "API-VUP-CONF-07) 기준정보 전체 조회", description = "산단, 공장, 에너지, UT설비, 계측기, 배관 정보를 모두 조회한다.")
	@SuppressWarnings("unchecked")
	public Object conf07(SessionVo session) throws Exception {
		QidDao tran = getQidDao();

		ConfigAllVo ret = new ConfigAllVo();

		try {
			tran.start();

			Map<String, ComplexVo> complexMap = new HashMap<String, ComplexVo>();
			Map<String, PlantVo> plantMap = new HashMap<String, PlantVo>();
			ComplexVo complex;
			PlantVo plant;

			List<Map<String, Object>> complexList = tran.selectQid2Res(QID.select_complex_all, null);
			for (Map<String, Object> map : complexList) {
				complex = new ComplexVo(map);
				complexMap.put(complex.getComplexId(), complex);
				ret.addComplex(complex);
			}

			List<Map<String, Object>> pipeList = selectPipeAll(tran, null);
			for (Map<String, Object> map : pipeList) {
				complex = complexMap.get(String.valueOf(map.get("complex_pid")));
				if (complex != null) {
					complex.addPipe(map);
				}
			}

			List<Map<String, Object>> factoryList = tran.selectQid2Res(QID.select_factory_all, null);
			for (Map<String, Object> map : factoryList) {
				complex = complexMap.get(String.valueOf(map.get("complex_pid")));
				if (complex != null) {
					complex.addPlant(map);

					plant = new PlantVo(map);
					plantMap.put(plant.getPlantId(), plant);
				}
			}

			List<Map<String, Object>> uiList = tran.selectQid2Res(QID.select_ut_facility_all, null);
			for (Map<String, Object> map : uiList) {
				plant = plantMap.get(String.valueOf(map.get("factory_pid")));
				if (plant != null) {
					plant.addUtFacility(map);
				}
			}

			List<Map<String, Object>> sensorList = tran.selectQid2Res(QID.select_sensor_all, null);
			for (Map<String, Object> map : sensorList) {
				plant = plantMap.get(String.valueOf(map.get("factory_pid")));
				if (plant != null) {
					plant.addSensor(map);
				}
			}

			List<Map<String, Object>> energyList = tran.selectQid2Res(QID.select_energy_all, null);
			ret.addEnergy(energyList);

			return ret;

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}
	}

	@MethodDescr(name = "VUP-API-OPER-01) UT설비 유지보수 계획 조회", description = "플렛폼에 등록되어 있는 UT설비 유지보수 계획을 조회한다.")
	public Object oper01(SessionVo session, Oper01Dto dto) throws Exception {
		Inlo inlo = api.getPlant(dto.factory_pid);
		String mntnResvDate = dto.mntn_resv_date;

		return this.selectListQid(QID.select_fac_mntn_plan_list,
				FxApi.makePara("inloNo", inlo.getInloNo(), "mntnResvDate", mntnResvDate));
	}

	@SuppressWarnings("unchecked")
	@MethodDescr(name = "VUP-API-OPER-02) 공정설비 운영 계획 조회", description = "플렛폼에 등록되어 있는 공정설비 운영 계획 조회을 조회한다.")
	public Object oper02(SessionVo session, Oper02Dto dto) throws Exception {

		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);

		List<Map<String, Object>> list = (List<Map<String, Object>>) this.selectListQid(QID.select_fac_oper_plan_list,
				para);
		List<Map<String, Object>> pathList = (List<Map<String, Object>>) this
				.selectListQid(QID.select_fac_oper_plan_dtl_list, para);

		String facNo;
		List<Map<String, Object>> pList;
		for (Map<String, Object> path : pathList) {

			facNo = path.get("fac_no").toString();
			for (Map<String, Object> map : list) {

				if (facNo.equals(map.get("fac_no").toString())) {
					pList = (List<Map<String, Object>>) map.get("days");
					if (pList == null) {
						pList = new ArrayList<Map<String, Object>>();
						map.put("days", pList);
					}
					pList.add(path);
					break;
				}
			}
		}

		return list;
	}

	@MethodDescr(name = "VUP-API-OPER-03) 공정설비 제품 생산 계획 조회", description = "플렛폼에 등록되어 있는 공정설비 제품 생산 계획을 조회한다.")
	public Object oper03(SessionVo session, Oper03Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_fac_prod_plan_list, para);
	}

	@MethodDescr(name = "VUP-API-TRAN-01. 에너지 거래 내역 등록", description = "거래 결과 내역을 받아 기록한다.")
	public Object tran01(SessionVo session, Tran01Dto dto) throws Exception {
		MakeEnergyTransactionDfo dfo = new MakeEnergyTransactionDfo();
		return dfo.setEnergyTransaction(dto, null);
	}

	@MethodDescr(name = "VUP-API-TRAN-02. 에너지 거래 내역 조회", description = "플렛폼에 등록되어 있는 거래 내역을 제공한다.")
	public Object tran02(SessionVo session, Tran02Dto dto) throws Exception {
		return this.selectListQid(QID.select_tran_02, dto);
	}

	@SuppressWarnings("unchecked")
	@MethodDescr(name = "VUP-API-TRAN-03. 에너지 경로 목록 조회", description = "산단에의 모든 에너지 경로를 제공한다.")
	public Object tran03(SessionVo session, Tran03Dto dto) throws Exception {

		Inlo inlo = api.getPlant(dto.complex_pid);

		List<Map<String, Object>> list = (List<Map<String, Object>>) this.selectListQid(QID.select_energy_rt_list,
				FxApi.makePara("inloNo", inlo.getInloNo()));
		List<Map<String, Object>> pathList = (List<Map<String, Object>>) this
				.selectListQid(QID.select_energy_rt_path_list, FxApi.makePara("inloNo", inlo.getInloNo()));

		String engRtId;
		List<Map<String, Object>> pList;
		for (Map<String, Object> path : pathList) {
			engRtId = path.get("eng_rt_id").toString();
			for (Map<String, Object> map : list) {
				if (engRtId.equals(map.get("eng_rt_id").toString())) {
					pList = (List<Map<String, Object>>) map.get("path_list");
					if (pList == null) {
						pList = new ArrayList<Map<String, Object>>();
						map.put("path_list", pList);
					}
					pList.add(path);
					break;
				}
			}
		}

		return list;
	}

	@MethodDescr(name = "VUP-API-TRAN-04. 에너지 생산 예측값 설정", description = "고기원에서 예측한 에너지 생산량을 기록한다.")
	public Object tran04(SessionVo session, Tran04Dto data) throws Exception {
		return new VupDao().updateTran04(data);
	}

	@MethodDescr(name = "VUP-API-TRAN-05. 에너지 소비 예측값 설정", description = "고기원에서 예측한 에너지 소비량을 기록한다.")
	public Object tran05(SessionVo session, Tran05Dto data) throws Exception {
		return new VupDao().updateTran05(data);
	}

	@MethodDescr(name = "VUP-API-TRAN-06. 에너지 최적 라우팅 전송", description = "에너지 거래의 최적 라우팅 내역을 설정한다.")
	public Object tran06(SessionVo session, Tran06Dto data) throws Exception {

		FE_ENG_TRANS_BAS bas = new EngTransSelectDfo().select(data.getTrnsNo());
		if (bas == null)
			throw new Exception("에너지 거래 정보가 없습니다. 거래번호:" + data.getTrnsNo());

		FE_ENG_RT_BAS rt = new EngRtSelectDfo().select(data.getEngRtId(), bas.getInloNo());
		if (rt == null)
			throw new Exception("존재하지 않는 에너지 경로입니다. 에너지경로ID:" + data.getEngRtId());

		return new EngTransRtInsertDfo().insert(data);
	}

	@MethodDescr(name = "VUP-API-TRAN-07. 에너지 생산량 예측 내역 조회", description = "플렛폼에 등록되어 있는 에너지 생산 예측량을 조회한다.")
	public Object tran07(SessionVo session, Tran07Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_tran_07, para);
	}

	@MethodDescr(name = "VUP-API-TRAN-08. 에너지 소비량 예측 내역 조회", description = "플렛폼에 등록되어 있는 에너지 소비 예측량을 조회한다.")
	public Object tran08(SessionVo session, Tran08Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_tran_08, para);
	}

	@SuppressWarnings("unchecked")
	@MethodDescr(name = "VUP-API-TRAN-09. 현재 에너지 경로 조회", description = "현재 거래되고 있는 경로를 조회한다.")
	public Object tran09(SessionVo session, Tran09Dto dto) throws Exception {

		Map<String, Object> para = ObjectUtil.toMap(dto);

		// 1. 현재 사용중인 에너지 경로 확인
		String engRtId = "";
		List<Map<String, Object>> tranaList = (List<Map<String, Object>>) this.selectListQid(QID.select_tran_09_01,
				para);
		Map<String, Object> ret;
		if (tranaList.size() == 1) {
			ret = tranaList.get(0);
			engRtId = ret.get("eng_rt_id").toString();
		} else {
			throw new Exception("SOURCE와 SINK사이 거래 정보가 없어 현재 에너지 경로를 알 수 없습니다.");
		}

		List<Map<String, Object>> list = (List<Map<String, Object>>) this.selectListQid(QID.select_tran_09_02,
				FxApi.makePara("engRtId", engRtId));
		List<Map<String, Object>> pathList = (List<Map<String, Object>>) this.selectListQid(QID.select_tran_09_03,
				FxApi.makePara("engRtId", engRtId));

		if (list.size() > 0) {
			ret.put("eng_rt_info", list.get(0));
			ret.put("eng_rt_path_list", pathList);
		} else {
			throw new Exception("에너지경로ID '" + engRtId + "'에 대한 경로를 찾지 못했습니다.");
		}

		return ret;
	}

	@MethodDescr(name = "VUP-API-TRAN-10.에너지 거래 정산 등록", description = "거래에 대한 정산 내역을 받아 기록한다.")
	public Object tran10(SessionVo session, VupEngTransCalcDto dto) throws Exception {
		MakeEnergySettlementDfo dfo = new MakeEnergySettlementDfo();
		return dfo.setEnergySettlement(dto);
	}

	public Object updateConf01(SessionVo session, String json) throws Exception {

		Conf01Dto data = FxmsUtil.toObject(json, Conf01Dto.class);

		if (data.getFacilities() != null && data.getFacilities().size() > 0) {
			new ConfParser().test(data);
//			return new VupApi().checkConf(data);
			throw new Exception("기준데이터는 아직 반영하지 않았습니다.");
		} else {
			throw new NotFoundException("data", "not found facilities");
		}

	}

	@MethodDescr(name = "VUP-API-VAL-01) 수집한 계측기값 전송 (애니트)", description = "수집된 데이트를 1분 단위로 최근 값을 기록한다.")
	public Object val01(SessionVo session, String json) throws Exception {

		Value01Dto data = FxmsUtil.toObject(json, Value01Dto.class);

		if (data.getFacilities() != null && data.getFacilities().size() > 0) {
			return new VupApi().checkValue(data);
		} else {
			throw new NotFoundException("data", "not found devices");
		}
	}

	@MethodDescr(name = "VUP-API-VAL-04) 계측값 조회", description = "플렛폼에서 가지고 있는 센서값 정보를 요청한다.")
	public Object val04(SessionVo session, Value04Dto dto) throws Exception {

		long moNo = -1;
		String psId = null;
		String psKind = PsApi.getApi().getPsKindRaw().getPsKindName();

		if (FxApi.isNotEmpty(dto.point_pid)) {
			MappMoPs moPs = VupApi.getApi().getMappMoPs(dto.point_pid);
			if (moPs == null) {
				throw new Exception("등록되지 않은 관제점TAG 입니다. (" + dto.point_pid + ")");
			}
			psId = moPs.getPsId();
			moNo = moPs.getMoNo();
		}

		if (moNo < 0 && FxApi.isNotEmpty(dto.device_pid)) {
			Mo mo = VupApi.getApi().getMoByDeviceTag(dto.device_pid);
			if (mo == null) {
				throw new Exception("등록되지 않은 계측기TAG 입니다. (" + dto.device_pid + ")");
			}
			moNo = mo.getMoNo();
		}

		if (moNo < 0) {
			throw new Exception("등록되지 않은 계측기 입니다.");
		}

		List<MappMoPs> moPsList = MappingApi.getApi().getMappMoPs(VupApi.VUP_MNG_DIV, moNo);

		long startDtm;
		if (dto.collected_date_start == null) {
			startDtm = Long.parseLong(DateUtil.getYmd() + "000000");
		} else {
			startDtm = Long.parseLong(dto.collected_date_start + "000000");
		}

		long endDtm = DateUtil.getDtm();
		if (dto.collected_date_end != null) {
			endDtm = Long.parseLong(dto.collected_date_end + "235959");
		}

		List<PsValues> values = null;
		if (psId != null) {
			values = ValueApi.getApi().getValues(moNo, psId, psKind, null, startDtm, endDtm);
		} else {
			values = ValueApi.getApi().getValues(moNo, psKind, startDtm, endDtm);
		}

		return this.convertValues(values, moPsList);
	}

	@MethodDescr(name = "VUP-API-VAL-05) 1일 단위 에너지 소비량 조회", description = "공장에서 일 단위로 소비한 에너지량을 제공한다.")
	public Object val05(SessionVo session, Val05Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_val_05, para);
	}

	@MethodDescr(name = "VUP-API-VAL-06) 15분 단위 에너지 소비량 조회", description = "공장에서 15분 단위로 소비한 에너지량을 제공한다.")
	public Object val06(SessionVo session, Val06Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_val_06, para);
	}

	@MethodDescr(name = "VUP-API-VAL-07) 1일 단위 에너지 생산량 조회", description = "공장에서 일 단위로 생산한 에너지량을 제공한다.")
	public Object val07(SessionVo session, Val07Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_val_07, para);
	}

	@MethodDescr(name = "VUP-API-VAL-08) 15분 단위 에너지 생산량 조회", description = "공장에서 15분 단위로 생산한 에너지량을 제공한다.")
	public Object val08(SessionVo session, Val08Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_val_08, para);
	}

	@MethodDescr(name = "VUP-API-VAL-09) 산단 밸브 실시간 상태 조회", description = "플렛폼에서 가지고 있는 밸브의 상태 정보를 제공한다.")
	public Object val09(SessionVo session, Val09Dto dto) throws Exception {
		Map<String, Object> para = makeInloPara(dto.complex_pid, dto.factory_pid, dto);
		return this.selectListQid(QID.select_val_09, para);
	}

	@Override
	protected QidDao getQidDao() throws Exception {
		return DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG)
				.createQidDao(BasCfg.getHome(VupHandlerQid.QUERY_XML_FILE));
	}

	/**
	 * device_tag, point_tag 기준으로 변경한다.
	 * 
	 * @param list
	 * @param moPsList
	 * @return
	 */
	private List<VupPsValues> convertValues(List<PsValues> list, List<MappMoPs> moPsList) {
		List<VupPsValues> ret = new ArrayList<>();
		for (PsValues v : list) {
			for (MappMoPs ps : moPsList) {
				if (ps.getPsId().equals(v.getPsItem().getPsId())) {
					ret.add(new VupPsValues(v.getMo().getMoTid(), ps.getMappId(), v));
					break;
				}
			}
		}

		return ret;
	}

	private Inlo getInlo(Map<String, Object> inPara) throws Exception {
		Inlo inlo;
		Object pid = inPara.get("factory_pid");
		if (pid != null) {
			inlo = api.getPlant(pid);
		} else {
			inlo = api.getPlant(inPara.get("complex_pid"));
		}
		return inlo;
	}

	private Map<String, Object> getSelectPara(Map<String, Object> inPara, boolean includeTime) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();

		Inlo inlo = getInlo(inPara);
		String startDate = DateUtil.checkDate(inPara.get("start_date"));
		String endDate = DateUtil.checkDate(inPara.get("end_date"));

		if (inPara.get("energy_code") != null) {
			ret.put("engId", api.getEngId(inPara.get("energy_code").toString()));
		}
		ret.put("inloNo", inlo.getInloNo());
		if (includeTime) {
			ret.put("startDtm", startDate + "000000");
			ret.put("endDtm", endDate + "235959");
		} else {
			ret.put("startDate", startDate);
			ret.put("endDate", endDate);
		}

		return ret;
	}

	private Map<String, Object> makeInloPara(String complexPid, String factoryPid, Object dto) throws Exception {

		Inlo inlo = null;
		if (factoryPid != null) {
			inlo = VupApi.getApi().getPlant(factoryPid);
		} else if (complexPid != null) {
			inlo = VupApi.getApi().getPlant(complexPid);
		}

		Map<String, Object> para = ObjectUtil.toMap(dto);
		para.put("inloNo", inlo.getInloNo());

		return para;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> selectPipeAll(QidDao tran, Object para) throws Exception {

		Map<String, PipeVo> pipeMap = new HashMap<String, PipeVo>();
		PipeVo pipe;
		Object pipeId;

		List<Map<String, Object>> pipeList = tran.selectQid2Res(QID.select_pipe_list, para);
		List<Map<String, Object>> pathList = tran.selectQid2Res(QID.select_pipe_path_all, para);

		for (Map<String, Object> map : pipeList) {
			pipe = new PipeVo(map);
			pipeMap.put(pipe.getPipeId(), pipe);
		}

		for (Map<String, Object> map : pathList) {
			pipeId = map.get("pipe_id");
			if (pipeId != null) {
				pipe = pipeMap.get(pipeId.toString());
				if (pipe != null) {
					pipe.addPath(map);
				}
			}
		}

		return pipeList;
	}
}
