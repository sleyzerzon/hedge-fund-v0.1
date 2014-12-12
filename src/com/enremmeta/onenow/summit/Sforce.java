package com.enremmeta.onenow.summit;

import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.PicklistEntry;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectorConfig;

//import com.sforce.ws.tools.wsdlc;

public class Sforce {

	final static String USERNAME = "pablo.personal@icloud.com";
	final static String PASSWORD = "p*blo433"; // Hard coding passwords in
												// source
	// files is a bad practice. This is
	// only a sample.
	final static String TOKEN = "oZsiDH5iH8HKyyHUqrHAfCZOy";

	final static String URL = "https://login.salesforce.com/services/Soap/c/32.0";

	public Sforce() {

	}

	public static void main(String args[]) throws Exception {
		final ConnectorConfig configAuth = new ConnectorConfig();
		configAuth.setAuthEndpoint(URL);
		configAuth.setServiceEndpoint(URL);
		configAuth.setManualLogin(true);
		EnterpriseConnection loginCon = new EnterpriseConnection(configAuth);
		LoginResult loginRes = loginCon.login(USERNAME, PASSWORD + TOKEN);

		final ConnectorConfig configEnt = new ConnectorConfig();
		configEnt.setSessionId(loginRes.getSessionId());
		String serviceEndpoint = loginRes.getServerUrl();
		configEnt.setServiceEndpoint(serviceEndpoint);
		loginCon.setSessionHeader(loginRes.getSessionId());
		EnterpriseConnection entCon = new EnterpriseConnection(configEnt);

		DescribeSObjectResult dsor = entCon.describeSObject("Reduction__c");
		Field fields[] = dsor.getFields();
		for (Field field : fields) {
			for (PicklistEntry ple : field.getPicklistValues()) {
				System.out.println(ple.getValue());
			}
		}

		Reduction__c red1 = new Reduction__c();
		red1.setName("Reduce this");
		red1.setSLA_Mode__c("Time (hours/each)");
		red1.setSLA_Target__c(311.22);
		Reduction__c red2 = new Reduction__c();
		red2.setName("Sumit Yak");
		red2.setSLA_Mode__c("Cost ($/each)");
		red1.setSLA_Target__c(100500.33);

		SaveResult[] saveRess = entCon.create(new SObject[] { red1, red2 });
		for (SaveResult saveRes : saveRess) {
			System.out.println(saveRes);
		}
		final ConnectorConfig configMeta = new ConnectorConfig();
		configMeta.setServiceEndpoint(loginRes.getMetadataServerUrl());
		configMeta.setSessionId(loginRes.getSessionId());
		MetadataConnection metaCon = new MetadataConnection(configMeta);
		// DescribeMetadataResult dmr = metaCon.describeMetadata(25);
		// DescribeMetadataObject dmos[] = dmr.getMetadataObjects();
		// for (DescribeMetadataObject dmo : dmos) {
		// System.out.println(dmo);
		// }
	}
}
