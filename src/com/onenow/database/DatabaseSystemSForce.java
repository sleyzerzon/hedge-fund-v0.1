package com.onenow.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.sforce.soap.enterprise.sobject.Day__c;
import com.sforce.soap.enterprise.sobject.Log__c;
import com.sforce.soap.enterprise.sobject.Market__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class DatabaseSystemSForce implements DatabaseSystem {

	private static final ConnectorConfig configAuth = new ConnectorConfig();
	private static final ConnectorConfig configEnt = new ConnectorConfig();
	private static EnterpriseConnection loginConnection;
	private static EnterpriseConnection entConnection;
	private static LoginResult loginResult;

	public DatabaseSystemSForce() {
		try {
			doLogin();
			doConnect();
		} catch (ConnectionException e) {
			e.printStackTrace();
			System.out.println("Connected to Salesforce! OOPS");
		}		
	}

	// REDUCTIONS
	public List<Reduction__c> getReductions() throws ConnectionException {
		List<Reduction__c> reductions = new ArrayList<Reduction__c>();
		QueryResult qResult = query("SELECT Name, Nickname__c, SLA_Mode__c, " + 
									"SLA_Target__c FROM Reduction__c");
		boolean done = false;
		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Reduction__c theRecord = (Reduction__c) records[i];
				reductions.add(theRecord);
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

	public Reduction__c newRedution(String name, String mode, Double target) {
		Reduction__c red = new Reduction__c();
		red.setName(name);
		red.setSLA_Mode__c(mode);
		red.setSLA_Target__c(target);
		// SObject[] object = new SObject[] { red };
		// return object;
		return red;
	}

	// DAYS
	public List<Day__c> getDays() throws ConnectionException {
		List<Day__c> days = new ArrayList<Day__c>();
		QueryResult qResult = query("SELECT Name FROM Day__c");
		boolean done = false;
		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Day__c theRecord = (Day__c) records[i];
				days.add(theRecord);
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return days;
	}

	public Day__c[] newDay(Date date, Double duration, int count,
			Double ondemandRate, Double spotRate, Double spent) {
		Day__c day = new Day__c();
		day.setDate__c(new GregorianCalendar(date.getYear(), date.getMonth(),
				date.getDay()));
		day.setDuration_hs__c(duration);

		day.setFinished_Count_d_Count__c(new Double(count));
		day.setRate_On_Demand_Market__c(ondemandRate);
		day.setRate_Spot__c(spotRate);
		day.setSpent__c(spent);
		// ?
		return new Day__c[] { day };
	}

	// MARKETS
	public List<Market__c> getMarkets() throws ConnectionException {
		List<Market__c> markets = new ArrayList<Market__c>();
		QueryResult qResult = query("SELECT Name, Cloud__c, Instance_Type__c, Operating_System__c, Pricing_Model__c, Reduction__c, Region__c, Zone__c FROM Market__c");
		boolean done = false;
		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Market__c theRecord = (Market__c) records[i];
				markets.add(theRecord);
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return markets;
	}

	public Market__c[] newMarket(String cloud, String instanceType,
			String operatingSystem, String pricingModel, String reduction,
			String region, String zone) {
		Market__c market = new Market__c();
		market.setCloud__c(cloud);
		market.setInstance_Type__c(instanceType);
		market.setOperating_System__c(operatingSystem);
		market.setPricing_Model__c(pricingModel);
		market.setReduction__c(reduction);
		market.setRegion__c(region);
		market.setZone__c(zone);
		return new Market__c[] { market };
	}

	// CLOUDS
	public List<Cloud__c> getClouds() throws ConnectionException {
		List<Cloud__c> clouds = new ArrayList<Cloud__c>();
		QueryResult qResult = query("SELECT Name, Nickname__c, Enabled__c, Access_Key__c, Account__c, Password__c, Provider__c, Secret_Key__c, KeyPath__c, User__c FROM Cloud__c");
		boolean done = false;
		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Cloud__c theRecord = (Cloud__c) records[i];
				clouds.add(theRecord);
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

	// ACCOUNTS
	public List<Account> getAccounts() throws ConnectionException {
		List<Account> accounts = new ArrayList<Account>();
		QueryResult qResult = query("SELECT Name FROM Account");
		boolean done = false;
		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Account theRecord = (Account) records[i];
				accounts.add(theRecord);
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

	// SYSTEM
	public System__c getSystem(String kind) throws ConnectionException {
		List<System__c> sysConfigs = new ArrayList<System__c>();
		QueryResult qResult = query("SELECT Name, Nickname__c, Kind__c, Enabled__c, Host__c, Port__c, User__c, Password__c, Token__c FROM System__c");
		boolean done = false;
		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				System__c theRecord = (System__c) records[i];
				sysConfigs.add(theRecord);
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return sysConfigs.get(0); // TODO: instead, get one of the Set for Kind,
									// that is Enabled
	}

	// LOG
	public List<Log__c> getLogs() throws ConnectionException {
		List<Log__c> logEntries = new ArrayList<Log__c>();
		QueryResult qResult = query("SELECT Name, Date__c, Source__c, Kind__c, Description__c FROM Log__c");
		boolean done = false;
		while (!done) {
			SObject[] records = qResult.getRecords();
			for (int i = 0; i < records.length; ++i) {
				Log__c theRecord = (Log__c) records[i];
				logEntries.add(theRecord);
			}
			if (qResult.isDone()) {
				done = true;
			} else {
				qResult = getEntConnection().queryMore(
						qResult.getQueryLocator());
			}
		}
		return logEntries;
	}

	public Log__c[] newLog(String source, String kind, String desc) {
		Log__c log = new Log__c();
		log.setSource__c(source);
		log.setKind__c(kind);
		log.setDescription__c(desc);
		return new Log__c[] { log };
	}

	// PRIVATE
	private static void doLogin() throws ConnectionException {
		getConfigAuth().setAuthEndpoint(ConstantsDatabase.URL);
		getConfigAuth().setServiceEndpoint(ConstantsDatabase.URL);
		getConfigAuth().setManualLogin(true);
		setLoginConnection(new EnterpriseConnection(getConfigAuth()));
		setLoginResult(getLoginConnection().login(ConstantsDatabase.USERNAME,
				ConstantsDatabase.PASSWORD + ConstantsDatabase.TOKEN));
	}

	private static void doConnect() throws ConnectionException {
		getConfigEnt().setSessionId(getLoginResult().getSessionId());
		String serviceEndpoint = getLoginResult().getServerUrl();
		getConfigEnt().setServiceEndpoint(serviceEndpoint);
		getLoginConnection().setSessionHeader(getLoginResult().getSessionId());
		setEntConnection(new EnterpriseConnection(getConfigEnt()));
	}

	private QueryResult query(String query) throws ConnectionException {
		QueryResult qResult = getEntConnection().query(query);
		return qResult;
	}

	private void create(SObject[] objects) throws ConnectionException {
		SaveResult[] results = getEntConnection().create(objects);
		for (SaveResult result : results) {
			// System.out.println("Create: " + result.getSuccess());
		}
	}

	private void describeClass(String table) throws ConnectionException {
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

	// // Use: create(red1);
	// @Override
	// private void create(SObject[] objects) throws ConnectionException {
	// getSForce().createSForce(objects);
	// }
	//
	//
	// @Override
	// // Use: describeClass("Reduction__c");
	// private void describeClass(String table) throws ConnectionException {
	// getSForce().describeClassSForce(table);
	// }
	//
	// // PRIVATE
	// private void init() throws ConnectionException {
	// setSForce(new DatabaseSystemSForce());
	// }
	//
	// // Use: query("SELECT FirstName, LastName FROM Contact");
	// private static QueryResult query(String query) throws ConnectionException
	// {
	// return getSForce().query(query);
	// }
	//
	// private static void retrieve() {
	// // TODO
	// }
	//
	// private static void update() {
	// // TODO
	// }
	//
	// private static void upsert() {
	// // TODO
	// }

	
	// PRIVATE
	
	// PRINT
	public String toString() {
		String s = "";
		return s;
	}
	
	// TEST
	
	
	// SET GET
	private static EnterpriseConnection getLoginConnection() {
		return loginConnection;
	}

	private static void setLoginConnection(EnterpriseConnection loginConnection) {
		DatabaseSystemSForce.loginConnection = loginConnection;
	}

	private static EnterpriseConnection getEntConnection() {
		return entConnection;
	}

	private static void setEntConnection(EnterpriseConnection entConnection) {
		DatabaseSystemSForce.entConnection = entConnection;
	}

	private static LoginResult getLoginResult() {
		return loginResult;
	}

	private static void setLoginResult(LoginResult loginResult) {
		DatabaseSystemSForce.loginResult = loginResult;
	}

	private static ConnectorConfig getConfigAuth() {
		return configAuth;
	}

	private static ConnectorConfig getConfigEnt() {
		return configEnt;
	}

}
