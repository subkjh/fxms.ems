package fxms.ems.vup.dpo;

import java.util.ArrayList;
import java.util.List;

import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;

/**
 * 애니트의 관제점 데이터를 확인한다.
 * 
 * @author subkjh
 *
 */
public class EnittPrintCheckedPointDfo implements FxDfo<List<VUP_COMM_ENITT_POINT>, Boolean> {

	public EnittPrintCheckedPointDfo() {
	}

	@Override
	public Boolean call(FxFact fact, List<VUP_COMM_ENITT_POINT> data) throws Exception {
		return print(data);
	}

	public boolean print(List<VUP_COMM_ENITT_POINT> points) throws Exception {

		List<String> okStrs = new ArrayList<>();
		List<String> erStrs = new ArrayList<>();

		for (VUP_COMM_ENITT_POINT point : points) {

			StringBuffer sb = new StringBuffer();
			sb.append(point.getPointClass()).append(", ").append(point.getDevicePid()).append(", ")
					.append(point.getPointPid()).append(" / ").append(point.getPointDesc()).append(" \t\t:: ");

			if (point.getMoNo() != null) {
				sb.append(" mo = ['").append(point.getMoNo()).append("' '").append(point.getMoName()).append("']");
			} else {
				sb.append(" mo = 등록안됨");
			}

			if (point.getPsId() != null) {
				sb.append(" point = ['").append(point.getPsId()).append("']");
			} else {
				sb.append(" point = 패턴없음 ");
			}

			if (point.getMoNo() != null && point.getPsId() != null) {
				okStrs.add(sb.toString());
			} else {
				erStrs.add(sb.toString());
			}

		}

		System.out.println("\n\n");
		System.out.println("--------------------------------------------------------------------------");

		for (String s : okStrs) {
			System.out.println(" ok " + s);
		}
		System.out.println("--------------------------------------------------------------------------");
		for (String s : erStrs) {
			System.out.println(" er " + s);
		}

		return true;

	}
}
