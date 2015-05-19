package com.onenow.main;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.HistorianConfig;

public class HistorianService {

    public HistorianConfig size30sec = new HistorianConfig(	InvDataSource.IB, InvDataTiming.HISTORICAL,
															1, DurationUnit.DAY, BarSize._30_secs, WhatToShow.TRADES,
															TradeType.TRADED, SamplingRate.HFMEDIUM);   	    	

    public HistorianConfig size1hr = new HistorianConfig(	InvDataSource.IB, InvDataTiming.HISTORICAL,
															1, DurationUnit.DAY, BarSize._1_hour, WhatToShow.TRADES,
															TradeType.TRADED, SamplingRate.HFMEDIUM);   	    	

	public HistorianService() {
		
	}
}
