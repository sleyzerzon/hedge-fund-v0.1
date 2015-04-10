package com.onenow.portfolio;

import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.InvApproach;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.Channel;
import com.onenow.data.InitMarket;
import com.onenow.data.MarketPrice;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.research.Candle;
import com.onenow.research.Chart;
import com.onenow.util.ParseDate;

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
	// 5-day (high 2016) ~$0.40 , (mid 2012) $0.55 ... , (low 2002): $1.1  => 2X
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
	
	private static Portfolio marketPortfolio = new Portfolio();
	private static MarketPrice marketPrice = new MarketPrice();
	private static Underlying index;
	
	static List<String> samplingRate = new ArrayList<String>();


//***	 Look for signals, particularly at resitance & support
//***	 Confirm via price, volume, and momentum
//***	 Become familiar with the rythm of the underlying
	
	public Pucara() {
		
	}
	
	public Pucara(Underlying index) throws InterruptedException {
		setIndex(index);
		InitMarket init = new InitMarket(index, getMarketPortfolio()); 		
		setSamplingRate(getSampling("all"));
		System.out.println("SAMPLING: " + getSamplingRate().toString() + "\n");
	}	
	
	public static void launch() throws InterruptedException {

//		getChannelPrices(getContractFactory());

//		while(true) {
			
			setAllCharts();
			analyzeAllInvestmentCharts();
			EntranceExitDecisioning decisioning = new EntranceExitDecisioning(getIndex());

			if(decisioning.EnterNowAtTop()) {
				launchBottomExocet(getIndex());
			}

			if(decisioning.EnterNowAtTop()) {
				launchTopExocet(getIndex());
			}
			
			System.out.println(",,,,,");
			Thread.sleep(50000);
//		}
	}
	
	public static void launchBottomExocet(Underlying under) {
//		String expDate = "20150319";
//		Exocet spxExocet = new Exocet(100, under, expDate, getBroker());
//		StrategyCallBuy swingCall = (StrategyCallBuy) spxExocet.getCall(InvApproach.SWING, TradeRatio.NONE, 0.50);
//		System.out.println(swingCall.toString());
	}

	public static void launchTopExocet(Underlying index) {
//		Exocet spxExocet = new Exocet(100, new Underlying(getIndexName()), getExpDate(), getBroker());
//		StrategyCallBuy swingCall = (StrategyCallBuy) spxExocet.getCall(InvApproach.SWING, TradeRatio.NONE, 0.50);
//		System.out.println(swingCall.toString());
	}

	
	private static void setAllCharts() {
		String fromDate = "2015-02-21"; // TODO: date
		String toDate = "2015-02-28";
		for(String sampling:getSamplingRate()) {
			for(Investment inv:getMarketPortfolio().getInvestments()) {
				setInvestmentChart(inv, fromDate, toDate, sampling);
			}
		}
	}
	
	private static void analyzeAllInvestmentCharts() {
		for(Investment inv:getMarketPortfolio().getInvestments()) {
			for(String trading:getTradingOptions()) {
				String analysis = "";
				analysis = analysis + "=====" + inv.toString() + "=====" + "\n";
				for(String sampling:getSampling(trading)) { 
					analysis = analysis + getInvestmentAnalysis(inv, sampling);
				}			
				System.out.println(analysis + "\n");
			}			
		}	
	}

	private static String getInvestmentAnalysis(Investment inv, String sampling) {
		String s = "\n";
		s = s + ">> " + sampling + "\t"; 
		Chart chart = inv.getCharts().get(sampling);
		if(chart!=null) { // not all sampling cases may be available
			s = s + getChartAnalysis(chart);			
		} else {
			s = s + "null";
		}
		s = s + "\n";
		return s;
	}

	// TODO: underlying price, resistance/support?
	private static void setInvestmentChart(Investment inv, String fromDate, String toDate, String sampling) {

		Chart chart = new Chart();
		chart = getMarketPrice().queryChart(inv, TradeType.TRADED.toString(), fromDate, toDate, sampling);
		
		if(!chart.getSizes().isEmpty()) {
			inv.getCharts().put(sampling, chart); // sampling is key	
			System.out.println("+ chart " + inv.toString() +  " " + sampling + "\n" + chart.toString());			
		} else {
			System.out.println("- chart " + inv.toString() + " " + sampling);
		}		
	}
	
	
	private static String getChartAnalysis(Chart chart) {
		String s = "";
		chart.setAnalysis();
		for(int i=0; i<chart.getPrices().size(); i++) {
			s = s + chart.getPriceAnalysis(i);
			s = s + chart.getVolumeAnalysis(i);
			s = s + chart.getMomentumAnalysis(i);
		}		
		return s;
	}
		
	private static List<String> getSampling(String rate) {
		List<String> list = new ArrayList<String>();
		if(rate.equals(SamplingRate.SCALP.toString()) || rate.equals("all")) {
			list.addAll(getScalpSampling());
		}
		if(rate.equals(SamplingRate.SWING.toString()) || rate.equals("all")) {
			list.addAll(getSwingSampling());
		}
		if(rate.equals(SamplingRate.TREND.toString()) || rate.equals("all")) {
			list.addAll(getTrendSampling());
		}
		return list;
	}
	
	private static List<String> getTradingOptions() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.SCALP.toString());
		list.add(SamplingRate.SWING.toString());
		list.add(SamplingRate.TREND.toString());
		return list;
	}
	private static List<String> getScalpSampling() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.SCALPSHORT.toString());
		list.add(SamplingRate.SCALPMEDIUM.toString());
		list.add(SamplingRate.SCALPLONG.toString());					
		return list;
	}
	private static List<String> getSwingSampling() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.SWINGSHORT.toString());
		list.add(SamplingRate.SWINGMEDIUM.toString());
		list.add(SamplingRate.SWINGLONG.toString());								
		return list;
	}
	private static List<String> getTrendSampling() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.TRENDSHORT.toString());
		list.add(SamplingRate.TRENDMEDIUM.toString());
		list.add(SamplingRate.TRENDLONG.toString());							
		return list;
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

	private static Underlying getIndex() {
		return index;
	}

	private static void setIndex(Underlying index) {
		Pucara.index = index;
	}



	
	// PRINT
	
	
	// SET GET
}
