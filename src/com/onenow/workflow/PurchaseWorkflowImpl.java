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

public class PurchaseWorkflowImpl implements PurchaseWorkflow {

	private IBrokersActivityClient iBrokers = new IBrokersActivityClientImpl();
	private PurchaseWorkflowSelfClient selfClient = new PurchaseWorkflowSelfClientImpl();

	@Override
	public void mainFlow() {
	
				
		Promise<List<Underlying>> underList = getiBrokers().getUnderlying();
		printUnderList(underList);	
		
//		Date expDate = new Date();
//		expDate.setTime(1000000);
//		Underlying apl = new Underlying("APL");
//		Investment stock = getiBrokers().getBest(apl, InvType.STOCK).get();
//		Investment call = getiBrokers().getBest(apl, InvType.CALL, expDate, 405.00).get();
//		Investment put = getiBrokers().getBest(apl, InvType.PUT, expDate, 390.00).get();
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
	
	@Asynchronous
	private void printUnderList(Promise<List<Underlying>> underList) {
		for(Underlying under:underList.get()) {
			String ticker = under.getTicker();
			System.out.println("Ticker listed: " + ticker);
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
