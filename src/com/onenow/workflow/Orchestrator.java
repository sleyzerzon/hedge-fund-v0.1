package com.onenow.workflow;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeTransaction;
import com.onenow.finance.TradeType;

public class Orchestrator {

	public static void main(String[] args) {
				
		// Trade trade = new Trade(
		Date expDate = new Date();
		expDate.setTime(1000000);
		Investment stock = marketPortfolio.getBest(apl, InvType.STOCK);
		Investment call = marketPortfolio.getBest(apl, InvType.CALL, expDate, 405.00);
		Investment put = marketPortfolio.getBest(apl, InvType.PUT, expDate, 390.00);
		
		System.out.println(call.toString() + " @$ " + marketPrices.getBuyPrice(call));
		put.toString();
		stock.toString();

		TradeTransaction transaction = new TradeTransaction();
		Trade trade = new Trade(call, TradeType.BUY, 100, marketPrices);
		transaction.addTrade(trade);
		System.out.println("1 call contract " + trade.getNetCost() + " " + transaction.getNetCost());
		
		// get buy/sell price and act
		
	}
	
}
