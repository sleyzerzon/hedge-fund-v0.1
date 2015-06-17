package com.onenow.main;

import java.util.Properties;
import java.util.logging.Level;

import com.onenow.constant.StreamName;
import com.onenow.constant.Topology;
import com.onenow.data.InitMarket;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.BusWallStInteractiveBrokers;
import com.onenow.io.Kinesis;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.InitLogger;
import com.onenow.util.SysProperties;
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
		InitLogger.run(streamName.toString());
		Watchr.log(Level.INFO, "Starting for STREAM: " + streamName);

		BusWallStInteractiveBrokers bus = new BusWallStInteractiveBrokers(streamName);

		// Kinesis.selfTest();


		if(	streamName.equals(StreamName.PRIMARY)) {
			BrokerInteractive broker = new BrokerInteractive(	streamName, 
																InitMarket.getTestPortfolio(), 
																bus); 
			broker.getLiveQuotes(); 			
		}
		
		if(streamName.equals(StreamName.STANDBY)) {
		}

		if(	streamName.equals(StreamName.REALTIME)) {
			BrokerInteractive broker = new BrokerInteractive(	streamName, 
																InitMarket.getSamplePortfolio(), 
																bus); 
			broker.getLiveQuotes(); 			
		}

		if(streamName.equals(StreamName.HISTORY)) {
			BrokerInteractive broker = new BrokerInteractive(	streamName, bus); 
			broker.procesHistoricalQuotesRequests();
		}

		if(	streamName.equals(StreamName.STREAMING)) {
			// TODO: Do straming queries from SQS
		}

//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
	
	private static StreamName getArgument(String[] args) {
		if(args.length>0) {
			if(args[0]!=null) {
				String s0 = args[0];
				if(s0.equals("PRIMARY")) {
					return StreamName.PRIMARY;
				}
				if(s0.equals("STANDBY")) {
					return StreamName.STANDBY;
				}
				if(s0.equals("REALTIME")) {
					return StreamName.REALTIME;
				}
				if(s0.equals("HISTORY")) {
					return StreamName.HISTORY;
				}
				if(s0.equals("STREAMING")) {
					return StreamName.STREAMING;
				}
				else {
					Watchr.log(Level.SEVERE, "Invalid args[0], should be Investor StreamName");
				}
			} else {
		    	Watchr.log(Level.SEVERE, "ERROR: mode is a required argument");
			}
		}
		return null;
	}
	
	
}
