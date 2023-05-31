package fxms.ems.vup.dbo.all;

import subkjh.dao.def.Column.COLUMN_OP;
import subkjh.dao.def.FxColumn;
import subkjh.dao.def.FxIndex;
import subkjh.dao.def.FxTable;
import subkjh.dao.def.Index.INDEX_TYPE;

/**
 * @since 2023.05.03 12:55
 * @author subkjh autometic create by subkjh.dao
 *
 */

@FxTable(name = "VUP_ENG_TRANS_CALC", comment = "VUP에너지거래정산테이블")
@FxIndex(name = "VUP_ENG_TRANS_CALC__PK", type = INDEX_TYPE.PK, columns = { "PLANT_PID", "ENG_TID", "CALC_YM",
		"SINK_SOURCE" })
public class VUP_ENG_TRANS_CALC {

	public VUP_ENG_TRANS_CALC() {
	}

	@FxColumn(name = "PLANT_PID", size = 50, comment = "공장PID")
	private String plantPid;

	@FxColumn(name = "ENG_TID", size = 20, comment = "에너지ID")
	private String engTid;

	@FxColumn(name = "CALC_YM", size = 6, comment = "정산년월")
	private String calcYm;

	@FxColumn(name = "SINK_SOURCE", size = 10, comment = "싱크소스구분")
	private String sinkSource;

	@FxColumn(name = "CALC_ST_CD", size = 10, comment = "정산상태코드")
	private String calcStCd;

	@FxColumn(name = "DEAWI_ST_CD", size = 10, comment = "입출금상태코드")
	private String deawiStCd;

	@FxColumn(name = "CALC_AMT", size = 14, comment = "정산금액")
	private Long calcAmt;

	@FxColumn(name = "TRNS_VOL", size = 9, comment = "거래량")
	private Integer trnsVol;

	@FxColumn(name = "TRNS_VOL_CHRG", size = 14, comment = "거래량요금")
	private Long trnsVolChrg;

	@FxColumn(name = "USE_UNIT_PRICE", size = 12, nullable = true, comment = "사용단가")
	private Double useUnitPrice;

	@FxColumn(name = "BASE_UNIT_PRICE", size = 12, nullable = true, comment = "기준단가")
	private Double baseUnitPrice;

	@FxColumn(name = "LINK_UNIT_PRICE", size = 12, nullable = true, comment = "연동단가")
	private Double linkUnitPrice;

	@FxColumn(name = "BASIC_CHRG", size = 9, nullable = true, comment = "기본요금")
	private Integer basicChrg;

	@FxColumn(name = "CNTRT_TRNS_VOL", size = 14, nullable = true, comment = "계약거래량")
	private Long cntrtTrnsVol;

	@FxColumn(name = "EXCS_CHRG", size = 9, nullable = true, comment = "초과요금")
	private Integer excsChrg;

	@FxColumn(name = "REDU_CHRG", size = 9, nullable = true, comment = "공제요금")
	private Integer reduChrg;

	@FxColumn(name = "GHG_COST", size = 9, nullable = true, comment = "온실가스비용")
	private Integer ghgCost;

	@FxColumn(name = "VAT_COST", size = 9, nullable = true, comment = "부가세비용")
	private Integer vatCost;

	@FxColumn(name = "COMM_FAC_COST", size = 9, nullable = true, comment = "공용설비사용")
	private Integer commFacCost;

	@FxColumn(name = "ORG_CALC_AMT", size = 14, nullable = true, comment = "원래정산금액")
	private Long orgCalcAmt;

	@FxColumn(name = "ORG_TRNS_VOL", size = 9, nullable = true, comment = "원래거래량")
	private Integer orgTrnsVol;

	@FxColumn(name = "ORG_TRNS_VOL_CHRG", size = 14, nullable = true, comment = "원래거래량요금")
	private Long orgTrnsVolChrg;

	@FxColumn(name = "CORR_MEMO", size = 40, nullable = true, comment = "보정변경사유")
	private String corrMemo;

	@FxColumn(name = "CORR_CHG_DATE", size = 8, nullable = true, comment = "보정변경일자")
	private String corrChgDate;

	@FxColumn(name = "CORR_USER_NO", size = 9, nullable = true, comment = "보정변경자번호")
	private Integer corrUserNo;

	@FxColumn(name = "REG_USER_NO", size = 9, operator = COLUMN_OP.insert, nullable = true, comment = "등록사용자번호", defValue = "0")
	private Integer regUserNo = 0;

	@FxColumn(name = "REG_DTM", size = 14, operator = COLUMN_OP.insert, nullable = true, comment = "등록일시")
	private Long regDtm;

	@FxColumn(name = "CHG_USER_NO", size = 9, nullable = true, comment = "수정사용자번호", defValue = "0")
	private Integer chgUserNo = 0;

	@FxColumn(name = "CHG_DTM", size = 14, nullable = true, comment = "수정일시")
	private Long chgDtm;

	/**
	 * @return 공장PID
	 */
	public String getPlantPid() {
		return plantPid;
	}

	/**
	 * @param plantPid 공장PID
	 */
	public void setPlantPid(String plantPid) {
		this.plantPid = plantPid;
	}

	/**
	 * @return 에너지ID
	 */
	public String getEngTid() {
		return engTid;
	}

	/**
	 * @param engTid 에너지ID
	 */
	public void setEngTid(String engTid) {
		this.engTid = engTid;
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
	 * @return 싱크소스구분
	 */
	public String getSinkSource() {
		return sinkSource;
	}

	/**
	 * @param sinkSource 싱크소스구분
	 */
	public void setSinkSource(String sinkSource) {
		this.sinkSource = sinkSource;
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

	/**
	 * @return 등록사용자번호
	 */
	public Integer getRegUserNo() {
		return regUserNo;
	}

	/**
	 * @param regUserNo 등록사용자번호
	 */
	public void setRegUserNo(Integer regUserNo) {
		this.regUserNo = regUserNo;
	}

	/**
	 * @return 등록일시
	 */
	public Long getRegDtm() {
		return regDtm;
	}

	/**
	 * @param regDtm 등록일시
	 */
	public void setRegDtm(Long regDtm) {
		this.regDtm = regDtm;
	}

	/**
	 * @return 수정사용자번호
	 */
	public Integer getChgUserNo() {
		return chgUserNo;
	}

	/**
	 * @param chgUserNo 수정사용자번호
	 */
	public void setChgUserNo(Integer chgUserNo) {
		this.chgUserNo = chgUserNo;
	}

	/**
	 * @return 수정일시
	 */
	public Long getChgDtm() {
		return chgDtm;
	}

	/**
	 * @param chgDtm 수정일시
	 */
	public void setChgDtm(Long chgDtm) {
		this.chgDtm = chgDtm;
	}
}
