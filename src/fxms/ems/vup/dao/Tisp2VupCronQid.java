package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/Tisp2VupCron.xml<br>
* @since 20230522174934
* @author subkjh 
*
*/


public class Tisp2VupCronQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/vup/Tisp2VupCron.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/vup/Tisp2VupCron.xml";

public Tisp2VupCronQid() { 
} 
/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.INLO_TID		as COMPLEX_PID <br>				, a.INLO_NAME	as COMPLEX_NAME<br>				, a.INLO_DESC	as COMPLEX_DESCR<br>		from 	FX_CF_INLO a <br>		where 	a.DEL_YN = 'N'<br>		and		a.INLO_CL_CD = 'COMPLEX'<br><br> <br>
*/
public final String select_complex_from_tisp = "select-complex-from-tisp";

/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.ENG_TID		as ENERGY_ID<br>				, a.ENG_NAME	as ENERGY_NAME<br>				, a.ENG_DESCR	as ENERGY_DESCR<br>				, a.ENG_UNIT	as ENERGY_UNIT<br>		from 	FE_ENG_BAS a <br>		where 	a.USE_YN = 'Y'<br><br> <br>
*/
public final String select_energy_from_tisp = "select-energy-from-tisp";

/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.PIPE_ID			as PIPE_ID <br>				, a.PIPE_NAME		as PIPE_NAME<br>				, a.PIPE_DESCR		as PIPE_DESCR<br>				, (<br>					select  max(t1.INLO_TID)	as COMPLEX_PID<br>					from 	FX_CF_INLO t1<br>							, FX_CF_INLO_MEM t2<br>					where	t2.LOWER_INLO_NO = a.MNG_INLO_NO <br>					and 	t1.INLO_NO 		= t2.INLO_NO <br>					and 	t1.INLO_CL_CD 	= 'COMPLEX'<br>				)				<br>									as COMPLEX_PID		<br>				, (<br>					select  max(t1.INLO_TID)	as COMPLEX_PID<br>					from 	FX_CF_INLO t1<br>							, FX_CF_INLO_MEM t2<br>					where	t2.LOWER_INLO_NO = a.MNG_INLO_NO <br>					and 	t1.INLO_NO 		= t2.INLO_NO <br>					and 	t1.INLO_CL_CD 	= 'PLANT'<br>				)					<br>									as PLANT_PID<br>				, a1.ENG_TID		as ENERGY_ID<br>				, a2.VAL1			as TRANS_ID<br>				, a2.VAL2			as PUBLIC_YN				<br>		from 	FE_FAC_PIPE a<br>					left join FE_ENG_BAS 	a1 on a1.ENG_ID = a.ENG_ID <br>					left join FX_CO_CD 		a2 on a2.CD_CLASS = 'PIPE_CL_CD' and a2.CD_CODE = a.PIPE_CL_CD<br><br> <br>
*/
public final String select_pipe_from_tisp = "select-pipe-from-tisp";

/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.INLO_TID		as PLANT_PID <br>				, a.INLO_NAME	as PLANT_NAME<br>				, a.INLO_DESC	as PLANT_DESCR<br>				, (<br>					select  max(a1.INLO_TID)	as COMPLEX_PID<br>					from 	FX_CF_INLO a1<br>							, FX_CF_INLO_MEM b<br>					where	b.LOWER_INLO_NO = a.INLO_NO <br>					and 	a1.INLO_NO 		= b.INLO_NO <br>					and 	a1.INLO_CL_CD 	= 'COMPLEX'<br>				)				as COMPLEX_PID	<br>		from 	FX_CF_INLO a <br>		where 	a.DEL_YN = 'N'<br>		and		a.INLO_CL_CD = 'PLANT'<br><br> <br>
*/
public final String select_plant_from_tisp = "select-plant-from-tisp";

}