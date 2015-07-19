package com.onenow.constant;

// import com.fasterxml.jackson.annotation.JsonCreator;

public enum PriceType {
	// offered
	BID, BID_EXCH, BID_OPTION,
	// asked
	ASK, ASK_EXCH, ASK_OPTION,
	// actually transacted
	TRADED, TRADED_EXCH, TRADED_OPTION,	
	// calculated for indices
	CALCULATED,	
	// other
	AUCTION_PRICE,
	MARK_PRICE, 
	INDEX_FUTURE_PREMIUM,
		
}
