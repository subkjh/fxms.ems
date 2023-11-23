package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/cems.xml<br>
* @since 20231122170926
* @author subkjh 
*
*/


public class CemsQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/cems.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/cems.xml";

public CemsQid() { 
} 
/**
* para : $inloTid<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select a.NODE_IP_ADDR<br>				, a.COMM_PORT_NO<br>				, a.COMM_PROTC<br>				, a.MO_NO<br>		from	FX_MO_NODE 	a<br>				, FX_MO		b<br>		where	a.MO_NO = b.MO_NO<br>		and		b.DEL_YN = 'N'	<br>		and		exists ( select t1.LOWER_INLO_NO <br>						from 	FX_CF_INLO_MEM 	t1<br>								, FX_CF_INLO	t2 <br>						where 	t1.INLO_NO 			= t2.INLO_NO<br>						and		t2.INLO_TID 		= $inloTid<br>						and		t1.LOWER_INLO_NO 	= b.INLO_NO<br>						)<br><br> <br>
*/
public final String select_GW__BY_COMPLEX = "select_GW__BY_COMPLEX";

/**
* para : $inloTid<br>
* result : NodeDto=fxms.ems.cems.dto.NodeDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.MO_NO				as MO_NO<br>				, b.MO_TID			as DISPLAY_NAME<br>				, a.PLC_MO_NO		as GW_MO_NO<br>		from	FE_MO_SENSOR 	a<br>				, FX_MO		b<br>		where	a.MO_NO = b.MO_NO<br>		and		b.DEL_YN = 'N'	<br>		and		exists ( select t1.LOWER_INLO_NO <br>						from 	FX_CF_INLO_MEM 	t1<br>								, FX_CF_INLO	t2 <br>						where 	t1.INLO_NO 			= t2.INLO_NO<br>						and		t2.INLO_TID 		= $inloTid<br>						and		t1.LOWER_INLO_NO 	= b.INLO_NO<br>						)<br><br> <br>
*/
public final String select_NODE__BY_COMPLEX = "select_NODE__BY_COMPLEX";

}