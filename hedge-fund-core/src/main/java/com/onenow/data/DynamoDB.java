package com.onenow.data;

import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.onenow.admin.InitAmazon;

public class DynamoDB {

	private static AmazonDynamoDB dynamo;
			
	public DynamoDB() {
	}
	
	public DynamoDB(Region region) {
		this.dynamo = InitAmazon.getDynamo(region);
	}

}
