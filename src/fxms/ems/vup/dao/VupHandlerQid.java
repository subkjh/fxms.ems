package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/VupHandler.xml<br>
* @since 20230614162949
* @author subkjh 
*
*/


public class VupHandlerQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/vup/VupHandler.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/vup/VupHandler.xml";

public VupHandlerQid() { 
} 
/**
* para : $getConsDate(), $getInloNo(), $getEngId(), $getFacNo(), $getExpConsAmt(), $getConsAmt(), $getExpConsAmt()<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_CONS_STAT (<br>			CONS_DATE<br>			, INLO_NO<br>			, ENG_ID<br>			, FAC_NO<br>			, EXP_CONS_AMT<br>			, CONS_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		) values (<br>			$getConsDate()<br>			, $getInloNo()<br>			, $getEngId()<br>			, $getFacNo()<br>			, $getExpConsAmt()<br>			, $getConsAmt()<br>			, 0<br>			, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>			, 0<br>			, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>		)<br>		<br>		ON DUPLICATE KEY UPDATE		<br>			EXP_CONS_AMT 		= $getExpConsAmt()<br>			, CHG_DTM			= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String INSERT_FE_ENG_CONS_STAT = "INSERT_FE_ENG_CONS_STAT";

/**
* para : $getProdDate(), $getInloNo(), $getEngId(), $getFacNo(), $getExpProdAmt(), $getProdAmt(), $getExpProdAmt()<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>insert into FE_ENG_PROD_STAT (<br>			PROD_DATE<br>			, INLO_NO<br>			, ENG_ID<br>			, FAC_NO<br>			, EXP_PROD_AMT<br>			, PROD_AMT<br>			, REG_USER_NO<br>			, REG_DTM<br>			, CHG_USER_NO<br>			, CHG_DTM<br>		) values (<br>			$getProdDate()<br>			, $getInloNo()<br>			, $getEngId()<br>			, $getFacNo()<br>			, $getExpProdAmt()<br>			, $getProdAmt()<br>			, 0<br>			, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>			, 0<br>			, DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br>		)<br>		ON DUPLICATE KEY UPDATE		<br>			EXP_PROD_AMT 		= $getExpProdAmt()<br>			, CHG_DTM			= DATE_FORMAT(now(), '%Y%m%d%H%i%s')<br><br> <br>
*/
public final String INSERT_FE_ENG_PROD_STAT = "INSERT_FE_ENG_PROD_STAT";

/**
* para : $start_date, $end_date, $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.ALARM_NO                          '알람번호 '<br>		        , a.ALARM_KEY                         '알람키 '<br>		        , a.ALARM_CFG_NO                      '알람조건번호 '<br>		        , a.ALCD_NO                           '경보코드번호 '<br>		        , a.ALCD_NAME                         '경보코드명 '<br>		        , a.ALARM_CL_CD                       '알람구분코드 '<br>		        , a.ALARM_LEVEL                       '경보등급 '<br>		        , a.ALARM_MSG                         '알람메세지 '<br>		        , a.PS_ID                             '성능ID '<br>		        , a.PS_VAL                            '성능값 '<br>		        , a.CMPR_VAL                          '비교값 '<br>		        , a.PS_DTM                            '상태수집일시 '<br>		        , a.PS_NAME                           '상태값명 '<br>		        , a.MO_NO                             'MO번호 '<br>		        , a.MO_NAME                           'MO명 '<br>		        , a.MO_DISP_NAME                      'MO표시명 '<br>		        , a.MO_INSTANCE                       'MO인스턴스 '<br>		        , a.MO_CLASS                          'MO클래스  '<br>		        , a.UPPER_MO_NO                       '상위MO번호 '<br>		        , a.UPPER_MO_NAME                     '상위MO명 '<br>		        , a.UPPER_MO_DISP_NAME                '상위MO표시명 '<br>		        , a.INLO_NO                           '설치위치번호 '<br>		        , a.INLO_NAME                         '설치위치명 '<br>		        , a.MODEL_NO                          '모델번호 '<br>		        , a.MODEL_NAME                        '모델명 '<br>		        , a.FAC_NO                            '설비번호 '<br>		        , a.FAC_NAME                          '설비명 '<br>		        , a.REG_DTM                           '등록일시 '<br>		        , a.OCCUR_DTM                         '발생일시 '<br>		        , a.OCCUR_CNT                         '발생횟수 '<br>		        , a.ACK_DTM                           '확인일시 '<br>		        , a.ACK_USER_NO                       '확인사용자번호 '<br>		        , a.RSN_REG_DTM                       '원인등록일시 '<br>		        , a.RSN_REG_USER_NO                   '원인등록사용자번호 '<br>		        , a.RSN_NO                            '원인번호 '<br>		        , a.RSN_NAME                          '원인명 '<br>		        , a.RSN_MEMO                          '원인메모 '<br>		        , a.IP_ADDR                           'IP주소 '<br>		        , a.CHG_DTM                           '변경일시 '<br>		        , a.FPACT_CD                          '후속조치코드 '<br>		        , a.FPACT_NAME                        '후속조치명 '<br>		        , a.RLSE_YN                           '해제여부 '<br>		        , a.RLSE_DTM                          '해제일시 '<br>		        , a.RLSE_USER_NO                      '해제사용자번호 '<br>		        , a.ALARM_RLSE_RSN_CD                 '알람해제원인코드 '<br>		        , a.ALARM_RLSE_RSN_NAME               '알람해제원인명 '<br>		        , a.RLSE_MEMO                         '해제메모 '<br>		        <br>		        , a1.INLO_TID				as FACTORY_PID<br>		        , a2.FAC_TID				as DEVICE_PID<br>		        <br>		from    FX_AL_ALARM_HST a  '경보알람이력테이블 '<br>					left join FX_CF_INLO 	a1 on a1.INLO_NO = a.INLO_NO <br>					left join FE_FAC_BAS	a2 on a2.FAC_NO = a.FAC_NO <br>				, FX_CF_INLO_MEM b<br>		where	a.OCCUR_DTM		>= concat($start_date, "000000")<br>		and		a.OCCUR_DTM		<= concat($end_date, "235959")<br>		and		b.INLO_NO		= $inloNo<br>		and		b.LOWER_INLO_NO	= a.INLO_NO<br>		order by <br>				a.OCCUR_DTM<br><br> <br>
*/
public final String select_alm_02 = "select-alm-02";

