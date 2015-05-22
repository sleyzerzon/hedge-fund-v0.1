package com.onenow.execution;

import java.util.ArrayList;

import com.ib.client.Types.NewsType;
import com.ib.controller.Formats;
import com.ib.controller.ApiConnection.ILogger;
import com.onenow.constant.ConnectionStatus;
import com.onenow.portfolio.BrokerController;
import com.onenow.portfolio.BrokerController.ConnectionHandler;
import com.onenow.portfolio.BrokerController.IBulletinHandler;
import com.onenow.portfolio.BrokerController.ITimeHandler;
import com.onenow.util.LogType;
import com.onenow.util.ParseTime;
import com.onenow.util.WatchLog;

public class BusWallSt implements ConnectionHandler {

	private ILogger inLogger = new SummitLogger();
	private ILogger outLogger = new SummitLogger();

	public BrokerController controller = new BrokerController(this, inLogger, outLogger);
	private final ArrayList<String> accountList = new ArrayList<String>();

	private NetworkService gwService;
	
	public BusWallSt() {
		
	}
	
	public BusWallSt(NetworkService service) {
		this.gwService = service;
	}

	  /**
	   * Connect to gateway at set IP and port
	   */
	  void connectToServer() {
		boolean tryToConnect = true;
	    while(tryToConnect) {		    		
			try {				
				tryToConnect = false;
				String log = "CONNECTING TO BUS..." + gwService.URI + ":" + gwService.port;
				WatchLog.add(LogType.INFO, log, "\n", "");
			    controller = new BrokerController((com.onenow.portfolio.BrokerController.ConnectionHandler) this, inLogger, outLogger);
			    controller.connect(	gwService.URI, gwService.port, 
			    							0, null);  
			} catch (Exception e) {
				tryToConnect = true;
				System.out.println("...COULD CONNECT TO BUS..." + "\n");
				e.printStackTrace();
				ParseTime.wait(10);
			}			
		} // end try to connect
		System.out.println("CONNECTED TO BUS!");
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
  public void show(String string) {
    System.out.println(string);
  }

  
  
}
