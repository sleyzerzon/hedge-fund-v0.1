package com.onenow.data;

import java.util.logging.Level;

import javax.swing.table.AbstractTableModel;

import com.ib.client.TickType;
import com.ib.client.Types.MktDataType;
import com.onenow.constant.GenericType;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.VolatilityType;
import com.onenow.constant.SizeType;
import com.onenow.constant.InvType;
import com.onenow.constant.PriceType;
import com.onenow.execution.Contract;
import com.onenow.execution.ContractFactory;
import com.onenow.instrument.Investment;
import com.onenow.portfolio.BusController.ITopMktDataHandler;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

/**
 * 
 * API Guide: https://www.interactivebrokers.com/en/software/api/api_Left.htm#CSHID=apiguide%2Ftables%2Ftick_types.htm|StartTopic=apiguide%2Ftables%2Ftick_types.htm|SkinName=ibskin
 * @author pablo
 *
 */
public class QuoteSharedHandler implements ITopMktDataHandler {
	
	// tickGeneric()
	
	// time
	long m_lastTime;

	// price
	double m_bid;
	double m_ask;
	double m_last;
	double m_close;
	
	// size
	int m_askSize;
	int m_bidSize;
	int m_volume;

	// added:
	public Investment investment;
	
	// other
	boolean m_frozen;

	// table
	protected AbstractTableModel chainTable;

	
	public QuoteSharedHandler() {
		
	}
	
	public QuoteSharedHandler(Investment inv, AbstractTableModel chainTable) {
		this.investment = inv;
		this.chainTable = chainTable;
	}
	
	public Contract getContract() {
		return ContractFactory.getContract(investment);
	}
	
	@Override
	// TODO: FROZEN, MARKETDEPTH
	public void tickPrice(TickType tickType, Double price, Integer canAutoExecute) {
			
		// TODO bond contracts: BIDYIELD, ASKYIELD, LASTYIELD
		
		switch( tickType) {	
		
		// BID
		case BID:
			m_bid = price;
			Watchr.log(Level.INFO, ">>>>> Bid " + price + " for " + investment.toString());
			MarketPrice.writePriceStreaming(investment, price, PriceType.BID, InvDataSource.IB);
			break;
			
		// ASK	
		case ASK:
			m_ask = price;
			Watchr.log(Level.INFO, ">>>>> Ask " + price + " for " + investment.toString());
			MarketPrice.writePriceStreaming(investment, price, PriceType.ASK, InvDataSource.IB);
			break;	
						
		// LAST
		case LAST_PRICE:	// last trade at which the contract traded
			m_last = price;
			Watchr.log(Level.INFO, ">>>>> Last " + price + " for " + investment.toString());
			MarketPrice.writePriceStreaming(investment, price, PriceType.TRADED, InvDataSource.IB);
			break;
						
		// TODO: other? or also LAST
		case AUCTION_PRICE:
			Watchr.log(Level.INFO, ">>>>> Auction Price " + price + " for " + investment.toString());
			MarketPrice.writePriceStreaming(investment, price, PriceType.AUCTION_PRICE, InvDataSource.IB);		// AUCTION_PRICE
			break;
		case MARK_PRICE: 
			Watchr.log(Level.INFO, ">>>>> Mark Price " + price + " for " + investment.toString());
			MarketPrice.writePriceStreaming(investment, price, PriceType.MARK_PRICE, InvDataSource.IB);			// MARK_PRICE
			break;


		////////////////
		//////// CALCULATE OWN
		case OPEN:
			Watchr.log(Level.INFO, ">>>>> Open Price " + price + " for " + investment.toString());
			break;
		case CLOSE:
			Watchr.log(Level.INFO, ">>>>> Close Price " + price + " for " + investment.toString());
			break;
		case HIGH:
			Watchr.log(Level.INFO, ">>>>> High Price " + price + " for " + investment.toString());
			break;
		case LOW:
			Watchr.log(Level.INFO, ">>>>> Low Price " + price + " for " + investment.toString());
			break;
		case HIGH_13_WEEK:
			Watchr.log(Level.INFO, ">>>>> High 13 Weeks Price " + price + " for " + investment.toString());
			break;
		case LOW_13_WEEK:
			Watchr.log(Level.INFO, ">>>>> Low Price " + price + " for " + investment.toString());
			break;
		case HIGH_26_WEEK:
			Watchr.log(Level.INFO, ">>>>> High 26 Weeks Price " + price + " for " + investment.toString());
			break;
		case LOW_26_WEEK:
			Watchr.log(Level.INFO, ">>>>> Low 26 Weeks Price " + price + " for " + investment.toString());
			break;
		case HIGH_52_WEEK:
			Watchr.log(Level.INFO, ">>>>> Low 52 Price " + price + " for " + investment.toString());
			break;
		case LOW_52_WEEK:
			Watchr.log(Level.INFO, ">>>>> Low 52 Price " + price + " for " + investment.toString());
			break;			
		
		default:
			Watchr.log(Level.WARNING, 	"$$$$$ tickPrice: " + " -tickType " + tickType +
					" -for " + investment.toString());
			break;	
		}
		
		chainTable.fireTableDataChanged();
	}
	
