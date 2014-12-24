package com.onenow.broker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.InvestmentStock;
import com.onenow.finance.MarketPrice;
import com.onenow.finance.Portfolio;
import com.onenow.finance.TradeType;
import com.onenow.finance.Underlying;

public class IBrokersActivityImpl implements IBrokersActivity{

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
	public Double getAskPrice(Investment inv) {
		setPriceAll();
		return(getMarketPrices().getPriceAsk(inv, TradeType.SELL));			
	}

	@Override
	public Double getBidPrice(Investment inv) {
		setPriceAll();
		return(getMarketPrices().getPriceAsk(inv, TradeType.BUY));			
	}

	private void setPriceAll() {
		for(Underlying under : getUnderlying()) {
			setUnderlyingPrice(under);
		}
	}
	
	private static void setUnderlyingPrice(Underlying under) {

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
		
		getMarketPrices().setPrice(stock, 396.00, 395.00);
		getMarketPrices().setPrice(call1, 7.41, 7.40);
		getMarketPrices().setPrice(call2, 8.85, 8.84);
		getMarketPrices().setPrice(put1, 9.50, 9.49);
		getMarketPrices().setPrice(put2, 8.33, 8.32);
		
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


