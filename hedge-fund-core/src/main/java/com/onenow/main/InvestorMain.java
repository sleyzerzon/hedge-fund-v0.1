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
		
		StreamName mode = getModeArgument(args);
		Watchr.log(Level.INFO, "Starting for STREAM: " + mode);

		FlexibleLogger.setup(mode.toString());
		
		Kinesis.selfTest();
		
		if(	mode.equals(StreamName.PRIMARY)) {
		    String toDashedDate = TimeParser.getDatePlusDashed(TimeParser.getTodayDashed(), 1);

			BrokerInteractive broker = new BrokerInteractive(	mode, 
																InitMarket.getTestPortfolio(), 
																new BusWallStIB(mode, Topology.LOCAL)); 

			broker.getLiveQuotes(); 			
		}
		
		if(mode.equals(StreamName.STANDBY)) {
		}

		if(	mode.equals(StreamName.REALTIME)) {
		    String toDashedDate = TimeParser.getDatePlusDashed(TimeParser.getTodayDashed(), 1);

			BrokerInteractive broker = new BrokerInteractive(	mode, 
																InitMarket.getSamplePortfolio(), 
																new BusWallStIB(mode, Topology.LOCAL)); 

			broker.getLiveQuotes(); 			
		}

		if(mode.equals(StreamName.HISTORY)) {
			// Do historical queries from SQS
			// broker.procesHistoricalQuotesRequests();
		}

		if(	mode.equals(StreamName.STREAMING)) {
			// TODO: Do straming queries from SQS
		}

//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
	
	private static StreamName getModeArgument(String[] args) {
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
