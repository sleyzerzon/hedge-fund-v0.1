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

	private Long time = new Long("1424288913903");
	private Investment inv = new Investment();
	private TradeType tradeType = TradeType.TRADED; 

	private Double price = 2011.0;
	private Integer size = 5;
	
	private InvDataSource source = InvDataSource.IB;
	private InvDataTiming timing = InvDataTiming.REALTIME;

}
