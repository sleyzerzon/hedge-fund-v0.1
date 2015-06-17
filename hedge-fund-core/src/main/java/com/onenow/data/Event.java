package com.onenow.data;

import java.util.logging.Level;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.InvType;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentFuture;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.util.Watchr;

public class Event {
	
	// investments to facilitate serialization / deserialization
	public InvestmentIndex index = null;
	public InvestmentOption option = null;
	public InvestmentStock stock = null;
	public InvestmentFuture future = null;
	
	public InvDataSource source;
	public InvDataTiming timing;
	public TradeType tradeType; 

	// public ObjectOrigination origin = new ObjectOrigination();

	public Event() {
		
	}	
	
	public Event(Investment investment, InvDataSource source, InvDataTiming timing, TradeType tradeType) {
		setInvestment(investment);
		this.source = source;
		this.timing = timing;
		this.tradeType = tradeType; 

	}
	
	public void setInvestment(Investment inv) {
		
		if( (inv instanceof InvestmentIndex) || inv.getInvType().equals(InvType.INDEX)) {
			index = (InvestmentIndex) inv;
			return;
		}
		if( (inv instanceof InvestmentOption) || inv.getInvType().equals(InvType.CALL) || inv.getInvType().equals(InvType.PUT)) {
			option = (InvestmentOption) inv;
			return;
		}
		if( (inv instanceof InvestmentStock) || inv.getInvType().equals(InvType.STOCK)) {
			stock = (InvestmentStock) inv;
			return;
		}
		if( (inv instanceof InvestmentFuture) || inv.getInvType().equals(InvType.FUTURE)) {
			future = (InvestmentFuture) inv;
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
		
		// s = s + origin.toString("EVENT") + " "; 
		s =	s + getInvestment().toString() + " ";
		s =	s + "source " + source + " "; 
		s =	s + "-timing " + timing + " "; 
		s =	s + "-tradeType " + tradeType;
		
		return s;
	}
}
