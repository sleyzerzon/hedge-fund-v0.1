package com.onenow.execution;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.EventActivity;
import com.onenow.data.HistorianConfig;
import com.sun.jersey.core.header.reader.HttpHeaderReader.Event;

public class HistorianService {

    public static HistorianConfig size30sec = new HistorianConfig(	InvDataSource.IB, InvDataTiming.HISTORICAL,
																	1, DurationUnit.DAY, BarSize._30_secs, WhatToShow.TRADES,
																	SamplingRate.HFMEDIUM);   	    	

    public static HistorianConfig size1hr = new HistorianConfig(	InvDataSource.IB, InvDataTiming.HISTORICAL,
																	1, DurationUnit.DAY, BarSize._1_hour, WhatToShow.TRADES,
																	SamplingRate.HFMEDIUM);   	    	

	public HistorianService() {
		
	}
	
	public static HistorianConfig getConfig(BarSize size, EventActivity event) {
		
		
		HistorianConfig config = new HistorianConfig(	event.source, InvDataTiming.HISTORICAL,
														1, DurationUnit.DAY, size, WhatToShow.TRADES,
														SamplingRate.HFMEDIUM); 
		
		return config;
		
	}
}
