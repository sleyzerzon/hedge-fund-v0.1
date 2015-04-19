package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentFuture;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;


/**
 * Initialize the market
 */
public class InitMarket {

	InvestmentIndex index;
	Portfolio marketPortfolio;
	
	List<String> indices = new ArrayList<String>();
	
	
	public InitMarket() {
		
	}

//	/**
//	 * Init all market instruments 
//	 * @param portfolio
//	 */
//	public InitMarket(Portfolio portfolio) {
//		setMarketPortfolio(portfolio); // empty portfolio reference set
//		initMarketInstruments(new Underlying("all"));
//	}

	/**
	 * Init market instruments relevant to an index
	 * @param index
	 * @param portfolio
	 */
	public InitMarket(Underlying index, Portfolio portfolio) {
		setMarketPortfolio(portfolio);
		initMarketInstruments(index);
	}

	/** 
	 * Initialize indices, stocks, and futures
	 * @param index
	 */
	private void initMarketInstruments(Underlying index) { // create the investments
		addIndexToPortfolio(index);
		System.out.println(getMarketPortfolio().toIndicesString());		
		
		initOptions(index);
		System.out.println(getMarketPortfolio().toOptionsString());		

		initStocks(index);		
		System.out.println(getMarketPortfolio().toStocksString());		

		initFutures();
		System.out.println(getMarketPortfolio().toFuturesString());		
	}
	 
	// INDEX 
	/**
	 * Initialize indices
	 * @param under
	 */
	private void addIndexToPortfolio(Underlying under) {
		InvestmentIndex index = new InvestmentIndex(under);
		Trade indexTrade = new Trade(index, TradeType.BUY, 1, 0.0);
		Transaction indexTrans = new Transaction(indexTrade);
		getMarketPortfolio().enterTransaction(indexTrans);
	}	
	
	// OPTIONS
	/**
	 * Initialize options
	 * @param index
	 */
	private void initOptions(Underlying index) { 
		ExpirationDate exps = new ExpirationDate();
		exps.initOptionExpList(); 

		for(String expDate:exps.getIndexExpList()) { // for every option expiration expiration
			seedAndAddOptionsToPortoflio(index, expDate);
		}
	}
	

	// TODO: seed amount automatically from real-time market value
	private void seedAndAddOptionsToPortoflio(Underlying index, String expDate) {
		Integer seed=0;
		
		if(index.getTicker().equals("SPX")) { 	
			seed=2100;		
			addOptionsToPortfolio(index, expDate, seed);
		}

		if(index.getTicker().equals("NDX")) { 	
			seed=4450;		
			addOptionsToPortfolio(index, expDate, seed);
		}

		if(index.getTicker().equals("RUT")) { 	
			seed=1350;		
			addOptionsToPortfolio(index, expDate, seed);
		}
	}
	
//	private void addIndexAndOptionsToPortfolio(String name, String expDate, Integer seed) {
//		Underlying under = new Underlying(name);
////		addIndexToPortfolio(under);		
//		addOptionsToPortfolio(under, expDate, seed);
//	}
	/**
	 * Generates all options that may be trading right now
	 * @param under
	 * @param expDate
	 * @param seed
	 */
	private void addOptionsToPortfolio(Underlying under, String expDate, Integer seed) {
		Double range = 10.0; // 100.0; 	// options range up-down
		Integer interval = 5;	// options interval
		for (Double strike=(double) (seed-range); strike<(seed+range); strike=strike+interval) {
			Investment call = new InvestmentOption(under, InvType.CALL, expDate, strike);
			Investment put = new InvestmentOption(under, InvType.PUT, expDate, strike);
			Trade callTrade = new Trade(call, TradeType.BUY, 1, 0.0);
			Trade putTrade = new Trade(put, TradeType.BUY, 1, 0.0);
			Transaction trans = new Transaction(callTrade, putTrade); 
			getMarketPortfolio().enterTransaction(trans);
		}
	}

	// FUTURES
	/**
	 * Initialize all futures
	 */
	private void initFutures() {
		ExpirationDate exps = new ExpirationDate();
		exps.initFuturesExpList(); 

		for(String expDate:exps.getFuturesExpList()) {
			initExpFutures(expDate);
		}
	}
	private void initExpFutures(String expDate) {
		Underlying under = new Underlying("ES");
		InvestmentFuture future = new InvestmentFuture(under, expDate);
		Trade trade = new Trade(future, TradeType.BUY, 1, 0.0);
		Transaction trans = new Transaction(trade);
		getMarketPortfolio().enterTransaction(trans);		

	}

	// STOCKS
	/**
	 * Initialize all stocks
	 * @param index
	 */
	private void initStocks(Underlying index) {
		if(index.getTicker().equals("SPX")) {
			
	// 1 100 Max rate of messages per second has been exceeded:max=50 rec=138 (1)
			List<String> stocks = new InitSNPMarket().getSNP500();
			for (String stock:stocks) {
				// TODO: remove comment to run at scale 
				setStock(stock);
			}
		}
	}

	private void setStock(String name) {
		Underlying under = new Underlying(name);
		InvestmentStock stock = new InvestmentStock(under);
		Trade stockTrade = new Trade(stock, TradeType.BUY, 1, 0.0);
		Transaction stockTrans = new Transaction(stockTrade);
		getMarketPortfolio().enterTransaction(stockTrans);		
	}


	// SET GET
	private List<String> getIndices() {
		return indices;
	}

	private void setIndices(List<String> indices) {
		this.indices = indices;
	}

	public Portfolio getMarketPortfolio() {
		return marketPortfolio;
	}

	private void setMarketPortfolio(Portfolio marketPortfolio) {
		this.marketPortfolio = marketPortfolio;
	}

	private InvestmentIndex getIndex() {
		return index;
	}

	private void setIndex(InvestmentIndex index) {
		this.index = index;
	}
	
}
