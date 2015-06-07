package com.onenow.io;

import org.testng.annotations.Test;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.data.EventRealTime;
import com.onenow.instrument.Investment;

public class KinesisTest {

	IRecordProcessorFactory testingProcessorFactory = BusProcessingFactory.processorFactoryString();

	private Long time = new Long("1424288913903");
	private Investment inv = new Investment();
	private TradeType tradeType = TradeType.TRADED; 

	private Double price = 2011.0;
	private Integer size = 5;
	
	private InvDataSource source = InvDataSource.IB;
	private InvDataTiming timing = InvDataTiming.REALTIME;


  @Test
  public void sendObject() {
	  
	  	final String eventString = "Hola PA!";
	  
	  	// RT_VOLUME 0.60;1;1424288913903;551;0.78662433;true
	  	EventRealTime eventRT = new EventRealTime(	time, inv, tradeType,
	  												price, size,
	  												source, timing);

		new Thread () {
			@Override public void run () {
				writeRepeatedly(eventString);
			}
		}.start();

		readRepeatedly(); 

  }
  
	  private void writeRepeatedly(String eventString) {
		  	while(true) {	
				BusSystem.write(StreamName.TESTING, eventString);					
		  	}
	  }
	  
	  private void readRepeatedly() {
		  	while(true) {	
				BusSystem.read(StreamName.TESTING, testingProcessorFactory);
				
				// validate read=write via ElastiCache
		  	}
	  }

}
