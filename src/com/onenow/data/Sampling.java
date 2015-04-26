package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.SamplingRate;

public class Sampling {

	
	
	public Sampling() {
		
		
	}
	
	
	// list.add(parser.removeDash(date).concat(" 16:30:00"));
	
	
	public String getGroupByTimeString(String samplingRate) {
		String dbSamplingRate="";
		if(samplingRate.equals("SCALPSHORT")) {		//SCALPING 5min, 15min, 60min
			return "5m";
		}
		if(samplingRate.equals("SCALPMEDIUM")) {
			return "15m";
		}
		if(samplingRate.equals("SCALPLONG")) {
			return "60m";
		}
		if(samplingRate.equals("SWINGSHORT")) {		//SWINGING 60min, 240min, daily
			return "60m";
		}
		if(samplingRate.equals("SWINGMEDIUM")) {
			return "4h";
		}
		if(samplingRate.equals("SWINGLONG")) {
			return "1d";
		}
		if(samplingRate.equals("TRENDSHORT")) {		//TREND 4hr, daily, weekly
			return "4h";
		}
		if(samplingRate.equals("TRENDMEDIUM")) {
			return "1d";
		}
		if(samplingRate.equals("TRENDLONG")) {
			return "1w";
		}
		return dbSamplingRate;
	}
	
	
	public List<String> getSamplingList(String rate) {
		List<String> list = new ArrayList<String>();
		if(rate.equals("default") || rate.equals("")) {
			list.addAll(getDefaultSampling());
		}
		if(rate.equals(SamplingRate.SCALP.toString()) || rate.equals("all")) {
			list.addAll(getScalpSampling());
		}
		if(rate.equals(SamplingRate.SWING.toString()) || rate.equals("all")) {
			list.addAll(getSwingSampling());
		}
		if(rate.equals(SamplingRate.TREND.toString()) || rate.equals("all")) {
			list.addAll(getTrendSampling());
		}
		return list;
	}
	
	public List<String> getTradingOptions() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.SCALP.toString());
		list.add(SamplingRate.SWING.toString());
		list.add(SamplingRate.TREND.toString());
		return list;
	}
	private List<String> getDefaultSampling() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.SCALPSHORT.toString());
		return list;
	}
	private List<String> getScalpSampling() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.SCALPSHORT.toString());
		list.add(SamplingRate.SCALPMEDIUM.toString());
		list.add(SamplingRate.SCALPLONG.toString());					
		return list;
	}
	private List<String> getSwingSampling() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.SWINGSHORT.toString());
		list.add(SamplingRate.SWINGMEDIUM.toString());
		list.add(SamplingRate.SWINGLONG.toString());								
		return list;
	}
	private List<String> getTrendSampling() {
		List<String> list = new ArrayList<String>();
		list.add(SamplingRate.TRENDSHORT.toString());
		list.add(SamplingRate.TRENDMEDIUM.toString());
		list.add(SamplingRate.TRENDLONG.toString());							
		return list;
	}

	
}
