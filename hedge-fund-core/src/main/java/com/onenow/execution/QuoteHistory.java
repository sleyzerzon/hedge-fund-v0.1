package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.data.Channel;
import com.onenow.data.EventHistory;
import com.onenow.data.MarketPrice;
import com.onenow.instrument.Investment;
import com.onenow.io.BrokerBusHistorian;
import com.onenow.io.BrokerBusHistorianRT;
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

	public ArrayList<EventHistory> quoteRows = new ArrayList<EventHistory>();
	
	private Investment investment;
	private TradeType tradeType;
	private InvDataSource source;
	private InvDataTiming timing;
	
	private Channel channel;

	public QuoteHistory (){
	}
	
	public QuoteHistory(Investment inv, TradeType tradeType, InvDataSource source, InvDataTiming timing) {
		this.investment = inv;
		this.tradeType = tradeType;
		this.source = source;
		this.timing = timing;
	}
	
	public QuoteHistory (Channel channel) {
		this.channel = channel;
	}
	
	// INTERFACE: IHistoricalDataHandler
	@Override public void historicalData(EventHistory row, boolean hasGaps) {
		
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
	
	// INTERFACE: IRealTimeBarHandler
	@Override public void realtimeBar(EventHistory row) {		

		quoteRows.add(row); 
		
		handleRow(row);
	}

	private void handleRow(EventHistory row) {
		
		// Clarify provenance
		row.investment = investment;
		row.tradeType = tradeType;
		row.source = source;
		row.timing = timing;
				
		// Write to history data stream
		BrokerBusHistorian histroyBroker = new BrokerBusHistorian();
		histroyBroker.write(row);

		Watchr.log(Level.INFO, "History " + row.toString());

		// Channel handling
		if(channel!=null) { // if constructed that way
			channel.setChannelPrices(	row.formattedTime().substring(0, 10), 
										row.high, row.low, row.close);
		}
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
