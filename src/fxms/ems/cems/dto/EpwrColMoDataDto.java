package fxms.ems.cems.dto;

public class EpwrColMoDataDto {

	public long facNo;
	public long moNo;
	public String moName;
	public String moType;

	public EpwrColDataDto lastData;
	public EpwrColDataDto prevData;

	/**
	 * * <<< 전력량계 상태 정의>>>
	 * 
	 * 가동중(1) : 유효전력량 DIFF > 0 <br>
	 * 가동중지(2) : 유효전력량 DIFF = 0 and 전압 3상의 합 > 0 <br>
	 * 데이터미수신(3) : 가동중, 가동중지 상태외 상태, 가동중지를 5번 반복시 데이터 미수신으로 판단 <br>
	 * 
	 * @return
	 */
	public int getState() {

		if (lastData != null && prevData != null) {
			if (lastData.kwh > prevData.kwh) {
				return 1;
			}
			if (lastData.volt > 0) {
				return 2;
			}
		} else if (lastData != null) {
			return 1;
		}

		return 3;
	}

}
