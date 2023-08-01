package fxms.ems.h2.dao;

/**
* File : deploy/conf/sql/fxms/ems/h2/h2.xml<br>
* @since 20230726103724
* @author subkjh 
*
*/


public class H2Qid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/h2/h2.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/h2/h2.xml";

public H2Qid() { 
} 
/**
* para : $moTid<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	b.MO_NO				as MO_NO<br>				, b.MO_NAME			as MO_NAME<br>				, a.PS_ID			as PS_ID<br>				, IF(a.PS_ID = 'UNDEF', a.POINT_TID, null) 		<br>									as MO_INSTANCE <br>		from 	FX_PS_POINT a<br>				, FX_MO b<br>		where	b.MO_TID = a.MO_TID<br>		and		a.PS_ID is not null<br>		and		b.MO_TID = $moTid<br><br> <br>
*/
public final String select_test_mo_psid = "select-test-mo-psid";

}