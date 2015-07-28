package com.onenow.data;

import com.onenow.constant.DataType;

public class EventActivityGreekHistory extends EventActivity {

	public EventActivityGreekHistory() {
		
		super();
		
		super.dataType = DataType.GREEK_HIST;

	}
	
	// analogous to EventActivityPriceHistory, but for any calculated Data
	// TODO: request this data in historian main
//	updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.MIDPOINT);							
//	updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.HISTORICAL_VOLATILITY);							
//	updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.OPTION_IMPLIED_VOLATILITY);											

}
