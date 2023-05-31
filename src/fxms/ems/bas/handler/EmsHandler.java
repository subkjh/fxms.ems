package fxms.ems.bas.handler;

import fxms.bas.api.MappingApi;
import fxms.bas.api.ValueApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.handler.BaseHandler;
import fxms.bas.handler.MethodDescr;
import fxms.bas.handler.vo.SessionVo;
import fxms.bas.impl.dpo.co.ToCheckDfo;
import fxms.bas.vo.PsVoRawList;
import fxms.bas.vo.mapp.MappMoPs;
import fxms.ems.bas.dto.PointValueDto;
import fxms.ems.bas.dto.PointValuesDto;
import fxms.ems.bas.exp.PointNotFoundException;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

public class EmsHandler extends BaseHandler {

	@Override
	protected QidDao getQidDao() throws Exception {
		return DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createQidDao();
	}

	@MethodDescr(name = "수집값 등록", description = "외부에서 수집 데이터를 등록한다.")
	public Object addValue(SessionVo session, PointValueDto data) throws Exception {

		MappMoPs mo = MappingApi.getApi().getMappMoPs("fxms.ems", data.getPointPid());
		if (mo == null) {
			new ToCheckDfo().toCheck("관제점", data.getPointPid(), "관제점 등록 안됨");
			throw new PointNotFoundException(data.getPointPid());
		}

		PsVoRawList voList = new PsVoRawList("ui", DateUtil.toMstime(data.getDate()));

		if (voList.add(mo.getMoNo(), mo.getPsId(), data.getValue()) == null) {
			throw new Exception("parameters is not enough : " + FxmsUtil.toJson(data));
		}

		ValueApi.getApi().addValue(voList, true);

		return data;
	}

}
