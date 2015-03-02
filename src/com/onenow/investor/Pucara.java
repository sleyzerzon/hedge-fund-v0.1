package com.onenow.investor;

import java.util.ArrayList;
import java.util.List;

import com.onenow.analyst.Candle;
import com.onenow.analyst.Chart;
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
import com.onenow.finance.TradeType;
import com.onenow.finance.Underlying;

public class Pucara {
	
	// wait for break-out
	// in bull market only from low only
	// after the low has been confirmed three times
	// sell two supports level before / -20 points from low, 7 day term
	// stop loss at 2x credit
	// close at Friday noon, never go through expiration
	// max loss 2% of capital
	
	// 30-day 1955 .1 delta put at $500 credit, with $200 margin each
	// make $10k with 20 contracts; with $4k margin => 2.5x return
	// + limit: $100 ($2k loss in the month) ... 20-point decrease 
	// ratio?
	
	// @2085-80; resistance $2085, $2002
	// 5-day (high 2016) ~$0.40 , (mid 2012) $0.55 ... , (low 2002): ? 
	// 30-day (2016) $1.25 , (2012): $1.25 ..., (2002): ?
	
	// act at $2005, sell based on levels of channel protection
	// ratio 1:2 credit-wise = sell 20x(2085-80) for $1600 credit; buy 1x(2105) for $800
	// - above 2105 profit $1600credit-800put-250buy back=550
	// - above 2085, below 2005 profit 550+nx100, up to $2550
	// - below 2085, move to later calendar

	private static BrokerInteractive IB;
	private static BrokerActivityImpl broker;

	private ContractFactory contractFactory = new ContractFactory();
	private List<Channel> channels = new ArrayList<Channel>();

	private ParseDate parser = new ParseDate();
	
	private static String indexName;
	private static String expDate;
	
	private static Portfolio marketPortfolio = new Portfolio();
	private static MarketPrice marketPrice = new MarketPrice();
	
	static List<String> samplingRate = new ArrayList<String>();


//***	 Look for signals, particularly at resitance & support
//***	 Confirm via price, volume, and momentum
//***	 Become familiar with the rythm of the underlying
	public Pucara() {
		
	}
	
	public Pucara(String index, String expDate) throws InterruptedException {
		setIndexName(index);
		setExpDate(expDate);
		
		InitMarket init = new InitMarket(getMarketPortfolio()); // TODO: seed
		
		setSampling();
		
		// !!!!!!!!!!!!!!!!!!!!!!!
//		getChannelPrices(getContractFactory()); 
	}

	
	
	public static void launch() throws InterruptedException {
		
		// MOMENTUM rsi>0.5, uptrending chi, stoch, 1hr....goign up
				
		while(true) {
			
			setCharts("2015-02-21", "2015-02-28");
			
//			System.out.println(anomaliesToString());
			
			if(isBullMarket()) { // TODO: futures market?
				// has been trading in range! And it is breaking out!
				if(isVolumeSpike() &&isMomentumReversedUp() && isFuturesGuidingUp()) { // BUY call
//					isUnderVWAP(6)
					launchBottomExocet();
				}
				if(isVolumeSpike() && isMomentumReversedDown() && isFuturesGuidingDown()) { // BUY put
					// isOverVWAP(12)
					// launchTopExocet();
				}
			}
			if(isBearMarket()){
				
			}
			if(isGoalAchieved() || isMarketClose() ) { // SELL
				
			}
			
			System.out.println(",,,,,");
			Thread.sleep(50000);
		}
	}
	
	private static String anomaliesToString() {
		String s="";
		List<Investment> invs = getMarketPortfolio().getInvestments();

		for(Investment inv:invs) {
			for(String sampling:getSamplingRate()) {
				Chart chart = inv.getCharts().get(sampling);
//				if(!chart.isLastCandleNormal()) {
//					s = s + "ANOMALY: last candle " + sampling + " " + inv.toString() + "\n";
//				}
			}
		}
		
		return s;
	}

	private static void setCharts(String fromDate, String toDate) {

		List<Investment> invs = getMarketPortfolio().getInvestments();
		
		
		for(String sampling:getSamplingRate()) {
			for(Investment inv:invs) {
				Chart chart = new Chart();
				
				chart = getMarketPrice().queryChart(inv, TradeType.TRADED.toString(), fromDate, toDate, sampling);
				
				if(!chart.getSizes().isEmpty()) {
					inv.getCharts().put(sampling, chart);	
					System.out.println("+ chart " + inv.toString() +  " " + sampling + "\n" +chart.toString());
					
					for(int i=0; i<chart.getPrices().size(); i++) {
						System.out.println(chart.getAnalysis(i));
					}
					System.out.println("\n");
					
				} else {
					System.out.println("- chart " + inv.toString() + " " + sampling);
				}
			}
		}
		
	}
		
	private void setSampling() {
		getSamplingRate().add(SamplingRate.SCALPSHORT.toString());
		getSamplingRate().add(SamplingRate.SCALPMEDIUM.toString());
		getSamplingRate().add(SamplingRate.SCALPLONG.toString());
		getSamplingRate().add(SamplingRate.SWINGSHORT.toString());
		getSamplingRate().add(SamplingRate.SWINGMEDIUM.toString());
		getSamplingRate().add(SamplingRate.SWINGLONG.toString());		
		getSamplingRate().add(SamplingRate.TRENDSHORT.toString());
		getSamplingRate().add(SamplingRate.TRENDMEDIUM.toString());
		getSamplingRate().add(SamplingRate.TRENDLONG.toString());

	}

	public static void launchBottomExocet() {
		Exocet spxExocet = new Exocet(100, new Underlying(getIndexName()), getExpDate(), getBroker());
		StrategyCallBuy swingCall = (StrategyCallBuy) spxExocet.getCall(InvApproach.SWING, TradeRatio.NONE, 0.50);
		System.out.println(swingCall.toString());
	}
	
	public static void launchTopExocet() {

	}

	
	// PRIVATE
	
	private static boolean isVolumeSpike() {
		return true;
	}
	
	private static boolean isFuturesGuidingUp(){
		return true;
	}
	
	private static boolean isFuturesGuidingDown() {
		return true;
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

	private static List<String> getSamplingRate() {
		return samplingRate;
	}

	private void setSamplingRate(List<String> samplingRate) {
		this.samplingRate = samplingRate;
	}



	
	// PRINT
	
	
	// SET GET
}
