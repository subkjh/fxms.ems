package fxms.ems.bas.dpo;

import fxms.bas.impl.dpo.FxDpo;
import fxms.bas.impl.dpo.FxFact;

public class MakeOperHstDpo implements FxDpo<Void, Boolean> {

	@Override
	public Boolean run(FxFact fact, Void data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void make() {
		// 입력된 일자의 요일을 구한다.
		// 같은 요일의 운영 계획을 가져온다.
		// 입력된 일자루 운영 이력을 기록한다.
	}

}
