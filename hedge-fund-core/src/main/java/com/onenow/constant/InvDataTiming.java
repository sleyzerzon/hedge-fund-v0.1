package com.onenow.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum InvDataTiming {
	REALTIME, HISTORICAL, STREAMING;
	

	@JsonCreator
    public static InvDataTiming forValue(String value) {
		if (value.toLowerCase().equals("realtime")) {
			return REALTIME;
		} 
		if (value.toLowerCase().equals("streaming")) {
			return STREAMING;
		} 
		return HISTORICAL;
    }
}
