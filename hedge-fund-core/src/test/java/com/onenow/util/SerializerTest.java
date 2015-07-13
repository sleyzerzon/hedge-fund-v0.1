package com.onenow.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.WhatToShow;
import com.onenow.constant.InvDataSource;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.HistorianConfig;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;

public class SerializerTest {
	
	Investment inv = new InvestmentStock(new Underlying("AAPL"));
	String toDashedDate = TimeParser.getTodayDashed();
	HistorianConfig config = HistorianService.getConfig(InvDataSource.IB, BarSize._5_mins, WhatToShow.TRADES);

	EventRequestHistory request = new EventRequestHistory(inv, toDashedDate, config);
	
	
	@Test
	public void deserialize() {
		Watchr.log("OBJECT " + request.toString());
		String json = Piping.serialize((Object) request);
		Watchr.log("SERIALIZED " + json);
		EventRequestHistory newReq = (EventRequestHistory) Piping.deserialize(json, EventRequestHistory.class);
		Watchr.log("DESERIALIZED " + newReq.toString());
		
		// equals compares the string form
		Assert.assertTrue(Piping.serialize(request).equals(Piping.serialize(newReq)));
	}


//	@Test
//	public void serialize() {
//    
//	}
	
	
}
