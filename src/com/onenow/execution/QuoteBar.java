package com.onenow.execution;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.ib.controller.Bar;
import com.onenow.data.Channel;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.IHistoricalDataHandler;
import com.onenow.portfolio.BrokerController.IRealTimeBarHandler;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;

/**
 * Handle historical data call-backs 
 *
 */
public class QuoteBar implements IHistoricalDataHandler, IRealTimeBarHandler, ITopMktDataHandler {  

	final private ArrayList<Bar> barRows = new ArrayList<Bar>();
	
//	final private BarModel m_model = new BarModel(m_rows);
	
	private Channel channel;

	public QuoteBar (){

	}
	
	public QuoteBar (Channel channel) {
		this.channel = channel;
	}

	// INTERFACE: IHistoricalDataHandler
	@Override public void historicalData(Bar bar, boolean hasGaps) {
		
		barRows.add(bar);
		
		if(hasGaps) {
			System.out.println("Historic data has gaps!");
		}	
		
		handleBar(bar);
	}

	private void handleBar(Bar bar) {

		System.out.println("History " + bar.toString());

		String day = bar.formattedTime().substring(0, 10);
		Double highPrice = bar.high(); // bar.getM_high();
		Double lowPrice = bar.low(); // getM_low();
		Double recentPrice = bar.close();
//		System.out.println("Day " + day + " " + highPrice + " " + lowPrice);

		if(getChannel()!=null) { // if constructed that way
			setChannelPrices(day, highPrice, lowPrice, recentPrice);
		}
	}

	// CHANNEL
	private void setChannelPrices(String day, Double highPrice, Double lowPrice, Double recentPrice) {
		if(getChannel().getResistanceDayMap().containsKey(day)) { // day resistance
			if( highPrice > (Double)getChannel().getResistanceDayMap().get(day)) { // price
				getChannel().addResistance(day, highPrice);
//					System.out.println("high " + highPrice);
			}
		}
		if(getChannel().getSupportDayMap().containsKey(day)) { // day support
			if( lowPrice < (Double)getChannel().getSupportDayMap().get(day)) { // price
				getChannel().addSupport(day, lowPrice);
//					System.out.println("low " + lowPrice);
			}
		}
		if(getChannel().getRecentDayMap().containsKey(day)) { // day support
//			System.out.println("RECENT " + channel.getContract().secType() + " " + day + " " + recentPrice);
			getChannel().addRecent(day, recentPrice); // last
		} 
	}
	
	
	// INTERFACE: IRealTimeBarHandler
	@Override public void realtimeBar(Bar bar) {
		
		barRows.add(bar); 
		
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
		s = s + barRows.toString();
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
