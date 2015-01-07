package com.onenow.finance;

public class Investment {

	private Underlying under;
	private InvType invType;

	// CONSTRUCTOR
	public Investment() {
		// does not set underlying
	}

	public Investment(Underlying underlying, InvType invType) {
		setUnder(underlying);
		setInvType(invType);
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
		String string = getUnder().toString() + " " + getInvType();
		return string;
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

}
