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

		Underlying index = new Underlying("SPX");

		Pucara ia58;
		
		try {
			ia58 = new Pucara(index);		
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
