package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/vup.xml<br>
* @since 20230814180103
* @author subkjh 
*
*/


public class VupQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/vup/vup.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/vup/vup.xml";

public VupQid() { 
} 
/**
* para : $workDtm, $workDtm, $psDate<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_AMT (<br>			CONS_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, EXP_CONS_AMT, CONS_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select 	a.CONS_DTM					as CONS_DTM<br>					, 'M15'						as DTM_TYPE<br>					, -1 						as FAC_NO<br>					, a.ENG_ID 					as ENG_ID<br>					, a.INLO_NO 				as INLO_NO<br>					, 0							as EXP_CONS_AMT<br>					, truncate(a.DIFF_VAL, 2)   as CONS_AMT<br>					, 0							as REG_USER_NO<br>					, $workDtm					as REG_DTM<br>					, 0							as CHG_USER_NO<br>					, $workDtm					as CHG_DTM			<br>			from 	VUP_ENG_CONS_RAW a<br>			where	a.CONS_DTM	= $psDate<br>		)<br>		select 	a.*<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			CONS_AMT 		= a.CONS_AMT	<br>			, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String insert_cons_amt_from_vup = "insert-cons-amt-from-vup";

/**
* para : $psDateStart, $dtmType, $engId, #COLUMN, #TABLE, $psDateStart, $psDateEnd, #COLUMN<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_AMT (<br>			CONS_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, EXP_CONS_AMT , CONS_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select 	$psDateStart				as CONS_DTM<br>					, $dtmType					as DTM_TYPE<br>					, d.FAC_NO 					as FAC_NO<br>					, $engId 					as ENG_ID<br>					, c.INLO_NO 				as INLO_NO<br>					, 0							as EXP_CONS_AMT<br>					, truncate(SUM(a.#COLUMN), 1) 	as CONS_AMT<br>					, 0							as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>												as REG_DTM<br>					, 0							as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>												as CHG_DTM			<br>			from #TABLE a<br>				, FX_MO b<br>				, FX_CF_INLO c<br>				, FE_MO_SENSOR d<br>			where 	a.mo_no 	= 	b.MO_NO <br>			and 	b.inlo_no 	= 	c.inlo_no<br>			and 	d.mo_no 	= 	b.mo_no<br>			and		a.ps_date	between $psDateStart and $psDateEnd<br>			group by d.REL_FAC_NO<br>					, c.INLO_NO<br>			having SUM(a.#COLUMN) > 0<br>		)<br>		select 	a.*<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			CONS_AMT 		= a.CONS_AMT	<br>			, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String insert_epower_used = "insert-epower-used";

/**
* para : $workDtm, $workDtm, $psDate<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_AMT (<br>			PROD_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, EXP_PROD_AMT, PROD_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select 	a.PROD_DTM					as PROD_DTM<br>					, 'M15'						as DTM_TYPE<br>					, -1 						as FAC_NO<br>					, a.ENG_ID 					as ENG_ID<br>					, a.INLO_NO 				as INLO_NO<br>					, 0							as EXP_PROD_AMT<br>					, truncate(a.DIFF_VAL, 2)   as PROD_AMT<br>					, 0							as REG_USER_NO<br>					, $workDtm					as REG_DTM<br>					, 0							as CHG_USER_NO<br>					, $workDtm					as CHG_DTM			<br>			from 	VUP_ENG_PROD_RAW a<br>			where	a.PROD_DTM	= $psDate<br>		)<br>		select 	a.*<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			PROD_AMT 		= a.PROD_AMT	<br>			, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String insert_prod_amt_from_vup = "insert-prod-amt-from-vup";

/**
* para : $dateHh, $dateHh, $dateHh, $workDtm, $workDtm, $workDtm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_AMT (<br>			CONS_DTM<br>			, DTM_TYPE<br>			, FAC_NO<br>			, ENG_ID<br>			, INLO_NO<br>			, EXP_CONS_AMT<br>			, CONS_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with <br>			ROWDATAS as (<br>				select	concat($dateHh, '0000')		as CONS_DTM<br>						, a.FAC_NO				as FAC_NO 		' 설비번호 '<br>						, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>						, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>						, truncate(sum(a.EXP_CONS_AMT), 1)<br>												as EXP_CONS_AMT 		' 예상생산량 '<br>						, truncate(sum(a.CONS_AMT), 1)<br>												as CONS_AMT 		' 생산량 '<br>				from 	FE_ENG_CONS_AMT a 		' 에너지생산량테이블 '<br>				where	a.CONS_DTM >= concat($dateHh, '0000')<br>				and		a.CONS_DTM <= concat($dateHh, '5959')<br>				and		a.DTM_TYPE	= 'M15'<br>				group by  <br>					a.FAC_NO<br>					, a.ENG_ID<br>					, a.INLO_NO	<br>			)<br>			select<br>					  a.CONS_DTM		as CONS_DTM<br>					, 'H1'				as DTM_TYPE<br>					, a.FAC_NO<br>					, a.ENG_ID<br>					, a.INLO_NO<br>					, a.EXP_CONS_AMT<br>					, a.CONS_AMT<br>					, 1					as REG_USER_NO<br>					, $workDtm			as REG_DTM<br>					, 1					as CHG_USER_NO<br>					, $workDtm			as CHG_DTM<br>			from	ROWDATAS	a<br>			<br>			ON DUPLICATE KEY UPDATE		<br>					EXP_CONS_AMT 	= a.EXP_CONS_AMT<br>					, CONS_AMT		= a.CONS_AMT<br>					, CHG_DTM		= $workDtm<br><br> <br>
*/
public final String make_cons_h1_from_m15 = "make-cons-h1-from-m15";

