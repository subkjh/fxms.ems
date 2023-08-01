package fxms.ems.bas.dpo;

import java.util.ArrayList;
import java.util.List;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_BAS;
import fxms.ems.bas.vo.EngPsVo;
import fxms.ems.bas.vo.EngPsVo.PsType;
import subkjh.dao.ClassDaoEx;

/**
 * 에너지에 설정된 성능항목을 조회하는 함수
 * 
 * @author subkjh
 *
 */
public class SelectEnergyPsIdDfo implements FxDfo<Void, List<EngPsVo>> {

	public static void main(String[] args) {

		SelectEnergyPsIdDfo dfo = new SelectEnergyPsIdDfo();
		try {
			System.out.println(FxmsUtil.toJson(dfo.selectEnergyPsId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<EngPsVo> call(FxFact fact, Void data) throws Exception {
		return selectEnergyPsId();
	}

	/**
	 * 에너지 정보 확인 성능항목 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<EngPsVo> selectEnergyPsId() throws Exception {
		List<FE_ENG_BAS> list = ClassDaoEx.SelectDatas(FE_ENG_BAS.class, FxApi.makePara("useYn", "Y"));

		List<EngPsVo> ret = new ArrayList<>();

		for (FE_ENG_BAS eng : list) {
			if (FxApi.isNotEmpty(eng.getInstPsId())) {
				ret.add(new EngPsVo(eng.getEngId(), eng.getInstPsId(), PsType.instance));
			}
			if (FxApi.isNotEmpty(eng.getIntgPsId())) {
				ret.add(new EngPsVo(eng.getEngId(), eng.getIntgPsId(), PsType.accumulate));
			}
			if (FxApi.isNotEmpty(eng.getPresPsId())) {
				ret.add(new EngPsVo(eng.getEngId(), eng.getPresPsId(), PsType.pressure));
			}
			if (FxApi.isNotEmpty(eng.getTempPsId())) {
				ret.add(new EngPsVo(eng.getEngId(), eng.getTempPsId(), PsType.temperature));
			}
			if (FxApi.isNotEmpty(eng.getUsedPsId())) {
				ret.add(new EngPsVo(eng.getEngId(), eng.getUsedPsId(), PsType.used));
			}
		}

		return ret;

	}

}