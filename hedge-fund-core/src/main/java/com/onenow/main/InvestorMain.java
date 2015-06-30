package com.onenow.main;

import java.util.logging.Level;

import com.onenow.constant.StreamName;
import com.onenow.data.InitMarket;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallStInteractiveBrokers;
import com.onenow.io.BusSystem;
import com.onenow.util.InitLogger;
import com.onenow.util.Watchr;

/** 
 * Makes investment choices in real-time
 * Also provides real-time and historical data as a service to the back-end
 * It operates in several different BrokerMode
 * - In PRIMARY / STANDBY modes, the application works as a database, only the master effecting investment decisions
 * - In HISTORIAN / HISTORIANRT modes, the application only provides data-gathering services to the back-end 
 * @param args
 */

public class InvestorMain {
 	
	public static void main(String[] args) {
		
		StreamName streamName = getArgument(args);
		InitLogger.run(streamName.toString());
		Watchr.log(Level.INFO, "Starting for STREAM: " + streamName);

		BusWallStInteractiveBrokers bus = new BusWallStInteractiveBrokers(streamName);

		// Kinesis.selfTest();


		if(BusSystem.isPrimaryStream(streamName)) {
			BrokerInteractive broker = new BrokerInteractive(	streamName, 
																InitMarket.getPrimaryPortfolio(), 
																bus); 
			broker.getLiveQuotes(); 			
		}
		
		if(BusSystem.isStandbyStream(streamName)) {
			// TODO: passive role on same investments as primary
		}

		if(BusSystem.isRealtimeStream(streamName)) {
			BrokerInteractive broker = new BrokerInteractive(	streamName, 
																InitMarket.getRealtimePortfolio(), 
																bus); 
			broker.getLiveQuotes(); 			
		}

		if(BusSystem.isHistoryStream(streamName)) {
			BrokerInteractive broker = new BrokerInteractive(	streamName, bus); 
			broker.procesHistoricalQuotesRequests();
		}

		if(BusSystem.isStreamingStream(streamName)) {
			// TODO: Do straming queries from SQS
		}

//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
	
	private static StreamName getArgument(String[] args) {
		StreamName streamName = null;
		
		if(args.length>0) {
			if(args[0]!=null) {
				streamName = BusSystem.getStreamName(args[0]);
			} 
		} else {
	    	Watchr.log(Level.SEVERE, "ERROR: mode is a required as a java process argument");
		}
		
		return streamName;
	}
	
	
}
