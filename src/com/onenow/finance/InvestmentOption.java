package com.onenow.finance;
import java.util.Date;


public class InvestmentOption extends Investment {

	private	Enum optionType; // call, put	
	private Double strikePrice;
	private Date expirationDate;
	private int sharesPerContract=100;

	public InvestmentOption() {
		
	}
	
	public InvestmentOption(Underlying underlying, Enum optionType, Date expirationDate, Double strikePrice) {
		super(underlying, optionType);
		setStrikePrice(strikePrice);
		setExpirationDate(expirationDate);
		setOptionType(optionType);
	}
	
	public String toString() {
		String string = super.toString() + ".  Type " + getOptionType() + ".  Strike $" + getStrikePrice() 
						+ ".  Expires " + getExpirationDate();
		System.out.println("Investment Option: " + string);
		return string;
	}
	
	public Double getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(Double strikePrice) {
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
