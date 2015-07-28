package com.onenow.data;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.DataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;

public class HistorianConfig {

	public InvDataSource source;
	public DataTiming timing;
	
	public SamplingRate sampling;

	public Integer durations;
	public DurationUnit durationUnit;
	public BarSize barSize;
	public WhatToShow whatToShow;
	
	public HistorianConfig() {
		
	}

	public HistorianConfig(	InvDataSource source, DataTiming timing,
							int durations, DurationUnit durationUnit, BarSize barSize, WhatToShow whatToShow,
							SamplingRate sampling) {

		this.source = source;
		this.timing = timing;
		
		this.sampling = sampling;
		
		this.durations = durations;
		this.durationUnit = durationUnit;
		this.barSize = barSize;
		this.whatToShow = whatToShow;
	}
	
	public String toString() {
		String s = "";
		s = 	"-source " + source + " " +
				"-timing " + timing + " " +
				"-sampling " + sampling + " " +
				"-durations " + durations.toString() + " " +
				"-durationUnit " + durationUnit + " " +
				"-barSize " + barSize + " " +
				"-whatToShow " + whatToShow;
		
		return s;
	}
	
}
