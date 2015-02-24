package com.onenow.investor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.onenow.broker.BrokerActivityImpl;
import com.onenow.broker.BrokerInteractive;
import com.onenow.finance.InvApproach;
import com.onenow.finance.StrategyIronCondor;
import com.onenow.finance.TradeRatio;
import com.onenow.finance.Underlying;

public class InvestorMain {
	
	
	public static void main(String[] args) throws ParseException, InterruptedException {

		Pucara ia58 = new Pucara();
		
		try {
			ia58 = new Pucara("SPX", "20150319");		
		} catch (Exception e) {
			System.out.println("COULD NOT CREATE INVESTOR\n");
			e.printStackTrace();
			return;
		}

		try {
			ia58.launch();
		} catch (Exception e) {
			System.out.println("COULD NOT EXECUTE INVESTOR\n");
			e.printStackTrace();
		}

	}
	
}
