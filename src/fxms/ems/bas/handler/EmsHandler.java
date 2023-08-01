package fxms.ems.bas.handler;

import fxms.bas.fxo.FxCfg;
import fxms.bas.handler.BaseHandler;
import fxms.bas.handler.MethodDescr;
import fxms.bas.handler.vo.SessionVo;
import fxms.ems.bas.dpo.AddValueDfo;
import fxms.ems.bas.dto.PointValueDto;
import fxms.ems.bas.dto.PointValuesDto;
import subkjh.dao.QidDao;
import subkjh.dao.database.DBManager;

/**
 * FxMS 에너지 전용 핸들러
 * 
 * @author subkjh
 *
 */
public class EmsHandler extends BaseHandler {

	@Override
	protected QidDao getQidDao() throws Exception {
		return DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createQidDao();
	}

	@MethodDescr(name = "수집값 등록", description = "외부에서 수집 데이터를 등록한다.")
	public Object addValue(SessionVo session, PointValueDto data) throws Exception {
		return new AddValueDfo().addValue(data);
	}

	@MethodDescr(name = "수집값 목록을 등록", description = "외부에서 수집 데이터를 등록한다.")
	public Object addValues(SessionVo session, PointValuesDto datas) throws Exception {
		return new AddValueDfo().addValues(datas);
	}

}
