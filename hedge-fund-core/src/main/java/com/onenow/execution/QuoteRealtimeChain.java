package com.onenow.execution;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.onenow.data.QuoteSharedHandler;
import com.onenow.data.QuoteOptionHandler;
import com.onenow.execution.QuoteRealtimeChain;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentOption;
import com.onenow.portfolio.BusController;


public class QuoteRealtimeChain extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ArrayList<QuoteSharedHandler> handlerRows = new ArrayList<QuoteSharedHandler>(); 

	private static BusController controller;
	
	
	public QuoteRealtimeChain() {

	}
	
	public QuoteRealtimeChain(BusController controller) {
		QuoteRealtimeChain.controller = controller;
	}
	
	public void addRow(Investment investment) {
		
		QuoteSharedHandler quoteHandler = null;
		
		if(investment instanceof InvestmentOption) {
			quoteHandler = new QuoteOptionHandler(investment, this);						
		} else {
			quoteHandler = new QuoteSharedHandler(investment, this);			
		}
		handlerRows.add(quoteHandler);

		try {
			controller.requestData(BusWallStInteractiveBrokers.getTickList(investment), quoteHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void desubscribeAll() {
		for (QuoteSharedHandler row : handlerRows) {
			controller.cancelMktData( row);
		}
	}		


	// TABLE (UI)
	@Override public int getRowCount() {
		return handlerRows.size();
	}
	
	@Override public int getColumnCount() {
		int columnCount = 999;
		return columnCount;
	}
	
	@Override public String getColumnName(int col) {

		// TODO: columns
		// switch( col) {
		// case 0: return row.m_description;
		// case 1: return row.m_bidSize;

		String s = "columnName";
	
		return s;
	}
	
	@Override public Object getValueAt(int row, int column) {
		
		// TODO: columns
		// switch( collumn ) {
		// case 0: return row.m_description;
		// case 1: return row.m_bidSize;

		return (Object) handlerRows.get(row); 
	}

	// PRINT
	public String toString(int which) {
		String s = "";
		s = s + handlerRows.get(which).toString();
		return s;
	}				
}