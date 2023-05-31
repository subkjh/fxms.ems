package fxms.ems.vup;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.PsApi;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.vo.PsItem;
import subkjh.bas.co.utils.FileUtil;

public class 관제점분석 {

	class Data implements Cloneable {
		String plantId, plantName;
		String deviceId, deviceName, deviceType;
		String dUnit, dUnitDescr;
		String pipeId;
		String engNm;
		String commType;
		String psName;
		String gubun;
		String key;
		String item;
		String cycle;
		String tag;

		@Override
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}

		public String getGroupKey() {
			return plantName + "," + item;
		}

		public String toExcel() {
//			DEVICE_PID	DEVICE_NAME	DEVICE_DESCR	DEVICE_UNIT	DEVICE_UNIT_DESCR	ENERGY_ID	PIPE_ID	DEVICE_TYPE
			StringBuffer sb = new StringBuffer();
			sb.append(key).append("\t");
			sb.append(gubun).append("\t");
			sb.append(plantId).append("\t");
			sb.append(item).append("\t");
			sb.append(deviceId).append("\t");
			sb.append(deviceId + " / " + deviceName).append("\t");
			sb.append(deviceId + " / " + deviceName).append("\t");
			sb.append(dUnit).append("\t");
			sb.append(dUnitDescr).append("\t");
			sb.append("압공".equals(engNm) ? "E01" : "스팀".equals(engNm) ? "E02" : "냉수".equals(engNm) ? "E03" : "NONE")
					.append("\t");

			if (isPipe(pipeId)) {
				sb.append(pipeId).append("\t");
			} else {
				sb.append("").append("\t");
			}

			sb.append(commType).append("\t");
			sb.append(deviceType).append("\t");

			// 설비PID
			if (isPipe(pipeId)) {
				sb.append("").append("\t");
			} else {
				sb.append(pipeId).append("\t");
			}

			sb.append(psName);

			return sb.toString();
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(plantId).append("(").append(plantName).append(")");
			sb.append(deviceId).append("(").append(deviceName).append(")");
			sb.append(dUnit).append("(").append(dUnitDescr).append(")");
			sb.append(pipeId).append(",");
			sb.append(engNm);
			return sb.toString();
		}
	}

	class Fac {
		final String plantId;
		final String plantName;
		final String item;
		String tag;
		String deviceId, deviceName, deviceType;
		String pipeId;
		String engNm;
		String commType;
		String dUnit, dUnitDescr;

		/** 성능항목으로 TAGID와 매핑 시켜야 한다. */
		List<Data> psIds = new ArrayList<Data>();

		Fac(Data data) {
			this.plantId = data.plantId;
			this.plantName = data.plantName;
			this.item = data.item;
			this.deviceId = data.deviceId;
			this.deviceName = data.deviceName;
			this.deviceType = data.deviceType;
			this.pipeId = data.pipeId;
			this.engNm = data.engNm;
			this.dUnit = data.dUnit;
			this.dUnitDescr = data.dUnitDescr;
			this.commType = data.commType;
			this.tag = data.tag;
		}

		Fac(String deviceType, String tag, Data data) {
			this(data);
			this.deviceId = "";
			this.deviceName = data.plantName + " " + data.item;
			this.deviceType = deviceType;
			this.tag = tag;
		}

		public String getGroupKey() {
			return plantName + "," + item;
		}

		public String getDeviceName() {
			if (deviceName == null)
				return "";

			String ret = deviceName.replaceFirst("순시 전력", "전력계");
			ret = ret.replaceFirst("순시 유량", "유량계");
			ret = ret.replaceFirst("온도", "온도계");

			return ret;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(plantId).append("\t");
//			sb.append(plantName).append("\t");
//			sb.append(item).append("\t");
//			if ("설비".equals(this.deviceType) && pipeId != null && psIds.size() > 0) {
//				sb.append(pipeId).append("\t");
//				sb.append(pipeId).append(" / ").append(getDeviceName()).append("\t");
//			} else {

			if (deviceId == null || deviceId.trim().length() == 0) {
				sb.append(getDeviceName()).append("\t");
				sb.append(getDeviceName()).append("\t");
			} else {
				sb.append(deviceId).append("\t");
				sb.append(deviceId).append(" / ").append(getDeviceName()).append("\t");
			}
			sb.append(deviceType).append("\t");
			sb.append(deviceName).append("\t");
			sb.append(dUnit).append("\t");
			sb.append(dUnitDescr).append("\t");
			sb.append(commType).append("\t");
			sb.append(engNm).append("\t");
			// 설비PID
			if (isPipe(pipeId)) {
				sb.append(pipeId).append("\t\t");
			} else {
				sb.append("\t").append(pipeId).append("\t");
			}

			for (int i = 0; i < psIds.size(); i++) {
				Data data = psIds.get(i);
//				sb.append(data.deviceId).append(":");
				// sb.append(data.deviceName).append(":");

				if (i > 0)
					sb.append(",");

				PsItem item = psMap.get(data.psName);
				if (item != null) {
					sb.append(data.deviceId + ":" + item.getPsId());
				} else {
					sb.append(data.psName);
				}
			}
			return sb.toString();
		}

	}

	class PsData {
		final String psUnit;
		final String psDesc;

		PsData(String psUnit, String psDesc) {
			this.psUnit = psUnit;
			this.psDesc = psDesc;
		}

		public String toString() {
			return psUnit + "\t" + psDesc;
		}
	}

	public static void main(String[] args) {
		관제점분석 c = new 관제점분석();

		c.analyzeNew(new File("src/fems/vup/관제점_시화.txt"));
		c.analyzeNew(new File("src/fems/vup/관제점_안산.txt"));
		c.analyzeNew(new File("src/fems/vup/관제점_인천.txt"));
//		c.analyzeNew(new File("src/fems/vup/관제점_조흥인근.txt"));

		// c.analyze(new File("src/fems/vup/관제점_시화.txt"));
//		c.analyze(new File("src/fems/vup/관제점_안산.txt"));
//		c.analyze(new File("src/fems/vup/관제점_인천.txt"));
//		c.analyze(new File("src/fems/vup/관제점_조흥인근.txt"));

//		 c.checkPs(new File("src/fems/vup/관제점_시화.txt"));

//		c.showTagName(new File("src/fems/vup/관제점_시화.txt"));
	}

	private Map<String, PsItem> psMap;

	private final List<String[]> psList;
	final int PLANT_NM = 1;
	final int ITEM = 2;
	final int COMPLEX_ID = 3;
	final int PLANT_NO = 4;
	final int COMM_TYPE = 5;
	final int UNIT = 7;
	final int UNIT_DISP = 8;
	final int TAG = 9;
	final int TAG_NO = 10;
	final int GUBUN = 11;
	final int KEY = 12;
	final int LINE_NO = 14;
	final int ENG_NM = 20;
	final int P_NM = 21;

	final int CYCLE = 22;

	private final Map<String, String> tagMap;

	public 관제점분석() {
		this.psList = new ArrayList<String[]>();

		psList.add(new String[] { "스팀 공정운전스위치상태", "스팀", "운전", "스위치" });
		psList.add(new String[] { "스팀 밸브열림상태", "스팀", "밸브" });
		psList.add(new String[] { "스팀 압력계값", "스팀", "압력" });
		psList.add(new String[] { "스팀 유량계 적산값", "스팀", "적산", "유량" });
		psList.add(new String[] { "스팀 유량계 순시값", "스팀", "순시", "유량" });
		psList.add(new String[] { "스팀 유량계 순시값", "스팀", "유량" });

		psList.add(new String[] { "압공 공정운전스위치상태", "압공", "운전", "스위치" });
		psList.add(new String[] { "압공 밸브열림상태", "압공", "밸브" });
		psList.add(new String[] { "압공 압력계값", "압공", "압력" });
		psList.add(new String[] { "압공 유량계 적산값", "압공", "적산", "유량" });
		psList.add(new String[] { "압공 유량계 순시값", "압공", "순시", "유량" });
		psList.add(new String[] { "압공 유량계 순시값", "압공", "유량" });

		psList.add(new String[] { "압공 온도계 온도값", "압공", "온도" });

		psList.add(new String[] { "전력계 순시값", "순시", "전력" });
		psList.add(new String[] { "전력계 적산값", "적산", "전력" });

		psList.add(new String[] { "공압기 진동계 신호값", "진동", "신호" });
		psList.add(new String[] { "공압기 기동/정지 신호상태값", "기동", "신호" });
		psList.add(new String[] { "공압기 기동/정지 신호상태값", "정지", "신호" });
		psList.add(new String[] { "공압기 상태값", "공압기", "상태" });
		psList.add(new String[] { "공압기 상태값", "장비", "상태" });
		psList.add(new String[] { "공압기 경보발생여부값", "공압기", "경보" });
		psList.add(new String[] { "공압기 경보발생여부값", "장비", "경보" });
		psList.add(new String[] { "공압기 Remote/Local선택제어", "Remote/Local", "선택" });

		psList.add(new String[] { "드라이어 상태값1", "드라이어#1", "상태" });
		psList.add(new String[] { "드라이어 상태값2", "드라이어#2", "상태" });
		psList.add(new String[] { "드라이어 통신정보값", "드라이어", "통신" });

		psList.add(new String[] { "진동센서고장여부값", "진동", "고장" });

		try {
			psMap = PsApi.toNameMap(PsApi.getApi().getPsItemsIncludeNotUse());
		} catch (Exception e) {
			e.printStackTrace();
		}

		tagMap = initTag();
	}

	private void analyze(File file) {

		List<String> lines = FileUtil.getLines(file);
		Data dataNow = new Data();
		Data dataPrev = new Data();
		String ss[];
		Map<String, Data> dataMap = new HashMap<String, Data>();

		for (String line : lines) {
			ss = line.split("\t");

			if ("1".equals(ss[0]))
				continue;

			dataNow = makeData(dataPrev, ss);

			if (dataNow.pipeId.length() > 0) {
				Data data = dataMap.get(dataNow.deviceId);
				if (data != null) {
					data.psName += "," + dataNow.psName;
				} else {
					dataMap.put(dataNow.deviceId, dataNow);
				}
			}

			dataPrev = dataNow;

		}

		for (Data o : dataMap.values()) {
			if (o.deviceId.contains("/")) {
				Data newdata = (Data) o.clone();
				o.deviceId = o.deviceId.replaceFirst("A/B", "A");
				newdata.deviceId = newdata.deviceId.replaceFirst("A/B", "B");
				System.out.println(newdata.toExcel());
			}
		}
		for (Data o : dataMap.values()) {
			System.out.println(o.toExcel());
		}

	}

	private void analyzeNew(File file) {

		// 1. 사업장, 항목 기준으로 데이터를 분리한다.
		// 2. 관리대상(설비)를 추출한다.
		// 3. 사업장, 항목 기준으로 가상 관리대상을 추가한다.
		// 4. 관제점을 추출한다.
		// 5. 관제점의 구분의 첫글자가 같은 설비를 찾아 매핑한다. 해당 내용이 었으면 사업장, 항목 가상 관리대상에 매핑한다.

		Map<String, List<Fac>> facMap = new HashMap<String, List<Fac>>();
		List<Fac> entry = null;

		// 1.
		Map<String, List<Data>> map = getDatas(file);

		// 2, 3.
		for (List<Data> list : map.values()) {

			Data data0 = list.get(0);

			// 가상설비 추가
			entry = new ArrayList<Fac>();
			facMap.put(data0.getGroupKey(), entry);
			entry.add(new Fac("VIRTUAL", "가상", data0));

			for (Data data : list) {
				if ("설비".equals(data.gubun)) {
					entry = facMap.get(list.get(0).getGroupKey());
					entry.add(new Fac(data));
				} else if (data.deviceName.contains("순시 전력")) {
					entry = facMap.get(list.get(0).getGroupKey());
					entry.add(new Fac(data));
				}
			}

		}

		// 4, 5
		for (List<Data> list : map.values()) {

			AAA: for (Data data : list) {

				// 관제점에 관제 주기가 없으면 무시한다.
				if (data.cycle == null || data.cycle.trim().length() == 0) {
					continue;
				}

				if ("관제점".equals(data.gubun)) {

					entry = facMap.get(data.getGroupKey());
					if (entry == null) {
						System.err.println(FxmsUtil.toJson(data));
						continue AAA;
					}

					for (Fac fac : entry) {
						if (isMySensor(fac.tag, data.tag)) {
							fac.psIds.add(data);
							continue AAA;
						}
					}

					// 없으면 가상에 넣는다.
					entry.get(0).psIds.add(data);
				}
			}
		}

		List<String> keys = getKeys(facMap);
		for (String key : keys) {
			for (Fac fac : facMap.get(key)) {
//				if ("VIRTUAL".equals(fac.deviceType) == false || fac.psIds.size() > 0) {
				System.out.println(fac.toString());
//				}
			}
		}

	}

	private String checkEmpty(String prev, String... nows) {
		StringBuffer sb = new StringBuffer();
		for (String s : nows) {
			if (s.trim().length() > 0) {
				if (sb.length() > 0) {
					sb.append("-");
				}
				sb.append(s.trim());
			}
		}

		if (sb.length() == 0) {
			return prev;
		}
		return sb.toString();
	}

	private String checkEmpty2(String prev, String... nows) {
		StringBuffer sb = new StringBuffer();
		for (String s : nows) {
			if (s.trim().length() > 0 && s.trim().equals("-") == false) {
				sb.append(s.trim());
			}
		}

		if (sb.length() == 0) {
			return prev;
		}
		return sb.toString();
	}

	private void checkPs(File file) {

		List<String> lines = FileUtil.getLines(file);
		Data dataNow = new Data();
		Data dataPrev = new Data();
		String ss[];
		Map<String, List<String>> psMap = new HashMap<String, List<String>>();
		Map<String, PsData> psUnitMap = new HashMap<String, PsData>();
		Map<String, List<String>> typeMap = new HashMap<String, List<String>>();

		for (String line : lines) {
			ss = line.split("\t");

			if ("1".equals(ss[0]))
				continue;

			dataNow = makeData(dataPrev, ss);

			// 성능항목에 대한 디바이스 종류 분석용
			List<String> list = psMap.get(dataNow.psName);
			if (list == null) {
				list = new ArrayList<String>();
				psMap.put(dataNow.psName, list);
				psUnitMap.put(dataNow.psName, new PsData(dataNow.dUnit, dataNow.dUnitDescr));
			}
			if (list.contains(dataNow.deviceType) == false) {
				list.add(dataNow.deviceType);
			}

			list = typeMap.get(dataNow.deviceType);
			if (list == null) {
				list = new ArrayList<String>();
				typeMap.put(dataNow.deviceType, list);
			}
			if (list.contains(dataNow.psName) == false) {
				list.add(dataNow.psName);
			}

			dataPrev = dataNow;

		}

		List<String> keys = new ArrayList<String>(psMap.keySet());
		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		for (String o : keys) {
			System.out.println(o + "\t" + psMap.get(o) + "\t" + psUnitMap.get(o));
		}
		System.out.println("-----------------------------------------------------------------");

		keys = new ArrayList<String>(typeMap.keySet());
		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		for (String o : keys) {
			System.out.println(o + "\t" + typeMap.get(o));
		}

	}

	private Map<String, List<Data>> getDatas(File file) {

		Map<String, List<Data>> ret = new HashMap<String, List<Data>>();
		List<String> lines = FileUtil.getLines(file);
		List<Data> list;
		String ss[];
		Data dataNow = new Data();
		Data dataPrev = new Data();

		for (String line : lines) {
			ss = line.split("\t");

			if ("1".equals(ss[0]))
				continue;

			dataNow = makeData(dataPrev, ss);

			list = ret.get(dataNow.getGroupKey());
			if (list == null) {
				list = new ArrayList<Data>();
				ret.put(dataNow.getGroupKey(), list);
			}

			list.add(dataNow);

			dataPrev = dataNow;

		}
		return ret;

	}

	private String getDeviceType(String tag, String dNm, String engNm) {
		if (dNm == null) {
			String type = this.tagMap.get(tag);
			return type == null ? "" : engNm + " " + type;
		}

		String name = "";
		if (dNm.contains("전력")) {
			name = "전력계";
		} else if (dNm.contains("유량")) {
			return engNm.equals("압공") ? "압공유량계" : engNm.equals("스팀") ? "스팀유량계" : "유량계";
		} else if (dNm.contains("압력")) {
			return engNm.equals("압공") ? "압공압력계" : engNm.equals("스팀") ? "스팀압력계" : "압력계";
		} else if (dNm.contains("스위치")) {
			return engNm.equals("압공") ? "압공운전스위치" : engNm.equals("스팀") ? "스팀운전스위치" : "운전스위치";
		} else if (dNm.contains("진동")) {
			return engNm.equals("압공") ? "공압기 진동센서" : engNm.equals("스팀") ? "스팀진동센서" : "진동센서";
		} else if (dNm.contains("밸브")) {
			return engNm.equals("압공") ? "압공밸브" : engNm.equals("스팀") ? "스팀밸브" : "밸브";
		} else if (dNm.contains("온도")) {
			return engNm.equals("압공") ? "압공온도계" : engNm.equals("스팀") ? "스팀온도계" : "온도계";
		} else if (dNm.contains("Remote/Local 선택")) {
			name = "공압기 Remote/Local선택스위치";
		} else if (dNm.contains("기동 신호")) {
			name = "공압기 기동/정비 상태신호센서";
		} else if (dNm.contains("정지 신호")) {
			name = "공압기 기동/정비 상태신호센서";
		} else if (dNm.contains("상태")) {
			return (dNm.contains("공압기") || dNm.contains("장비")) ? "공압기 상태센서"
					: dNm.contains("드라이어") ? "드라이어 상태센서" : "통신상태센서";
		} else if (dNm.contains("통신")) {
			name = dNm.contains("드라이어") ? "드라이어 통신정보센서" : "통신상태센서";
		} else if (dNm.contains("경보")) {
			name = (dNm.contains("공압기") || dNm.contains("장비")) ? "공압기 경보신호센서" : "경보신호센서";
		} else {
			name = "";
		}

		return name;
	}

	private String getEngId(String s) {
		if (s == null)
			return "";
		return s.equals("압공") ? "E01" : s.equals("스팀") ? "E02" : "E03";
	}

	private List<String> getKeys(Map map) {
		List<String> keys = new ArrayList<String>(map.keySet());
		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		return keys;
	}

	private String getLineNum(String prev, String ss[]) {
		String name = ss[LINE_NO].trim();
		if (name.contains("예정") || name.contains("확인")) {
			return "";
		}

		return checkEmpty(prev, ss[LINE_NO], ss[LINE_NO + 1], ss[LINE_NO + 2], ss[LINE_NO + 3],
				"-".equals(ss[LINE_NO + 4].trim()) ? "" : ss[LINE_NO + 4]);
	}

	private String getPsName(Data data) {

		String name = data.engNm + data.deviceName;

		boolean bret = true;
		for (String ss[] : psList) {
			bret = true;
			for (int i = 1; i < ss.length; i++) {
				if (name.contains(ss[i]) == false) {
					bret = false;
					break;
				}
			}
			if (bret) {
				return ss[0];
			}
		}

		return "[" + data.deviceName + "]";
	}

	private boolean isPipe(String name) {
		String ss[] = name.split("-");
		return ss.length > 2;
	}

	private Data makeData(Data dataPrev, String ss[]) {
		Data dataNow = new Data();
		dataNow.plantName = checkEmpty(dataPrev.plantName, ss[PLANT_NM]);
		dataNow.plantId = checkEmpty2(dataPrev.plantId, ss[COMPLEX_ID], ss[PLANT_NO]);
		dataNow.commType = ss[COMM_TYPE].trim();
		dataNow.dUnit = ss[UNIT].trim();
		dataNow.dUnitDescr = ss[UNIT_DISP].trim();
		dataNow.engNm = checkEmpty(dataPrev.engNm, ss[ENG_NM]);
		dataNow.tag = ss[TAG].trim();

		if (ss[TAG_NO].trim().length() == 0) {
			dataNow.deviceId = dataPrev.deviceId;
		} else {
			dataNow.deviceId = checkEmpty(dataPrev.deviceId, dataNow.tag, ss[TAG_NO]);
		}

		dataNow.deviceName = getDeviceName(ss[P_NM], dataNow.tag, dataNow.engNm);
		dataNow.psName = checkEmpty(dataPrev.psName, ss[P_NM]);
		dataNow.gubun = checkEmpty(dataPrev.gubun, ss[GUBUN]);
		dataNow.key = checkEmpty(dataPrev.key, ss[KEY]);
		dataNow.item = checkEmpty(dataPrev.item, ss[ITEM]);

		dataNow.psName = getPsName(dataNow);
		dataNow.deviceType = getDeviceType(dataNow.tag, dataNow.deviceName, dataNow.engNm);

		dataNow.pipeId = this.getLineNum(dataPrev.pipeId, ss);

		dataNow.cycle = ss[CYCLE].trim();

		return dataNow;
	}

	private void showTagName(File file) {
		List<String> lines = FileUtil.getLines(file);
		String ss[];
		for (String line : lines) {
			ss = line.split("\t");
			if ("1".equals(ss[0]))
				continue;
			System.out.println(ss[TAG] + "\t" + ss[P_NM]);
		}
	}

	private Map<String, String> initTag() {
		Map<String, String> map = new HashMap<String, String>();

		map.put("FT", "유량계");
		map.put("FI", "유량계 순시값");
		map.put("FIA", "유량계 적산값");
		map.put("PT", "압력계");
		map.put("PI", "압력계값");
		map.put("HS", "공정 운전스위치");
		map.put("XV", "밸브");

		return map;
	}

	private String getDeviceName(String name, String tag, String engNm) {

		String ret = name.trim();
		if (ret.length() > 0) {
			return ret;
		}
		engNm = engNm.trim();

		ret = this.tagMap.get(tag);
		if (ret == null)
			return "미지정";
		return engNm + " " + ret;
	}

	private boolean isMySensor(String sensorTag, String pointTag) {

		boolean ret = false;

		if (sensorTag.startsWith("XV") && pointTag.startsWith("HS")) {
			ret = true;
		} else if (sensorTag.startsWith("FT") && pointTag.startsWith("FI")) {
			// FT - FI, FIA
			ret = true;
		} else if (sensorTag.startsWith("FT") && pointTag.startsWith("FQI")) {
			ret = true;
		} else if (sensorTag.startsWith("PT") && pointTag.startsWith("PI")) {
//			PT, PG
			ret = true;
		} else if (sensorTag.startsWith("TT") && pointTag.startsWith("TI")) {
			ret = true;
		} else if (sensorTag.startsWith("W") && pointTag.startsWith("W")) {
			ret = true;
		}

		// System.out.println("'" + sensorTag +"' '" + pointTag + "' = " + ret);

		return ret;
	}
}
