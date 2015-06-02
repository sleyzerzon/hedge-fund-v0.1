package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.data.Channel;
import com.onenow.io.BrokerBusHistorian;
import com.onenow.io.BrokerBusHistorianRT;
import com.onenow.io.EventHistory;
import com.onenow.portfolio.BrokerController.IHistoricalDataHandler;
import com.onenow.portfolio.BrokerController.IRealTimeBarHandler;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;

import java.util.logging.Level;

import com.onenow.util.WatchLog;

/**
 * Handle historical data call-backs 
 *
 */
public class QuoteHistory implements IHistoricalDataHandler, IRealTimeBarHandler, ITopMktDataHandler {  

	public ArrayList<EventHistory> quoteRows = new ArrayList<EventHistory>();
	
	private Channel channel;

	public QuoteHistory (){
	}
	
	public QuoteHistory (Channel channel) {
		this.channel = channel;
	}
	
	// INTERFACE: IHistoricalDataHandler
	@Override public void historicalData(EventHistory row, boolean hasGaps) {
		
		quoteRows.add(row);
		handleRow(row);
		
		if(hasGaps) {
			System.out.println("Historic data has gaps!");
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
		
		// Write to Real-Time datastream
		BrokerBusHistorian histroyBroker = new BrokerBusHistorian();
		histroyBroker.write(row);

		handleRow(row);
	}

	private void handleRow(EventHistory row) {

		String log = "History " + row.toString();
		WatchLog.add(Level.INFO, log, "", "");

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
