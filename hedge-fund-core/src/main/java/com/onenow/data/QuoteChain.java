package com.onenow.data;

import static com.ib.controller.Formats.*;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import apidemo.util.Util;

import com.ib.controller.Formats;
import com.onenow.data.QuoteChain;
import com.onenow.execution.BusWallStInteractiveBrokers;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentOption;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;
import com.onenow.util.Watchr;


public class QuoteChain extends AbstractTableModel {

	private static ArrayList<QuoteHandler> m_rows = new ArrayList<QuoteHandler>(); 

	private static BrokerController controller;
	
	
	public QuoteChain() {

	}
	
	public QuoteChain(BrokerController controller) {
		this.controller = controller;
	}
	
	public static void addRow(Investment investment, boolean snapshot) {
		
		QuoteHandler quoteHandler = null;
		
		if(investment instanceof InvestmentOption) {
			quoteHandler = new QuoteHandlerOption(investment);						
		} else {
			quoteHandler = new QuoteHandlerNonOption(investment);			
		}
		m_rows.add(quoteHandler);

		controller.requestData(BusWallStInteractiveBrokers.getTickList(), false, quoteHandler);

		if (snapshot) {
			Util.sleep( 11); // try to avoid pacing violation at TWS
		}

	}
	
	public void desubscribeAll() {
		for (QuoteHandler row : m_rows) {
			controller.cancelMktData( row);
		}
	}		


	// TABLE (UI)
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
		String s = "";
		s = s + m_rows.get(which).toString();
		return s;
	}				
}