package com.onenow.data;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;

public class HistorianConfig {

	public InvDataSource source;
	public InvDataTiming timing;
	public SamplingRate sampling;
	public TradeType tradeType; 

	public HistorianConfig() {
		
	}

	public HistorianConfig(	InvDataSource source, InvDataTiming timing,
							TradeType tradeType, SamplingRate sampling) {
		
		this.source = source;
		this.timing = timing;
		this.tradeType = tradeType;
		this.sampling = sampling;
	}

	
}
