package com.onenow.data;

import com.onenow.constant.StreamingData;

public class EventActivityGreekHistory extends EventActivity {

	public EventActivityGreekHistory() {
		
		super();
		
		super.streamingData = StreamingData.GREEK_HISTORY;

	}
	
	// analogous to EventActivityPriceHistory, but for any calculated Data
	// TODO: request this data in historian main
//	updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.MIDPOINT);							
//	updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.HISTORICAL_VOLATILITY);							
//	updateL2HistoryFromL3(inv, toDashedDate, WhatToShow.OPTION_IMPLIED_VOLATILITY);											

}
