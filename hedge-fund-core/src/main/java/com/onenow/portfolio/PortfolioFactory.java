package com.onenow.portfolio;

import java.util.ArrayList;
import java.util.List;

import com.onenow.alpha.Broker;
import com.onenow.constant.InvApproach;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.Channel;
import com.onenow.data.InitMarket;
import com.onenow.data.MarketPrice;
import com.onenow.data.DataSampling;
import com.onenow.execution.BrokerActivityImpl;
import com.onenow.execution.BrokerInteractive;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.Underlying;
import com.onenow.research.Candle;
import com.onenow.research.Chart;
import com.onenow.util.ParseTime;

public class PortfolioFactory {
	
	private Broker 			broker;
	private Portfolio 		marketPortfolio;
	private MarketPrice 	marketPrice;
	private Underlying 		index;

	private DataSampling sampling = new DataSampling();
	static List<String> samplingRate = new ArrayList<String>();
	
	public PortfolioFactory() {
		
	}
	
	public PortfolioFactory(Broker broker, Portfolio marketPortfolio) {
		this.broker = broker;
		this.marketPortfolio = marketPortfolio;
		this.marketPrice = new MarketPrice(getMarketPortfolio(), broker);		
	}	
	
	public void launch() {

		while(true) {							// In Real-Time Constantly		
			getUptodateInvestmentCharts();
			analyzeUptodateInvestmentCharts();
			
			//***	 Look for signals, particularly at resitance & support
			//***	 Confirm via price, volume, and momentum
			//***	 Become familiar with the rythm of the underlying
			EntranceExitDecisioning decisioning = new EntranceExitDecisioning(getIndex());

			if(decisioning.EnterNowAtBottom()) {
				// goLong(getIndex());
			}

			if(decisioning.EnterNowAtTop()) {
				// goShort(getIndex());
			}
			
			System.out.println(",,,,,");
			// Thread.sleep(50000);
		}
	}
	
	// LONG AND SHORT
	public void goLong(Underlying under) {
		String expDate = "20150319"; // TODO: generate dynamically
		PortfolioAction spxExocet = new PortfolioAction(100, under, expDate, broker);
		StrategyCallBuy swingCall = (StrategyCallBuy) spxExocet.getCall(InvApproach.SWING, TradeRatio.NONE, 0.50);
		System.out.println(swingCall.toString());
	}

	public void goShort(Underlying index) {
		String expDate = "20150319"; // TODO: generate dynamically
		PortfolioAction spxExocet = new PortfolioAction(100, index, expDate, broker);
		StrategyCallBuy swingCall = (StrategyCallBuy) spxExocet.getCall(InvApproach.SWING, TradeRatio.NONE, 0.50);
		System.out.println(swingCall.toString());
	}

	
	// CHARTS
	// TODO: underlying price, resistance/support?
	private void getUptodateInvestmentCharts() {
		String fromDate = "2015-02-21"; 	// TODO: configurable date
		String toDate = "2015-02-28";
		for(SamplingRate sampling:getSampling().getList(SamplingRate.SCALPSHORT)) { // TODO: what rates?
			for(Investment inv:getMarketPortfolio().investments) {
				getInvestmentChart(inv, sampling, fromDate, toDate);
			}
		}
	}
	
	private void getInvestmentChart(Investment inv, SamplingRate sampling, String fromDate, String toDate) {

		Chart chart = new Chart();
		
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
		
		chart = marketPrice.readChart(	inv, TradeType.TRADED, sampling, 
										fromDate, toDate,
										source, timing);
		
		if(!chart.getSizes().isEmpty()) {
			inv.getCharts().put(sampling, chart); // sampling is key	
			System.out.println("+ chart " + inv.toString() +  " " + sampling + "\n" + chart.toString() + "\n\n");			
		} else {
			System.out.println("- chart " + inv.toString() + " " + sampling  + "\n\n");
		}		
	}

	// ANALYSIS
	private void analyzeUptodateInvestmentCharts() {
		System.out.println("\n\n" + "ANALYZING CHARTS");
		for(Investment inv:getMarketPortfolio().investments) {
			for(SamplingRate trading:getSampling().getTradingOptions()) {
				String analysis = "";
				analysis = analysis + "=====" + inv.toString() + "=====" + "\n";
				for(SamplingRate sampling:getSampling().getList(trading)) { 
					analysis = analysis + getInvestmentAnalysis(inv, sampling);
				}			
				System.out.println(analysis + "\n");
			}			
		}	
	}

	private String getInvestmentAnalysis(Investment inv, SamplingRate sampling) {
		String s = "\n";
		s = s + ">> " + sampling + "\t"; 
		Chart chart = inv.getCharts().get(sampling);
		if(chart!=null) { // not all sampling cases may be available
			s = s + getChartAnalysis(chart);			
		} else {
			s = s + "null";
		}
		s = s + "\n";
		return s;
	}	
	
	private String getChartAnalysis(Chart chart) {
		String s = "";
		chart.setAnalysis();
		for(int i=0; i<chart.getPrices().size(); i++) {
			s = s + chart.getPriceAnalysis(i);
			s = s + chart.getVolumeAnalysis(i);
			s = s + chart.getMomentumAnalysis(i);
		}		
		return s;
	}
		

	
	// TEST
	
	// PRINT
	
	// SET / GET

	private Portfolio getMarketPortfolio() {
		return marketPortfolio;
	}

	private void setMarketPortfolio(Portfolio marketPortfolio) {
		this.marketPortfolio = marketPortfolio;
	}

	private Underlying getIndex() {
		return index;
	}

	private void setIndex(Underlying index) {
		this.index = index;
	}

	public DataSampling getSampling() {
		return sampling;
	}

	public void setSampling(DataSampling sampling) {
		this.sampling = sampling;
	}

}
