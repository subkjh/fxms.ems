package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/vup_test.xml<br>
* @since 20230614162949
* @author subkjh 
*
*/


public class VupTestQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/vup/vup_test.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/vup/vup_test.xml";

public VupTestQid() { 
} 
/**
* para : $startDate, $endDate, $date<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_AMT (<br>			CONS_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, CONS_AMT, EXP_CONS_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select	a.INLO_NO<br>					, a.ENG_ID	<br>					, a.DTM_TYPE<br>					, SUBSTRING(a.CONS_DTM, 9)		as HMS<br>					, count(1)						as COUNT<br>					, truncate(AVG(a.CONS_AMT), 1)	as EXP_CONS_AMT<br>			from	FE_ENG_CONS_AMT a<br>			where	a.CONS_DTM	>= $startDate<br>			and		a.CONS_DTM	<= $endDate<br>			and		a.INLO_NO > 0			<br>			group by a.INLO_NO<br>					, a.ENG_ID	<br>					, a.DTM_TYPE<br>					, SUBSTRING(a.CONS_DTM, 9)<br>		)<br>		select 	concat($date, a.HMS)	as CONS_DTM<br>				, a.DTM_TYPE				as DTM_TYPE<br>				, -1						as FAC_NO<br>				, a.ENG_ID					as ENG_ID<br>				, a.INLO_NO					as INLO_NO<br>				, null						as CONS_AMT<br>				, a.EXP_CONS_AMT			as EXP_CONS_AMT<br>				, 0							as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>											as REG_DTM<br>				, 0							as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s') <br>											as CHG_DTM<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			EXP_CONS_AMT 		= a.EXP_CONS_AMT	<br>			, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_cons_exp_amt = "make-cons-exp-amt";

/**
* para : $startDate, $endDate, $date<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_AMT (<br>			PROD_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, PROD_AMT, EXP_PROD_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select	a.INLO_NO<br>					, a.ENG_ID	<br>					, a.DTM_TYPE<br>					, SUBSTRING(a.PROD_DTM, 9)		as HMS<br>					, count(1)						as COUNT<br>					, truncate(AVG(a.PROD_AMT), 1)	as EXP_PROD_AMT<br>			from	FE_ENG_PROD_AMT a<br>			where	a.PROD_DTM	>= $startDate<br>			and		a.PROD_DTM	<= $endDate<br>			and		a.INLO_NO > 0<br>			group by a.INLO_NO<br>					, a.ENG_ID	<br>					, a.DTM_TYPE<br>					, SUBSTRING(a.PROD_DTM, 9)<br>		)<br>		select 	concat($date, a.HMS)	as PROD_DTM<br>				, a.DTM_TYPE				as DTM_TYPE<br>				, -1						as FAC_NO<br>				, a.ENG_ID					as ENG_ID<br>				, a.INLO_NO					as INLO_NO<br>				, null						as PROD_AMT<br>				, a.EXP_PROD_AMT			as EXP_PROD_AMT<br>				, 0							as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>											as REG_DTM<br>				, 0							as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s') <br>											as CHG_DTM<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			EXP_PROD_AMT 		= a.EXP_PROD_AMT	<br>			, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_prod_exp_amt = "make-prod-exp-amt";

}