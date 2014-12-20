package com.onenow.exchange;
import java.util.Date;


public class InvestmentOption extends Investment {
	
	private Float strikePrice;
	private Date expirationDate;
	private	Enum optionType; // call, put
	
	private int sharesPerContract=100;

	public Float getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(Float strikePrice) {
		this.strikePrice = strikePrice;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Enum getOptionType() {
		return optionType;
	}

	public void setOptionType(Enum optionType) {
		this.optionType = optionType;
	}

	public int getSharesPerContract() {
		return sharesPerContract;
	}

	public void setSharesPerContract(int sharesPerContract) {
		this.sharesPerContract = sharesPerContract;
	}

}