/**
* para : <br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.INLO_TID 			as COMPLEX_PID<br>				, a.INLO_NAME 		as COMPLEX_NM<br>				, a.INLO_DESC 		as COMPLEX_DESC<br>		from	FX_CF_INLO			a<br>		where	a.DEL_YN 			= 'N'<br>		and		a.INLO_CL_CD 		= 'COMPLEX'<br><br> <br>
*/
public final String select_complex_all = "select-complex-all";

/**
* para : <br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.ENG_TID 			as ENERGY_CD<br>				, a.ENG_NAME 		as ENERGY_NM<br>		from	FE_ENG_BAS	a<br>		where	a.USE_YN 	= 'Y'<br>		and		a.ENG_TID	is not null<br><br> <br>
*/
public final String select_energy_all = "select-energy-all";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.ENG_RT_ID<br>--				, a.ENG_ID<br>--				, a.INLO_NO<br>				, a.ENG_RT_DESCR<br>--				, a.STRT_INLO_NO<br>--				, a.FNSH_INLO_NO<br>--				, a.STRT_FAC_NO<br>--				, a.FNSH_FAC_NO<br>				, a.USE_YN<br>				, a.CHG_DTM				<br>				, a1.INLO_TID		as COMPLEX_PID				<br>				, a1.INLO_NAME		as COMPLEX_NM				<br>				, a2.ENG_TID		as ENERGE_CD				<br>				, a2.ENG_NAME		as ENERGE_NM<br>				, a3.INLO_NAME		as STRT_FACTORY_NM<br>				, a3.INLO_TID		as STRT_FACTORY_PID<br>				, a4.INLO_NAME		as FNSH_FACTORY_NM<br>				, a4.INLO_TID		as FNSH_FACTORY_PID<br>				, a5.FAC_NAME		as STRT_FAC_NM<br>				, a5.FAC_TID		as STRT_FAC_PID<br>				, a6.FAC_NAME		as FNSH_FAC_NM				<br>				, a6.FAC_TID		as FNSH_FAC_PID				<br>		from	FE_ENG_RT_BAS		a<br>					left join FX_CF_INLO 	a1 on a1.INLO_NO 	= a.INLO_NO<br>					left join FE_ENG_BAS	a2 on a2.ENG_ID 	= a.ENG_ID<br>					left join FX_CF_INLO	a3 on a3.INLO_NO 	= a.STRT_INLO_NO<br>					left join FX_CF_INLO	a4 on a4.INLO_NO 	= a.FNSH_INLO_NO<br>					left join FE_FAC_BAS	a5 on a5.FAC_NO 	= a.STRT_FAC_NO<br>					left join FE_FAC_BAS	a6 on a6.FAC_NO 	= a.FNSH_FAC_NO<br>				, FX_CF_INLO_MEM	b<br>		where 	a.INLO_NO	= b.LOWER_INLO_NO<br>		and 	b.INLO_NO 	= $inloNo<br><br> <br>
*/
public final String select_energy_rt_list = "select-energy-rt-list";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	c.ENG_RT_ID<br>				, c.RT_SEQ<br>				, c.PIPE_ID<br>				, c.PIPE_DESCR				<br>				, c1.PIPE_NAME<br>		from	FE_ENG_RT_BAS		a<br>				, FX_CF_INLO_MEM	b<br>				, FE_ENG_RT_PATH	c<br>					left join FE_FAC_PIPE	c1 on c1.PIPE_ID	= c.PIPE_ID<br>		where 	a.INLO_NO	= b.LOWER_INLO_NO<br>		and 	b.INLO_NO 	= $inloNo<br>		and		c.ENG_RT_ID	= a.ENG_RT_ID<br>		order by <br>				c.ENG_RT_ID<br>				, c.RT_SEQ<br><br> <br>
*/
public final String select_energy_rt_path_list = "select-energy-rt-path-list";

