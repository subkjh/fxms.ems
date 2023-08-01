package fxms.ems.vup.handler;

import java.util.HashMap;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.ems.bas.dbo.FE_ENG_CONS_AMT;
import fxms.ems.bas.dbo.FE_ENG_CONS_STAT;
import fxms.ems.bas.dbo.FE_ENG_PROD_AMT;
import fxms.ems.bas.dbo.FE_ENG_PROD_STAT;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dto.Tran04Dto;
import fxms.ems.vup.dto.Tran05Dto;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.ClassDaoEx;

public class VupDao {

	public static void main(String[] args) {

		Tran05Dto dto = new Tran05Dto();
		dto.date = "20230725";
		dto.factory_pid = "F010001";
		dto.energy_code = "E01";
		dto.date = "20230725";
		dto.time = "10;15";
		dto.expAmt = 1234.23f;

		VupDao dao = new VupDao();
		try {
			dao.updateTran05(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Boolean updateTran04(Tran04Dto dto) throws Exception {

		String date = DateUtil.checkDate(dto.date);
		long inloNo = VupApi.getApi().getPlant(dto.factory_pid).getInloNo();
		String engId = VupApi.getApi().getEngId(dto.energy_code);
		long facNo = -1L;
		try {
			facNo = VupApi.getApi().getFacNo(dto.device_pid);
		} catch (Exception e2) {
		}

		if (FxApi.isNotEmpty(dto.time)) {

			Map<String, Object> para = FxApi.makePara("prodDtm", Long.parseLong(date + DateUtil.getHHmmss(dto.time)) //
					, "dtmType", "M15" //
					, "inloNo", inloNo //
					, "engId", engId //
					, "facNo", facNo);//

			Map<String, Object> data = new HashMap<>(para);
			data.put("expProdAmt", dto.expAmt);

			ClassDaoEx.open().setOfClass(FE_ENG_PROD_AMT.class, para, data).close();

		} else {
			Map<String, Object> para = FxApi.makePara("prodDate", date //
					, "inloNo", inloNo //
					, "engId", engId //
					, "facNo", facNo);//

			Map<String, Object> data = new HashMap<>(para);
			data.put("expProdAmt", dto.expAmt);

			ClassDaoEx.open().setOfClass(FE_ENG_PROD_STAT.class, para, data).close();

		}

		return true;
	}

	public Boolean updateTran05(Tran05Dto dto) throws Exception {
		String date = DateUtil.checkDate(dto.date);
		long inloNo = VupApi.getApi().getPlant(dto.factory_pid).getInloNo();
		String engId = VupApi.getApi().getEngId(dto.energy_code);
		long facNo = -1L;
		try {
			facNo = VupApi.getApi().getFacNo(dto.device_pid);
		} catch (Exception e2) {
		}

		if (FxApi.isNotEmpty(dto.time)) {

			Map<String, Object> para = FxApi.makePara("consDtm", Long.parseLong(date + DateUtil.getHHmmss(dto.time)) //
					, "dtmType", "M15" //
					, "inloNo", inloNo //
					, "engId", engId //
					, "facNo", facNo);//

			Map<String, Object> data = new HashMap<>(para);
			data.put("expConsAmt", dto.expAmt);

			ClassDaoEx.open().setOfClass(FE_ENG_CONS_AMT.class, para, data).close();

		} else {
			Map<String, Object> para = FxApi.makePara("consDate", date //
					, "inloNo", inloNo //
					, "engId", engId //
					, "facNo", facNo);//

			Map<String, Object> data = new HashMap<>(para);
			data.put("expConsAmt", dto.expAmt);

			ClassDaoEx.open().setOfClass(FE_ENG_CONS_STAT.class, para, data).close();

		}

		return true;
	}
}
