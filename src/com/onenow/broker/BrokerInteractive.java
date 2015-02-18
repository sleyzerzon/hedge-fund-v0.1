package com.onenow.broker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.ib.controller.ApiConnection.ILogger;
import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.InvestmentIndex;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.InvestmentStock;
import com.onenow.finance.MarketAnalytics;
import com.onenow.finance.MarketPrice;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;
import com.onenow.investor.ConnectionStatus;
import com.onenow.investor.Contract;
import com.onenow.investor.ContractFactory;
import com.onenow.investor.DataType;
import com.onenow.investor.InvestorController;
import com.onenow.investor.QuoteTable;
import com.onenow.investor.SummitLogger;
import com.onenow.investor.InvestorController.ConnectionHandler;
import com.onenow.investor.InvestorController.IBulletinHandler;
import com.onenow.investor.InvestorController.ITimeHandler;

public class BrokerInteractive implements Broker, ConnectionHandler  {

	private boolean priceUpdate;
	
	private List<Underlying> underList;
	private Portfolio marketPortfolio;
	private List<Trade> trades;
	private Portfolio myPortfolio; 
	private MarketPrice marketPrices;
	private MarketAnalytics marketAnalytics;

	
	private ILogger inLogger = new SummitLogger();
	private ILogger outLogger = new SummitLogger();
	private InvestorController controller = new InvestorController(this, getInLogger(), getOutLogger());
		
	private final ArrayList<String> accountList = new ArrayList<String>();
	
	public BrokerInteractive(boolean priceUpdate) {
		setPriceUpdate(priceUpdate);
		
		connectToServer();
		
		setUnderList(new ArrayList<Underlying>());
		setMarketPortfolio(new Portfolio());
		setMyPortfolio(new Portfolio());
		setMarketPrices(new MarketPrice());
		setTrades(new ArrayList<Trade>());
	}

	
	public synchronized void notifyPriceSet() {
		if(allQuotesSet()) {
			setPriceUpdate(true);
		    notifyAll();
//		    System.out.println("^^^ ALL SET");
		}
	}

	public void setLastTime(Investment inv, Long lastTime) {
		getMarketPrices().setLastTime(inv, lastTime);
		System.out.println("Last time " +  getMarketPrices().getLastTime(inv, DataType.LASTTIME.toString()));
	}

	public void setAskPrice(Investment inv, Double ask) {
		getMarketPrices().setAskPrice(inv, ask);
		System.out.println("Quote Ask $ " +  getMarketPrices().getPrice(inv, TradeType.BUY.toString()));
		notifyPriceSet();
	}
	public void setBidPrice(Investment inv, Double bid) {
		getMarketPrices().setBidPrice(inv, bid);
		System.out.println("Quote Bid $ " +  getMarketPrices().getPrice(inv, TradeType.SELL.toString()));		
		notifyPriceSet();
	}
	public void setLastPrice(Investment inv, Double last) {
		getMarketPrices().setClosePrice(inv, last);
		System.out.println("Quote Last $ " +  getMarketPrices().getPrice(inv, TradeType.LAST.toString()));	
		notifyPriceSet();
	}
	public void setClosePrice(Investment inv, Double close) {
		getMarketPrices().setClosePrice(inv, close);
		System.out.println("Quote Close $ " +  getMarketPrices().getPrice(inv, TradeType.CLOSE.toString()));	
		notifyPriceSet();
	}
	
	private void connectToServer() {
		setController(new InvestorController((com.onenow.investor.InvestorController.ConnectionHandler) this, getInLogger(), getOutLogger()));		
		getController().connect("127.0.0.1", 4001, 0, null);  // app port 7496	
		
	}

