package com.onenow.investor;

import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.InvestmentIndex;
import com.onenow.finance.InvestmentOption;
import com.onenow.finance.Portfolio;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;


public class InitMarket {

	Portfolio marketPortfolio;
	
	
	public InitMarket() {
		
	}
	
	public InitMarket(Portfolio portfolio) {
		setMarketPortfolio(portfolio);
		initMarket();
	}
	
	private void initMarket() { // create the investments
		String expDate="20150326";

		String spx="SPX";
		Integer seedSPX=2100;		
		setIndexAndOptions(spx, expDate, seedSPX);
		
		String ndx="NDX";
		Integer seedNDX=4450;		
		setIndexAndOptions(ndx, expDate, seedNDX);

		String rut="RUT";
		Integer seedRUT=1350;		
		setIndexAndOptions(rut, expDate, seedRUT);

		System.out.println(getMarketPortfolio().toString());		
	}

	private void setIndexAndOptions(String name, String expDate, Integer seed) {
		Underlying under = new Underlying(name);
		setIndex(under);		
		setOptions(under, expDate, seed);
	}

	private void setOptions(Underlying under, String expDate, Integer seed) {
		for (Double strike=(double) (seed-200); strike<(seed+200); strike=strike+5) {
			Investment call = new InvestmentOption(under, InvType.CALL, expDate, strike);
			Investment put = new InvestmentOption(under, InvType.PUT, expDate, strike);
			Trade callTrade = new Trade(call, TradeType.BUY, 1, 0.0);
			Trade putTrade = new Trade(call, TradeType.BUY, 1, 0.0);
			Transaction optTrans = new Transaction(callTrade, putTrade);
			getMarketPortfolio().enterTransaction(optTrans);
		}
	}

	private void setIndex(Underlying under) {
		InvestmentIndex index = new InvestmentIndex(under);
		Trade indexTrade = new Trade(index, TradeType.BUY, 1, 0.0);
		Transaction indexTrans = new Transaction(indexTrade);
		getMarketPortfolio().enterTransaction(indexTrans);
	}

	public Portfolio getMarketPortfolio() {
		return marketPortfolio;
	}

	private void setMarketPortfolio(Portfolio marketPortfolio) {
		this.marketPortfolio = marketPortfolio;
	}


}
