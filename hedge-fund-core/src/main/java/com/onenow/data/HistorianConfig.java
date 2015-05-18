package com.onenow.data;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;

public class HistorianConfig {

	public InvDataSource source;
	public InvDataTiming timing;
	
	public SamplingRate sampling;
	public TradeType tradeType; 

	public int durations;
	public DurationUnit durationUnit;
	public BarSize barSize;
	public WhatToShow whatToShow;
	
	public HistorianConfig() {
		
	}

	public HistorianConfig(	InvDataSource source, InvDataTiming timing,
							int durations, DurationUnit durationUnit, BarSize barSize, WhatToShow whatToShow,
							TradeType tradeType, SamplingRate sampling) {
		
		this.source = source;
		this.timing = timing;
		
		this.tradeType = tradeType;
		this.sampling = sampling;
		
		this.durations = durations;
		this.durationUnit = durationUnit;
		this.barSize = barSize;
		this.whatToShow = whatToShow;
	}
	
}
