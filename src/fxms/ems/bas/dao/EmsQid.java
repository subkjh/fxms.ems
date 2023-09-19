package fxms.ems.bas.dao;

/**
* File : deploy/conf/sql/fxms/ems/ems.xml<br>
* @since 20230803140742
* @author subkjh 
*
*/


public class EmsQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/ems.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/ems.xml";

public EmsQid() { 
} 
/**
* para : $psDate<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_AMT (<br>			CONS_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, EXP_CONS_AMT, CONS_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select 	a.CONS_DTM					as CONS_DTM<br>					, 'M15'						as DTM_TYPE<br>					, -1 						as FAC_NO<br>					, a.ENG_ID 					as ENG_ID<br>					, a.INLO_NO 				as INLO_NO<br>					, 0							as EXP_CONS_AMT<br>					, truncate(a.DIFF_VAL, 2)   as CONS_AMT<br>					, 0							as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>												as REG_DTM<br>					, 0							as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>												as CHG_DTM			<br>			from 	FE_ENG_CONS_RAW a<br>			where	a.CONS_DTM	= $psDate<br>		)<br>		select 	a.*<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			CONS_AMT 		= a.CONS_AMT	<br>			, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String insert_cons_amt_m15_from_raw = "insert-cons-amt-m15-from-raw";

/**
* para : $psDate<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_AMT (<br>			PROD_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, EXP_PROD_AMT, PROD_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select 	a.PROD_DTM					as PROD_DTM<br>					, 'M15'						as DTM_TYPE<br>					, -1 						as FAC_NO<br>					, a.ENG_ID 					as ENG_ID<br>					, a.INLO_NO 				as INLO_NO<br>					, 0							as EXP_PROD_AMT<br>					, truncate(a.DIFF_VAL, 2)   as PROD_AMT<br>					, 0							as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>												as REG_DTM<br>					, 0							as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>												as CHG_DTM			<br>			from 	FE_ENG_PROD_RAW a<br>			where	a.PROD_DTM	= $psDate<br>		)<br>		select 	a.*<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			PROD_AMT 		= a.PROD_AMT	<br>			, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String insert_prod_amt_m15_from_raw = "insert-prod-amt-m15-from-raw";

/**
* para : $dateHh, $dateHh, $dateHh<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_AMT (<br>			CONS_DTM<br>			, DTM_TYPE<br>			, FAC_NO<br>			, ENG_ID<br>			, INLO_NO<br>			, EXP_CONS_AMT<br>			, CONS_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with <br>			ROWDATAS as (<br>				select	concat($dateHh, '0000')		as CONS_DTM<br>						, a.FAC_NO				as FAC_NO 		' 설비번호 '<br>						, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>						, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>						, truncate(sum(a.EXP_CONS_AMT), 1)<br>												as EXP_CONS_AMT 		' 예상생산량 '<br>						, truncate(sum(a.CONS_AMT), 1)<br>												as CONS_AMT 		' 생산량 '<br>				from 	FE_ENG_CONS_AMT a 		' 에너지생산량테이블 '<br>				where	a.CONS_DTM >= concat($dateHh, '0000')<br>				and		a.CONS_DTM <= concat($dateHh, '5959')<br>				and		a.DTM_TYPE	= 'M15'<br>				group by  <br>					a.FAC_NO<br>					, a.ENG_ID<br>					, a.INLO_NO	<br>			)<br>			select<br>					  a.CONS_DTM		as CONS_DTM<br>					, 'H1'				as DTM_TYPE<br>					, a.FAC_NO<br>					, a.ENG_ID<br>					, a.INLO_NO<br>					, a.EXP_CONS_AMT<br>					, a.CONS_AMT<br>					, 1											as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as REG_DTM<br>					, 1											as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as CHG_DTM<br>			from	ROWDATAS	a<br>			<br>			ON DUPLICATE KEY UPDATE		<br>					EXP_CONS_AMT 	= a.EXP_CONS_AMT<br>					, CONS_AMT		= a.CONS_AMT<br>					, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_cons_h1_from_m15 = "make-cons-h1-from-m15";

/**
* para : $measrDtm, $measrDtmStart, $measrDtmEnd<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_RAW (<br>			CONS_DTM<br>			, INLO_NO<br>			, ENG_ID<br>			, INST_VAL<br>			, PREV_INTG_VAL<br>			, CUR_INTG_VAL<br>			, DIFF_VAL<br>			, PRES_VAL<br>			, TEMP_VAL<br>			, MEMO<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 $measrDtm						as CONS_DTM<br>					, b.INLO_NO						as INLO_NO<br>					, a.ENG_ID						as ENG_ID<br>					, max(a.INST_VAL)				as INST_VAL<br>					, max(a.PREV_INTG_VAL)			as PREV_INTG_VAL<br>					, max(a.CUR_INTG_VAL)			as CUR_INTG_VAL<br>					, sum(a.DIFF_VAL)				as DIFF_VAL<br>					, avg(a.PRES_VAL)				as PRES_VAL<br>					, avg(a.TEMP_VAL)				as TEMP_VAL<br>					, 'FE_ENG_MEASR_RAW'			as MEMO<br>					, 1											as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as REG_DTM<br>					, 1											as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as CHG_DTM				<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO 			b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		!= 'OUT'<br>			group by <br>					b.INLO_NO<br>					, a.ENG_ID<br>		)<br>		select	*<br>		from	DATAS 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				INST_VAL 		= a.INST_VAL<br>				, PREV_INTG_VAL	= a.PREV_INTG_VAL<br>				, CUR_INTG_VAL	= a.CUR_INTG_VAL<br>				, DIFF_VAL		= a.DIFF_VAL<br>				, PRES_VAL		= a.PRES_VAL<br>				, TEMP_VAL		= a.TEMP_VAL<br>				, MEMO			= a.MEMO<br>				, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String make_cons_raw_from_energy_raw = "make-cons-raw-from-energy-raw";

/**
* para : $date, $date, $date<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_STAT (<br>			CONS_DATE<br>			, FAC_NO<br>			, ENG_ID<br>			, INLO_NO<br>			, EXP_CONS_AMT<br>			, CONS_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with <br>		ROWDATAS as (<br>			select	a.FAC_NO				as FAC_NO 		' 설비번호 '<br>					, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>					, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>					, truncate(sum(a.EXP_CONS_AMT), 1)<br>											as EXP_CONS_AMT 		' 예상생산량 '<br>					, truncate(SUM(a.CONS_AMT), 1)<br>											as CONS_AMT 		' 생산량 '<br>			from 	FE_ENG_CONS_AMT a 		' 에너지생산량테이블 '<br>			where	a.CONS_DTM >= concat($date, '000000')<br>			and		a.CONS_DTM <= concat($date, '235959')<br>			and		a.DTM_TYPE	= 'H1'<br>			group by  <br>				a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO	<br>		)<br>		select<br>				$date				as CONS_DATE<br>				, a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.EXP_CONS_AMT<br>				, a.CONS_AMT<br>				, 1											as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as REG_DTM<br>				, 1											as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as CHG_DTM<br>		from	ROWDATAS	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				EXP_CONS_AMT 	= a.EXP_CONS_AMT<br>				, CONS_AMT		= a.CONS_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_cons_stat_from_h1 = "make-cons-stat-from-h1";

/**
* para : $measrDtmStart, $measrDtmEnd, $measrDtmStart, $measrDtmEnd, $measrDtmStart, $dtmType<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_AMT_FAC (<br>				ENG_DTM<br>				, DTM_TYPE<br>				, ENG_ID<br>				, FAC_NO<br>				, CONS_AMT<br>				, PROD_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 a.ENG_ID			as ENG_ID<br>					, c.FAC_NO			as FAC_NO<br>					, sum(a.DIFF_VAL)	as PROD_AMT<br>					, 0					as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		= 'OUT'<br>			and		c.FAC_NO			is not null		<br>					<br>			group by <br>					a.ENG_ID<br>					, c.FAC_NO<br>			<br>			union all					<br>					<br>			select	a.ENG_ID			as ENG_ID<br>					, c.FAC_NO			as FAC_NO<br>					, 0					as PROD_AMT<br>					, sum(a.DIFF_VAL)	as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		!= 'OUT'<br>			and		c.FAC_NO			is not null		<br>			group by <br>					a.ENG_ID<br>					, c.FAC_NO<br>		)<br>		select 	 $measrDtmStart			as ENG_DTM<br>				, $dtmType				as DTM_TYPE<br>				, a.ENG_ID				as ENG_ID<br>				, a.FAC_NO				as FAC_NO<br>				, a.CONS_AMT			as CONS_AMT<br>				, a.PROD_AMT			as PROD_AMT<br>				, 1						as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as REG_DTM<br>				, 1						as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as CHG_DTM				<br>		from	DATAS 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				CONS_AMT 		= a.CONS_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_energy_raw_to_fac = "make-energy-raw-to-fac";

/**
* para : $measrDtmStart, $measrDtmEnd, $measrDtmStart, $measrDtmEnd, $engDate<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_STAT_FAC (<br>				ENG_DATE<br>				, ENG_ID<br>				, FAC_NO<br>				, CONS_AMT<br>				, PROD_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 a.ENG_ID			as ENG_ID<br>					, c.FAC_NO			as FAC_NO<br>					, sum(a.DIFF_VAL)	as PROD_AMT<br>					, 0					as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		= 'OUT'<br>			and		c.FAC_NO			is not null		<br>					<br>			group by <br>					a.ENG_ID<br>					, c.FAC_NO<br>			<br>			union all					<br>					<br>			select	a.ENG_ID			as ENG_ID<br>					, c.FAC_NO			as FAC_NO<br>					, 0					as PROD_AMT<br>					, sum(a.DIFF_VAL)	as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		!= 'OUT'<br>			and		c.FAC_NO			is not null		<br>			group by <br>					a.ENG_ID<br>					, c.FAC_NO<br>		)<br>		select 	 $engDate				as ENG_DATE<br>				, a.ENG_ID				as ENG_ID<br>				, a.FAC_NO				as FAC_NO<br>				, a.CONS_AMT			as CONS_AMT<br>				, a.PROD_AMT			as PROD_AMT<br>				, 1						as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as REG_DTM<br>				, 1						as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as CHG_DTM				<br>		from	DATAS 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				CONS_AMT 		= a.CONS_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_energy_raw_to_fac_stat = "make-energy-raw-to-fac-stat";

/**
* para : $measrDtmStart, $measrDtmEnd, $measrDtmStart, $measrDtmEnd, $measrDtmStart, $dtmType<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_AMT_INLO (<br>				ENG_DTM<br>				, DTM_TYPE<br>				, ENG_ID<br>				, INLO_NO<br>				, CONS_AMT<br>				, PROD_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 a.ENG_ID			as ENG_ID<br>					, b.INLO_NO			as INLO_NO<br>					, sum(a.DIFF_VAL)	as PROD_AMT<br>					, 0					as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		= 'OUT'<br>			and		c.FAC_NO			is null		<br>			group by <br>					a.ENG_ID<br>					, b.INLO_NO<br>			<br>			union all					<br>					<br>			select 	 a.ENG_ID			as ENG_ID<br>					, b.INLO_NO			as INLO_NO<br>					, 0					as PROD_AMT<br>					, sum(a.DIFF_VAL)	as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		!= 'OUT'<br>			and		c.FAC_NO			is null		<br>			group by <br>					a.ENG_ID<br>					, b.INLO_NO<br>		)<br>		select 	 $measrDtmStart			as ENG_DTM<br>				, $dtmType				as DTM_TYPE<br>				, a.ENG_ID				as ENG_ID<br>				, a.INLO_NO				as INLO_NO<br>				, a.CONS_AMT			as CONS_AMT<br>				, a.PROD_AMT			as PROD_AMT<br>				, 1						as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as REG_DTM<br>				, 1						as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as CHG_DTM				<br>		from	DATAS 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				CONS_AMT 		= a.CONS_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_energy_raw_to_inlo = "make-energy-raw-to-inlo";

/**
* para : $measrDtmStart, $measrDtmEnd, $measrDtmStart, $measrDtmEnd, $engDate<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_STAT_INLO (<br>				ENG_DATE<br>				, ENG_ID<br>				, INLO_NO<br>				, CONS_AMT<br>				, PROD_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 a.ENG_ID			as ENG_ID<br>					, b.INLO_NO			as INLO_NO<br>					, sum(a.DIFF_VAL)	as PROD_AMT<br>					, 0					as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		= 'OUT'<br>			and		c.FAC_NO			is null		<br>			group by <br>					a.ENG_ID<br>					, b.INLO_NO<br>			<br>			union all					<br>					<br>			select 	 a.ENG_ID			as ENG_ID<br>					, b.INLO_NO			as INLO_NO<br>					, 0					as PROD_AMT<br>					, sum(a.DIFF_VAL)	as CONS_AMT<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO				b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		!= 'OUT'<br>			and		c.FAC_NO			is null		<br>			group by <br>					a.ENG_ID<br>					, b.INLO_NO<br>		)<br>		select 	 $engDate				as ENG_DATE<br>				, a.ENG_ID				as ENG_ID<br>				, a.INLO_NO				as INLO_NO<br>				, a.CONS_AMT			as CONS_AMT<br>				, a.PROD_AMT			as PROD_AMT<br>				, 1						as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as REG_DTM<br>				, 1						as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>										as CHG_DTM				<br>		from	DATAS 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				CONS_AMT 		= a.CONS_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_energy_raw_to_inlo_stat = "make-energy-raw-to-inlo-stat";

/**
* para : $dateHh, $dateHh, $dateHh<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_AMT (<br>				PROD_DTM<br>				, DTM_TYPE<br>				, FAC_NO<br>				, ENG_ID<br>				, INLO_NO<br>				, EXP_PROD_AMT<br>				, PROD_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with <br>		ROWDATAS as (<br>			select	concat($dateHh, '0000')	as PROD_DTM<br>					, a.FAC_NO				as FAC_NO 		' 설비번호 '<br>					, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>					, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>					, truncate(sum(a.EXP_PROD_AMT), 1)	<br>											as EXP_PROD_AMT 		' 예상생산량 '<br>					, truncate(sum(a.PROD_AMT), 1)<br>											as PROD_AMT 		' 생산량 '<br>			from 	FE_ENG_PROD_AMT a 		' 에너지생산량테이블 '<br>			where	a.PROD_DTM 	>= concat($dateHh, '0000')<br>			and		a.PROD_DTM 	<= concat($dateHh, '5959')<br>			and		a.DTM_TYPE	= 'M15'<br>			group by  <br>				a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO	<br>		)<br>		select<br>				  a.PROD_DTM<br>				, 'H1'				as DTM_TYPE<br>				, a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.EXP_PROD_AMT<br>				, a.PROD_AMT<br>				, 1											as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as REG_DTM<br>				, 1											as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as CHG_DTM<br>		from	ROWDATAS	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				EXP_PROD_AMT 	= a.EXP_PROD_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM			= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_prod_h1_from_m15 = "make-prod-h1-from-m15";

/**
* para : $measrDtm, $measrDtmStart, $measrDtmEnd<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_RAW (<br>			PROD_DTM<br>			, INLO_NO<br>			, ENG_ID<br>			, INST_VAL<br>			, PREV_INTG_VAL<br>			, CUR_INTG_VAL<br>			, DIFF_VAL<br>			, PRES_VAL<br>			, TEMP_VAL<br>			, MEMO<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 $measrDtm						as PROD_DTM<br>					, b.INLO_NO						as INLO_NO<br>					, a.ENG_ID						as ENG_ID<br>					, max(a.INST_VAL)				as INST_VAL<br>					, max(a.PREV_INTG_VAL)			as PREV_INTG_VAL<br>					, max(a.CUR_INTG_VAL)			as CUR_INTG_VAL<br>					, sum(a.DIFF_VAL)				as DIFF_VAL<br>					, avg(a.PRES_VAL)				as PRES_VAL<br>					, avg(a.TEMP_VAL)				as TEMP_VAL<br>					, 'FE_ENG_MEASR_RAW'			as MEMO<br>					, 1											as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as REG_DTM<br>					, 1											as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as CHG_DTM				<br>			from	FE_ENG_MEASR_RAW 	a<br>					, FX_MO 			b<br>					, FE_MO_SENSOR		c<br>			where	a.MEASR_DTM			>= $measrDtmStart<br>			and		a.MEASR_DTM			<= $measrDtmEnd<br>			and		a.MO_NO 			= b.MO_NO<br>			and		b.MO_NO				= c.MO_NO<br>			and		c.IN_OUT_TAG		= 'OUT'<br>			group by <br>					b.INLO_NO<br>					, a.ENG_ID<br>		)<br>		select	*<br>		from	DATAS 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				INST_VAL 		= a.INST_VAL<br>				, PREV_INTG_VAL	= a.PREV_INTG_VAL<br>				, CUR_INTG_VAL	= a.CUR_INTG_VAL<br>				, DIFF_VAL		= a.DIFF_VAL<br>				, PRES_VAL		= a.PRES_VAL<br>				, TEMP_VAL		= a.TEMP_VAL<br>				, MEMO			= a.MEMO<br>				, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String make_prod_raw_from_energy_raw = "make-prod-raw-from-energy-raw";

/**
* para : $date, $date, $date<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_STAT (<br>			PROD_DATE<br>			, FAC_NO<br>			, ENG_ID<br>			, INLO_NO<br>			, EXP_PROD_AMT<br>			, PROD_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with <br>		ROWDATAS as (<br>			select	a.FAC_NO				as FAC_NO 		' 설비번호 '<br>					, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>					, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>					, truncate(sum(a.EXP_PROD_AMT), 1)	<br>											as EXP_PROD_AMT 		' 예상생산량 '<br>					, truncate(sum(a.PROD_AMT), 1)<br>											as PROD_AMT 		' 생산량 '<br>			from 	FE_ENG_PROD_AMT a 		' 에너지생산량테이블 '<br>			where	a.PROD_DTM 	>= concat($date, '000000')<br>			and		a.PROD_DTM 	<= concat($date, '235959')<br>			and		a.DTM_TYPE	= 'H1'<br>			group by  <br>				a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO	<br>		)<br>		select<br>				$date			as PROD_DATE<br>				, a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.EXP_PROD_AMT<br>				, a.PROD_AMT<br>				, 1											as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as REG_DTM<br>				, 1											as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as CHG_DTM<br>		from	ROWDATAS	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				EXP_PROD_AMT 	= a.EXP_PROD_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM			= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_prod_stat_from_h1 = "make-prod-stat-from-h1";

/**
* para : $measrDtmStart, $measrDtmEnd<br>
* result : RESULT_FE_ENG_TRANS_CALC_DTL=fxms.ems.bas.dbo.FE_ENG_TRANS_CALC_DTL<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>with DATAS as (<br>			select 	a.TRNS_NO<br>					, a.ENG_ID<br>					, a.INLO_NO<br>					, a.SELL_INLO_NO<br>					, a.BUY_INLO_NO<br>					, b.ENG_RT_ID<br>					, c.SENSOR_MO_NO<br>			from 	FE_ENG_TRANS_BAS 	a<br>					, FE_ENG_TRANS_RT	b<br>					, FE_ENG_RT_BAS		c<br>			where	a.TRNS_ST_CD 		= '1' ' 계약유지 '<br>			and		b.TRNS_NO			= a.TRNS_NO<br>			and		b.ENG_RT_USE_YN		= 'Y'<br>			and		c.ENG_RT_ID			= b.ENG_RT_ID		<br>			and		c.SENSOR_MO_NO		> 0<br>		)		<br>		select 	a.TRNS_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.SELL_INLO_NO<br>				, a.BUY_INLO_NO<br>				, a.ENG_RT_ID<br>				, a.SENSOR_MO_NO				<br>				, min(MEASR_DTM)		as STRT_MEASR_DTM<br>				, min(CUR_INTG_VAL)		as STRT_MEASR_VAL<br>				, max(MEASR_DTM)		as FNSH_MEASR_DTM<br>				, max(CUR_INTG_VAL)		as FNSH_MEASR_VAL<br>				, sum(b.DIFF_VAL)		as TRNS_VOL<br>		from	DATAS				a<br>				, FE_ENG_MEASR_RAW	b<br>		where	a.SENSOR_MO_NO		= b.MO_NO<br>		and		b.MEASR_DTM			>= $measrDtmStart<br>		and		b.MEASR_DTM			<= $measrDtmEnd<br>		group by <br>				a.TRNS_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.SELL_INLO_NO<br>				, a.BUY_INLO_NO<br>				, a.ENG_RT_ID<br>				, a.SENSOR_MO_NO<br><br> <br>
*/
public final String select_calculate_amount = "select-calculate-amount";

