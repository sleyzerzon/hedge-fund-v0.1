package com.onenow.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.data.EventRequestHistory;
import com.onenow.data.HistorianConfig;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;

public class SerializerTest {
	
	Investment inv = new InvestmentStock(new Underlying("AAPL"));
	String toDashedDate = TimeParser.getTodayDashed();
	HistorianConfig config = new HistorianService().size30sec;

	EventRequestHistory request = new EventRequestHistory(inv, toDashedDate, config);
	
	
	@Test
	public void deserialize() {
		Watchr.log("OBJECT " + request.toString());
		String json = Serializer.serialize(request);
		Watchr.log("SERIALIZED " + json);
		EventRequestHistory newReq = (EventRequestHistory) Serializer.deserialize(json, EventRequestHistory.class);
		Watchr.log("DESERIALIZED " + newReq.toString());
		Assert.assertTrue(request.equals(newReq));
	}


//	@Test
//	public void serialize() {
//    
//	}
	
	
}
