package com.onenow.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum InvDataSource {
	IB, SCHWAB, ETRADE, AMERITRADE;
	

	@JsonCreator
    public static InvDataSource forValue(String value) {
		if (value.toLowerCase().equals("ib")) {
			return IB;
		} 
		if (value.toLowerCase().equals("schwab")) {
			return SCHWAB;
		}
		if (value.toLowerCase().equals("etrade")) {
			return ETRADE;
		}
		return AMERITRADE;
    }
}
