package com.onenow.database;

import java.util.List;

import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Cloud__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;


public class DatabaseSystemActivityImpl implements DatabaseSystemActivity {

	private static DatabaseSystemSForce SForce;

	private void init() throws ConnectionException {
		setSForce(new DatabaseSystemSForce());
	}
	
	@Override
	public List<System__c> getSystem() throws ConnectionException {
		init();
		return(getSForce().getSystem());
		}
		
	@Override
	public List<Account> getAccounts() throws ConnectionException {
		init();
		return(getSForce().getAccounts());		
	}
		
	@Override
	public List<Cloud__c> getClouds() throws ConnectionException {
		init();
		return(getSForce().getCloudsSForce());
	}
	
	@Override
	public List<Reduction__c> getReductions() throws ConnectionException {
		init();
		return(getSForce().getReductionsSForce());		
	}
	
	// Use: create(red1);
	@Override
	public void create(SObject[] objects) throws ConnectionException {
		init();
		getSForce().createSForce(objects);
	}

	// Use:
	// SObject[] red1 = newRedution("Pablo Reduce this", "Time (hours/each)", 322.98);
	// SObject[] red2 = newRedution("Pablo Yak this", "Cost ($/each)", 2322.98);
	@Override
	public SObject[] newRedution(String name, String mode, Double target) throws ConnectionException {
		init();
		return (getSForce().newRedutionSForce(name, mode, target));
	}	

	@Override
	// Use: describeClass("Reduction__c");
	public void describeClass(String table) throws ConnectionException {
		init();
		getSForce().describeClassSForce(table);
	}
	
	// PRIVATE
	// Use: query("SELECT FirstName, LastName FROM Contact");
	private static QueryResult query(String query) throws ConnectionException {
		return getSForce().query(query);
	}	
	
	private static void retrieve() {
		// TODO
	}

	private static void update() {
		// TODO
	}

	private static void upsert() {
		// TODO
	}

	// SET GET
	private static DatabaseSystemSForce getSForce() {
		return SForce;
	}

	private void setSForce(DatabaseSystemSForce sforce) {
		this.SForce = SForce;
	}

	
	

}
