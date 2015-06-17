package com.onenow.data;

import java.util.logging.Level;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.execution.Contract;
import com.onenow.execution.ApiController.IOptHandler;
import com.onenow.execution.ApiController.TopMktDataAdapter;
import com.onenow.instrument.Investment;
import com.onenow.portfolio.BrokerController.MktDataAdapter;
import com.onenow.util.Watchr;

public class QuoteChainSingle extends MktDataAdapter implements IOptHandler { // extends TopMktDataAdapter implements IOptHandler {  
//	Contract m_c;
	double m_bid;
	double m_ask;
	double m_impVol;
	double m_delta;
	double m_gamma;
	double m_vega;
	double m_theta;
	boolean m_done;

	// added:
	Investment investment;
	MarketPrice marketPrice;
	String m_description;
	AbstractTableModel m_model;
	Contract contract;

	public QuoteChainSingle() {
		
	}
	
	public QuoteChainSingle(AbstractTableModel model, Contract contract, Investment inv, MarketPrice marketPrice) {
		this.m_model = model;
		this.contract = contract;		
		this.investment = inv;
		this.marketPrice = marketPrice;
	
	}

	@Override public void tickPrice(TickType tickType, double price, int canAutoExecute) {
		switch(tickType) {
			case BID:
				Watchr.log("BID " + price);
				m_bid = price;
				break;
			case ASK:
				m_ask = price;
				Watchr.log("ASK " + price);
				break;
            default: break; 
		}
	}

	@Override public void tickOptionComputation( TickType tickType, double impVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
		if (tickType == TickType.MODEL_OPTION) {
			m_impVol = impVol;
			m_delta = delta;
			m_gamma = gamma;
			m_vega = vega;
			m_theta = theta;
			Watchr.log("IMPVOLATILITY " + impVol + " DELTA " + delta + " GAMMA " + gamma + " VEGA " + vega + " THETA " + theta);
		}
	}
	
	@Override public void tickString(TickType tickType, String value) {
		switch( tickType) {
		case LAST_TIMESTAMP:
//			m_lastTime = Long.parseLong(value);
			Watchr.log("LAST_TIMESTAMP " + Long.parseLong(value));
			break;
		case AVG_VOLUME:
			Watchr.log("AVG_VOLUME " + value); // not for indices
			break;
		case OPTION_CALL_VOLUME:
			Watchr.log("OPTION_CALL_VOLUME " + value); // stocks 
			break;
		case OPTION_PUT_VOLUME:
			Watchr.log("OPTION_PUT_VOLUME " + value); // stocks
			break;
		case AUCTION_VOLUME:
			Watchr.log("AUCTION_VOLUME " + value); // subscribe to
			break;
		case RT_VOLUME:
			// the time-stamp is in UTC time zone
			Watchr.log("RT_VOLUME " + value + " for " + investment.toString()); 
			MarketPrice.parseAndWriteRealTime(investment, value);
			// Example: RT_VOLUME 0.60;1;1424288913903;551;0.78662433;true
			break;
		case VOLUME_RATE:
			Watchr.log(Level.INFO, "VOLUME_RATE " + value); // not for indices
			break;
		
        default: break; 
	}
	}
	
	@Override public void tickSnapshotEnd() {
		m_done = true;
	}
	
	// PRINT
	public String toString() {
		String s="\n\n";
		s = s + "QUOTE" + "\n";
		s = s + "Contract " + contract.description() + "\n";
		s = s + "Bid " + m_bid + "\n";
		s = s + "Ask " + m_ask + "\n";
		s = s + "ImpliedVol " + m_impVol + "\n";		
		s = s + "Delta " + m_delta + "\n";
		s = s + "Gamma " + m_gamma + "\n";
		s = s + "Vega " + m_vega + "\n";
		s = s + "Theta " + m_theta + "\n";
		s = s + "Done " + m_done + "\n";
		return s;
	}

	@Override
	public void tickSize(TickType tickType, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void marketDataType(MktDataType marketDataType) {
		// TODO Auto-generated method stub
		
	}

}