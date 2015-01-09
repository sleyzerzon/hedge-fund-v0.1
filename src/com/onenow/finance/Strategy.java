package com.onenow.finance;

public class Strategy {
	
	private Transaction transaction;
	
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
	
	
	// CONSTRUCTOR
	public Strategy() {
		setTransaction(new Transaction());
	}
	
	// PUBLIC	
	public Double getNetValue(Double marketPrice) {
		Double net = 0.0;
		for(Trade trade:getTransaction().getTrades()) {
			Double price = trade.getNetCost();
			Double value = trade.getValue(marketPrice);
			net += price+value;
		}
		// System.out.println("NET VALUE ($" + marketPrice + "): $"+ net);
		return net;
	}
	
	public boolean isCreditStrategy() {
		boolean isCredit=false;
		// TODO
		return isCredit;
	}
	
	public boolean isDebitStrategy() {
		boolean isDebit=false;
		// TODO
		return isDebit;
	}
	
	public String toString(){
		String s = "end strategy...";
		return(s);
	}


	// PRIVATE


	// SET GET
	public Transaction getTransaction() {
		return transaction;
	}

	private void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}


}
