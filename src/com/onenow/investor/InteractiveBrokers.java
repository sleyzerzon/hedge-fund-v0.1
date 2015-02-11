package com.onenow.investor;

import java.util.ArrayList;
import java.util.List;

import apidemo.MarketDataPanel.BarResultsPanel;
import apidemo.util.TCombo;

import com.ib.client.ComboLeg;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.MktDataType;
import com.ib.client.Types.NewsType;
import com.ib.client.Types.SecType;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.Bar;
import com.ib.controller.Formats;
import com.onenow.finance.Underlying;
import com.onenow.investor.InvestorController.ConnectionHandler;
import com.onenow.investor.InvestorController.IBulletinHandler;
import com.onenow.investor.InvestorController.IHistoricalDataHandler;
import com.onenow.investor.InvestorController.ITimeHandler;

public class InteractiveBrokers implements ConnectionHandler {

	private int port = 7496; 
	private String hostDefault = "127.0.0.1";
	private String host =  "localhost";
	private int clientDefault = Integer.parseInt("0");
//	private int clientId = Integer.parseInt("pablo0000"); 
	
	private ILogger inLogger = new SummitLogger();
	private ILogger outLogger = new SummitLogger();
	private InvestorController controller = new InvestorController(this, getInLogger(), getOutLogger());
		
	private final ArrayList<String> accountList = new ArrayList<String>();
		
	final TCombo<MktDataType> marketCombo = new TCombo<MktDataType>( MktDataType.values() );
	
	final ArrayList<Bar> m_rows = new ArrayList<Bar>();
	
	ParseDate parser = new ParseDate();


	// LOOK FOR FREEK-OUT IN LAST 30 DAYS, SET CHANNEL
	// HAVE 7+ (14) DAYS IN CASE OVER-BUYING/SELLING CONTINUES
	// measured at time that run-up + sell-off end (Warn level); before OVER-REACTION begins
	// ACT: buy AFTER over-sold ends, 
	// ACT: calls after over-buying ends: sell ITM, buy OTM 
	// ==> 30+ minute Full Stochastic: 
	// TODO: generate that stochastic 
	
	private static List<Channel> channels = new ArrayList<Channel>();
	private ContractFactory contractFactory = new ContractFactory();
	
	public void run() throws InterruptedException {

		setController(new InvestorController((com.onenow.investor.InvestorController.ConnectionHandler) this, getInLogger(), getOutLogger()));
		
		getController().connect("127.0.0.1", 4001, 0, null);  // app port 7496	
		
//		QuoteModel qModel = new QuoteModel(getController());
//		qModel.addContract(cf.stockToQuote());
				
		getContractFactory().setChannels(channels);
		getChannelPrice(getContractFactory());
		System.out.println(channels.toString());

		// underPrice
		// optionPrice()
		//
		// calculateImpliedVolatility()
		// calculateOptionPrice() gets greeks too
		//
		// ratios
		// risk level

		getOptionPrice(getContractFactory());
		
		// IRealTimeBarHandler
	}

	private void getOptionPrice(ContractFactory cf) throws InterruptedException {
		QuoteHistoryModel qHistory = new QuoteHistoryModel(getController(), getChannels());
		qHistory.addContract(	cf.optionToQuote("SPX"), 
								"20150206 16:30:00", 1, DurationUnit.DAY, BarSize._1_hour, 
								WhatToShow.TRADES, false
								);
		System.out.println("...");
		Thread.sleep(12000);
		System.out.println(qHistory.toString());
	}
	
	private void getChannelPrice(ContractFactory cf) throws InterruptedException {
		for(int i=0; i<getChannels().size(); i++) {			
			Channel channel = getChannels().get(i);
			List<String> endList = getEndList(channel);
			
			for(int j=0; j<endList.size(); j++) {
				String end = endList.get(j);
											
				QuoteHistoryModel qHistory = new QuoteHistoryModel(getController(), getChannels());
				qHistory.addContract(	cf.indexToQuote(channel.getUnder().getTicker()), 
										end, 1, DurationUnit.DAY, BarSize._1_hour, 
										WhatToShow.TRADES, false
										);
				System.out.println("...");
				Thread.sleep(12000);
			}
		}
	}
	
	private List<String> getEndList(Channel channel) {
		List<String> list = new ArrayList<String>();
		for(int j=0; j<channel.getResDate().size(); j++) {
			String date = channel.getResDate().get(j); // Resistance
			list.add(parser.removeDash(date));
		}
		for(int j=0; j<channel.getSupDate().size(); j++) {
			String date = channel.getSupDate().get(j); // Resistance
			list.add(parser.removeDash(date));
		}
		return list;
	}
	

