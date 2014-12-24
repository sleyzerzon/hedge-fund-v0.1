package com.onenow.workflow;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.onenow.broker.IBrokersActivityClient;
import com.onenow.broker.IBrokersActivityClientImpl;
import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeTransaction;
import com.onenow.finance.TradeType;
import com.onenow.finance.Underlying;
import com.onenow.salesforce.SForceActivityClient;
import com.onenow.salesforce.SForceActivityClientImpl;
import com.sforce.soap.enterprise.sobject.Cloud__c;

public class PurchaseWorkfowImpl implements PurchaseWorkflow {

	private IBrokersActivityClient iBrokers = new IBrokersActivityClientImpl();
	private PurchaseWorkflowSelfClient selfClient = new PurchaseWorkflowSelfClientImpl();

	@Override
	public void mainFlow() {
	
				
		Promise<List<Underlying>> underList = getiBrokers().getUnderlying();
		printUnderList(underList);
		
//		// Trade trade = new Trade(
//		Date expDate = new Date();
//		expDate.setTime(1000000);
//		Investment stock = marketPortfolio.getBest(apl, InvType.STOCK);
//		Investment call = marketPortfolio.getBest(apl, InvType.CALL, expDate, 405.00);
//		Investment put = marketPortfolio.getBest(apl, InvType.PUT, expDate, 390.00);
//		
//		System.out.println(call.toString() + " @$ " + marketPrices.getPriceBid(call));
//		put.toString();
//		stock.toString();
//
//		TradeTransaction transaction = new TradeTransaction();
//		Trade trade = new Trade(call, TradeType.BUY, 100, marketPrices);
//		transaction.addTrade(trade);
//		System.out.println("1 call contract " + trade.getNetCost() + " " + transaction.getNetCost());
//		
//		// get buy/sell price and act
		
	}
	
	private void printUnderList(Promise<List<Underlying>> underList) {
		for(Underlying under:underList) {
			under.getTicker();
		}
	}

	private IBrokersActivityClient getiBrokers() {
		return iBrokers;
	}

	private void setiBrokers(IBrokersActivityClient iBrokers) {
		this.iBrokers = iBrokers;
	}

	private PurchaseWorkflowSelfClient getSelfClient() {
		return selfClient;
	}

	private void setSelfClient(PurchaseWorkflowSelfClient selfClient) {
		this.selfClient = selfClient;
	}
	
}

// @Asynchronous
//