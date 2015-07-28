package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.ConnectionStatus;
import com.onenow.constant.InvType;
import com.onenow.constant.InvestorRole;
import com.onenow.constant.StreamName;
import com.onenow.constant.Topology;
import com.onenow.data.QuoteHistoryInvestment;
import com.onenow.data.QuoteSharedHandler;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvFuture;
import com.onenow.instrument.InvIndex;
import com.onenow.instrument.InvOption;
import com.onenow.instrument.InvStock;
import com.onenow.instrument.Underlying;
import com.onenow.io.BusSystem;
import com.onenow.portfolio.BusController;
import com.onenow.portfolio.BusController.ConnectionHandler;
import com.onenow.portfolio.BusController.IBulletinHandler;
import com.onenow.portfolio.BusController.IHistoricalDataHandler;
import com.onenow.portfolio.BusController.ITimeHandler;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class BusWallStInteractiveBrokers implements ConnectionHandler {

	public BusController busController = new BusController(this);
	public boolean isConnectionBroken = true;	
	public boolean isFarmAvailable = false;
	private final ArrayList<String> accountList = new ArrayList<String>();

	// Default
	public NetworkService gateway = NetworkConfig.getGateway(Topology.LOCAL);
	private InvestorRole investorRole = InvestorRole.REALTIME;
	
	
	public BusWallStInteractiveBrokers() {

	}

	public BusWallStInteractiveBrokers(InvestorRole investorRole) {
		// always local for now
		this.gateway = NetworkConfig.getGateway(Topology.LOCAL);
		this.investorRole = investorRole;
	}

	// configurable topology 
	public BusWallStInteractiveBrokers(InvestorRole investorRole, Topology topo) {
		
		this.investorRole = investorRole;
		this.gateway = NetworkConfig.getGateway(topo);
		
		
	}
	
	  /**
	   * Connect to gateway at set IP and port
	   */
	  void connectToServer() {
		 
		int clientID = getClientID();
		  
		boolean tryingToConnect = true;
	    while(tryingToConnect) {		    		
			try {				
				tryingToConnect = false;
				String log = "CONNECTING TO BUS..." + gateway.URI + ":" + gateway.port;
				Watchr.log(Level.INFO, log, "\n", "");
			    
				busController = new BusController((com.onenow.portfolio.BusController.ConnectionHandler) this);
			    busController.connect(		gateway.URI, Integer.parseInt(gateway.port), clientID, null); 
			    
			} catch (Exception e) {
				isConnectionBroken = true;
				tryingToConnect = true;
				Watchr.log(Level.WARNING, "...COULD NOT CONNECT TO BUS: ");
				e.printStackTrace();
				TimeParser.sleep(30);
			}			
		} // end try to connect
	    
	    isConnectionBroken = false;
		Watchr.log(Level.INFO, "CONNECTED TO BUS!", "", "");
	  }

	  public Integer getClientID() {
		  
		  Integer id = 0;
		  
		  try {
			  if(investorRole.equals(InvestorRole.PRIMARY)) {
				  id = 0;
			  }
			  if(investorRole.equals(InvestorRole.STANDBY)) {
				  id = 1;
			  }
			  if(investorRole.equals(InvestorRole.REALTIME)) {
				  id = 2;
			  }
			  if(investorRole.equals(InvestorRole.HISTORY)) {
				  id = 3;
			  }
		} catch (Exception e) {
			// ignore exception upon default initialization
			// e.printStackTrace(); 
		}

		  return id;
	  }
	
	  @Override
	  public void connected() {
		isConnectionBroken = false;
	    show(ConnectionStatus.CONNECTED.toString());

	    busController.reqCurrentTime( new ITimeHandler() {
	      @Override public void currentTime(long time) {
	        show( "Server date/time is " + Formats.fmtDate(time * 1000) + " OR " + TimeParser.getFormatedPacificDateTime(time*1000));
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
		isConnectionBroken = true;
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

		private String getMessageContext(int id) {
			
			if(id==-1) {
				// sometimes the id does not correspond to a handler, i.e. -1 to generically signify error
				return "-1";
			}
			
			String context = "";
			Investment inv = new Investment();

			try {
				QuoteSharedHandler rtHandler = busController.m_topMktDataMap.get(id);
				inv = rtHandler.investment;
			} catch (Exception e1) {
				try {
					QuoteHistoryInvestment histHandler = busController.m_historicalDataMap.get(id);
					inv = histHandler.investment;
				} catch (Exception e2){
					Watchr.log(Level.WARNING, "Could not find query investment for reqId: " + id);
				}
			}

			context = context + " " + ContractFactory.getContract(inv).toString();

			try {
				String detail = busController.reqDetail.get(id);
				if(detail!=null) {
				context = context + " " + detail;
				}
			} catch (Exception e3) {
				Watchr.log(Level.WARNING, "Could not fill query context for reqId: " + id);			
			}
							
			return context;
		}
		
	  /** 
	   * Make decisions based on messaging from the API counterpart
	   */
	    // TODO: look for IBmessage and IBerror in the log
	  @Override
	  public void message(int id, int code, String message) {
		
		boolean warning = false;
		boolean severe = false;
		String summary = "";
				
	    if( isConnectionError(code)) {	    	
	    	summary = "Connection Error: ";
			severe = true;
	    }
	    
	    // TODO: re-try request when data error occurs
	    if( isMarketDataError(code) || isFarmUnavailable(code)) {
	    	summary = "Data Warning: ";
	    	warning = true;
	    }
	    	 
	    if( isGeneralMessage(code) ) {
	    	summary = "General Message: ";
	    	warning = true;
	    }
	    
		logCategorizedMessage(id, code, message, warning, severe, summary);
	    	    
	  }

	private void logCategorizedMessage(int id, int code, String message,
			boolean warning, boolean severe, String summary) {
		// 10000021 162 HISTORICAL MARKET DATA SERVICE ERROR MESSAGE:HMDS QUERY RETURNED NO DATA: EWM5 C2105@GLOBEX TRADES
	    String log = "-id " + id + " -code " + code + " -message " + message + " " + getMessageContext(id);
		
	    if(!severe && !warning) {
	    	Watchr.log(Level.INFO, log);
	    }
	    else {
	    	if(severe) {
	    		Watchr.log(Level.SEVERE, summary + log);
	    	} 
	    	if(warning) {
	    		Watchr.log(Level.WARNING, summary + log);
	    	}
	    }
	}

	    // TODO: 2100, 2101, 2102, 2109, the whole 10000 series, most of the 501 series, as well as 1/2/3/4 series
	  private boolean isGeneralMessage(int code) {
		  boolean general = false;
		  
		  if(     !isConnectionError(code) &&
				  !isMarketDataError(code) &&
				  !isFarmAvailable(code) &&
				  !isFarmUnavailable(code)
				  ) {
			  
	    	  isConnectionBroken = false;
			  general = true;
		  }
		  return general;
	  }
	  

	  // -id 10000132 -code 101 -message Max number of tickers has been reached -conid 0 -symbol ES -secType FOP -expiry 20150807 -strike 2095.0 -right CALL -multiplier 50 -exchange GLOBEX -currency USD null
	  private boolean isConnectionError(int errorCode) {
		
		  if(   errorCode==502 ||			// -message Couldn't connect to TWS... or their server is down
				errorCode==504 || 			// not connected
				errorCode==507  	 		// null message
				  				) { 
			  
	    	  isConnectionBroken = true;
			  return true;
		  }
		  return false;
	  }
	  
	  private boolean isMarketDataError(int messageCode) {
		  
		  if( 	messageCode==162 || 						// Historical market data Service error message
				messageCode==164 || 						// There is no market data to check price percent violations
				messageCode==165 ||   	  					// Historical market Data Service query message
				messageCode==354 || 						// Not subscribed to requested market data
				messageCode==414 || 						// Snapshot market data subscription is not applicable to generic ticks
				messageCode==510 || 						// Request market data - sending error
				messageCode==511 || 						// Cancel market data - sending error
				messageCode==1101 ||						// Connectivity between IB and TWS has been restored- data lost
				messageCode==1300							// TWS socket port has been reset and this connection is being dropped
				) {

	    	  isConnectionBroken = false;
			  return true;
		  }
		  return false;
	  }
	  
	  private boolean isFarmAvailable(int messageCode) {
		  		  
		  if(   messageCode==1102 || 			// Connectivity between IB and TWS has been restored- data maintained
				messageCode==2104 || 			// market data farm connected
				messageCode==2108 || 			// market data inactive but should be available upon demand
				messageCode==2106 ||			// historic data farm connected (HMDS)
				messageCode==2107 				// historic data inactive but should be available upon demand
				  				) {			

			  isFarmAvailable = true;
			  isConnectionBroken = false; // TODO: in what other situations do we know it's not broken/anymore

			  Watchr.log(Level.WARNING, "Farm Available: " + messageCode);
		  }
		  
		  return isFarmAvailable;
	  }
		  

	  private boolean isFarmUnavailable(int messageCode) {
		  
		  if(   messageCode==322 || 			// Only 50 simultaneous API historical data requests allowed
				messageCode==1100 ||			// Connectivity between IB and TWS has been lost
				messageCode==2103 || 			// A market data farm is disconnected
				messageCode==2105 || 			// A historical data farm is disconnected
				messageCode==2119 || 			// market data farm ...?
				messageCode==2110) {			// Connectivity between TWS and server is broken 

			  isFarmAvailable = false;
			  isConnectionBroken = false; // TODO: in what other situations do we know it's not broken/anymore
			  
			  Watchr.log(Level.WARNING, "Farm Unavailable: " + messageCode);
			  
		  }

		  return !isFarmAvailable;
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

			if(!(inv instanceof InvStock) && !(inv instanceof InvIndex) && !(inv instanceof InvFuture) && !(inv instanceof InvStock)) {
				tickType = tickType + "104, ";	// Historical Volatility (currently for stocks)
			}
			
			// tickType = tickType + "105, ";  // 105(AVERAGE OPT VOLUME)
			
			tickType = tickType + "106, ";	// Option Implied Volatility (currently for stocks)

			// tickType = tickType + "107, ";  // CLIMPVLT
			
			// tickType = tickType + "125, ";  // Bond Analytic Data
			
			if(!(inv instanceof InvOption) && !(inv instanceof InvFuture) && !(inv instanceof InvStock)) {
				tickType = tickType + "162, ";	// Index Future Premium 
			}
			
			tickType = tickType + "165, "; 	//  TickType.AVG_VOLUME
											// Contains generic stats

			// tickType = tickType + "166, ";  // CSCREEN
			
			tickType = tickType + "221, ";	// Mark Price (used in TWS P&L computations)
			
			if(!(inv instanceof InvOption) && !(inv instanceof InvFuture)) {
				tickType = tickType + "225, ";	// Auction values (volume, price and imbalance)
			}										// TickType.AUCTION_VOLUME
													// Contains auction values (volume, price and imbalance)
			
			// tickType = tickType + "232, "; 	// Mark Price
			
			tickType = tickType + "233, "; 	//  TickType.RT_VOLUME
													// Contains the last trade price, last trade size, last trade time, 
													// total volume, VWAP, and single trade flag.								
									
			tickType = tickType + "236, "; 	// Shortable

			// tickType = tickType + "47, ";  // Fundamentals
			
			if(!(inv instanceof InvOption) && !(inv instanceof InvFuture) && !(inv instanceof InvStock) && !(inv instanceof InvIndex)) {
				tickType = tickType + "256, "; 	// Inventory
			}
			
			tickType = tickType + "258, ";	// Fundamental Ratios
			
			tickType = tickType + "291, ";	// IVCLOSE
			
			if(!(inv instanceof InvOption) && !(inv instanceof InvFuture) && !(inv instanceof InvStock) && !(inv instanceof InvIndex)) {
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
