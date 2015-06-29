package com.onenow.execution;

import java.util.HashMap;
import java.util.logging.Level;

import com.amazonaws.services.sqs.model.Message;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.HistorianConfig;
import com.onenow.data.QuoteHistoryInvestment;
import com.onenow.instrument.Investment;
import com.onenow.io.Lookup;
import com.onenow.portfolio.BusController;
import com.onenow.util.Piping;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class QuoteHistoryChain {

	
	public static BusController controller;

	private static HashMap<String, QuoteHistoryInvestment>		history = new HashMap<String, QuoteHistoryInvestment>();						// price history from L3

	private static long lastQueryTime;

	public QuoteHistoryChain() {
		
	}
	
	public QuoteHistoryChain(BusController controller) {
		this.controller = controller;
	}


	public void processHistoryOneRequest(Message message) {
		Object requestObject = Piping.deserialize(message.getBody(), EventRequestHistory.class);
		  if(requestObject!=null) {
			  Watchr.log(Level.FINE, "Received request object: " + requestObject.toString());
			  EventRequestHistory request = (EventRequestHistory) requestObject;
			  // get the history reference for the specific investment 
			  QuoteHistoryInvestment invHist = lookupInvHistory(request);
			  // TODO: handle the case of many requests with no response, which over-runs IB (50 max at a time) 
			  TimeParser.paceHistoricalQuery(lastQueryTime); 
			  // look for SQS requests for history
			  String endDateTime = TimeParser.getClose(TimeParser.getDateUndashed(TimeParser.getDateMinusDashed(request.toDashedDate, 1)));
			  
			  // TODO: request only if <x number of data points in the time series
			  Integer reqId = readHistoricalQuotes(	request.getInvestment(), 
													endDateTime, 
													request.config, invHist);
			  lastQueryTime = TimeParser.getTimestampNow();		
		  }
	}
				  
	  /**
	   * Returns reference to object where history will be stored, upon asynchronous return
	   */
	  public Integer readHistoricalQuotes(Investment inv, String endDateTime, HistorianConfig config, QuoteHistoryInvestment quoteHistory) {
	
		  Contract contract = ContractFactory.getContract(inv);
		  Integer reqId = controller.reqHistoricalData(	contract, endDateTime, 
	    												1, config.durationUnit, config.barSize, config.whatToShow, 
	    												false, quoteHistory);
		  String log = 	"||| REQUESTED HISTORY FOR: " + inv.toString() + " ||| CONTRACT " + contract.toString() + 
				  		"||| ENDING " + endDateTime + " ||| REQID " + reqId + " ||| CONFIG " + config.toString();
		  Watchr.log(Level.INFO, log);
	    
		  return reqId;
	  }

		/**
		 * Every investment has it's own history from L3
		 * @param inv
		 * @param tradeType
		 * @param source
		 * @param timing
		 * @return
		 */
	  	private static QuoteHistoryInvestment lookupInvHistory(EventRequestHistory request) {
	  		
			String key = Lookup.getEventTimedKey(request);
	
			if(history.get(key)==null) {
				history.put(key, new QuoteHistoryInvestment(request));	
			} 
				
			return history.get(key);
		}
}
