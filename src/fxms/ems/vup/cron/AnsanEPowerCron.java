package fxms.ems.vup.cron;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.MoApi;
import fxms.bas.api.ServiceApi;
import fxms.bas.api.ValueApi;
import fxms.bas.api.VarApi;
import fxms.bas.cron.Crontab;
import fxms.bas.fxo.FxAttr;
import fxms.bas.fxo.adapter.FxAdapterInfo;
import fxms.bas.fxo.service.ValueService;
import fxms.bas.mo.Mo;
import fxms.bas.vo.PsVoRaw;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dao.VupQid;
import subkjh.bas.BasCfg;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * 반월/시화 전력계 값을 가져와 기록한다.
 * 
 * @author subkjh
 *
 */
@FxAdapterInfo(service = "VupService", descr = "안산 전력계 데이터 VUP에 넣기")
public class AnsanEPowerCron extends Crontab {

	enum Type {
		pressure, instance, accumulate, temperature
	}

	private final VupQid QID = new VupQid();

	public static void main(String[] args) {

		ServiceApi.getApi().setServiceUrl(ValueService.class,
				"rmi://10.0.1.11:63810/" + ValueService.class.getSimpleName());

		AnsanEPowerCron cron = new AnsanEPowerCron();
		try {
			cron.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FxAttr(name = "schedule", description = "실행계획", value = "* * * * *")
	private String schedule;

	@Override
	protected String getSchedule() {
		return schedule;
	}

	@Override
	public void start() throws Exception {
		try {

			Map<String, Object> varInfo = new HashMap<String, Object>();
			varInfo.put("varGrpName", "TIME");
			varInfo.put("varDispName", "안산데이터 VUP적용 최종시간");
			varInfo.put("varDesc", "안산데이터를 VUP에 넣은 최종시간을 나타낸다.");
			String varName = "vup-ansan-table-read-dtm";
			VarApi.getApi().updateVarInfo(varName, varInfo);

			String dtm = VarApi.getApi().getVarValue(varName, "20230301000000");

			dtm = makeRaw(dtm);

			// 처리 내역 기록
			VarApi.getApi().setVarValue(varName, dtm, false);

		} catch (Exception e) {
			Logger.logger.error(e);
		}
	}

	@SuppressWarnings("unchecked")
	private String makeRaw(String dtm) throws Exception {

		QidDao tran = DBManager.getMgr().getDataBase("VUPDB").createQidDao(BasCfg.getHome(VupQid.QUERY_XML_FILE));

		String dtmNew = dtm;

		try {
			tran.start();

			Map<String, Object> wherePara = new HashMap<String, Object>();
			wherePara.put("readPlc", "ADG");
			wherePara.put("dtm", dtm);

			List<Map<String, Object>> list = tran.selectQid2Res(QID.select_vup_ansan_list, wherePara);

			if (list.size() > 0) {

				initMappig();

				for (Map<String, Object> map : list) {

					dtmNew = map.get("DTM").toString();

					PsVoRawList valueList = makePs(map);

					// for debug
					/*
					 * List<PsVo> tmp = new ArrayList<PsVo>(); for (PsVo vo : valueList) { if
					 * (vo.getMoNo() == 101033) { tmp.add(vo); } }
					 * System.out.println(DateUtil.toHstime(valueList.getMstime()) + " : " +
					 * FxmsUtil.toJson(tmp));
					 */

					ValueApi.getApi().addValue(valueList, false);
				}
			}

		} catch (Exception e) {
			Logger.logger.error(e);
		} finally {
			tran.stop();
		}

		return dtmNew;
	}

	final Map<String, Mo> flowRateMapping = new HashMap<String, Mo>();
	final Map<String, Mo> ePowerMapping = new HashMap<String, Mo>();
	final Map<String, Mo> ePowerAmtMapping = new HashMap<String, Mo>();
	final Map<String, Mo> flowRateIntMapping = new HashMap<String, Mo>();
	final Map<String, Mo> MoStatus = new HashMap<String, Mo>();
	final Map<String, Mo> eCurrent = new HashMap<String, Mo>();
	final Map<String, Mo> airPressure = new HashMap<String, Mo>();

	private void initMappig() throws Exception {

		MoApi api = MoApi.getApi();

		Map<String, Mo> moMap = MoApi.toMoNameMap(api
				.getMoList(FxApi.makePara("moClass", VupApi.VIRTUAL_MO_CLASS, "moType", VupApi.VIRTUAL_POWER_MO_TYPE)));

		eCurrent.put("READ_VAL006", moMap.get("공압기 1"));
		eCurrent.put("READ_VAL007", moMap.get("공압기 2"));
		eCurrent.put("READ_VAL008", moMap.get("공압기 3"));

		ePowerMapping.put("READ_VAL009", moMap.get("공압기 1"));
		ePowerAmtMapping.put("READ_VAL010", moMap.get("공압기 1"));
		airPressure.put("READ_VAL011", moMap.get("공압기 1"));
		MoStatus.put("READ_VAL013", moMap.get("공압기 1"));

		ePowerMapping.put("READ_VAL014", moMap.get("공압기 2"));
		ePowerAmtMapping.put("READ_VAL015", moMap.get("공압기 2"));
		airPressure.put("READ_VAL016", moMap.get("공압기 2"));
		MoStatus.put("READ_VAL018", moMap.get("공압기 2"));

		ePowerMapping.put("READ_VAL019", moMap.get("공압기 3"));
		ePowerAmtMapping.put("READ_VAL020", moMap.get("공압기 3"));
		airPressure.put("READ_VAL021", moMap.get("공압기 3"));
		MoStatus.put("READ_VAL023", moMap.get("공압기 3"));

//		 6 	READ_VAL006	FLOAT			공압실.공압기 1 평균전류	Amps	공통	 정상 	
//		 7 	READ_VAL007	FLOAT			공압실.공압기 2 평균전류	Amps	공통	 센서불량 	
//		 8 	READ_VAL008	FLOAT			공압실.공압기 3 평균전류	Amps	공통	 센서불량 	
//		 9 	READ_VAL009	FLOAT			공압실.공압기 1 kW	kW	공통	 정상 	 사용 
//		 10 	READ_VAL010	DOUBLE 			공압실.공압기 1 kWh	kWh	공통	 정상 	 사용 
//		 11 	READ_VAL011	FLOAT			공압실.공압기 1 압력	bar	공통	 정상 	 사용 
//		 12 	READ_VAL012	INT  			공압실.공압기 1 운전 시간	Hr	공통	 정상 	
//		 13 	READ_VAL013	TINYINT			공압실.공압기 1 상태	On/Off	공통	 정상 	
//		 14 	READ_VAL014	FLOAT			공압실.공압기 2 kW	kW	공통	 센서불량 	 사용 
//		 15 	READ_VAL015	DOUBLE 			공압실.공압기 2 kWh	kWh	공통	 정상 	 사용 
//		 16 	READ_VAL016	FLOAT			공압실.공압기 2 압력	bar	공통	 정상 	 사용 
//		 17 	READ_VAL017	INT  			공압실.공압기 2 운전 시간	Hr	공통	 정상 	
//		 18 	READ_VAL018	TINYINT			공압실.공압기 2 상태	On/Off	공통	 정상 	
//		 19 	READ_VAL019	FLOAT			공압실.공압기 3 kW	kW	공통	 정상 	 사용 
//		 20 	READ_VAL020	DOUBLE 			공압실.공압기 3 kWh	kWh	공통	 정상 	 사용 
//		 21 	READ_VAL021	FLOAT			공압실.공압기 3 압력	bar	공통	 정상 	 사용 
//		 22 	READ_VAL022	INT  			공압실.공압기 3 운전 시간	Hr	공통	 정상 	
//		 23 	READ_VAL023	TINYINT			공압실.공압기 3 상태	On/Off	공통	 정상 	

		flowRateMapping.put("READ_VAL026", moMap.get("유량계.광명"));
		flowRateMapping.put("READ_VAL028", moMap.get("유량계.기양_1"));
		flowRateMapping.put("READ_VAL030", moMap.get("유량계.기양_2"));
		flowRateMapping.put("READ_VAL032", moMap.get("유량계.대진"));
		flowRateMapping.put("READ_VAL034", moMap.get("유량계.메인텍"));
		flowRateMapping.put("READ_VAL036", moMap.get("유량계.양지"));
		flowRateMapping.put("READ_VAL038", moMap.get("유량계.영일"));
		flowRateMapping.put("READ_VAL040", moMap.get("유량계.오성"));
		flowRateMapping.put("READ_VAL042", moMap.get("유량계.우진"));
		flowRateMapping.put("READ_VAL044", moMap.get("유량계.지에스"));
		flowRateMapping.put("READ_VAL046", moMap.get("유량계.폐수장"));
		flowRateMapping.put("READ_VAL048", moMap.get("유량계.HPC"));
		flowRateMapping.put("READ_VAL050", moMap.get("유량계.SKC"));
		flowRateMapping.put("READ_VAL052", moMap.get("유량계.SKPC"));

		flowRateIntMapping.put("READ_VAL027", moMap.get("유량계.광명"));
		flowRateIntMapping.put("READ_VAL029", moMap.get("유량계.기양_1"));
		flowRateIntMapping.put("READ_VAL031", moMap.get("유량계.기양_2"));
		flowRateIntMapping.put("READ_VAL033", moMap.get("유량계.대진"));
		flowRateIntMapping.put("READ_VAL035", moMap.get("유량계.메인텍"));
		flowRateIntMapping.put("READ_VAL037", moMap.get("유량계.양지"));
		flowRateIntMapping.put("READ_VAL038", moMap.get("유량계.영일"));
		flowRateIntMapping.put("READ_VAL041", moMap.get("유량계.오성"));
		flowRateIntMapping.put("READ_VAL043", moMap.get("유량계.우진"));
		flowRateIntMapping.put("READ_VAL045", moMap.get("유량계.지에스"));
		flowRateIntMapping.put("READ_VAL047", moMap.get("유량계.폐수장"));
		flowRateIntMapping.put("READ_VAL049", moMap.get("유량계.HPC"));
		flowRateIntMapping.put("READ_VAL051", moMap.get("유량계.SKC"));
		flowRateIntMapping.put("READ_VAL053", moMap.get("유량계.SKPC"));

//		 24 	READ_VAL024	FLOAT			유량.메인.유량	Nm3/h	공통	 정상 	 사용 
//		 25 	READ_VAL025	FLOAT			유량.메인.적산유량	Nm3	공통	 정상 	 사용 
//		 26 	READ_VAL026	FLOAT			유량.광명.유량	Nm3/h	광명	 정상 	 사용 
//		 27 	READ_VAL027	FLOAT			유량.광명.적산유량	Nm3	광명	 정상 	 사용 
//		 28 	READ_VAL028	FLOAT			유량.기양_1.유량	Nm3/h	기양	 정상 	 사용 
//		 29 	READ_VAL029	FLOAT			유량.기양_1.적산유량	Nm3	기양	 정상 	 사용 
//		 30 	READ_VAL030	FLOAT			유량.기양_2.유량	Nm3/h	기양	 센서불량 	 사용 
//		 31 	READ_VAL031	FLOAT			유량.기양_2.적산유량	Nm3	기양	 정상 	 사용 
//		 32 	READ_VAL032	FLOAT			유량.대진.유량	Nm3/h	대진	 정상 	 사용 
//		 33 	READ_VAL033	FLOAT			유량.대진.적산유량	Nm3	대진	 정상 	 사용 
//		 34 	READ_VAL034	FLOAT			유량.메인텍.유량	Nm3/h	메인텍	 정상 	 사용 
//		 35 	READ_VAL035	FLOAT			유량.메인텍.적산유량	Nm3	메인텍	 정상 	 사용 
//		 36 	READ_VAL036	FLOAT			유량.양지.유량	Nm3/h	양지	 정상 	 사용 
//		 37 	READ_VAL037	FLOAT			유량.양지.적산유량	Nm3	양지	 정상 	 사용 
//		 38 	READ_VAL038	FLOAT			유량.영일.유량	Nm3/h	영일	 정상 	 사용 
//		 39 	READ_VAL039	FLOAT			유량.영일.적산유량	Nm3	영일	 정상 	 사용 
//		 40 	READ_VAL040	FLOAT			유량.오성.유량	Nm3/h	오성	 정상 	 사용 
//		 41 	READ_VAL041	FLOAT			유량.오성.적산유량	Nm3	오성	 정상 	 사용 
//		 42 	READ_VAL042	FLOAT			유량.우진.유량	Nm3/h	우진	 정상 	 사용 
//		 43 	READ_VAL043	FLOAT			유량.우진.적산유량	Nm3	우진	 정상 	 사용 
//		 44 	READ_VAL044	FLOAT			유량.지에스.유량	Nm3/h	지에스	 정상 	 사용 
//		 45 	READ_VAL045	FLOAT			유량.지에스.적산유량	Nm3	지에스	 정상 	 사용 
//		 46 	READ_VAL046	FLOAT			유량.폐수장.유량	Nm3/h	폐수장	 센서불량 	 사용 
//		 47 	READ_VAL047	FLOAT			유량.폐수장.적산유량	Nm3	폐수장	 센서불량 	 사용 
//		 48 	READ_VAL048	FLOAT			유량.HPC.유량	Nm3/h	HPC	 정상 	 사용 
//		 49 	READ_VAL049	FLOAT			유량.HPC.적산유량	Nm3	HPC	 정상 	 사용 
//		 50 	READ_VAL050	FLOAT			유량.SKC.유량	Nm3/h	SKC	 정상 	 사용 
//		 51 	READ_VAL051	FLOAT			유량.SKC.적산유량	Nm3	SKC	 정상 	 사용 
//		 52 	READ_VAL052	FLOAT			유량.SKPC.유량	Nm3/h	SKC	 정상 	 사용 
//		 53 	READ_VAL053	FLOAT			유량.SKPC.적산유량	Nm3	SKC	 정상 	 사용 

		ePowerMapping.put("READ_VAL054", moMap.get("전력계.그레이트솔루션")); // X
		ePowerMapping.put("READ_VAL058", moMap.get("W-F020014 / 대진알미늄 전력계"));
		ePowerMapping.put("READ_VAL062", moMap.get("전력계.안산도금")); // X
		ePowerMapping.put("READ_VAL066", moMap.get("W-F020008 / 우진도금 전력계"));
		ePowerMapping.put("READ_VAL070", moMap.get("W-F020002 / 지에스켐텔 전력계"));
		ePowerMapping.put("READ_VAL074", moMap.get("W-F020006 / 기양금속2 전력계"));
		ePowerMapping.put("READ_VAL078", moMap.get("W-F020013 / 영일도금 전력계"));
		ePowerMapping.put("READ_VAL082", moMap.get("W-F020007 / ㈜메인텍 전력계"));
		ePowerMapping.put("READ_VAL086", moMap.get("W-F020003 / 오성케미칼 전력계"));
		ePowerMapping.put("READ_VAL090", moMap.get("전력계.HPC")); // X
		ePowerMapping.put("READ_VAL094", moMap.get("W-F020005 / 광명금속 전력계"));
		ePowerMapping.put("READ_VAL098", moMap.get("전력계.SKPC")); // X
		ePowerMapping.put("READ_VAL102", moMap.get("W-F020004 / 승화산업 전력계"));
		ePowerMapping.put("READ_VAL106", moMap.get("W-F020010 / 기양금속 전력계"));
		ePowerMapping.put("READ_VAL110", moMap.get("전력계.SKC")); // X

		ePowerAmtMapping.put("READ_VAL055", moMap.get("전력계.그레이트솔루션"));// X
		ePowerAmtMapping.put("READ_VAL059", moMap.get("전력계.대진알루미늄"));
		ePowerAmtMapping.put("READ_VAL063", moMap.get("전력계.안산도금"));// X
		ePowerAmtMapping.put("READ_VAL067", moMap.get("W-F020008 / 우진도금 전력계"));
		ePowerAmtMapping.put("READ_VAL071", moMap.get("W-F020002 / 지에스켐텔 전력계"));
		ePowerAmtMapping.put("READ_VAL075", moMap.get("W-F020006 / 기양금속2 전력계"));
		ePowerAmtMapping.put("READ_VAL079", moMap.get("W-F020013 / 영일도금 전력계"));
		ePowerAmtMapping.put("READ_VAL083", moMap.get("W-F020007 / ㈜메인텍 전력계"));
		ePowerAmtMapping.put("READ_VAL087", moMap.get("W-F020003 / 오성케미칼 전력계"));
		ePowerAmtMapping.put("READ_VAL091", moMap.get("전력계.HPC"));// X
		ePowerAmtMapping.put("READ_VAL095", moMap.get("W-F020005 / 광명금속 전력계"));
		ePowerAmtMapping.put("READ_VAL099", moMap.get("전력계.SKPC"));// X
		ePowerAmtMapping.put("READ_VAL103", moMap.get("W-F020004 / 승화산업 전력계"));
		ePowerAmtMapping.put("READ_VAL107", moMap.get("W-F020010 / 기양금속 전력계"));
		ePowerAmtMapping.put("READ_VAL111", moMap.get("전력계.SKC"));// X

//		 54 	READ_VAL054	FLOAT			전력.그레이트솔루션.KW	KW	그레이트솔루션	 정상 	 사용 
//		 55 	READ_VAL055	DOUBLE 			전력.그레이트솔루션.KWh	KWh	그레이트솔루션	 정상 	 사용 
//		 56 	READ_VAL056	FLOAT			전력.그레이트솔루션.KwPF	PF	그레이트솔루션	 정상 	
//		 57 	READ_VAL057	FLOAT			전력.그레이트솔루션.평균전압	Volt	그레이트솔루션	 정상 	
//		 58 	READ_VAL058	FLOAT			전력.대진알루미늄.KW	KW	대진알루미늄	 정상 	 사용 
//		 59 	READ_VAL059	DOUBLE 			전력.대진알루미늄.KWh	KWh	대진알루미늄	 정상 	 사용 
//		 60 	READ_VAL060	FLOAT			전력.대진알루미늄.KwPF	PF	대진알루미늄	 정상 	
//		 61 	READ_VAL061	FLOAT			전력.대진알루미늄.평균전압	Volt	대진알루미늄	 정상 	
//		 62 	READ_VAL062	FLOAT			전력.안산도금.KW	KW	안산도금	 정상 	 사용 
//		 63 	READ_VAL063	DOUBLE 			전력.안산도금.KWh	KWh	안산도금	 정상 	 사용 
//		 64 	READ_VAL064	FLOAT			전력.안산도금.KwPF	PF	안산도금	 정상 	
//		 65 	READ_VAL065	FLOAT			전력.안산도금.평균전압	Volt	안산도금	 정상 	
//		 66 	READ_VAL066	FLOAT			전력.우진도금.KW	KW	우진도금	 정상 	 사용 
//		 67 	READ_VAL067	DOUBLE 			전력.우진도금.KWh	KWh	우진도금	 정상 	 사용 
//		 68 	READ_VAL068	FLOAT			전력.우진도금.KwPF	PF	우진도금	 정상 	
//		 69 	READ_VAL069	FLOAT			전력.우진도금.평균전압	Volt	우진도금	 정상 	
//		 70 	READ_VAL070	FLOAT			전력.지에스컴텍.KW	KW	지에스컴텍	 정상 	 사용 
//		 71 	READ_VAL071	DOUBLE 			전력.지에스컴텍.KWh	KWh	지에스컴텍	 정상 	 사용 
//		 72 	READ_VAL072	FLOAT			전력.지에스컴텍.KwPF	PF	지에스컴텍	 정상 	
//		 73 	READ_VAL073	FLOAT			전력.지에스컴텍.평균전압	Volt	지에스컴텍	 정상 	
//		 74 	READ_VAL074	FLOAT			전력.기양금속2.KW	KW	기양금속2	 정상 	 사용 
//		 75 	READ_VAL075	DOUBLE 			전력.기양금속2.KWh	KWh	기양금속2	 정상 	 사용 
//		 76 	READ_VAL076	FLOAT			전력.기양금속2.KwPF	PF	기양금속2	 정상 	
//		 77 	READ_VAL077	FLOAT			전력.기양금속2.평균전압	Volt	기양금속2	 정상 	
//		 78 	READ_VAL078	FLOAT			전력.영일도금.KW	KW	영일도금	 정상 	 사용 
//		 79 	READ_VAL079	DOUBLE 			전력.영일도금.KWh	KWh	영일도금	 정상 	 사용 
//		 80 	READ_VAL080	FLOAT			전력.영일도금.KwPF	PF	영일도금	 정상 	
//		 81 	READ_VAL081	FLOAT			전력.영일도금.평균전압	Volt	영일도금	 정상 	
//		 82 	READ_VAL082	FLOAT			전력.메인텍.KW	KW	메인텍	 정상 	 사용 
//		 83 	READ_VAL083	DOUBLE 			전력.메인텍.KWh	KWh	메인텍	 정상 	 사용 
//		 84 	READ_VAL084	FLOAT			전력.메인텍.KwPF	PF	메인텍	 정상 	
//		 85 	READ_VAL085	FLOAT			전력.메인텍.평균전압	Volt	메인텍	 정상 	
//		 86 	READ_VAL086	FLOAT			전력.오성케미칼.KW	KW	오성케미칼	 정상 	 사용 
//		 87 	READ_VAL087	DOUBLE 			전력.오성케미칼.KWh	KWh	오성케미칼	 정상 	 사용 
//		 88 	READ_VAL088	FLOAT			전력.오성케미칼.KwPF	PF	오성케미칼	 정상 	
//		 89 	READ_VAL089	FLOAT			전력.오성케미칼.평균전압	Volt	오성케미칼	 정상 	
//		 90 	READ_VAL090	FLOAT			전력.HPC.KW	KW	HPC	 정상 	 사용 
//		 91 	READ_VAL091	DOUBLE 			전력.HPC.KWh	KWh	HPC	 정상 	 사용 
//		 92 	READ_VAL092	FLOAT			전력.HPC.KwPF	PF	HPC	 정상 	
//		 93 	READ_VAL093	FLOAT			전력.HPC.평균전압	Volt	HPC	 정상 	
//		 94 	READ_VAL094	FLOAT			전력.광명금속.KW	KW	광명금속	 정상 	 사용 
//		 95 	READ_VAL095	DOUBLE 			전력.광명금속.KWh	KWh	광명금속	 정상 	 사용 
//		 96 	READ_VAL096	FLOAT			전력.광명금속.KwPF	PF	광명금속	 정상 	
//		 97 	READ_VAL097	FLOAT			전력.광명금속.평균전압	Volt	광명금속	 정상 	
//		 98 	READ_VAL098	FLOAT			전력.SKPC.KW	KW	SKPC	 정상 	 사용 
//		 99 	READ_VAL099	DOUBLE 			전력.SKPC.KWh	KWh	SKPC	 정상 	 사용 
//		 100 	READ_VAL100	FLOAT			전력.SKPC.KwPF	PF	SKPC	 정상 	
//		 101 	READ_VAL101	FLOAT			전력.SKPC.평균전압	Volt	SKPC	 정상 	
//		 102 	READ_VAL102	FLOAT			전력.승화산업.KW	KW	승화산업	 정상 	 사용 
//		 103 	READ_VAL103	DOUBLE 			전력.승화산업.KWh	KWh	승화산업	 정상 	 사용 
//		 104 	READ_VAL104	FLOAT			전력.승화산업.KwPF	PF	승화산업	 정상 	
//		 105 	READ_VAL105	FLOAT			전력.승화산업.평균전압	Volt	승화산업	 정상 	
//		 106 	READ_VAL106	FLOAT			전력.기양금속.KW	KW	기양금속	 정상 	 사용 
//		 107 	READ_VAL107	DOUBLE 			전력.기양금속.KWh	KWh	기양금속	 정상 	 사용 
//		 108 	READ_VAL108	FLOAT			전력.기양금속.KwPF	PF	기양금속	 정상 	
//		 109 	READ_VAL109	FLOAT			전력.기양금속.평균전압	Volt	기양금속	 정상 	
//		 110 	READ_VAL110	FLOAT			전력.SKC.KW	KW	SKC	 센서불량 	 사용 
//		 111 	READ_VAL111	DOUBLE 			전력.SKC.KWh	KWh	SKC	 센서불량 	 사용 
//		 112 	READ_VAL112	FLOAT			전력.SKC.KwPF	PF	SKC	 센서불량 	
//		 113 	READ_VAL113	FLOAT			전력.SKC.평균전압	Volt	SKC	 센서불량

		MoStatus.put("READ_VAL118", moMap.get("유량계.폐수장"));
		MoStatus.put("READ_VAL119", moMap.get("유량계.지에스"));
		MoStatus.put("READ_VAL120", moMap.get("유량계.오성"));
		MoStatus.put("READ_VAL121", moMap.get("유량계.양지"));
		MoStatus.put("READ_VAL122", moMap.get("유량계.광명"));
		MoStatus.put("READ_VAL123", moMap.get("유량계.기양_2"));
		MoStatus.put("READ_VAL124", moMap.get("유량계.메인텍"));
		MoStatus.put("READ_VAL125", moMap.get("유량계.우진"));
		MoStatus.put("READ_VAL126", moMap.get("유량계.HPC"));
		MoStatus.put("READ_VAL127", moMap.get("유량계.기양_1"));
		MoStatus.put("READ_VAL128", moMap.get("유량계.SKPC"));
		MoStatus.put("READ_VAL129", moMap.get("유량계.SKC"));
		MoStatus.put("READ_VAL130", moMap.get("유량계.영일"));
		MoStatus.put("READ_VAL131", moMap.get("유량계.대진"));

//		 114 	READ_VAL114	TINYINT			통신.공압기 1 전력 통신	OnLine/OffLine	공통	 정상 	
//		 115 	READ_VAL115	TINYINT			통신.공압기 2 전력 통신	OnLine/OffLine	공통	 정상 	
//		 116 	READ_VAL116	TINYINT			통신.공압기 3 전력 통신	OnLine/OffLine	공통	 정상 	
//		 117 	READ_VAL117	TINYINT			통신.공압기 전체 전력 통신	OnLine/OffLine	공통	 정상 	
//		 118 	READ_VAL118	TINYINT			통신.유량계.폐수장 통신	OnLine/OffLine	공통	 센서불량 	
//		 119 	READ_VAL119	TINYINT			통신.유량계.지에스 통신	OnLine/OffLine	지에스	 정상 	
//		 120 	READ_VAL120	TINYINT			통신.유량계.오성 통신	OnLine/OffLine	오성	 정상 	
//		 121 	READ_VAL121	TINYINT			통신.유량계.양지 통신	OnLine/OffLine	양지	 정상 	
//		 122 	READ_VAL122	TINYINT			통신.유량계.광명 통신	OnLine/OffLine	광명	 정상 	
//		 123 	READ_VAL123	TINYINT			통신.유량계.기양2 통신	OnLine/OffLine	기양2	 정상 	
//		 124 	READ_VAL124	TINYINT			통신.유량계.메인텍 통신	OnLine/OffLine	메인텍	 정상 	
//		 125 	READ_VAL125	TINYINT			통신.유량계.우진 통신	OnLine/OffLine	우진	 정상 	
//		 126 	READ_VAL126	TINYINT			통신.유량계.HPC 통신	OnLine/OffLine	HPC	 정상 	
//		 127 	READ_VAL127	TINYINT			통신.유량계.기양1 통신	OnLine/OffLine	기양1	 정상 	
//		 128 	READ_VAL128	TINYINT			통신.유량계.SKPC 통신	OnLine/OffLine	SKPC	 정상 	
//		 129 	READ_VAL129	TINYINT			통신.유량계.SKC 통신	OnLine/OffLine	SKC	 정상 	
//		 130 	READ_VAL130	TINYINT			통신.유량계.영일 통신	OnLine/OffLine	영일	 정상 	
//		 131 	READ_VAL131	TINYINT			통신.유량계.대진 통신	OnLine/OffLine	대진	 정상 	

		MoStatus.put("READ_VAL132", moMap.get("전력계.폐수장"));
		MoStatus.put("READ_VAL133", moMap.get("전력계.지에스컴텍"));
		MoStatus.put("READ_VAL134", moMap.get("전력계.오성케미칼"));

		MoStatus.put("READ_VAL136", moMap.get("전력계.광명금속"));
		MoStatus.put("READ_VAL137", moMap.get("전력계.기양금속2"));
		MoStatus.put("READ_VAL138", moMap.get("전력계.메인텍"));
		MoStatus.put("READ_VAL139", moMap.get("전력계.우진도금"));
		MoStatus.put("READ_VAL140", moMap.get("전력계.HPC"));
		MoStatus.put("READ_VAL141", moMap.get("전력계.기양금속"));
		MoStatus.put("READ_VAL142", moMap.get("전력계.SKPC"));
		MoStatus.put("READ_VAL143", moMap.get("전력계.SKC"));
		MoStatus.put("READ_VAL144", moMap.get("전력계.영일도금"));
		MoStatus.put("READ_VAL145", moMap.get("전력계.대진알루미늄"));

//		 132 	READ_VAL132	TINYINT			통신.전력계.폐수장 통신	OnLine/OffLine	폐수장	 센서불량 	
//		 133 	READ_VAL133	TINYINT			통신.전력계.지에스 통신	OnLine/OffLine	지에스	 센서불량 	
//		 134 	READ_VAL134	TINYINT			통신.전력계.오성 통신	OnLine/OffLine	오성	 센서불량 	
//		 135 	READ_VAL135	TINYINT			통신.전력계.양지 통신	OnLine/OffLine	양지	 센서불량 	
//		 136 	READ_VAL136	TINYINT			통신.전력계.광명 통신	OnLine/OffLine	광명	 센서불량 	
//		 137 	READ_VAL137	TINYINT			통신.전력계.기양2 통신	OnLine/OffLine	기양2	 센서불량 	
//		 138 	READ_VAL138	TINYINT			통신.전력계.메인텍 통신	OnLine/OffLine	메인텍	 센서불량 	
//		 139 	READ_VAL139	TINYINT			통신.전력계.우진 통신	OnLine/OffLine	우진	 센서불량 	
//		 140 	READ_VAL140	TINYINT			통신.전력계.HPC 통신	OnLine/OffLine	HPC	 센서불량 	
//		 141 	READ_VAL141	TINYINT			통신.전력계.기양1 통신	OnLine/OffLine	기양1	 센서불량 	
//		 142 	READ_VAL142	TINYINT			통신.전력계.SKPC 통신	OnLine/OffLine	SKPC	 센서불량 	
//		 143 	READ_VAL143	TINYINT			통신.전력계.SKC 통신	OnLine/OffLine	SKC	 센서불량 	
//		 144 	READ_VAL144	TINYINT			통신.전력계.영일 통신	OnLine/OffLine	영일	 센서불량 	
//		 145 	READ_VAL145	TINYINT			통신.전력계.대진 통신	OnLine/OffLine	대진	 센서불량 	

	}

	private PsVoRawList makePs(Map<String, Object> map) {

		PsVoRawList psList = new PsVoRawList(getClass().getSimpleName(), DateUtil.toMstime(map.get("DTM").toString()));

		Number value;
		Mo mo;
		for (String key : flowRateMapping.keySet()) {
			value = (Number) map.get(key);
			mo = flowRateMapping.get(key);
			if (value != null && mo != null) {
				// psList.add(new PsVo(mo, FemsCode.PS_ITEM.flowRate, value.doubleValue()));
			}
		}

		for (String key : flowRateIntMapping.keySet()) {
			value = (Number) map.get(key);
			mo = flowRateIntMapping.get(key);
			if (value != null && mo != null) {
				// psList.add(new PsVo(mo, FemsCode.PS_ITEM.flowRateInt, value.doubleValue()));
			}
		}

		for (String key : ePowerMapping.keySet()) {
			value = (Number) map.get(key);
			mo = ePowerMapping.get(key);
			if (value != null && mo != null) {
				psList.add(new PsVoRaw(mo.getMoNo(), VupApi.VUP_PS_ITEM.ePower.name(), value.doubleValue()));
			}
		}

		for (String key : ePowerAmtMapping.keySet()) {
			value = (Number) map.get(key);
			mo = ePowerAmtMapping.get(key);
			if (value != null && mo != null) {
				psList.add(new PsVoRaw(mo.getMoNo(), VupApi.VUP_PS_ITEM.ePowerAccum.name(), value.doubleValue()));
			}
		}

		for (String key : eCurrent.keySet()) {
			value = (Number) map.get(key);
			mo = eCurrent.get(key);
			if (value != null && mo != null) {
				// psList.add(new PsVo(mo, FemsCode.PS_ITEM.eCurrent, value.doubleValue()));
			}
		}

		for (String key : airPressure.keySet()) {
			value = (Number) map.get(key);
			mo = airPressure.get(key);
			if (value != null && mo != null) {
				// psList.add(new PsVo(mo, FemsCode.PS_ITEM.airPressure, value.doubleValue()));
			}
		}

		for (String key : MoStatus.keySet()) {
			value = (Number) map.get(key);
			mo = MoStatus.get(key);
			if (value != null && mo != null) {
				// psList.add(new PsVo(mo, PsApi.MO_STATUS_PS_ID, value.doubleValue()));
			}
		}

		return psList;

//		COLUMN_NAME	TYPE	DEFAULT	N/N	COMMENT	단위	COMPANY	 정합성 	 VUP 사용 
//		READ_PLC	VARCHAR(10)			PLC Name	이름			
//		READ_DT	DATE			저장 일자	년-월-일			
//		READ_TM	TIME			저장 시간	시:분:초			
//	 1 	READ_VAL001	FLOAT			공압실.평균전류	Amps	공통	 센서불량 	
//	 2 	READ_VAL002	FLOAT			공압실.전체 kW	kW	공통	 정상 	 사용 
//	 3 	READ_VAL003	DOUBLE 			공압실.전체 kWh	kWh	공통	 정상 	 사용 
//	 4 	READ_VAL004	FLOAT			공압실.전체 역율	%	공통	 정상 	 사용 
//	 5 	READ_VAL005	FLOAT			공압실.전체 평균 전압	Volt	공통	 센서불량 	

	}
}
