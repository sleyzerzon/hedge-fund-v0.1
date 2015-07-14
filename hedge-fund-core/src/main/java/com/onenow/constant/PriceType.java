package com.onenow.constant;

// import com.fasterxml.jackson.annotation.JsonCreator;

public enum PriceType {
	BID, 		// offered
	ASK, 		// asked
	TRADED, 	// actually transacted
	CALCULATED;	// calculated for indices	
	
//	@JsonCreator
//    public static PriceType forValue(String value) {
//		if (value.toLowerCase().equals("bid")) {
//			return BID;
//		} 
//		if (value.toLowerCase().equals("ask")) {
//			return ASK;
//		}
//		if (value.toLowerCase().equals("traded")) {
//			return TRADED;
//		}
//		return CALCULATED;
//    }
}
