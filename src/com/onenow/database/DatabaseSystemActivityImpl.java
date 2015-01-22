package com.onenow.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Cloud__c;
import com.sforce.soap.enterprise.sobject.Day__c;
import com.sforce.soap.enterprise.sobject.Log__c;
import com.sforce.soap.enterprise.sobject.Market__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;


public class DatabaseSystemActivityImpl implements DatabaseSystemActivity {

	private static DatabaseSystemSForce SForce = new DatabaseSystemSForce();

	// CONSTRUCTOR
	public DatabaseSystemActivityImpl() {
		
	}
	
	// PUBLIC
	@Override
	public List<Reduction__c> getReductions() throws ConnectionException {
		return getSForce().getReductions();		
	}

	@Override
	public Reduction__c newRedution(String name, String mode, Double target) throws ConnectionException {
		return (getSForce().newRedution(name, mode, target));
	}	

	@Override
	public List<Day__c> getDays() throws ConnectionException {
		return(getSForce().getDays());		
	}
	
	@Override
	public Day__c[] newDay(Date date, Double duration, int count, Double ondemandRate, Double spotRate, Double spent) {
		return(getSForce().newDay(date, duration, count, ondemandRate, spotRate, spent));
	}
	
	@Override
	public List<Market__c> getMarkets() throws ConnectionException {
		return(getSForce().getMarkets());		
	}
	
	@Override
	public Market__c[] newMarket(	String cloud, String instanceType, String operatingSystem, 
									String pricingModel, String reduction, String region, String zone) {
		
		return getSForce().newMarket(cloud, instanceType, operatingSystem, pricingModel, reduction, region, zone);
	}

	@Override
	public List<Cloud__c> getClouds() throws ConnectionException {
		return(getSForce().getClouds());
	}
	
	@Override
	public List<Account> getAccounts() throws ConnectionException {
		return(getSForce().getAccounts());		
	}

	@Override
	public System__c getSystemTSDB() throws ConnectionException {
		return(getSForce().getSystem("TSDB"));  // TODO: select the right one
	}

	@Override
	public System__c getSystemSWF() throws ConnectionException {
		return(getSForce().getSystem("SWF"));	// TODO: select the right one
	}
	
	@Override
	public System__c getSystemPricing() throws ConnectionException {
		return(getSForce().getSystem("Pricing"));	// TODO: select the right one
	}
		
	@Override
	public Log__c[] newLog(String source, String kind, String desc) {
		return(getSForce().newLog(source, kind, desc));
	}

	// PRIVATE
	private String toStringReductions(List<Reduction__c> reds) {
		String s="";
		for(Reduction__c red:reds){
			s = s + "Nickname " + red.getNickname__c() + ". " + 
					"Mode " + red.getSLA_Mode__c() + ". " + 
					"Target " + red.getSLA_Target__c() + "\n";
		}
		return s;
	}
	private String toStringMarkets(List<Market__c> markets) {
		String s="";
		for(Market__c market:markets) {
			s = s + "Name " + market.getName() + ". " + 
					"Region " + market.getRegion__c() + ". " + 
					"Zone " + market.getZone__c() + "\n";
		}
		return s;
	}
	private String toStringDays(List<Day__c> days) {
		String s="";
		for(Day__c day:days) {
			s = s + "Spent $" + day.getSpent__c() + ". " + 
					"Finished #" + day.getFinished_Count_d_Count__c() + "\n";
		}
		return s;		
	}
	private String toStringClouds(List<Cloud__c> clouds) {
		String s="";
		for(Cloud__c cloud:clouds) {
			s = s + "Nickname " + cloud.getNickname__c() + ". " +
					"Cloud " + cloud.getAccess_Key__c() + "\n";
		}
		return s;				
	}
	private String toStringAccounts(List<Account> accounts) {
		String s="";
		for(Account account:accounts) {
			s = s + "Name "+ account.getName() + "\n";
		}
		return s;						
	}
	private String toStringSystem(System__c sys) {
		String s="";
		s = s + "Host " + sys.getHost__c();
		return s;								
	}
	
	
	// PRINT
	public String toString() {
		List<Reduction__c> reds =  new ArrayList<Reduction__c>();
		List<Market__c> markets = new ArrayList<Market__c>();
		List<Day__c> days = new ArrayList<Day__c>(); 
		List<Cloud__c> clouds = new ArrayList<Cloud__c>();
		List<Account> accounts = new ArrayList<Account>();
		System__c tsdb = new System__c();
		System__c pricing = new System__c();				
		try {
			reds =  getReductions();
			markets = getMarkets();
			days = getDays(); 
			clouds = getClouds();
			accounts = getAccounts();
			tsdb = getSystemTSDB();
			pricing = getSystemPricing();			
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		String s = "";
		s = s + "Reductions: " + "\n" + toStringReductions(reds);
		s = s + "Markets: " + "\n" + toStringMarkets(markets);
		s = s + "Days: " + "\n" + toStringDays(days);
		s = s + "Clouds: " + "\n" + toStringClouds(clouds);
		s = s + "Accounts: " + "\n" + toStringAccounts(accounts);
		s = s + "TSDB: " + "\n" + toStringSystem(tsdb);
		s = s + "Pricing: " + "\n" + toStringSystem(pricing);
		System.out.println(s);
		return s;
	}

	
	// TEST
	
	
	// SET GET
	private static DatabaseSystemSForce getSForce() {
		return SForce;
	}

	private void setSForce(DatabaseSystemSForce sforce) {
		this.SForce = SForce;
	}
	
	

}