/**
* para : $mntnResvDate, $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.MNTN_NO<br>				, a.MNTN_TITLE<br>				, a.FAC_NO<br>				, a.FAC_TID<br>				, a.MNTN_RESV_DATE<br>				, a.MNTN_STRT_DATE<br>				, a.MNTN_FNSH_DATE<br>				, a.MNTN_CL_CD<br>				, a.MNTN_CNTC_NAME <br>				, a.MNTN_CNTC_INFO<br>				, a.MNTN_TYPE_CD<br>				, a.MNTN_RESV_CNTS<br>				, a.MNTN_CNTS<br>				, a.MNTN_FNSH_YN<br>				, a.CHG_DTM							<br>				, b.FAC_NAME<br>				, b.INLO_NO<br>				, b1.INLO_NAME<br>				, b1.INLO_TID		as FACTORY_PID<br>				<br>		from	FE_FAC_MNTN 	a<br>				, FE_FAC_BAS 	b<br>						left join FX_CF_INLO b1 on b1.INLO_NO = b.INLO_NO		<br>				, FX_CF_INLO_MEM		c				<br>		where	a.FAC_NO			= b.FAC_NO<br>		and		a.MNTN_RESV_DATE 	>= $mntnResvDate<br>		and		b.INLO_NO			= c.LOWER_INLO_NO<br>		and 	c.INLO_NO 			= $inloNo<br><br> <br>
*/
public final String select_fac_mntn_plan_list = "select-fac-mntn-plan-list";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.FAC_NO<br>				, a.DOW_CD<br>				, a.OPER_TIME_SERIES<br>				, a.OPER_HOURS<br>				, a.CHG_DTM<br>				, a1.CD_NAME		as DOW_NAME<br>		from	FE_FAC_OPER_PLAN_DTL 	a<br>						left join FX_CO_CD	a1 on a1.CD_CLASS = 'DOW_CD' and a1.CD_CODE = a.DOW_CD<br>				, FE_FAC_OPER_PLAN 		b<br>				, FE_FAC_BAS			c				<br>				, FX_CF_INLO_MEM		d				<br>		where	a.FAC_NO	= b.FAC_NO<br>		and		b.FAC_NO	= c.FAC_NO 	<br>		and		c.INLO_NO	= d.LOWER_INLO_NO<br>		and 	d.INLO_NO 	= $inloNo<br><br> <br>
*/
public final String select_fac_oper_plan_dtl_list = "select-fac-oper-plan-dtl-list";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.FAC_NO<br>				, a.IN_PEOPLE_NUM<br>				, a.OPER_STRT_DATE<br>				, a.OPER_FNSH_DATE<br>				, a.DAILY_OPER_YN<br>				, a.OPER_PLAN_DESCR<br>				, a.CHG_DTM				<br>				, b.FAC_NAME<br>				, b.INLO_NO<br>				, b1.INLO_NAME<br>				, b1.INLO_TID		as FACTORY_PID		<br>		from	FE_FAC_OPER_PLAN 	a<br>				, FE_FAC_BAS		b<br>						left join FX_CF_INLO 	b1 on b1.INLO_NO = b.INLO_NO<br>				, FX_CF_INLO_MEM	c				<br>		where	a.FAC_NO	= b.FAC_NO 	<br>		and		b.INLO_NO	= c.LOWER_INLO_NO<br>		and 	c.INLO_NO 	= $inloNo<br><br> <br>
*/
public final String select_fac_oper_plan_list = "select-fac-oper-plan-list";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.PROD_DATE<br>			, a.INLO_NO<br>			, a.FAC_NO<br>			, a.PROD_ITEM_CD<br>			, a.EXP_PROD_AMT<br>			, a.PROD_AMT<br>			, a.CHG_DTM<br>			, a1.INLO_NAME<br>			, a1.INLO_TID		as FACTORY_PID<br>			, a2.FAC_NAME<br>			, a3.CD_NAME		as PROD_ITEM_NAME<br>		from	FE_FAC_PROD_ITEM_AMT a<br>					left join FX_CF_INLO	a1 on a1.INLO_NO 	= a.INLO_NO<br>					left join FE_FAC_BAS 	a2 on a2.FAC_NO		= a.FAC_NO<br>					left join FX_CO_CD	 	a3 on a3.CD_CLASS = 'PROD_ITEM_CD' and a3.CD_CODE = a.PROD_ITEM_CD<br>				, FX_CF_INLO_MEM		b				<br>		where	a.INLO_NO	= b.LOWER_INLO_NO<br>		and 	b.INLO_NO 	= $inloNo<br><br> <br>
*/
public final String select_fac_prod_plan_list = "select-fac-prod-plan-list";

/**
* para : <br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.INLO_TID 			as FACTORY_PID<br>				, a.INLO_NAME 		as FACTORY_NM<br>				, a.INLO_DESC 		as FACTORY_DESC<br>				, ( select	 T.INLO_TID<br>					from 	FX_CF_INLO_MEM 	T2<br>							, FX_CF_INLO 	T<br>					where 	T2.LOWER_INLO_NO 	= a.INLO_NO<br>					and		T.INLO_NO 			= T2.INLO_NO<br>					and     T.INLO_CL_CD 		= 'COMPLEX' )<br>									as COMPLEX_PID<br>		from	FX_CF_INLO			a<br>		where	a.DEL_YN 			= 'N'<br>		and		a.INLO_CL_CD 		= 'PLANT'<br>		and 	INLO_TID 			is not null<br><br> <br>
*/
public final String select_factory_all = "select-factory-all";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.INLO_TID 			as FACTORY_PID<br>				, a.INLO_NAME 		as FACTORY_NM<br>				, a.INLO_DESC 		as FACTORY_DESC<br>		from	FX_CF_INLO			a<br>				, FX_CF_INLO_MEM  	b<br>		where	a.DEL_YN 			= 'N'<br>		and		a.INLO_CL_CD 		= 'PLANT'<br>		and 	INLO_TID 			is not null<br>		and		b.LOWER_INLO_NO  	= a.INLO_NO<br><br>and b.INLO_NO = $inloNo<br><br> <br>
*/
public final String select_factory_list = "select-factory-list";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select a.PIPE_ID <br>				, a.PIPE_NAME <br>				, a.PIPE_DESCR<br>				, b.INLO_TID		as COMPLEX_PID <br>				, b.INLO_NAME 		as COMPLEX_NM				<br>				, a1.INLO_TID  		as FACTORY_PID<br>				, a1.INLO_NAME 		as FACTORY_NM						<br>				, a2.ENG_TID		as ENERGY_CD<br>				, a2.ENG_NAME		as ENERGY_NM<br>				, a2.ENG_UNIT		as ENERGY_UNIT<br>		from 	FE_FAC_PIPE 		a<br>					left join FX_CF_INLO  a1 on a1.INLO_NO 	= a.LINK_INLO_NO<br>					left join FE_ENG_BAS  a2 on a2.ENG_ID	= a.ENG_ID<br>				, FX_CF_INLO		b<br>		where 	a.MNG_INLO_NO 		= b.INLO_NO<br>		and		b.INLO_TID			is not null<br><br>and a.MNG_INLO_NO = $inloNo<br><br> <br>
*/
public final String select_pipe_list = "select-pipe-list";

