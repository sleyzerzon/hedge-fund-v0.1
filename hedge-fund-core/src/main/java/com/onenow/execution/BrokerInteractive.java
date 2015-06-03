package com.onenow.execution;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.onenow.alpha.Broker;
import com.onenow.constant.BrokerMode;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.data.Channel;
import com.onenow.data.EventHistoryRequest;
import com.onenow.data.HistorianConfig;
import com.onenow.data.MarketPrice;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.io.Lookup;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;
import com.onenow.risk.MarketAnalytics;
import com.onenow.util.TimeParser;

public class BrokerInteractive implements Broker  {

	  private BrokerMode brokerMode;	
	  private List<Underlying> underList;
	  private Portfolio marketPortfolio;
	  private List<Trade> trades;
	  private Portfolio myPortfolio;
	  private MarketPrice marketPrices;
	  private MarketAnalytics marketAnalytics;
	
	  private ContractFactory contractFactory = new ContractFactory();
	//  private List<Channel> channels = new ArrayList<Channel>();
	  
	  private BusWallSt bus;

	  private static HashMap<String, QuoteHistory>		history;						// price history from L3
	  private static long lastQueryTime;

	  public BrokerInteractive() {
		  
	  }
	
	  /**
	   * Get quotes after initializing overall market and my portfolio
	   * @throws ConnectException
	   */
	  public BrokerInteractive(BrokerMode mode, Portfolio marketPortfolio, BusWallSt bus) { 
		  
		this.brokerMode = mode;
	    this.marketPortfolio = marketPortfolio;
		this.bus = bus;
		
	    bus.connectToServer();
	
	    // create new underlying list, portfolio, then initialize the market
	    this.underList = new ArrayList<Underlying>(); // TODO: get from portfolio?
	
	    // my porfolio, prices, and trades
	    this.myPortfolio = new Portfolio();
	    this.marketPrices = new MarketPrice(getMarketPortfolio(), this);
	    this.trades = new ArrayList<Trade>();
	  }
		  
	  // GET REAL TIME QUOTES
	  /**
	   * For every currently-traded investment: request quotes
	   * Quotes are in response to specific request, or real-time notifications
	   */
	  public void getLiveQuotes() {
		  List<Investment> invs = getMarketPortfolio().investments;
		  for(Investment inv:invs) {
			  System.out.println("\n\n" + "#PRICE# SUBSCRIBING TO LIVE QUOTE FOR: " + inv.toString());
			  QuoteRealTime quoteLive = new QuoteRealTime(bus.controller, marketPrices, inv);
		  }
	  }	
	  
	  // GET HISTORICAL QUOTES
	  // TODO: pool of threads so main flow is not blocked
	  public void procesHistoricalQuotesRequests() {

		  while(true) {
			  
			  // get events from SQS
			  EventHistoryRequest req = null;
		  
			  // get the history reference for the specific investment 
			  QuoteHistory invHist = lookupInvHistory(	req.investment, req.config.tradeType, 
														req.config.source, req.config.timing);
	
			  // TODO: handle the case of many requests with no response, which over-runs IB (50 max at a time) 
			  TimeParser.paceHistoricalQuery(lastQueryTime); 
	
			  // look for SQS requests for history
			  Integer reqId = readHistoricalQuotes(	req.investment, 
													TimeParser.getClose(TimeParser.getUndashedDate(TimeParser.getDashedDateMinus(req.toDashedDate, 1))), 
													req.config, invHist);
	
			  lastQueryTime = TimeParser.getTimestampNow();		
		  }
		}
		
	  /**
	   * Returns reference to object where history will be stored, upon asynchronous return
	   */
	  public Integer readHistoricalQuotes(Investment inv, String end, HistorianConfig config, QuoteHistory quoteHistory) {
	
		  Contract contract = ContractFactory.getContract(inv);
	
		  Integer reqId = bus.controller.reqHistoricalData(	contract, end, 
	    													1, config.durationUnit, config.barSize, config.whatToShow, 
	    													false, quoteHistory);
		  System.out.println("#PRICE# REQUESTED HISTORY FOR: " + inv.toString() + " ENDING " + end + " REQ ID " + reqId);
	    
		  return reqId;
	  }

