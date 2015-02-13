package com.onenow.investor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.ib.controller.Bar;
import com.onenow.finance.Underlying;
import com.onenow.investor.InvestorController.IHistoricalDataHandler;
import com.onenow.investor.InvestorController.IRealTimeBarHandler;
import com.onenow.investor.InvestorController.ITopMktDataHandler;


public class QuoteBar implements IHistoricalDataHandler, IRealTimeBarHandler, ITopMktDataHandler { //  

	final BarModel m_model = new BarModel();
	final ArrayList<Bar> m_rows = new ArrayList<Bar>();
	
	private Channel channel;

	public QuoteBar (){

	}
	
	public QuoteBar (Channel channel) {
		this.channel = channel;
	}

	// *** IHistoricalDataHandler
	@Override public void historicalData(Bar bar, boolean hasGaps) {
		m_rows.add(bar);
//		System.out.println("History " + bar.toString());
		if(hasGaps) {
			System.out.println("Historic data has gaps!");
		}		
		setChannelPrices(bar);
	}

	private void setChannelPrices(Bar bar) {
		String day=bar.formattedTime().substring(0, 10);
		Double highPrice=bar.high(); // bar.getM_high();
		Double lowPrice= bar.low(); // getM_low();
		Double recentPrice=bar.close();
//		System.out.println("Day " + day + " " + highPrice + " " + lowPrice);
		
		if(channel.getResistanceDayMap().containsKey(day)) { // day resistance
			if( highPrice > (Double)channel.getResistanceDayMap().get(day)) { // price
				channel.addResistance(day, highPrice);
//					System.out.println("high " + highPrice);
			}
		}
		if(channel.getSupportDayMap().containsKey(day)) { // day support
			if( lowPrice < (Double)channel.getSupportDayMap().get(day)) { // price
				channel.addSupport(day, lowPrice);
//					System.out.println("low " + lowPrice);
			}
		}
		if(channel.getRecentDayMap().containsKey(day)) { // day support
//			System.out.println("RECENT " + channel.getContract().secType() + " " + day + " " + recentPrice);
			channel.addRecent(day, recentPrice); // last
		} 
	}
	
	
	// *** IRealTimeBarHandler
	@Override public void realtimeBar(Bar bar) {
		m_rows.add( bar); 
		setChannelPrices(bar);		
	}

	
	@Override public void historicalDataEnd() {
//		fire();
	}


	// **** ITopMktDataHandler
	@Override
	public void tickPrice(TickType tickType, double price, int canAutoExecute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickSize(TickType tickType, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickString(TickType tickType, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickSnapshotEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void marketDataType(MktDataType marketDataType) {
		// TODO Auto-generated method stub
		
	}

	// *** BAR MODEL
	public class BarModel extends AbstractTableModel {
		@Override public int getRowCount() {
			return m_rows.size();
		}

		@Override public int getColumnCount() {
			return 7;
		}
		
		@Override public String getColumnName(int col) {
			switch( col) {
				case 0: return "Date/time";
				case 1: return "Open";
				case 2: return "High";
				case 3: return "Low";
				case 4: return "Close";
				case 5: return "Volume";
				case 6: return "WAP";
				default: return null;
			}
		}

		@Override public Object getValueAt(int rowIn, int col) {
			Bar row = m_rows.get( rowIn);
			switch( col) {
				case 0: return row.formattedTime();
				case 1: return row.open();
				case 2: return row.high();
				case 3: return row.low();
				case 4: return row.close();
				case 5: return row.volume();
				case 6: return row.wap();
				default: return null;
			}
		}
	}


}
