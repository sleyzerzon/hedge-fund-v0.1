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
	private Portfolio marketPortfolio;
	
	private List<String> indices = new ArrayList<String>();
	
	private TimeParser parseDate = new TimeParser();
	
	public InitMarket() {	
	}

	/**
	 * Initialize market instruments: indices, stocks, futures
	 * @param index
	 * @param portfolio
	 */
	
	public InitMarket(	Portfolio portfolio,
						List<Underlying> stocks, List<Underlying> indices,
						List<Underlying> futures, List<Underlying> options,
						String toDashedDate) {
		
		this.marketPortfolio = portfolio;

		initStocks(stocks);		
		Watchr.log(Level.INFO, marketPortfolio.toStocksString());		

		addIndicesToPortfolio(indices);
		Watchr.log(Level.INFO, marketPortfolio.toIndicesString());				

		initOptions(options, toDashedDate);
		Watchr.log(Level.INFO, marketPortfolio.toOptionsString());		

		initFutures(futures);
		Watchr.log(Level.INFO, marketPortfolio.toFuturesString());			

		// TODO: futures options
		
	}
	
	 
	// INDEX 
	/**
	 * Initialize indices
	 * @param under
	 */
	private void addIndicesToPortfolio(List<Underlying> unders) {
		for(Underlying under:unders) {
			InvestmentIndex index = new InvestmentIndex(under);
			Trade indexTrade = new Trade(index, TradeType.BUY, 1, 0.0);
			Transaction indexTrans = new Transaction(indexTrade);
			marketPortfolio.enterTransaction(indexTrans);
		}
	}	
	
	// OPTIONS
	/**
	 * Initialize options
	 * @param unders
	 */
	private void initOptions(List<Underlying> unders, String toDashedDate) { 
		ExpirationDate exps = new ExpirationDate();
		exps.initIndexOptionExpList(); 
		
		// look at high/low price basis in the last 30 days
		String fromDashedDate = parseDate.getDashedDateMinus(toDashedDate, 30);

		for(Underlying under:unders) {
			for(String expDate:exps.getValidOptionExpList(parseDate.getUndashedToday())) { 			
				// TODO: seed lowprice and highprice automatically from market value range in the time window of interest
				addOptionsToPortfolio(	under, expDate, 
										lowPrice(under, fromDashedDate, toDashedDate), 
										highPrice(under, fromDashedDate, toDashedDate));	
			}
		}
	}
	
	private double lowPrice(Underlying index, String fromDate, String toDate) {
		Double price=0.0;		
		if(index.getTicker().equals("SPX") || index.getTicker().equals("ES")) {
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
		if(index.getTicker().equals("SPX") || index.getTicker().equals("ES")) {
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
			marketPortfolio.enterTransaction(trans);
		}
	}

	// FUTURES
	/**
	 * Initialize all futures
	 */
	private void initFutures(List<Underlying> unders) {
		ExpirationDate exps = new ExpirationDate();
		exps.initFuturesExpList(); 
		
		for(Underlying under:unders) {
			for(String expDate:exps.getValidFuturesExpList(parseDate.getUndashedToday())) {
				initExpFutures(under, expDate);
			}
		}
	}
	private void initExpFutures(Underlying under, String expDate) {
		InvestmentFuture future = new InvestmentFuture(under, expDate);
		Trade trade = new Trade(future, TradeType.BUY, 1, 0.0);
		Transaction trans = new Transaction(trade);
		marketPortfolio.enterTransaction(trans);		

	}

	// STOCKS
	/**
	 * Initialize all stocks
	 * @param stocks
	 */
	private void initStocks(List<Underlying> stocks) {
		for (Underlying stock:stocks) {
			setStock(stock);
		}
	}

	private void setStock(Underlying under) {
		InvestmentStock stock = new InvestmentStock(under);
		Trade stockTrade = new Trade(stock, TradeType.BUY, 1, 0.0);
		Transaction stockTrans = new Transaction(stockTrade);
		marketPortfolio.enterTransaction(stockTrans);		
	}
	
}
