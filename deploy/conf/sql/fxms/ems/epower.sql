-- 전기요금목록조회	
select 	a.EPWR_PRICE_ID			as EPWR_PRICE_ID		/* 전기요금ID */
		, a.EPWR_PRICE_NAME		as EPWR_PRICE_NAME		/* 전기요금명 */
		, b.BASIC_PRICE			as BASIC_PRICE			/* 기본요금(원/kW) */
		, a.PRICE_DESCR			as PRICE_DESCR			/* 요금설명 */
from	FE_EPWR_PRICE	a
		, FE_EPWR_PRICE_BASE b
where 	a.EPWR_PRICE_ID		= b.EPWR_PRICE_ID
and		a.USE_YN			= 'Y'
and		b.END_EFFCT_DATE	= '99991231'				/* 기본요금의 유효 기간 */
;
	
-- 공장별사용요금제조회
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
select  a.INLO_NO
		, a.INLO_NAME
		, a2.*
from	FX_CF_INLO a
			left join FE_EPWR_INLO 	a1 on a1.INLO_NO 		= a.INLO_NO
			left join datas 		a2 on a2.EPWR_PRICE_ID 	= a1.EPWR_PRICE_ID
where	a.INLO_CL_CD = 'PLANT'					
;	

-- 한 시점에서의 전력량 요금 구하기
select 	a.EPWR_PRICE_ID			as EPWR_PRICE_ID		/* 전기요금ID */
		, a.EPWR_PRICE_NAME		as EPWR_PRICE_NAME		/* 전기요금명 */
		, b.BASIC_PRICE			as BASIC_PRICE			/* 기본요금(원/kW) */
		, c.DESCR				as TMLOAD_DESCR			/* 부하 */
		, d.EPWR_TM_LOAD_CD		as EPWR_TM_LOAD_CD		/* 시간대부하코드 */
		, ( select CD_NAME from FX_CO_CD d1 where d1.CD_CLASS = 'EPWR_TM_LOAD_CD' and d1.CD_CODE = d.EPWR_TM_LOAD_CD )
								as EPWR_TM_LOAD_NM		/* 시간대부하명 */
		, d.PRICE				as UNIT_PRICE			/* 단가 */
		, c.MMDD
		, c.HH
from	FE_EPWR_PRICE			a
		, FE_EPWR_PRICE_BASE 	b
		, FE_EPWR_PRICE_TMLOAD 	c
		, FE_EPWR_PRICE_UNIT 	d
where 	a.EPWR_PRICE_ID		= b.EPWR_PRICE_ID
and		b.END_EFFCT_DATE	= '99991231'				/* 기본요금의 유효 기간 */
and		c.MMDD				= '07**'					/* 7월. 일자가 적용되면 ** 자리에 일자 넣음 */
and		c.HH				= '13'						/* 13시 시간대 */
and		c.END_EFFCT_DATE	= '99991231'
and		d.EPWR_PRICE_ID		= a.EPWR_PRICE_ID
and		d.MM 				= '07'						/* 7월 */
and		d.EPWR_TM_LOAD_CD 	in ( c.EPWR_TM_LOAD_CD, '0')
and		d.END_EFFCT_DATE	= '99991231'				/* 단가의 유효 기간 */


-- 공장에 전기요금ID 적용하기
insert into FE_EPWR_INLO ( 
	INLO_NO
	, START_EFFCT_DATE
	, END_EFFCT_DATE
	, THRE_VAL
	, REG_USER_NO
	, REG_DTM
	, CHG_USER_NO
	, CHG_DTM
) 
select 	a.INLO_NO
		, '20230101'
		, '99991231'
		,  80
		,  1
		, 20230719134100 
		,  1
		, 20230719134100 
from 	FX_CF_INLO a
where	a.INLO_CL_CD = 'PLANT'
and		a.DEL_YN = 'N'
