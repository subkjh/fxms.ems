package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/enitt_loc.xml<br>
* @since 20230522174934
* @author subkjh 
*
*/


public class EnittLocQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/vup/enitt_loc.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/vup/enitt_loc.xml";

public EnittLocQid() { 
} 
/**
* para : $notiId<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.CONTAINER_ID		as FACTORY_PID<br>				, a.NOTI_ID			as NOTIFY_ID<br>				, a.NOTI_TYPE		as NOTIFY_TYPE<br>				, a.NOTI_STATE		as NOTIFY_STATE<br>				, a.NOTI_COUNT		as NOTIFY_COUNT<br>				, a.MESSAGE<br>				, a.LOGS<br>				, DATE_FORMAT(a.CREATED_DT, '%Y%m%d%H%i%S')		as CREATED_DT<br>				, DATE_FORMAT(a.UPDATED_DT, '%Y%m%d%H%i%S')		as UPDATED_DT<br>				<br>				, b.DEVICE_ID		as DEVICE_PID<br>				, b.POINT_ID		as POINT_PID<br>				, b.RULE_ID<br>				, b.RULE_NM<br>				, b.RULE_DESC<br>				, b.RULE_MESSAGE<br>				, b.SEVERITY				<br>		from 	hist_notification a<br> 				, info_rule_master b<br> 		where 	a.RULE_ID = b.RULE_ID 	<br> 		and		a.NOTI_ID > $notiId<br> 		order by a.NOTI_ID<br> 		limit 3<br><br> <br>
*/
public final String select_alarm_list = "select-alarm-list";

/**
* para : <br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.CONTAINER_NM<br>				, a.CONTAINER_DESC <br>				, c.CONTAINER_ID<br>				, c.DEVICE_GROUP<br>				, c.DEVICE_ID<br>				, c.DEVICE_NM<br>				, c.DEVICE_TAG<br>				, c.DEVICE_DESC<br>				, c.DEVICE_TYPE<br>				, c.DEVICE_LOC<br>				, c.MODEL<br>				, c.MODEL_NO<br>				, c.MANUFACTURER<br>				, c.SPECIFICATION<br>				, c.CONN_PROTOCOL<br>				, c.CONN_ADR<br>				, c.CONN_PORT				<br>				, d.POINT_ID<br>				, d.POINT_NM<br>				, d.POINT_TAG<br>				, d.POINT_DESC<br>				, d.POINT_TYPE<br>				, d.RW<br>				, d.VAL_TYPE<br>				, d.DEF_VAL<br>				, d.MIN_VAL<br>				, d.MAX_VAL<br>				, d.UNIT<br>				, d.SCALE<br>		from 	info_container_master 		a<br>				, info_data_match_backup  	b<br>				, info_device_master   		c<br>				, info_point_master 		d<br>		where 	a.CONTAINER_ID  = b.CONTAINER_ID<br>		and 	b.DEVICE_ID  	= c.DEVICE_ID <br>		and 	b.POINT_ID 		= d.POINT_ID<br><br> <br>
*/
public final String select_conf_list = "select-conf-list";

/**
* para : $collectedDt<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	a.*<br>				, DATE_FORMAT(a.collected_dt, '%Y%m%d%H%i%S') as COL_DATE<br>		from 	data_d010102001 a<br>		where	a.collected_dt > STR_TO_DATE($collectedDt, '%Y%m%d%H%i%S')<br><br> <br>
*/
public final String select_value_list = "select-value-list";

}