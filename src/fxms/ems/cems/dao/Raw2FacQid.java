package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/raw2fac.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class Raw2FacQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/raw2fac.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/raw2fac.xml";

public Raw2FacQid() { 
} 
/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_FAC_BAS (<br>				FAC_NO<br>				, FAC_NAME<br>				, INLO_NO<br>				, FAC_CL_CD<br>				, PROC_TYPE_CD<br>		)<br>		with datas as (<br>				select<br>						distinct  <br>						a.FAC_NAME  			as FAC_NAME <br>						, a.PLANT_INLO_NO 		as INLO_NO<br>						, a.FAC_CL_CD 			as FAC_CL_CD<br>						, ifnull(a.PROC_TYPE_CD, '9999') 	<br>												as PROC_TYPE_CD <br>				from 	CEMS_FAC_RAW a<br>							left join FE_FAC_BAS a1 on a1.INLO_NO = a.PLANT_INLO_NO and a1.FAC_NAME = a.FAC_NAME<br>				where 	a.FAC_CL_CD 	is not null<br>				and 	a1.FAC_NO 		is null<br>				and		a.COMPLEX_NAME = $complexName<br>		)<br>		select	nextval(FX_SEQ_FACNO)	as FAC_NO<br>				, datas.*<br>		from datas<br><br> <br>
*/
public final String insert_FE_FAC_BAS = "insert_FE_FAC_BAS";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_MO_SENSOR (<br>				MO_NO<br>				, SENSR_NAME		<br>				, SENSR_DESC<br>				, PLC_MO_NO<br>				, FAC_NO<br>		)<br>		select	a.MO_NO			as MO_NO<br>				, a.MO_NAME		as SENSR_NAME<br>				, a.MO_TYPE		as SENSR_DESC<br>				, c.MO_NO		as PLC_MO_NO<br>				, b1.FAC_NO		as FAC_NO <br>		from	FX_MO a<br>					left join FE_MO_SENSOR 	a1 on a1.MO_NO = a.MO_NO<br>				, CEMS_FAC_RAW b<br>					left join FE_FAC_BAS	b1 on b1.INLO_NO = b.PLANT_INLO_NO and b1.FAC_NAME = b.FAC_NAME<br>				, FX_MO_NODE c<br>		where	a.MO_TID 		= b.MO_TID<br>		and		c.NODE_IP_ADDR	= b.GW_IP<br>		and		b.COMPLEX_NAME = $complexName<br>				<br>		on duplicate key update<br>				FAC_NO 		= b1.FAC_NO		<br>				, PLC_MO_NO = c.MO_NO<br><br> <br>
*/
public final String insert_FE_MO_SENSOR = "insert_FE_MO_SENSOR";

