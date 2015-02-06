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

	// LOOK FOR FREEK-OUT IN LAST 30 DAYS, SET CHANNEL
	// HAVE 14 DAYS IN CASE OVER-BUYING/SELLING CONTINUES
	// measured at time that run-up + sell-off end (Warn level); before OVER-REACTION begins
	// buy AFTER over-sold ends, sell after over-buying ends (Act level)
	private static List<Channel> channels = new ArrayList<Channel>();
	
	public void run() {

		setController(new InvestorController((com.onenow.investor.InvestorController.ConnectionHandler) this, getInLogger(), getOutLogger()));
		
		getController().connect("127.0.0.1", 4001, 0, null);  // app port 7496	
		
		QuoteModel qModel = new QuoteModel(getController());
		qModel.addContract(contractToQuote());
				
		setChannels();

		QuoteHistoryModel qHistory = new QuoteHistoryModel(getController(), getChannels());
		qHistory.addContract(	contractToQuote(), "20111231 16:30:00", 1, DurationUnit.DAY, 
								BarSize._30_secs, WhatToShow.MIDPOINT,
								false);
		
		// IRealTimeBarHandler
	}
	

	
	private Contract contractToQuote() {	
		int p_conId=0;
		String p_symbol="IBM";
		String p_secType=SecType.STK.toString();	// "OPT"
		
		String p_expiry="";		// "20120316"
		double p_strike=0.0;	// 20.0
		String p_right=""; 	// "P" ... "Put/call"
		
		String p_multiplier="100";
		String p_exchange="SMART";		// or "BEST"; "Comp Exchange"???
		String p_currency="USD";
		String p_localSymbol="";
		String p_tradingClass="";
		ArrayList<ComboLeg> p_comboLegs=new ArrayList<ComboLeg>();
		String p_primaryExch="";
		boolean p_includeExpired=false;
		String p_secIdType="";
		String p_secId="";
		
		Contract cont = new Contract();
		cont = new Contract(p_conId, p_symbol, p_secType, p_expiry,
                    p_strike, p_right, p_multiplier,
                    p_exchange, p_currency, p_localSymbol, p_tradingClass,
                    p_comboLegs, p_primaryExch, p_includeExpired,
                    p_secIdType, p_secId);
		return cont;
	}
	
	
	private static void setChannels() {
		Channel spx = new Channel(new Underlying("SPX"));
//		Channel rut = new Channel(new Underlying("RUT"));
//		Channel ndx = new Channel(new Underlying("NDX"));

		getChannels().add(spx);
//		getChannels().add(spx);
//		getChannels().add(spx);
		
		// SPX
		spx.addResistance("20150205"); 
		spx.addSupport("20150202");
		spx.addSupport("20150129");
		spx.addResistance("20150122");
		spx.addSupport("20150114");
		spx.addResistance("20150108");
		// *** 30-day trend change
		spx.addResistance("20141229");  
		spx.addSupport("20141216"); // fundamentals t2 low 
		spx.addResistance("20141205"); 
		// November: mild market 
		spx.addResistance("20141015"); // CRASH
		spx.addResistance("20140905"); 
		spx.addSupport("20140807"); // fundamentals t1 low

		
//		spx.addResistance(sdf.parse("20150205 14:30:00"), 2063.0); // ET
//		spx.addSupport(sdf.parse("20150202 09:05:00"), 1983.0);
//		spx.addSupport(sdf.parse("20150129 12:00:00"), 1990.0);
//		spx.addResistance(sdf.parse("20150122 14:00:00"), 2063.0);
//		spx.addSupport(sdf.parse("20150114 12:00:00"), 1992.0);
//		// spx.addResistance(sdf.parse("20150113 09:00:00"), 2055.0); // faux
//		spx.addResistance(sdf.parse("20150108 14:00:00"), 2064.0);
////		spx.addSupport(sdf.parse("20150106 16:30:00"), 2002.0); 
//		spx.addResistance(sdf.parse("20141229 16:00:00"), 2093.0); // old high
//		spx.addSupport(sdf.parse("20141216 09:05:00"), 1970.0); // old low
//		spx.addResistance(sdf.parse("20141205 16:30:00"), 2076.0); // ET
//		// November: fundamentals slow
//		// October: market goes down
//		// September births the top, sets the fundamental
//		spx.addResistance(sdf.parse("20140905 16:30:00"), 2008.0); // old high
//		// August births the bottom, sets the fundamental
//		spx.addSupport(sdf.parse("20140807 09:05:00"), 1906.0); // old low
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
}
