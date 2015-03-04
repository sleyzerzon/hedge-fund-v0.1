package com.onenow.investor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import apidemo.ApiDemo;
//import apidemo.MarketDataPanel.DeepResultsPanel.DeepModel;
//import apidemo.MarketDataPanel.DeepResultsPanel.DeepRow;
import apidemo.util.HtmlButton;
import apidemo.util.NewTabbedPanel.NewTabPanel;

import com.ib.client.Types.DeepSide;
import com.ib.client.Types.DeepType;
//import com.ib.controller.ApiController.IDeepMktDataHandler;
import com.onenow.investor.BrokerController.IDeepMktDataHandler;
import com.onenow.broker.BrokerInteractive;
import com.onenow.finance.Investment;


public class QuoteDepth extends NewTabPanel implements IDeepMktDataHandler {
	final DeepModel m_buy = new DeepModel();
	final DeepModel m_sell = new DeepModel();

	private BrokerController controller;
	private BrokerInteractive broker;
	private ContractFactory contractFactory = new ContractFactory();
	private Investment investment;

	public QuoteDepth() {
		
	}
		
	public QuoteDepth(BrokerInteractive broker, BrokerController cont, Investment inv) {

		setController(cont);
		setBroker(broker);
			
		setInvestment(inv);
		Contract contract = getContractFactory().getContract(getInvestment());
		
		getController().reqDeepMktData(contract, 6, this);

	}
		
//		HtmlButton desub = new HtmlButton( "Desubscribe") {
//			public void actionPerformed() {
//				onDesub();
//			}
//		};
//		
//		JTable buyTab = new JTable( m_buy);
//		JTable sellTab = new JTable( m_sell);
//		
//		JScrollPane buyScroll = new JScrollPane( buyTab);
//		JScrollPane sellScroll = new JScrollPane( sellTab);
//		
//		JPanel mid = new JPanel( new GridLayout( 1, 2) );
//		mid.add( buyScroll);
//		mid.add( sellScroll);
//		
//		setLayout( new BorderLayout() );
//		add( mid);
//		add( desub, BorderLayout.SOUTH);
	
	
	protected void onDesub() {
//		getController().cancelDeepMktData( this);
	}

	@Override public void activated() {
	}

	/** Called when the tab is closed by clicking the X. */
	@Override public void closed() {
//		getController().cancelDeepMktData( this);
	}
	
	@Override public void updateMktDepth(int pos, String mm, DeepType operation, DeepSide side, double price, int size) {
		if (side == DeepSide.BUY) {
			m_buy.updateMktDepth(pos, mm, operation, price, size);
		}
		else {
			m_sell.updateMktDepth(pos, mm, operation, price, size);
		}
	}

	public class DeepModel extends AbstractTableModel {
		final ArrayList<DeepRow> m_rows = new ArrayList<DeepRow>();

		@Override public int getRowCount() {
			return m_rows.size();
		}

		public void updateMktDepth(int pos, String mm, DeepType operation, double price, int size) {
			switch( operation) {
				case INSERT:
					m_rows.add( pos, new DeepRow( mm, price, size) );
//					getBroker().setDepth(getInvestment(), m_rows); TODO
					System.out.println("Deep insert " + mm + " " + price + " " + size);
					fireTableRowsInserted(pos, pos);
					break;
				case UPDATE:
					m_rows.get( pos).update( mm, price, size);
//					getBroker().setDepth(getInvestment(), m_rows); TODO
					System.out.println("Deep update " + mm + " " + price + " " + size);
					fireTableRowsUpdated(pos, pos);
					break;
				case DELETE:
					if (pos < m_rows.size() ) {
						m_rows.remove( pos);
					}
					else {
						// this happens but seems to be harmless
						// System.out.println( "can't remove " + pos);
					}
//					getBroker().setDepth(getInvestment(), m_rows); TODO
					System.out.println("Deep delete ");
					fireTableRowsDeleted(pos, pos);
					break;
			}
		}

		@Override public int getColumnCount() {
			return 3;
		}
		
		@Override public String getColumnName(int col) {
			switch( col) {
				case 0: return "Mkt Maker";
				case 1: return "Price";
				case 2: return "Size";
				default: return null;
			}
		}

		@Override public Object getValueAt(int rowIn, int col) {
			DeepRow row = m_rows.get( rowIn);
			
			switch( col) {
				case 0: return row.m_mm;
				case 1: return row.m_price;
				case 2: return row.m_size;
				default: return null;
			}
		}
	}
	
	public static class DeepRow {
		String m_mm;
		double m_price;
		int m_size;

		public DeepRow(String mm, double price, int size) {
			update( mm, price, size);
		}
		
		void update( String mm, double price, int size) {
			m_mm = mm;
			m_price = price;
			m_size = size;
		}
	}

	private BrokerController getController() {
		return controller;
	}

	private void setController(BrokerController controller) {
		this.controller = controller;
	}

	private BrokerInteractive getBroker() {
		return broker;
	}

	private void setBroker(BrokerInteractive broker) {
		this.broker = broker;
	}

	private ContractFactory getContractFactory() {
		return contractFactory;
	}

	private void setContractFactory(ContractFactory contractFactory) {
		this.contractFactory = contractFactory;
	}

	private Investment getInvestment() {
		return investment;
	}

	private void setInvestment(Investment investment) {
		this.investment = investment;
	}
}
