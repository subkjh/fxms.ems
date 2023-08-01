package fxms.ems.bas.dpo;

import fxms.bas.api.ValueApi;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.impl.dpo.co.ToCheckDfo;
import fxms.bas.impl.dpo.ps.PsPointMap;
import fxms.bas.vo.PsPoint;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.bas.dto.PointValueDto;
import fxms.ems.bas.dto.PointValuesDto;
import fxms.ems.bas.exp.PointNotFoundException;
import subkjh.bas.co.utils.DateUtil;

/**
 * 에너지 원천 데이터를 이용하여 소비, 생산량을 계산한다.
 * 
 * @author subkjh
 *
 */
public class AddValueDfo implements FxDfo<PointValueDto, Integer> {

	@Override
	public Integer call(FxFact fact, PointValueDto data) throws Exception {
		addValue(data);
		return null;
	}

	public int addValue(PointValueDto data) throws Exception {

		PsPoint point = PsPointMap.getMap().getPsPoint(data.getPointId());

		if (point == null) {
			new ToCheckDfo().toCheck("관제점", data.getPointId(), "관제점 등록 안됨");
			throw new PointNotFoundException(data.getPointId());
		}

		PsVoRawList voList = new PsVoRawList("ui", DateUtil.toMstime(data.getDate()));

		if (voList.add(point.getMoNo(), point.getPsId(), data.getValue()) == null) {
			throw new Exception("parameters is not enough : " + FxmsUtil.toJson(data));
		}

		ValueApi.getApi().addValue(voList, true);

		return 1;

	}

	public int addValues(PointValuesDto datas) throws Exception {

		for (PointValueDto data : datas.getDatas()) {
			addValue(data);
		}

		return datas.getDatas().size();

	}

}
