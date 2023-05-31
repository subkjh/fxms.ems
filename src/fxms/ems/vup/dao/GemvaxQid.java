package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/gemvax.xml<br>
* @since 20230522174934
* @author subkjh 
*
*/


public class GemvaxQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/vup/gemvax.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/vup/gemvax.xml";

public GemvaxQid() { 
} 
/**
* para : $calcYm<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.SINK_FACTORY_ID					as PLANT_PID                         '공장PID '<br>				, if(a.ENERGY_GROUP_NAME = '압축공기', 'E01', <br>					if(a.ENERGY_GROUP_NAME = '스팀', 'E02', 'E03')) <br>					 								as ENG_TID							'에너지ID '		<br>		        , concat(a.YEAR, a.MONTH)			as CALC_YM                           '정산년월 '<br>		        , 'SINK'							as SINK_SOURCE                       '싱크소스구분 '<br>		        , a.SETTLEMENT_STATUS_CD			as CALC_ST_CD                        '정산상태코드 '<br>		        , a.DEPOSIT_CD						as DEAWI_ST_CD                       '입출금상태코드 '<br>		        , a.SETTLEMENT_AMOUNT				as CALC_AMT                          '정산금액 '<br>		        , a.TOTAL_USING_VOLUME				as TRNS_VOL                          '거래량 '<br>		        , a.USING_PRICE_CHARGE				as TRNS_VOL_CHRG                     '거래량요금 '<br>		        , a.UNIT_PRICE						as USE_UNIT_PRICE                    '사용단가 '<br>		        , a.BASIS_UNIT_PRICE				as BASE_UNIT_PRICE                   '기준단가 '<br>		        , a.INTER_UNIT_PRICE				as LINK_UNIT_PRICE                   '연동단가 '<br>		        , a.BASIC_PRICE_CHARGE				as BASIC_CHRG                        '기본요금 '<br>		        , a.TOTAL_BUY_UNIT_VOLUME			as CNTRT_TRNS_VOL                    '계약거래량 '<br>		        , a.EXCESS_CHARGE					as EXCS_CHRG                         '초과요금 '<br>		        , a.DEDUCTION_CHARGE				as REDU_CHRG                         '공제요금 '<br>		        , a.EMISSION_CHARGE					as GHG_COST                          '온실가스비용 '<br>		        , a.VAT_CHARGE						as VAT_COST                          '부가세비용 '<br>		        , a.VUP_FEE_CHARGE					as COMM_FAC_COST                     '공용설비사용 '<br>		        , a.ORI_SETTLEMENT_AMOUNT			as ORG_CALC_AMT                      '원래정산금액 '<br>		        , a.ORI_TOTAL_USING_VOLUME			as ORG_TRNS_VOL                      '원래거래량 '<br>		        , a.ORI_USING_PRICE_CHARGE			as ORG_TRNS_VOL_CHRG                 '원래거래량요금 '<br>		        , ''								as CORR_MEMO                         '보정변경사유 '<br>		        , ''								as CORR_CHG_DATE                     '보정변경일자 '<br>		        , ''								as CORR_USER_NO                      '보정변경자번호 '<br>		        , ''								as REG_USER_NO                       '등록사용자번호 '<br>		        , ''								as REG_DTM                           '등록일시 '<br>		        , ''								as CHG_USER_NO                       '수정사용자번호 '<br>		        , ''								as CHG_DTM                           '수정일시 '<br>		from 	view_tbl_settlement_buy a		 <br>		where	concat(a.YEAR, a.MONTH) >= $calcYm<br><br> <br>
*/
public final String select_settlement_buy_list = "select-settlement-buy-list";

/**
* para : $calcYm<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    0									as TRNS_NO                           '거래번호 '<br>		        , concat(a.YEAR, a.MONTH)			as CALC_YM                           '정산년월 '<br>		        , a.SETTLEMENT_STATUS_CD			as CALC_ST_CD                        '정산상태코드 '<br>		        , a.DEPOSIT_CD						as DEAWI_ST_CD                       '입출금상태코드 '<br>		        , a.SETTLEMENT_AMOUNT				as CALC_AMT                          '정산금액 '<br>		        , a.TOTAL_USING_VOLUME				as TRNS_VOL                          '거래량 '<br>		        , a.USING_PRICE_CHARGE				as TRNS_VOL_CHRG                     '거래량요금 '<br>		        , a.UNIT_PRICE						as USE_UNIT_PRICE                    '사용단가 '<br>		        , a.BASIS_UNIT_PRICE				as BASE_UNIT_PRICE                   '기준단가 '<br>		        , a.INTER_UNIT_PRICE				as LINK_UNIT_PRICE                   '연동단가 '<br>		        , a.BASIC_PRICE_CHARGE				as BASIC_CHRG                        '기본요금 '<br>		        , a.TOTAL_BUY_UNIT_VOLUME			as CNTRT_TRNS_VOL                    '계약거래량 '<br>		        , a.EXCESS_CHARGE					as EXCS_CHRG                         '초과요금 '<br>		        , a.DEDUCTION_CHARGE				as REDU_CHRG                         '공제요금 '<br>		        , a.EMISSION_CHARGE					as GHG_COST                          '온실가스비용 '<br>		        , a.VAT_CHARGE						as VAT_COST                          '부가세비용 '<br>		        , a.VUP_FEE_CHARGE					as COMM_FAC_COST                     '공용설비사용 '<br>		        , a.ORI_SETTLEMENT_AMOUNT			as ORG_CALC_AMT                      '원래정산금액 '<br>		        , a.ORI_TOTAL_USING_VOLUME			as ORG_TRNS_VOL                      '원래거래량 '<br>		        , a.ORI_USING_PRICE_CHARGE			as ORG_TRNS_VOL_CHRG                 '원래거래량요금 '<br>		        , ''								as CORR_MEMO                         '보정변경사유 '<br>		        , ''								as CORR_CHG_DATE                     '보정변경일자 '<br>		        , ''								as CORR_USER_NO                      '보정변경자번호 '<br>		        , ''								as REG_USER_NO                       '등록사용자번호 '<br>		        , ''								as REG_DTM                           '등록일시 '<br>		        , ''								as CHG_USER_NO                       '수정사용자번호 '<br>		        , ''								as CHG_DTM                           '수정일시 '<br>		from 	view_tbl_settlement_buy a		 <br>		where	concat(a.YEAR, a.MONTH) >= $calcYm<br><br> <br>
*/
public final String select_settlement_list = "select-settlement-list";

