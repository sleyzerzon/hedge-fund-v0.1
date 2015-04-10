package com.onenow.execution;

import java.util.ArrayList;
import java.util.List;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.ib.controller.ApiConnection.ILogger;
import com.onenow.constant.ConnectionStatus;
import com.onenow.constant.DataType;
import com.onenow.constant.InvType;
import com.onenow.constant.TradeType;
import com.onenow.data.InitMarket;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.MarketPrice;
import com.onenow.instrument.Portfolio;
import com.onenow.instrument.Trade;
import com.onenow.instrument.Transaction;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.Pucara;
import com.onenow.portfolio.QuoteDepth;
import com.onenow.portfolio.QuoteTable;
import com.onenow.portfolio.SummitLogger;
import com.onenow.portfolio.BrokerController.ConnectionHandler;
import com.onenow.portfolio.BrokerController.IBulletinHandler;
import com.onenow.portfolio.BrokerController.ITimeHandler;
import com.onenow.portfolio.QuoteDepth.DeepRow;
import com.onenow.risk.MarketAnalytics;

public class BrokerInteractive implements Broker, ConnectionHandler  {

	private Pucara pucara;
	
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
	
	public BrokerInteractive() {
		connectToServer();
		
		setUnderList(new ArrayList<Underlying>());
		setMarketPortfolio(new Portfolio());
		InitMarket init = new InitMarket(getMarketPortfolio());
		
		setMyPortfolio(new Portfolio());
		setMarketPrices(new MarketPrice());
		setTrades(new ArrayList<Trade>());		
		
		getQuotes();
//		getMarketDepth(); No market depth for index/options
	}
	
	private void getQuotes() {
		List<Investment> invs = getMarketPortfolio().getInvestments();
		for(Investment inv:invs) { 
			QuoteTable quote = new QuoteTable(getController(), getMarketPrices(), inv);
		}
	}
	
	private void connectToServer() {
		setController(new BrokerController((com.onenow.portfolio.BrokerController.ConnectionHandler) this, getInLogger(), getOutLogger()));		
		getController().connect("127.0.0.1", 4001, 0, null);  // app port 7496	
		
	}

	
	private void getMarketDepth() {
		List<Investment> invs = getMarketPortfolio().getInvestments();
		for(Investment inv:invs) { 
			QuoteDepth resultPanel = new QuoteDepth(this, getController(), inv);
		}
	}


	private boolean allQuotesSet() {
		boolean allSet=true;
		List<Investment> invs = getMarketPortfolio().getInvestments();
		Integer notDone=0;
		for(Investment inv:invs) {
			if(inv instanceof InvestmentIndex) { // only check the index
				Double buyPrice = getMarketPrices().getPriceFromMap(inv, TradeType.BUY.toString());
				if(buyPrice==null) {
					notDone++;
					return false;
				}
				Double sellPrice = getMarketPrices().getPriceFromMap(inv, TradeType.SELL.toString());
				if(sellPrice==null) {
					notDone++;
					return false;
				}
				Double closePrice = getMarketPrices().getPriceFromMap(inv, TradeType.CLOSE.toString());
				if(closePrice==null) {
					notDone++;
					return false;
				}
				Double lastPrice = getMarketPrices().getPriceFromMap(inv, TradeType.TRADED.toString());
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
		
		price = getMarketPrices().getPriceFromMap(inv, dataType);
	
		if(price==null) {
			return 0.0;
		}
		
		return price;
		
	}
			
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

	private BrokerController getController() {
		return controller;
	}

	private void setController(BrokerController controller) {
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


	private Pucara getPucara() {
		return pucara;
	}


	private void setPucara(Pucara pucara) {
		this.pucara = pucara;
	}

}
