package com.onenow.database;

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

	private static DatabaseSystemSForce SForce;

	public DatabaseSystemActivityImpl() {
		setSForce(new DatabaseSystemSForce());
	}
	
	@Override
	public List<Reduction__c> getReductions() throws ConnectionException {
		return(getSForce().getReductions());		
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
	public Market__c[] newMarket(cloud, instanceType, operatingSystem, pricingModel, reduction, region, zone) {
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
		return(getSForce().newLog());
	}

	// SET GET
	private static DatabaseSystemSForce getSForce() {
		return SForce;
	}

	private void setSForce(DatabaseSystemSForce sforce) {
		this.SForce = SForce;
	}

	@Override
	public Market__c[] newMarket(String cloud, String instanceType,
			String operatingSystem, String pricingModel, String reduction,
			String region, String zone) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
