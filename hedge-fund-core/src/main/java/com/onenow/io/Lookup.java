package com.onenow.io;

import com.amazonaws.regions.Region;
import com.onenow.constant.PriceType;
import com.onenow.data.Event;
import com.onenow.data.EventRequest;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvFuture;
import com.onenow.instrument.InvOption;
import com.onenow.instrument.Underlying;
import com.onenow.util.Watchr;

/**
 * Generate the database key for individual time series and data points
 *
 */
public class Lookup {
	
	static String separator = "-";
	
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
			s = s + separator + getEventKey(event);
		} catch (Exception e) {
		}
			
		return s;
	}
	
	public static String getPriceEventKey(Event event) {
		String s = "";
		s = getEventKey(event) + lookupPriceType(event);		
		return s;
	}

	public static String getSizeEventKey(Event event) {
		String s = "";
		s = getEventKey(event) + lookupSizeType(event);
		return s;
	}

	/**
	 * Key to find price/size values for specific investments
	 * @param inv
	 * @param priceType
	 * @return
	 */	
	private static String getEventKey(Event event) {
		String s = ""; 

		s = lookupGeneral(event, s);		
		
		s = lookupOptions(event, s);
		
		s = lookupFutures(event, s);

		// source/timing vary by datapoint, and are thus now columns in the time series
		// s = lookupSourceTiming(event, s);
	
		return (s);
	}

	private static String lookupGeneral(Event event, String s) {
		// GENERAL
		try {
			Underlying under = event.getInvestment().getUnder();
			s = s + under.getTicker() + separator + event.getInvestment().getInvType();
		} catch (Exception e) {
			e.printStackTrace();
			Watchr.severe("LOOKUP TICKER EXCEPTION: " + event.toString());
		}
		return s;
	}

	private static String lookupOptions(Event event, String s) {
		// OPTIONS
		try {
			if (event.getInvestment() instanceof InvOption) {
				String exp = (String) ((InvOption) event.getInvestment()).getExpirationDate();
				Double strike = ((InvOption) event.getInvestment()).getStrikePrice();
				s = s + separator + exp + separator + strike; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			Watchr.severe("LOOKUP OPTIONS EXCEPTION: " + event.toString());
		}
		return s;
	}

	private static String lookupFutures(Event event, String s) {
		// FUTURES
		try {
			if (event.getInvestment() instanceof InvFuture) {
				String exp = (String) ((InvFuture) event.getInvestment()).getExpirationDate();
				s = s + separator + exp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Watchr.severe("LOOKUP FUTURES EXCEPTION: " + event.toString());
		}
		return s;
	}

	private static String lookupPriceType(Event event) {
		String s = "";
		try {
			String priceType = separator + event.priceType.toString();
			s = s + priceType;
			if(priceType.equals(separator)) {
				Watchr.severe("LOOKUP PRICE TYPE EMPTY");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Watchr.severe("LOOKUP PRICE TYPE EXCEPTION: " + event.toString());
		}
		return s;
	}

	private static String lookupSizeType(Event event) {
		String s = "";
		try {
			String sizeType = separator + event.sizeType.toString();
			s = s + sizeType;
			if(sizeType.equals(separator)) {
				Watchr.severe("LOOKUP SIZE TYPE EMPTY");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Watchr.severe("LOOKUP SIZE TYPE EXCEPTION: " + event.toString());
		}
		return s;
	}

	private static String lookupSourceTiming(Event event, String s) {
		// SOURCE AND TIMING
		try {
			String source = separator + event.source.toString();
			String timing = separator + event.timing.toString();
			s = s + source + timing;
			if(source.equals(separator) || timing.equals(separator)) {
				Watchr.severe("LOOKUP PRICE SOURCE / TIMING EMPTY");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Watchr.severe("LOOKUP SOURCE / TIMING EXCEPTION: " + event.toString());
		}
		return s;
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
		
		s = s + separator + tradeType.toString();
		
		return s;
	}

	
	public static String getChartKey(EventRequest request) {
		String s = "";

		try {
			s = s + request.getInvestment().toString();
		} catch (Exception e) {
		}

		try {
			s = s + separator + request.priceType.toString();
		} catch (Exception e) {
		}

		try {
			s = s + separator + request.sampling.toString();
		} catch (Exception e) {
		}

		try {
			s = s + separator + request.toDashedDate;
		} catch (Exception e) {
		}

		try {
			s = s + separator + request.source.toString() + separator + request.timing.toString();
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



