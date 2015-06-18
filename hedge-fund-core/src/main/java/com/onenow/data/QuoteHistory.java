package com.onenow.data;

import java.util.ArrayList;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.MemoryLevel;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.io.BusSystem;
import com.onenow.portfolio.BrokerController.IHistoricalDataHandler;
import com.onenow.portfolio.BrokerController.IRealTimeBarHandler;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;

import java.util.logging.Level;

import com.onenow.util.Watchr;

/**
 * Handle historical data call-backs 
 *
 */
public class QuoteHistory implements IHistoricalDataHandler, IRealTimeBarHandler, ITopMktDataHandler {  

	public ArrayList<EventActivityHistory> quoteRows = new ArrayList<EventActivityHistory>();
	
	private Investment investment;
	private TradeType tradeType;
	private InvDataSource source;
	private InvDataTiming timing;
	
	private Channel channel;

	public QuoteHistory (){
	}
	
	public QuoteHistory(EventRequestHistory request) {
		this.investment = request.getInvestment();
		this.tradeType = request.tradeType;
		this.source = request.source;
		this.timing = request.timing;
	}
	
	public QuoteHistory (Channel channel) {
		this.channel = channel;
	}
	
	// INTERFACE: IHistoricalDataHandler
	@Override public void historicalData(EventActivityHistory row, boolean hasGaps) {
		
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
	
	@Override public void realtimeBar(EventActivityHistory row) {		

		quoteRows.add(row); 
		
		handleRow(row);
	}

	private void handleRow(EventActivityHistory row) {
		
		// Clarify provenance
		row.setInvestment(investment);
		row.tradeType = tradeType;
		row.source = source;
		row.timing = timing;
				
		// Write to history data stream
		BusSystem.write(StreamName.HISTORY, row);

		Watchr.log(Level.INFO, "Received History from " + MemoryLevel.L3PARTNER + " " + row.toString());

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
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + quoteRows.toString();
		return s;
	}

}
