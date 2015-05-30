package com.onenow.data;

import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.onenow.admin.InitAmazon;

public class DynamoDB {

	private static AmazonDynamoDB dynamoDB;
	 private static DynamoDBUtils dynamoDBUtils;
	 
	public DynamoDB() {
	}
	
	public DynamoDB(Region region) {
		this.dynamoDB = InitAmazon.getDynamo(region);
	    this.dynamoDBUtils = new DynamoDBUtils(dynamoDB);
	}
		
	public static void createTable(String tableName) {
		
		dynamoDBUtils.createCountTableIfNotExists(tableName);
        
        System.out.println("DynamoDB table is ready for use: " + tableName);
        
	}

	public static DynamoDBCountPersister getCountPersister(String tableName) {
		
		DynamoDBCountPersister persister =
                new DynamoDBCountPersister(dynamoDBUtils.createMapperForTable(tableName));
		
		return persister;
	}

}
