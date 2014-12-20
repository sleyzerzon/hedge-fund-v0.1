package com.onenow.exchange;

public class Investment {

	private Underlying stock;
	private Float offerPrice;
	
	public Underlying getStock() {
		return stock;
	}

	public void setStock(Underlying stock) {
		this.stock = stock;
	}

	public Float getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(Float offerPrice) {
		this.offerPrice = offerPrice;
	}

}
