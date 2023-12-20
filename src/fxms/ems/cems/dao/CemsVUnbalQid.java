package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/CemsVUnbal.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class CemsVUnbalQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/CemsVUnbal.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/CemsVUnbal.xml";

public CemsVUnbalQid() { 
} 
/**
* para : $moNo, $psDtm, $facNo, $inloNo, $vunbalVal, $vaVal, $vbVal, $vcVal, $iunbalVal, $iaVal, $ibVal, $icVal, $vunbalVal, $vaVal, $vbVal, $vcVal, $iunbalVal, $iaVal, $ibVal, $icVal, $facNo, $inloNo<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into CEMS_V_UNBAL ( <br>				  MO_NO<br>				, PS_DTM<br>				, FAC_NO<br>				, INLO_NO<br>				, VUNBAL_VAL<br>				, VA_VAL<br>				, VB_VAL<br>				, VC_VAL<br>				, IUNBAL_VAL<br>				, IA_VAL<br>				, IB_VAL<br>				, IC_VAL<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM <br>		) values (<br>				  $moNo<br>				, $psDtm<br>				, $facNo<br>				, $inloNo<br>				, $vunbalVal<br>				, $vaVal<br>				, $vbVal<br>				, $vcVal<br>				, $iunbalVal<br>				, $iaVal<br>				, $ibVal<br>				, $icVal					<br>				, 1			<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>				, 1<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>		)<br>		on duplicate key update			<br>			  VUNBAL_VAL	= $vunbalVal<br>			, VA_VAL 		= $vaVal<br>			, VB_VAL		= $vbVal		<br>			, VC_VAL		= $vcVal		<br>			, IUNBAL_VAL	= $iunbalVal<br>			, IA_VAL 		= $iaVal<br>			, IB_VAL		= $ibVal		<br>			, IC_VAL		= $icVal		<br>			, FAC_NO		= $facNo<br>			, INLO_NO		= $inloNo<br>			, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_CEMS_V_UNBAL = "make_CEMS_V_UNBAL";

/**
* para : $psDate<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.MO_NO				as MO_NO<br>				, a.PS_DATE			as PS_DTM<br>				, c.FAC_NO			as FAC_NO<br>				, c.INLO_NO			as INLO_NO<br>				, a.IA_AVG			as IA_VAL<br>				, a.IB_AVG			as IB_VAL<br>				, a.IC_AVG			as IC_VAL<br>				, a.VA_AVG			as VA_VAL<br>				, a.VB_AVG			as VB_VAL<br>				, a.VC_AVG			as VC_VAL<br>		from	FX_V_EPWR_15M a <br>				, FE_MO_SENSOR b<br>				, FE_FAC_BAS c<br>		where 	a.PS_DATE 	= $psDate<br>		and 	a.MO_NO 	= b.MO_NO <br>		and 	b.FAC_NO 	= c.FAC_NO<br>		and		c.DEL_YN	= 'N'<br><br> <br>
*/
public final String select_CEMS_V_UNBAL = "select_CEMS_V_UNBAL";

}