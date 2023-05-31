package fxms.ems.vup.dto;

import java.util.List;

/**
 * factory_id String 필수 공장 아이디<br>
 * devices ArrayList 필수 설비 목록<br>
 * 
 * device_pid String 필수 설비 아이디<br>
 * device_state int 필수 설비 상태 (1: 정상 수집, 0: 미수집)<br>
 * collected_dt Timestamp 필수 계측일시<br>
 * * data ArrayList 필수 데이터 목록<br>
 * 
 * pid String 필수 관제점 아이디<br>
 * value Object 필수 관제점 값<br>
 * 
 * @author subkjh
 *
 */
public class Value01Dto {

	public List<Value01Sub1Dto> facilities;

	public List<Value01Sub1Dto> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<Value01Sub1Dto> facilities) {
		this.facilities = facilities;
	}

}
