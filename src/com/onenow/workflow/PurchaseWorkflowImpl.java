package com.onenow.workflow;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.onenow.broker.IBrokersActivityClient;
import com.onenow.broker.IBrokersActivityClientImpl;
import com.onenow.finance.Underlying;

public class PurchaseWorkflowImpl implements PurchaseWorkflow {

	private IBrokersActivityClient iBrokers = new IBrokersActivityClientImpl();
	private PurchaseWorkflowSelfClient selfClient = new PurchaseWorkflowSelfClientImpl();

	@Override
	public void mainFlow() {

		Promise<List<Underlying>> underList = getiBrokers().getUnderlying();
		printUnderList(underList);

		// // Trade trade = new Trade(
		// Date expDate = new Date();
		// expDate.setTime(1000000);
		// Investment stock = marketPortfolio.getBest(apl, InvType.STOCK);
		// Investment call = marketPortfolio.getBest(apl, InvType.CALL, expDate,
		// 405.00);
		// Investment put = marketPortfolio.getBest(apl, InvType.PUT, expDate,
		// 390.00);
		//
		// System.out.println(call.toString() + " @$ " +
		// marketPrices.getPriceBid(call));
		// put.toString();
		// stock.toString();
		//
		// TradeTransaction transaction = new TradeTransaction();
		// Trade trade = new Trade(call, TradeType.BUY, 100, marketPrices);
		// transaction.addTrade(trade);
		// System.out.println("1 call contract " + trade.getNetCost() + " " +
		// transaction.getNetCost());
		//
		// // get buy/sell price and act

	}

	@Asynchronous
	void printUnderList(Promise<List<Underlying>> underListPromise) {
		// The idea that, because this method was annotated as Asynchronous
		// at this point SWF says that the get() on a Promise will complete:
		// http://docs.aws.amazon.com/amazonswf/latest/awsflowguide/getting-started-example-helloworldworkflowasync.html
		// The very first paragraph.
		List<Underlying> underList = underListPromise.get();
		System.out.println("HELLO HELLO!");
		for (Underlying under : underList) {
			System.out.println(under.getTicker());
		}
		// Date expDate = new Date();
		// expDate.setTime(1000000);
		// Underlying apl = new Underlying("APL");
		// Investment stock = getiBrokers().getBest(apl, InvType.STOCK).get();
		// Investment call = getiBrokers().getBest(apl, InvType.CALL, expDate,
		// 405.00).get();
		// Investment put = getiBrokers().getBest(apl, InvType.PUT, expDate,
		// 390.00).get();
		//
		// System.out.println(call.toString() + " @$ " +
		// marketPrices.getPriceBid(call));
		// put.toString();
		// stock.toString();
		//
		// TradeTransaction transaction = new TradeTransaction();
		// Trade trade = new Trade(call, TradeType.BUY, 100, marketPrices);
		// transaction.addTrade(trade);
		// System.out.println("1 call contract " + trade.getNetCost() + " " +
		// transaction.getNetCost());
		//
		// // get buy/sell price and act

	}

	@Asynchronous
	private void printUnderList2(Promise<List<Underlying>> underListPromise) {
		List<Underlying> underList = underListPromise.get();
		for (Underlying under : underList) {
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
