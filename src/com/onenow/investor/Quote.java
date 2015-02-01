package com.onenow.investor;

import static com.ib.controller.Formats.fmtPct;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.investor.InvestorController.ITopMktDataHandler;
import com.onenow.investor.InvestorController.TopMktDataAdapter;


public class Quote extends TopMktDataAdapter implements ITopMktDataHandler {
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
	
	//Pablo added default constructor
	public Quote () {
		
	}
	
	Quote( AbstractTableModel model, String description) {
		m_model = model;
		m_description = description;
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
	}
}

