package com.onenow.main;

import java.util.ArrayList;
import java.util.List;

import com.onenow.data.Historian;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.util.ParseDate;

/** 
 * Gather complete accurate historical market data
 * @param args
 */

public class HistorianMain {

	private static BrokerInteractive IB;
	private Historian historian;

	public static void main(String[] args) {
		
		// get a controller for interactive brokers
		try {
			
			IB = new BrokerInteractive(); // create Interactive Brokers broker & get quotes
			
			Historian hist = new Historian(IB);
			hist.updateHistory();
			
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INTERACTIVE BROKER\n");
			e.printStackTrace();
		}

		
//		// get channel price history
//		try {
//			IB.getChannelPrices();
//		} catch (InterruptedException e1) {
//			System.out.println("COULD NOT CREATE GET PRICE HISTORY\n");
//			e1.printStackTrace();
//		}	
	}
	
}
