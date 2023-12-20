package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/EpwrChargeLoad.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class EpwrChargeLoadQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/EpwrChargeLoad.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/EpwrChargeLoad.xml";

public EpwrChargeLoadQid() { 
} 
/**
* para : $yyyymm, $yyyymm, $yyyymm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_EPWR_CHARGE_LOAD       ' 전기요금부하테이블 '<br>		(<br>			  INLO_NO                   ' 설치위치번호 ' <br>			, YYYY_MM                   ' 년월 ' <br>			, EPWR_TM_LOAD_CD           ' 전력부하시간코드 ' <br>			, USED_AMT                  ' 사용량(kWh) ' <br>			, USED_CHARGE               ' 사용요금 ' <br>			, PEAK_USE_EPWR             ' 최대사용전력(kWh) ' <br>			, REG_DESCR					' 등록설명 '<br>			, REG_USER_NO               ' 등록사용자번호 ' <br>			, REG_DTM                   ' 등록일시 ' <br>			, CHG_USER_NO               ' 수정사용자번호 ' <br>			, CHG_DTM                   ' 수정일시 ' <br>		)<br>		with datas as (<br>			select 	a.INLO_NO				as INLO_NO <br>					, $yyyymm				as YYYY_MM<br>					, a.EPWR_TM_LOAD_CD		as EPWR_TM_LOAD_CD<br>					, sum(USED_AMT)			as USED_AMT<br>					, sum(USED_CHARGE)		as USED_CHARGE<br>					, max(a.PEAK_USE_EPWR)	as PEAK_USE_EPWR			<br>					, count(1)				as CNT<br>					, max(a.YYYYMMDDHH)		as YYYYMMDDHH		<br>			from 	FE_EPWR_CHARGE_TIME a<br>			where 	a.YYYYMMDDHH >= concat($yyyymm, '0100')<br>			and 	a.YYYYMMDDHH <= concat($yyyymm, '3124')<br>			group by a.INLO_NO<br>					, a.EPWR_TM_LOAD_CD<br>		)<br>		, a as ( <br>			select <br>					a.INLO_NO								as INLO_NO<br>					, a.YYYY_MM								as YYYY_MM<br>					, a.EPWR_TM_LOAD_CD						as EPWR_TM_LOAD_CD<br>					, a.USED_AMT							as USED_AMT<br>					, a.USED_CHARGE							as USED_CHARGE<br>					, a.PEAK_USE_EPWR						as PEAK_USE_EPWR<br>					, concat('1H datas=', CNT, ', yyyymmddhh=', YYYYMMDDHH)<br>															as REG_DESCR					<br>					, 1										as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	as REG_DTM<br>					, 1										as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	as CHG_DTM<br>			from datas a<br>		)<br>		select 	*<br>		from 	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				USED_AMT			= a.USED_AMT<br>				, USED_CHARGE		= a.USED_CHARGE<br>				, PEAK_USE_EPWR		= a.PEAK_USE_EPWR<br>				, REG_DESCR			= a.REG_DESCR<br><br> <br>
*/
public final String make_epwr_charge_load = "make-epwr-charge-load";

}