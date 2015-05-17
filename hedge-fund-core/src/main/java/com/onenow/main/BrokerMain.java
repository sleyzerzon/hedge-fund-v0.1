package com.onenow.main;

import java.util.List;

import com.onenow.data.InitMarket;
import com.onenow.data.InvestmentList;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.ParseDate;

public class BrokerMain {

	private static BrokerInteractive IB;
	private static BrokerActivityImpl broker;

	private static InvestmentList invList = new InvestmentList();
	private static ParseDate parseDate = new ParseDate();

	/**
	 * The principal process for all interactions with Wall Street and gateways thereof
	 * @param args
	 */
	public static void main(String[] args) {

		// choose investments
		Portfolio marketPortfolio = new Portfolio();
	    List<Underlying> stocks = invList.getUnderlying(invList.someStocks);
	    List<Underlying> indices = invList.getUnderlying(invList.someIndices);
	    List<Underlying> futures = invList.getUnderlying(invList.futures);
	    List<Underlying> options = invList.getUnderlying(invList.options);
	    String fromDate = parseDate.getDashedToday();
	    String toDate = parseDate.getDashedToday();

	    // fill the market portfolio
	    InitMarket initMarket = new InitMarket(	marketPortfolio, 
	    										stocks, indices,
	    										futures, options,
	    										fromDate, toDate);

		// create Interactive Brokers broker & start getting quotes
		try {
			setIB(new BrokerInteractive(marketPortfolio)); 
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INTERACTIVE BROKER\n");
			e.printStackTrace();
		}
		
		
		// set the overall broker: for when there are multiple brokers
		try {
			setBroker(new BrokerActivityImpl(getIB()));  
		} catch (Exception e) {
			System.out.println("COULD NOT SET MASTER BROKER\n");
			e.printStackTrace();
		}
		
	}

	// SET GET
	private static BrokerInteractive getIB() {
		return IB;
	}

	private static void setIB(BrokerInteractive iB) {
		IB = iB;
	}

	private static BrokerActivityImpl getBroker() {
		return broker;
	}

	private static void setBroker(BrokerActivityImpl broker) {
		BrokerMain.broker = broker;
	}
}
