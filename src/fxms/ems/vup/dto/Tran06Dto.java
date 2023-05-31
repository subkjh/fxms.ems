package fxms.ems.vup.dto;

import fxms.bas.fxo.FxAttr;

/**
 * @description 에너지거래라우팅테이블
 * @author fxms auto
 * @date 20230504
 */

public class Tran06Dto {

	public Tran06Dto() {
	}

	@FxAttr(description = "거래번호")
	private Long trnsNo;

	@FxAttr(description = "에너지루트ID", example = "E01-F010004-F010007")
	private String engRtId;

	@FxAttr(description = "에너지루트사용여부", example = "Y")
	private String engRtUseYn = "Y";

	@FxAttr(description = "라우팅메모", required = false)
	private String rtMemo;

	/**
	 * @return 거래번호
	 */
	public Long getTrnsNo() {
		return trnsNo;
	}

	/**
	 * @param trnsNo 거래번호
	 */
	public void setTrnsNo(Long trnsNo) {
		this.trnsNo = trnsNo;
	}

	/**
	 * @return 에너지루트ID
	 */
	public String getEngRtId() {
		return engRtId;
	}

	/**
	 * @param engRtId 에너지루트ID
	 */
	public void setEngRtId(String engRtId) {
		this.engRtId = engRtId;
	}

	/**
	 * @return 에너지루트사용여부
	 */
	public String isEngRtUseYn() {
		return engRtUseYn;
	}

	/**
	 * @param engRtUseYn 에너지루트사용여부
	 */
	public void setEngRtUseYn(String engRtUseYn) {
		this.engRtUseYn = engRtUseYn;
	}

	/**
	 * @return 라우팅메모
	 */
	public String getRtMemo() {
		return rtMemo;
	}

	/**
	 * @param rtMemo 라우팅메모
	 */
	public void setRtMemo(String rtMemo) {
		this.rtMemo = rtMemo;
	}
}
