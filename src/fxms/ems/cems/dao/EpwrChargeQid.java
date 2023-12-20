package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/EpwrCharge.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class EpwrChargeQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/EpwrCharge.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/EpwrCharge.xml";

public EpwrChargeQid() { 
} 
/**
* para : $yyyymm, $yyyymm, $yyyymm, $yyyymm, $yyyymm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_EPWR_CHARGE            ' 전기요금테이블 '<br>		(<br>			  INLO_NO                   ' 설치위치번호 ' <br>			, YYYY_MM                   ' 년월 ' <br>			, EPWR_PRICE_ID             ' 전기요금ID ' <br>			, BASIC_PRICE               ' 기본요금단가(원/kW) ' <br>			, CNTRT_EPWR				' 계약전력(kWh) '<br>			, BASIC_CHARGE				' 기본요금(BASIC_PRICE * CNTRT_EPWR) ' <br>			<br>			, USED_AMT                  ' 사용량(kWh) ' <br>			, PEAK_USE_EPWR             ' 최대사용전력(kWh) ' <br>			, PEAK_USE_DTM				' 최대사용일시 '<br>			, USED_CHARGE               ' 사용요금 ' <br>			<br>			, POWER_FACTR_CHARGE		' 역률요금 '<br>			<br>			, REG_DESCR					' 등록설명 '			<br>			, REG_USER_NO               ' 등록사용자번호 ' <br>			, REG_DTM                   ' 등록일시 ' <br>			, CHG_USER_NO               ' 수정사용자번호 ' <br>			, CHG_DTM                   ' 수정일시 ' <br>		)<br>		with datas as (<br>			select 	a.INLO_NO				as INLO_NO <br>					, $yyyymm				as YYYY_MM<br>					, sum(USED_AMT)			as USED_AMT<br>					, sum(USED_CHARGE)		as USED_CHARGE<br>					, max(a.PEAK_USE_EPWR)	as PEAK_USE_EPWR<br>					, count(1)				as CNT<br>			from 	FE_EPWR_CHARGE_TIME a<br>					, FE_EPWR_INLO b<br>			where 	a.YYYYMMDDHH 	>= concat($yyyymm, '0100')<br>			and 	a.YYYYMMDDHH 	<= concat($yyyymm, '3124')<br>			and		a.INLO_NO 		= b.INLO_NO<br>			group by a.INLO_NO<br>		)<br>		, a as (<br>			select<br>					a.INLO_NO								as INLO_NO<br>					, a.YYYY_MM								as YYYY_MM					<br>					, b.EPWR_PRICE_ID						as EPWR_PRICE_ID<br>					, b.BASIC_PRICE							as BASIC_PRICE<br>					, b.CNTRT_EPWR							as CNTRT_EPWR<br>					, b.BASIC_PRICE * b.CNTRT_EPWR 			as BASIC_CHARGE					<br>					, a.USED_AMT							as USED_AMT<br>					, a.PEAK_USE_EPWR						as PEAK_USE_EPWR<br>					, ( select 	max(PEAK_USE_DTM)<br>						from	FE_EPWR_CHARGE_TIME t1<br>						where	t1.YYYYMMDDHH BETWEEN concat($yyyymm, '0100') and concat($yyyymm, '3124')<br>						and		t1.INLO_NO 	= a.INLO_NO<br>						and		t1.PEAK_USE_EPWR	= a.PEAK_USE_EPWR )<br>															as PEAK_USE_DTM<br>					, a.USED_CHARGE							as USED_CHARGE					<br>					, 0										as POWER_FACTR_CHARGE<br>					, concat('1H datas=', CNT)				as REG_DESCR										<br>					, 1										as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	as REG_DTM<br>					, 1										as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	as CHG_DTM<br>			from 	datas a<br>					, V_PLANT_EPWR_PRICE b<br>			where	a.INLO_NO = b.INLO_NO<br>		)<br>		select  *<br>		from 	a		<br>		ON DUPLICATE KEY UPDATE		<br>				EPWR_PRICE_ID			= a.EPWR_PRICE_ID<br> 				, BASIC_PRICE			= a.BASIC_PRICE<br>				, CNTRT_EPWR			= a.CNTRT_EPWR<br>				, BASIC_CHARGE			= a.BASIC_CHARGE				<br>				, USED_AMT 				= a.USED_AMT<br>				, PEAK_USE_EPWR			= a.PEAK_USE_EPWR<br>				, PEAK_USE_DTM			= a.PEAK_USE_DTM<br>				, USED_CHARGE			= a.USED_CHARGE				<br>				, POWER_FACTR_CHARGE 	= a.POWER_FACTR_CHARGE<br>				, REG_DESCR				= a.REG_DESCR<br><br> <br>
*/
public final String make_epwr_charge_monthly = "make-epwr-charge-monthly";

/**
* para : $yyyymm, $startDtm, $endDtm<br>
* result : EpwrPeakDto=fxms.ems.cems.dto.EpwrPeakDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	c.INLO_NO				as INLO_NO		<br>				, a.PEAK				as PEAK_EPWR<br>				, b.ENG_DTM				as ENG_DTM<br>		from	KEPCO_PP_EPWR_PRICE 	a<br>				, KEPCO_PP_EPWR_AMT 	b<br>				, FE_EPWR_INLO			c<br>		where	a.KEPCO_PP_ID		= b.KEPCO_PP_ID<br>		and		a.PAY_YYYYMM 		= $yyyymm<br>		and		b.DTM_TYPE			= '15M'<br>		and		b.ENG_DTM 			between $startDtm and $endDtm<br>		and		c.KEPCO_PP_ID		= b.KEPCO_PP_ID<br>		and		TRUNCATE(b.ACT_PW_AMT * 4, 0) = a.PEAK<br><br> <br>
*/
public final String select_peak_epwr_dtm = "select_peak_epwr_dtm";

