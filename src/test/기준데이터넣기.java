package test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.mo.NodeMo;
import fxms.bas.vo.Inlo;
import fxms.ems.bas.mo.SensorMo;
import subkjh.bas.co.utils.FileUtil;

public class 기준데이터넣기 {

	class Data {
		String site;
		String gw;
		String name;
		String namespace_index;
		int node_id;

		Inlo inlo;
		NodeMo node;
		SensorMo sensor;

		public String toString() {
			return site + "," + gw + "," + name + "," + inlo + "," + sensor + "," + node;
		}
	}

	class Data2 {
		int PLANT_NO;
		String PLANT_TID;
		String PLANT_NAME;
		String COMPANY_NAME;
		String COMPANY_GW_IP;
		String COMPLEX_NAME;
		NodeMo node;
		Inlo inlo;

		public String toString() {
			return PLANT_NAME + "," + COMPANY_GW_IP + "," + node + "," + inlo;
		}
	}

	public static void main(String[] args) {

//		Logger.logger.setLevel(LOG_LEVEL.info);

		MoApi.api = new MoApiDfo();

		기준데이터넣기 c = new 기준데이터넣기();
		try {
			c.makeMo();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Map<String, Inlo> inloMap = new HashMap<>();
	private Map<String, NodeMo> nodeMap = new HashMap<>();
	private Map<String, SensorMo> sensorMap = new HashMap<>();

	void loadDatas() throws Exception {

		List<Inlo> inloList = MoApi.getApi().getInlos("PLANT");
		for (Inlo inlo : inloList) {
			this.inloMap.put(inlo.getInloTid(), inlo);
			this.inloMap.put(inlo.getInloAllName(), inlo);
		}

		List<NodeMo> nodeList = MoApi.getApi().getMos(FxApi.makePara("moClass", "NODE"), NodeMo.class);
		for (NodeMo node : nodeList) {
			this.nodeMap.put(node.getMoTid(), node);
		}

		List<SensorMo> sensorList = MoApi.getApi().getMos(FxApi.makePara("moClass", "SENSOR"), SensorMo.class);
		for (SensorMo node : sensorList) {
			this.sensorMap.put(node.getMoTid(), node);
		}

		System.out.println(this.inloMap.size() + "," + this.nodeMap.size() + "," + this.sensorMap.size());

	}

	List<NodeMo> makeGw(List<Data> datas) {

		Map<String, NodeMo> gwMap = new HashMap<>();
		NodeMo node;

		for (Data data : datas) {
			node = gwMap.get(data.gw);
			if (node == null) {
//				node = MoApi.makeMo(null, null)
			}
		}

		return null;

	}

	void syncGw(Map<String, Data2> gwMap) throws Exception {
		// Gateway 노드 추가
		for (Data2 gw : gwMap.values()) {
			if (gw.node == null) {
				NodeMo newNode = new NodeMo(gw.COMPANY_NAME + " GW", "GW", gw.COMPANY_GW_IP);
				if (gw.inlo != null) {
					newNode.setInloNo(gw.inlo.getInloNo());
				}
				newNode.setMoTid(gw.COMPANY_GW_IP);
				gw.node = (NodeMo) MoApi.getApi().addMo(0, NodeMo.MO_CLASS, FxmsUtil.toMap(newNode), "테스트용", false);
			} else {

				if (gw.inlo != null) {
					gw.node.setInloNo(gw.inlo.getInloNo());
					gw.node.setInloMemo(gw.inlo.getInloAllName());
				}

				Map<String, Object> node = FxmsUtil.toMap(gw.node);
				node.put("pollCycle", 60);

				System.out.println(node);

				MoApi.getApi().updateMo(0, gw.node.getMoNo(), node);
			}
		}
	}

	void syncSensor(List<Data> nodes) throws Exception {
		// Gateway 노드 추가
		SensorMo sensor;
		Map<String, Object> para;

		for (Data data : nodes) {

			if (data.sensor == null) {
				sensor = new SensorMo(data.name, "전력계");
			} else {
				sensor = data.sensor;
			}

			NodeMo node = this.nodeMap.get(data.gw);
			if (node != null) {
				sensor.setInloNo(node.getInloNo());
				sensor.setPlcMoNo(node.getMoNo());
				sensor.setMoTid(data.name);

				para = FxmsUtil.toMap(sensor);
				para.put("namespaceIndex", data.namespace_index);
				para.put("nodeId", data.node_id);

				// 불필요한 내용삭제
				para.remove("no");
				para.remove("eventType");
				para.remove("status");

				if (sensor.getMoNo() > 0) {
					System.out.println(para);
					MoApi.getApi().updateMo(0, sensor.getMoNo(), para);
				} else {
					MoApi.getApi().addMo(0, SensorMo.MO_CLASS, para, "테스트용", false);
				}

				continue;

			}
			System.err.println("error : " + data);
		}
	}

	void makeMo() throws Exception {

		loadDatas();

		List<String> lines = FileUtil.getLines(new File("datas/init/node_list.txt"));
		List<Data> nodes = parse(lines);
		syncSensor(nodes);

//		lines = FileUtil.getLines(new File("datas/init/node_list2.txt"));
//		Map<String, Data2> gwMap = parse2(lines);
//		syncGw(gwMap);

	}

	List<Data> parse(List<String> lines) {

		String ss[];
		Data data;
		List<Data> list = new ArrayList<>();

		for (String line : lines) {

			ss = line.split(",");

			if (ss[0].equals("endpoint"))
				continue;

			data = new Data();
			data.gw = ss[0];
			data.site = ss[1].split("\\.")[0];
			data.name = ss[1];
			data.namespace_index = ss[2];
			data.node_id = Integer.parseInt(ss[3]);

			data.node = this.nodeMap.get(data.gw);
			data.inlo = this.inloMap.get(data.site);
			data.sensor = this.sensorMap.get(data.name);

			list.add(data);

//			System.out.println(data);
		}

		return list;

	}

	Map<String, Data2> parse2(List<String> lines) {

		String ss[];
		Data2 data;
		Map<String, Data2> map = new HashMap<>();

		for (String line : lines) {
			ss = line.split("\t");
			data = new Data2();
			try {
				data.PLANT_NO = Integer.parseInt(ss[0]);
			} catch (Exception e) {
				continue;
			}
			data.PLANT_TID = ss[1];
			data.PLANT_NAME = ss[1];
			data.COMPANY_NAME = ss[4];
			data.COMPANY_GW_IP = ss[5];
			data.COMPLEX_NAME = ss[7];
			data.inlo = findInlo(data);

			if (data.COMPANY_GW_IP.trim().length() > 0) {
				map.put(data.COMPANY_GW_IP, data);
				data.node = this.nodeMap.get(data.COMPANY_GW_IP);
			}

			System.out.println(data);

		}

		return map;

	}

	private Inlo findInlo(Data2 data) {
		String key = data.COMPLEX_NAME + " > " + data.COMPANY_NAME + " > 사업장";
		return this.inloMap.get(key);
	}

}