	@Override public void tickGeneric(TickType tickType, Double value) {

		switch( tickType) {			

		case RT_HISTORICAL_VOL: 	// Streaming historical volatility, w/o time stamp
			Watchr.log(Level.INFO, ">>>>> Option RT Historical Volatility " + value + " for " + investment.toString());
			MarketPrice.writeVolatilityStreaming(investment, value, VolatilityType.RT_HISTORICAL_VOL, InvDataSource.IB);			
			break;
		case OPTION_HISTORICAL_VOL:
			Watchr.log(Level.INFO, ">>>>> Option Historical Volatility " + value + " for " + investment.toString());
			MarketPrice.writeVolatilityStreaming(investment, value, VolatilityType.OPTION_HISTORICAL_VOL, InvDataSource.IB);			
			break;
		case OPTION_IMPLIED_VOL:
			Watchr.log(Level.INFO, ">>>>> Option Implied Volatility " + value + " for " + investment.toString());
			MarketPrice.writeVolatilityStreaming(investment, value, VolatilityType.OPTION_IMPLIED_VOL, InvDataSource.IB);			
			break;
		case INDEX_FUTURE_PREMIUM:
			Watchr.log(Level.INFO, ">>>>> Mark Price " + value + " for " + investment.toString());
			MarketPrice.writePriceStreaming(investment, value, PriceType.INDEX_FUTURE_PREMIUM, InvDataSource.IB);
			break;
		case TRADE_COUNT:
			Watchr.log(Level.INFO, ">>>>> Trade Count " + value + " for " + investment.toString());
			MarketPrice.writeGenericStreaming(investment, value, GenericType.TRADE_COUNT, InvDataSource.IB);			
			break;
		case TRADE_RATE:
			Watchr.log(Level.INFO, ">>>>> Trade Rate " + value + " for " + investment.toString());
			MarketPrice.writeGenericStreaming(investment, value, GenericType.TRADE_RATE, InvDataSource.IB);			
			break;
		case SHORTABLE:
			Watchr.log(Level.INFO, ">>>>> Shortable " + value + " for " + investment.toString());
			MarketPrice.writeGenericStreaming(investment, value, GenericType.SHORTABLE, InvDataSource.IB);			
			break;
		case HALTED:
			Watchr.log(Level.INFO, ">>>>> Halted " + value + " for " + investment.toString());
			MarketPrice.writeGenericStreaming(investment, value, GenericType.HALTED, InvDataSource.IB);			
			break;


		default:
			Watchr.log(Level.WARNING, 	"$$$$$ tickGeneric: " + " -tickType " + tickType +
					" -for " + investment.toString());
			break;	
		}

	}
	
	
	@Override
	public void tickSize(TickType tickType, Integer size) {
		
		switch( tickType) {

		// SIZE
		case BID_SIZE:
			m_bidSize = size;
			Watchr.log(Level.INFO, ">>>>> Bid Size " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.BID_SIZE, InvDataSource.IB);
			break;
		case ASK_SIZE:
			m_askSize = size;
			Watchr.log(Level.INFO, ">>>>> Ask Size " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.ASK_SIZE, InvDataSource.IB);
			break;
		case LAST_SIZE:
			Watchr.log(Level.INFO, ">>>>> Last Size " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.TRADED_SIZE, InvDataSource.IB);
			break;
			
		// VOLUME
		case VOLUME:
			m_volume = size;
			Watchr.log(Level.INFO, ">>>>> Volume " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.VOLUME, InvDataSource.IB);
			break;
		case AVG_VOLUME:
			Watchr.log(Level.INFO, ">>>>> Average Volume " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.AVG_VOLUME, InvDataSource.IB);
			break;
		case VOLUME_RATE:
			Watchr.log(Level.INFO, ">>>>> Volume Rate " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.VOLUME_RATE, InvDataSource.IB);
			break;
			
		// OPEN INTEREST
		case OPTION_CALL_OPEN_INTEREST:
			Watchr.log(Level.INFO, ">>>>> Call Open Interest " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.OPTION_CALL_OPEN_INTEREST, InvDataSource.IB);
			break;
		case OPTION_PUT_OPEN_INTEREST:
			Watchr.log(Level.INFO, ">>>>> Put Open Interest " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.OPTION_PUT_OPEN_INTEREST, InvDataSource.IB);
			break;
		case OPTION_CALL_VOLUME:
			Watchr.log(Level.INFO, ">>>>> Call Volume " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.OPTION_CALL_VOLUME, InvDataSource.IB);
			break;
		case OPTION_PUT_VOLUME:
			Watchr.log(Level.INFO, ">>>>> Put Volume " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.OPTION_PUT_VOLUME, InvDataSource.IB);
			break;
			
		// OTHER
		case REGULATORY_IMBALANCE:
			Watchr.log(Level.INFO, ">>>>> Regulatory Imbalance " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.REGULATORY_IMBALANCE, InvDataSource.IB);
			break;
		case AUCTION_VOLUME:
			Watchr.log(Level.INFO, ">>>>> Auction Volume " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.AUCTION_VOLUME, InvDataSource.IB);
			break;
		case AUCTION_IMBALANCE:
			Watchr.log(Level.INFO, ">>>>> Auction Imbalance " + size + " for " + investment.toString());
			MarketPrice.writeSizeStreaming(investment, size, SizeType.AUCTION_IMBALANCE, InvDataSource.IB);
			break;
        default:
    		Watchr.log(Level.WARNING, 	"$$$$$ tickSize:" + " -tickType: " + tickType +
					" -for " + investment.toString());
        	break; 
		}
		
		chainTable.fireTableDataChanged();
	}

