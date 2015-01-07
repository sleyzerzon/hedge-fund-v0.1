package com.onenow.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.onenow.broker.BrokerActivityClient;
import com.onenow.broker.BrokerActivityClientImpl;
import com.onenow.finance.InvType;
import com.onenow.finance.Investment;
import com.onenow.finance.Trade;
import com.onenow.finance.TradeType;
import com.onenow.finance.Transaction;
import com.onenow.finance.Underlying;

public class PurchaseWorkflowImpl implements PurchaseWorkflow {

	private PurchaseWorkflowSelfClient selfClient = new PurchaseWorkflowSelfClientImpl();
	private BrokerActivityClient broker = new BrokerActivityClientImpl();

	@Override
	public void mainFlow() {

		System.out.println("Started workflow.");
		System.out.println("Started workflow 2.");

		// get underlying
		BrokerActivityClient broker = getBroker();
		Promise<List<Underlying>> underlyingPromise = broker.getUnderlying();
		Promise<List<Underlying>> underlying = getUnderList(underlyingPromise);

		Promise<List<Investment>> mktInvPromise = getBroker().getInvestments(
				false); // overall market
		List<Investment> market = getInvestments(mktInvPromise);

		// find suitable investment
		Date expDate = new Date();
		expDate.setTime(1000000);
		Investment stock = getBest(underlying.get(0), InvType.STOCK);
		Investment call1 = getBest(underlying.get(0), InvType.CALL, expDate,
				405.00);
		Investment call2 = getBest(underlying.get(0), InvType.CALL, expDate,
				400.00);
		Investment put1 = getBest(underlying.get(0), InvType.PUT, expDate,
				390.00);
		Investment put2 = getBest(underlying.get(0), InvType.PUT, expDate,
				385.00);

		// buy something
		Trade tradeStock = new Trade(stock, TradeType.BUY, 50,
				getPriceAsk(stock));
		Trade tradeCall1 = new Trade(call1, TradeType.BUY, 50,
				getPriceAsk(call1));
		Trade tradeCall2 = new Trade(call2, TradeType.BUY, 50,
				getPriceAsk(call2));
		Trade tradePut1 = new Trade(put1, TradeType.BUY, 50, getPriceAsk(put1));
		Trade tradePut2 = new Trade(put2, TradeType.BUY, 50, getPriceAsk(put2));

		Transaction tx = new Transaction(tradeCall1, tradeCall2, tradePut1,
				tradePut2);
		getBroker().addTrade(tx);

		Promise<List<Trade>> tradesPromise = getBroker().getTrades();
		List<Trade> trades = getTrades(tradesPromise);

		Promise<List<Investment>> myInvPromise = getBroker().getInvestments(
				true);
		List<Investment> myInv = getInvestments(myInvPromise);

		System.out.println("Transaction Net:" + tx.getNetCost());
		System.out.println("Transaction Call Net:"
				+ tx.getNetCost(InvType.CALL));
		System.out.println("Transaction Put Net:" + tx.getNetCost(InvType.PUT));

		System.out.println("Ended workflow.");
	}

	@Asynchronous
	private Promise<List<Underlying>> getUnderList(Promise<List<Underlying>> listPromise) {
		List<Underlying> theList = new ArrayList<Underlying>();
		System.out.println("Called getUnderList.");
		theList = listPromise.get();
		theList.toString();
		for (Underlying under : theList) {
			System.out.println("Ticker listed: " + under.getTicker());
		}

		return Promise.asPromise(theList);
	}

	@Asynchronous
	List<Investment> getInvestments(Promise<List<Investment>> listPromise) {
		List<Investment> theList = new ArrayList<Investment>();
		try {
			theList = listPromise.get();
			for (Investment inv : theList) {
				System.out.println("Investment listed: " + inv.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theList;
	}

	@Asynchronous
	public List<Trade> getTrades(Promise<List<Trade>> tradesPromise) {
		List<Trade> trades = new ArrayList<Trade>();
		try {
			trades = getBroker().getTrades().get();
			for (Trade trade : trades) {
				System.out.println("Trade: " + trade.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return trades;
	}

	@Asynchronous
	public Double getPriceAsk(Investment inv) {
		Double number = 0.0;
		try {
			number = getBroker().getPriceAsk(inv).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}

	@Asynchronous
	public Double getPriceBid(Investment inv) {
		Double number = 0.0;
		try {
			number = getBroker().getPriceBid(inv).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}

	@Asynchronous
	private Investment getBest(Underlying under, InvType invType) { // stock &
																	// spot/on-demand
		Investment inv = new Investment();
		try {
			inv = getBroker().getBest(under, invType).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inv;
	}

	@Asynchronous
	public Investment getBest(Underlying under, Enum invType, Enum InvTerm) { // Reserved
		Investment inv = new Investment();
		inv = getBroker().getBest(under, invType, InvTerm).get();
		return inv;
	}

	@Asynchronous
	private Investment getBest(Underlying under, InvType invType, Date expDate,
			Double strike) { // options
		Investment inv = new Investment();
		try {
			inv = getBroker().getBest(under, invType, expDate, strike).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inv;
	}

	// SET GET
	private PurchaseWorkflowSelfClient getSelfClient() {
		return selfClient;
	}

	private void setSelfClient(PurchaseWorkflowSelfClient selfClient) {
		this.selfClient = selfClient;
	}

	private BrokerActivityClient getBroker() {
		return broker;
	}

	private void setBroker(BrokerActivityClient broker) {
		this.broker = broker;
	}

}
