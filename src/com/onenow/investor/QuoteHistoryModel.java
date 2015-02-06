package com.onenow.investor;

import java.util.List;

import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;

public class QuoteHistoryModel {
	
	private InvestorController controller;
	private QuoteBar panel;

	private List<Channel> channels;
	
	public QuoteHistoryModel() {
	
	}

	public QuoteHistoryModel(InvestorController cont, List<Channel> channels) {
		setController(cont);
		setChannels(channels);
		
	}
	
	void addContract(Contract cont, 
					 String end, int durations, DurationUnit durUnit,
					 BarSize size, WhatToShow show,
					 boolean rth) {
	
		panel = new QuoteBar(channels);
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

	private List<Channel> getChannels() {
		return channels;
	}

	private void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
	
	

	
}
	
	
	
	
	
	
	
	
	
