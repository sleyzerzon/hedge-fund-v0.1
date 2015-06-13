package com.onenow.instrument;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import com.onenow.constant.InvType;
import com.onenow.constant.SamplingRate;
import com.onenow.data.ObjectOrigination;
import com.onenow.execution.Contract;
import com.onenow.research.Chart;
import com.onenow.risk.Reward;
import com.onenow.risk.Risk;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class Investment {

	private Underlying under;
	private InvType invType;
	
	private Reward reward;
	private Risk risk;
	
	private HashMap<SamplingRate, Chart> charts = new HashMap<SamplingRate, Chart>();
	
	public ObjectOrigination origin = new ObjectOrigination();
	
	
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
		
		s = origin.toString("INVESTMENT") + " " + 
			"-under " + getUnder().toString() + " " +
			"-type " + getInvType();
		
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

	public HashMap<SamplingRate, Chart> getCharts() {
		return charts;
	}

	public void setCharts(HashMap<SamplingRate, Chart> charts) {
		this.charts = charts;
	}

}
