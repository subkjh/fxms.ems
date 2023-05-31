package fxms.ems.vup.dto;

import java.io.Serializable;

import fxms.bas.fxo.FxAttr;

/**
 * @description 에너지거래기본테이블
 * @author fxms auto
 * @date 20230502
 */

public class Tran01Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	public Tran01Dto() {
	}

	@FxAttr(description = "거래번호")
	private Long trnsNo;

	@FxAttr(description = "에너지TID", example = "E01")
	private String engTid;

	@FxAttr(description = "산단 PID", example = "F01")
	private String complexPid;

	@FxAttr(description = "SOURCE 공장 PID", example = "F010011")
	private String sourcePlantPid;

	@FxAttr(description = "SINK 공장 PID", example = "F 010001")
	private String sinkPlantPid;

	@FxAttr(description = "거래시작일시", required = false)
	private Long trnsStrtDtm;

	@FxAttr(description = "거래종료일시", required = false)
	private Long trnsFnshDtm;

	@FxAttr(description = "월계약거래량", required = false)
	private Long monthCntrtTrnsVol;

	@FxAttr(description = "시간당최대계약거래량", required = false)
	private Long hourlyMaxCntrtTrnsVol;

	@FxAttr(description = "거래상태코드", example = "0")
	private String trnsStCd = "0";

	@FxAttr(description = "거래방식코드", example = "4301")
	private String trnsMethdCd = "4301";

	@FxAttr(description = "단가표ID", required = false)
	private String unitPriceTblId;

	@FxAttr(description = "계약문서번호", required = false)
	private String cntrtDocNum;

	@FxAttr(description = "거래설명", required = false)
	private String trnsDescr;

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
	 * @return 거래시작일시
	 */
	public Long getTrnsStrtDtm() {
		return trnsStrtDtm;
	}

	/**
	 * @param trnsStrtDtm 거래시작일시
	 */
	public void setTrnsStrtDtm(Long trnsStrtDtm) {
		this.trnsStrtDtm = trnsStrtDtm;
	}

	/**
	 * @return 거래종료일시
	 */
	public Long getTrnsFnshDtm() {
		return trnsFnshDtm;
	}

	/**
	 * @param trnsFnshDtm 거래종료일시
	 */
	public void setTrnsFnshDtm(Long trnsFnshDtm) {
		this.trnsFnshDtm = trnsFnshDtm;
	}

	/**
	 * @return 월계약거래량
	 */
	public Long getMonthCntrtTrnsVol() {
		return monthCntrtTrnsVol;
	}

	/**
	 * @param monthCntrtTrnsVol 월계약거래량
	 */
	public void setMonthCntrtTrnsVol(Long monthCntrtTrnsVol) {
		this.monthCntrtTrnsVol = monthCntrtTrnsVol;
	}

	/**
	 * @return 시간당최대계약거래량
	 */
	public Long getHourlyMaxCntrtTrnsVol() {
		return hourlyMaxCntrtTrnsVol;
	}

	/**
	 * @param hourlyMaxCntrtTrnsVol 시간당최대계약거래량
	 */
	public void setHourlyMaxCntrtTrnsVol(Long hourlyMaxCntrtTrnsVol) {
		this.hourlyMaxCntrtTrnsVol = hourlyMaxCntrtTrnsVol;
	}

	/**
	 * @return 거래상태코드
	 */
	public String getTrnsStCd() {
		return trnsStCd;
	}

	/**
	 * @param trnsStCd 거래상태코드
	 */
	public void setTrnsStCd(String trnsStCd) {
		this.trnsStCd = trnsStCd;
	}

	/**
	 * @return 거래방식코드
	 */
	public String getTrnsMethdCd() {
		return trnsMethdCd;
	}

	/**
	 * @param trnsMethdCd 거래방식코드
	 */
	public void setTrnsMethdCd(String trnsMethdCd) {
		this.trnsMethdCd = trnsMethdCd;
	}

	/**
	 * @return 단가표ID
	 */
	public String getUnitPriceTblId() {
		return unitPriceTblId;
	}

	/**
	 * @param unitPriceTblId 단가표ID
	 */
	public void setUnitPriceTblId(String unitPriceTblId) {
		this.unitPriceTblId = unitPriceTblId;
	}

	/**
	 * @return 계약문서번호
	 */
	public String getCntrtDocNum() {
		return cntrtDocNum;
	}

	/**
	 * @param cntrtDocNum 계약문서번호
	 */
	public void setCntrtDocNum(String cntrtDocNum) {
		this.cntrtDocNum = cntrtDocNum;
	}

	/**
	 * @return 거래설명
	 */
	public String getTrnsDescr() {
		return trnsDescr;
	}

	/**
	 * @param trnsDescr 거래설명
	 */
	public void setTrnsDescr(String trnsDescr) {
		this.trnsDescr = trnsDescr;
	}

	public String getEngTid() {
		return engTid;
	}

	public void setEngTid(String engTid) {
		this.engTid = engTid;
	}

	public String getComplexPid() {
		return complexPid;
	}

	public void setComplexPid(String complexPid) {
		this.complexPid = complexPid;
	}

	public String getSourcePlantPid() {
		return sourcePlantPid;
	}

	public void setSourcePlantPid(String sourcePlantPid) {
		this.sourcePlantPid = sourcePlantPid;
	}

	public String getSinkPlantPid() {
		return sinkPlantPid;
	}

	public void setSinkPlantPid(String sinkPlantPid) {
		this.sinkPlantPid = sinkPlantPid;
	}

	
}
