package com.onenow.finance;

import com.onenow.analyst.Chart;
import com.onenow.investor.Contract;

public class Investment {

	private Underlying under;
	private InvType invType;
	
	private Reward reward;
	private Risk risk;
	
	private Chart chart5min = new Chart();
	private Chart chart15min = new Chart();;
	private Chart chart60min = new Chart();;
	private Chart chart240min = new Chart();;
	private Chart chartDaily = new Chart();;
	private Chart chartWeekly = new Chart();;
	
	
	// CONSTRUCTOR
	public Investment() {
		// does not set underlying
	}

	public Investment(Underlying underlying, InvType invType) {
		setUnder(underlying);
		setInvType(invType);
		
		setReward(new Reward());
		setRisk(new Risk());
		
	}

	// PUBLIC
	public boolean equals(Investment inv) {
		boolean equal = false;
		if (inv.getUnder().getTicker().equals(getUnder().getTicker())) {
			// TODO add cases for options etc
			equal = true;
		}
		return equal;
	}

	// PRINT:
	public String toString() {
		String s = "";
		s = getUnder().toString() + " " + getInvType();
		return s;
	}
	
	// TEST
	public boolean test() {
		// TODO
		return true;
	}

	// SET GET
	public Underlying getUnder() {
		return under;
	}

	public void setUnder(Underlying under) {
		this.under = under;
	}

	public InvType getInvType() {
		return invType;
	}

	public void setInvType(InvType invTuype) {
		this.invType = invTuype;
	}

	public Reward getReward() {
		return reward;
	}

	private void setReward(Reward reward) {
		this.reward = reward;
	}

	public Risk getRisk() {
		return risk;
	}

	private void setRisk(Risk risk) {
		this.risk = risk;
	}

	public Chart getChart5min() {
		return chart5min;
	}

	public void setChart5min(Chart chart5min) {
		this.chart5min = chart5min;
	}

	public Chart getChart15min() {
		return chart15min;
	}

	public void setChart15min(Chart chart15min) {
		this.chart15min = chart15min;
	}

	public Chart getChart60min() {
		return chart60min;
	}

	public void setChart60min(Chart chart60min) {
		this.chart60min = chart60min;
	}

	public Chart getChart240min() {
		return chart240min;
	}

	public void setChart240min(Chart cahrt240min) {
		this.chart240min = cahrt240min;
	}

	public Chart getChartDaily() {
		return chartDaily;
	}

	public void setChartDaily(Chart chartDaily) {
		this.chartDaily = chartDaily;
	}

	public Chart getChartWeekly() {
		return chartWeekly;
	}

	public void setChartWeekly(Chart chartWeekly) {
		this.chartWeekly = chartWeekly;
	}

}
