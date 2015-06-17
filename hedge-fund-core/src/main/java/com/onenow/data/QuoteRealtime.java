package com.onenow.data;

import static com.ib.controller.Formats.*;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.ib.controller.Formats;
import com.onenow.data.QuoteRealtime;
import com.onenow.execution.BusWallStInteractiveBrokers;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentOption;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;
import com.onenow.util.Watchr;


public class QuoteRealtime extends AbstractTableModel {

	private BrokerController controller;
	private MarketPrice marketPrice;
	private Investment investment;
	
	private ArrayList<QuoteRealtimeSingle> m_rows = new ArrayList<QuoteRealtimeSingle>(); // TODO: why here?


	public QuoteRealtime() {

	}

	/**
	 * Constructor that requests market data, specifying the different data tick types requested
	 * @param controller
	 * @param mPrice
	 * @param inv
	 */
	public QuoteRealtime(BrokerController controller, MarketPrice mPrice, Investment inv) {
		
		this.controller = controller;
		this.marketPrice = mPrice;
		this.investment = inv;
		
		Contract contract = ContractFactory.getContract(investment);
		Watchr.log("Contract " + contract.toString());
		
		// set quote on table to receive callbacks later
		QuoteRealtimeSingle quote = new QuoteRealtimeSingle(this, contract, investment, marketPrice);
		m_rows.add(quote);
		
		controller.reqMktData(contract, BusWallStInteractiveBrokers.getTickList(), false, (ITopMktDataHandler) quote);
	}
	
	void addRow( QuoteRealtimeSingle row) { // callback
		m_rows.add( row);
		System.out.println("Quote " + toString(0));
	}
	
	public void desubscribe() {
		for (QuoteRealtimeSingle row : m_rows) {
			controller.cancelMktData( row);
		}
	}		

	public void cancel(int i) {
		controller.cancelMktData( m_rows.get( i) );
	}


	// INTERFACE
	@Override public int getRowCount() {
		return m_rows.size();
	}
	
	@Override public int getColumnCount() {
		return 9;
	}
	
	@Override public String getColumnName(int col) {
		String s = "";
		return s;
	}
	
	@Override public Object getValueAt(int rowIn, int col) {
		Object o = null;
		return o;
	}

	// PRINT
	public String toString(int which) {
		QuoteRealtimeSingle row = m_rows.get(which);
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
}