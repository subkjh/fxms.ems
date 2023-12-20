package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/EngMeasrAmtFac.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class EngMeasrAmtFacQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/EngMeasrAmtFac.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/EngMeasrAmtFac.xml";

public EngMeasrAmtFacQid() { 
} 
/**
* para : $measrDtmStart, $measrDtmEnd<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_MEASR_AMT_FAC (<br>				ENG_DTM<br>				, DTM_TYPE<br>				, ENG_ID<br>				, FAC_NO<br>				, CONS_AMT<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		with DATAS as (<br>			select	a.ENG_DTM				as ENG_DTM<br>					, a.DTM_TYPE			as DTM_TYPE<br>					, 'E41'					as ENG_ID<br>					, a.FAC_NO				as FAC_NO<br>					, a.CONS_AMT * 0.000229	as CONS_AMT<br>			from	FE_ENG_MEASR_AMT_FAC 	a<br>			where	a.ENG_DTM			>= $measrDtmStart<br>			and		a.ENG_DTM			<= $measrDtmEnd<br>			and		a.ENG_ID			= 'E11'<br>		)<br>		select 	a.ENG_DTM			as ENG_DTM<br>				, a.DTM_TYPE		as DTM_TYPE<br>				, a.ENG_ID			as ENG_ID<br>				, a.FAC_NO			as FAC_NO<br>				, a.CONS_AMT		as CONS_AMT<br>				, 1					as REG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>									as REG_DTM<br>				, 1					as CHG_USER_NO<br>				, DATE_FORMAT(now(), '%Y%m%d%H%i%s')	<br>									as CHG_DTM				<br>		from	DATAS 	a								<br>		on duplicate key update		<br>				CONS_AMT 		= a.CONS_AMT<br>				, CHG_DTM		= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String make_E41_energy_amt_fac = "make-E41-energy-amt-fac";

}