		/**
		 * Every investment has it's own history from L3
		 * @param inv
		 * @param tradeType
		 * @param source
		 * @param timing
		 * @return
		 */
		private static QuoteHistory lookupInvHistory(	Investment inv, TradeType tradeType,
												InvDataSource source, InvDataTiming timing) {
			
			String key = Lookup.getInvestmentKey(	inv, tradeType,
													source, timing);
	
			QuoteHistory invHist = history.get(key);
			if(invHist==null) {
				invHist = new QuoteHistory(inv, tradeType, source, timing);
				history.put(key, invHist);			
			}
			return invHist;
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
		
		  /**
		   * Get market depth where available
		   * No market depth for index/options
		   */
		  private void getMarketDepth() {
		//    List<Investment> invs = getMarketPortfolio().investments;
		//    for(Investment inv:invs) {
		//      QuoteDepth resultPanel = new QuoteDepth(this, controller, inv);
		//    }
		  }
		  
		  
	  ///// BROKER
		@Override
		public List<Underlying> getUnderlying() {
			return underList;
		}
	
		@Override
		public List<Trade> getTrades() {
			return trades;
		}
	
		@Override
		public void enterTransaction(Transaction transaction) {
			getTrades().addAll(transaction.getTrades());
			getMyPortfolio().enterTransaction(transaction);
		}
	
		@Override
		public Portfolio getMyPortfolio() {
			return myPortfolio;
		}
	
		@Override
		public Portfolio getMarketPortfolio() {
			return marketPortfolio;
		}
	
		@Override
		public Double getPrice(Investment inv, TradeType tradeType) {
			Double price=0.0;
	
			price = marketPrices.readPrice(inv, tradeType);
	
			if(price==null) {
				return 0.0;
			}
			
	    return price;
	
	  }
	
	  
	
	  // TEST
	  /**
	   * Test if the prices are set for the investments under consideration
	   * @return
	   */
	  //TODO: use it for testing
	  private boolean allQuotesSet() {
	    boolean allSet=true;
	    List<Investment> invs = getMarketPortfolio().investments;
	    Integer notDone=0;
	    for(Investment inv:invs) {
	      if(inv instanceof InvestmentIndex) { // only check the index
	        Double buyPrice = marketPrices.readPrice(inv, TradeType.BUY);
	        if(buyPrice==null) {
	          notDone++;
	          return false;
	        }
	        Double sellPrice = marketPrices.readPrice(inv, TradeType.SELL);
	        if(sellPrice==null) {
	          notDone++;
	          return false;
	        }
	        Double closePrice = marketPrices.readPrice(inv, TradeType.CLOSE);
	        if(closePrice==null) {
	          notDone++;
	          return false;
	        }
	        Double lastPrice = marketPrices.readPrice(inv, TradeType.TRADED);
	        if(lastPrice==null) {
	          notDone++;
	          return false;
	        }
	      }
	    }
	    Integer all=invs.size()*4;
	    if(notDone/all>0.9) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	
	  // PRINT
	  public String toString() {
	    String s = "";
	    s = s + "UNDERLYING: " + "\n" + underList.toString() + "\n";
	    s = s + "MARKET PORTFOLIO: " + "\n" + getMarketPortfolio().toString() + "\n";
	    s = s + "PRICES" + "\n" + marketPrices.toString() + "\n";
	    s = s + "TRADES: " + "\n";
	    for(Trade trade:getTrades()) {
	      s = s + trade.toString() + "\n";
	    }
	    s = s + "MY PORTFOLIO" + "\n" + getMyPortfolio() + "\n";
	    return s;
	  }
	
	
	  // SET GET
		@Override
		public BrokerMode getMode() {
			return brokerMode;
		}
}