/**
* para : $date, $date, $date, $workDtm, $workDtm, $workDtm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_STAT (<br>			CONS_DATE<br>			, FAC_NO<br>			, ENG_ID<br>			, INLO_NO<br>			, EXP_CONS_AMT<br>			, CONS_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with <br>		ROWDATAS as (<br>			select	a.FAC_NO				as FAC_NO 		' 설비번호 '<br>					, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>					, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>					, truncate(sum(a.EXP_CONS_AMT), 1)<br>											as EXP_CONS_AMT 		' 예상생산량 '<br>					, truncate(SUM(a.CONS_AMT), 1)<br>											as CONS_AMT 		' 생산량 '<br>			from 	FE_ENG_CONS_AMT a 		' 에너지생산량테이블 '<br>			where	a.CONS_DTM >= concat($date, '000000')<br>			and		a.CONS_DTM <= concat($date, '235959')<br>			and		a.DTM_TYPE	= 'H1'<br>			group by  <br>				a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO	<br>		)<br>		select<br>				$date				as CONS_DATE<br>				, a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.EXP_CONS_AMT<br>				, a.CONS_AMT<br>				, 1					as REG_USER_NO<br>				, $workDtm			as REG_DTM<br>				, 1					as CHG_USER_NO<br>				, $workDtm			as CHG_DTM<br>		from	ROWDATAS	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				EXP_CONS_AMT 	= a.EXP_CONS_AMT<br>				, CONS_AMT		= a.CONS_AMT<br>				, CHG_DTM		= $workDtm<br><br> <br>
*/
public final String make_cons_stat_from_h1 = "make-cons-stat-from-h1";

