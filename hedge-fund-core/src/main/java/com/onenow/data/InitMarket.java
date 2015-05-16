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
import com.onenow.util.ParseDate;

/**
 * Initialize the market
 */
public class InitMarket {

	InvestmentIndex index;
	Portfolio marketPortfolio;
	
	List<String> indices = new ArrayList<String>();
	
	ParseDate parseDate = new ParseDate();
	
	public InitMarket() {
		
	}


	/**
	 * Initialize market instruments: indices, stocks, futures
	 * @param index
	 * @param portfolio
	 */
	public InitMarket(	Portfolio portfolio,	
						Underlying index, Underlying stocks, 
						Underlying options, Underlying futures) {
		
		setMarketPortfolio(portfolio);

		addIndexToPortfolio(index);
		System.out.println(getMarketPortfolio().toIndicesString());		

		initStocks(stocks);		
		System.out.println(getMarketPortfolio().toStocksString());		

		String fromDate = parseDate.getUndashedToday();
		String toDate = parseDate.getUndashedToday();
		initOptions(options, fromDate, toDate);
		System.out.println(getMarketPortfolio().toOptionsString());		

		initFutures(futures);
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
	private void initOptions(Underlying index, String fromDate, String toDate) { 
		ExpirationDate exps = new ExpirationDate();
		exps.initOptionExpList(); 

		for(String expDate:exps.getValidIndexExpList(parseDate.getUndashedToday())) { 			
			// TODO: seed lowprice and highprice automatically from market value range in the time window of interest
			addOptionsToPortfolio(	index, expDate, 
									lowPrice(index, fromDate, toDate), 
									highPrice(index, fromDate, toDate));	
		}
	}
	
	private double lowPrice(Underlying index, String fromDate, String toDate) {
		Double price=0.0;
		
		if(index.getTicker().equals("SPX")) {
			price = 2090.0;	
		}
		if(index.getTicker().equals("NDX")) {
			price = 4450.0;
		}
		if(index.getTicker().equals("RUT")) {
			price = 1350.0;
		}
		
		return price;
	}
	
	private double highPrice(Underlying index, String fromDate, String toDate) {
		Double price=0.0;
		
		if(index.getTicker().equals("SPX")) {
			price = 2110.0;	
		}
		if(index.getTicker().equals("NDX")) {
			price = 4450.0;
		}
		if(index.getTicker().equals("RUT")) {
			price = 1350.0;
		}

		return price;		
	}
	
	/**
	 * Generates all possible options that may have traded in history
	 * @param under
	 * @param expDate
	 */
	private void addOptionsToPortfolio(Underlying under, String expDate, Double lowestStrike, Double highestStrike) {
		Integer interval = 5;			// options interval
		for (Double strike=lowestStrike; strike<highestStrike; strike=strike+interval) {
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
	private void initFutures(Underlying under) {
		ExpirationDate exps = new ExpirationDate();
		exps.initFuturesExpList(); 

		for(String expDate:exps.getValidFuturesExpList(parseDate.getUndashedToday())) {
			initExpFutures(under, expDate);
		}
	}
	private void initExpFutures(Underlying under, String expDate) {
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

		// 1 100 Max rate of messages per second has been exceeded:max=50 rec=138 (1)
		if(index.getTicker().equals("SPX")) {
			List<String> stocks = new InitSNPMarket().getSNP500();
			for (String stock:stocks) {
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
