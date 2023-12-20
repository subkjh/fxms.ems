package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/EngMeasrAmtInlo.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class EngMeasrAmtInloQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/EngMeasrAmtInlo.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/EngMeasrAmtInlo.xml";

public EngMeasrAmtInloQid() { 
} 
/**
* para : $engDtm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_AMT_INLO (<br>				ENG_DTM<br>				, DTM_TYPE<br>				, ENG_ID<br>				, INLO_NO<br>				, CONS_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with datas as (<br>			select 	a.ENG_DTM			as ENG_DTM<br>					, '15M'				as DTM_TYPE<br>					, 'E13'				as ENG_ID<br>					, b.INLO_NO			as INLO_NO<br>					, a.ACT_PW_AMT		as CONS_AMT<br>					, 1					as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as REG_DTM<br>					, 1					as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as CHG_DTM				<br>			from	KEPCO_PP_EPWR_AMT 	a<br>					, FE_EPWR_INLO 		b<br>			where 	a.KEPCO_PP_ID 	= b.KEPCO_PP_ID<br>			and		a.ENG_DTM		= $engDtm <br>			and		a.DTM_TYPE		= '15M'<br>		)<br>		select 	*<br>		from 	datas a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				CONS_AMT 		= a.CONS_AMT<br>				, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String make_E13_energy_amt_inlo = "make-E13-energy-amt-inlo";

/**
* para : $measrDtmStart, $measrDtmEnd, $measrDtmStart, $dtmType<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_AMT_INLO (<br>				ENG_DTM<br>				, DTM_TYPE<br>				, ENG_ID<br>				, INLO_NO<br>				, CONS_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 'E14'				as ENG_ID<br>					, b.INLO_NO			as INLO_NO<br>					, sum(a.DIFF_VAL)	as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_TYPE			= '전력계'<br>			group by <br>					b.INLO_NO<br>		)<br>		select 	 $measrDtmStart			as ENG_DTM<br>				, $dtmType				as DTM_TYPE<br>				, a.ENG_ID				as ENG_ID<br>				, a.INLO_NO				as INLO_NO<br>				, a.CONS_AMT			as CONS_AMT<br>				, 1						as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as REG_DTM<br>				, 1						as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as CHG_DTM				<br>		from	DATAS 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				CONS_AMT 		= a.CONS_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_E14_energy_amt_inlo = "make-E14-energy-amt-inlo";

/**
* para : $measrDtmStart, $measrDtmEnd<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_AMT_INLO (<br>				ENG_DTM<br>				, DTM_TYPE<br>				, ENG_ID<br>				, INLO_NO<br>				, CONS_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select	a.ENG_DTM				as ENG_DTM<br>					, a.DTM_TYPE			as DTM_TYPE<br>					, 'E41'					as ENG_ID<br>					, a.INLO_NO				as INLO_NO<br>					, a.CONS_AMT * 0.000229	as CONS_AMT<br>			from	FE_ENG_MEASR_AMT_INLO 	a<br>			where	a.ENG_DTM			>= $measrDtmStart<br>			and		a.ENG_DTM			<= $measrDtmEnd<br>			and		a.ENG_ID			= 'E11'<br>		)<br>		select 	a.ENG_DTM			as ENG_DTM<br>				, a.DTM_TYPE		as DTM_TYPE<br>				, a.ENG_ID			as ENG_ID<br>				, a.INLO_NO			as INLO_NO<br>				, a.CONS_AMT		as CONS_AMT<br>				, 1					as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>									as REG_DTM<br>				, 1					as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>									as CHG_DTM				<br>		from	DATAS 	a								<br>		on duplicate key update		<br>				CONS_AMT 		= a.CONS_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_E41_energy_amt_inlo = "make-E41-energy-amt-inlo";

/**
* para : $measrDtmStart, $measrDtmEnd<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_AMT_INLO (<br>				ENG_DTM<br>				, DTM_TYPE<br>				, ENG_ID<br>				, INLO_NO<br>				, CONS_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select	a.ENG_DTM				as ENG_DTM<br>					, a.DTM_TYPE			as DTM_TYPE<br>					, 'E43'					as ENG_ID<br>					, a.INLO_NO				as INLO_NO<br>					, a.CONS_AMT * 0.000229	as CONS_AMT<br>			from	FE_ENG_MEASR_AMT_INLO 	a<br>			where	a.ENG_DTM			>= $measrDtmStart<br>			and		a.ENG_DTM			<= $measrDtmEnd<br>			and		a.ENG_ID			= 'E13'<br>		)<br>		select 	a.ENG_DTM			as ENG_DTM<br>				, a.DTM_TYPE		as DTM_TYPE<br>				, a.ENG_ID			as ENG_ID<br>				, a.INLO_NO			as INLO_NO<br>				, a.CONS_AMT		as CONS_AMT<br>				, 1					as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>									as REG_DTM<br>				, 1					as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>									as CHG_DTM				<br>		from	DATAS 	a								<br>		on duplicate key update		<br>				CONS_AMT 		= a.CONS_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_E43_energy_amt_inlo = "make-E43-energy-amt-inlo";

}