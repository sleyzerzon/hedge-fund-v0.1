package com.onenow.main;

import java.util.logging.Level;

import com.onenow.constant.InvestorRole;
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
		
		InvestorRole role = getArgument(args);
		InitLogger.run(role.toString());
		Watchr.log(Level.INFO, "Starting with ROLE: " + role);

		BusWallStInteractiveBrokers bus = new BusWallStInteractiveBrokers(role);

		// Kinesis.selfTest();


		// LIVE QUOTES
		if(role.equals(InvestorRole.PRIMARY)) {
			BrokerInteractive broker = new BrokerInteractive(	role, 
																InitMarket.getPrimaryPortfolio(), 
																bus); 
			broker.getLiveData(); 			
		}	
		if(role.equals(InvestorRole.REALTIME)) {
			BrokerInteractive broker = new BrokerInteractive(	role, 
																InitMarket.getRealtimePortfolio(), 
																bus); 
			broker.getLiveData(); 			
		}
		if(role.equals(InvestorRole.STANDBY)) {
			// TODO: passive role on same investments as primary
		}

		// HISTORIC QUOTES
		if(role.equals(InvestorRole.HISTORY)) {
			BrokerInteractive broker = new BrokerInteractive(	role, bus); 
			broker.getHistoricalData();
		}

		
//		PortfolioFactory portfolioFactory = new PortfolioFactory(broker, marketPortfolio);
//		portfolioFactory.launch();							

	}
	
	private static InvestorRole getArgument(String[] args) {
		
		InvestorRole investorRole = InvestorRole.REALTIME;
		
		if(args.length>0) {
			if(args[0]!=null) {
				if(args[0].equals(InvestorRole.HISTORY.toString())) {
					investorRole = InvestorRole.HISTORY;
				}
				if(args[0].equals(InvestorRole.PRIMARY.toString())) {
					investorRole = InvestorRole.PRIMARY;
				}
				if(args[0].equals(InvestorRole.STANDBY.toString())) {
					investorRole = InvestorRole.STANDBY;
				}
			} 
		} else {
	    	Watchr.log(Level.SEVERE, "ERROR: investor role is a required as a java process argument");
		}
		
		return investorRole;
	}
	
	
}