/**
* para : $psDateStart, $workDtm, $workDtm, $psDateStart, $psDateEnd, $engId, $dtmType<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_AMT (<br>			CONS_DTM, DTM_TYPE, FAC_NO, ENG_ID, INLO_NO, EXP_CONS_AMT , CONS_AMT , REG_USER_NO , REG_DTM , CHG_USER_NO , CHG_DTM <br>		) <br>		with ROWDATAS as (<br>			select $psDateStart					as CONS_DTM<br>					, 'H1'						as DTM_TYPE<br>					, a.FAC_NO 					as FAC_NO<br>					, a.ENG_ID 					as ENG_ID<br>					, a.INLO_NO 				as INLO_NO<br>					, 0							as EXP_CONS_AMT<br>					, truncate(SUM(a.CONS_AMT), 1) 	<br>												as CONS_AMT<br>					, 0							as REG_USER_NO<br>					, $workDtm					as REG_DTM<br>					, 0							as CHG_USER_NO<br>					, $workDtm					as CHG_DTM			<br>			from FE_ENG_CONS_AMT a<br>			where 	a.CONS_DTM	between $psDateStart and $psDateEnd<br>			and		a.ENG_ID	= $engId<br>			and		a.DTM_TYPE	= $dtmType<br>			group by a.FAC_NO<br>					, a.ENG_ID<br>					, a.INLO_NO<br>		)<br>		select 	a.*<br>		from	ROWDATAS a <br><br>		ON DUPLICATE KEY UPDATE<br>			CONS_AMT 		= a.CONS_AMT	<br>			, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String make_epower_cons_amt_h1_from_m15 = "make-epower-cons-amt-h1-from-m15";

/**
* para : $dateHh, $dateHh, $dateHh, $workDtm, $workDtm, $workDtm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_AMT (<br>				PROD_DTM<br>				, DTM_TYPE<br>				, FAC_NO<br>				, ENG_ID<br>				, INLO_NO<br>				, EXP_PROD_AMT<br>				, PROD_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with <br>		ROWDATAS as (<br>			select	concat($dateHh, '0000')	as PROD_DTM<br>					, a.FAC_NO				as FAC_NO 		' 설비번호 '<br>					, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>					, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>					, truncate(sum(a.EXP_PROD_AMT), 1)	<br>											as EXP_PROD_AMT 		' 예상생산량 '<br>					, truncate(sum(a.PROD_AMT), 1)<br>											as PROD_AMT 		' 생산량 '<br>			from 	FE_ENG_PROD_AMT a 		' 에너지생산량테이블 '<br>			where	a.PROD_DTM 	>= concat($dateHh, '0000')<br>			and		a.PROD_DTM 	<= concat($dateHh, '5959')<br>			and		a.DTM_TYPE	= 'M15'<br>			group by  <br>				a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO	<br>		)<br>		select<br>				  a.PROD_DTM<br>				, 'H1'				as DTM_TYPE<br>				, a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.EXP_PROD_AMT<br>				, a.PROD_AMT<br>				, 1					as REG_USER_NO<br>				, $workDtm			as REG_DTM<br>				, 1					as CHG_USER_NO<br>				, $workDtm			as CHG_DTM<br>		from	ROWDATAS	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				EXP_PROD_AMT 	= a.EXP_PROD_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM		= $workDtm<br><br> <br>
*/
public final String make_prod_h1_from_m15 = "make-prod-h1-from-m15";

/**
* para : $date, $date, $date, $workDtm, $workDtm, $workDtm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_STAT (<br>			PROD_DATE<br>			, FAC_NO<br>			, ENG_ID<br>			, INLO_NO<br>			, EXP_PROD_AMT<br>			, PROD_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with <br>		ROWDATAS as (<br>			select	a.FAC_NO				as FAC_NO 		' 설비번호 '<br>					, a.ENG_ID				as ENG_ID 		' 에너지ID '<br>					, a.INLO_NO				as INLO_NO 		' 설치위치번호 '<br>					, truncate(sum(a.EXP_PROD_AMT), 1)	<br>											as EXP_PROD_AMT 		' 예상생산량 '<br>					, truncate(sum(a.PROD_AMT), 1)<br>											as PROD_AMT 		' 생산량 '<br>			from 	FE_ENG_PROD_AMT a 		' 에너지생산량테이블 '<br>			where	a.PROD_DTM 	>= concat($date, '000000')<br>			and		a.PROD_DTM 	<= concat($date, '235959')<br>			and		a.DTM_TYPE	= 'H1'<br>			group by  <br>				a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO	<br>		)<br>		select<br>				$date			as PROD_DATE<br>				, a.FAC_NO<br>				, a.ENG_ID<br>				, a.INLO_NO<br>				, a.EXP_PROD_AMT<br>				, a.PROD_AMT<br>				, 1				as REG_USER_NO<br>				, $workDtm		as REG_DTM<br>				, 1				as CHG_USER_NO<br>				, $workDtm		as CHG_DTM<br>		from	ROWDATAS	a<br>		<br>		ON DUPLICATE KEY UPDATE		<br>				EXP_PROD_AMT 	= a.EXP_PROD_AMT<br>				, PROD_AMT		= a.PROD_AMT<br>				, CHG_DTM		= $workDtm<br><br> <br>
*/
public final String make_prod_stat_from_h1 = "make-prod-stat-from-h1";

