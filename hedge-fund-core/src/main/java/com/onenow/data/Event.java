package com.onenow.data;

import java.util.logging.Level;

import com.onenow.constant.GenericType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.DataTiming;
import com.onenow.constant.InvType;
import com.onenow.constant.PriceType;
import com.onenow.constant.SizeType;
import com.onenow.constant.VolatilityType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvFuture;
import com.onenow.instrument.InvIndex;
import com.onenow.instrument.InvOption;
import com.onenow.instrument.InvStock;
import com.onenow.instrument.Underlying;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class Event {
	
	public Long timeInMsec; // for activities and also for specific time queries, instead of to/from range

	public InvDataSource source;
	public DataTiming timing;
	
	public PriceType priceType; 
	public SizeType sizeType; 
	public GreekType greekType; 
	public VolatilityType volatilityType; 
	public GenericType genericType; 

	// public ObjectOrigination origin = new ObjectOrigination();

	// investments to facilitate serialization / deserialization
	public InvIndex index = null;
	public InvOption option = null;
	public InvStock stock = null;
	public InvFuture future = null;	

	public Event() {
	}	
	
	public Event(Investment investment, InvDataSource source, DataTiming timing, PriceType priceType) {
		setInvestment(investment);
		this.source = source;
		this.timing = timing;
		this.priceType = priceType; 

	}
	
	public String getFormatedTime() {
		return TimeParser.getFormatedPacificDateTime(timeInMsec);
	}

	public void setInvestment(Investment inv) {
		
		if( (inv instanceof InvIndex) || inv.getInvType().equals(InvType.INDEX)) {
			index = (InvIndex) inv;
			return;
		}
		if( (inv instanceof InvOption) || inv.getInvType().equals(InvType.CALL) || inv.getInvType().equals(InvType.PUT)) {
			option = (InvOption) inv;
			return;
		}
		if( (inv instanceof InvStock) || inv.getInvType().equals(InvType.STOCK)) {
			stock = (InvStock) inv;
			return;
		}
		if( (inv instanceof InvFuture) || inv.getInvType().equals(InvType.FUTURE)) {
			future = (InvFuture) inv;
			return;
		}
		try {
			Watchr.log(Level.SEVERE, "Trying to set investment type not handled: " + inv.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Investment getInvestment() {
		if(index!=null) {
			return index;
		}
		if(option!=null) {
			return option;
		}
		if(stock!=null) {
			return stock;
		}
		if(future!=null) {
			return future;
		}
		Watchr.log(Level.SEVERE, "Trying to get investment type not handled: ");
		return null;
	}

	public Underlying getUnder() {
		return getInvestment().getUnder();
	}
	
	public InvType getInvType() {
		return getInvestment().getInvType();
	}

	public Double getOptionStrikePrice() {
		if(option==null) {
			return 0.0;
		}
		return option.getStrikePrice();
	}

	public String getOptionExpirationDate() {
		if(option==null) {
			return "na";
		} 
		return option.getExpirationDate();
	}

	public String getFutureExpirationDate() {
		if(future==null) {
			return "na";
		}
		return future.getExpirationDate();
	}

	public String toString() {
		String s = "";
				
		try {
			s =	s + getInvestment().toString() + " ";
		} catch (Exception e) {
		}

		try {
			s =	s + "-source " + source + " ";
		} catch (Exception e) {
		} 

		try {
			s =	s + "-timing " + timing + " ";
		} catch (Exception e) {
		} 
		
		try {
			if(priceType!=null) {
				s =	s + "-priceType " + priceType + " ";
			}
		} catch (Exception e) {
		}

		try {
			if(sizeType!=null) {
				s =	s + "-sizeType " + sizeType + " ";
			}
		} catch (Exception e) {
		}

		try {
			if(greekType!=null) {
				s =	s + "-greekType " + greekType + " ";
			}
		} catch (Exception e) {
		}

		try {
			if(genericType!=null) {
				s =	s + "-genericType " + genericType + " ";
			}
		} catch (Exception e) {
		}

		try {
			if(timeInMsec!=null) {
				s = s + "-time " + timeInMsec + " ";
			}
		} catch (Exception e) {
		}

		try {
		s = s + "-date " + getFormatedTime() + " ";
		} catch (Exception e) {
		}
		
		return s;
	}
}
