package com.onenow.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

/**
 * Initialize the market
 */
public class InitMarket {

	private InvestmentIndex index;
	private static Portfolio marketPortfolio;
	
	private List<String> indices = new ArrayList<String>();
	

	/**
	 * Initialize market instruments: indices, stocks, futures
	 * @param index
	 * @param portfolio
	 */

	public InitMarket() {
		
	}

		
	public static Portfolio getPortfolio(	List<Underlying> stocks, List<Underlying> indices,
											List<Underlying> futures, List<Underlying> options,
											List<Underlying> futureOptions,
											String toDashedDate, int maxTimeSeries) {
		
		marketPortfolio = new Portfolio();
		
		addStocksToPortfolio(stocks);		

		addIndicesToPortfolio(indices);

		addOptionsToPortfolio(options, toDashedDate, maxTimeSeries);

		addFuturesToPortfolio(futures);
		
		addFutureoptionsToPortfolio(futureOptions, toDashedDate, maxTimeSeries);
		
		marketPortfolio.toString();

		return marketPortfolio;
		
	}

	public static Portfolio getHistoryPortfolio() {	
		
		String toDashedDate = TimeParser.getTodayDashed(); 

		return getHistoryPortfolio(toDashedDate);
		
	}
	
	public static Portfolio getHistoryPortfolio(String toDashedDate) {	
			
		int maxTimeSeries = 3;

		return getPortfolio(	new ArrayList<Underlying>(), 
								InvestmentList.getUnderlying(InvestmentList.someIndices),
								InvestmentList.getUnderlying(InvestmentList.futureNames), 
								InvestmentList.getUnderlying(InvestmentList.optionNames),
								InvestmentList.getUnderlying(InvestmentList.futureNames),
								toDashedDate, maxTimeSeries);
	}
	
	public static Portfolio getRealtimePortfolio() {	
		
		String toDashedDate = TimeParser.getTodayDashed(); 

		return getHistoryPortfolio(toDashedDate);
		
	}
	
	public static Portfolio getRealtimePortfolio(String toDashedDate) {	
		
		int maxTimeSeries = 5;

		return getPortfolio(	InvestmentList.getUnderlying(InvestmentList.someStocks), 
								InvestmentList.getUnderlying(InvestmentList.someIndices),
								InvestmentList.getUnderlying(InvestmentList.futureNames), 
								InvestmentList.getUnderlying(InvestmentList.optionNames),
								InvestmentList.getUnderlying(InvestmentList.futureNames),
								toDashedDate, maxTimeSeries);
	}


	public static Portfolio getPrimaryPortfolio() {	
				
		String toDashedDate = TimeParser.getTodayDashed(); 

		return getPrimaryPortfolio(toDashedDate);
		
	}

	public static Portfolio getPrimaryPortfolio(String toDashedDate) {	
		
		int maxTimeSeries = 5;
		
		return getPortfolio(	InvestmentList.getUnderlying(InvestmentList.justApple), 
								new ArrayList<Underlying>(),
								new ArrayList<Underlying>(), 
								new ArrayList<Underlying>(),
								new ArrayList<Underlying>(),
								toDashedDate, maxTimeSeries);
	}
	

	// INDEX 
	/**
	 * Initialize indices
	 * @param under
	 */
	private static void addIndicesToPortfolio(List<Underlying> unders) {
		for(Underlying under:unders) {
			InvestmentIndex index = new InvestmentIndex(under);
			Trade indexTrade = new Trade(index, TradeType.BUY, 1, 0.0);
			Transaction indexTrans = new Transaction(indexTrade);
			marketPortfolio.enterTransaction(indexTrans);
		}
	}	
	