/**
* para : $yyyymm, $yyyymm, $yyyymm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_EPWR_CHARGE ( INLO_NO, YYYY_MM, BEST_EPWR_PRICE_ID, BEST_PEAK_USE_EPWR, PEAK_SAVING_CHARGE, REG_USER_NO, REG_DTM, CHG_USER_NO, CHG_DTM )<br>		with lastdata as (<br>			select 	a.INLO_NO<br>					, max(EXE_DTM) as EXE_DTM <br>			from 	CEMS_ELEC_BILL_PLAN a<br>			where 	EXE_DTM BETWEEN concat($yyyymm, '00') and concat($yyyymm, '31') <br>			group by <br>			a.INLO_NO<br>		) <br>		, datas as (<br>			select 	a.INLO_NO				as INLO_NO<br>					, $yyyymm				as YYYY_MM<br>					, b.BEST_EPWR_PRICE_ID	as BEST_EPWR_PRICE_ID<br>					, b.BEST_PEAK_USE_EPWR 	as BEST_PEAK_USE_EPWR<br>					, b.PEAK_SAVING_CHARGE 	as PEAK_SAVING_CHARGE<br>					, 1						as REG_USER_NO		<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>											as REG_DTM<br>					, 1						as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>											as CHG_DTM<br>			from	FE_EPWR_INLO a<br>					, CEMS_ELEC_BILL_PLAN b<br>					, lastdata c<br>			where	a.INLO_NO 	= b.INLO_NO<br>			and		b.INLO_NO	= c.INLO_NO			<br>			and		b.EXE_DTM 	= c.EXE_DTM<br>		)<br>		select 	a.*<br>		from 	datas a<br>		on duplicate key update			<br>			BEST_EPWR_PRICE_ID 		= a.BEST_EPWR_PRICE_ID<br>			, BEST_PEAK_USE_EPWR 	= a.BEST_PEAK_USE_EPWR<br>			, PEAK_SAVING_CHARGE 	= a.PEAK_SAVING_CHARGE<br>			, CHG_DTM				= a.CHG_DTM<br><br> <br>
*/
public final String update_BEST_INFO = "update_BEST_INFO";

/**
* para : $yyyymm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_EPWR_CHARGE ( <br>				INLO_NO<br>				, YYYY_MM<br>				, KEPCO_USED_AMT<br>				, KEPCO_CHARGE<br>				, KEPCO_CHARGE_APPLY_EPWR<br>				, KEPCO_BASIC_CHARGE<br>				, KEPCO_ETC_CHARGE<br>				, KEPCO_USED_CHARGE<br>				, KEPCO_LAGGING_PF<br>				, KEPCO_LEADING_PF<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM <br>		)<br>		with datas as (<br>			select 	a.INLO_NO			as INLO_NO<br>					, b.PAY_YYYYMM		as YYYY_MM<br>					, b.TOTAL_ENERGY	as KEPCO_USED_AMT<br>					, b.TOTAL_PRICE 	as KEPCO_CHARGE<br>					, b.PEAK			as KEPCO_CHARGE_APPLY_EPWR<br>					, b.BASIC_PRICE		as KEPCO_BASIC_CHARGE<br>					, b.ETC_PRICE		as KEPCO_ETC_CHARGE<br>					, b.ENERGY_PRICE	as KEPCO_USED_CHARGE<br>					, b.LAGGING_PF		as KEPCO_LAGGING_PF<br>					, b.LEADING_PF		as KEPCO_LEADING_PF			<br>					, 1					as REG_USER_NO		<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>										as REG_DTM<br>					, 1					as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>										as CHG_DTM<br>			from	FE_EPWR_INLO a<br>					, KEPCO_PP_EPWR_PRICE b<br>			where	a.KEPCO_PP_ID 	= b.KEPCO_PP_ID<br>			and		b.PAY_YYYYMM	= $yyyymm<br>		)<br>		select 	a.*<br>		from 	datas a<br>		on duplicate key update			<br>			KEPCO_USED_AMT 				= a.KEPCO_USED_AMT<br>			, KEPCO_CHARGE 				= a.KEPCO_CHARGE<br>			, KEPCO_CHARGE_APPLY_EPWR 	= a.KEPCO_CHARGE_APPLY_EPWR<br>			, KEPCO_BASIC_CHARGE		= a.KEPCO_BASIC_CHARGE<br>			, KEPCO_ETC_CHARGE			= a.KEPCO_ETC_CHARGE<br>			, KEPCO_USED_CHARGE			= a.KEPCO_USED_CHARGE<br>			, KEPCO_LAGGING_PF			= a.KEPCO_LAGGING_PF<br>			, KEPCO_LEADING_PF			= a.KEPCO_LEADING_PF<br>			, CHG_DTM					= a.CHG_DTM<br><br> <br>
*/
public final String update_KEPCO_CHARGE = "update_KEPCO_CHARGE";

/**
* para : $engDtm, $inloNo, $yyyyMm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update FE_EPWR_CHARGE <br>		set<br>				KEPCO_CHARGE_APPLY_DTM = $engDtm<br>				, CHG_USER_NO = 11<br>				, CHG_DTM = DATE_FORMAT(now(), '%Y%m%d%H%i%s') <br>		where	INLO_NO		= $inloNo<br>		and		YYYY_MM		= $yyyyMm<br><br> <br>
*/
public final String update_charge_apply_dtm = "update_charge_apply_dtm";

}