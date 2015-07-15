package com.onenow.data;

import java.util.ArrayList;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.MemoryLevel;
import com.onenow.constant.StreamName;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;
import com.onenow.io.BusSystem;
import com.onenow.portfolio.BusController.IHistoricalDataHandler;
import com.onenow.portfolio.BusController.IRealTimeBarHandler;
import com.onenow.portfolio.BusController.ITopMktDataHandler;

import java.util.logging.Level;

import com.onenow.util.Watchr;

/**
 * Handle historical data call-backs 
 *
 */
public class QuoteHistoryInvestment implements IHistoricalDataHandler, IRealTimeBarHandler, ITopMktDataHandler {  

	public ArrayList<EventActivityPriceHistory> quoteRows = new ArrayList<EventActivityPriceHistory>();
	
	public Investment investment;
	private PriceType priceType; 
	private InvDataSource source;
	private InvDataTiming timing;
	
	private Channel channel;

	public QuoteHistoryInvestment (){
	}
	
	public QuoteHistoryInvestment(EventRequestHistory request) {
		this.investment = request.getInvestment();
		this.priceType = request.priceType;
		this.source = request.source;
		this.timing = request.timing;
	}
	
	public QuoteHistoryInvestment (Channel channel) {
		this.channel = channel;
	}
	
	// INTERFACE: IHistoricalDataHandler
	@Override public void historicalData(EventActivityPriceHistory row, boolean hasGaps) {
		
		quoteRows.add(row);
		handleRow(row);
		
		if(hasGaps) {
			Watchr.log(Level.WARNING, "Historic data has gaps!");
		}		
	}
	
	public int size() {
		int size = 0;
		size = quoteRows.size();
		return size;
	}	
	
	@Override public void realtimeBar(EventActivityPriceHistory row) {		

		quoteRows.add(row); 
		
		handleRow(row);
	}

	private void handleRow(final EventActivityPriceHistory row) {
		
		// Clarify provenance
		row.setInvestment(investment);
		row.priceType = priceType;
		row.source = source;
		row.timing = timing;
		Watchr.log(Level.INFO, "Received History from " + MemoryLevel.L3PARTNER + " " + row.toString());
		
		writeBusThread(row);
				
	}

	private void writeBusThread(final EventActivityPriceHistory row) {
		// Write to history data stream	
		new Thread () {
			@Override public void run () {
				BusSystem.write(row, BusSystem.getStreamName("HISTORY"));
			}
		}.start();
	}

	@Override public void historicalDataEnd() {
	}

	// INTERFACE: ITopMktDataHandler 
	@Override
	public void tickPrice(TickType tickType, double price, int canAutoExecute) {
	}

	@Override
	public void tickSize(TickType tickType, int size) {
	}

	@Override
	public void tickString(TickType tickType, String value) {
	}

	@Override
	public void tickSnapshotEnd() {
	}

	@Override
	public void marketDataType(MktDataType marketDataType) {
	}	

	@Override
	public void tickGeneric(TickType tickType, double value) {
		// TODO Auto-generated method stub
		
	}

	// PRINT
	public String toString() {
		String s = "";
		s = s + quoteRows.toString();
		return s;
	}


}
