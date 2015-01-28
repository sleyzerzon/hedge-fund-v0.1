package com.onenow.investor;

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

		BrokerActivityClient broker = getBroker();
		Promise<List<Underlying>> underlyingPromise = broker.getUnderlying();

		Promise<List<Investment>> investmentPromise = getBroker().getInvestments(false); // overall market

		doTrades(underlyingPromise);
	}

	@Asynchronous
	private Promise<Trade> doBuy(Promise<Investment> invPromise, int quantity,
			Promise<Double> pricePromise) {
		System.out.println("In doBuy()");
		Trade trade = new Trade(invPromise.get(), TradeType.BUY, 50,
				pricePromise.get());
		return Promise.asPromise(trade);
	}

	@Asynchronous
	private void doTrades(Promise<List<Underlying>> listPromise) {
		System.out.println("In doTrades()");
		List<Underlying> unders = listPromise.get();
//		System.out.println("RECEIVED UNDELYING LIST: " + unders.toString());
		
		
		Underlying under1 = unders.get(0);
//		System.out.println("RECEIVED UNDELYING: " + under1.toString());

		Date expDate = new Date();
		expDate.setTime(1000000);
		Promise<Investment> stockPromise = getBroker().getBest(under1, InvType.STOCK);
//		Promise<Investment> call1Promise = getBroker().getBest(under1,
//				InvType.CALL, expDate, 405.00);
//		Promise<Investment> call2Promise = getBroker().getBest(under1,
//				InvType.CALL, expDate, 400.00);
//		Promise<Investment> put1Promise = getBroker().getBest(under1,
//				InvType.PUT, expDate, 390.00);
//		Promise<Investment> put2Promise = getBroker().getBest(under1,
//				InvType.PUT, expDate, 385.00);

		// find suitable investment
		Promise<Double> stockPricePromise = getBroker().getPriceAsk(stockPromise);
		
//		Promise<Double> call1PricePromise = getBroker().getPriceAsk(
//				call1Promise);
//		Promise<Double> call2PricePromise = getBroker().getPriceAsk(
//				call2Promise);
//		Promise<Double> put1PricePromise = getBroker().getPriceAsk(put1Promise);
//		Promise<Double> put2PricePromise = getBroker().getPriceAsk(put2Promise);

		Promise<Trade> tradeStock = doBuy(stockPromise, 50, stockPricePromise);

//		Promise<Trade> tradeCall1 = doBuy(call1Promise, 50, call1PricePromise);
//
//		Promise<Trade> tradeCall2 = doBuy(call2Promise, 50, call2PricePromise);
//
//		Promise<Trade> tradePut1 = doBuy(put1Promise, 50, put1PricePromise);
//		Promise<Trade> tradePut2 = doBuy(put2Promise, 50, put2PricePromise);

//		doTransaction(tradeStock, tradeCall1, tradeCall2, tradePut1, tradePut2);
		doTransaction(tradeStock);
		Promise<List<Trade>> tradesPromise = getBroker().getTrades();
		processTrades(tradesPromise);

		Promise<List<Investment>> myInvPromise = getBroker().getInvestments(
				true);
//		List<Investment> myInv = getInvestments(myInvPromise);
		//
		// System.out.println("Transaction Net:" + tx.getNetCost());
		// System.out.println("Transaction Call Net:"
		// + tx.getNetCost(InvType.CALL));
		// System.out.println("Transaction Put Net:" +
		// tx.getNetCost(InvType.PUT));
		//
		// System.out.println("Ended workflow.");


//		System.out.println("Transaction Net:" + tx.getNetCost());
//		System.out.println("Transaction Call Net:"
//				+ tx.getNetCost(InvType.CALL));
//		System.out.println("Transaction Put Net:" + tx.getNetCost(InvType.PUT));
//
//		System.out.println("Ended workflow.");
	}

	@Asynchronous
	private void processTrades(Promise<List<Trade>> tradesPromise) {

		System.out.println("In processTrades()");
		List<Trade> trades = tradesPromise.get();
		for (Trade trade : trades) {
			System.out.println(trade);
		}

	}

	@Asynchronous
	private void doTransaction(Promise<Trade>... tradePromises) {
		System.out.println("In doTransaction()");
		Trade[] trades = new Trade[tradePromises.length];
		for (int i = 0; i < tradePromises.length; i++) {
			trades[i] = tradePromises[i].get();
		}
		Transaction tx = new Transaction(trades);
		getBroker().enterTransaction(tx);
		Promise<List<Trade>> tradesPromise = getBroker().getTrades();
		processTrades(tradesPromise);
	}

	@Asynchronous
	List<Investment> getInvestments(Promise<List<Investment>> listPromise) {
		List<Investment> theList = new ArrayList<Investment>();
		theList = listPromise.get();
		for (Investment inv : theList) {
			System.out.println("Investment listed: " + inv.toString());
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
	public Investment getBest(Underlying under, Enum invType, Enum InvTerm) { // Reserved
		Investment inv = new Investment();
//		inv = getBroker().getBest(under, invType, InvTerm).get();
		return inv;
	}

	@Asynchronous
	private Investment getBest(Underlying under, InvType invType, Date expDate,
			Double strike) { // options
		Investment inv = new Investment();
		try {
//			inv = getBroker().getBest(under, invType, expDate, strike).get();
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