	public void initMarket(String name, String expDate, Integer seed) { // create the investments
		Underlying under = new Underlying(name);
		
		InvestmentIndex index = new InvestmentIndex(under);
		Trade indexTrade = new Trade(index, TradeType.BUY, 1, 0.0);
		Transaction indexTrans = new Transaction(indexTrade);
		getMarketPortfolio().enterTransaction(indexTrans);
		
		for (Double strike=(double) (seed-200); strike<(seed+200); strike=strike+5) {
			Investment call = new InvestmentOption(under, InvType.CALL, expDate, strike);
			Investment put = new InvestmentOption(under, InvType.PUT, expDate, strike);
			Trade callTrade = new Trade(call, TradeType.BUY, 1, 0.0);
			Trade putTrade = new Trade(call, TradeType.BUY, 1, 0.0);
			Transaction optTrans = new Transaction(callTrade, putTrade);
			getMarketPortfolio().enterTransaction(optTrans);
		}

		System.out.println(getMarketPortfolio().toString());
		
		getQuotes();

	}
	
	private void getQuotes() {
		List<Investment> invs = getMarketPortfolio().getInvestments();
		for(Investment inv:invs) { 
			QuoteTable quoteIndex = new QuoteTable(this, getController(), inv);
		}
	}

	private boolean allQuotesSet() {
		boolean allSet=true;
		List<Investment> invs = getMarketPortfolio().getInvestments();
		Integer notDone=0;
		for(Investment inv:invs) {
			if(inv instanceof InvestmentIndex) { // only check the index
				Double buyPrice = getMarketPrices().getPrice(inv, TradeType.BUY.toString());
				if(buyPrice==null) {
					notDone++;
					return false;
				}
				Double sellPrice = getMarketPrices().getPrice(inv, TradeType.SELL.toString());
				if(sellPrice==null) {
					notDone++;
					return false;
				}
				Double closePrice = getMarketPrices().getPrice(inv, TradeType.CLOSE.toString());
				if(closePrice==null) {
					notDone++;
					return false;
				}
				Double lastPrice = getMarketPrices().getPrice(inv, TradeType.LAST.toString());
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
	public Double getPrice(Investment inv, String dataType) {
		Double price=0.0;
		
		price = getMarketPrices().getPrice(inv, dataType);
	
		if(price==null) {
			return 0.0;
		}
		
		return price;
		
	}
		
		
//		// TODO: type
//		
//		Double price=0.0;
//		
//		String ticker = inv.getUnder().getTicker();
//
//		QuoteTable quoteTable = new QuoteTable(getController());
//
//		if(inv instanceof InvestmentIndex) {
//			Contract index = getContractFactory().indexToQuote(ticker);			
//			quoteTable.addContract(index);		
//			try {
//				Thread.sleep(20000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			};
//			System.out.println("XXXXXXXXXXXXXXXXXXX INDEX " + price);
//		}
//
//		if(inv instanceof InvestmentOption) {
//			Contract option = getContractFactory().optionToQuote(ticker);			
//			quoteTable.addContract(option);
//			try {
//				Thread.sleep(20000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			};
//			System.out.println("XXXXXXXXXXXXXXXXXXX OPTION " + price);
//		}
//		
//
//		price = quoteTable.getLastClose();
//
//		
//		return price;
//	}
	
	///// CONNECTION HANDLER
	@Override
	public void connected() {
		show(ConnectionStatus.CONNECTED.toString());
		
		getController().reqCurrentTime( new ITimeHandler() {
			@Override public void currentTime(long time) {
				show( "Server date/time is " + Formats.fmtDate(time * 1000) );
			}
		});
		
		getController().reqBulletins( true, new IBulletinHandler() {
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

	private InvestorController getController() {
		return controller;
	}

	private void setController(InvestorController controller) {
		this.controller = controller;
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


	private void setMarketPortfolio(Portfolio marketPortfolio) {
		this.marketPortfolio = marketPortfolio;
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


	private boolean isPriceUpdate() {
		return priceUpdate;
	}


	private void setPriceUpdate(boolean priceUpdate) {
		this.priceUpdate = priceUpdate;
	}

}