/**
* para : <br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FX_MO_NODE (<br>				MO_NO<br>				, NODE_IP_ADDR<br>		)<br>		select	a.MO_NO		as MO_NO<br>				, a.MO_NAME	as NODE_IP_ADDR<br>		from	FX_MO a<br>				left join FX_MO_NODE a1 on a1.MO_NO = a.MO_NO<br>		where	a1.MO_NO is null<br><br> <br>
*/
public final String insert_FX_MO_NODE = "insert_FX_MO_NODE";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FX_MO ( <br>				MO_NO<br>				, MO_NAME<br>				, MO_DISP_NAME<br>				, MO_CLASS<br>				, MO_TYPE<br>				, UPPER_MO_NO<br>				, INLO_NO<br>				, POLL_CYCLE<br>				, MO_TID		<br>		)<br>		with gws as ( <br>			select	distinct PLANT_INLO_NO<br>					, GW_IP<br>			from	CEMS_FAC_RAW<br>			where	PLANT_INLO_NO is not null<br>			and		COMPLEX_NAME = $complexName<br>		)<br>		, datas as (	<br>			select <br>					nextval(FX_SEQ_MONO)	as MO_NO<br>					, GW_IP					as MO_NAME<br>					, GW_IP					as MO_DISP_NAME<br>					, 'NODE'				as MO_CLASS<br>					, 'Gateway'				as MO_TYPE<br>					, a.PLANT_INLO_NO		as INLO_NO<br>					, 60					as POLL_CYCLE<br>					, GW_IP					as MO_TID<br>			from 	gws a<br>					left join FX_MO_NODE a1 on a1.NODE_IP_ADDR = a.GW_IP<br>			where 	a1.NODE_IP_ADDR is null<br>		)<br>		select 	MO_NO<br>				, MO_NAME<br>				, MO_DISP_NAME<br>				, MO_CLASS<br>				, MO_TYPE<br>				, MO_NO				as UPPER_MO_NO<br>				, INLO_NO<br>				, POLL_CYCLE<br>				, MO_TID<br>		from 	datas<br><br> <br>
*/
public final String insert_gateway__into_FX_MO = "insert_gateway__into_FX_MO";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FX_MO ( <br>				MO_NO<br>				, MO_NAME<br>				, MO_DISP_NAME<br>				, MO_CLASS<br>				, MO_TYPE<br>				, UPPER_MO_NO<br>				, INLO_NO<br>				, POLL_CYCLE<br>				, MO_TID		<br>				, MO_ADD_JSON<br>		)<br>		with datas as ( <br>			select <br>					nextval(FX_SEQ_MONO)	as MO_NO<br>					, concat(a.FAC_NAME, ' 전력계')<br>											as MO_NAME<br>					, concat(a.FAC_NAME, ' 전력계')<br>											as MO_DISP_NAME<br>					, 'SENSOR'				as MO_CLASS<br>					, a.MO_TYPE				as MO_TYPE<br>					, a.PLANT_INLO_NO		as INLO_NO<br>					, 60					as POLL_CYCLE<br>					, a.MO_TID				as MO_TID<br>					, JSON_OBJECT( "voltage", a.VOLTAGE, "wiring", a.WIRING, "breakCapacity", a.CIR_BRK_CAPA )	<br>											as MO_ADD_JSON					<br>			from 	CEMS_FAC_RAW a<br>					left join FX_MO a1 on a1.MO_TID = a.MO_TID<br>			where 	a1.MO_TID is null<br>			and		a.PLANT_INLO_NO is not null<br>			and		a.COMPLEX_NAME = $complexName<br>		)	<br>		select 	MO_NO<br>				, MO_NAME<br>				, MO_DISP_NAME<br>				, MO_CLASS<br>				, MO_TYPE<br>				, MO_NO				as UPPER_MO_NO<br>				, INLO_NO<br>				, POLL_CYCLE<br>				, MO_TID<br>				, MO_ADD_JSON<br>		from 	datas<br><br> <br>
*/
public final String insert_sensor__into_FX_MO = "insert_sensor__into_FX_MO";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.COMPANY_INLO_NO = ( select INLO_NO from FX_CF_INLO where DEL_YN = 'N' and INLO_CL_CD = 'COMPANY' and INLO_ALL_NAME = concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME) )<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_company_inlo_no__on_CEMS_FAC_RAW = "update_company_inlo_no__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.COMPLEX_INLO_NO = ( select INLO_NO from FX_CF_INLO where DEL_YN = 'N' and INLO_CL_CD = 'COMPLEX' and INLO_NAME = a.COMPLEX_NAME  )<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_complex_inlo_no__on_CEMS_FAC_RAW = "update_complex_inlo_no__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.FAC_CL_CD = ( select CD_CODE from FX_CO_CD where CD_CLASS = 'FAC_CL_CD' and CD_NAME = a.FAC_TYPE )<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_fac_cl_cd__on_CEMS_FAC_RAW = "update_fac_cl_cd__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update	CEMS_FAC_RAW a<br>		set		a.FAC_NO = ( select b.FAC_NO from FE_FAC_BAS b  where b.INLO_NO = a.PLANT_INLO_NO and b.FAC_NAME = a.FAC_NAME )<br>		where	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_fac_no__on_CEMS_FAC_RAW = "update_fac_no__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update	CEMS_FAC_RAW a<br>		set		a.GW_MO_NO = ( <br>					select 	b.MO_NO <br>					from 	FX_MO_NODE b<br>							, FX_MO c  <br>					where 	b.MO_NO = c.MO_NO<br>					and		b.NODE_IP_ADDR = a.GW_IP <br>					and  	c.INLO_NO = a.PLANT_INLO_NO<br>				)<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_gw_mo_no__on_CEMS_FAC_RAW = "update_gw_mo_no__on_CEMS_FAC_RAW";

