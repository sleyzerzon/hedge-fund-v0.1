package com.onenow.execution;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.ib.controller.ApiConnection.ILogger;
import com.onenow.constant.BrokerMode;
import com.onenow.constant.ConnectionStatus;
import com.onenow.constant.TradeType;
import com.onenow.data.Channel;
import com.onenow.data.HistorianConfig;
import com.onenow.data.MarketPrice;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.Portfolio;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;
import com.onenow.portfolio.BrokerController.ConnectionHandler;
import com.onenow.portfolio.BrokerController.IBulletinHandler;
import com.onenow.portfolio.BrokerController.ITimeHandler;
import com.onenow.risk.MarketAnalytics;
import com.onenow.util.ParseDate;

public class BrokerInteractive implements Broker, ConnectionHandler  {

  private BrokerMode brokerMode;	
  private List<Underlying> underList;
  private Portfolio marketPortfolio;
  private List<Trade> trades;
  private Portfolio myPortfolio;
  private MarketPrice marketPrices;
  private MarketAnalytics marketAnalytics;


  private ILogger inLogger = new SummitLogger();
  private ILogger outLogger = new SummitLogger();
  private BrokerController controller = new BrokerController(this, getInLogger(), getOutLogger());

  private final ArrayList<String> accountList = new ArrayList<String>();

  private ContractFactory contractFactory = new ContractFactory();
  private List<Channel> channels = new ArrayList<Channel>();

  private ParseDate parseDate = new ParseDate();
  private NetworkService brokerService = new NetworkConfig().broker;

  public BrokerInteractive() {
	  
  }

  /**
   * Get quotes after initializing overall market and my portfolio
   * @throws ConnectException
   */
  public BrokerInteractive(BrokerMode mode, Portfolio marketPortfolio) { 
	  
	this.brokerMode = mode;
    this.marketPortfolio = marketPortfolio;
	  
    connectToServer();

    // create new underlying list, portfolio, then initialize the market
    setUnderList(new ArrayList<Underlying>()); // TODO: get from portfolio?

    // my porfolio, prices, and trades
    setMyPortfolio(new Portfolio());
    setMarketPrices(new MarketPrice(getMarketPortfolio(), this));
    setTrades(new ArrayList<Trade>());
  }



// INIT
  /**
   * Connect to gateway at set IP and port
   */
  private void connectToServer() {
	boolean tryToConnect = true;
    while(tryToConnect) {		    		
		try {				
			tryToConnect = false;
			System.out.println("\n" + "CONNECTING TO BROKER IN HISTORIAN...");				
		    controller = new BrokerController((com.onenow.portfolio.BrokerController.ConnectionHandler) this, getInLogger(), getOutLogger());
		    controller.connect(	brokerService.URI, brokerService.port, 
		    							0, null);  
		} catch (Exception e) {
			tryToConnect = true;
			System.out.println("...COULD NOT CREATE INTERACTIVE BROKER INSIDE HISTORIAN" + "\n");
			e.printStackTrace();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {}
		}			
	} // end try to connect
	System.out.println("CONNECTED TO HISTORIAN BROKER!");
  }
  
  // GET QUOTES
  /**
   * For every currently-traded investment: request quotes
   * Quotes are in response to specific request, or real-time notifications
   */
  public void getLiveQuotes() {
    List<Investment> invs = getMarketPortfolio().investments;
    for(Investment inv:invs) {
      System.out.println("#PRICE# SUBSCRIBING TO LIVE QUOTE FOR: " + inv.toString());
      QuoteTable quoteLive = new QuoteTable(controller, getMarketPrices(), inv);
      if(true){
    	  
      }
    }
  }

