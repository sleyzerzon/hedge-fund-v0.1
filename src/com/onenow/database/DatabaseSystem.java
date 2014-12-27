package com.onenow.database;

import java.util.List;

import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Cloud__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;

public abstract interface DatabaseSystem {
	public abstract QueryResult query(String query) throws ConnectionException;
	public abstract List<System__c> getSystem() throws ConnectionException;
	public abstract List<Account> getAccounts() throws ConnectionException;
	public abstract List<Cloud__c> getCloudsSForce() throws ConnectionException;
	public abstract List<Reduction__c> getReductionsSForce() throws ConnectionException;
	public abstract void createSForce(SObject[] objects) throws ConnectionException;
	public abstract SObject[] newRedutionSForce(String name, String mode, Double target);
	public abstract void describeClassSForce(String table) throws ConnectionException;
}
