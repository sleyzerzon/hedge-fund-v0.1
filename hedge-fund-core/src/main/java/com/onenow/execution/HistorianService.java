package com.onenow.execution;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;
import com.onenow.data.EventActivity;
import com.onenow.data.HistorianConfig;
import com.sun.jersey.core.header.reader.HttpHeaderReader.Event;

public class HistorianService {

	
	public HistorianService() {
		
	}
	
	public static HistorianConfig getConfig(InvDataSource source, BarSize size, WhatToShow whatToShow) {
		
		
		HistorianConfig config = new HistorianConfig(	source, InvDataTiming.HISTORICAL,
														1, DurationUnit.DAY, size, whatToShow,
														SamplingRate.HFMEDIUM); 
		
		return config;
		
	}
}
