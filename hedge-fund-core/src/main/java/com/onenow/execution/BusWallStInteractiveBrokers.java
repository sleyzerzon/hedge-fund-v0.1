package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.ConnectionStatus;
import com.onenow.constant.StreamName;
import com.onenow.constant.Topology;
import com.onenow.portfolio.BusController;
import com.onenow.portfolio.BusController.ConnectionHandler;
import com.onenow.portfolio.BusController.IBulletinHandler;
import com.onenow.portfolio.BusController.ITimeHandler;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class BusWallStInteractiveBrokers implements ConnectionHandler {

	public BusController controller = new BusController(this);
	
	private final ArrayList<String> accountList = new ArrayList<String>();

	public NetworkService gateway;
	
	private StreamName streamName;
	
	
	public BusWallStInteractiveBrokers() {
		// always local for now
		this.gateway = NetworkConfig.getGateway(Topology.LOCAL);
		this.streamName = StreamName.STREAMING;
	}

	public BusWallStInteractiveBrokers(StreamName streamName) {
		// always local for now
		this.gateway = NetworkConfig.getGateway(Topology.LOCAL);
		this.streamName = streamName;
	}

	// configurable topology for testing
	public BusWallStInteractiveBrokers(StreamName streamName, Topology topo) {
		
		this.streamName = streamName;
		this.gateway = NetworkConfig.getGateway(topo);
		
		// fixed gateway for production
//		if(!NetworkConfig.isMac()) {
//			this.gateway = NetworkConfig.getGateway(Topology.AWSLOCAL);
//		}
		
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
			    controller = new BusController((com.onenow.portfolio.BusController.ConnectionHandler) this);

			    // controller.disconnect(); 
			    controller.connect(		gateway.URI, Integer.parseInt(gateway.port), 
			    						clientID, null);  
			} catch (Exception e) {
				tryToConnect = true;
				Watchr.log(Level.WARNING, "...COULD NOT CONNECT TO BUS: " + e.getMessage(), "", "\n");
				// e.printStackTrace();
				TimeParser.wait(10);
			}			
		} // end try to connect
		Watchr.log(Level.INFO, "CONNECTED TO BUS!", "", "");
	  }

	  public Integer getClientID() {
		  
		  Integer id = null;
		  
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

		  return id;
	  }
	
		public static String getTickList() {
			String volumeTicks = 	"";

			volumeTicks = volumeTicks + "100, "; 	//  TickType.OPTION_CALL_VOLUME, TickType.OPTION_PUT_VOLUME
			// Contains option Volume (currently for stocks)

			volumeTicks = volumeTicks + "101, "; 	// Contains option Open Interest (currently for stocks)

			volumeTicks = volumeTicks + "104, ";	// Historical Volatility (currently for stocks)
			
			volumeTicks = volumeTicks + "106, ";	// Option Implied Volatility (currently for stocks)

			volumeTicks = volumeTicks + "165, "; 	//  TickType.AVG_VOLUME
													// Contains generic stats

			volumeTicks = volumeTicks + "225, ";	// Auction values (volume, price and imbalance)
													// TickType.AUCTION_VOLUME
													// Contains auction values (volume, price and imbalance)
			
			volumeTicks = volumeTicks + "233, "; 	//  TickType.RT_VOLUME
													// Contains the last trade price, last trade size, last trade time, 
													// total volume, VWAP, and single trade flag.								
									
			volumeTicks = volumeTicks + "411";		// Real-time Historical Volatility
			
									// ? TickType.VOLUME_RATE.toString();
									// ? TickType.OPEN_INTEREST -> 22
									// ? TickType.VOLUME -> 8
			return volumeTicks;
		} 
		
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
  }

  @Override
  public void show(String log) {
  	Watchr.log(Level.INFO, log);
  }

  
  
}
