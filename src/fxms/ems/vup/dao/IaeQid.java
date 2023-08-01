package fxms.ems.vup.dao;

/**
* File : deploy/conf/sql/fxms/ems/vup/iae.xml<br>
* @since 20230724165022
* @author subkjh 
*
*/


public class IaeQid { 

/** 쿼리 모임 화일명. deploy/conf/sql/fxms/ems/vup/iae.xml*/
public static final String QUERY_XML_FILE = "deploy/conf/sql/fxms/ems/vup/iae.xml";

public IaeQid() { 
} 
/**
* para : $history_id<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	<br>				history_id<br>				, timestamp<br>				, path<br>				, source_company<br>				, source_plant<br>				, sink_company<br>				, sink_plant<br>				, energy_category<br>				, volume<br>				, unit<br>		from	iae_air_compressed_prediction<br>		where 	history_id > $history_id<br><br> <br>
*/
public final String select_iae_air_compressed_prediction = "select_iae_air_compressed_prediction";

/**
* para : $history_id<br>
* result : RESULT_MAP=java.util.HashMap<br>
* ---------------------------------------------------------------------------------- <br>
* database : null<br>
* sql <br><br>
 * <br>select 	<br>				history_id<br>				, timestamp<br>				, path<br>				, source_company<br>				, source_plant<br>				, sink_company<br>				, sink_plant<br>				, energy_category<br>				, volume<br>				, unit<br>		from	iae_steam_prediction<br>		where 	history_id > $history_id<br><br> <br>
*/
public final String select_iae_steam_prediction = "select_iae_steam_prediction";

}