  /**
   * Returns reference to object where history will be stored, upon asynchronous return
   */
  public void readHistoricalQuotes(Investment inv, String end, HistorianConfig config, QuoteHistory quoteHistory) {

    Contract contract = getContractFactory().getContract(inv);

    int reqId = controller.reqHistoricalData(	contract, end, 
    												1, config.durationUnit, config.barSize, config.whatToShow, 
    												false, quoteHistory);
    System.out.println("#PRICE# REQUESTED HISTORY FOR: " + inv.toString() + " ENDING " + end + " REQ ID " + reqId);
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
        list.add(parseDate.removeDash(date).concat(" 16:30:00"));
      } catch (Exception e) { } // nothing to do
    }
    for(int j=channel.getSupDayList().size()-1; j>=0; j--) { // Support
      try {
        date = channel.getSupDayList().get(j);
        list.add(parseDate.removeDash(date).concat(" 16:30:00"));
      } catch (Exception e) { } // nothing to do
    }
    for(int k=channel.getRecentDayMap().size()-1; k>=0; k--) { // Recent
      try {
        date = channel.getRecentDayList().get(k); // Recent
        list.add(parseDate.removeDash(date).concat(" 16:30:00"));
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
    return getUnderList();
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

    price = getMarketPrices().readPrice(inv, tradeType);

    if(price==null) {
      return 0.0;
    }

    return price;

  }

  ///// CONNECTION HANDLER
  @Override
  public void connected() {
    show(ConnectionStatus.CONNECTED.toString());

    controller.reqCurrentTime( new ITimeHandler() {
      @Override public void currentTime(long time) {
        show( "Server date/time is " + Formats.fmtDate(time * 1000) );
      }
    });

    controller.reqBulletins( true, new IBulletinHandler() {
      @Override public void bulletin(int msgId, NewsType newsType, String message, String exchange) {
        String str = String.format( "Received bulletin:  type=%s  exchange=%s", newsType, exchange);
        show( str);
        show( message);
      }
    });
  }

  @Override
  public void disconnected() {
    show(ConnectionStatus.DISCONNECTED.toString());

  }

  @Override
  public void accountList(ArrayList<String> list) {
    show( "Received account list");
    getAccountList().clear();
    getAccountList().addAll( list);
  }

  @Override
  public void error(Exception e) {
    show( e.toString() );
  }

  @Override
  public void message(int id, int errorCode, String errorMsg) {
    show( id + " " + errorCode + " " + errorMsg);
  }

  @Override
  public void show(String string) {
    System.out.println(string);
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
        Double buyPrice = getMarketPrices().readPrice(inv, TradeType.BUY);
        if(buyPrice==null) {
          notDone++;
          return false;
        }
        Double sellPrice = getMarketPrices().readPrice(inv, TradeType.SELL);
        if(sellPrice==null) {
          notDone++;
          return false;
        }
        Double closePrice = getMarketPrices().readPrice(inv, TradeType.CLOSE);
        if(closePrice==null) {
          notDone++;
          return false;
        }
        Double lastPrice = getMarketPrices().readPrice(inv, TradeType.TRADED);
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
    s = s + "UNDERLYING: " + "\n" + getUnderList().toString() + "\n";
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
  private ILogger getInLogger() {
    return inLogger;
  }

  private void setInLogger(ILogger inLogger) {
    this.inLogger = inLogger;
  }

  private ILogger getOutLogger() {
    return outLogger;
  }

  private void setOutLogger(ILogger outLogger) {
    this.outLogger = outLogger;
  }

  private ArrayList<String> getAccountList() {
    return accountList;
  }


  private MarketPrice getMarketPrices() {
    return marketPrices;
  }


  private void setMarketPrices(MarketPrice marketPrices) {
    this.marketPrices = marketPrices;
  }


  private MarketAnalytics getMarketAnalytics() {
    return marketAnalytics;
  }


  private void setMarketAnalytics(MarketAnalytics marketAnalytics) {
    this.marketAnalytics = marketAnalytics;
  }


  private void setMyPortfolio(Portfolio myPortfolio) {
    this.myPortfolio = myPortfolio;
  }


  private List<Underlying> getUnderList() {
    return underList;
  }


  private void setUnderList(List<Underlying> underList) {
    this.underList = underList;
  }


  private void setTrades(List<Trade> trades) {
    this.trades = trades;
  }


  public ContractFactory getContractFactory() {
    return contractFactory;
  }

  public void setContractFactory(ContractFactory contractFactory) {
    this.contractFactory = contractFactory;
  }

  public List<Channel> getChannels() {
    return channels;
  }

  public void setChannels(List<Channel> channels) {
    this.channels = channels;
  }

	@Override
	public BrokerMode getMode() {
		return brokerMode;
	}
}
