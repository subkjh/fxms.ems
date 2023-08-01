package fxms.ems.vup.dpo;

import fxms.bas.api.FxApi;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.dpo.FxDfo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_TRANS_RT;
import fxms.ems.vup.dto.Tran06Dto;
import subkjh.bas.co.utils.DateUtil;
import subkjh.bas.co.utils.ObjectUtil;
import subkjh.dao.ClassDaoEx;
import subkjh.dao.util.FxTableMaker;

/**
 * 에너지 거래 정보를 가져온다.
 * 
 * @author subkjh
 *
 */
public class EngTransRtInsertDfo implements FxDfo<Tran06Dto, Boolean> {

	public static void main(String[] args) {
		Tran06Dto dto = new Tran06Dto();
		EngTransRtInsertDfo dfo = new EngTransRtInsertDfo();

		dto.setEngRtUseYn("Y");
		dto.setRtMemo("TEST");

		String line = "1	E01-F010001-F010002\r\n" + "12	E01-F010001-F010002\r\n" + "42	E01-F010001-F010002\r\n"
				+ "62	E01-F010001-F010002\r\n" + "84	E01-F010001-F010002\r\n" + "2	E01-F010001-F010003\r\n"
				+ "13	E01-F010001-F010003\r\n" + "43	E01-F010001-F010003\r\n" + "63	E01-F010001-F010003\r\n"
				+ "85	E01-F010001-F010003\r\n" + "14	E01-F010004-F010005\r\n" + "44	E01-F010004-F010005\r\n"
				+ "65	E01-F010004-F010005\r\n" + "86	E01-F010004-F010005\r\n" + "15	E01-F010004-F010006\r\n"
				+ "45	E01-F010004-F010006\r\n" + "64	E01-F010004-F010006\r\n" + "87	E01-F010004-F010006\r\n"
				+ "16	E01-F010008-F010007\r\n" + "46	E01-F010008-F010007\r\n" + "67	E01-F010008-F010007\r\n"
				+ "88	E01-F010008-F010007\r\n" + "17	E01-F010008-F010009\r\n" + "47	E01-F010008-F010009\r\n"
				+ "66	E01-F010008-F010009\r\n" + "89	E01-F010011-F010009";
		String ss[] = line.split("\r\n");
		for (String s : ss) {
			String a[] = s.split("\t");
			dto.setTrnsNo(Long.parseLong(a[0]));
			dto.setEngRtId(a[1]);

			try {
				dfo.insert(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public EngTransRtInsertDfo() {

	}

	@Override
	public Boolean call(FxFact fact, Tran06Dto data) throws Exception {
		return insert(data);
	}

	public boolean insert(Tran06Dto data) throws Exception {

		FE_ENG_TRANS_RT dbo = ObjectUtil.toObject(data, FE_ENG_TRANS_RT.class);

		if ("n".equalsIgnoreCase(dbo.isEngRtUseYn())) {
			ClassDaoEx.open() //
					.updateOfClass(FE_ENG_TRANS_RT.class,
							FxApi.makePara("trnsNo", dbo.getTrnsNo(), "engRtId", dbo.getEngRtId(), "engRtUseYn", "Y"),
							FxApi.makePara("rtFnshDtm", DateUtil.getDtm(), "engRtUseYn", "N")) //
					.close();
		} else {

			FxTableMaker.initRegChg(0, dbo);
			dbo.setRtStrtDtm(DateUtil.getDtm());

			ClassDaoEx.open() //
					.updateOfClass(FE_ENG_TRANS_RT.class, FxApi.makePara("trnsNo", dbo.getTrnsNo(), "engRtUseYn", "Y"),
							FxApi.makePara("rtFnshDtm", dbo.getRtStrtDtm(), "engRtUseYn", "N")) //
					.insertOfClass(FE_ENG_TRANS_RT.class, dbo) //
					.close();
		}

		return true;

	}
}
