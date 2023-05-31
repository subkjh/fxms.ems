package fxms.ems.vup.dto;

import java.io.Serializable;

import fxms.bas.fxo.FxAttr;

/**
 * @description 에너지거래정산테이블
 * @author fxms auto
 * @date 20230502
 */

public class FeEngTransCalcDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public FeEngTransCalcDto() {
	}

	@FxAttr(description = "거래번호")
	private Long trnsNo;

	@FxAttr(description = "정산년월")
	private String calcYm;

	@FxAttr(description = "정산상태코드")
	private String calcStCd;

	@FxAttr(description = "입출금상태코드")
	private String deawiStCd;

	@FxAttr(description = "정산금액")
	private Long calcAmt;

	@FxAttr(description = "거래량")
	private Integer trnsVol;

	@FxAttr(description = "거래량요금")
	private Long trnsVolChrg;

	@FxAttr(description = "사용단가")
	private Double useUnitPrice;

	@FxAttr(description = "기준단가", required = false)
	private Double baseUnitPrice;

	@FxAttr(description = "연동단가", required = false)
	private Double linkUnitPrice;

	@FxAttr(description = "기본요금")
	private Integer basicChrg;

	@FxAttr(description = "계약거래량", required = false)
	private Long cntrtTrnsVol;

	@FxAttr(description = "초과요금", required = false)
	private Integer excsChrg;

	@FxAttr(description = "공제요금", required = false)
	private Integer reduChrg;

	@FxAttr(description = "온실가스비용", required = false)
	private Integer ghgCost;

	@FxAttr(description = "부가세비용", required = false)
	private Integer vatCost;

	@FxAttr(description = "공용설비사용", required = false)
	private Integer commFacCost;

	@FxAttr(description = "원래정산금액", required = false)
	private Long orgCalcAmt;

	@FxAttr(description = "원래거래량", required = false)
	private Integer orgTrnsVol;

	@FxAttr(description = "원래거래량요금", required = false)
	private Long orgTrnsVolChrg;

	@FxAttr(description = "보정변경사유", required = false)
	private String corrMemo;

	@FxAttr(description = "보정변경일자", required = false)
	private String corrChgDate;

	@FxAttr(description = "보정변경자번호", required = false)
	private Integer corrUserNo;

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
	 * @return 정산년월
	 */
	public String getCalcYm() {
		return calcYm;
	}

	/**
	 * @param calcYm 정산년월
	 */
	public void setCalcYm(String calcYm) {
		this.calcYm = calcYm;
	}

	/**
	 * @return 정산상태코드
	 */
	public String getCalcStCd() {
		return calcStCd;
	}

	/**
	 * @param calcStCd 정산상태코드
	 */
	public void setCalcStCd(String calcStCd) {
		this.calcStCd = calcStCd;
	}

	/**
	 * @return 입출금상태코드
	 */
	public String getDeawiStCd() {
		return deawiStCd;
	}

	/**
	 * @param deawiStCd 입출금상태코드
	 */
	public void setDeawiStCd(String deawiStCd) {
		this.deawiStCd = deawiStCd;
	}

	/**
	 * @return 정산금액
	 */
	public Long getCalcAmt() {
		return calcAmt;
	}

	/**
	 * @param calcAmt 정산금액
	 */
	public void setCalcAmt(Long calcAmt) {
		this.calcAmt = calcAmt;
	}

	/**
	 * @return 거래량
	 */
	public Integer getTrnsVol() {
		return trnsVol;
	}

	/**
	 * @param trnsVol 거래량
	 */
	public void setTrnsVol(Integer trnsVol) {
		this.trnsVol = trnsVol;
	}

	/**
	 * @return 거래량요금
	 */
	public Long getTrnsVolChrg() {
		return trnsVolChrg;
	}

	/**
	 * @param trnsVolChrg 거래량요금
	 */
	public void setTrnsVolChrg(Long trnsVolChrg) {
		this.trnsVolChrg = trnsVolChrg;
	}

	/**
	 * @return 사용단가
	 */
	public Double getUseUnitPrice() {
		return useUnitPrice;
	}

	/**
	 * @param useUnitPrice 사용단가
	 */
	public void setUseUnitPrice(Double useUnitPrice) {
		this.useUnitPrice = useUnitPrice;
	}

	/**
	 * @return 기준단가
	 */
	public Double getBaseUnitPrice() {
		return baseUnitPrice;
	}

	/**
	 * @param baseUnitPrice 기준단가
	 */
	public void setBaseUnitPrice(Double baseUnitPrice) {
		this.baseUnitPrice = baseUnitPrice;
	}

	/**
	 * @return 연동단가
	 */
	public Double getLinkUnitPrice() {
		return linkUnitPrice;
	}

	/**
	 * @param linkUnitPrice 연동단가
	 */
	public void setLinkUnitPrice(Double linkUnitPrice) {
		this.linkUnitPrice = linkUnitPrice;
	}

	/**
	 * @return 기본요금
	 */
	public Integer getBasicChrg() {
		return basicChrg;
	}

	/**
	 * @param basicChrg 기본요금
	 */
	public void setBasicChrg(Integer basicChrg) {
		this.basicChrg = basicChrg;
	}

	/**
	 * @return 계약거래량
	 */
	public Long getCntrtTrnsVol() {
		return cntrtTrnsVol;
	}

	/**
	 * @param cntrtTrnsVol 계약거래량
	 */
	public void setCntrtTrnsVol(Long cntrtTrnsVol) {
		this.cntrtTrnsVol = cntrtTrnsVol;
	}

	/**
	 * @return 초과요금
	 */
	public Integer getExcsChrg() {
		return excsChrg;
	}

	/**
	 * @param excsChrg 초과요금
	 */
	public void setExcsChrg(Integer excsChrg) {
		this.excsChrg = excsChrg;
	}

	/**
	 * @return 공제요금
	 */
	public Integer getReduChrg() {
		return reduChrg;
	}

	/**
	 * @param reduChrg 공제요금
	 */
	public void setReduChrg(Integer reduChrg) {
		this.reduChrg = reduChrg;
	}

	/**
	 * @return 온실가스비용
	 */
	public Integer getGhgCost() {
		return ghgCost;
	}

	/**
	 * @param ghgCost 온실가스비용
	 */
	public void setGhgCost(Integer ghgCost) {
		this.ghgCost = ghgCost;
	}

	/**
	 * @return 부가세비용
	 */
	public Integer getVatCost() {
		return vatCost;
	}

	/**
	 * @param vatCost 부가세비용
	 */
	public void setVatCost(Integer vatCost) {
		this.vatCost = vatCost;
	}

	/**
	 * @return 공용설비사용
	 */
	public Integer getCommFacCost() {
		return commFacCost;
	}

	/**
	 * @param commFacCost 공용설비사용
	 */
	public void setCommFacCost(Integer commFacCost) {
		this.commFacCost = commFacCost;
	}

	/**
	 * @return 원래정산금액
	 */
	public Long getOrgCalcAmt() {
		return orgCalcAmt;
	}

	/**
	 * @param orgCalcAmt 원래정산금액
	 */
	public void setOrgCalcAmt(Long orgCalcAmt) {
		this.orgCalcAmt = orgCalcAmt;
	}

	/**
	 * @return 원래거래량
	 */
	public Integer getOrgTrnsVol() {
		return orgTrnsVol;
	}

	/**
	 * @param orgTrnsVol 원래거래량
	 */
	public void setOrgTrnsVol(Integer orgTrnsVol) {
		this.orgTrnsVol = orgTrnsVol;
	}

	/**
	 * @return 원래거래량요금
	 */
	public Long getOrgTrnsVolChrg() {
		return orgTrnsVolChrg;
	}

	/**
	 * @param orgTrnsVolChrg 원래거래량요금
	 */
	public void setOrgTrnsVolChrg(Long orgTrnsVolChrg) {
		this.orgTrnsVolChrg = orgTrnsVolChrg;
	}

	/**
	 * @return 보정변경사유
	 */
	public String getCorrMemo() {
		return corrMemo;
	}

	/**
	 * @param corrMemo 보정변경사유
	 */
	public void setCorrMemo(String corrMemo) {
		this.corrMemo = corrMemo;
	}

	/**
	 * @return 보정변경일자
	 */
	public String getCorrChgDate() {
		return corrChgDate;
	}

	/**
	 * @param corrChgDate 보정변경일자
	 */
	public void setCorrChgDate(String corrChgDate) {
		this.corrChgDate = corrChgDate;
	}

	/**
	 * @return 보정변경자번호
	 */
	public Integer getCorrUserNo() {
		return corrUserNo;
	}

	/**
	 * @param corrUserNo 보정변경자번호
	 */
	public void setCorrUserNo(Integer corrUserNo) {
		this.corrUserNo = corrUserNo;
	}

}
