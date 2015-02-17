package com.onenow.investor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.broker.BrokerInteractive;
import com.onenow.finance.InvProb;
import com.onenow.finance.StrategyIronCondor;
import com.onenow.finance.TradeRatio;
import com.onenow.finance.Underlying;

public class InvestorMain {

	private static ContractFactory contractFactory = new ContractFactory();
	private static List<Channel> channels = new ArrayList<Channel>();

	ParseDate parser = new ParseDate();

	
	public static void main(String[] args) throws ParseException, InterruptedException {

		BrokerActivityImpl broker = new BrokerActivityImpl(new BrokerInteractive());

//		getChannelPrices(getContractFactory());

		Exocet spxExocet = new Exocet(100, new Underlying("SPX"), "20150319", broker);
		StrategyIronCondor swing = spxExocet.getIronCondor(InvProb.SWING, TradeRatio.NONE, 0.50);
		System.out.println(spxExocet.toString());
		
	}
	
	
	private void getChannelPrices(ContractFactory contractFactory) throws InterruptedException {
		
//		Contract index = contractFactory.indexToQuote("SPX");
//		getContractFactory().addChannel(getChannels(), index);
		Contract index = contractFactory.getIndexToQuote("RUT");
		getContractFactory().addChannel(getChannels(), index);
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


	// SET GET
	private static ContractFactory getContractFactory() {
		return contractFactory;
	}


	private static void setContractFactory(ContractFactory contractFactory) {
		InvestorMain.contractFactory = contractFactory;
	}


	private static List<Channel> getChannels() {
		return channels;
	}


	private static void setChannels(List<Channel> channels) {
		InvestorMain.channels = channels;
	}

}
