package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.ConnectionStatus;
import com.onenow.constant.StreamName;
import com.onenow.constant.Topology;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentFuture;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.InvestmentStock;
import com.onenow.portfolio.BusController;
import com.onenow.portfolio.BusController.ConnectionHandler;
import com.onenow.portfolio.BusController.IBulletinHandler;
import com.onenow.portfolio.BusController.ITimeHandler;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class BusWallStInteractiveBrokers implements ConnectionHandler {

	public BusController busController = new BusController(this);
	public boolean isConnected = false;	
	private final ArrayList<String> accountList = new ArrayList<String>();

	// Default
	public NetworkService gateway = NetworkConfig.getGateway(Topology.LOCAL);
	private StreamName streamName = StreamName.STREAMING;
	
	
	public BusWallStInteractiveBrokers() {

	}

	public BusWallStInteractiveBrokers(StreamName streamName) {
		// always local for now
		this.gateway = NetworkConfig.getGateway(Topology.LOCAL);
		this.streamName = streamName;
	}

	// configurable topology 
	public BusWallStInteractiveBrokers(StreamName streamName, Topology topo) {
		
		this.streamName = streamName;
		this.gateway = NetworkConfig.getGateway(topo);
		
		
	}
	
	  /**
	   * Connect to gateway at set IP and port
	   */
	  void connectToServer() {
		 
		int clientID = getClientID();
		  
		boolean tryToConnect = true;
	    while(tryToConnect) {		    		
			try {				
				tryToConnect = false;
				String log = "CONNECTING TO BUS..." + gateway.URI + ":" + gateway.port;
				Watchr.log(Level.INFO, log, "\n", "");
			    
				busController = new BusController((com.onenow.portfolio.BusController.ConnectionHandler) this);
			    busController.connect(		gateway.URI, Integer.parseInt(gateway.port), clientID, null); 
			    
			} catch (Exception e) {
				tryToConnect = true;
				Watchr.log(Level.WARNING, "...COULD NOT CONNECT TO BUS: " + e.getMessage(), "", "\n");
				// e.printStackTrace();
				TimeParser.wait(10);
			}			
		} // end try to connect
	    
	    isConnected = true;
		Watchr.log(Level.INFO, "CONNECTED TO BUS!", "", "");
	  }

	  public Integer getClientID() {
		  
		  Integer id = 0;
		  
		  try {
			if(streamName.equals(StreamName.PRIMARY)) {
				  id = 0;
			  }
			  if(streamName.equals(StreamName.STANDBY)) {
				  id = 1;
			  }
			  if(streamName.equals(StreamName.REALTIME)) {
				  id = 2;
			  }
			  if(streamName.equals(StreamName.HISTORY)) {
				  id = 3;
			  }
			  if(streamName.equals(StreamName.STREAMING)) {
				  id = 4;
			  }
			  if(streamName.equals(StreamName.TESTING)) {
				  id = 5;
			  }
		} catch (Exception e) {
			// ignore exception upon default initialization
			// e.printStackTrace(); 
		}

		  return id;
	  }
	
	  @Override
	  public void connected() {
	    show(ConnectionStatus.CONNECTED.toString());

	    busController.reqCurrentTime( new ITimeHandler() {
	      @Override public void currentTime(long time) {
	        show( "Server date/time is " + Formats.fmtDate(time * 1000) );
	      }
	    });

	    busController.reqBulletins( true, new IBulletinHandler() {
	      @Override public void bulletin(int msgId, NewsType newsType, String message, String exchange) {
	        String str = String.format( "Received bulletin:  type=%s  exchange=%s", newsType, exchange);
	        show( str);
	        show( message);
	      }
	    });
	  }

	  @Override
	  public void disconnected() {
		  
		isConnected = false;
	    show(ConnectionStatus.DISCONNECTED.toString());
	    Watchr.log(Level.SEVERE, "disconnected() in BusWallStreetInteractiveBrokers");
	    
	  }

	  @Override
	  public void accountList(ArrayList<String> list) {
	    show( "Received account list");
	    accountList.clear();
	    accountList.addAll( list);
	  }

	  @Override
	  public void error(Exception e) {
	    show( e.toString() );
	  }

	  @Override
	  public void message(int id, int errorCode, String errorMsg) {
	    show( id + " " + errorCode + " " + errorMsg);
	    
	    // 504 not connected, 507 BAD MESSAGE LENGTH NULL
	    if(errorCode==504 || errorCode==507) {
	    	isConnected = false;
	    	// busController.disconnect();
	    }
	  }

	  @Override
	  public void show(String log) {
	  	Watchr.log(Level.INFO, log);
	  }
	  
	  // https://www.interactivebrokers.com/en/software/api/apiguide/tables/generic_tick_types.htm
		public static String getTickList(Investment inv) {
			String tickType = 	"";

			tickType = tickType + "100, "; 	//  TickType.OPTION_CALL_VOLUME, TickType.OPTION_PUT_VOLUME
			// Contains option Volume (currently for stocks)

			tickType = tickType + "101, "; 	// Contains option Open Interest (currently for stocks)

			if(!(inv instanceof InvestmentStock) && !(inv instanceof InvestmentIndex) && !(inv instanceof InvestmentFuture) && !(inv instanceof InvestmentStock)) {
				tickType = tickType + "104, ";	// Historical Volatility (currently for stocks)
			}
			
			// tickType = tickType + "105, ";  // 105(AVERAGE OPT VOLUME)
			
			tickType = tickType + "106, ";	// Option Implied Volatility (currently for stocks)

			// tickType = tickType + "107, ";  // CLIMPVLT
			
			// tickType = tickType + "125, ";  // Bond Analytic Data
			
			if(!(inv instanceof InvestmentOption) && !(inv instanceof InvestmentFuture) && !(inv instanceof InvestmentStock)) {
				tickType = tickType + "162, ";	// Index Future Premium 
			}
			
			tickType = tickType + "165, "; 	//  TickType.AVG_VOLUME
											// Contains generic stats

			// tickType = tickType + "166, ";  // CSCREEN
			
			tickType = tickType + "221, ";	// Mark Price (used in TWS P&L computations)
			
			if(!(inv instanceof InvestmentOption) && !(inv instanceof InvestmentFuture)) {
				tickType = tickType + "225, ";	// Auction values (volume, price and imbalance)
			}										// TickType.AUCTION_VOLUME
													// Contains auction values (volume, price and imbalance)
			
			// tickType = tickType + "232, "; 	// Mark Price
			
			tickType = tickType + "233, "; 	//  TickType.RT_VOLUME
													// Contains the last trade price, last trade size, last trade time, 
													// total volume, VWAP, and single trade flag.								
									
			tickType = tickType + "236, "; 	// Shortable

			// tickType = tickType + "47, ";  // Fundamentals
			
			if(!(inv instanceof InvestmentOption) && !(inv instanceof InvestmentFuture) && !(inv instanceof InvestmentStock) && !(inv instanceof InvestmentIndex)) {
				tickType = tickType + "256, "; 	// Inventory
			}
			
			tickType = tickType + "258, ";	// Fundamental Ratios
			
			tickType = tickType + "291, ";	// IVCLOSE
			
			if(!(inv instanceof InvestmentOption) && !(inv instanceof InvestmentFuture) && !(inv instanceof InvestmentStock) && !(inv instanceof InvestmentIndex)) {
				tickType = tickType + "292, ";	// Receive top news for underlying contracts from TWS for news feeds to which you have subscribed
			}
			
			// tickType = tickType + "293, "; 	// TRADECOUNT
			// tickType = tickType + "294, ";	// TRADERATE
			// tickType = tickType + "295, "; 	// VOLUMERATE
			// tickType = tickType + "318, "; 	// LASTRTHTRADE
			// tickType = tickType + "370, ";	// PARTICIPATIONMONITOR
			// tickType = tickType + "377, "; 	// CTTTICKTAG
			// tickType = tickType + "381, "; 	// IB RATE
			// tickType = tickType + "384, ";	// RFQTICKRESPTAG
			// tickType = tickType + "387, "; 	// DMM
			// tickType = tickType + "388, "; 	// ISSUER FUNDAMENTALS
			// tickType = tickType + "391, "; 	// IBWARRANTIMPVOLCOMPETETICK
			// tickType = tickType + "407, "; 	// FUTURESMARGINS
			
			tickType = tickType + "411, ";		// Real-time Historical Volatility
			
			// tickType = tickType + "428, "; 	// MONETARY CLOSE PRICE
			// tickType = tickType + "439, "; 	// MONITORTICKTAG
			 
			tickType = tickType + "456 ";	// IBDividend
			
			// tickType = tickType + "459, ";	// RTCLOSE
			// tickType = tickType + "460, "; 	// BOND FACTOR MULTIPLIER
			// tickType = tickType + "499, ";	// FEE AND REBATE RATE
			// tickType = tickType + "506, "; 	// MIDPTIV
			// tickType = tickType + "511, "; 	// HVOLRT10 (PER-UNDERLYING)
			// tickType = tickType + "512, ";	// HVOLRT30 (PER-UNDERLYING)
			// tickType = tickType + "513, "; 	// HVOLRT50 (PER-UNDERLYING)
			// tickType = tickType + "514, ";	// HVOLRT75 (PER-UNDERLYING)
			// tickType = tickType + "515, "; 	// HVOLRT100 (PER-UNDERLYING)
			// tickType = tickType + "516, ";	// HVOLRT150 (PER-UNDERLYING)
			// tickType = tickType + "517, "; 	// HVOLRT200 (PER-UNDERLYING)
			// tickType = tickType + "545";	// VSIV
			
			return tickType;
		} 
  
}