/**
* para : <br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select a.PIPE_ID <br>				, a.LINK_OBJ_CL_CD	<br>				, a1.CD_NAME				as LINK_OBJ_CL_NM<br>				, a.LINK_OBJ_ID<br>				, a.LINK_OBJ_NAME <br>		from 	FE_FAC_PIPE_PATH a<br>					left join FX_CO_CD a1 on a1.CD_CLASS = 'LINK_OBJ_CL_CD' and a1.CD_CODE = a.LINK_OBJ_CL_CD<br>		order by a.PIPE_ID<br>				, a.LINK_SEQ<br><br> <br>
*/
public final String select_pipe_path_all = "select-pipe-path-all";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.FAC_NAME				as FAC_NAME<br>				, a.FAC_TID				as FAC_TID<br>				, a.FAC_TYPE			as FAC_TYPE<br>				, a.FAC_DESCR			as FAC_DESCR<br>				, a.ENG_ID <br>				, a.DESGN_CAPA<br>				, a.CAPA_UNIT<br>				, a.VENDR_NAME<br>				, a.MODEL_NAME<br>				, a.FAC_CL_CD<br>				, a1.CD_NAME			as FAC_CL_NM<br>				, a.PROC_TYPE_CD<br>				, a2.CD_NAME			as PROC_TYPE_NM<br>				, a.PROD_ITEM_CD<br>				, a3.CD_NAME			as PROD_ITEM_NM<br>				, b.INLO_TID			as FACTORY_PID<br>				, b.INLO_NAME			as FACTORY_NM<br>		from 	FE_FAC_BAS 		a<br>					left join FX_CO_CD a1 on a1.CD_CLASS = 'FAC_CL_CD' and a1.CD_CODE = a.FAC_CL_CD<br>					left join FX_CO_CD a2 on a2.CD_CLASS = 'PROC_TYPE_CD' and a2.CD_CODE = a.PROC_TYPE_CD<br>					left join FX_CO_CD a3 on a3.CD_CLASS = 'PROD_ITEM_CD' and a3.CD_CODE = a.PROD_ITEM_CD<br>				, FX_CF_INLO	b<br>		where	a.PROC_TYPE_CD	!= 'ENERGY'<br>		and		a.DEL_YN 		= 'N'<br>		and		b.INLO_NO 		= a.INLO_NO<br><br>and exists ( select 1 from FX_CF_INLO_MEM b1 where b1.INLO_NO = $inloNo and<br>			b.INLO_NO = b1.LOWER_INLO_NO )<br><br> <br>
*/
public final String select_prod_facility_list = "select-prod-facility-list";

/**
* para : <br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.MO_TID			as DEVICE_PID<br>				, a.MO_NAME			as DEVICE_NM<br>				, a.MO_TYPE			as DEVICE_TYPE<br>				, c.MODEL_NAME		as DEVICE_MODEL_NM<br>				, d.INLO_TID		as FACTORY_PID		<br>				, b.PIPE_ID			as PIPE_ID<br>				, b1.ENG_TID		as ENERGY_CODE<br>				, b1.ENG_NAME		as ENERGY_NM<br>				, b1.ENG_UNIT		as ENERGY_UNIT<br>				, ( select group_concat(concat(PS_ID, ':', PS_NAME) )<br>					from	FX_PS_ITEM t1<br>					where	t1.MO_CLASS = a.MO_CLASS<br>					and		t1.MO_TYPE	= a.MO_TYPE<br>					)				as POINT_INFO<br>		from 	FX_MO			a<br>				, FE_MO_SENSOR 	b<br>					left join FE_ENG_BAS b1 on b1.ENG_ID = b.ENG_ID<br>				, FX_CF_MODEL	c<br>				, FX_CF_INLO	d<br>		where	a.MO_NO		= b.MO_NO<br>		and		c.MODEL_NO	= a.MODEL_NO<br>		and		d.INLO_NO	= a.INLO_NO<br>		and		a.DEL_YN	= 'N'<br>		and		a.MO_TID	is not null<br>		and		d.INLO_TID	is not null<br><br> <br>
*/
public final String select_sensor_all = "select-sensor-all";

