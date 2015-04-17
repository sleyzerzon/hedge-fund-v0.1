package com.onenow.execution;

import static com.ib.controller.Formats.*;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.ib.controller.Formats;
import com.onenow.constant.DataType;
import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.data.MarketPrice;
import com.onenow.execution.QuoteTable;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.ITopMktDataHandler;
import com.onenow.portfolio.BrokerController.TopMktDataAdapter;


public class QuoteTable extends AbstractTableModel {

	private BrokerController controller;
	private MarketPrice marketPrice;
	
	private ContractFactory contractFactory = new ContractFactory();
	private Investment investment;

	public QuoteTable() {
		
	}

	/**
	 * Request market data, specifying the different data tick types requested
	 * @param cont
	 * @param mPrice
	 * @param inv
	 */
	public QuoteTable(BrokerController cont, MarketPrice mPrice, Investment inv) {
		
		setController(cont);
		setMarketPrice(mPrice);
		
		setInvestment(inv);
		Contract contract = getContractFactory().getContract(getInvestment());
		System.out.println("Contract " + contract.toString());
		
		// set quote on table to receive callbacks later
		QuoteSingle quote = new QuoteSingle(this, contract.description(), getInvestment(), getMarketPrice());
		m_rows.add(quote);
		
		String volumeTicks = 	"233, " + //  TickType.RT_VOLUME
								// Contains the last trade price, last trade size, last trade time, 
								// total volume, VWAP, and single trade flag.
								"165, " + //  TickType.AVG_VOLUME
								// Contains generic stats
								"100, " + //  TickType.OPTION_CALL_VOLUME, TickType.OPTION_PUT_VOLUME
								// Contains option Volume (currently for stocks)
								"225, " +
								// Auction values (volume, price and imbalance)
								"101, " + 
								// Contains option Open Interest (currently for stocks)
								"225, " + // TickType.AUCTION_VOLUME
								// Contains auction values (volume, price and imbalance)
								"104, " +
								// Historical Volatility (currently for stocks)
								"106, " +
								// Option Implied Volatility (currently for stocks)
								"411";
								// Real-time Historical Volatility
		
								// ? TickType.VOLUME_RATE.toString();
								// ? TickType.OPEN_INTEREST -> 22
								// ? TickType.VOLUME -> 8
		
		getController().reqMktData(contract, volumeTicks, false, (ITopMktDataHandler) quote);
		
		fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
	} 

	
	private ArrayList<QuoteSingle> m_rows = new ArrayList<QuoteSingle>(); // TODO: why here?

	void addRow( QuoteSingle row) { // callback
		m_rows.add( row);
//		System.out.println("Quote " + toString(0));
		fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
	}
	
	public void desubscribe() {
		for (QuoteSingle row : m_rows) {
			getController().cancelMktData( row);
		}
	}		

	public void cancel(int i) {
		getController().cancelMktData( m_rows.get( i) );
	}


	// INTERFACE
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
	
	// PUBLIC
	public Double getLastAsk() {
		Integer size = m_rows.size();
		QuoteSingle quote = m_rows.get(size-1);
		Double price = quote.m_ask;
		if(price.equals(0.0)) {
			return getLastClose();
		}
		return price;
	}
	public Double getLastBid() {
		Integer size = m_rows.size();
		QuoteSingle quote = m_rows.get(size-1);
		Double price = quote.m_bid;
		if(price.equals(0.0)) {
			return getLastClose();
		}
		return price;
	}
	public Double getLastClose() {
		Integer size = m_rows.size();
		QuoteSingle quote = m_rows.get(size-1);
		Double price = quote.m_close;
		
//		System.out.println("QUOTES " + price + " " + size);
//		System.out.println(quote.toString());
		return price;
	}

	// PRIVATE

	// TEST
	
	// PRINT
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

	// SET GET
	private BrokerController getController() {
		return controller;
	}

	private void setController(BrokerController controller) {
		this.controller = controller;
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

	private MarketPrice getMarketPrice() {
		return marketPrice;
	}

	private void setMarketPrice(MarketPrice marketPrice) {
		this.marketPrice = marketPrice;
	}

}