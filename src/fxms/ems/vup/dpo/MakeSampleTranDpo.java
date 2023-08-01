package fxms.ems.vup.dpo;

import java.util.ArrayList;
import java.util.List;

import fxms.bas.impl.dpo.FxDpo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_RT_BAS;
import fxms.ems.bas.dbo.FE_ENG_TRANS_BAS;
import fxms.ems.bas.dbo.FE_ENG_TRANS_RT;
import subkjh.bas.co.utils.DateUtil;
import subkjh.dao.ClassDaoEx;

/**
 * 에너지 경로를 이용하여 샘플 거래 정보를 생성한다.<br>
 * MakrRouteDpo를 실행한 후 도면과 다른 부분은 USE_YN = 'N'으로 설정한다.
 * 
 * @author subkjh
 *
 */
public class MakeSampleTranDpo implements FxDpo<Void, Boolean> {

	class TransData {
		FE_ENG_TRANS_BAS bas;
		FE_ENG_TRANS_RT rt;
	}

	public static void main(String[] args) {
		MakeSampleTranDpo dpo = new MakeSampleTranDpo();
		try {
			dpo.make();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MakeSampleTranDpo() {

	}

	private List<TransData> makeSampleTrans() throws Exception {
		List<FE_ENG_RT_BAS> rtList = selectRt();

		List<TransData> transList = new ArrayList<>();
		FE_ENG_TRANS_BAS trans;
		FE_ENG_TRANS_RT rt;

		for (FE_ENG_RT_BAS bas : rtList) {
			trans = new FE_ENG_TRANS_BAS();

			trans.setBuyInloNo(bas.getFnshInloNo());
			trans.setCntrtDocNum("TEST-10101");
			trans.setEngId(bas.getEngId());
			trans.setInloNo(bas.getInloNo());
			trans.setMonthCntrtTrnsVol(10000L);
			trans.setSellInloNo(bas.getStrtInloNo());
			trans.setTrnsDescr("샘플로 자동 추가됨");
			trans.setTrnsFnshDtm(29991231000000L);
			trans.setTrnsMethdCd("4301"); // 중개자거래
			trans.setTrnsStCd("1"); // 계약유지
			trans.setTrnsNo(null);
			trans.setTrnsStrtDtm(20230101000000L);
			trans.setUnitPriceTblId(null);

			rt = new FE_ENG_TRANS_RT();
			rt.setEngRtId(bas.getEngRtId());
			rt.setRtMemo("샘플자동등록");
			rt.setRtStrtDtm(DateUtil.getDtm());
			rt.setTrnsNo(null);
			rt.setEngRtUseYn("Y");

			TransData data = new TransData();
			data.rt = rt;
			data.bas = trans;
			transList.add(data);

		}

		return transList;
	}

	private void save(List<TransData> datas) throws Exception {
		MakeEnergyTransactionDfo dfo = new MakeEnergyTransactionDfo();
		for (TransData data : datas) {
			dfo.setEnergyTransaction(data.bas, data.rt);
		}
	}

	private List<FE_ENG_RT_BAS> selectRt() throws Exception {

		return ClassDaoEx.SelectDatas(FE_ENG_RT_BAS.class, null);

	}

	public void make() throws Exception {

		List<TransData> list = makeSampleTrans(); // 임시 거래 정보 생성

		save(list); // 기록

	}

	@Override
	public Boolean run(FxFact fact, Void data) throws Exception {
		return null;
	}
}
