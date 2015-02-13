package com.onenow.investor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ib.client.ComboLeg;
import com.ib.client.Types.SecType;
import com.onenow.finance.Underlying;

public class ContractFactory {

	public ContractFactory() {
		
	}
	
	
	public static void addChannel(List<Channel> channels, Contract contract) {

		String today = new DateTime().getToday();

		if(contract.symbol().equals("SPX")) { 	// for SecType.IND and SecType.OPT
			
			Channel spx = new Channel(contract);
			channels.add(spx);
			
			
			// LOOKING FOR OVER-REACTION of 30% of range within 3 days
			// AFTER HI/LO that subsides next day, with lower closing		
			// SPX
			
			// SLOW CHANNEL
			spx.addRecent(today);  
			spx.addResistance("2015-02-06"); 
			spx.addSupport("2015-02-02");
			spx.addResistance("2015-01-22");
			spx.addSupport("2015-01-15");
			spx.addResistance("2015-01-08");
			spx.addSupport("2015-01-06");
			// *** 30-day trend change
			spx.addResistance("2014-12-29");  
			spx.addSupport("2014-12-16"); // fundamentals t2 low 
			spx.addResistance("2014-12-05"); 
			// November: mild market 
			spx.addSupport("2014-10-15"); // CRASH
			spx.addResistance("2014-09-18"); 
			spx.addSupport("2014-08-07"); // fundamentals t1 low
			spx.addResistance("2014-07-24");				
		}
		
		if(contract.symbol().equals("RUT")) { 	// for SecType.IND and SecType.OPT
			
			Channel rut = new Channel(contract);
			channels.add(rut);
			
			// MIDDLEPATH CHANNEL
			// repeated confrontation resistance/support over 3 months
			rut.addRecent(today);  
			rut.addSupport("2015-02-02"); 
			rut.addResistance("2013-12-31"); 			
			rut.addSupport("2013-12-16"); 			
			rut.addResistance("2013-09-03"); 			

		}
		
		
	}
	
	public Contract optionToQuote(String name) {
		String p_secType=SecType.OPT.toString();	

		String p_symbol=name;
		String p_exchange="SMART";		// or "BEST"; "Comp Exchange"???
		
		int p_conId=0;
		
		String p_expiry="20150319";		// "20120316"
		double p_strike=2090.0;	// 20.0
		String p_right="Call"; 	// "P" ... "Put/call"
		
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
	
	
	public Contract indexToQuote(String name) {
		String p_secType=SecType.IND.toString();	

		String p_symbol="";
		String p_exchange="";
		
		if(name.equals("SPX")) {
			p_symbol="SPX";
			p_exchange="CBOE";		// or "BEST"; "Comp Exchange"???
		}
		if(name.equals("RUT")) {
			p_symbol="RUT";
			p_exchange="RUSSELL";		// or "BEST"; "Comp Exchange"???
		}
		if(name.equals("NDX")) {
			p_symbol="NDX";
			p_exchange="NASDAQ";		// or "BEST"; "Comp Exchange"???
		}
		
		
		int p_conId=0;
		
		String p_expiry="";		// "20120316"
		double p_strike=0.0;	// 20.0
		String p_right=""; 	// "P" ... "Put/call"
		
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
	
	public Contract stockToQuote() {	
		int p_conId=0;
		String p_symbol="IBM";
		String p_secType=SecType.STK.toString();	// "OPT"
		
		String p_expiry="";		// "20120316"
		double p_strike=0.0;	// 20.0
		String p_right=""; 	// "P" ... "Put/call"
		
		String p_multiplier="100";
		String p_exchange="SMART";		// or "BEST"; "Comp Exchange"???
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
}
