package com.onenow.execution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import com.ib.client.ComboLeg;
import com.ib.client.Types.SecType;
import com.onenow.constant.InvType;
import com.onenow.data.Channel;
import com.onenow.instrument.Investment;
import com.onenow.instrument.InvestmentFuture;
import com.onenow.instrument.InvestmentIndex;
import com.onenow.instrument.InvestmentOption;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

/**
 * Translate investment objects into contract in the format of the specific Wall Street Broker 
 *
 */
public class ContractFactory {

	public ContractFactory() {
		
	}
	
	
	public static Contract getContract(Investment inv) {
				
		Contract contract = new Contract();
		
		if(inv instanceof InvestmentIndex) {
			contract = getIndexToQuote(inv);
			System.out.println("GOT INDEX CONTRACT FOR " + contract.toString());
			return contract;
		}

		if(inv instanceof InvestmentOption) {
			contract = getOptionToQuote(inv);
			System.out.println("GOT OPTION CONTRACT FOR " + contract.toString());
			return contract;			
		}

		if(inv instanceof InvestmentStock) {
			contract = getStockToQuote(inv);
			System.out.println("GOT STOCK CONTRACT FOR " + contract.toString());
			return contract;			
		}

		if(inv instanceof InvestmentFuture) {
			contract = getFutureToQuote(inv);
			System.out.println("GOT FUTURE CONTRACT FOR " + contract.toString());
			return contract;			
		}
		
		Watchr.log(Level.SEVERE, "COULD NOT MATCH INVESTMENT TO CONTRACT");

		return contract;
	}
	
	private static Contract getOptionToQuote(Investment inv) {
		
		String p_secType=SecType.OPT.toString();
		String p_exchange="SMART";
		
		// TODO: include all future underlyings
		if(inv.getUnder().getTicker().equals("ES")) {
			p_secType=SecType.FOP.toString();	// sop	
			p_exchange="GLOBEX";
		} 
		
		String p_symbol=inv.getUnder().getTicker();
				
		
		int p_conId = 0;
		
		String p_expiry="";
		double p_strike=0.0;
		String p_right="";
		if(inv instanceof InvestmentOption) {
			p_expiry=((InvestmentOption) inv).getExpirationDate();	
			p_strike=((InvestmentOption) inv).getStrikePrice();		
			p_right=inv.getInvType().toString(); 					
		}
		
		String p_multiplier="100";
		String p_currency="USD";
		String p_localSymbol="";
		String p_tradingClass="";
		ArrayList<ComboLeg> p_comboLegs=new ArrayList<ComboLeg>();
		String p_primaryExch="";
		boolean p_includeExpired=false;
		String p_secIdType="";
		String p_secId="";
		
		Contract cont = new Contract();
		cont = new Contract(p_conId, p_symbol, p_secType, p_expiry,
                    p_strike, p_right, p_multiplier,
                    p_exchange, p_currency, p_localSymbol, p_tradingClass,
                    p_comboLegs, p_primaryExch, p_includeExpired,
                    p_secIdType, p_secId);
		return cont;	
	}

	private static Contract getFutureToQuote(Investment inv) {
		
		String p_secType=SecType.FUT.toString();	
		String p_exchange="GLOBEX";		

		String p_symbol=inv.getUnder().getTicker();
		
		int p_conId = 0;
		
		String p_expiry=((InvestmentFuture) inv).getExpirationDate();
		String p_multiplier="50";		
		
		double p_strike=0.0;
		String p_right="";
		
		String p_currency="USD";
		String p_localSymbol="";
		String p_tradingClass="";
		ArrayList<ComboLeg> p_comboLegs=new ArrayList<ComboLeg>();
		String p_primaryExch="";
		boolean p_includeExpired=false;
		String p_secIdType="";
		String p_secId="";
		
		Contract cont = new Contract();
		cont = new Contract(p_conId, p_symbol, p_secType, p_expiry,
                    p_strike, p_right, p_multiplier,
                    p_exchange, p_currency, p_localSymbol, p_tradingClass,
                    p_comboLegs, p_primaryExch, p_includeExpired,
                    p_secIdType, p_secId);
		return cont;	
	}

	
	private static Contract getIndexToQuote(Investment inv) {
		String p_secType=SecType.IND.toString();	

		String p_symbol="";
		String p_exchange="";
		if(inv.getUnder().getTicker().equals("SPX")) {
			p_symbol="SPX";
			p_exchange="CBOE";		
		}
		if(inv.getUnder().getTicker().equals("RUT")) {
			p_symbol="RUT";
			p_exchange="RUSSELL";		
		}
		if(inv.getUnder().getTicker().equals("NDX")) {
			p_symbol="NDX";
			p_exchange="NASDAQ";		
		}
			
		int p_conId = 0;
		
		String p_expiry="";		
		double p_strike=0.0;	
		String p_right=""; 	
		
		String p_multiplier="100";
		String p_currency="USD";
		String p_localSymbol="";
		String p_tradingClass="";
		ArrayList<ComboLeg> p_comboLegs=new ArrayList<ComboLeg>();
		String p_primaryExch="";
		boolean p_includeExpired=false;
		String p_secIdType="";
		String p_secId="";
		
		Contract cont = new Contract();
		cont = new Contract(p_conId, p_symbol, p_secType, p_expiry,
                    p_strike, p_right, p_multiplier,
                    p_exchange, p_currency, p_localSymbol, p_tradingClass,
                    p_comboLegs, p_primaryExch, p_includeExpired,
                    p_secIdType, p_secId);
		return cont;	
	}
	
	private static Contract getStockToQuote(Investment inv) {	
		String p_secType=SecType.STK.toString();	
		String p_symbol=inv.getUnder().getTicker();

		int p_conId = 0;
		
		String p_expiry="";		
		double p_strike=0.0;	
		String p_right=""; 	
		
		String p_multiplier=""; 
		String p_exchange="SMART";		
		String p_primaryExch="ISLAND";
		String p_currency="USD";
		String p_localSymbol="";
		String p_tradingClass="";
		ArrayList<ComboLeg> p_comboLegs=new ArrayList<ComboLeg>();
		boolean p_includeExpired=false;
		String p_secIdType="";
		String p_secId="";
		
		Contract cont = new Contract();
		cont = new Contract(p_conId, p_symbol, p_secType, p_expiry,
                    p_strike, p_right, p_multiplier,
                    p_exchange, p_currency, p_localSymbol, p_tradingClass,
                    p_comboLegs, p_primaryExch, p_includeExpired,
                    p_secIdType, p_secId);
		return cont;
	}
}
