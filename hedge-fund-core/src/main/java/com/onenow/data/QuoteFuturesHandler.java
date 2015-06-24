package com.onenow.data;

import java.util.logging.Level;

import com.onenow.portfolio.BusController.IEfpHandler;
import com.onenow.util.Watchr;

public class QuoteFuturesHandler extends QuoteSharedHandler implements IEfpHandler {

	public QuoteFuturesHandler() {
		
	}
	
//	@Override
//	public void tickGeneric(TickType tickType, double price) {
//
//	}
	
	public void tickEFP(int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, 
						String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		
		// TODO: BID_EFP_COMPUTATION, ASK_EFP_COMPUTATION, LAST_EFP_COMPUTATION, OPEN_EFP_COMPUTATION, HIGH_EFP_COMPUTATION, LOW_EFP_COMPUTATION, CLOSE_EFP_COMPUTATION
		switch( tickType) {			
		default:
			Watchr.log(Level.WARNING, 	"$$$$$ tickEFP: " + " -tickType " + tickType +
					" -for " + investment.toString());
			break;	
		}

	}
}
