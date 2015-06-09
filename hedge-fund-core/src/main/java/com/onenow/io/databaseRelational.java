package com.onenow.io;

import java.sql.*;

import com.onenow.admin.NetworkConfig;
import com.onenow.admin.NetworkService;

public class databaseRelational {
// https://www.youtube.com/watch?v=2i4t-SL1VsU
	
	public static void main(String[] args) throws ClassNotFoundException {
				
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			NetworkService rdbmsService = NetworkConfig.getRDBMS();
			
			Connection myCon = DriverManager.getConnection(	rdbmsService.protocol+"://"+rdbmsService.URI+":"+rdbmsService.port+"/"+rdbmsService.endPoint, 
															rdbmsService.user, rdbmsService.pass);
			
			Statement myStat = myCon.createStatement();
			
			ResultSet myRs = myStat.executeQuery("select * from cloud");
			
			while(myRs.next()) {
				System.out.println(myRs.getString("name") + "\t" + myRs.getString("user") + "\t" + myRs.getString("pass"));
			}
			
		} catch (SQLException e) {
			System.out.println("COULD NOT CONNECT TO RELATIONAL DATABASE");
			e.printStackTrace();
		}
		
		// TODO: while loop trying... ends in System.out.println("CONNECTED TO DB!");
	}

}
