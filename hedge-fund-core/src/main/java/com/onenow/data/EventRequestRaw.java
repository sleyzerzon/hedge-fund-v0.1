package com.onenow.data;

import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;
import com.onenow.instrument.Investment;

public class EventRequestRaw extends EventRequest {
	
	public DBQuery dbQuery; 
	public ColumnName columnName;
	

	public EventRequestRaw() {
		
	}
	
	public EventRequestRaw(	DBQuery dbQuery, ColumnName columnName, SamplingRate sampling, String timeGap, String endPoint, EventActivityPriceHistory event) {

		super();
		this.dbQuery = dbQuery;
		this.columnName = columnName;

		super.sampling = sampling;
		super.timeGap = timeGap;
		super.endPoint = endPoint;
		
		setInvestment(event.getInvestment());
		
		super.source = event.source;
		super.timing = event.timing;	
		
		// whichever is not null is the type in question
		super.priceType = event.priceType;
		super.sizeType = event.sizeType;

	}
	
	public EventRequestRaw(	DBQuery dbQuery, ColumnName columnName,
							Investment inv, PriceType tradeType, SamplingRate sampling, 
							String fromDashedDate, String toDashedDate,
							InvDataSource source, InvDataTiming timing) {
		
		super();
		this.dbQuery = dbQuery;
		this.columnName = columnName;
		
		setInvestment(inv);

		super.source = source;
		super.timing = timing;		
		super.priceType = tradeType;

		super.sampling = sampling;
		super.fromDashedDate = fromDashedDate;
		super.toDashedDate = toDashedDate;		

	}

	public EventRequestRaw(	DBQuery dbQuery, ColumnName columnName,
							Investment inv, PriceType tradeType, SamplingRate sampling, 
							InvDataSource source, InvDataTiming timing,
							String timeGap, String endPoint) {

		super();
		this.dbQuery = dbQuery;
		this.columnName = columnName;

		setInvestment(inv);
		
		super.source = source;
		super.timing = timing;
		super.priceType = tradeType;
		
		super.sampling = sampling;
		super.timeGap = timeGap;
		super.endPoint = endPoint;

	}
	
	public String toString() {
		
		String s = "";
		
		s = s + super.toString() + " ";

		try {
			s = s + "-dbQuery " + dbQuery + " ";
		} catch (Exception e) {
		}

		try {
			s = s + "-columnName " + columnName + " ";
		} catch (Exception e) {
		}
	
		return s;
		
	}

}
