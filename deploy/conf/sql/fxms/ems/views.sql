-- 공장 목록
create or replace view V_PLANT as
	with datas as (
		select 	a.INLO_NO		
				, ( select	 max(T.INLO_NO)
					from 	FX_CF_INLO_MEM 	T2
							, FX_CF_INLO 	T
					where 	T2.LOWER_INLO_NO 	= a.INLO_NO
					and		T.INLO_NO 			= T2.INLO_NO
					and     T.INLO_CL_CD 		= 'COMPLEX' )	
							as COMPLEX_INLO_NO	
				, ( select	 max(T.INLO_NO)
					from 	FX_CF_INLO_MEM 	T2
							, FX_CF_INLO 	T
					where 	T2.LOWER_INLO_NO 	= a.INLO_NO
					and		T.INLO_NO 			= T2.INLO_NO
					and     T.INLO_CL_CD 		= 'COMPANY' )	
							as COMPANY_INLO_NO	
				, ( select	 max(T.INLO_NO)
					from 	FX_CF_INLO_MEM 	T2
							, FX_CF_INLO 	T
					where 	T2.LOWER_INLO_NO 	= a.INLO_NO
					and		T.INLO_NO 			= T2.INLO_NO
					and     T.INLO_CL_CD 		= 'PLANT' )	
							as PLANT_INLO_NO	
		from 	FX_CF_INLO a
		where   a.DEL_YN = 'N'
	)
	select	
			plant.INLO_NO			as PLANT_NO
			, plant.INLO_TID		as PLANT_TID
			, plant.INLO_NAME		as PLANT_NAME 
			, company.INLO_NO		as COMPANY_NO
			, company.INLO_NAME		as COMPANY_NAME
			, company.INLO_TID		as COMPANY_TID
			, complex.INLO_NAME		as COMPLEX_NAME
			, complex.INLO_NO		as COMPLEX_NO
			, complex.INLO_TID		as COMPLEX_TID
			, inlo.INLO_NO
			, inlo.INLO_CL_CD
			, inlo.INLO_TYPE_CD
			, inlo.INLO_LEVEL_CD
	from 	datas a
				left join FX_CF_INLO complex 	on complex.INLO_NO = a.COMPLEX_INLO_NO
				left join FX_CF_INLO company 	on company.INLO_NO = a.COMPANY_INLO_NO
				left join FX_CF_INLO plant 		on plant.INLO_NO = a.PLANT_INLO_NO
			, FX_CF_INLO inlo
	where	a.INLO_NO			= inlo.INLO_NO
	and		inlo.DEL_YN			= 'N'
	and		inlo.INLO_CL_CD		= 'PLANT'		
;		

-- 설비 목록 및 설비의 설치된 계측기
create or replace view V_FACILITY as
	with datas as (
		select 	a.MO_NO
				, b.SENSR_NAME
				, b.FAC_NO
				, a.MO_TYPE
		from 	FX_MO			a
				, FE_MO_SENSOR	b
		where	a.MO_NO = b.MO_NO		
	)	
	, b as ( select * from datas where MO_TYPE = '전력계' )
	, c as ( select * from datas where MO_TYPE = '온도계' )
	, d as ( select * from datas where MO_TYPE = '습도계' )
	select 	a.FAC_NO
			, a.FAC_NAME
			, a.FAC_CL_CD
			, a.FAC_TID
			, a.FAC_TYPE
			, a.FAC_DESCR
			, e.*
			, b.MO_NO			as EPWR_MO_NO
			, b.SENSR_NAME		as EPWR_MO_NAME
			, c.MO_NO			as TEMP_MO_NO
			, c.SENSR_NAME		as TEMP_MO_NAME
			, d.MO_NO			as HUMI_MO_NO
			, d.SENSR_NAME		as HUMI_MO_NAME
	from	FE_FAC_BAS a
				left join b on b.FAC_NO = a.FAC_NO		
				left join c on c.FAC_NO = a.FAC_NO		
				left join d on d.FAC_NO = a.FAC_NO
				left join V_PLANT e on e.INLO_NO = a.INLO_NO
