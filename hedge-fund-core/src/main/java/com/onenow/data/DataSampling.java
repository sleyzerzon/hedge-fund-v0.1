package com.onenow.data;

import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.SamplingRate;

public class DataSampling {	
	
	public DataSampling() {
	}	
	
	public static String getGroupByTimeString(SamplingRate sampling) {
		
		String dbSamplingRate = "15m";

		if(sampling.equals(SamplingRate.UHFREQ)) {		// midpoint
			dbSamplingRate = "5s";
		}
		if(sampling.equals(SamplingRate.HIFREQ)) {		// midpoint
			dbSamplingRate = "2m";
		}
		if(sampling.equals(SamplingRate.SCALP)) {		// midpoint
			dbSamplingRate = "15m";
		}
		if(sampling.equals(SamplingRate.SWING)) {		// midpoint
			dbSamplingRate = "60m";
		}
		if(sampling.equals(SamplingRate.TREND)) {		// midpoint
			dbSamplingRate = "4h";
		}
		if(sampling.equals(SamplingRate.UHFSHORT)) {	// UHF 1sec, 5sec, 20sec
			dbSamplingRate = "1s";
		}
		if(sampling.equals(SamplingRate.UHFMEDIUM)) {
			dbSamplingRate = "5s";
		}
		if(sampling.equals(SamplingRate.UHFLONG)) {
			dbSamplingRate = "20s";
		}
		if(sampling.equals(SamplingRate.HFSHORT)) {		//HF 1min, 3min, 15min
			dbSamplingRate = "1m";
		}
		if(sampling.equals(SamplingRate.HFMEDIUM)) {
			dbSamplingRate = "3m";
		}
		if(sampling.equals(SamplingRate.HFLONG)) {
			dbSamplingRate = "15m";
		}
		if(sampling.equals(SamplingRate.SCALPSHORT)) {		//SCALPING 5min, 15min, 60min
			dbSamplingRate = "5m";
		}
		if(sampling.equals(SamplingRate.SCALPMEDIUM)) {
			dbSamplingRate = "15m";
		}
		if(sampling.equals(SamplingRate.SCALPLONG)) {
			dbSamplingRate = "60m";
		}
		if(sampling.equals(SamplingRate.SWINGSHORT)) {		//SWINGING 60min, 240min, daily
			dbSamplingRate = "60m";
		}
		if(sampling.equals(SamplingRate.SWINGMEDIUM)) {
			dbSamplingRate = "4h";
		}
		if(sampling.equals(SamplingRate.SWINGLONG)) {
			dbSamplingRate = "1d";
		}
		if(sampling.equals(SamplingRate.TRENDSHORT)) {		//TREND 4hr, daily, weekly
			dbSamplingRate = "4h";
		}
		if(sampling.equals(SamplingRate.TRENDMEDIUM)) {
			dbSamplingRate = "1d";
		}
		if(sampling.equals(SamplingRate.TRENDLONG)) {
			dbSamplingRate = "1w";
		}
		return dbSamplingRate;
	}
	
	
	// call multiple times to get all
	public static List<SamplingRate> getList(SamplingRate rate) { 
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		if(rate.equals(SamplingRate.UHFREQ)) {
			list.addAll(getUHFSampling());
		}
		if(rate.equals(SamplingRate.HIFREQ)) {
			list.addAll(getHIFREQSampling());
		}
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
	
	public static List<SamplingRate> getTradingOptions() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.UHFREQ);
		list.add(SamplingRate.HIFREQ);
		list.add(SamplingRate.SCALP);
		list.add(SamplingRate.SWING);
		list.add(SamplingRate.TREND);
		return list;
	}
	private static List<SamplingRate> getUHFSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.UHFSHORT);
		list.add(SamplingRate.UHFMEDIUM);
		list.add(SamplingRate.UHFLONG);					
		return list;
	}
	private static List<SamplingRate> getHIFREQSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.HFSHORT);
		list.add(SamplingRate.HFMEDIUM);
		list.add(SamplingRate.HFLONG);					
		return list;
	}
	private static List<SamplingRate> getScalpSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.SCALPSHORT);
		list.add(SamplingRate.SCALPMEDIUM);
		list.add(SamplingRate.SCALPLONG);					
		return list;
	}
	private static List<SamplingRate> getSwingSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.SWINGSHORT);
		list.add(SamplingRate.SWINGMEDIUM);
		list.add(SamplingRate.SWINGLONG);								
		return list;
	}
	private static List<SamplingRate> getTrendSampling() {
		List<SamplingRate> list = new ArrayList<SamplingRate>();
		list.add(SamplingRate.TRENDSHORT);
		list.add(SamplingRate.TRENDMEDIUM);
		list.add(SamplingRate.TRENDLONG);							
		return list;
	}

	
}
