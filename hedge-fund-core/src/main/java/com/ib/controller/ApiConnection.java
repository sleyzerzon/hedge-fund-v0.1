package com.ib.controller;


import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.logging.Level;

import com.ib.client.ComboLeg;
import com.ib.client.EWrapper;
import com.ib.client.Order;
import com.ib.client.OrderComboLeg;
import com.ib.client.OrderType;
import com.ib.client.TagValue;
import com.ib.client.Types.AlgoStrategy;
import com.ib.client.Types.HedgeType;
import com.ib.client.Types.SecType;
import com.onenow.execution.EWireBuilder;
import com.onenow.execution.Contract;
import com.onenow.execution.EClientErrors;
import com.onenow.execution.EClientSocket;
import com.onenow.execution.EReader;
import com.onenow.util.Watchr;

public class ApiConnection extends EClientSocket {
	
	public static final char EOL = 0;
	public static final char LOG_EOL = '_';

	public ApiConnection(EWrapper wrapper) {
		super( wrapper);
	}

	@Override public synchronized void eConnect(Socket socket, int clientId) throws IOException {
		super.eConnect(socket, clientId);

		// replace the output stream with one that logs all data to m_outLogger
		if (isConnected()) {
			try {
				Field realOsField = FilterOutputStream.class.getDeclaredField( "out");
				realOsField.setAccessible( true);
				OutputStream realOs = (OutputStream)realOsField.get( m_dos);
				realOsField.set( m_dos, new MyOS( realOs) );
			}
			catch( Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** Replace the input stream with one that logs all data to m_inLogger. */
	@Override public EReader createReader(EClientSocket socket, DataInputStream dis) {
		try {
			Field realIsField = FilterInputStream.class.getDeclaredField( "in");
			realIsField.setAccessible( true);
			InputStream realIs = (InputStream)realIsField.get( dis);
			realIsField.set( dis, new MyIS( realIs) );
		}
		catch( Exception e) {
			e.printStackTrace();
		}
		return super.createReader(socket, dis);
	}

	public synchronized void placeOrder(Contract contract, Order order) {
		// not connected?
		if( !isConnected() ) {
            notConnected();
			return;
		}

		// ApiController requires TWS 932 or higher; this limitation could be removed if needed
		if (serverVersion() < 66) {
			System.out.println("ERROR: PLACE ORDER BUILD");
            error( EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS, "ApiController requires TWS build 932 or higher to place orders.");
            return;
		}

		EWireBuilder b = prepareBuffer();
		b.setUseSendMax(); // Order placement relies on null valued numeric fields

		int VERSION = 43;

		// send place order msg
		try {
			b.append( PLACE_ORDER);
			b.append( VERSION);
			b.append( order.orderId() );
			b.append( contract.conid() );
			b.append( contract.symbol());
			b.send( contract.secType() );
			b.append( contract.expiry());
			b.send( contract.strike());
			b.append( contract.right().getApiString() ); // TODO: Here and below: do not need to call getApiString() - NPE is possible
			b.append( contract.multiplier() );
			b.append( contract.exchange() );
			b.append( contract.primaryExch() );
			b.append( contract.currency() );
			b.append( contract.localSymbol() );
            if (m_serverVersion >= MIN_SERVER_VER_TRADING_CLASS) {
                b.append(contract.tradingClass() );
            }
			b.send( contract.secIdType() );
			b.append( contract.secId() );
			b.send( order.action() );
			b.append( order.totalQuantity() );
			b.send( order.orderType() );
			b.send( order.lmtPrice() );
			b.send( order.auxPrice() );
			b.send( order.tif() );
			b.append( order.ocaGroup() );
			b.append( order.account() );
			b.append( ""); // open/close
			b.append( ""); // origin
			b.append( order.orderRef() );
			b.send( order.transmit() );
			b.append( order.parentId() );
			b.send( order.blockOrder() );
			b.send( order.sweepToFill() );
			b.append( order.displaySize() );
			b.send( order.triggerMethod() );
			b.send( order.outsideRth() );
			b.send( order.hidden() );

			// send combo legs for BAG orders
			if(contract.secType() == SecType.BAG) {
				b.append( contract.comboLegs().size());

				for (ComboLeg leg : contract.comboLegs() ) {
					b.append( leg.conid() );
					b.append( leg.ratio() );
					b.append( leg.action().getApiString() );
					b.append( leg.exchange() );
					b.append( leg.openClose().getApiString() );
					b.append( leg.shortSaleSlot() );
					b.append( leg.designatedLocation() );
					b.append( leg.exemptCode() );
				}

				b.append( order.orderComboLegs().size());
				for (OrderComboLeg orderComboLeg : order.orderComboLegs() ) {
					b.send( orderComboLeg.price());
				}

				b.append( order.smartComboRoutingParams().size() );
				for (TagValue tagValue : order.smartComboRoutingParams() ) {
					b.append( tagValue.m_tag);
					b.append( tagValue.m_value);
				}
			}

			b.append( ""); // obsolete field
			b.send( order.discretionaryAmt() );
			b.append( order.goodAfterTime() );
			b.append( order.goodTillDate() );
			b.append( order.faGroup());
			b.send( order.faMethod() );
			b.append( order.faPercentage() );
			b.append( order.faProfile());
			b.append( 0); // short sale slot
			b.append( ""); // designatedLocation
			b.append( ""); // exemptCode
			b.send( order.ocaType() );
			b.send( order.rule80A() );
			b.append( ""); // settlingFirm
			b.send( order.allOrNone() );
			b.append( order.minQty() );
			b.send( order.percentOffset() );
			b.send( order.eTradeOnly() );
			b.send( order.firmQuoteOnly() );
			b.send( order.nbboPriceCap() );
			b.append( order.auctionStrategy() );
			b.send( order.startingPrice() );
			b.send( order.stockRefPrice() );
			b.send( order.delta() );
			b.send( order.stockRangeLower() );
			b.send( order.stockRangeUpper() );
			b.send( order.overridePercentageConstraints() );
			b.send( order.volatility() );
			b.send( order.volatilityType() );
			b.send( order.deltaNeutralOrderType() );
			b.send( order.deltaNeutralAuxPrice() );

			if (order.deltaNeutralOrderType() != OrderType.None) {
				b.append( order.deltaNeutralConId() );
				b.append( ""); //deltaNeutralSettlingFirm
				b.append( ""); //deltaNeutralClearingAccount
				b.append( ""); //deltaNeutralClearingIntent
				b.append( ""); //deltaNeutralOpenClose
                b.append( ""); //deltaNeutralShortSale
                b.append( ""); //deltaNeutralShortSaleSlot
                b.append( ""); //deltaNeutralDesignatedLocation
			}
			
			b.append( order.continuousUpdate() );
			b.send( order.referencePriceType() );
			b.send( order.trailStopPrice() );
			b.send( order.trailingPercent() );
			b.append( order.scaleInitLevelSize() );
			b.append( order.scaleSubsLevelSize() );
			b.send( order.scalePriceIncrement() );

			if (order.scalePriceIncrement() != 0 && order.scalePriceIncrement() != Double.MAX_VALUE) {
				b.send( order.scalePriceAdjustValue() );
				b.append( order.scalePriceAdjustInterval() );
				b.send( order.scaleProfitOffset() );
				b.send( order.scaleAutoReset() );
				b.append( order.scaleInitPosition() );
				b.append( order.scaleInitFillQty() );
				b.send( order.scaleRandomPercent() );
			}

			if (m_serverVersion >= MIN_SERVER_VER_SCALE_TABLE) {
				b.append( order.scaleTable() );
				b.append( ""); // active start time
				b.append( ""); // active stop time
			}

	        b.send( order.hedgeType() );
			if (order.hedgeType() != HedgeType.None) {
				b.append( order.hedgeParam() );
			}

			b.send( order.optOutSmartRouting() );
			b.append( "");//clearingAccount
			b.append( "");//clearingIntent
			b.send( order.notHeld() );

			b.send( contract.underComp() != null);
			if (contract.underComp() != null) {
				b.append( contract.underComp().conid() );
				b.send( contract.underComp().delta() );
				b.send( contract.underComp().price() );
			}

			b.send( order.algoStrategy() );
			if( order.algoStrategy() != AlgoStrategy.None) {
				b.append( order.algoParams().size() );
				for( TagValue tagValue : order.algoParams() ) {
					b.append( tagValue.m_tag);
					b.append( tagValue.m_value);
				}
			}

	        if (m_serverVersion >= MIN_SERVER_VER_ALGO_ID) {
	        	b.append( order.algoId() );
	        }

			b.send( order.whatIf() );
			
			// send orderMiscOptions stub
	        if(m_serverVersion >= MIN_SERVER_VER_LINKING) {	     
	            b.append( "" );
	        }

	        closeAndSend( b );
		}
		catch( Exception e) {
			e.printStackTrace();
			message( order.orderId(), 512, "Order sending error - " + e);
			close();
		}
	}

    /** An output stream that forks all writes to the output logger. */
    private class MyOS extends OutputStream {
    	final OutputStream m_os;

    	MyOS( OutputStream os) {
    		m_os = os;
    	}

    	@Override public void write(byte[] b) throws IOException {
    		m_os.write( b);
    		logReplace( new String( b) );
    	}

    	@Override public synchronized void write(byte[] b, int off, int len) throws IOException {
    		m_os.write(b, off, len);
    		logReplace( new String( b, off, len) );
    	}

    	@Override public synchronized void write(int b) throws IOException {
    		m_os.write(b);
    		logReplace( String.valueOf( (char)b) );
    	}

    	@Override public void flush() throws IOException {
    		m_os.flush();
    	}

    	@Override public void close() throws IOException {
    		m_os.close();
    		String log = "<output stream closed>";
//    		m_outLogger.logReplace( log );
    		// Watch.log(log);
    	}

    	void logReplace( String str) {
    		String log = str.replace( EOL, LOG_EOL);
//    		m_outLogger.logReplace( log );
    		// Watch.log(log);
    	}
    }

    /** An input stream that forks all reads to the input logger. */
    private class MyIS extends InputStream {
    	InputStream m_is;

    	MyIS( InputStream is) {
    		m_is = is;
    	}

		@Override public int read() throws IOException {
			int c = m_is.read();
			logReplace( String.valueOf( (char)c) );
			return c;
		}

		@Override public int read(byte[] b) throws IOException {
			int n = m_is.read(b);
			logReplace( new String( b, 0, n) );
			return n;
		}

		@Override public int read(byte[] b, int off, int len) throws IOException {
			int n = m_is.read(b, off, len);
			logReplace( new String( b, 0, n) );
			return n;
		}

		@Override public void close() throws IOException {
			super.close();
			logReplace( "<input stream closed>");
		}

    	void logReplace( String str) {
    		String log = str.replace( EOL, LOG_EOL);
//    		m_inLogger.logReplace( log );
    		Watchr.log(Level.INFO, log);
    	}
    }
}
