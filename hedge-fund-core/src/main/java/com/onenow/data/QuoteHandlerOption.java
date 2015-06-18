package com.onenow.data;

import com.ib.client.TickType;
import com.onenow.execution.ApiController.IOptHandler;
import com.onenow.instrument.Investment;
import com.onenow.util.Watchr;

public class QuoteHandlerOption extends QuoteHandler implements IOptHandler {  

	double m_impVol;
	double m_delta;
	double m_gamma;
	double m_vega;
	double m_theta;
	
	// other
	boolean m_done;

	public QuoteHandlerOption() {
		
	}

	public QuoteHandlerOption(Investment inv) {
		this.investment = inv;
	}

	@Override public void tickOptionComputation( TickType tickType, double impVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
		if (tickType == TickType.MODEL_OPTION) {
			m_impVol = impVol;
			m_delta = delta;
			m_gamma = gamma;
			m_vega = vega;
			m_theta = theta;
			Watchr.log("IMPVOLATILITY " + impVol + " DELTA " + delta + " GAMMA " + gamma + " VEGA " + vega + " THETA " + theta +
						" for " + investment.toString());
			
			// TODO: WRITE TO DB
		}
	}
		
	@Override public void tickSnapshotEnd() {
		m_done = true;
	}
	
	// PRINT
	public String toString() {
		String s = "";
		s = s + super.toString() + " ";
		s = s + "-ImpliedVol " + m_impVol + " ";		
		s = s + "-Delta " + m_delta + " ";
		s = s + "-Gamma " + m_gamma + " ";
		s = s + "-Vega " + m_vega + " ";
		s = s + "-Theta " + m_theta + " ";
		s = s + "-Done " + m_done + " ";
		return s;
	}
}