/**
* para : $complexName, $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.MO_TID =	concat(<br>								( 	select 	min(b.ATTR_VALUE)<br>									from  	FX_CF_INLO a<br>											, FX_CF_INLO_ATTR b<br>									where 	a.INLO_NAME = $complexName<br>									and 	b.INLO_NO  = a.INLO_NO <br>									and 	b.ATTR_NAME = 'opcua.tag'<br>								)<br>								, '.'<br>								, replace(GW_IP, '.', '_')<br>								, '.'<br>								, OPC_METER_ID <br>							) <br>		where		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_mo_tid__on_CEMS_FAC_RAW = "update_mo_tid__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.MO_TYPE 	= '전력계'<br>		where	a.FAC_NAME not like '%피크계측%'<br>		and		a.TAG 		= '전기'<br>		and		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_mo_type_1__on_CEMS_FAC_RAW = "update_mo_type_1__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.MO_TYPE 		= '피크전력계'<br>		where	a.FAC_NAME 		like '%피크계측%'<br>		and		a.PROCESS_NAME 	is null<br>		and		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_mo_type_2__on_CEMS_FAC_RAW = "update_mo_type_2__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.MO_TYPE 		= '피크전력계'<br>		where	a.FAC_NAME 		like '%메인%'<br>		and		a.PROCESS_NAME 	is null<br>		and		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_mo_type_3__on_CEMS_FAC_RAW = "update_mo_type_3__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.MO_TYPE 	= '가스유량계'<br>		where	a.TAG 		= '가스'<br>		and		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_mo_type_4__on_CEMS_FAC_RAW = "update_mo_type_4__on_CEMS_FAC_RAW";

/**
* para : <br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update CEMS_FAC_RAW a<br>		set a.PLANT_INLO_NO = ( select INLO_NO from FX_CF_INLO where DEL_YN = 'N' and INLO_CL_CD = 'PLANT' and INLO_ALL_NAME = concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME, ' > ', a.PLANT_NAME) )<br><br> <br>
*/
public final String update_plant_inlo_no__on_CEMS_FAC_RAW = "update_plant_inlo_no__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.PLANT_NAME = '사업장'<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_plant_name_1__on_CEMS_FAC_RAW = "update_plant_name_1__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.PLANT_NAME = '1공장'<br>		where 	COMPANY_NAME = '㈜단석산업'<br>		and		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_plant_name_2__on_CEMS_FAC_RAW = "update_plant_name_2__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set		a.PROC_TYPE_CD = ( select CD_CODE from FX_CO_CD where CD_CLASS = 'PROC_TYPE_CD' and CD_NAME = a.PROCESS_NAME )<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_proc_type_cd_1__on_CEMS_FAC_RAW = "update_proc_type_cd_1__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.PROC_TYPE_CD = ( select CD_CODE from FX_CO_CD where CD_CLASS = 'PROC_TYPE_CD' and CD_NAME = '공기압축' )<br>		where 	a.PROCESS_NAME in ( '컴프레셔', '콤프레셔' )<br>		and		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_proc_type_cd_2__on_CEMS_FAC_RAW = "update_proc_type_cd_2__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_FAC_RAW a<br>		set 	a.PROC_TYPE_CD = ( select CD_CODE from FX_CO_CD where CD_CLASS = 'PROC_TYPE_CD' and CD_NAME = '공정기타' )<br>		where 	a.PROCESS_NAME like '%기타%'<br>		and		a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_proc_type_cd_3__on_CEMS_FAC_RAW = "update_proc_type_cd_3__on_CEMS_FAC_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update	CEMS_FAC_RAW a<br>		set		a.SENSOR_MO_NO = ( <br>					select 	b.MO_NO <br>					from 	FX_MO b  <br>					where 	b.MO_CLASS = 'SENSOR'<br>					and		b.MO_TID = a.MO_TID <br>					and  	b.INLO_NO = a.PLANT_INLO_NO<br>				)<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_sensor_mo_no__on_CEMS_FAC_RAW = "update_sensor_mo_no__on_CEMS_FAC_RAW";

}