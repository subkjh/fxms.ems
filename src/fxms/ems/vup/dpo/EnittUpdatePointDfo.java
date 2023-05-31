package fxms.ems.vup.dpo;

import java.util.List;
import java.util.Map;

import fxms.bas.api.MappingApi;
import fxms.bas.api.PsApi;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.vo.PsItem;
import fxms.bas.vo.mapp.MappData;
import fxms.ems.vup.api.VupApi;
import fxms.ems.vup.dbo.all.VUP_COMM_ENITT_POINT;
import subkjh.bas.co.log.Logger;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;

/**
 * 애니트의 관제점 데이터를 확인한다.
 * 
 * @author subkjh
 *
 */
public class EnittUpdatePointDfo implements FxDfo<List<VUP_COMM_ENITT_POINT>, Boolean> {

	public EnittUpdatePointDfo() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean call(FxFact fact, List<VUP_COMM_ENITT_POINT> data) throws Exception {
		return update(data);
	}

	public boolean update(List<VUP_COMM_ENITT_POINT> points) throws Exception {

		Map<String, PsItem> psMap = PsApi.toIdMap(PsApi.getApi().getPsItemsIncludeNotUse());
		ClassDao tran = DBManager.getMgr().getDataBase("VUPCOMMDB").createClassDao();
		boolean updated;
		try {
			tran.start();

			for (VUP_COMM_ENITT_POINT point : points) {

				tran.updateOfClass(VUP_COMM_ENITT_POINT.class, point); // 내용 수정

				// 관제점 & MO:PSID 매핑
				MappData mappData = VupApi.getApi().makeMappPs(point.getPointPid(), point.getPointDesc());
				
if ( point.getPointPid().equals("F010001-HS1201")) {
	System.out.println("\n\n\n\n");
	System.out.println(point.getPsId());
	System.out.println(psMap.get(point.getPsId()));
	System.out.println("\n\n\n\n");
}
				updated = false;
				if (point.getMoNo() != null) {
					PsItem psItem = psMap.get(point.getPsId());
					if (psItem != null) {
						MappingApi.getApi().setMappPs(0, mappData, point.getMoNo(), point.getMoName(), psItem.getPsId(),
								psItem.getPsName());
						updated = true;
					}
				}

				// 매핑 데이터 없으면 기존 데이터 삭제
				if (updated == false) {
					MappingApi.getApi().removeMappPs(mappData);
				}

			}
			tran.commit();
			return true;
		} catch (Exception e) {
			Logger.logger.error(e);
			tran.rollback();
			throw e;
		} finally {
			tran.stop();
		}
	}
}
