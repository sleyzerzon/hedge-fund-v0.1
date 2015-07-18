package com.onenow.execution;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.sqs.model.Message;
import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.QueueName;
import com.onenow.constant.StreamName;
import com.onenow.constant.PriceType;
import com.onenow.data.HistorianConfig;
import com.onenow.data.MarketPrice;
import com.onenow.data.QuoteDepth;
import com.onenow.data.QuoteHistoryInvestment;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.io.SQS;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;
import com.onenow.risk.MarketAnalytics;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class BrokerInteractive implements BrokerInterface  {

	  private List<Underlying> underList;
	  private Portfolio marketPortfolio;
	  private List<Trade> trades;
	  private Portfolio myPortfolio;
	  private MarketPrice marketPrices;
	  private MarketAnalytics marketAnalytics;
	  
	  private StreamName streamName = StreamName.REALTIME_STAGING;
	
	  private BusWallStInteractiveBrokers busIB;

	  public BrokerInteractive() {

	  }
	
	  /**
	   * Constructor for HISTORY dataStream
	   * @param streamName
	   * @param busIB
	   */
	  public BrokerInteractive(StreamName streamName, BusWallStInteractiveBrokers busIB) { 
			
		  	this.streamName = streamName;
			this.busIB = busIB;
			
			connectToServices(busIB);			
	  }
	  
	  /**
	   * Get quotes after initializing overall market and my portfolio
	   * @throws ConnectException
	   */
	  public BrokerInteractive(StreamName streamName, Portfolio marketPortfolio, BusWallStInteractiveBrokers busIB) { 
		  
		this.streamName = streamName;
	    this.marketPortfolio = marketPortfolio;
		this.busIB = busIB;
		
		connectToServices(busIB);
		
	    // create new underlying list, portfolio, then initialize the market
	    this.underList = new ArrayList<Underlying>(); // TODO: get from portfolio?
	
	    // my porfolio, prices, and trades
	    this.myPortfolio = new Portfolio();
	    this.marketPrices = new MarketPrice(getMarketPortfolio(), this);
	    this.trades = new ArrayList<Trade>();
	  }
		  
	  private void connectToServices(BusWallStInteractiveBrokers bus) {
		  bus.connectToServer();
		  SQS.createQueue(QueueName.HISTORY_STAGING);
		  SQS.listQueues();
	  }

	
	  // GET REAL TIME QUOTES
	  /**
	   * For every currently-traded investment: request quotes
	   * Quotes are in response to specific request, or real-time notifications
	   */
	  public void getLiveData() {
		  
		  Watchr.log(marketPortfolio.toString());
		  
		  DataRealtimeChain dataRealtimeChain = new DataRealtimeChain(busIB.busController);
		  
		  List<Investment> invs = getMarketPortfolio().investments;
		  for(Investment inv:invs) {

			  Watchr.log(Level.INFO, "SUBSCRIBING TO LIVE QUOTE FOR: " + inv.toString());
			  dataRealtimeChain.addRow(inv);
		  }
	  }	
	  
	  // GET HISTORICAL QUOTES
	  // API https://www.interactivebrokers.com/en/software/api/apiguide/tables/api_message_codes.htm
	  public void getHistoricalData() {
		  		  
		  DataHistoryChain dataHistoryChain = new DataHistoryChain(busIB.busController);
		  
		  while(true) {
			  List<Message> serializedMessages = SQS.receiveMessages(SQS.getHistoryQueueURL());			  
			  if(serializedMessages.size()>0) {	
				  for(Message message: serializedMessages) {						
					  processIndividualMessageUntilSuccessful(dataHistoryChain, message);
				  } 
				  
				  SQS.deleteMesssage(SQS.getHistoryQueueURL(), serializedMessages);
			  }
			  TimeParser.sleep(1); // pace requests for messages from queue 
		  } 
		}

	private void processIndividualMessageUntilSuccessful(DataHistoryChain quoteHistoryChain, Message message) {
		  boolean reqSuccess = false;
		  while (!reqSuccess) {	// re-try if individual message does not get requested due to connection issues
			  reqSuccess = processIndividualMessage(quoteHistoryChain, message);
			  TimeParser.sleep(1);
		  } 
	}

	private boolean processIndividualMessage(final DataHistoryChain quoteHistoryChain, Message message) {
		
		  boolean success = false;
		
		  // waitWhileBrokerConnectionBroken();
			  
		  if(busIB.isConnectionBroken) {
			    // reconnect if remains broken for a while
				new Thread () {
					@Override public void run () {
					  	confirmAndReplaceBusController(quoteHistoryChain);
					}
				}.start();	

		  } 
		
		  // waitWhileFarmUnavailable();
		  
		  // if connected and connection is active, finally request
		  if(!busIB.isConnectionBroken && busIB.isFarmAvailable) {
			  quoteHistoryChain.processHistoryOneRequest(message);
			  success = true;
			  
		  }
		return success;
	}

	private void confirmAndReplaceBusController(DataHistoryChain quoteHistoryChain) {
	    TimeParser.sleep(120);
	    if(busIB.isConnectionBroken) {
			busIB.busController.disconnect();
			busIB.connectToServer();
			quoteHistoryChain.controller = busIB.busController; // get the new one
	    }
	}

	private void waitWhileBrokerConnectionBroken() {
		  int counter=0;
		  while (busIB.isConnectionBroken && counter<2) {
		      Watchr.log(Level.WARNING, "Connection Broken ");
			  // TimeParser.sleep(60);
			  counter++;
		  }
	}

	private void waitWhileFarmUnavailable() {
		  int counter=0;
		  while(!busIB.isFarmAvailable && counter <2) {
				Watchr.log(Level.WARNING, "Farm Unavailable");
				// TimeParser.sleep(15);
				counter++;
		  }
	}
	  
		@Override
		public Integer readHistoricalQuotes(Investment inv, String end,
				HistorianConfig config, QuoteHistoryInvestment history) {
			
			DataHistoryChain quoteHistoryChain = new DataHistoryChain(busIB.busController);
			return quoteHistoryChain.requestHistoricalData(inv, end, config, history);
			
		}

		
		  /**
		   * Get market depth where available
		   * No market depth for index/options
		   */
		  private void getMarketDepth() {
		    List<Investment> invs = getMarketPortfolio().investments;
		    for(Investment inv:invs) {
		      QuoteDepth resultPanel = new QuoteDepth(this, busIB.busController, inv);
		    }
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
		public Double getPrice(Investment inv, PriceType tradeType) {
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
	    List<Investment> invs = getMarketPortfolio().investments;
	    Integer notDone=0;
	    for(Investment inv:invs) {
	      if(inv instanceof InvestmentIndex) { // only check the index
	        Double buyPrice = marketPrices.readPrice(inv, PriceType.BID);
	        if(buyPrice==null) {
	          notDone++;
	          return false;
	        }
	        Double sellPrice = marketPrices.readPrice(inv, PriceType.ASK);
	        if(sellPrice==null) {
	          notDone++;
	          return false;
	        }
//	        Double closePrice = marketPrices.readPrice(inv, TradeType.CLOSE);
//	        if(closePrice==null) {
//	          notDone++;
//	          return false;
//	        }
	        Double lastPrice = marketPrices.readPrice(inv, PriceType.TRADED);
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

	@Override
	public StreamName getStream() {
		return streamName;
	}

}
