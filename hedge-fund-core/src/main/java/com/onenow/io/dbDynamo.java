package com.onenow.io;

import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.onenow.admin.InitAmazon;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.data.DynamoDBUtils;

public class dbDynamo {

	private static AmazonDynamoDB dynamoDB;
	 private static DynamoDBUtils dynamoDBUtils;
	 
	public dbDynamo() {
	}
	
	public dbDynamo(Region region) {
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
