package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.SamplingRate;

public class Sampling {

	
	
	public Sampling() {
		
		
	}
	
	
	// list.add(parser.removeDash(date).concat(" 16:30:00"));
	
	
	public String getGroupByTimeString(SamplingRate sampling) {
		String dbSamplingRate="";
		if(sampling.equals("SCALPSHORT")) {		//SCALPING 5min, 15min, 60min
			return "5m";
		}
		if(sampling.equals("SCALPMEDIUM")) {
			return "15m";
		}
		if(sampling.equals("SCALPLONG")) {
			return "60m";
		}
		if(sampling.equals("SWINGSHORT")) {		//SWINGING 60min, 240min, daily
			return "60m";
		}
		if(sampling.equals("SWINGMEDIUM")) {
			return "4h";
		}
		if(sampling.equals("SWINGLONG")) {
			return "1d";
		}
		if(sampling.equals("TRENDSHORT")) {		//TREND 4hr, daily, weekly
			return "4h";
		}
		if(sampling.equals("TRENDMEDIUM")) {
			return "1d";
		}
		if(sampling.equals("TRENDLONG")) {
			return "1w";
		}
		return dbSamplingRate;
	}
	
	
	// call multiple times to get all
	public List<SamplingRate> getList(SamplingRate rate) { 
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		if(rate.equals(SamplingRate.SCALP)) {
			list.addAll(getScalpSampling());
		}
		if(rate.equals(SamplingRate.SWING)) {
			list.addAll(getSwingSampling());
		}
		if(rate.equals(SamplingRate.TREND)) {
			list.addAll(getTrendSampling());
		}
		return list;
	}
	
	public List<SamplingRate> getTradingOptions() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.SCALP);
		list.add(SamplingRate.SWING);
		list.add(SamplingRate.TREND);
		return list;
	}
	private List<SamplingRate> getScalpSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.SCALPSHORT);
		list.add(SamplingRate.SCALPMEDIUM);
		list.add(SamplingRate.SCALPLONG);					
		return list;
	}
	private List<SamplingRate> getSwingSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.SWINGSHORT);
		list.add(SamplingRate.SWINGMEDIUM);
		list.add(SamplingRate.SWINGLONG);								
		return list;
	}
	private List<SamplingRate> getTrendSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.TRENDSHORT);
		list.add(SamplingRate.TRENDMEDIUM);
		list.add(SamplingRate.TRENDLONG);							
		return list;
	}

	
}
