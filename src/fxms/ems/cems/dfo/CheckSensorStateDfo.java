package fxms.ems.cems.dfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.vo.ValueApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsVoRawList;
import fxms.ems.cems.dao.dfo.CheckSensorStateDfoQid;
import fxms.ems.cems.dto.EpwrColDataDto;
import fxms.ems.cems.dto.EpwrColMoDataDto;
import subkjh.bas.co.log.Logger;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDaoEx;

/**
 * <<< 전력량계 상태 정의>>>
 * 
 * 가동중(1) : 유효전력량 DIFF > 0 <br>
 * 가동중지(2) : 유효전력량 DIFF = 0 and 전압 3상의 합 > 0 <br>
 * 데이터미수신(3) : 가동중, 가동중지 상태외 상태, 가동중지를 5번 반복시 데이터 미수신으로 판단 <br>
 * 
 * @author subkjh
 *
 */

public class CheckSensorStateDfo implements FxDfo<Void, Void> {

	public static void main(String[] args) {

		CheckSensorStateDfo dfo = new CheckSensorStateDfo();
		try {
			dfo.checkSensorState();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Void call(FxFact fact, Void yyyymm) throws Exception {
		checkSensorState();
		return null;
	}

	@SuppressWarnings("unchecked")
	public int checkSensorState() throws Exception {

		// 계측기 데이터 수집데이터 주기를 확인하여 최근 n개의 데이터를 가져와 상태를 확인한다.
		int minute = FxCfg.getCfg().getInt("data.polling.cycle", 1);
		int dataSize = 5; // 각 계측기당 5개

		CheckSensorStateDfoQid QID = new CheckSensorStateDfoQid();

		// 1. 계측기와 설비 목록 조회
		Map<Long, EpwrColMoDataDto> moMap = new HashMap<>();
		List<EpwrColMoDataDto> mos = (List<EpwrColMoDataDto>) QidDaoEx
				.SelectDatas(CheckSensorStateDfoQid.QUERY_XML_FILE, QID.select_epower_collected_mo, null);
		for (EpwrColMoDataDto mo : mos) {
			moMap.put(mo.moNo, mo);
		}

		// 2. 계측기 수집값 조회
		List<EpwrColDataDto> datas = (List<EpwrColDataDto>) QidDaoEx.SelectDatas(CheckSensorStateDfoQid.QUERY_XML_FILE,
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
