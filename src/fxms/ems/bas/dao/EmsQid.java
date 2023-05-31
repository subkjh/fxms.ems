package fxms.ems.bas.dao;

/**
* File : deploy/conf/sql/fxms/ems/ems.xml<br>
* @since 20230522174934
* @author subkjh 
*
*/


public class EmsQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/ems.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/ems.xml";

public EmsQid() { 
} 
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