/**
* para : $inloClCd<br>
* result : RESULT_JAVA_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>with datas as (<br>			select 	a.INLO_NO		<br>					, ( select	 max(T.INLO_NO)<br>						from 	FX_CF_INLO_MEM 	T2<br>								, FX_CF_INLO 	T<br>						where 	T2.LOWER_INLO_NO 	= a.INLO_NO<br>						and		T.INLO_NO 			= T2.INLO_NO<br>						and     T.INLO_CL_CD 		= 'COMPLEX' )<br>	<br>								as COMPLEX_INLO_NO<br>	<br>					, ( select	 max(T.INLO_NO)<br>						from 	FX_CF_INLO_MEM 	T2<br>								, FX_CF_INLO 	T<br>						where 	T2.LOWER_INLO_NO 	= a.INLO_NO<br>						and		T.INLO_NO 			= T2.INLO_NO<br>						and     T.INLO_CL_CD 		= 'COMPANY' )<br>	<br>								as COMPANY_INLO_NO<br>	<br>					, ( select	 max(T.INLO_NO)<br>						from 	FX_CF_INLO_MEM 	T2<br>								, FX_CF_INLO 	T<br>						where 	T2.LOWER_INLO_NO 	= a.INLO_NO<br>						and		T.INLO_NO 			= T2.INLO_NO<br>						and     T.INLO_CL_CD 		= 'PLANT' )<br>	<br>								as PLANT_INLO_NO<br>	<br>			from 	FX_CF_INLO a<br>			where   a.DEL_YN = 'N'<br>		)<br>		select	inlo.*<br>				, plant.INLO_TID		as PLANT_TID<br>				, plant.INLO_NAME		as PLANT_NAME<br>				, company.INLO_TID		as COMPANY_TID<br>				, company.INLO_NAME		as COMPANY_NAME<br>				, complex.INLO_TID		as COMPLEX_TID<br>				, complex.INLO_NAME		as COMPLEX_NAME<br>				, a.PLANT_INLO_NO<br>				, a.COMPANY_INLO_NO<br>				, a.COMPLEX_INLO_NO<br>		from 	datas a<br>					left join FX_CF_INLO complex 	on complex.INLO_NO = a.COMPLEX_INLO_NO<br>					left join FX_CF_INLO company 	on company.INLO_NO = a.COMPANY_INLO_NO<br>					left join FX_CF_INLO plant 		on plant.INLO_NO = a.PLANT_INLO_NO<br>				, FX_CF_INLO inlo<br>		where	a.INLO_NO			= inlo.INLO_NO<br><br>and inlo.INLO_CL_CD = $inloClCd<br><br> <br>
*/
public final String select_factory_list = "select-factory-list";

}