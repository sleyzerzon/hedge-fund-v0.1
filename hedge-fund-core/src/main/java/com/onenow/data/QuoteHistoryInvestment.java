package com.onenow.data;

import java.util.ArrayList;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.DataTiming;
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
	private DataTiming timing;
	
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
		
		clarifyProvenance(row);

		MarketPrice.handleRow(row);
		
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
		
		clarifyProvenance(row);
		
		MarketPrice.handleRow(row);
	}

	private void clarifyProvenance(EventActivityPriceHistory row) {
		// Clarify provenance
		row.setInvestment(investment);
		row.priceType = priceType;
		row.source = source;
		row.timing = timing;	
	}


	@Override public void historicalDataEnd() {
	}

	// INTERFACE: ITopMktDataHandler 
	@Override
	public void tickPrice(TickType tickType, Double price, Integer canAutoExecute) {
	}

	@Override
	public void tickSize(TickType tickType, Integer size) {
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
	public void tickGeneric(TickType tickType, Double value) {
		// TODO Auto-generated method stub
		
	}

	// PRINT
	public String toString() {
		String s = "";
		s = s + quoteRows.toString();
		return s;
	}


}
