package com.onenow.database;

import java.sql.*;

public class RelationalDB {
// https://www.youtube.com/watch?v=2i4t-SL1VsU
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		// hedge-fund-dev-free.c38uxrs0s2gx.us-east-1.rds.amazonaws.com
		// CREATE TABLE urler(id INT UNSIGNED NOT NULL AUTO_INCREMENT,author VARCHAR(63) NOT NULL,message TEXT,PRIMARY KEY (id))
		
		String driver = "jdbc:mysql://";
		String endpoint = "hedge-fund-dev-free.c38uxrs0s2gx.us-east-1.rds.amazonaws.com";
		String port = ":3306/";
		String db = "core_dev_free";
		String url = driver + endpoint + port + db;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection myCon = DriverManager.getConnection(url, "pablo0000", "pablo777");
			
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
