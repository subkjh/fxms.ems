package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/EpwrInloPrice.xml<br>
* @since 20231122170926
* @author subkjh 
*
*/


public class EpwrInloPriceQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/EpwrInloPrice.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/EpwrInloPrice.xml";

public EpwrInloPriceQid() { 
} 
/**
* para : <br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update	FE_EPWR_INLO_PRICE a<br>				, ( <br>					with lastdatas as ( <br>						select	KEPCO_PP_ID, max(REQ_DATE) REQ_DATE<br>						from 	KEPCO_PP_PRICE_INFO<br>						group by KEPCO_PP_ID<br>					)<br>					select 	a.EPWR_PRICE_ID<br>							, a.CNTRT_EPWR<br>							, c.INLO_NO<br>					from 	KEPCO_PP_PRICE_INFO a<br>							, lastdatas			b<br>							, FE_EPWR_INLO		c		<br>					where	a.KEPCO_PP_ID 	= b.KEPCO_PP_ID<br>					and		a.REQ_DATE 		= b.REQ_DATE<br>					and		b.KEPCO_PP_ID	= c.KEPCO_PP_ID<br>				) b<br>		set		a.EPWR_PRICE_ID 	= b.EPWR_PRICE_ID<br>				, a.CNTRT_EPWR 		= b.CNTRT_EPWR<br>				, CHG_DTM			= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>		where	a.INLO_NO 			= b.INLO_NO<br>		and		a.END_EFFCT_DATE 	= '99991231'<br><br> <br>
*/
public final String update_EPWR_INLO_PRICE = "update_EPWR_INLO_PRICE";

}