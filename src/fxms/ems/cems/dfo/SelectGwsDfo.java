package fxms.ems.cems.dfo;

import java.util.List;
import java.util.Map;

import fxms.api.FxApi;
import fxms.api.mo.MoApi;
import fxms.api.mo.MoApiDfo;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dbo.all.FX_MO_NODE;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.bas.mo.NodeMo;
import fxms.ems.cems.dao.CemsQid;
import fxms.ems.cems.dto.GwDto;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.QidDaoEx;

public class SelectGwsDfo implements FxDfo<Void, List<GwDto>> {

	public static void main(String[] args) {
		MoApi.api = new MoApiDfo();

		SelectGwsDfo dfo = new SelectGwsDfo();
		try {
			System.out.println(FxmsUtil.toJson(dfo.select()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<GwDto> call(FxFact arg0, Void arg1) throws Exception {
		return null;
	}

	public List<GwDto> select() throws Exception {

		Map<String, Object> para = FxApi.makePara("moClass", NodeMo.MO_CLASS, "delYn", "N", "commProtc", "OPC-UA");
		return ClassDaoEx.SelectDatas(FX_MO_NODE.class, para, GwDto.class);

	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> select(String complexTid) throws Exception {

		CemsQid QID = new CemsQid();
		return (List<Map<String, Object>>) QidDaoEx.SelectDatas(CemsQid.QUERY_XML_FILE, QID.select_GW__BY_COMPLEX,
				FxApi.makePara("inloTid", complexTid));

	}
}
