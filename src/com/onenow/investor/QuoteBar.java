package com.onenow.investor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ib.controller.Bar;
import com.onenow.finance.Underlying;
import com.onenow.investor.InvestorController.IHistoricalDataHandler;
import com.onenow.investor.InvestorController.IRealTimeBarHandler;


public class QuoteBar implements IHistoricalDataHandler  { // , IRealTimeBarHandler { //  

	final BarModel m_model = new BarModel();
	final ArrayList<Bar> m_rows = new ArrayList<Bar>();
	
	private Channel channel;

	public QuoteBar (){

	}
	
	public QuoteBar (Channel channel) {
		this.channel = channel;
	}

	// *** HANDLE HISTORY HERE
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
		
		if(channel.getResistance().containsKey(day)) { // day resistance
			if( highPrice > (Double)channel.getResistance().get(day)) { // price
				channel.addResistance(day, highPrice);
//					System.out.println("high " + highPrice);
			}
		}
		if(channel.getSupport().containsKey(day)) { // day support
			if( lowPrice < (Double)channel.getSupport().get(day)) { // price
				channel.addSupport(day, lowPrice);
//					System.out.println("low " + lowPrice);
			}
		}
		if(channel.getRecent().containsKey(day)) { // day support
			channel.addRecent(day, recentPrice); // last
		}
	}
	
	
	
	@Override public void historicalDataEnd() {
//		fire();
	}

//	@Override public void realtimeBar(Bar bar) {
//		m_rows.add( bar); 
//		fire();
//	}
	
//	private void fire() {
//		SwingUtilities.invokeLater( new Runnable() {
//			@Override public void run() {
//				m_model.fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
//				m_chart.repaint();
//			}
//		});
//	}

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