/**
* para : $trns_month, $trns_month$energy_code$source_pid$sink_pid<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.TRNS_NO                           '거래번호 '<br>		<br>		        -- , a.ENG_ID                            '에너지ID '<br>		        , d.ENG_TID  as ENERGY_CODE<br>		        , d.ENG_NAME as ENERGY_NM<br>		        		        <br>		        -- , a.INLO_NO                           '설치위치(산단) '<br>		        -- , a.SELL_INLO_NO                      '판매설치위치번호 '<br>		        -- , a.BUY_INLO_NO                       '구입설치위치번호 '<br>		        , b.INLO_TID  as SOURCE_PID<br>		        , b.INLO_NAME as SOURCE_NAME<br>		        , c.INLO_TID  as SINK_PID<br>		        , c.INLO_NAME as SINK_NAME<br>		        <br>		        , a.TRNS_STRT_DTM                     '거래시작일시 '<br>		        , a.TRNS_FNSH_DTM                     '거래종료일시 '<br>		        , a.MONTH_CNTRT_TRNS_VOL              '월계약거래량 '<br>		        , a.HOURLY_MAX_CNTRT_TRNS_VOL         '시간당최대계약거래량 '<br>		        <br>		        , a.TRNS_ST_CD                        '거래상태코드 '<br>		        , a1.CD_NAME as TRNS_ST_NM<br>		         <br>		        , a.TRNS_METHD_CD                     '거래방식코드 '<br>		        , a2.CD_NAME as TRNS_METHD_NM<br>		        <br>		        , a.UNIT_PRICE_TBL_ID                 '단가표ID '<br>		        , a.CNTRT_DOC_NUM                     '계약문서번호 '<br>		        , a.TRNS_DESCR                        '거래설명 '<br>		        , a.TRNS_CHRG                         '거래요금(삭제예정) '<br>		        , a.TRNS_AMT                          '거래량(삭제예정) '<br>		from    FE_ENG_TRANS_BAS 	a  '에너지거래기본테이블 '<br>					left join FX_CO_CD a1 on a1.CD_CLASS ='TRNS_ST_CD' and a1.CD_CODE = a.TRNS_ST_CD <br>					left join FX_CO_CD a2 on a2.CD_CLASS ='TRNS_METHD_CD' and a2.CD_CODE = a.TRNS_METHD_CD <br>				, FX_CF_INLO		b<br>				, FX_CF_INLO		c<br>				, FE_ENG_BAS		d<br>		where	a.TRNS_STRT_DTM		<= concat($trns_month, '01000000')<br>		and		a.TRNS_FNSH_DTM		>= concat($trns_month, '32000000')<br>		and		b.INLO_NO			= a.SELL_INLO_NO<br>		and		c.INLO_NO			= a.BUY_INLO_NO<br>		and		d.ENG_ID			= a.ENG_ID<br><br>and d.ENG_TID = $energy_code<br><br>and b.INLO_TID = $source_pid<br><br>and c.INLO_TID = $sink_pid<br><br> <br>
*/
public final String select_tran_02 = "select-tran-02";

/**
* para : $start_date, $end_date, $inloNo$energy_code<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.PROD_DATE<br>				, a.INLO_NO<br>				, a.ENG_ID<br>				, a.FAC_NO<br>				, a.EXP_PROD_AMT				<br>				, a1.INLO_NAME		as FACTORY_NAME<br>				, a1.INLO_TID		as FACTORY_PID<br>				, a2.ENG_TID		as ENERGY_CODE<br>				, a2.ENG_NAME		as ENERGY_NAME<br>				, a3.FAC_TID		as DEVICE_PID<br>				, a3.FAC_NAME		as FAC_NAME<br>		from	FE_ENG_PROD_STAT a<br>					left join FX_CF_INLO a1 on a1.INLO_NO = a.INLO_NO<br>					left join FE_ENG_BAS a2 on a2.ENG_ID	= a.ENG_ID<br>					left join FE_FAC_BAS a3 on a3.FAC_NO	= a.FAC_NO<br>				, FX_CF_INLO_MEM b<br>		where	a.PROD_DATE		>= $start_date<br>		and		a.PROD_DATE		<= $end_date<br>		and		b.INLO_NO		= $inloNo<br>		and		b.LOWER_INLO_NO	= a.INLO_NO<br><br>and a2.ENG_TID = $energy_code<br><br> <br>
*/
public final String select_tran_07 = "select-tran-07";

/**
* para : $start_date, $end_date, $inloNo$energy_code<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.CONS_DATE<br>				, a.INLO_NO<br>				, a.ENG_ID<br>				, a.FAC_NO<br>				, a.EXP_CONS_AMT				<br>				, a1.INLO_NAME		as FACTORY_NAME<br>				, a1.INLO_TID		as FACTORY_PID<br>				, a2.ENG_TID		as ENERGY_CODE<br>				, a2.ENG_NAME		as ENERGY_NAME<br>				, a3.FAC_TID		as DEVICE_PID<br>				, a3.FAC_NAME		as FAC_NAME<br>		from	FE_ENG_CONS_STAT a<br>					left join FX_CF_INLO a1 on a1.INLO_NO = a.INLO_NO<br>					left join FE_ENG_BAS a2 on a2.ENG_ID	= a.ENG_ID<br>					left join FE_FAC_BAS a3 on a3.FAC_NO	= a.FAC_NO<br>				, FX_CF_INLO_MEM b<br>		where	a.CONS_DATE		>= $start_date<br>		and		a.CONS_DATE		<= $end_date<br>		and		b.INLO_NO		= $inloNo<br>		and		b.LOWER_INLO_NO	= a.INLO_NO<br><br>and a2.ENG_TID = $energy_code<br><br> <br>
*/
public final String select_tran_08 = "select-tran-08";

