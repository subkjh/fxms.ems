package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/EpwrChargeTime.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class EpwrChargeTimeQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/EpwrChargeTime.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/EpwrChargeTime.xml";

public EpwrChargeTimeQid() { 
} 
/**
* para : $date, $date<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update	FE_EPWR_CHARGE_TIME a <br>				, ( with datas as (<br>						select 	a.INLO_NO					as INLO_NO<br>								, substr(a.ENG_DTM, 1, 10) 	as YYYYMMDDHH<br>								, max(a.CONS_AMT)			as PEAK_USE_EPWR		 <br>						from 	FE_ENG_MEASR_AMT_INLO a<br>						where 	a.ENG_DTM 		between concat($date, '000000')  and concat($date, '235959') <br>						and 	a.ENG_ID 		= 'E11'	<br>						and 	a.DTM_TYPE 		= '15M'<br>						group by a.INLO_NO			<br>								, substr(a.ENG_DTM, 1, 10)<br>					)<br>					, a as ( <br>						select 	a.INLO_NO		<br>								, a.YYYYMMDDHH				as YYYYMMDDHH<br>								, a.PEAK_USE_EPWR * 4		as PEAK_USE_EPWR<br>								, max(ENG_DTM) 			 	as PEAK_USE_DTM<br>						from 	FE_ENG_MEASR_AMT_INLO t1<br>								, datas a<br>						where 	t1.ENG_DTM BETWEEN concat(a.YYYYMMDDHH, '0000') and concat(a.YYYYMMDDHH, '5959') <br>						and 	t1.ENG_ID 		= 'E11'<br>						and 	t1.DTM_TYPE 	= '15M' <br>						and 	t1.INLO_NO 		= a.INLO_NO <br>						and		t1.CONS_AMT		= a.PEAK_USE_EPWR<br>						group by a.INLO_NO<br>								, a.YYYYMMDDHH<br>								, a.PEAK_USE_EPWR<br>					)<br>					select * from a <br>				) b<br>		set		a.PEAK_USE_DTM 		= b.PEAK_USE_DTM<br>				, a.PEAK_USE_EPWR	= b.PEAK_USE_EPWR<br>				, CHG_DTM			=  DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>		where	a.INLO_NO	 = b.INLO_NO<br>		and		a.YYYYMMDDHH = b.YYYYMMDDHH<br><br> <br>
*/
public final String make_epwr_charge_peak_time = "make-epwr-charge-peak-time";

/**
* para : $date, $date, $date, $date, $date, $date, $date, $date, $date, $date<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_EPWR_CHARGE_TIME       ' 전기요금TIME테이블 '<br>		(<br>			  INLO_NO                   ' 설치위치번호 ' <br>			, YYYYMMDDHH                ' 년월일시 ' <br>			, USED_AMT                  ' 사용량(kWh) ' <br>			, EPWR_PRICE_ID             ' 전기요금ID ' <br>			, USED_PRICE                ' 사용단가 ' <br>			, USED_CHARGE               ' 사용요금 ' <br>			, EPWR_TM_LOAD_CD           ' 전력부하시간코드 ' <br>			, REG_USER_NO               ' 등록사용자번호 ' <br>			, REG_DTM                   ' 등록일시 ' <br>			, CHG_USER_NO               ' 수정사용자번호 ' <br>			, CHG_DTM                   ' 수정일시 ' <br>		)<br>		with datas as (<br>			select 	a.EPWR_PRICE_ID			as EPWR_PRICE_ID		' 전기요금ID '<br>					, a.EPWR_PRICE_NAME		as EPWR_PRICE_NAME		' 전기요금명 '<br>					, b.BASIC_PRICE			as BASIC_PRICE			' 기본요금(원/kW) '<br>					, c.DESCR				as TMLOAD_DESCR			' 부하 '<br>					, d.EPWR_TM_LOAD_CD		as EPWR_TM_LOAD_CD		' 시간대부하코드 '<br>					, ( select CD_NAME from FX_CO_CD d1 where d1.CD_CLASS = 'EPWR_TM_LOAD_CD' and d1.CD_CODE = d.EPWR_TM_LOAD_CD )<br>											as EPWR_TM_LOAD_NM		' 시간대부하명 '<br>					, d.PRICE				as UNIT_PRICE			' 단가 '<br>					, c.MMDD<br>					, c.HH<br>			from	FE_EPWR_PRICE			a<br>					, FE_EPWR_PRICE_BASE 	b<br>					, FE_EPWR_PRICE_TMLOAD 	c<br>					, FE_EPWR_PRICE_UNIT 	d<br>			where 	a.EPWR_PRICE_ID		= b.EPWR_PRICE_ID<br>			and		b.START_EFFCT_DATE	<= $date<br>			and		b.END_EFFCT_DATE	>= $date<br>			and		c.MMDD				= concat(substr($date, 5, 2), '**')			' 7월. 일자가 적용되면 ** 자리에 일자 넣음 '<br>			and		c.START_EFFCT_DATE	<= $date<br>			and		c.END_EFFCT_DATE	>= $date<br>			and		d.EPWR_PRICE_ID		= a.EPWR_PRICE_ID<br>			and		d.MM 				= substr($date, 5, 2)						' 7월 '<br>			and		d.EPWR_TM_LOAD_CD 	in ( c.EPWR_TM_LOAD_CD, '0')				' 시간대 부하가 없으면 0으로 시간부하가 설정되어 있음 '<br>			and		d.START_EFFCT_DATE	<= $date<br>			and		d.END_EFFCT_DATE	>= $date<br>		)<br>		, a as (<br>			select  c.INLO_NO						as INLO_NO<br>					, substr(b.ENG_DTM, 1, 10)		as YYYYMMDDHH<br>					, b.CONS_AMT					as USED_AMT<br>					, a.EPWR_PRICE_ID 				as EPWR_PRICE_ID<br>					, a.UNIT_PRICE					as USED_PRICE<br>					, TRUNCATE(b.CONS_AMT * a.UNIT_PRICE, 1 ) <br>													as USED_CHARGE<br>					, a.EPWR_TM_LOAD_CD				as EPWR_TM_LOAD_CD<br>			from 	datas a<br>					, FE_ENG_MEASR_AMT_INLO b<br>					, FE_EPWR_INLO_PRICE 	c<br>			where b.ENG_ID 		= 'E11' <br>			and b.ENG_DTM 		>= concat($date, '000000') <br>			and b.ENG_DTM 		<= concat($date, '235959') <br>			and b.DTM_TYPE 		= '1H'<br>			and c.INLO_NO 		= b.INLO_NO<br>			and a.EPWR_PRICE_ID = c.EPWR_PRICE_ID<br>			and substr(b.ENG_DTM, 9, 2) = a.HH<br>		)<br>		select	a.*<br>				, 1										as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	as REG_DTM<br>				, 1										as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	as CHG_DTM									<br>		from	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				USED_AMT 			= a.USED_AMT<br>				, EPWR_PRICE_ID		= a.EPWR_PRICE_ID<br>				, USED_PRICE		= a.USED_PRICE<br>				, USED_CHARGE		= a.USED_CHARGE<br>				, EPWR_TM_LOAD_CD	= a.EPWR_TM_LOAD_CD<br>				, CHG_DTM			= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_epwr_charge_time = "make-epwr-charge-time";

}