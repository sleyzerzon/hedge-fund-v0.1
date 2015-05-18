package com.onenow.database;

import java.sql.*;

import com.onenow.execution.NetworkConnection;
import com.onenow.execution.NetworkService;

public class RelationalDB {
// https://www.youtube.com/watch?v=2i4t-SL1VsU
	
	private static NetworkService rdbmsService = new NetworkConnection().rdbms;

	public static void main(String[] args) throws ClassNotFoundException {
				
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection myCon = DriverManager.getConnection(	rdbmsService.protocol+"://"+rdbmsService.URL+":"+rdbmsService.port.toString()+"/"+rdbmsService.endPoint, 
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
	}

}
