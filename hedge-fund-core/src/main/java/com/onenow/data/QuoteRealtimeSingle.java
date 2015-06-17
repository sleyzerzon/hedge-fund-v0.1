package com.onenow.data;

import static com.ib.controller.Formats.fmtPct;

import java.util.logging.Level;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.InvDataType;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.portfolio.BrokerController.MktDataAdapter;
import com.onenow.util.Watchr;

/**
 * Single quote class
 *
 */
public class QuoteRealtimeSingle extends MktDataAdapter {
	
	AbstractTableModel m_model;
	String m_description;
	double m_bid;
	double m_ask;
	double m_last;
	long m_lastTime;
	int m_bidSize;
	int m_askSize;
	double m_close;
	int m_volume;
	boolean m_frozen;
	// added:
	Investment investment;
	MarketPrice marketPrice;
	
	public QuoteRealtimeSingle () {
		
	}
	
	QuoteRealtimeSingle( AbstractTableModel model, String description, Investment inv, MarketPrice marketPrice) {
		m_model = model;
		m_description = description;
		
		this.investment = inv;
		this.marketPrice = marketPrice;
	}
	
	public String change() {
		return m_close == 0	? null : fmtPct( (m_last - m_close) / m_close);
	}

	// INTERFACE
	@Override public void tickPrice( TickType tickType, double price, int canAutoExecute) {
		switch( tickType) {
			case BID:
				m_bid = price;
				// System.out.println("Bid " + m_bid);
//				marketPrice.writePriceNotRealTime(investment, m_bid, TradeType.SELL.toString());
				break;
			case ASK:
				m_ask = price;
				// System.out.println("Ask " + m_ask);
//				marketPrice.writePriceNotRealTime(investment, m_ask, TradeType.BUY.toString());
				break;
			case LAST:
				m_last = price;
				// System.out.println("Last " + m_last);
//				marketPrice.writePriceNotRealTime(investment, m_last, TradeType.TRADED.toString());
				break;
			case CLOSE:
				m_close = price;
				// System.out.println("Close " + m_close);
//				marketPrice.writePriceNotRealTime(investment, m_close, TradeType.CLOSE.toString());
				break;
			default: break;	
		}
	}

	@Override public void tickSize( TickType tickType, int size) {
		switch( tickType) {
			case BID_SIZE:
				m_bidSize = size;
//				marketPrice.writeSizeNotRealTime(investment, m_bidSize, InvDataType.BIDSIZE.toString());
				// System.out.println("Bid size " + m_bidSize);
				break;
			case ASK_SIZE:
				m_askSize = size;
//				marketPrice.writeSizeNotRealTime(investment, m_askSize, InvDataType.ASKSIZE.toString());
				// System.out.println("Ask size " + m_askSize);
				break;
			case VOLUME:
				m_volume = size;
//				marketPrice.writeSizeNotRealTime(investment, m_volume, InvDataType.VOLUME.toString());
				// System.out.println("Volume size " + m_volume);
				break;
            default: break; 
		}
	}
	
	/**
	 * Handler of all callback tick types
	 */
	@Override public void tickString(TickType tickType, String value) {
		switch( tickType) {
			case LAST_TIMESTAMP:
				m_lastTime = Long.parseLong(value);
				Watchr.log("LAST_TIMESTAMP " + m_lastTime);
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
	
	@Override public void marketDataType(MktDataType marketDataType) {
//		m_frozen = marketDataType == MktDataType.Frozen;
//		m_model.fireTableDataChanged();
//		
//		if(m_frozen==true) {
//			Watchr.log(Level.WARNING, "...frozen data");
//		}
	}



	// PRINT
	public String toString() {
		String s="\n\n";
		s = s + "QUOTE" + "\n";
		s = s + "Description " + m_description + "\n";
		s = s + "Bid " + m_bid + "\n";
		s = s + "Ask " + m_ask + "\n";
		s = s + "Last " + m_last + "\n";
		s = s + "Last time " + m_lastTime + "\n";
		s = s + "Bid size " + m_bidSize + "\n";
		s = s + "Ask size " + m_askSize + "\n";
		s = s + "Close " + m_close + "\n";
		s = s + "Frozen " + m_frozen + "\n";
		return s;
	}

}