package com.onenow.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum InvDataTiming {
	REALTIME, HISTORICAL;
	

	@JsonCreator
    public static InvDataTiming forValue(String value) {
		if (value.toLowerCase().equals("realtime")) {
			return REALTIME;
		} 
		return HISTORICAL;
    }
}
