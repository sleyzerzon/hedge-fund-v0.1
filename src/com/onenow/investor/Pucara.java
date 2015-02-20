package com.onenow.investor;

import java.util.ArrayList;
import java.util.List;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.broker.BrokerInteractive;
import com.onenow.finance.InvApproach;
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

	public Pucara() {
		
	}
	
	public Pucara(String index, String expDate) {
		setIB(new BrokerInteractive(this));
		setBroker(new BrokerActivityImpl(IB));
		setIndexName(index);
		setExpDate(expDate);
		getIB().initMarket(index, expDate, 2100); // TODO: seed
	}

	
//	getChannelPrices(getContractFactory());

	public static void launchBottomExocet() {
		Exocet spxExocet = new Exocet(100, new Underlying(getIndexName()), getExpDate(), getBroker());
		StrategyCallBuy swingCall = (StrategyCallBuy) spxExocet.getCall(InvApproach.SWING, TradeRatio.NONE, 0.50);
		System.out.println(swingCall.toString());
	}
	
	public static void launchTopExocet() {

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
		
//		Contract index = contractFactory.indexToQuote("SPX");
//		getContractFactory().addChannel(getChannels(), index);
//		Contract index = contractFactory.getIndexToQuote("RUT");
//		getContractFactory().addChannel(getChannels(), index);
//		Contract option = contractFactory.optionToQuote("SPX");
//		getContractFactory().addChannel(getChannels(), option);

		for(int i=0; i<getChannels().size(); i++) {			
			Channel channel = getChannels().get(i);
			List<String> endList = getEndList(channel);
			
			for(int j=0; j<endList.size(); j++) {
				String end = endList.get(j);
											
//				QuoteBar quoteHistory = new QuoteBar(channel);
////				getController().reqHistoricalData(	channel.getContract(), 
//													end, 1, DurationUnit.DAY, BarSize._1_hour, 
//													WhatToShow.TRADES, false,
//													quoteHistory);
//			    Thread.sleep(12000);
//				System.out.println("...");
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



	
	// PRINT
	
	
	// SET GET
}
