package com.onenow.finance;

import java.util.Hashtable;

public class Reward {

	private Hashtable<Enum, Double> reward;
	
	public Reward() {
		
	}
	
	public Reward(Double probOfProfit, Double maxROI) {
		setReward(new Hashtable<Enum, Double>());
		calculateReward(probOfProfit, maxROI);
	}
	
	// PUBLIC
	public Double getAlgoOrder(Enum rewardAlgo) {
		Double order = 0.0;
		order = getReward().get(rewardAlgo);
		return order;
	}
	
	// PRIVATE
	private void calculateReward(Double probOfProfit, Double maxROI) {
		getReward().put(RewardAlgo.Linear, linearBiasAlgo(probOfProfit, maxROI));
		getReward().put(RewardAlgo.Success, successBiasAlgo(probOfProfit, maxROI));
		getReward().put(RewardAlgo.ROI, ROIBiasAlgo(probOfProfit, maxROI));
	}

	public Double linearBiasAlgo(Double probOfProfit, Double maxROI) {
		Double order=probOfProfit*maxROI;
		return order;
	}

	public Double successBiasAlgo(Double probOfProfit, Double maxROI) {
		Double order=Math.pow(probOfProfit, 1.2)*maxROI;
		return order;
	}

	public Double ROIBiasAlgo(Double probOfProfit, Double maxROI) {
		Double order=probOfProfit*Math.pow(maxROI, 1.2);
		return order;
	}

	
	// PRINT
	
	// SET GET
	private Hashtable<Enum, Double> getReward() {
		return reward;
	}

	private void setReward(Hashtable<Enum, Double> reward) {
		this.reward = reward;
	}

	// APPROACH: Iron Condor
	// 1) LOOK FOR THE STRIKE SPREAD TO BE SMALLER THAN THE PRICING GAP
	// 2) FOR MAX NETom: Pcs+Pcb >> Pcs+Ppb
	// P: price
	// S: strike
	// c: call
	// p: put
	// b: bought
	// s: sold
	// om: out of money
	// im: in the money
	//
	// NETom = 100x(Pcs+Pps-Pcb-Ppb)  ... @start, out of money
	//
	// out of money--
	// NETcom = 100x(Pcs-Pcb) ... P<Scs
	// NETpom = 100x(Pps-Ppb) ... P>Sps
	//
	// in the money, calls--
	// NETcb = -100xPcb + 100x(P-Scb)
	// NETcs = 100xPcs + 100x(Scs-P)
	//
	// to avoid loss--
	// NETcb+NETcs > 0
	// thus,
	// 100x(Pcs-Pcb+Scs-Scb) > 0
	// in other words,
	// Pcs-Pcb > Scb-Scs	****** SEARCH FOR THIS: calls ******
	// Pps-Ppb > Sps-Spb   ****** SEARCH FOR THIS: puts ******

	
	// decide which of the 4 spreads to use
	// start with credit ones for leverage
	// We will look for a call spread that has a good ratio of cost to potential profit, usually 1:3 or greater.
	// resistance points? expecting news?


}
