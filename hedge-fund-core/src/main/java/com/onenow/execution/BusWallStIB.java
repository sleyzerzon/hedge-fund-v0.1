package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.constant.ConnectionStatus;
import com.onenow.constant.StreamName;
import com.onenow.constant.Topology;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.ConnectionHandler;
import com.onenow.portfolio.BrokerController.IBulletinHandler;
import com.onenow.portfolio.BrokerController.ITimeHandler;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class BusWallStIB implements ConnectionHandler {

	public BrokerController controller = new BrokerController(this);
	
	private final ArrayList<String> accountList = new ArrayList<String>();

	public NetworkService gateway;
	
	private StreamName streamName;
	
	
	public BusWallStIB() {
		// always local for now
		this.gateway = NetworkConfig.getGateway(Topology.LOCAL);
		this.streamName = StreamName.STREAMING;
	}
	
	// configurable topology for testing
	public BusWallStIB(StreamName mode, Topology topo) {
		
		this.streamName = mode;
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
			    controller = new BrokerController((com.onenow.portfolio.BrokerController.ConnectionHandler) this);

			    // controller.disconnect(); 
			    controller.connect(		gateway.URI, Integer.parseInt(gateway.port), 
			    						clientID, null);  
			} catch (Exception e) {
				tryToConnect = true;
				Watchr.log(Level.SEVERE, "...COULD NOT CONNECT TO BUS: " + e.getMessage(), "", "\n");
				e.printStackTrace();
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
