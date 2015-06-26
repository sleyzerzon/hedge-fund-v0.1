package com.onenow.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TradeType {
	BUY, SELL, TRADED, CALCULATED, CLOSE;
	
	@JsonCreator
    public static TradeType forValue(String value) {
		if (value.toLowerCase().equals("buy")) {
			return BUY;
		} 
		if (value.toLowerCase().equals("sell")) {
			return SELL;
		}
		if (value.toLowerCase().equals("traded")) {
			return TRADED;
		}
		if (value.toLowerCase().equals("calculated")) {
			return CALCULATED;
		}
		return CLOSE;
    }
}
