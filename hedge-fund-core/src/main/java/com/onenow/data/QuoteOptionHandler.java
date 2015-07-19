package com.onenow.data;

import java.util.logging.Level;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.onenow.constant.InvType;
import com.onenow.constant.OptionVolatility;
import com.onenow.constant.SizeType;
import com.onenow.execution.ApiController.IOptHandler;
import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class QuoteOptionHandler extends QuoteSharedHandler implements IOptHandler {  

	double m_impVol;
	double m_delta;
	double m_gamma;
	double m_vega;
	double m_theta;
	
	// other
	boolean m_done;

	public QuoteOptionHandler() {
		super();
	}
	
	public QuoteOptionHandler(Investment inv, AbstractTableModel chainTable) {
		super(inv, chainTable);
	}


	// https://www.interactivebrokers.com/en/software/api/api_Left.htm#CSHID=apiguide%2Ftables%2Ftick_types.htm|StartTopic=apiguide%2Ftables%2Ftick_types.htm|SkinName=ibskin
	@Override public void tickOptionComputation( TickType tickType, Double impVol, Double delta, Double optPrice, Double pvDividend, Double gamma, Double vega, Double theta, Double undPrice) {
			
		// TODO: ASK_OPTION_COMPUTATION, BID_OPTION_COMPUTATION, LAST_OPTION_COMPUTATION, MODEL_OPTION_COMPUTATION
		
		switch( tickType) {
		
		case MODEL_OPTION:
			// implied volatility
			m_impVol = impVol;
			Watchr.log(Level.INFO, ">>>>> Option Model Implied Volatility Computation " + impVol + " for " + investment.toString());
			MarketPrice.writeOptionComputationStreaming(TimeParser.getTimeMilisecondsNow(), investment, impVol, OptionVolatility.MODEL_OPTION);			

			// delta
			m_delta = delta;
			MarketPrice.writeGreekStreaming(TimeParser.getTimeMilisecondsNow(), investment, delta, GreekType.DELTA);

			m_gamma = gamma;
			MarketPrice.writeGreekStreaming(TimeParser.getTimeMilisecondsNow(), investment, gamma, GreekType.GAMMA);

			m_vega = vega;
			MarketPrice.writeGreekStreaming(TimeParser.getTimeMilisecondsNow(), investment, vega, GreekType.VEGA);

			m_theta = theta;
			MarketPrice.writeGreekStreaming(TimeParser.getTimeMilisecondsNow(), investment, theta, GreekType.THETA);			

			Watchr.log(	Level.INFO,
						"-modeloption: " + 
						"-IMPVOLATILITY " + impVol + " -DELTA " + delta + " -GAMMA " + gamma + " -VEGA " + vega + " -THETA " + theta + " " +
						"-for " + investment.toString());			
			break;
		
		// OptionComputationType
		case CUST_OPTION_COMPUTATION:
			Watchr.log(Level.INFO, ">>>>> Option Custom Implied Volatility Computation " + impVol + " for " + investment.toString());
			MarketPrice.writeOptionComputationStreaming(TimeParser.getTimeMilisecondsNow(), investment, impVol, OptionVolatility.CUST_OPTION_COMPUTATION);			
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