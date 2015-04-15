package com.onenow.portfolio;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.onenow.constant.InvApproach;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;

public class InvestorMain {
		
	public static void main(String[] args) throws ParseException, InterruptedException {

		// TODO: test invest in only SPX-related instruments
		Underlying index = new Underlying("SPX");				

		PortfolioFactory portfolioFactory;
		
		try {
			System.out.println("TRYING TO CREATE INVESTOR");
			portfolioFactory = new PortfolioFactory(index);		// create it
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INVESTOR\n");
			e.printStackTrace();
			return;
		}
		

		try {
			System.out.println("TRYING TO LAUNCH INVESTOR");
			portfolioFactory.launch();							// launch it
		} catch (Exception e) {
			System.out.println("COULD NOT EXECUTE INVESTOR\n");
			e.printStackTrace();
		}

	}
	
}
