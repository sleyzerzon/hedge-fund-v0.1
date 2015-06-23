package com.onenow.data;

import java.util.logging.Level;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.onenow.constant.InvType;
import com.onenow.execution.ApiController.IOptHandler;
import com.onenow.instrument.Investment;
import com.onenow.util.Watchr;

public class QuoteRealtimeOption extends QuoteRealtimeHandler implements IOptHandler {  

	double m_impVol;
	double m_delta;
	double m_gamma;
	double m_vega;
	double m_theta;
	
	// other
	boolean m_done;

	public QuoteRealtimeOption() {
		
	}
	
	public QuoteRealtimeOption(Investment inv, AbstractTableModel chainTable) {
		super(inv, chainTable);
	}


	@Override public void tickOptionComputation( TickType tickType, double impVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
				
		switch( tickType) {
		case MODEL_OPTION:
			m_impVol = impVol;
			m_delta = delta;
			m_gamma = gamma;
			m_vega = vega;
			m_theta = theta;
			// Watchr.log(	Level.INFO,
			//			"-modeloption: " + 
			//			" -IMPVOLATILITY " + impVol + " -DELTA " + delta + " -GAMMA " + gamma + " -VEGA " + vega + " -THETA " + theta + " " +
			//			" -for " + investment.toString());
			
			// TODO: WRITE TO DB
			break;
		case ASK_OPTION: 
			// TODO
			break;
		case BID_OPTION:
			// TODO
			break;
		case LAST_OPTION:
			// TODO
			break;
        default:
    		Watchr.log(Level.WARNING, 	"$$$$$ tickOptionComputation:" + " -tickType " + tickType +
					" -optPrice " + optPrice + " -undPrice " + undPrice + " -pvdividend " + pvDividend +
					" -for " + investment.toString());
        	break; 
		
		}
		chainTable.fireTableDataChanged();
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