package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;
import com.onenow.alpha.Broker;
import com.onenow.constant.BrokerMode;
import com.onenow.constant.ConnectionStatus;
import com.onenow.constant.Topology;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.ConnectionHandler;
import com.onenow.portfolio.BrokerController.IBulletinHandler;
import com.onenow.portfolio.BrokerController.ITimeHandler;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class BusWallSt implements ConnectionHandler {

//	private ILogger inLogger = new SummitLogger();
//	private ILogger outLogger = new SummitLogger();

//	public BrokerController controller = new BrokerController(this, inLogger, outLogger);
	public BrokerController controller = new BrokerController(this);
	
	private final ArrayList<String> accountList = new ArrayList<String>();

	public NetworkService gateway;
	
	private BrokerMode mode;
	
	
	public BusWallSt() {
		// always local for now
		this.gateway = NetworkConfig.getGateway(Topology.LOCAL);
		this.mode = BrokerMode.STREAMING;
	}
	
	// configurable topology for testing
	public BusWallSt(BrokerMode mode, Topology topo) {
		
		this.mode = mode;
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
//			    controller = new BrokerController((com.onenow.portfolio.BrokerController.ConnectionHandler) this, 
//			    									inLogger, outLogger);
			    controller = new BrokerController((com.onenow.portfolio.BrokerController.ConnectionHandler) this);

			    // controller.disconnect(); 
			    controller.connect(		gateway.URI, Integer.parseInt(gateway.port), 
			    						clientID, null);  
			} catch (Exception e) {
				tryToConnect = true;
				String log = "...COULD NOT CONNECT TO BUS";
				Watchr.log(Level.INFO, log, "", "\n");
				e.printStackTrace();
				TimeParser.wait(10);
			}			
		} // end try to connect
		String log = "CONNECTED TO BUS!";
		Watchr.log(Level.INFO, log, "", "");
	  }

	  private Integer getClientID() {
		  
		  Integer id = 0;
		  
		  if(mode.equals(BrokerMode.PRIMARY)) {
			  id = 0;
		  }
		  if(mode.equals(BrokerMode.STANDBY)) {
			  id = 1;
		  }
		  if(mode.equals(BrokerMode.REALTIME)) {
			  id = 2;
		  }
		  if(mode.equals(BrokerMode.HISTORIAN)) {
			  id = 3;
		  }
		  if(mode.equals(BrokerMode.STREAMING)) {
			  id = 4;
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