	// FUTURES
	/**
	 * Initialize all futures
	 */
	private static void addFuturesToPortfolio(List<Underlying> unders) {
		ExpirationDate exps = new ExpirationDate();
		exps.initFuturesExpList(); 
		
		for(Underlying under:unders) {
			for(String expDate:exps.getValidFuturesExpList(TimeParser.getTodayUndashed())) {
				initExpFutures(under, expDate);
			}
		}
	}
	private static void initExpFutures(Underlying under, String expDate) {
		InvestmentFuture future = new InvestmentFuture(under, expDate);
		Trade trade = new Trade(future, TradeType.BUY, 1, 0.0);
		Transaction trans = new Transaction(trade);
		marketPortfolio.enterTransaction(trans);		

	}


	// FUTURE OPTIONS
	private static void addFutureoptionsToPortfolio(List<Underlying> unders, String toDashedDate, int maxTimeSeries) {
		addOptionsToPortfolio(unders, toDashedDate, maxTimeSeries);
	}

	// OPTIONS
	/**
	 * Initialize options
	 * @param unders
	 */
	private static void addOptionsToPortfolio(List<Underlying> unders, String toDashedDate, int maxTimeSeries) { 
		ExpirationDate exps = new ExpirationDate();
		exps.initIndexOptionExpList(); 
		
		// look at high/low price basis in the last 30 days
		String fromDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 30);

		for(Underlying under:unders) {
			for(String expDate:exps.getValidOptionExpList(TimeParser.getTodayUndashed())) { 			
				// TODO: seed lowprice and highprice automatically from market value range in the time window of interest
				addOptionsToPortfolio(	under, expDate, 
										lowPrice(under, fromDashedDate, toDashedDate, maxTimeSeries), 
										highPrice(under, fromDashedDate, toDashedDate, maxTimeSeries));	
			}
		}
	}

	/**
	 * Generates all possible options that may have traded in history
	 * @param under
	 * @param expDate
	 */
	private static void addOptionsToPortfolio(Underlying under, String expDate, Double lowestStrike, Double highestStrike) {
		Integer interval = 5;			// options interval
		for (Double strike=lowestStrike; strike<highestStrike; strike=strike+interval) {
			Investment call = new InvestmentOption(under, InvType.CALL, expDate, strike);
			Investment put = new InvestmentOption(under, InvType.PUT, expDate, strike);
			Trade callTrade = new Trade(call, TradeType.BUY, 1, 0.0);
			Trade putTrade = new Trade(put, TradeType.BUY, 1, 0.0);
			Transaction trans = new Transaction(callTrade, putTrade); 
			marketPortfolio.enterTransaction(trans);
		}
	}

	private static double getMarketPrice(Underlying index) {
		Double price=0.0;
		if(index.getTicker().equals("SPX") || index.getTicker().equals("ES")) {
			price = 2100.0;	
		}
		if(index.getTicker().equals("NDX")) {
			price = 4500.0;
		}
		if(index.getTicker().equals("RUT")) {
			price = 1500.0;
		}				
		return price;
	}
	
	private static double lowPrice(Underlying index, String fromDate, String toDate, int maxTimeSeries) {
		Double price=0.0;		
		int increment = 5;
		price = getMarketPrice(index) - increment*maxTimeSeries;
		return price;
	}
	
	private static double highPrice(Underlying index, String fromDate, String toDate, int maxTimeSeries) {
		Double price=0.0;	
		int increment = 5;
		price = getMarketPrice(index) + increment*maxTimeSeries;		
		return price;		
	}
	
	
	// STOCKS
	/**
	 * Initialize all stocks
	 * @param stocks
	 */
	private static void addStocksToPortfolio(List<Underlying> stocks) {
		for (Underlying stock:stocks) {
			setStock(stock);
		}
	}

	private static void setStock(Underlying under) {
		InvestmentStock stock = new InvestmentStock(under);
		Trade stockTrade = new Trade(stock, TradeType.BUY, 1, 0.0);
		Transaction stockTrans = new Transaction(stockTrade);
		marketPortfolio.enterTransaction(stockTrans);		
	}


	public static Portfolio getMarketPortfolio(String toDashedDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