/**
* para : $source_pid, $sink_pid, $energy_code<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>with datas as (<br>			select	a.TRNS_NO<br>					, b.INLO_TID		as SOURCE_FACTORY_PID<br>					, b.INLO_NAME		as SOURCE_FACTORY_NM<br>					, c.INLO_TID		as SINK_FACTORY_PID<br>					, c.INLO_NAME		as SINK_FACTORY_NM<br>					, d.ENG_TID			as ENERGY_CD<br>					, d.ENG_NAME		as ENERGY_NM<br>					, a.TRNS_ST_CD		as TRNS_ST_CD<br>					, a1.CD_NAME		as TRNS_ST_NM<br>			from    FE_ENG_TRANS_BAS	a<br>						left join FX_CO_CD a1 on a1.CD_CLASS = 'TRNS_ST_CD' and a1.CD_CODE = a.TRNS_ST_CD<br>					, FX_CF_INLO		b<br>					, FX_CF_INLO		c<br>					, FE_ENG_BAS		d<br>			where	b.INLO_TID		= $source_pid<br>			and		c.INLO_TID		= $sink_pid<br>			and		a.SELL_INLO_NO	= b.INLO_NO<br>			and		a.BUY_INLO_NO	= c.INLO_NO<br>			and		d.ENG_TID		= $energy_code<br>			and		a.ENG_ID		= d.ENG_ID<br>			order by TRNS_STRT_DTM desc<br>			limit 0, 1<br>		)<br>		select	a.TRNS_NO<br>--				, a.SELL_INLO_NO<br>--				, a.BUY_INLO_NO<br>				, a.TRNS_STRT_DTM<br>				, a.TRNS_FNSH_DTM<br>				, b.ENG_RT_ID<br>				, b.RT_STRT_DTM<br>				, c.*<br>		from    FE_ENG_TRANS_BAS	a<br>				, FE_ENG_TRANS_RT	b<br>				, datas				c<br>		where	a.TRNS_NO  	= b.TRNS_NO<br>		and		c.TRNS_NO	= a.TRNS_NO<br>		order by	<br>				b.RT_STRT_DTM desc<br>		limit 0, 1<br><br> <br>
*/
public final String select_tran_09_01 = "select-tran-09-01";

/**
* para : $engRtId<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.ENG_RT_ID<br>				-- , a.ENG_ID<br>				-- , a.INLO_NO<br>				, a.ENG_RT_DESCR<br>				-- , a.STRT_INLO_NO<br>				-- , a.FNSH_INLO_NO<br>				-- , a.STRT_FAC_NO<br>				-- , a.FNSH_FAC_NO<br>				-- , a.USE_YN<br>				-- , a.CHG_DTM<br>				<br>				, a1.INLO_TID		as COMPLEX_PID<br>				, a1.INLO_NAME		as COMPLEX_NM								<br>				, a2.ENG_NAME		as ENERGY_NM<br>				, a2.ENG_TID		as ENERGY_CD<br>				, a3.INLO_NAME		as SOURCE_NM<br>				, a3.INLO_TID		as SOURCE_PID<br>				, a5.FAC_NAME		as SOURCE_FAC_NM<br>				, a4.INLO_NAME		as SINK_NM<br>				, a4.INLO_TID		as SINK_PID<br>				, a6.FAC_NAME		as SINK_FAC_NM<br>				<br>		from	FE_ENG_RT_BAS		a<br>					left join FX_CF_INLO 	a1 on a1.INLO_NO 	= a.INLO_NO<br>					left join FE_ENG_BAS	a2 on a2.ENG_ID 	= a.ENG_ID<br>					left join FX_CF_INLO	a3 on a3.INLO_NO 	= a.STRT_INLO_NO<br>					left join FX_CF_INLO	a4 on a4.INLO_NO 	= a.FNSH_INLO_NO<br>					left join FE_FAC_BAS	a5 on a5.FAC_NO 	= a.STRT_FAC_NO<br>					left join FE_FAC_BAS	a6 on a6.FAC_NO 	= a.FNSH_FAC_NO<br>		where 	a.ENG_RT_ID	= $engRtId<br><br> <br>
*/
public final String select_tran_09_02 = "select-tran-09-02";

/**
* para : $engRtId<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.RT_SEQ<br>				, a2.LINK_SEQ<br>				, a.PIPE_ID<br>				, a.PIPE_DESCR				<br>				, a1.PIPE_NAME				<br>				, a2.LINK_OBJ_CL_CD<br>				, a2.LINK_OBJ_ID<br>				, a2.LINK_OBJ_NAME<br>		from	FE_ENG_RT_PATH		a<br>					left join FE_FAC_PIPE		a1 on a1.PIPE_ID	= a.PIPE_ID<br>					left join FE_FAC_PIPE_PATH	a2 on a2.PIPE_ID	= a.PIPE_ID<br>		where 	a.ENG_RT_ID	= $engRtId<br>		order by <br>				a.RT_SEQ		<br>				, a2.LINK_SEQ<br><br> <br>
*/
public final String select_tran_09_03 = "select-tran-09-03";

