package com.onenow.main;

import java.util.logging.Level;

import com.onenow.constant.StreamName;
import com.onenow.constant.Topology;
import com.onenow.data.InitMarket;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallStIB;
import com.onenow.io.Kinesis;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;
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
		Watchr.log(Level.INFO, "Starting for STREAM: " + streamName);

		FlexibleLogger.setup(streamName.toString());
		
		Kinesis.selfTest();
		
		if(	streamName.equals(StreamName.PRIMARY)) {
		    String toDashedDate = TimeParser.getDatePlusDashed(TimeParser.getTodayDashed(), 1);

			BrokerInteractive broker = new BrokerInteractive(	streamName, 
																InitMarket.getTestPortfolio(), 
																new BusWallStIB(streamName, Topology.LOCAL)); 

			broker.getLiveQuotes(); 			
		}
		
		if(streamName.equals(StreamName.STANDBY)) {
		}

		if(	streamName.equals(StreamName.REALTIME)) {
		    String toDashedDate = TimeParser.getDatePlusDashed(TimeParser.getTodayDashed(), 1);

			BrokerInteractive broker = new BrokerInteractive(	streamName, 
																InitMarket.getSamplePortfolio(), 
																new BusWallStIB(streamName, Topology.LOCAL)); 

			broker.getLiveQuotes(); 			
		}

		if(streamName.equals(StreamName.HISTORY)) {
			// Do historical queries from SQS
			// broker.procesHistoricalQuotesRequests();
		}

		if(	streamName.equals(StreamName.STREAMING)) {
			// TODO: Do straming queries from SQS
		}

//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
	
	private static StreamName getArgument(String[] args) {
		StreamName mode = null;
		if(args.length>0) {
			if(args[0]!=null) {
				String s0 = args[0];
				if(s0.equals("PRIMARY")) {
					mode = StreamName.PRIMARY;
				}
				if(s0.equals("STANDBY")) {
					mode = StreamName.STANDBY;
				}
				if(s0.equals("REALTIME")) {
					mode = StreamName.REALTIME;
				}
				if(s0.equals("HISTORY")) {
					mode = StreamName.HISTORY;
				}
				if(s0.equals("STREAMING")) {
					mode = StreamName.STREAMING;
				}
			} else {
		    	Watchr.log(Level.SEVERE, "ERROR: mode is a required argument");
			}
		}
		return mode;
	}
	
}
