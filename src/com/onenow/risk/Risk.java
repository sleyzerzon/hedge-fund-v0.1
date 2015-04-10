package com.onenow.risk;

public class Risk {
		
	public Risk() { // constructor for stocks
		
	}
	

	public Double probabilityOfProfit(Double DTRRR, Double VTRRR, Double RTRRR) { // options
		Double prob = 0.9;
		Double risk = 0.0;
		
		risk = DTRRR * VTRRR * RTRRR;
		
		return prob; // TODO what is the actual probability?
	}
	
	private Double probabilityOfProfit() { // stocks
		Double prob=0.0;
		// TODO
		return prob;
	}
	
	
}
