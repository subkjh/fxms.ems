package test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.mo.MoApi;
import fxms.bas.vo.Inlo;
import fxms.ems.bas.dbo.FE_CF_INLO_CMPY;
import subkjh.bas.co.utils.FileUtil;
import subkjh.dao.ClassDaoEx;

public class 회사정보넣기 {

	File file = new File("datas/init/회사정보.txt");

	public static void main(String[] args) {
		회사정보넣기 c = new 회사정보넣기();
		try {
			c.set();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public 회사정보넣기() {

	}

	class Data {
		String 고유번호;
		String 산업단지명;
		String 업종;
		String 대표업종;
		String 업체명;
		String 대표자;
		String 사업자등록번호;
		String 업체주소;
		Inlo inlo;

		public Data(String s) {
			String ss[] = s.split("\t");

			if (ss.length != 8)
				return;

			고유번호 = ss[0].trim();
			산업단지명 = ss[1].trim();
			업종 = ss[2].trim();
			대표업종 = ss[3].trim();
			업체명 = ss[4].trim();
			대표자 = ss[5].trim();
			사업자등록번호 = ss[6].trim();
			업체주소 = ss[7].trim();
		}
	}

	void set() throws Exception {
		List<Data> datas = read();
		List<Inlo> inloList = MoApi.getApi().getInlos("COMPANY");
		Map<String, Inlo> map = new HashMap<>();
		List<Map<String, Object>> updates = new ArrayList<>();

		for (Inlo inlo : inloList) {
			map.put(inlo.getInloName(), inlo);
		}

		ClassDaoEx dao = ClassDaoEx.open();
		for (Data data : datas) {
			find(data, map);
			if (data.inlo != null) {
				Map<String, Object> d = FxApi.makePara("sectorClCds", data.업종, "repSectorClCd", data.대표업종, "ceoName",
						data.대표자, "bizRegNum", data.사업자등록번호, "addr", data.업체주소);

//				dao.updateOfClass(FX_CF_INLO.class, FxApi.makePara("inloNo", data.inlo.getInloNo()), d);
				dao.updateOfClass(FE_CF_INLO_CMPY.class, FxApi.makePara("inloNo", data.inlo.getInloNo()), d);
			}
		}
		dao.close();

		for (Map<String, Object> o : updates) {
			System.out.println(o);
		}
	}

	List<Data> read() {
		List<Data> list = new ArrayList<>();
		List<String> lines = FileUtil.getLines(file);
		for (String line : lines) {
			list.add(new Data(line));
		}
		return list;
	}

	void find(Data data, Map<String, Inlo> map) {
		data.inlo = map.get(data.업체명);
		if (data.inlo == null) {
			data.inlo = map.get(data.업체명.replaceAll("\\(주\\)", "㈜"));
			if (data.inlo == null) {
				data.inlo = map.get(data.업체명.replaceAll("\\(주\\)", ""));

			}
		}
	}

}
