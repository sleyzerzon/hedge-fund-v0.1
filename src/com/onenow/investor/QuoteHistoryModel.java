package com.onenow.investor;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.onenow.investor.InvestorController.IHistoricalDataHandler;

public class QuoteHistoryModel {
	
	private InvestorController controller;
	private QuoteBar panel;
	
	public QuoteHistoryModel() {
	
	}

	public QuoteHistoryModel(InvestorController cont) {
		setController(cont);
	}

	void addContract(Contract cont, 
					 String end, int durations, DurationUnit durUnit,
					 BarSize size, WhatToShow show,
					 boolean rth) {
		
		panel = new QuoteBar();
		getController().reqHistoricalData(cont, end, durations, durUnit,
											size, show, rth,
											panel);
	}
	

	// PRINT
	public String toString() {
		String s="";
		return s;
	}

	// TEST
	
	// SET GET
	private InvestorController getController() {
		return controller;
	}

	private void setController(InvestorController controller) {
		this.controller = controller;
	}
	
	
}
	
	
	
	
	
	
	
	
	
