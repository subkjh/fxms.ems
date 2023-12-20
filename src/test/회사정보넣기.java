package test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.mo.MoApi;
import fxms.bas.impl.handler.dto.inlo.InloAddDto;
import fxms.bas.vo.Inlo;
import fxms.ems.bas.dbo.FE_CF_INLO_CMPY;
import fxms.ems.bas.dbo.FE_EPWR_INLO;
import fxms.rule.action.AddDataAction;
import subkjh.bas.co.utils.FileUtil;
import subkjh.dao.ClassDaoEx;

public class 회사정보넣기 {



	public static void main(String[] args) {
		회사정보넣기 c = new 회사정보넣기();
		try {
//			c.set();
			c.add("대전");
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
		String 담당자명;
		String 담당자연락처;
		String 담당자이메일;
		String 파워플래너고객번호;
		String 파워플래너ID;
		String 파워플래너암호;
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
			담당자명 = ss[8].trim();
			담당자연락처 = ss[9].trim();
			담당자이메일 = ss[10].trim();
			파워플래너고객번호 = ss[11].trim();
			파워플래너ID = ss[12].trim();
			파워플래너암호 = ss[13].trim();
		}
	}

	void set() throws Exception {
		List<Data> datas = read(new File("datas/init/회사정보.txt"));
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

	List<Data> read(File file) {
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

	void add(String complexName) throws Exception {
		List<Data> datas = read(new File("datas/init/대전업체_테스트용.txt"));
		InloAddDto dto;

		Inlo complex = MoApi.getApi().getInlo(complexName, "COMPLEX");
		if (complex == null) {
			throw new Exception("산단 정보 없음");
		}

		for (Data data : datas) {
			dto = new InloAddDto();

			// 회사
			dto.inloClCd = "COMPANY";
			dto.inloName = data.업체명;
			dto.inloDesc = data.업체명;
			dto.upperInloNo = complex.getInloNo();

			Map<String, Object> map = new HashMap<>();
			map.put("addr", dto);
			map.put("cntcrName", data.담당자명);
			map.put("cntcrEmail", data.담당자이메일);
			map.put("telNum", data.담당자연락처);

			Inlo company = MoApi.getApi().addInlo(1, map);

			dto.inloClCd = "PLANT";
			dto.inloName = "사업장";
			dto.inloDesc = data.업체명;
			dto.upperInloNo = company.getInloNo();

			map = new HashMap<>();
			map.put("addr", dto);
			map.put("cntcrName", data.담당자명);
			map.put("cntcrEmail", data.담당자이메일);
			map.put("telNum", data.담당자연락처);

			Inlo plant = MoApi.getApi().addInlo(1, map);

			Map<String, Object> feEpwrInlo = new HashMap<>();
			feEpwrInlo.put("inloNo", plant.getInloNo());
			feEpwrInlo.put("kepcoPpId", data.파워플래너ID);
			feEpwrInlo.put("kepcoPpPwd", data.파워플래너암호);

			ClassDaoEx.InsertOfClass(FE_EPWR_INLO.class, feEpwrInlo);

		}
	}

//	고유번호	산업단지명	업종	대표업종번호	업체명	대표자	사업자등록번호	법인등록번호	업체주소	사업장 담당자 이름(직급)	사업장 담당자 연락처	사업장 담당자 이메일	한전 파원플래너 ID & PW			비고
//	
//	고객번호	ID	PW	
//				
//(예시) daejeon-23-01	대전	윤활유 및 그리스 제조업	19221	범우화학공업	이철형	111-11-11111	111111-1111111	경기 시흥시 옥구천서로131번길 41	김정일 과장	010-1111-1111 (or 051-1111-111)	xxxx@samsung.com				업로드 파일명 : 산업단지명_사업장 기본 정보_YYYYMMDD.xls 
//daejeon-23-01	대전	"폴리실라잔
//석유화확 제조업"	242903	㈜디엔에프	김명운	314-81-38516	160111-0109896	대전광역시 대덕구 대화로132번길 142 	부장 김범민	010-6289-6553	bmkim@dnfsolution.com	0631474802	0631474802	7639274	
//daejeon-23-02	대전	"자동차 및 산업기계
//비금속 제조업"		㈜대창열처리	김승준			대전광역시 대덕구 대전로131번길 135 	대표 김승준	010-2545-9523	juni9523@naver.com		dcht6619	dcdc!!6619	
//daejeon-23-03	대전	"직물 및 면사염색
//섬유 의복 제조업"		한백섬유㈜	강원구			대전광역시 대덕구 대전로1331번길 310 	대표 강원구	010-9930-1623	noja0505@hanmail.net	0632281386	0632281386	h*20170420	
//daejeon-23-04	대전	"믹스커피
//음식료 제조업"		㈜모카씨엔티	송은석			대전광역시 대덕구 대전로1291번길 133 	개선팀장 김기훈	"
//010-3427-1158"	"
//anycafe711@naver.com"				
//daejeon-23-05	대전	"면사, 타올염색
//섬유 의복 제조업"		㈜삼두염색	강동원			대전광역시 대덕구 대화로32번길 165 	차장 이지주	010-5454-1185	electeng@hanmail.net	0622143090	0622143090	samd7177**	

}
