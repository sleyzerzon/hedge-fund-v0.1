package com.onenow.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.onenow.constant.InvType;
import com.onenow.constant.PriceType;
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
	
	private static double spxEsPriceCenter = 2115.0;
	private static double ndxPriceCenter = 4500.0;
	private static double rutPriceCenter = 1500.0;
	
	private static double strikeIncrement = 5.0;
	private static int maxTimeSeries = 2;

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
											String toDashedDate) {
		
		marketPortfolio = new Portfolio();
		
		addStocksToPortfolio(stocks);		

		addIndicesToPortfolio(indices);

		addOptionsToPortfolio(options, toDashedDate);

		addFuturesToPortfolio(futures);
		
		addFutureoptionsToPortfolio(futureOptions, toDashedDate);
		
		marketPortfolio.toString();

		return marketPortfolio;
		
	}

	public static Portfolio getHistoryPortfolio() {	
		
		String toDashedDate = TimeParser.getTodayDashed(); 

		return getHistoryPortfolio(toDashedDate);
		
	}
	
	public static Portfolio getHistoryPortfolio(String toDashedDate) {	
			
		return getPortfolio(	InvestmentList.getUnderlying(InvestmentList.getSomeStocks()), 
								InvestmentList.getUnderlying(InvestmentList.getSomeIndices()),
								InvestmentList.getUnderlying(InvestmentList.getFutures()), 
								InvestmentList.getUnderlying(InvestmentList.getOptions()),
								InvestmentList.getUnderlying(InvestmentList.getFutures()),
								toDashedDate);
	}
	
	public static Portfolio getRealtimePortfolio() {	
		
		String toDashedDate = TimeParser.getTodayDashed(); 

		return getHistoryPortfolio(toDashedDate);
		
	}
	
	public static Portfolio getRealtimePortfolio(String toDashedDate) {	
		
		return getPortfolio(	new ArrayList<Underlying>(), 
								InvestmentList.getUnderlying(InvestmentList.getSomeIndices()),
								InvestmentList.getUnderlying(InvestmentList.getFutures()), 
								InvestmentList.getUnderlying(InvestmentList.getOptions()),
								InvestmentList.getUnderlying(InvestmentList.getFutures()),
								toDashedDate);
	}


	public static Portfolio getPrimaryPortfolio() {	
				
		String toDashedDate = TimeParser.getTodayDashed(); 

		return getPrimaryPortfolio(toDashedDate);
		
	}

	public static Portfolio getPrimaryPortfolio(String toDashedDate) {	
		
		return getPortfolio(	InvestmentList.getUnderlying(InvestmentList.getSomeStocks()), 
								new ArrayList<Underlying>(),
								new ArrayList<Underlying>(), 
								new ArrayList<Underlying>(),
								new ArrayList<Underlying>(),
								toDashedDate);
	}
	

	// INDEX 
	/**
	 * Initialize indices
	 * @param under
	 */
	private static void addIndicesToPortfolio(List<Underlying> unders) {
		for(Underlying under:unders) {
			InvestmentIndex index = new InvestmentIndex(under);
			Trade indexTrade = new Trade(index, PriceType.CALCULATED, 1, 0.0);
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
		Trade trade = new Trade(future, PriceType.BID, 1, 0.0);
		Transaction trans = new Transaction(trade);
		marketPortfolio.enterTransaction(trans);		

	}


	// FUTURE OPTIONS
	private static void addFutureoptionsToPortfolio(List<Underlying> unders, String toDashedDate) {
		addOptionsToPortfolio(unders, toDashedDate);
	}

	// OPTIONS
	/**
	 * Initialize options
	 * @param unders
	 */
	private static void addOptionsToPortfolio(List<Underlying> unders, String toDashedDate) { 
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
		
		for (Double strike=lowestStrike; strike<highestStrike; strike=strike+strikeIncrement) {
			Investment call = new InvestmentOption(under, InvType.CALL, expDate, strike);
			Investment put = new InvestmentOption(under, InvType.PUT, expDate, strike);
			Trade callTrade = new Trade(call, PriceType.BID, 1, 0.0);
			Trade putTrade = new Trade(put, PriceType.BID, 1, 0.0);
			Transaction trans = new Transaction(callTrade, putTrade); 
			marketPortfolio.enterTransaction(trans);
		}
	}

	private static double getMarketPrice(Underlying index) {
		Double price=0.0;
		if(index.getTicker().equals("SPX") || index.getTicker().equals("ES")) {
			price = spxEsPriceCenter;	
		}
		if(index.getTicker().equals("NDX")) {
			price = ndxPriceCenter;
		}
		if(index.getTicker().equals("RUT")) {
			price = rutPriceCenter;
		}				
		return price;
	}
	
	private static double lowPrice(Underlying index, String fromDate, String toDate, int maxTimeSeries) {
		Double price=0.0;		
		price = getMarketPrice(index) - strikeIncrement*maxTimeSeries;
		return price;
	}
	
	private static double highPrice(Underlying index, String fromDate, String toDate, int maxTimeSeries) {
		Double price=0.0;	
		price = getMarketPrice(index) + strikeIncrement*maxTimeSeries;		
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
		Trade stockTrade = new Trade(stock, PriceType.BID, 1, 0.0);
		Transaction stockTrans = new Transaction(stockTrade);
		marketPortfolio.enterTransaction(stockTrans);		
	}


	public static Portfolio getMarketPortfolio(String toDashedDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
