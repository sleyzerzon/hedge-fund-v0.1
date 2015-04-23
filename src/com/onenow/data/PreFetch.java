package com.onenow.data;

import com.onenow.constant.TradeType;
import com.onenow.database.Cache;
import com.onenow.instrument.Investment;
import com.onenow.portfolio.Portfolio;

public class PreFetch extends Thread {

//	private Thread thread;
	private String threadName;

	private Portfolio portfolio;
	private Cache cache;
	
	private TradingRate tradingRate;

	
	public PreFetch() {
	   
	}
   
	public PreFetch(String name, Portfolio portfolio, Cache cache){
		
		setThreadName(name);
		System.out.println("Creating " +  threadName );
		
		setCache(cache);
		
		setTradingRate(new TradingRate());

	}
   
	public void run() {
		
		while(true) {
			System.out.println("Running " +  threadName );
			
		    try {
		    	
				for(String sampling:getTradingRate().getTradingRate("")) {
					String dataType = TradeType.TRADED.toString();
					for(Investment inv:getPortfolio().getInvestments()) {
						
						// getCache().readChart(inv, dataType, sampling, "", "");
					}
				}
		    	
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	// SET GET
	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public TradingRate getTradingRate() {
		return tradingRate;
	}

	public void setTradingRate(TradingRate tradingRate) {
		this.tradingRate = tradingRate;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}
	   

}
