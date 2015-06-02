package com.onenow.io;

import javax.sql.DataSource;

import com.amazonaws.regions.Region;
import com.onenow.constant.StreamName;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.DataSampling;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.Underlying;

/**
 * Generate the database key for individual time series and data points
 *
 */
public class Lookup {
	
	public Lookup() {
		
	}

	/**
	 * Key to find, for a specific point in time, the price/size for an investment
	 * @param time
	 * @param inv
	 * @param tradeType
	 * @return
	 */
	public static String getInvestmentTimedKey(Long time, Investment inv, TradeType tradeType, com.onenow.constant.InvDataSource source, InvDataTiming timing) {
		String s = "";
		s = s + time.toString();
		s = s + "-" + getInvestmentKey(inv, tradeType, source, timing);
		return s;
	}
	
	/**
	 * Key to find price/size values for specific investments
	 * @param inv
	 * @param tradeType
	 * @return
	 */
	public static String getInvestmentKey(Investment inv, TradeType tradeType, com.onenow.constant.InvDataSource source, InvDataTiming timing) {
		Underlying under = inv.getUnder();
		String s = ""; 
		s = s + under.getTicker() + "-" + inv.getInvType();		
		if (inv instanceof InvestmentOption) {
			String exp = (String) ((InvestmentOption) inv).getExpirationDate();
			Double strike = ((InvestmentOption) inv).getStrikePrice();
			s = s + "-" + exp + "-" + strike; 
		}
		s = s + "-" + tradeType.toString();
		s = s + "-" + source.toString() + "-" + timing.toString();
		return (s);
	}
	
	/**
	 * Key to find the latest time stamp available for an investment
	 * @param inv
	 * @param tradeType
	 * @param timeStamp
	 * @return
	 */
	public static String getTimestampKey(Investment inv, TradeType tradeType, Long timeStamp) {
		String s = "";
		s = inv.toString();
		s = s + "-" + tradeType.toString();
		return s;
	}

	
	public static String getChartKey(	Investment inv, TradeType tradeType, SamplingRate sampling, 
								String fromDate, String toDate,
								InvDataSource source, InvDataTiming timing) {
		String s = "";
		s = s + inv.toString();
		s = s + "-" + tradeType.toString();
		s = s + "-" + sampling.toString();
		s = s + "-" + fromDate + "-" + toDate;
		s = s + "-" + source.toString() + "-" + timing.toString();
//		System.out.println("key " + s);
		return s;
	}
	
	
	public static String getKinesisKey(Region region) {
		String s = "";
		s = s + region.toString();
		return s;
	}
}



