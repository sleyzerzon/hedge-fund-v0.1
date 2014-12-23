package com.onenow.brokerage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onenow.orchestrator.InvType;
import com.onenow.orchestrator.Investment;
import com.onenow.orchestrator.InvestmentOption;
import com.onenow.orchestrator.InvestmentStock;
import com.onenow.orchestrator.MarketPrice;
import com.onenow.orchestrator.Portfolio;
import com.onenow.orchestrator.Underlying;

public class InteractiveBrokersImpl implements InteractiveBrokers{

	static Portfolio marketPortfolio = new Portfolio();
	static MarketPrice marketPrices = new MarketPrice();

	
	@Override
	public List<Underlying> getUnderlying() {
		
		List<Underlying> underList = new ArrayList<Underlying>();
		
		Underlying apl = new Underlying("APL");
		Underlying intc = new Underlying("INTC");
		Underlying rus = new Underlying("RUS");
		Underlying amzn = new Underlying("AMZN");

		underList.add(apl);
		underList.add(intc);
		underList.add(rus);
		underList.add(amzn);
		
		return underList;
	}
	
	@Override
	public Double getPrice() {
		Double price = 0.0;
		
		return price;	
	}
	
	private void setPriceAll() {
		for(Underlying under : getUnderlying()) {
			setPrice(under);
		}
	}
	
	private static void setPrice(Underlying under) {

		Date expDate = new Date();
		expDate.setTime(1000000);

		Investment stock = new InvestmentStock(under);
		Investment call1 = new InvestmentOption(under, InvType.CALL, expDate, 405.00);
		Investment call2 = new InvestmentOption(under, InvType.CALL, expDate, 400.00);
		Investment put1 = new InvestmentOption(under, InvType.PUT, expDate, 390.00);
		Investment put2 = new InvestmentOption(under, InvType.PUT, expDate, 385.00);

		getMarketPortfolio().addInvestment(stock);
		getMarketPortfolio().addInvestment(call1);
		getMarketPortfolio().addInvestment(call2);
		getMarketPortfolio().addInvestment(put1);
		getMarketPortfolio().addInvestment(put2);
		
		getMarketPrices().setInvestmentPrice(stock, 396.00, 395.00);
		getMarketPrices().setInvestmentPrice(call1, 7.41, 7.40);
		getMarketPrices().setInvestmentPrice(call2, 8.85, 8.84);
		getMarketPrices().setInvestmentPrice(put1, 9.50, 9.49);
		getMarketPrices().setInvestmentPrice(put2, 8.33, 8.32);
		
		getMarketPortfolio().toString();
		
	}

	private static Portfolio getMarketPortfolio() {
		return marketPortfolio;
	}

	private void setMarketPortfolio(Portfolio marketPortfolio) {
		this.marketPortfolio = marketPortfolio;
	}

	private static MarketPrice getMarketPrices() {
		return marketPrices;
	}

	private void setMarketPrices(MarketPrice marketPrices) {
		this.marketPrices = marketPrices;
	}
	
	
	
	
}


