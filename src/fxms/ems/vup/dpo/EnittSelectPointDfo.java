package fxms.ems.vup.dpo;

import java.util.List;

import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;
import subkjh.dao.ClassDaoEx;

/**
 * 애니트의 관제점 데이터를 확인한다.
 * 
 * @author subkjh
 *
 */
public class EnittSelectPointDfo implements FxDfo<Void, List<VUP_COMM_ENITT_POINT>> {

	public static void main(String[] args) {
		EnittSelectPointDfo dfo = new EnittSelectPointDfo();
		try {
			System.out.println(FxmsUtil.toJson(dfo.select()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EnittSelectPointDfo() {
	}

	@Override
	public List<VUP_COMM_ENITT_POINT> call(FxFact fact, Void data) throws Exception {
		return select();
	}

	public List<VUP_COMM_ENITT_POINT> select() throws Exception {
		return ClassDaoEx.SelectDatas("VUPCOMMDB", VUP_COMM_ENITT_POINT.class, null);
	}
}
