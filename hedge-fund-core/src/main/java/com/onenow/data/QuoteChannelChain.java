package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.util.TimeParser;

public class QuoteChannelChain {

	
	public QuoteChannelChain() {
		
	}
	
	  // CHANNELS
	  /**
	   * Get price channel price history
	   */
	  public void getChannelPrices() throws InterruptedException {
	//    InvestmentIndex indexInv = new InvestmentIndex(new Underlying("SPX"));
	//    Contract index = getContractFactory().getContract(indexInv);
	//    getContractFactory().addChannel(getChannels(), index);
	//
	////		Contract index = contractFactory.getIndexToQuote("RUT");
	////		getContractFactory().addChannel(getChannels(), index);
	////		Contract option = contractFactory.getOptionToQuote(indexInv);
	////		getContractFactory().addChannel(getChannels(), option);
	//
	//    for(int i=0; i<getChannels().size(); i++) {
	//      Channel channel = getChannels().get(i);
	//      List<String> endList = getEndList(channel);
	//
	//      for(int j=0; j<endList.size(); j++) {
	//        String end = endList.get(j);
	//
	//        System.out.println("\n..." + "getting historical quotes for " + indexInv.toString());
	//
	//// TODO: add history argument				QuoteHistory quoteHistory = readHistoricalQuotes(channel.getInvestment(), end);
	//
	//        Thread.sleep(12000);
	//        System.out.println("...");
	//      }
	//      System.out.println(channel.toString());
	//    }
	  }
	
	  private List<String> getEndList(Channel channel) {
		  List<String> list = new ArrayList<String>();
		  String date="";
		  for(int i=channel.getResDayList().size()-1; i>=0; i--) { // Resistance
			  try {
				  date = channel.getResDayList().get(i);
				  list.add(TimeParser.removeDash(date).concat(" 16:30:00"));
			  } catch (Exception e) { } // nothing to do
		  }
		  
		  for(int j=channel.getSupDayList().size()-1; j>=0; j--) { // Support
			 try {
				 date = channel.getSupDayList().get(j);
				 list.add(TimeParser.removeDash(date).concat(" 16:30:00"));
			  } catch (Exception e) { } // nothing to do
		  }
		  
		  for(int k=channel.getRecentDayMap().size()-1; k>=0; k--) { // Recent
			 try {
				 date = channel.getRecentDayList().get(k); // Recent
				 list.add(TimeParser.removeDash(date).concat(" 16:30:00"));
			  } catch (Exception e) { } // nothing to do
		  }
		return list;
	  }
}