	@Override
	public void tickString(TickType tickType, String value) {
					
		switch( tickType) {
		
		case BID_EXCH:
			Watchr.log(Level.INFO, ">>>>> Best Bid Exchange " + value + " for " + investment.toString());
			MarketPrice.parseAndWriteStrings(investment, value, InvDataSource.IB);		// BID_EXCH: options exchange hosting the best bid price
			break;
		case ASK_EXCH:
			Watchr.log(Level.INFO, ">>>>> Best Ask Exchange " + value + " for " + investment.toString());
			MarketPrice.parseAndWriteStrings(investment, value, InvDataSource.IB);		// ASK_EXCH
			break;

		case LAST_TIMESTAMP:	// timestamp for the last trade
			// TODO: what should be the use of this stamp? instead of getting our own for other ticks?
			m_lastTime = Long.parseLong(value);
			MarketPrice.lastTimeMilisecMap.put(investment, Long.valueOf(value));
			break;
		case FUNDAMENTAL_RATIOS:
			MarketPrice.parseAndWriteStrings(investment, value, InvDataSource.IB);
			break;
		case UNKNOWN:
			// TODO
			break;

		////////////////
		//////// REALTIME
		case RT_VOLUME:
			Watchr.log(Level.INFO, ">>>>> RT_VOLUME " + value + " for " + investment.toString()); 
			MarketPrice.parseAndWriteRealTimePriceSize(investment, value);
			// Example: RT_VOLUME 0.60;1;1424288913903;551;0.78662433;true
			break;

        default:
    		Watchr.log(Level.WARNING, 	"$$$$$ tickString: " + " -tickType " + tickType +
					" -for " + investment.toString());
        	break; 
		}
		
		chainTable.fireTableDataChanged();
	}

	@Override
	public void tickSnapshotEnd() {
		Watchr.log(Level.SEVERE, "call to empty: tickSnapshotEnd" + " for " + investment.toString());		
	}
	

	@Override
	public void marketDataType(MktDataType marketDataType) {
				
		m_frozen = marketDataType == MktDataType.Frozen;
		
		chainTable.fireTableDataChanged();

	}


	public String toString() {
		String s = "";

		s = s + "*** QUOTE HANDLER " + " ";
		s = s + "-lastTime " + m_lastTime + " ";
		s = s + "-investment " + investment + " ";
		s = s + "-bid " + m_bid + " ";
		s = s + "-ask " + m_ask + " ";
		s = s + "-last " + m_last + " ";
		s = s + "-close " + m_close + " ";
		s = s + "-askSize " + m_askSize + " ";
		s = s + "-bidSize " + m_bidSize + " ";
		s = s + "-volume " + m_volume + " ";
		s = s + "-frozen " + m_frozen;

		return s;
	}
}
