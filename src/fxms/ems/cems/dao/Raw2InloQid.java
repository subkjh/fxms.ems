package fxms.ems.cems.dao;

/**
* File : deploy/conf/sql/fxms/ems/cems/raw2inlo.xml<br>
* @since 20231219183303
* @author subkjh 
*
*/


public class Raw2InloQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/cems/raw2inlo.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/cems/raw2inlo.xml";

public Raw2InloQid() { 
} 
/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FX_CF_INLO ( <br>				INLO_NO<br>				, UPPER_INLO_NO<br>				, INLO_NAME<br>				, INLO_ALL_NAME<br>				, INLO_DESC<br>				, INLO_CL_CD<br>				, INLO_TYPE_CD<br>				, INLO_LEVEL_CD<br>				, ADDR<br>				, CNTCR_NAME<br>				, TEL_NUM<br>				, CNTCR_EMAIL				<br>		)<br>		select	nextval(FX_SEQ_INLONO)		as INLO_NO<br>				, b.INLO_NO					as UPPER_INLO_NO<br>				, a.COMPANY_NAME			as INLO_NAME<br>				, concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME)<br>											as INLO_ALL_NAME<br>				, a.COMPANY_NAME			as INLO_DESC<br>				, 'COMPANY'					as INLO_CL_CD<br>				, 'NONE'					as INLO_TYPE_CD<br>				, 'NONE'					as INLO_LEVEL_CD<br>				, a.ADDR					as ADDR<br>				, a.CNTCR_NAME				as CNTCR_NAME<br>				, a.TEL_NUM					as TEL_NUM<br>				, a.CNTCR_EMAIL				as CNTCR_EMAIL<br>		from 	CEMS_COMPANY_RAW 	a<br>				 left join FX_CF_INLO a1 on a1.INLO_ALL_NAME = concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME) and a1.INLO_CL_CD = 'COMPANY'<br>				, FX_CF_INLO		b<br>		where	a.COMPLEX_NAME = $complexName<br>		and		a.COMPLEX_NAME = b.INLO_NAME<br>		and		b.INLO_CL_CD = 'COMPLEX'<br>		and     a1.INLO_NO is null<br><br> <br>
*/
public final String insert_company__on_CEMS_COMPANY_RAW = "insert_company__on_CEMS_COMPANY_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FX_CF_INLO ( <br>				INLO_NO<br>				, UPPER_INLO_NO<br>				, INLO_NAME<br>				, INLO_ALL_NAME<br>				, INLO_DESC<br>				, INLO_CL_CD<br>				, INLO_TYPE_CD<br>				, INLO_LEVEL_CD<br>		)<br>		select	nextval(FX_SEQ_INLONO)<br>				, 100						' CEMS '<br>				, a.COMPLEX_NAME<br>				, a.COMPLEX_NAME<br>				, a.COMPLEX_NAME<br>				, 'COMPLEX'<br>				, 'NONE'<br>				, 'NONE'<br>		from 	CEMS_COMPANY_RAW a<br>				 left join FX_CF_INLO a1 on a1.INLO_NAME = a.COMPLEX_NAME and a1.INLO_CL_CD = 'COMPLEX'<br>		where	a.COMPLEX_NAME = $complexName<br>		and		a1.INLO_NO is null<br><br> <br>
*/
public final String insert_complex__on_CEMS_COMPANY_RAW = "insert_complex__on_CEMS_COMPANY_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FX_CF_INLO ( <br>				INLO_NO<br>				, SECTOR_CL_CDS<br>				, REP_SECTOR_CL_CD<br>				, CEO_NAME<br>				, BIZ_REG_NUM<br>				, CORP_REG_NUM<br>				, MAIN_PROD_NAME<br>				, REG_USER_NO<br>				, REG_DTM<br>				, CHG_USER_NO<br>				, CHG_DTM<br>		)<br>		select	a.PLANT_INLO_NO				as INLO_NO<br>				, a.SECTOR_CL_CDS			as SECTOR_CL_CDS<br>				, a.COMPANY_NAME			as INLO_NAME<br>				, concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME, ' > ', a.PLANT_NAME)<br>											as INLO_ALL_NAME<br>				, a.COMPANY_NAME			as INLO_DESC<br>				, 'PLANT'					as INLO_CL_CD<br>				, 'NONE'					as INLO_TYPE_CD<br>				, 'NONE'					as INLO_LEVEL_CD<br>				, a.ADDR					as ADDR<br>				, a.CNTCR_NAME				as CNTCR_NAME<br>				, a.TEL_NUM					as TEL_NUM<br>				, a.CNTCR_EMAIL				as CNTCR_EMAIL<br>		from 	CEMS_COMPANY_RAW 	a<br>				 left join FX_CF_INLO a1 on a1.INLO_ALL_NAME = concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME, ' > ', a.PLANT_NAME) and a1.INLO_CL_CD = 'PLANT'<br>				, FX_CF_INLO		b<br>		where	a.COMPLEX_NAME = $complexName<br>		and		b.INLO_NO = a.COMPANY_INLO_NO<br>		and		b.INLO_CL_CD = 'COMPANY'<br>		and     a1.INLO_NO is null<br><br> <br>
*/
public final String insert_fe_cf_inlo_cmpy__on_CEMS_COMPANY_RAW = "insert_fe_cf_inlo_cmpy__on_CEMS_COMPANY_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FX_CF_INLO ( <br>				INLO_NO<br>				, UPPER_INLO_NO<br>				, INLO_NAME<br>				, INLO_ALL_NAME<br>				, INLO_DESC<br>				, INLO_CL_CD<br>				, INLO_TYPE_CD<br>				, INLO_LEVEL_CD<br>				, ADDR<br>				, CNTCR_NAME<br>				, TEL_NUM<br>				, CNTCR_EMAIL				<br>		)<br>		select	nextval(FX_SEQ_INLONO)		as INLO_NO<br>				, b.INLO_NO					as UPPER_INLO_NO<br>				, a.COMPANY_NAME			as INLO_NAME<br>				, concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME, ' > ', a.PLANT_NAME)<br>											as INLO_ALL_NAME<br>				, a.COMPANY_NAME			as INLO_DESC<br>				, 'PLANT'					as INLO_CL_CD<br>				, 'NONE'					as INLO_TYPE_CD<br>				, 'NONE'					as INLO_LEVEL_CD<br>				, a.ADDR					as ADDR<br>				, a.CNTCR_NAME				as CNTCR_NAME<br>				, a.TEL_NUM					as TEL_NUM<br>				, a.CNTCR_EMAIL				as CNTCR_EMAIL<br>		from 	CEMS_COMPANY_RAW 	a<br>				 left join FX_CF_INLO a1 on a1.INLO_ALL_NAME = concat(a.COMPLEX_NAME, ' > ', a.COMPANY_NAME, ' > ', a.PLANT_NAME) and a1.INLO_CL_CD = 'PLANT'<br>				, FX_CF_INLO		b<br>		where	a.COMPLEX_NAME = $complexName<br>		and		b.INLO_NO = a.COMPANY_INLO_NO<br>		and		b.INLO_CL_CD = 'COMPANY'<br>		and     a1.INLO_NO is null<br><br> <br>
*/
public final String insert_plant__on_CEMS_COMPANY_RAW = "insert_plant__on_CEMS_COMPANY_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_COMPANY_RAW a<br>		set 	a.COMPANY_INLO_NO = ( select INLO_NO from FX_CF_INLO t1 where t1.UPPER_INLO_NO = a.COMPLEX_INLO_NO and t1.INLO_NAME = a.COMPANY_NAME and t1.INLO_CL_CD = 'COMPANY' )<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_company_inlo_no_on_CEMS_COMPANY_RAW = "update_company_inlo_no_on_CEMS_COMPANY_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_COMPANY_RAW a<br>		set 	a.COMPLEX_INLO_NO = ( select INLO_NO from FX_CF_INLO t1 where t1.INLO_NAME = a.COMPLEX_NAME and t1.INLO_CL_CD = 'COMPLEX' )<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_complex_inlo_no_on_CEMS_COMPANY_RAW = "update_complex_inlo_no_on_CEMS_COMPANY_RAW";

/**
* para : $complexName<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>update 	CEMS_COMPANY_RAW a<br>		set 	a.PLANT_INLO_NO = ( select INLO_NO from FX_CF_INLO t1 where t1.UPPER_INLO_NO = a.COMPANY_INLO_NO and t1.INLO_NAME = a.PLANT_NAME and t1.INLO_CL_CD = 'PLANT' )<br>		where 	a.COMPLEX_NAME = $complexName<br><br> <br>
*/
public final String update_plant_inlo_no_on_CEMS_COMPANY_RAW = "update_plant_inlo_no_on_CEMS_COMPANY_RAW";

}