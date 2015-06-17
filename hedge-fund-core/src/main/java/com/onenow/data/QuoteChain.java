package com.onenow.data;

import static com.ib.controller.Formats.fmtNz;
import static com.ib.controller.Formats.fmtPct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

import apidemo.util.Util;

import com.onenow.execution.BusWallStInteractiveBrokers;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.Investment;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.IOptHandler;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;
import com.onenow.util.Watchr;

public class QuoteChain extends AbstractTableModel {
	
	private BrokerController controller;
	private MarketPrice marketPrice;
	private Investment investment;
	
	ArrayList<QuoteChainSingle> m_list = new ArrayList<QuoteChainSingle>();


	public QuoteChain(){
		
	}
	
	public QuoteChain(BrokerController controller, MarketPrice mPrice, Investment inv){
		
		this.controller = controller;
		this.marketPrice = mPrice;
		this.investment = inv;
		
		Contract contract = ContractFactory.getContract(investment);
		Watchr.log("Contract " + contract.toString());
		
		// set quote on table to receive callbacks later
		addRow(contract, false);
		
	}
	
	
    public void desubscribe() {
        for (QuoteChainSingle row : m_list) {
            controller.cancelOptionMktData(row);
        	// controller.cancelMktData( (com.onenow.portfolio.BrokerController.IOptHandler) row);
        }
    }
	
	@Override public int getRowCount() {
		return m_list.size();
	}

	public void sort() {
//		Collections.sort( m_list, c);
//		fireTableDataChanged();
	}

	public void addRow(Contract contract, boolean snapshot) {
		QuoteChainSingle row = new QuoteChainSingle(this, contract, investment, marketPrice);
		m_list.add( row);
		
		controller.reqOptionMktData(contract, BusWallStInteractiveBrokers.getTickList(), snapshot, row);
		// controller.reqMktData(contract, BusWallStInteractiveBrokers.getTickList(), snapshot, (com.onenow.portfolio.BrokerController.IOptHandler) row);
		
		if (snapshot) {
			Util.sleep( 11); // try to avoid pacing violation at TWS
		}
	}

	@Override public int getColumnCount() {
		int c =0;
		// return m_snapshot.isSelected() ? 10 : 9;
		return c;
	}
	
	@Override public String getColumnName(int col) {
		String s = "";
//		switch( col) {
//			case 0: return "Expiry";
//			case 1: return "Strike";
//			case 2: return "Bid";
//			case 3: return "Ask";
//			case 4: return "Imp Vol";
//			case 5: return "Delta";
//			case 6: return "Gamma";
//			case 7: return "Vega";
//			case 8: return "Theta";
//			default: return null;
//		}
		return s;
	}

	@Override public Object getValueAt(int rowIn, int col) {
		QuoteChainSingle row = m_list.get( rowIn);
		switch( col) {
			case 0: return row.contract.expiry();
			case 1: return row.contract.strike();
			case 2: return fmtNz( row.m_bid);
			case 3: return fmtNz( row.m_ask);
			case 4: return fmtPct( row.m_impVol);
			case 5: return fmtNz( row.m_delta);
			case 6: return fmtNz( row.m_gamma);
			case 7: return fmtNz( row.m_vega);
			case 8: return fmtNz( row.m_theta);
			case 9: return row.m_done ? "*" : null;
			default: return null;
		}
	}
	

}
