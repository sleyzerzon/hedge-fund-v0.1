package com.onenow.io;

import java.util.logging.Level;

import javax.sql.DataSource;

import com.amazonaws.regions.Region;
import com.onenow.constant.StreamName;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;
import com.onenow.data.DataSampling;
import com.onenow.data.EventActivity;
import com.onenow.data.Event;
import com.onenow.data.EventRequest;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentFuture;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.Underlying;
import com.onenow.util.Watchr;

/**
 * Generate the database key for individual time series and data points
 *
 */
public class Lookup {
	
	public Lookup() {
		
	}

	/**
	 * Key to find, for a specific point in time, the price/size for an investment
	 * @return
	 */
	public static String getEventTimedKey(EventRequest event) {
		String s = "";
		
		try {
			s = s + event.toDashedDate.toString(); // time -> toDashedDate
		} catch (Exception e) {
		}
			
		try {
			s = s + "-" + getEventKey(event);
		} catch (Exception e) {
		}
			
		return s;
	}
	
	/**
	 * Key to find price/size values for specific investments
	 * @param inv
	 * @param priceType
	 * @return
	 */	
	public static String getEventKey(Event event) {
		String s = ""; 
	
		try {
			Underlying under = event.getInvestment().getUnder();
			s = s + under.getTicker() + "-" + event.getInvestment().getInvType();
		} catch (Exception e) {
		}		
		
		try {
			if (event.getInvestment() instanceof InvestmentOption) {
				String exp = (String) ((InvestmentOption) event.getInvestment()).getExpirationDate();
				Double strike = ((InvestmentOption) event.getInvestment()).getStrikePrice();
				s = s + "-" + exp + "-" + strike; 
			}
		} catch (Exception e) {
		}
		
		try {
			if (event.getInvestment() instanceof InvestmentFuture) {
				String exp = (String) ((InvestmentFuture) event.getInvestment()).getExpirationDate();
				s = s + "-" + exp;
			}
		} catch (Exception e) {
		}

		try {
			s = s + "-" + event.priceType.toString();
		} catch (Exception e) {
		}

		try {
			s = s + "-" + event.source.toString() + "-" + event.timing.toString();
		} catch (Exception e) {
		}
	
		return (s);
	}
	
	/**
	 * Key to find the latest time stamp available for an investment
	 * @param inv
	 * @param tradeType
	 * @param timeStamp
	 * @return
	 */
	public static String getTimestampKey(Investment inv, PriceType tradeType, Long timeStamp) {
		String s = "";
		
		s = inv.toString();
		
		s = s + "-" + tradeType.toString();
		
		return s;
	}

	
	public static String getChartKey(EventRequest request) {
		String s = "";

		try {
			s = s + request.getInvestment().toString();
		} catch (Exception e) {
		}

		try {
			s = s + "-" + request.priceType.toString();
		} catch (Exception e) {
		}

		try {
			s = s + "-" + request.sampling.toString();
		} catch (Exception e) {
		}

		try {
			s = s + "-" + request.toDashedDate;
		} catch (Exception e) {
		}

		try {
			s = s + "-" + request.source.toString() + "-" + request.timing.toString();
		} catch (Exception e) {
		}
		
		return s;
	}
	
	
	public static String getKinesisKey(Region region) {
		String s = "";
		s = s + region.toString();
		return s;
	}
}



