package com.onenow.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum InvType {
	STOCK, INDEX, CALL, PUT, SPOT, ONDEMAND, RESERVED, FUTURE;
	
	@JsonCreator
    public static InvType forValue(String value) {
		if (value.toLowerCase().equals("stock")) {
			return STOCK;
		} 
		if (value.toLowerCase().equals("index")) {
			return INDEX;
		} 
		if (value.toLowerCase().equals("call")) {
			return CALL;
		} 
		if (value.toLowerCase().equals("put")) {
			return PUT;
		} 
		if (value.toLowerCase().equals("spot")) {
			return SPOT;
		} 
		if (value.toLowerCase().equals("ondemand")) {
			return ONDEMAND;
		} 
		if (value.toLowerCase().equals("reserved")) {
			return RESERVED;
		} 
		return FUTURE;
    }
}
