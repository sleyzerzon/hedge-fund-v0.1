package com.onenow.investor;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.ib.controller.Bar;
import com.onenow.investor.InvestorController.IHistoricalDataHandler;
import com.onenow.investor.InvestorController.IRealTimeBarHandler;


public class QuoteBar implements IHistoricalDataHandler  { // , IRealTimeBarHandler { //  

	final BarModel m_model = new BarModel();
	final ArrayList<Bar> m_rows = new ArrayList<Bar>();

	public QuoteBar (){

	}


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
}