/**
* para : #vupEngTable, #measrDtmName, $measrDtm, #measrDtmName, $workDtm, $workDtm, $measrDtm, $sinkSource<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into #vupEngTable (<br>			#measrDtmName<br>			, INLO_NO<br>			, ENG_ID<br>			, INST_VAL<br>			, PREV_INTG_VAL<br>			, CUR_INTG_VAL<br>			, DIFF_VAL<br>			, PRES_VAL<br>			, TEMP_VAL<br>			, MEMO<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with DATAS as (<br>			select<br>					if(a.PIPE_CL_CD = 'SINK', 'SINK', 'SOURCE') 	as  SINK_SOURCE<br>					, c.MO_NO<br>					, c.INLO_NO<br>			from	FE_FAC_PIPE 		a<br>					, FE_FAC_PIPE_PATH 	b<br>					, FX_MO 			c<br>			where	a.PIPE_ID 			= b.PIPE_ID<br>			and 	b.LINK_OBJ_CL_CD 	= 'MO'<br>			and 	c.MO_TID 			= b.LINK_OBJ_ID<br>			and		c.MO_TYPE in ( '압공유량계', '스팀유량계' )<br>		)<br>		, DATAS2 as (<br>			select 	 $measrDtm						as #measrDtmName<br>					, b.INLO_NO						as INLO_NO<br>					, a.ENG_ID						as ENG_ID<br>					, max(a.INST_VAL)				as INST_VAL<br>					, max(a.PREV_INTG_VAL)			as PREV_INTG_VAL<br>					, max(a.CUR_INTG_VAL)			as CUR_INTG_VAL<br>					, sum(a.DIFF_VAL)				as DIFF_VAL<br>					, avg(a.PRES_VAL)				as PRES_VAL<br>					, avg(a.TEMP_VAL)				as TEMP_VAL<br>					, 'FE_ENG_MEASR_RAW'			as MEMO<br>					, 1								as REG_USER_NO<br>					, $workDtm						as REG_DTM<br>					, 1								as CHG_USER_NO<br>					, $workDtm						as CHG_DTM				<br>			from	FE_ENG_MEASR_RAW 	a<br>					, DATAS 			b<br>			where	a.MEASR_DTM		= $measrDtm<br>			and		a.MO_NO 		= b.MO_NO<br>			and		b.SINK_SOURCE	= $sinkSource<br>			and		a.DIFF_VAL		>= 0<br>			group by <br>					b.INLO_NO<br>					, a.ENG_ID<br>		)<br>		select	*<br>		from	DATAS2 	a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				INST_VAL 		= a.INST_VAL<br>				, PREV_INTG_VAL	= a.PREV_INTG_VAL<br>				, CUR_INTG_VAL	= a.CUR_INTG_VAL<br>				, DIFF_VAL		= a.DIFF_VAL<br>				, PRES_VAL		= a.PRES_VAL<br>				, TEMP_VAL		= a.TEMP_VAL<br>				, MEMO			= a.MEMO<br>				, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String make_vup_raw_from_energy_raw = "make-vup-raw-from-energy-raw";

/**
* para : $measrDtm, $measrDtm<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into VUP_ENG_CONS_RAW (<br>			CONS_DTM<br>			, INLO_NO<br>			, ENG_ID<br>			, INST_VAL<br>			, PREV_INTG_VAL<br>			, CUR_INTG_VAL<br>			, DIFF_VAL<br>			, PRES_VAL<br>			, TEMP_VAL<br>			, MEMO<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		)<br>		with DATAS as (<br>			select 	 $measrDtm						as CONS_DTM<br>					, a.INLO_NO						as INLO_NO<br>					, a.ENG_ID						as ENG_ID<br>					, max(a.INST_VAL)				as INST_VAL<br>					, max(a.PREV_INTG_VAL)			as PREV_INTG_VAL<br>					, max(a.CUR_INTG_VAL)			as CUR_INTG_VAL<br>					, max(a.CUR_INTG_VAL) - max(a.PREV_INTG_VAL)<br>													as DIFF_VAL<br>					, avg(a.PRES_VAL)				as PRES_VAL<br>					, avg(a.TEMP_VAL)				as TEMP_VAL<br>					, 'FE_ENG_MEASR_RAW'			as MEMO<br>					, 1											as REG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as REG_DTM<br>					, 1											as CHG_USER_NO<br>					, DATE_FORMAT(now(), '%Y%m%d%H%i%s')		as CHG_DTM				<br>			from	FE_ENG_MEASR_RAW 	a<br>			where	a.MEASR_DTM		= $measrDtm<br>			and		a.ENG_ID		= 'EPWR'<br>			group by <br>					a.INLO_NO<br>					, a.ENG_ID<br>		)<br>		select 	*<br>		from 	DATAS a<br>								<br>		ON DUPLICATE KEY UPDATE		<br>				INST_VAL 		= a.INST_VAL<br>				, PREV_INTG_VAL	= a.PREV_INTG_VAL<br>				, CUR_INTG_VAL	= a.CUR_INTG_VAL<br>				, DIFF_VAL		= a.DIFF_VAL<br>				, PRES_VAL		= a.PRES_VAL<br>				, TEMP_VAL		= a.TEMP_VAL<br>				, MEMO			= a.MEMO<br>				, CHG_DTM		= a.CHG_DTM<br><br> <br>
*/
public final String make_vup_raw_from_energy_raw_epower = "make-vup-raw-from-energy-raw-epower";