;		

-- 공장별 전력계
create or replace view V_PLANT_EPWR as
	with datas as (
		select 	a.MO_NO
				, b.SENSR_NAME
				, a.MO_TYPE
				, a.INLO_NO
		from 	FX_MO			a
				, FE_MO_SENSOR	b
		where	a.MO_NO 	= b.MO_NO
		and		a.MO_TYPE	in ( '전력계', '피크전력계' )
		and		b.FAC_NO	is null
	)
	, a1 as ( select * from datas where MO_TYPE = '전력계' )
	, a2 as ( select * from datas where MO_TYPE = '피크전력계' )
	select	a.*
			, a1.MO_NO			as EPWR_MO_NO
			, a1.SENSR_NAME		as EPWR_MO_NAME
			, a1.MO_TYPE		as EPWR_MO_TYPE
			, a2.MO_NO			as PEEK_EPWR_MO_NO
			, a2.SENSR_NAME		as PEEK_EPWR_MO_NAME
			, a2.MO_TYPE		as PEEK_EPWR_MO_TYPE
	from	V_PLANT	a	
			left join a1 on a1.INLO_NO = a.INLO_NO
			left join a2 on a2.INLO_NO = a.INLO_NO
;		

-- 계약 전력 요금제
create or replace view V_PLANT_EPWR_PRICE as
	with datas as (
		select 	a.EPWR_PRICE_ID			as EPWR_PRICE_ID		/* 전기요금ID */
				, a.EPWR_PRICE_NAME		as EPWR_PRICE_NAME		/* 전기요금명 */
				, b.BASIC_PRICE			as BASIC_PRICE			/* 기본요금(원/kW) */
				, a.PRICE_DESCR			as PRICE_DESCR			/* 요금설명 */
		from	FE_EPWR_PRICE	a
				, FE_EPWR_PRICE_BASE b
		where 	a.EPWR_PRICE_ID		= b.EPWR_PRICE_ID
		and		a.USE_YN			= 'Y'
		and		b.END_EFFCT_DATE	= '99991231'				/* 기본요금의 유효 기간 */
	)
	select  a1.INLO_NO
			, a1.INLO_NAME
			, a2.*
			, a.CNTRT_EPWR				/* 계약 전력 */
	from	FE_EPWR_INLO_PRICE a
				left join FX_CF_INLO 	a1 on a1.INLO_NO 		= a.INLO_NO
				left join datas 		a2 on a2.EPWR_PRICE_ID 	= a.EPWR_PRICE_ID
	where 	a.END_EFFCT_DATE	= '99991231'				/* 유효 기간 */
;

-- 공장, 게이트웨이, 노드 정보
create or replace view V_PLANT_SENSOR as
select 	a.INLO_NO				as PLANT_NO
		, a.INLO_TID			as PLANT_TID
		, a.INLO_NAME			as PLANT_NAME
		, a.INLO_ALL_NAME		as PLANT_ALL_NAME
		, b.MO_NO 				as GW_NO
		, b.MO_TID				as GW_TID
		, b.MO_NAME				as GW_NAME
		, b1.NODE_IP_ADDR		as GW_IP_ADDR
		, c.MO_NO 				as SENSOR_NO
		, c.MO_TID 				as SENSOR_TID
		, c.MO_NAME				as SENSOR_NAME
		, c.MO_TYPE				as SENSOR_TYPE
		, c.MO_ADD_JSON			as MO_ADD_JSON
from	FX_CF_INLO 	a
		, FX_MO		b
			left join FX_MO_NODE b1 on b1.MO_NO = b.MO_NO
		, FX_MO		c
			left join FE_MO_SENSOR c1 on c1.MO_NO = c.MO_NO
where	a.INLO_NO 	= b.INLO_NO
and		b.MO_CLASS 	= 'NODE'
and		b.DEL_YN	= 'N'
and		c1.PLC_MO_NO = b.MO_NO
;
