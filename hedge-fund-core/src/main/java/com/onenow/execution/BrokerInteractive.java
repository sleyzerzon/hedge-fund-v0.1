package com.onenow.execution;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.sqs.model.Message;
import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.InvestorRole;
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
	  
	  private InvestorRole investorRole = InvestorRole.REALTIME;
	
	  private BusWallStInteractiveBrokers busIB;

	  public BrokerInteractive() {

	  }
	
	  /**
	   * Constructor for HISTORY dataStream
	   * @param investorRole
	   * @param busIB
	   */
	  public BrokerInteractive(InvestorRole investorRole, BusWallStInteractiveBrokers busIB) { 
			
		  	this.investorRole = investorRole;
			this.busIB = busIB;
			
			connectToServices(busIB);			
	  }
	  
	  /**
	   * Get quotes after initializing overall market and my portfolio
	   * @throws ConnectException
	   */
	  public BrokerInteractive(InvestorRole streamName, Portfolio marketPortfolio, BusWallStInteractiveBrokers busIB) { 
		  
		this.investorRole = streamName;
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
		
		  // if connected and connection is active, finally request
		  if(!busIB.isConnectionBroken && busIB.isFarmAvailable) {
			  quoteHistoryChain.processHistoryOneRequest(message);
			  success = true;
		  } else {
			  	// TODO: wait to see failure mode
				// evaluateForReconnection(quoteHistoryChain);
		  }
		return success;
	}

	private void evaluateForReconnection(
			final DataHistoryChain quoteHistoryChain) {
		new Thread () {
			@Override public void run () {
				TimeParser.sleep(60);
				// received anything in the last seconds to indicate the connection is not broken anymore?
				if(busIB.isConnectionBroken) {
					busIB.busController.disconnect();
					busIB.connectToServer();
					quoteHistoryChain.controller = busIB.busController; // get the new one
				}
			}
		}.start();
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
	public InvestorRole getRole() {
		return investorRole;
	}

}
