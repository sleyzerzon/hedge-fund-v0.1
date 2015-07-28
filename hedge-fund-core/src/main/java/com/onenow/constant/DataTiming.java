package com.onenow.constant;

public enum DataTiming {
	RT,	// REALTIME 
	HISTORY, 
	STREAM;
	

//	@JsonCreator
//    public static InvDataTiming forValue(String value) {
//		if (value.toLowerCase().equals("realtime")) {
//			return REALTIME;
//		} 
//		if (value.toLowerCase().equals("streaming")) {
//			return STREAMING;
//		} 
//		return HISTORICAL;
//    }
}