/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.INLO_NO<br>				, b.ENG_ID<br>		from	FX_CF_INLO a<br>				, FE_ENG_BAS b<br>		where 	a.INLO_CL_CD = 'PLANT'<br>		and		b.USE_YN = 'Y'<br>		and		b.ENG_ID in ( 'AIR', 'COOLWATER', 'STEAM' )<br><br> <br>
*/
public final String select_plant_energy = "select-plant-energy";

/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select<br>				if(a.PIPE_CL_CD = 'SINK', 'SINK', 'SOURCE') 	as  PIPE_CL_CD<br>				, c.MO_NO<br>		from	FE_FAC_PIPE 		a<br>				, FE_FAC_PIPE_PATH 	b<br>				, FX_MO 			c<br>		where	a.PIPE_ID 			= b.PIPE_ID<br>		and 	b.LINK_OBJ_CL_CD 	= 'MO'<br>		and 	c.MO_TID 			= b.LINK_OBJ_ID<br><br> <br>
*/
public final String select_sensor_source_sink = "select-sensor-source-sink";

/**
* para : $recNo<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select *<br>		from    VUP_ANSAN_F a<br>		where	REC_NO > $recNo<br><br> <br>
*/
public final String select_vup_ansan_f_list = "select-vup-ansan-f-list";

/**
* para : $readPlc, $dtm<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	concat(DATE_FORMAT(read_dt, '%Y%m%d'),  DATE_FORMAT(read_tm, '%H%i%s'))		as DTM<br>				, a.*<br>		from    VUP_ANSAN a<br>		where	READ_PLC = $readPlc<br>		and		concat(DATE_FORMAT(read_dt, '%Y%m%d'),  DATE_FORMAT(read_tm, '%H%i%s')) > $dtm<br>		order by a.read_dt<br>				, a.read_tm<br><br> <br>
*/
public final String select_vup_ansan_list = "select-vup-ansan-list";

}