/**
* para : <br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.FAC_NAME			as FACILITY_NM<br>				, a.FAC_TID			as FACILITY_PID<br>				, e.ENG_TID			as ENERGY_CD<br>				, e.ENG_NAME		as ENGRGY_NM<br>				, a.VENDR_NAME		as VENDOR_NM<br>				, a.MODEL_NAME		as MODEL_NM<br>				, a1.CD_NAME		as FACILITY_CL_NM<br>				, c.INLO_TID		as FACTORY_PID<br>				, c.INLO_NAME		as FACTORY_NM<br>		from 	FE_FAC_BAS 		a<br>					left join FX_CO_CD a1 on a1.CD_CLASS = 'FAC_CL_CD' and a1.CD_CODE = a.FAC_CL_CD<br>				, FX_CF_INLO		c<br>				, FE_ENG_BAS 		e<br>		where	a.PROC_TYPE_CD	= 'ENERGY'<br>		and		a.DEL_YN 		= 'N'<br>		and		c.INLO_NO 		= a.INLO_NO<br>		and		c.INLO_TID		is not null<br>		and		e.ENG_ID		= a.ENG_ID<br><br> <br>
*/
public final String select_ut_facility_all = "select-ut-facility-all";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.FAC_NAME<br>				, a.FAC_TID<br>				, a.FAC_TYPE<br>				, a.FAC_DESCR<br>				, a.ENG_ID<br>				, a.DESGN_CAPA<br>				, a.CAPA_UNIT<br>				, a.VENDR_NAME<br>				, a.MODEL_NAME<br>				, a.FAC_CL_CD<br>				, a1.CD_NAME		as FAC_CL_NM				<br>				, b.INLO_TID		as FACTORY_PID<br>				, b.INLO_NAME		as FACTORY_NM<br>				, c.ENG_NAME 		as ENERGY_NM<br>				, c.ENG_TID			as ENERGY_CODE				<br>		from 	FE_FAC_BAS 		a<br>					left join FX_CO_CD a1 on a1.CD_CLASS = 'FAC_CL_CD' and a1.CD_CODE = a.FAC_CL_CD<br>				, FX_CF_INLO		b<br>				, FE_ENG_BAS 		c<br>		where	a.PROC_TYPE_CD	= 'ENERGY'<br>		and		a.DEL_YN 		= 'N'<br>		and		b.INLO_NO		= a.INLO_NO<br>		and		c.ENG_ID		= a.ENG_ID<br><br>and exists ( select 1 from FX_CF_INLO_MEM b1 where b1.INLO_NO = $inloNo and<br>			b.INLO_NO = b1.LOWER_INLO_NO )<br><br> <br>
*/
public final String select_ut_facility_list = "select-ut-facility-list";

/**
* para : $start_date, $end_date, $inloNo, $energy_code<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.CONS_DATE                         '소비일시 '<br>		        --, a.INLO_NO                           '설치위치번호 '<br>		        --, a.ENG_ID                            '에너지ID '<br>		        --, a.FAC_NO                            '설비번호 '<br>		        , a.EXP_CONS_AMT                      '예상소비량 '<br>		        , a.CONS_AMT                          '소비량 '<br>		        <br>		        , a1.INLO_NAME				as FACTORY_NAME<br>		        , ifnull(a1.INLO_TID, concat('TS', a.INLO_NO))<br>		        							as FACTORY_PID<br>		        , ifnull(a2.FAC_TID, concat('TSFAC', a.FAC_NO))<br>		        							as DEVICE_PID<br>		        , a2.FAC_NAME				as DEVICE_NAME<br>		        , a3.ENG_NAME				as ENERGY_NAME<br>		        , a3.ENG_TID				as ENERGY_CODE<br>		from    FE_ENG_CONS_STAT 	a  '에너지소비통계테이블 '<br>					left join FX_CF_INLO 	a1 on a1.INLO_NO 	= a.INLO_NO <br>					left join FE_FAC_BAS	a2 on a2.FAC_NO		= a.FAC_NO 		<br>					left join FE_ENG_BAS	a3 on a3.ENG_ID		= a.ENG_ID<br>				, FX_CF_INLO_MEM 	b<br>		where	a.CONS_DATE		>= $start_date<br>		and		a.CONS_DATE		<= $end_date<br>		and		b.INLO_NO		= $inloNo<br>		and		b.LOWER_INLO_NO	= a.INLO_NO<br>		and		a3.ENG_TID		= $energy_code<br><br> <br>
*/
public final String select_val_05 = "select-val-05";

/**
* para : $start_date, $end_date, $inloNo, $energy_code<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.CONS_DTM                          '소비일시 '<br>		        --, a.INLO_NO                           '설치위치번호 '<br>		        --, a.ENG_ID                            '에너지ID '<br>		        --, a.FAC_NO                            '설비번호 '<br>		        , a.EXP_CONS_AMT                      '예상소비량 '<br>		        , a.CONS_AMT                          '소비량 '<br>		        , a1.INLO_NAME				as FACTORY_NAME<br>		        , ifnull(a1.INLO_TID, concat('TS', a.INLO_NO))<br>		        							as FACTORY_PID<br>		        , ifnull(a2.FAC_TID, concat('TSFAC', a.FAC_NO))<br>		        							as DEVICE_PID<br>		        , a2.FAC_NAME				as DEVICE_NAME<br>		        , a3.ENG_NAME				as ENERGY_NAME<br>		        , a3.ENG_TID				as ENERGY_CODE<br>		        <br>		from    FE_ENG_CONS_AMT 	a  '에너지소비량테이블 '<br>					left join FX_CF_INLO 	a1 on a1.INLO_NO = a.INLO_NO <br>					left join FE_FAC_BAS	a2 on a2.FAC_NO = a.FAC_NO<br>					left join FE_ENG_BAS	a3 on a3.ENG_ID		= a.ENG_ID					<br>				, FX_CF_INLO_MEM 	b<br>		where	a.CONS_DTM		>= concat($start_date, '000000')<br>		and		a.CONS_DTM		<= concat($end_date, '235959')<br>		and		b.INLO_NO		= $inloNo<br>		and		b.LOWER_INLO_NO	= a.INLO_NO<br>		and		a.DTM_TYPE		= 'M15'<br>		and		a3.ENG_TID		= $energy_code<br><br> <br>
*/
public final String select_val_06 = "select-val-06";

