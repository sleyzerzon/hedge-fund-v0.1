package com.onenow.io;

import java.util.logging.Level;

import org.testng.annotations.Test;

import com.onenow.data.EventRealTime;
import com.onenow.util.Watchr;

public class KinesisTest {
	private BrokerBusRealtime 					brokerBusRealtime = new BrokerBusRealtime();

  @Test
  public void sendObject() {

	  	EventRealTime event = null;
	  
		// Write to Real-Time datastream
		Watchr.log(Level.INFO, "PriceSizeCache WRITE " + event.toString());
		brokerBusRealtime.write(event);			

  }
}
