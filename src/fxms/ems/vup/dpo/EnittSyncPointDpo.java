package fxms.ems.vup.dpo;

import java.util.List;

import fxms.bas.api.MoApi;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.dpo.FxDpo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;

/**
 * 애니트 관제점을 플랫폼에 적용하기
 * 
 * * C:\Thingspire\02. 사내프로젝트\2201. VUP(과제)\1. 제공자료\애니트 폴더에서<br>
 * 20230411_에니트_시화도금단지_정리본(전체).xlsx 파일의 데이터를 <br>
 * VUP_COMM_ENITT_POINT 테이블에 기록한다.<br>
 * 
 * @author subkjh
 *
 */
public class EnittSyncPointDpo implements FxDpo<Void, List<VUP_COMM_ENITT_POINT>> {

	public static void main(String[] args) {
		MoApi.api = new MoApiDfo();

		EnittSyncPointDpo dpo = new EnittSyncPointDpo();
		try {
			dpo.sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<VUP_COMM_ENITT_POINT> run(FxFact fact, Void data) throws Exception {

		List<VUP_COMM_ENITT_POINT> points = sync();

		return points;
	}

	public List<VUP_COMM_ENITT_POINT> sync() throws Exception {

		List<VUP_COMM_ENITT_POINT> points = new EnittSelectPointDfo().select();

		sync(points);

		return points;
	}

	public boolean sync(List<VUP_COMM_ENITT_POINT> points) throws Exception {

		new EnittCheckPointDfo().check(points); // MO와 성능을 찾아 설정한다.

		new EnittUpdatePointDfo().update(points); // 설정된 내용을 수정한다.

		new EnittSetMoDfo().setMo(points); // 관리대상을 설정한다.

		new EnittPrintCheckedPointDfo().print(points); // 처리 내용을 출력한다.

		return true;
	}

}
