package fxms.ems.cems.dfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.vo.ValueApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.cems.dao.dfo.CheckStateSensorDfoQid;
import fxms.ems.cems.dto.EpwrColDataDto;
import fxms.ems.cems.dto.EpwrColMoDataDto;
import subkjh.dao.QidDaoEx;

/**
 * <<< 전력량계 상태 정의>>>
 * 
 * (가동 중(1)/정지(2)/데이터 미수신(3)/대기(4))<br>
 * . 가동 중 : 유효전력량Diff > 0<br>
 * . 중지 : 유효전력량Diff = 0 && 전압3상의 합 > 0<br>
 * . 대기 : 설비에 전력량계만 있을 경우와(다른 계량기가 반드시 없어야함), 동시에 가동 중 상태에 만족하는 조건에 들어갈 경우, 미리
 * 입력된 대기전력시작값, 대기전력종료값 사이에 유효전력량Diff 값이 위치한 경우 대기 상태로 간주.<br>
 * . 데이터 미수신 : 그 외<br>
 * 
 * @author subkjh
 *
 */

public class CheckStatePowerMeterDfo implements FxDfo<Void, Integer> {

	public static void main(String[] args) {

		CheckStatePowerMeterDfo dfo = new CheckStatePowerMeterDfo();
		try {
			dfo.call(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer call(FxFact fact, Void empty) throws Exception {

		// 계측기 데이터 수집데이터 주기를 확인하여 최근 n개의 데이터를 가져와 상태를 확인한다.
		int minute = FxCfg.getCfg().getInt("data.polling.cycle", 1);
		int dataSize = 5; // 각 계측기당 5개

		CheckStateSensorDfoQid QID = new CheckStateSensorDfoQid();

		// 1. 계측기와 설비 목록 조회
		Map<Long, EpwrColMoDataDto> moMap = new HashMap<>();
		List<EpwrColMoDataDto> mos = (List<EpwrColMoDataDto>) QidDaoEx
				.SelectDatas(CheckStateSensorDfoQid.QUERY_XML_FILE, QID.select_epower_collected_mo, null);
		for (EpwrColMoDataDto mo : mos) {
			moMap.put(mo.moNo, mo);
		}

		// 2. 계측기 수집값 조회
		List<EpwrColDataDto> datas = (List<EpwrColDataDto>) QidDaoEx.SelectDatas(CheckStateSensorDfoQid.QUERY_XML_FILE,
				QID.select_epower_collected_data, FxApi.makePara("minute", minute * dataSize));

		EpwrColMoDataDto dto;
		for (EpwrColDataDto data : datas) {
			dto = moMap.get(data.moNo);
			if (dto != null) {
				if (dto.lastData == null) {
					dto.lastData = data;
				} else if (dto.prevData == null) {
					dto.prevData = data;
				}
			}
		}

		// 3. 계측기 상태 추출
		PsVoRawList values = new PsVoRawList("state", System.currentTimeMillis());
		for (EpwrColMoDataDto mo : mos) {
			values.add(mo.moNo, "state", mo.getState());
		}
		ValueApi.getApi().addValues(values, true);

		return values.size();
	}

}
