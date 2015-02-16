package com.onenow.investor;

import static com.ib.controller.Formats.*;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.ib.controller.Formats;
import com.onenow.investor.InvestorController.ITopMktDataHandler;
import com.onenow.investor.InvestorController.TopMktDataAdapter;
import com.onenow.investor.QuoteTable;


public class QuoteTable extends AbstractTableModel {

	private InvestorController controller;

	public QuoteTable() {
		
	}

	public QuoteTable(InvestorController cont) {
		setController(cont);
	}
	
	private ArrayList<QuoteSingle> m_rows = new ArrayList<QuoteSingle>();

	public void addContract( Contract contract) {
		QuoteSingle quote = new QuoteSingle( this, contract.description() );
		m_rows.add(quote);
		getController().reqMktData(contract, "", false, (ITopMktDataHandler) quote);
//		fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
	}
	
	void addRow( QuoteSingle row) { // callback
		m_rows.add( row);
//		System.out.println("Quote " + toString(0));
//		fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
	}

	public String toString(int which) {
		QuoteSingle row = m_rows.get(which);
		String s="\n";
		s = s + "-\n";
		s = s + "Description " + row.m_description + "\n";
		s = s + "Bid Size " + row.m_bidSize + "\n";
		s = s + "Bid " + fmt( row.m_bid) + "\n";
		s = s + "Ask " + fmt( row.m_ask) + "\n";
		s = s + "Last " + row.m_askSize + "\n";
		s = s + "Ask Size " + row.m_askSize + "\n";
		s = s + "Time " + fmtTime( row.m_lastTime) + "\n";
		s = s + "Change " + row.change() + "\n";
		s = s + "Volume " + Formats.fmt0( row.m_volume) + "\n";
		s = s + "-\n";
		return s;
	}				
	
	public void desubscribe() {
		for (QuoteSingle row : m_rows) {
			getController().cancelMktData( row);
		}
	}		

	@Override public int getRowCount() {
		return m_rows.size();
	}
	
	@Override public int getColumnCount() {
		return 9;
	}
	
	@Override public String getColumnName(int col) {
		switch( col) {
			case 0: return "Description";
			case 1: return "Bid Size";
			case 2: return "Bid";
			case 3: return "Ask";
			case 4: return "Ask Size";
			case 5: return "Last";
			case 6: return "Time";
			case 7: return "Change";
			case 8: return "Volume";
			default: return null;
		}
	}

	@Override public Object getValueAt(int rowIn, int col) {
		QuoteSingle row = m_rows.get( rowIn);
		switch( col) {
			case 0: return row.m_description;
			case 1: return row.m_bidSize;
			case 2: return fmt( row.m_bid);
			case 3: return fmt( row.m_ask);
			case 4: return row.m_askSize;
			case 5: return fmt( row.m_last);
			case 6: return fmtTime( row.m_lastTime);
			case 7: return row.change();
			case 8: return Formats.fmt0( row.m_volume);
			default: return null;
		}
	}
	
	public void color(TableCellRenderer rend, int rowIn, Color def) {
		QuoteSingle row = m_rows.get( rowIn);
		Color c = row.m_frozen ? Color.gray : def;
		((JLabel)rend).setForeground( c);
	}

	public void cancel(int i) {
		getController().cancelMktData( m_rows.get( i) );
	}
	
	public class QuoteSingle extends TopMktDataAdapter {
		AbstractTableModel m_model;
		String m_description;
		double m_bid;
		double m_ask;
		double m_last;
		long m_lastTime;
		int m_bidSize;
		int m_askSize;
		double m_close;
		int m_volume;
		boolean m_frozen;
		
		public QuoteSingle () {
			
		}
		
		QuoteSingle( AbstractTableModel model, String description) {
			m_model = model;
			m_description = description;
		}

		public String toString() {
			String s="\n\n";
			s = s + "QUOTE" + "\n";
			s = s + "Description " + m_description + "\n";
			s = s + "Bid " + m_bid + "\n";
			s = s + "Ask " + m_ask + "\n";
			s = s + "Last " + m_last + "\n";
			s = s + "Last time " + m_lastTime + "\n";
			s = s + "Bid size " + m_bidSize + "\n";
			s = s + "Ask size " + m_askSize + "\n";
			s = s + "Close " + m_close + "\n";
			s = s + "Frozen " + m_frozen + "\n";
			return s;
		}
		
		public String change() {
			return m_close == 0	? null : fmtPct( (m_last - m_close) / m_close);
		}

		@Override public void tickPrice( TickType tickType, double price, int canAutoExecute) {
			switch( tickType) {
				case BID:
					m_bid = price;
					break;
				case ASK:
					m_ask = price;
					break;
				case LAST:
					m_last = price;
					break;
				case CLOSE:
					m_close = price;
					break;
				default: break;	
			}
			m_model.fireTableDataChanged(); // should use a timer to be more efficient
			
			if(m_close!=0.0){
				System.out.println(toString());
			}
		}

		@Override public void tickSize( TickType tickType, int size) {
			switch( tickType) {
				case BID_SIZE:
					m_bidSize = size;
					break;
				case ASK_SIZE:
					m_askSize = size;
					break;
				case VOLUME:
					m_volume = size;
					break;
                default: break; 
			}
			m_model.fireTableDataChanged();
			
//			System.out.println("B " + toString());
		}
		
		@Override public void tickString(TickType tickType, String value) {
			switch( tickType) {
				case LAST_TIMESTAMP:
					m_lastTime = Long.parseLong( value) * 1000;
					break;
                default: break; 
			}
		}
		
		@Override public void marketDataType(MktDataType marketDataType) {
			m_frozen = marketDataType == MktDataType.Frozen;
			m_model.fireTableDataChanged();
			
//			System.out.println("A " + toString());
		}
	}

	private InvestorController getController() {
		return controller;
	}

	private void setController(InvestorController controller) {
		this.controller = controller;
	}
}