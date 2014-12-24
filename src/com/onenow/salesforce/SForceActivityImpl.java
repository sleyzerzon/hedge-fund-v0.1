package com.onenow.salesforce;

import java.util.ArrayList;
import java.util.List;

import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.PicklistEntry;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Cloud__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SForceActivityImpl implements SForceActivity {

	private final static ConnectorConfig configAuth = new ConnectorConfig();
	private final static ConnectorConfig configEnt = new ConnectorConfig();
	private static EnterpriseConnection loginConnection;
	private static EnterpriseConnection entConnection;
	private static LoginResult loginResult;

	private void init() throws ConnectionException {
		doLogin();
		doConnect();
	}
	private static void doLogin() throws ConnectionException {
		getConfigAuth().setAuthEndpoint(Constants.URL);
		getConfigAuth().setServiceEndpoint(Constants.URL);
		getConfigAuth().setManualLogin(true);
		setLoginConnection(new EnterpriseConnection(getConfigAuth()));
		setLoginResult(getLoginConnection().login(Constants.USERNAME,
				Constants.PASSWORD + Constants.TOKEN));
	}

	private static void doConnect() throws ConnectionException {
		getConfigEnt().setSessionId(getLoginResult().getSessionId());
		String serviceEndpoint = getLoginResult().getServerUrl();
		getConfigEnt().setServiceEndpoint(serviceEndpoint);
		getLoginConnection().setSessionHeader(getLoginResult().getSessionId());
		setEntConnection(new EnterpriseConnection(getConfigEnt()));
	}
	
	@Override
	public List<System__c> getSystem() throws ConnectionException {
		init();
		
		List<System__c> sysConfigs = new ArrayList<System__c>();
		QueryResult qResult = query("SELECT Name, Nickname__c, Host__c, Port__c, User__c, Password__c, Token__c FROM System__c");
		boolean done = false;

		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				System__c theRecord = (System__c) records[i];
				sysConfigs.add(theRecord);
				System.out.println("Found: " + theRecord.getName());
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return sysConfigs;		
	}
	
	@Override
	public List<Account> getAccounts() throws ConnectionException {
		init();
		
		List<Account> accounts = new ArrayList<Account>();
		QueryResult qResult = query("SELECT Name FROM Account");
		boolean done = false;

		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Account theRecord = (Account) records[i];
				accounts.add(theRecord);
				System.out.println("Found: " + theRecord.getName());
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return accounts;
	}
	@Override
	public List<Cloud__c> getClouds() throws ConnectionException {
		init();
		
		List<Cloud__c> clouds = new ArrayList<Cloud__c>();
		QueryResult qResult = query("SELECT Name, Access_Key__c, Account__c, Nickname__c, Password__c, Provider__c, Secret_Key__c, KeyPath__c, User__c FROM Cloud__c");
		boolean done = false;

		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Cloud__c theRecord = (Cloud__c) records[i];
				clouds.add(theRecord);
				System.out.println("Found: " + theRecord.getName());
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return clouds;
	}

	@Override
	public List<Reduction__c> getReductions() throws ConnectionException {
		init();
		
		List<Reduction__c> reductions = new ArrayList<Reduction__c>();
		QueryResult qResult = query("SELECT Name, Nickname__c, SLA_Mode__c, SLA_Target__c FROM Reduction__c");
		boolean done = false;

		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Reduction__c theRecord = (Reduction__c) records[i];
				reductions.add(theRecord);
				System.out.println("Found: " + theRecord.getName());
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return reductions;
	}

	// Use: create(red1);
	@Override
	public void create(SObject[] objects) throws ConnectionException {
		init();
		
		SaveResult[] results = getEntConnection().create(objects);
		for (SaveResult result : results) {
			// System.out.println("Create: " + result.getSuccess());
		}
	}

	// Use:
	// SObject[] red1 = newRedution("Pablo Reduce this", "Time (hours/each)", 322.98);
	// SObject[] red2 = newRedution("Pablo Yak this", "Cost ($/each)", 2322.98);
	private static SObject[] newRedution(String name, String mode, Double target) {
		Reduction__c red = new Reduction__c();
		red.setName(name);
		red.setSLA_Mode__c(mode);
		red.setSLA_Target__c(target);
		SObject[] object = new SObject[] { red };
		return object;
	}

	// Use: describeClass("Reduction__c");
	private static void describeClass(String table) throws ConnectionException {
		DescribeSObjectResult description = getEntConnection().describeSObject(
				table);
		Field fields[] = description.getFields();
		for (Field field : fields) {
			for (PicklistEntry pickList : field.getPicklistValues()) {
				System.out.println(pickList.getValue()); // get picklist items
															// from here
			}
		}
	}

	// Use: query("SELECT FirstName, LastName FROM Contact");
	private static QueryResult query(String query) throws ConnectionException {
		QueryResult qResult = getEntConnection().query(query);
		System.out.println("Number of objects: " + qResult.getSize());
		return qResult;
	}
	private static void retrieve() {

	}

	private static void update() {

	}

	private static void upsert() {

	}

	
	private static EnterpriseConnection getLoginConnection() {
		return loginConnection;
	}

	private static void setLoginConnection(EnterpriseConnection loginCon) {
		loginConnection = loginCon;
	}

	private static EnterpriseConnection getEntConnection() {
		return entConnection;
	}

	private static void setEntConnection(EnterpriseConnection entCon) {
		entConnection = entCon;
	}

	private static ConnectorConfig getConfigAuth() {
		return configAuth;
	}

	private static ConnectorConfig getConfigEnt() {
		return configEnt;
	}

	private static LoginResult getLoginResult() {
		return loginResult;
	}

	private static void setLoginResult(LoginResult loginRes) {
		loginResult = loginRes;
	}
}