/**
* para : $start_date, $end_date, $inloNo, $energy_code<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.PROD_DATE                         '생산일시 '<br>		        --, a.INLO_NO                           '설치위치번호 '<br>		        --, a.ENG_ID                            '에너지ID '<br>		        --, a.FAC_NO                            '설비번호 '<br>		        , a.EXP_PROD_AMT                      '예상생산량 '<br>		        , a.PROD_AMT                          '생산량 '<br>		        <br>		        , a1.INLO_NAME				as FACTORY_NAME<br>		        , ifnull(a1.INLO_TID, concat('TS', a.INLO_NO))<br>		        							as FACTORY_PID<br>		        , ifnull(a2.FAC_TID, concat('TSFAC', a.FAC_NO))<br>		        							as DEVICE_PID<br>		        , a2.FAC_NAME				as DEVICE_NAME<br>		        , a3.ENG_NAME				as ENERGY_NAME<br>		        , a3.ENG_TID				as ENERGY_CODE<br>		        		        <br>		from    FE_ENG_PROD_STAT a  '에너지생산통계테이블 '<br>					left join FX_CF_INLO 	a1 on a1.INLO_NO = a.INLO_NO <br>					left join FE_FAC_BAS	a2 on a2.FAC_NO = a.FAC_NO<br>					left join FE_ENG_BAS	a3 on a3.ENG_ID		= a.ENG_ID<br>							<br>				, FX_CF_INLO_MEM 	b<br>		where	a.PROD_DATE		>= $start_date<br>		and		a.PROD_DATE		<= $end_date<br>		and		b.INLO_NO		= $inloNo<br>		and		b.LOWER_INLO_NO	= a.INLO_NO<br>		and		a3.ENG_TID		= $energy_code<br><br> <br>
*/
public final String select_val_07 = "select-val-07";

/**
* para : $start_date, $end_date, $inloNo, $energy_code<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select    a.PROD_DTM                          '생산일시 '<br>		        -- , a.INLO_NO                           '설치위치번호 '<br>		        -- , a.ENG_ID                            '에너지ID '<br>		        -- , a.FAC_NO                            '설비번호 '<br>		        , a.EXP_PROD_AMT                      '예상생산량 '<br>		        , a.PROD_AMT                          '생산량 '<br>		        , a1.INLO_NAME				as FACTORY_NAME<br>		        , ifnull(a1.INLO_TID, concat('TS', a.INLO_NO))<br>		        							as FACTORY_PID<br>		        , ifnull(a2.FAC_TID, concat('TSFAC', a.FAC_NO))<br>		        							as DEVICE_PID<br>		        							       <br>		        , a3.ENG_NAME				as ENERGY_NM<br>		        , a3.ENG_TID				as ENERGY_CODE<br>		        <br>		from    FE_ENG_PROD_AMT 	a  '에너지생산량테이블 '<br>					left join FX_CF_INLO 	a1 on a1.INLO_NO = a.INLO_NO <br>					left join FE_FAC_BAS	a2 on a2.FAC_NO = a.FAC_NO<br>					left join FE_ENG_BAS	a3 on a3.ENG_ID		= a.ENG_ID<br>					<br>				, FX_CF_INLO_MEM 	b<br>		where	a.PROD_DTM		>= concat($start_date, '000000')<br>		and		a.PROD_DTM		<= concat($end_date, '235959')<br>		and		b.INLO_NO		= $inloNo<br>		and		b.LOWER_INLO_NO	= a.INLO_NO<br>		and		a.DTM_TYPE		= 'M15'<br>		and		a3.ENG_TID		= $energy_code<br><br> <br>
*/
public final String select_val_08 = "select-val-08";

/**
* para : $inloNo<br>
* result : result_map=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select	a.PS_ID<br>				, c.PS_NAME<br>				, a.CUR_COLL_VAL	as COLLECTED_VALUE<br>				, a.CUR_COLL_DTM	as COLLECTED_DATE<br>				, b.MO_TID			as DEVICE_PID<br>				, b.MO_NAME			as DEVICE_NM<br>				, b.MO_TYPE			as DEVICE_TYPE<br>				, d.INLO_TID		as FACTORY_PID<br>				, d.INLO_NAME		as FACTORY_NM<br>		from    FX_V_ACUR 			a<br>				, FX_MO				b<br>				, FX_PS_ITEM		c<br>				, FX_CF_INLO		d<br>		where	a.MO_NO 		= b.MO_NO<br>		and		a.PS_ID			= c.PS_ID<br>		and		b.INLO_NO		= d.INLO_NO<br>		and		exists ( select 1 from FX_CF_INLO_MEM t1 where t1.INLO_NO = $inloNo and t1.LOWER_INLO_NO = b.INLO_NO )<br>		and		a.PS_ID in ( 'E01V2', 'E02V2' )<br><br> <br>
*/
public final String select_val_09 = "select-val-09";

}