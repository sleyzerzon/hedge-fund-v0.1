package com.onenow.data;

import java.util.logging.Level;

import com.ib.client.Types.WhatToShow;
import com.onenow.constant.ColumnName;
import com.onenow.constant.GenericType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.InvType;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;
import com.onenow.constant.SizeType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentFuture;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class Event {
	
	public Long timeInMilisec; // for activities and also for specific time queries, instead of to/from range

	public InvDataSource source;
	public InvDataTiming timing;
	
	public PriceType priceType; 
	public SizeType sizeType; 
	public GreekType greekType; 
	public GenericType genericType; 

	// public ObjectOrigination origin = new ObjectOrigination();

	// investments to facilitate serialization / deserialization
	public InvestmentIndex index = null;
	public InvestmentOption option = null;
	public InvestmentStock stock = null;
	public InvestmentFuture future = null;	

	public Event() {
	}	
	
	public Event(Investment investment, InvDataSource source, InvDataTiming timing, PriceType priceType) {
		setInvestment(investment);
		this.source = source;
		this.timing = timing;
		this.priceType = priceType; 

	}
	
	public String getFormatedTime() {
		return TimeParser.getFormatedPacificDateTime(timeInMilisec);
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
			s =	s + "-priceType " + priceType + " ";
		} catch (Exception e) {
		}
		
		try {
			if(timeInMilisec!=null) {
				s = s + "-time " + timeInMilisec + " ";
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
