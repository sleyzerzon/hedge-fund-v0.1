package com.onenow.finance;

public class Reward {

	public Reward() {		
	}
	// decide which of the 4 spreads to use
	// start with credit ones for leverage
	// We will look for a call spread that has a good ratio of cost to potential profit, usually 1:3 or greater.
	// resistance points? expecting news?

	
	public Double linearBias(Double probOfProfit, Double maxROI) {
		Double order=probOfProfit*maxROI;
		return order;
	}

	public Double successBias(Double probOfProfit, Double maxROI) {
		Double order=Math.pow(probOfProfit, 1.2)*maxROI;
		return order;
	}

	public Double ROIBias(Double probOfProfit, Double maxROI) {
		Double order=probOfProfit*Math.pow(maxROI, 1.2);
		return order;
	}

}
