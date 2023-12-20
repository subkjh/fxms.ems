package fxms.ems.cems.dao.dfo;

/**
* File : deploy/conf/sql/fxms/ems/cems/dfo/CheckStateSensorDfo.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class CheckStateSensorDfoQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/dfo/CheckStateSensorDfo.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/dfo/CheckStateSensorDfo.xml";

public CheckStateSensorDfoQid() { 
} 
/**
* para : $minute<br>
* result : EpwrColDataDto=fxms.ems.cems.dto.EpwrColDataDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	c.MO_NO 				as MO_NO<br>				, d.Va + d.Vb + d.Vc	as VOLT<br>				, d.Kwh 				as KWH<br>				, d.PS_DATE 			as PS_DATE<br>		from	FE_FAC_BAS 		a<br>				, FE_MO_SENSOR	b<br>				, FX_MO			c<br>				, FX_V_EPWR_RAW d<br>		where	a.FAC_NO	= b.FAC_NO <br>		and 	b.MO_NO 	= c.MO_NO <br>		and 	c.MO_TYPE 	in ( '피크전력계', '전력계')<br>		and		c.DEL_YN	= 'N'<br>		and 	d.MO_NO 	= c.mo_NO<br>		and 	d.PS_DATE > DATE_FORMAT(DATE_SUB(NOW(), INTERVAL $minute MINUTE) , '%Y%m%d%H%i%s')	<br>		order by c.MO_NO<br>				, d.PS_DATE desc<br><br> <br>
*/
public final String select_epower_collected_data = "select-epower-collected-data";

/**
* para : <br>
* result : EpwrColMoDataDto=fxms.ems.cems.dto.EpwrColMoDataDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.FAC_NO 				as FAC_NO<br>				, c.MO_NO 				as MO_NO<br>				, c.MO_NAME 			as MO_NAME<br>				, c.MO_TYPE 			as MO_TYPE<br>		from	FE_FAC_BAS 		a<br>				, FE_MO_SENSOR	b<br>				, FX_MO			c<br>		where	a.FAC_NO	= b.FAC_NO <br>		and 	b.MO_NO 	= c.MO_NO <br>		and 	c.MO_TYPE 	in ( '피크전력계', '전력계')<br>		and		c.DEL_YN 	= 'N'<br><br> <br>
*/
public final String select_epower_collected_mo = "select-epower-collected-mo";

/**
* para : $minute<br>
* result : EpwrColDataDto=fxms.ems.cems.dto.EpwrColDataDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	c.MO_NO 				as MO_NO<br>				, d.Va + d.Vb + d.Vc	as VOLT<br>				, d.Kwh 				as KWH 		' TODO : 컬럼 변경해야 함 '<br>				, d.PS_DATE 			as PS_DATE<br>		from	FE_FAC_BAS 		a<br>				, FE_MO_SENSOR	b<br>				, FX_MO			c<br>				, FX_V_EPWR_RAW d					' TODO : 컬럼 변경해야 함 '<br>		where	a.FAC_NO	= b.FAC_NO <br>		and 	b.MO_NO 	= c.MO_NO <br>		and 	c.MO_TYPE 	in ( '유량계')<br>		and		c.DEL_YN	= 'N'<br>		and 	d.MO_NO 	= c.mo_NO<br>		and 	d.PS_DATE > DATE_FORMAT(DATE_SUB(NOW(), INTERVAL $minute MINUTE) , '%Y%m%d%H%i%s')	<br>		order by c.MO_NO<br>				, d.PS_DATE desc<br><br> <br>
*/
public final String select_gas_collected_data = "select-gas-collected-data";

/**
* para : <br>
* result : EpwrColMoDataDto=fxms.ems.cems.dto.EpwrColMoDataDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.FAC_NO 				as FAC_NO<br>				, c.MO_NO 				as MO_NO<br>				, c.MO_NAME 			as MO_NAME<br>				, c.MO_TYPE 			as MO_TYPE<br>		from	FE_FAC_BAS 		a<br>				, FE_MO_SENSOR	b<br>				, FX_MO			c<br>		where	a.FAC_NO	= b.FAC_NO <br>		and 	b.MO_NO 	= c.MO_NO <br>		and 	c.MO_TYPE 	in ( '유량계')<br>		and		c.DEL_YN 	= 'N'<br><br> <br>
*/
public final String select_gas_collected_mo = "select-gas-collected-mo";

/**
* para : $minute<br>
* result : EpwrColDataDto=fxms.ems.cems.dto.EpwrColDataDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	c.MO_NO 				as MO_NO<br>				, d.Va + d.Vb + d.Vc	as VOLT<br>				, d.Kwh 				as KWH			' TODO : 컬럼 변경해야 함 '<br>				, d.PS_DATE 			as PS_DATE<br>		from	FE_FAC_BAS 		a<br>				, FE_MO_SENSOR	b<br>				, FX_MO			c<br>				, FX_V_EPWR_RAW d						' TODO : 컬럼 변경해야 함 '<br>		where	a.FAC_NO	= b.FAC_NO <br>		and 	b.MO_NO 	= c.MO_NO <br>		and 	c.MO_TYPE 	in ( '열량계')<br>		and		c.DEL_YN	= 'N'<br>		and 	d.MO_NO 	= c.mo_NO<br>		and 	d.PS_DATE > DATE_FORMAT(DATE_SUB(NOW(), INTERVAL $minute MINUTE) , '%Y%m%d%H%i%s')	<br>		order by c.MO_NO<br>				, d.PS_DATE desc<br><br> <br>
*/
public final String select_heat_collected_data = "select-heat-collected-data";

/**
* para : <br>
* result : EpwrColMoDataDto=fxms.ems.cems.dto.EpwrColMoDataDto<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.FAC_NO 				as FAC_NO<br>				, c.MO_NO 				as MO_NO<br>				, c.MO_NAME 			as MO_NAME<br>				, c.MO_TYPE 			as MO_TYPE<br>		from	FE_FAC_BAS 		a<br>				, FE_MO_SENSOR	b<br>				, FX_MO			c<br>		where	a.FAC_NO	= b.FAC_NO <br>		and 	b.MO_NO 	= c.MO_NO <br>		and 	c.MO_TYPE 	in ( '열량계')<br>		and		c.DEL_YN 	= 'N'<br><br> <br>
*/
public final String select_heat_collected_mo = "select-heat-collected-mo";

}