	private static List<Channel> getChannels() {
		return channels;
	}

	private void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
	
	

//	private void submitRfq() {
//		consoleMsg("REQ: rfq " + m_rfqId);
//
//		m_status = m_contract.underComp() != null ? Status.Rfq : Status.Ticks;
//
//		client().placeOrder(m_rfqId, m_contract, new RfqOrder(m_clientId, m_rfqId, 1));
//	}

	   
//	private void submitSecDef(int reqId, Contract contract) {
//		consoleMsg("REQ: secDef " + reqId);
//		getController().reqContractDetails(reqId, contract);
//	}


//	void onReqType() {
//	getController().reqMktDataType(MktDataType.Frozen);
//}

//    Order order = new Order();
	
//	order.m_action = "BUY";
//	 order.m_totalQuantity = 1;
//	 order.m_orderType = "LMT";
//	 order.m_lmtPrice = enteredLmtPrice;
//	m_client.placeOrder(GlobalOrderId, contract, order);
	 
//    order.m_action = "BUY";
//    order.m_totalQuantity = 100;
//    order.m_orderType = "LMT";
//    order.m_lmtPrice = enteredLmtPrice;
//    m_client.placeOrder(GlobalOrderId, contract, order);
    
    
//	
//	private void onReqType() {
//		getController().reqMktDataType( MktDataType.Realtime );
//	}
//	
//
//	protected void onDeep() {
//		getController().reqDeepMktData(contract, 6, resultPanel);
//	}

	
//	private void onDeep() {
//		getController().reqDeepMktData(contract, 6, resultPanel);
//	}
//	
//	protected void onDesub() {
//		getController().cancelDeepMktData( this);
//	}
//	private void closed() {
//		ApiDemo.INSTANCE.controller().cancelDeepMktData( this);
//	}
//	@Override public void closed() {
//		if (m_historical) {
//			ApiDemo.INSTANCE.controller().cancelHistoricalData( this);
//		}
//		else {
//			ApiDemo.INSTANCE.controller().cancelRealtimeBars( this);
//		}
//	}
//
//	private void onHistorical() {
//		getController().reqHistoricalData(contract, m_end.getText(), m_duration.getInt(), m_durationUnit.getSelectedItem(), m_barSize.getSelectedItem(), m_whatToShow.getSelectedItem(), m_rthOnly.isSelected(), panel);
//	}
//	protected void onRealTime() {
//		getController().reqRealTimeBars(contract, m_whatToShow.getSelectedItem(), m_rthOnly.isSelected(), panel);
//	}
//
//	protected void onGo() {
//		ScannerSubscription sub = new ScannerSubscription();
//		sub.numberOfRows( m_numRows.getInt() );
//		sub.scanCode( m_scanCode.getSelectedItem().toString() );
//		sub.instrument( m_instrument.getSelectedItem().toString() );
//		sub.locationCode( m_location.getText() );
//		sub.stockTypeFilter( m_stockType.getSelectedItem().toString() );
//		
//		ScannerResultsPanel resultsPanel = new ScannerResultsPanel();
//		m_resultsPanel.addTab( sub.scanCode(), resultsPanel, true, true);
//
//		getController().reqScannerSubscription( sub, resultsPanel);
//	}


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

	// PRIVATE
	
	
	// PRINT
	
	
	// TEST
	
	
	// SETTER GETTER
	public ILogger getInLogger() {
		return inLogger;
	}
	
	public void setInLogger(ILogger inLogger) {
		this.inLogger = inLogger;
	}
	
	public ILogger getOutLogger() {
		return outLogger;
	}
	
	public void setOutLogger(ILogger outLogger) {
		this.outLogger = outLogger;
	}

	public InvestorController getController() {
		return controller;
	}

	public void setController(InvestorController controller) {
		this.controller = controller;
	}

	public int getClientDefault() {
		return clientDefault;
	}

	public void setClientDefault(int clientDefault) {
		this.clientDefault = clientDefault;
	}

	public String getHostDefault() {
		return hostDefault;
	}

	public void setHostDefault(String hostDefault) {
		this.hostDefault = hostDefault;
	}

	public ArrayList<String> getAccountList() {
		return accountList;
	}

	private ContractFactory getContractFactory() {
		return contractFactory;
	}

	private void setContractFactory(ContractFactory contractFactory) {
		this.contractFactory = contractFactory;
	}
}
