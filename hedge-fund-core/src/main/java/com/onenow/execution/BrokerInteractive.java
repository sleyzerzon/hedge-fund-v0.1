package com.onenow.execution;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.sqs.model.Message;
import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.QueueName;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.data.HistorianConfig;
import com.onenow.data.MarketPrice;
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
	  
	  private StreamName streamName = StreamName.REALTIME;
	
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
	  public void getLiveQuotes() {
		  
		  Watchr.log(marketPortfolio.toString());
		  
		  QuoteRealtimeChain quoteRealtimeChain = new QuoteRealtimeChain(busIB.busController);
		  
		  List<Investment> invs = getMarketPortfolio().investments;
		  for(Investment inv:invs) {

			  Watchr.log(Level.INFO, "SUBSCRIBING TO LIVE QUOTE FOR: " + inv.toString());
			  quoteRealtimeChain.addRow(inv);
		  }
	  }	
	  
	  // GET HISTORICAL QUOTES
	  // API https://www.interactivebrokers.com/en/software/api/apiguide/tables/api_message_codes.htm
	  public void procesHistoricalQuotesRequests() {
		  		  
		  QuoteHistoryChain quoteHistoryChain = new QuoteHistoryChain(busIB.busController);
		  
		  while(true) {
			  List<Message> serializedMessages = SQS.receiveMessages(SQS.getHistoryQueueURL());			  
			  if(serializedMessages.size()>0) {	
				  for(Message message: serializedMessages) {
					  
					  if(busIB.isConnectionBroken) {
							Watchr.log(Level.WARNING, "Connection Broken");
							TimeParser.wait(20);
							busIB.connectToServer();
							quoteHistoryChain.controller = busIB.busController; // get the new one
					  } else {
						  if(busIB.isConnectionInactive) {
							  Watchr.log(Level.WARNING, "Connection Inactive");
							  TimeParser.wait(15);
						  } else {
							  quoteHistoryChain.processHistoryOneRequest(message);								  
						  }
						  // if connected and connection is active, then request
					  }
				  }
				  SQS.deleteMesssage(SQS.getHistoryQueueURL(), serializedMessages);
			  }
			  TimeParser.wait(1); // pace requests for messages from queue 
			  } 
		}
	  
		@Override
		public Integer readHistoricalQuotes(Investment inv, String end,
				HistorianConfig config, QuoteHistoryInvestment history) {
			
			QuoteHistoryChain quoteHistoryChain = new QuoteHistoryChain(busIB.busController);
			return quoteHistoryChain.readHistoricalQuotes(inv, end, config, history);
			
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

	@Override
	public StreamName getStream() {
		return streamName;
	}

}
