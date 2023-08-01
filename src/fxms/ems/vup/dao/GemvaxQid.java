package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/gemvax.xml<br>
* @since 20230726103724
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

/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>with datas as (<br>			select 	a.SEQ<br>					, a.FACTORY_GROUP_ID<br>					, a.ENERGY_GROUP_ID<br>					, a.ROOM_NAME 					<br>					, a.ROOM_START_MONTH <br>					, a.ROOM_END_MONTH<br>					, a.TRADING_MODEL_CD<br>					, ( select CODE_NAME from tbl_common_code t1 where t1.CODE_GROUP_ID = '43' and t1.CODE_ID = a.TRADING_MODEL_CD )<br>												as TRADING_MODEL_NM<br>					, a.UNIT_PRICE_TYPE_CD <br>					, ( select CODE_NAME from tbl_common_code t1 where t1.CODE_GROUP_ID = '42' and t1.CODE_ID = a.UNIT_PRICE_TYPE_CD )<br>												as UNIT_PRICE_TYPE_NM<br>					, a.SETTLEMENT_TYPE_CD <br>					, ( select CODE_NAME from tbl_common_code t1 where t1.CODE_GROUP_ID = '52' and t1.CODE_ID = a.SETTLEMENT_TYPE_CD )<br>												as SETTLEMENT_TYPE_NM<br>					, b.TRADING_ROOM_SEQ <br>					, b.SOURCE_FACTORY_ID <br>					, b.SALE_UNIT_PRICE <br>					, b.SALE_UNIT_VOLUME <br>					, c.SINK_FACTORY_ID<br>					, c.TRADING_ROOM_SALE_SEQ<br>					, c.BUY_START_MONTH<br>					, c.BUY_END_MONTH <br>					, c.BUY_UNIT_VOLUME<br>					, c.BUY_MAX_UNIT_VOLUME<br>					, DATE_FORMAT(c.TRADING_DATE , '%Y%m%d%H%i%s') <br>												as TRADING_DATE<br>					, c.SEQ as SEQ2<br>			from 	tbl_trading_room 		a<br>					, tbl_trading_room_sale b<br>					, tbl_trading_room_buy 	c<br>			where 	a.SEQ 					= b.TRADING_ROOM_SEQ <br>			and 	c.TRADING_ROOM_SALE_SEQ = b.SEQ<br>			and 	a.USE_FLAG  			= 'Y'<br>			and 	c.CANCEL_FLAG 			= 'N'<br>			and 	DATE_FORMAT(c.TRADING_DATE , '%Y%m%d%H%i%s') <br>											> '20230101000000'<br>		) <br>		select 	a.*<br>				, a.FACTORY_GROUP_ID			as COMPLEX_TID<br>				, a.SOURCE_FACTORY_ID			as SELL_PLANT_TID<br>				, a.SINK_FACTORY_ID				as BUY_PLANT_TID<br>				, a.ENERGY_GROUP_ID				as ENG_TID<br>				, a.TRADING_MODEL_CD 			as TRNS_METHD_CD<br>				, a.TRADING_DATE				as TRNS_DTM<br>				, concat(a.BUY_START_MONTH, '01000000')<br>									 			as TRNS_STRT_DTM<br>				, concat(a.BUY_END_MONTH, '01000000')									 			<br>												as TRNS_FNSH_DTM<br>				, a.BUY_UNIT_VOLUME 			as HOURLY_MAX_CNTRT_TRNS_VOL		<br>				, concat(SEQ, '-', TRADING_ROOM_SEQ, '-', TRADING_ROOM_SALE_SEQ, '-', SEQ2) 	<br>												as CNTRT_DOC_NUM<br>				, concat(SEQ, ' ', ROOM_NAME, '/', TRADING_MODEL_NM )<br>												as TRNS_DESCR<br>		from 	datas a<br><br> <br>
*/
public final String select_trading_list = "select-trading-list";

}