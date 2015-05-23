package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.data.Channel;
import com.onenow.portfolio.BrokerController.IHistoricalDataHandler;
import com.onenow.portfolio.BrokerController.IRealTimeBarHandler;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;
import com.onenow.util.LogType;
import com.onenow.util.WatchLog;

/**
 * Handle historical data call-backs 
 *
 */
public class QuoteHistory implements IHistoricalDataHandler, IRealTimeBarHandler, ITopMktDataHandler {  

	public ArrayList<QuoteRow> quoteRows = new ArrayList<QuoteRow>();
	
	private Channel channel;

	public QuoteHistory (){
	}
	
	public QuoteHistory (Channel channel) {
		this.channel = channel;
	}
	
	// INTERFACE: IHistoricalDataHandler
	@Override public void historicalData(QuoteRow row, boolean hasGaps) {
		
		quoteRows.add(row);
		handleBar(row);
		
		if(hasGaps) {
			System.out.println("Historic data has gaps!");
		}		
	}

	private void handleBar(QuoteRow row) {

		String log = "History " + row.toString();
		WatchLog.add(LogType.INFO, log, "", "");

		if(getChannel()!=null) { // if constructed that way
			channel.setChannelPrices(	row.formattedTime().substring(0, 10), 
										row.high(), row.low(), row.close());
		}
	}
	
	public int size() {
		int size = 0;
		size = quoteRows.size();
		return size;
	}	
	
	// INTERFACE: IRealTimeBarHandler
	@Override public void realtimeBar(QuoteRow bar) {		
		quoteRows.add(bar); 
		handleBar(bar);
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

	// SET GET
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