/**
* para : $calcYm<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.SOURCE_FACTORY_ID				as PLANT_PID                         '공장PID '<br>				, if(a.ENERGY_GROUP_NAME = '압축공기', 'E01', <br>					if(a.ENERGY_GROUP_NAME = '스팀', 'E02', 'E03')) <br>					 								as ENG_TID							'에너지ID '		<br>		        , concat(a.YEAR, a.MONTH)			as CALC_YM                           '정산년월 '<br>		        , 'SOURCE'							as SINK_SOURCE                       '싱크소스구분 '<br>		        , a.SETTLEMENT_STATUS_CD			as CALC_ST_CD                        '정산상태코드 '<br>		        , a.PAYMENT_CD						as DEAWI_ST_CD                       '입출금상태코드 '<br>		        , a.SETTLEMENT_AMOUNT				as CALC_AMT                          '정산금액 '<br>		        , a.TOTAL_SUPPLY_VOLUME				as TRNS_VOL                          '거래량 '<br>		        , a.SUPPLY_PRICE_CHARGE				as TRNS_VOL_CHRG                     '거래량요금 '<br>		        , a.UNIT_PRICE						as USE_UNIT_PRICE                    '사용단가 '<br>		        , 0									as BASE_UNIT_PRICE                   '기준단가 '<br>		        , 0									as LINK_UNIT_PRICE                   '연동단가 '<br>		        , 0									as BASIC_CHRG                        '기본요금 '<br>		        , 0									as CNTRT_TRNS_VOL                    '계약거래량 '<br>		        , 0									as EXCS_CHRG                         '초과요금 '<br>		        , 0									as REDU_CHRG                         '공제요금 '<br>		        , 0									as GHG_COST                          '온실가스비용 '<br>		        , a.VAT_CHARGE						as VAT_COST                          '부가세비용 '<br>		        , a.VUP_FEE_CHARGE					as COMM_FAC_COST                     '공용설비사용 '<br>		        , a.ORI_SETTLEMENT_AMOUNT			as ORG_CALC_AMT                      '원래정산금액 '<br>		        , a.ORI_TOTAL_SUPPLY_VOLUME			as ORG_TRNS_VOL                      '원래거래량 '<br>		        , a.ORI_SUPPLY_PRICE_CHARGE			as ORG_TRNS_VOL_CHRG                 '원래거래량요금 '<br>		        , ''								as CORR_MEMO                         '보정변경사유 '<br>		        , ''								as CORR_CHG_DATE                     '보정변경일자 '<br>		        , ''								as CORR_USER_NO                      '보정변경자번호 '<br>		        , ''								as REG_USER_NO                       '등록사용자번호 '<br>		        , ''								as REG_DTM                           '등록일시 '<br>		        , ''								as CHG_USER_NO                       '수정사용자번호 '<br>		        , ''								as CHG_DTM                           '수정일시 '<br>		from 	view_tbl_settlement_sale a		 <br>		where	concat(a.YEAR, a.MONTH) >= $calcYm<br><br> <br>
*/
public final String select_settlement_sale_list = "select-settlement-sale-list";

/**
* para : $trnsNo<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.SEQ						as TRNS_NO							 '거래번호 '<br>				, if(a.ENERGY_GROUP_NAME = '압축공기', 'E01', <br>					if(a.ENERGY_GROUP_NAME = '스팀', 'E02', 'E03')) <br>					 						as ENG_TID							'에너지ID '<br>				, a.SOURCE_FACTORY_ID		as SELL_INLO_TID					'판매설치위치번호 '<br>				, a.SINK_FACTORY_ID			as BUY_INLO_TID						'구입설치위치번호 '<br>				, DATE_FORMAT(a.TRADING_START_DATE, '%Y%m%d%H%i%S') <br>											as TRNS_STRT_DTM					'거래시작일시 '<br>				, DATE_FORMAT(a.TRADING_END_DATE, '%Y%m%d%H%i%S')		<br>											as TRNS_FNSH_DTM					'거래종료일시 '<br>				, a.BUY_MAX_UNIT_VOLUME		as HOURLY_MAX_CNTRT_TRNS_VOL		'시간당최대계약거래량 '<br>		from 	view_tbl_trading_buy 		a<br>		where 	a.CANCEL_FLAG	= 'N'<br>		and		a.SEQ >= $trnsNo<br><br> <br>
*/
public final String select_trade_list = "select-trade-list";

}