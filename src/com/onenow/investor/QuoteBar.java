package com.onenow.investor;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.ib.controller.Bar;
import com.onenow.finance.Underlying;
import com.onenow.investor.InvestorController.IHistoricalDataHandler;
import com.onenow.investor.InvestorController.IRealTimeBarHandler;


public class QuoteBar implements IHistoricalDataHandler  { // , IRealTimeBarHandler { //  

	final BarModel m_model = new BarModel();
	final ArrayList<Bar> m_rows = new ArrayList<Bar>();
	
	
	// LOOK FOR FREEK-OUT IN LAST 30 DAYS, SET CHANNEL
	// HAVE 14 DAYS IN CASE OVER-BUYING/SELLING CONTINUES
	// measured at time that run-up + sell-off end (Warn level); before OVER-REACTION begins
	// buy AFTER over-sold ends, sell after over-buying ends (Act level)
	private static Channel spx = new Channel(new Underlying("SPX"));
	private static Channel rut = new Channel(new Underlying("RUT"));
	private static Channel ndx = new Channel(new Underlying("NDX"));

	public QuoteBar (){
		setChannels();

	}

	// *** HANDLE HISTORY HERE
	@Override public void historicalData(Bar bar, boolean hasGaps) {
		m_rows.add(bar);
		System.out.println("History " + bar.toString());
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
	
	private static void setChannels() {
		// SPX
		getSpx().addResistance("20150205"); 
		getSpx().addSupport("20150202");
		getSpx().addSupport("20150129");
		getSpx().addResistance("20150122");
		getSpx().addSupport("20150114");
		getSpx().addResistance("20150108");
		// *** 30-day trend change
		getSpx().addResistance("20141229");  
		getSpx().addSupport("20141216"); // fundamentals t2 low 
		getSpx().addResistance("20141205"); 
		// November: mild market 
		getSpx().addResistance("20141015"); // CRASH
		getSpx().addResistance("20140905"); 
		getSpx().addSupport("20140807"); // fundamentals t1 low

		
//		spx.addResistance(sdf.parse("20150205 14:30:00"), 2063.0); // ET
//		spx.addSupport(sdf.parse("20150202 09:05:00"), 1983.0);
//		spx.addSupport(sdf.parse("20150129 12:00:00"), 1990.0);
//		spx.addResistance(sdf.parse("20150122 14:00:00"), 2063.0);
//		spx.addSupport(sdf.parse("20150114 12:00:00"), 1992.0);
//		// spx.addResistance(sdf.parse("20150113 09:00:00"), 2055.0); // faux
//		spx.addResistance(sdf.parse("20150108 14:00:00"), 2064.0);
////		spx.addSupport(sdf.parse("20150106 16:30:00"), 2002.0); 
//		spx.addResistance(sdf.parse("20141229 16:00:00"), 2093.0); // old high
//		spx.addSupport(sdf.parse("20141216 09:05:00"), 1970.0); // old low
//		spx.addResistance(sdf.parse("20141205 16:30:00"), 2076.0); // ET
//		// November: fundamentals slow
//		// October: market goes down
//		// September births the top, sets the fundamental
//		spx.addResistance(sdf.parse("20140905 16:30:00"), 2008.0); // old high
//		// August births the bottom, sets the fundamental
//		spx.addSupport(sdf.parse("20140807 09:05:00"), 1906.0); // old low
	}
	private Channel getRut() {
		return rut;
	}

	private void setRut(Channel rut) {
		this.rut = rut;
	}

	private static Channel getSpx() {
		return spx;
	}

	private void setSpx(Channel spx) {
		this.spx = spx;
	}

	private Channel getNdx() {
		return ndx;
	}

	private void setNdx(Channel ndx) {
		this.ndx = ndx;
	}


}
