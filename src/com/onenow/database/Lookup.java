package com.onenow.database;

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
	public String getInvestmentTimedKey(Long time, Investment inv, String dataType) {
		String s="";
		s = time.toString() + "-";
		s = s + getInvestmentKey(inv, dataType);
		return s;
	}
	
	/**
	 * Key to find price/size values for specific investments
	 * @param inv
	 * @param dataType
	 * @return
	 */
	public String getInvestmentKey(Investment inv, String dataType) {
		Underlying under = inv.getUnder();
		String lookup = ""; 
		lookup = under.getTicker() + "-" + inv.getInvType();		
		if (inv instanceof InvestmentOption) {
			Double strike = ((InvestmentOption) inv).getStrikePrice();
			String exp = (String) ((InvestmentOption) inv).getExpirationDate();
			lookup = lookup + "-" + strike + "-" + exp; 
		}
		lookup = lookup + "-" + dataType;
		
		return (lookup);
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
		s = inv.toString() + "-";
		s = s + dataType;
		return s;
	}

	
	public String getChartKey(Investment inv, String dataType, String sampling, String fromDate, String toDate) {
		String s = "";
		s = inv.toString() + "-";
		s = s + dataType + "-";
		s = s + sampling + "-";
		s = s + fromDate + "-" + toDate;
		System.out.println("key " + s);
		return s;
	}
	
	
}



