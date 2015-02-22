package com.onenow.investor;

import java.util.ArrayList;
import java.util.List;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.broker.BrokerInteractive;
import com.onenow.finance.InvApproach;
import com.onenow.finance.Investment;
import com.onenow.finance.InvestmentIndex;
import com.onenow.finance.MarketPrice;
import com.onenow.finance.Portfolio;
import com.onenow.finance.StrategyCallBuy;
import com.onenow.finance.StrategyCallSpread;
import com.onenow.finance.StrategyIronCondor;
import com.onenow.finance.TradeRatio;
import com.onenow.finance.Underlying;

public class Pucara {

	private static BrokerInteractive IB;
	private static BrokerActivityImpl broker;

	private ContractFactory contractFactory = new ContractFactory();
	private List<Channel> channels = new ArrayList<Channel>();

	private ParseDate parser = new ParseDate();
	
	private static String indexName;
	private static String expDate;
	
	private static Portfolio marketPortfolio = new Portfolio();
	private static MarketPrice marketPrice = new MarketPrice();

	public Pucara() {
		
	}
	
	public Pucara(String index, String expDate) throws InterruptedException {
		setIndexName(index);
		setExpDate(expDate);
		
		InitMarket init = new InitMarket(getMarketPortfolio()); // TODO: seed
		getChannelPrices(getContractFactory());
	}

	
	
	public static void launch() throws InterruptedException {
		
		Investment inv = getMarketPortfolio().getInvestments().get(0);
		String dataType = DataType.LASTTIME.toString();
		String fromDate = "2015-02-16";
		String toDate = "2015-02-23";
		String sampling = "1h";
		
		Double price = getMarketPrice().getPriceFromDB(inv, dataType, fromDate, toDate, sampling); 
		Integer size = getMarketPrice().getSizeFromDB(inv, dataType, fromDate, toDate, sampling);
		
		while(true) {
			System.out.println(getAnomalies());
			
			if(isBullMarket()) { // TODO: futures market?
				if(isUnderVWAP(6) && isMomentumReversedUp()) { // BUY call
					launchBottomExocet();
				}
				if(isOverVWAP(12) && isMomentumReversedDown()) { // BUY put
					// launchTopExocet();
				}
			}
			if(isBearMarket()){
				
			}
			if(isGoalAchieved() || isMarketClose() ) { // SELL
				
			}
			Thread.sleep(50000);
		}
	}

	public static void launchBottomExocet() {
		Exocet spxExocet = new Exocet(100, new Underlying(getIndexName()), getExpDate(), getBroker());
		StrategyCallBuy swingCall = (StrategyCallBuy) spxExocet.getCall(InvApproach.SWING, TradeRatio.NONE, 0.50);
		System.out.println(swingCall.toString());
	}
	
	public static void launchTopExocet() {

	}

	
	// PRIVATE
	private static String getAnomalies() {
		return "";
	}
	
	private boolean isCounterMarket() { // price under VWAP in bull market, over in bear market
		return true;
	}
	
	private static boolean isUnderVWAP(Integer buffer) {
		return true;
	}
	private static boolean isOverVWAP(Integer buffer) { 
		return true;
	}
	private static boolean isMomentumReversedUp() { // commensurate: by deviation
		return isPriceUp() && isVolumeUp();
	}
	
	private static boolean isPriceUp() {
		return true;
	}
	private static boolean isVolumeUp() {
		return true;
	}
	
	private static boolean isMomentumReversedDown() { // commensurate: by deviation 
		return isPriceDown() && isVolumeDown();
	}
	private static boolean isPriceDown() {
		return true;
	}
	private static boolean isVolumeDown() {
		return true;
	}
	
	private static boolean isGoalAchieved() {
		return false;
	}
	
	private static boolean isMarketClose() {
		return false;
	}
	private static boolean isBullMarket() {
		boolean bull=true;
		return bull;
	}
	private static boolean isBearMarket() {
		return !isBullMarket();
	}

	
//	public synchronized void run() {
//	    while(!priceUpdate) {
//	        try {
//	            wait();
//	        } catch (InterruptedException e) {}
//	    }
//        launchExocet();
//        priceUpdate=false;
//        notifyAll();
//        
//        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//	}

	
	
	private void getChannelPrices(ContractFactory contractFactory) throws InterruptedException {
		
		InvestmentIndex indexInv = new InvestmentIndex(new Underlying("SPX"));
		Contract index = contractFactory.getIndexToQuote(indexInv);
		getContractFactory().addChannel(getChannels(), index);
//		Contract index = contractFactory.getIndexToQuote("RUT");
//		getContractFactory().addChannel(getChannels(), index);
		Contract option = contractFactory.getOptionToQuote(indexInv);
		getContractFactory().addChannel(getChannels(), option);

		for(int i=0; i<getChannels().size(); i++) {			
			Channel channel = getChannels().get(i);
			List<String> endList = getEndList(channel);
			
			for(int j=0; j<endList.size(); j++) {
				String end = endList.get(j);
											
//				QuoteBar quoteHistory = new QuoteBar(channel);
//				getController().reqHistoricalData(	channel.getContract(), 
//													end, 1, DurationUnit.DAY, BarSize._1_hour, 
//													WhatToShow.TRADES, false,
//													quoteHistory);
			    Thread.sleep(12000);
				System.out.println("...");
			}
			System.out.println(channel.toString());
		}
	}
	private List<String> getEndList(Channel channel) {
		List<String> list = new ArrayList<String>();
		String date="";
		for(int i=channel.getResDayList().size()-1; i>=0; i--) { // Resistance
			try {
				date = channel.getResDayList().get(i); 
				list.add(parser.removeDash(date));
			} catch (Exception e) { } // nothing to do
		}
		for(int j=channel.getSupDayList().size()-1; j>=0; j--) { // Support
			try {
				date = channel.getSupDayList().get(j); 
				list.add(parser.removeDash(date));
			} catch (Exception e) { } // nothing to do
		}
		for(int k=channel.getRecentDayMap().size()-1; k>=0; k--) { // Recent			
			try {
				date = channel.getRecentDayList().get(k); // Recent
				list.add(parser.removeDash(date));
			} catch (Exception e) { } // nothing to do
		}
		return list;
	}

	
	// TEST
	
	// PRINT
	
	// SET GET
	private static BrokerActivityImpl getBroker() {
		return broker;
	}

	private void setBroker(BrokerActivityImpl broker) {
		this.broker = broker;
	}

	private ContractFactory getContractFactory() {
		return contractFactory;
	}

	private void setContractFactory(ContractFactory contractFactory) {
		this.contractFactory = contractFactory;
	}

	private List<Channel> getChannels() {
		return channels;
	}

	private void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	private ParseDate getParser() {
		return parser;
	}

	private void setParser(ParseDate parser) {
		this.parser = parser;
	}

	private static String getIndexName() {
		return indexName;
	}

	private void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	private static String getExpDate() {
		return expDate;
	}

	private void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	private static BrokerInteractive getIB() {
		return IB;
	}

	private static void setIB(BrokerInteractive iB) {
		IB = iB;
	}

	private static Portfolio getMarketPortfolio() {
		return marketPortfolio;
	}

	private void setMarketPortfolio(Portfolio marketPortfolio) {
		this.marketPortfolio = marketPortfolio;
	}

	private static MarketPrice getMarketPrice() {
		return marketPrice;
	}

	private static void setMarketPrice(MarketPrice marketPrice) {
		Pucara.marketPrice = marketPrice;
	}



	
	// PRINT
	
	
	// SET GET
}
