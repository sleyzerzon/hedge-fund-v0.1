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

public class DataHistoryChain {

	
	public static BusController controller;

	private static HashMap<String, QuoteHistoryInvestment>		history = new HashMap<String, QuoteHistoryInvestment>();						// price history from L3

	private static long lastQueryTime;

	public DataHistoryChain() {
		
	}
	
	public DataHistoryChain(BusController controller) {
		this.controller = controller;
	}


	public void processHistoryOneRequest(Message message) {
		Object requestObject = Piping.deserialize(message.getBody(), EventRequestHistory.class);
		  if(requestObject!=null) {
			  
			  Watchr.log(Level.FINE, "Received request object: " + requestObject.toString());
			  EventRequestHistory request = (EventRequestHistory) requestObject;
			  QuoteHistoryInvestment invHist = lookupInvHistory(request);	// one history per investment
			  
			  // TODO: count number of outstanding requests, must be < 50
			  TimeParser.paceHistoricalQuery(lastQueryTime);				// avoid over-running IB
			  
			  Integer reqId = requestHistoricalData(	request.getInvestment(), 
					  									getEndDateTime(request), 
														request.config, invHist);
			  lastQueryTime = TimeParser.getTimestampNow();		
		  }
	}

	private String getEndDateTime(EventRequestHistory request) {
		String endDateTime = TimeParser.getClose(TimeParser.getDateUndashed(TimeParser.getDateMinusDashed(request.toDashedDate, 1)));
		return endDateTime;
	}
				  
	  /**
	   * Returns reference to object where history will be stored, upon asynchronous return
	   */
	  public Integer requestHistoricalData(Investment inv, String endDateTime, HistorianConfig config, QuoteHistoryInvestment quoteHistory) {
	
		  Contract contract = ContractFactory.getContract(inv);
		  Integer reqId = null;
 		  try {
 			  reqId = controller.reqHistory(	contract, endDateTime, 
												1, config.durationUnit, config.barSize, config.whatToShow, 
												false, quoteHistory);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  		  
		  String log = 	"||| REQUESTED HISTORY FOR: " + inv.toString() + " ||| CONTRACT " + contract.toString() + 
				  		"||| ENDING " + endDateTime + " ||| REQID " + reqId + " ||| CONFIG " + config.toString();
		  Watchr.log(Level.INFO, log);
	    
		  return reqId;
	  }

		/**
		 * Every investment has it's own history from L3
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
