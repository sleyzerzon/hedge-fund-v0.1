package com.onenow.database;

import javax.sql.DataSource;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
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
	 * @param dataType
	 * @return
	 */
	public String getInvestmentTimedKey(Long time, Investment inv, String dataType, com.onenow.constant.InvDataSource source, InvDataTiming timing) {
		String s = "";
		s = s + time.toString();
		s = s + "-" + getInvestmentKey(inv, dataType, source, timing);
		return s;
	}
	
	/**
	 * Key to find price/size values for specific investments
	 * @param inv
	 * @param dataType
	 * @return
	 */
	public String getInvestmentKey(Investment inv, String dataType, com.onenow.constant.InvDataSource source, InvDataTiming timing) {
		Underlying under = inv.getUnder();
		String s = ""; 
		s = s + under.getTicker() + "-" + inv.getInvType();		
		if (inv instanceof InvestmentOption) {
			Double strike = ((InvestmentOption) inv).getStrikePrice();
			String exp = (String) ((InvestmentOption) inv).getExpirationDate();
			s = s + "-" + strike + "-" + exp; 
		}
		s = s + "-" + dataType;
		s = s + "-" + source.toString() + "-" + timing.toString();
		return (s);
	}
	
	/**
	 * Key to find the latest time stamp available for an investment
	 * @param inv
	 * @param dataType
	 * @param timeStamp
	 * @return
	 */
	public String getTimestampKey(Investment inv, String dataType, Long timeStamp) {
		String s = "";
		s = inv.toString();
		s = s + "-" + dataType;
		return s;
	}

	
	public String getChartKey(	Investment inv, String dataType, String sampling, 
								String fromDate, String toDate,
								InvDataSource source, InvDataTiming timing) {
		String s = "";
		s = s + inv.toString();
		s = s + "-" + dataType;
		s = s + "-" + sampling;
		s = s + "-" + fromDate + "-" + toDate;
		s = s + "-" + source.toString() + "-" + timing.toString();
		System.out.println("key " + s);
		return s;
	}
	
	
}



