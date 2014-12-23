package com.onenow.summit;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Talk JDBC via tunnel.
 * 
 * ssh -o ServerAliveInterval=10 -i ~/.ssh/enrado2.pem -N -L
 * 10000:localhost:10000 hadoop@ec2-54-235-51-113.compute-1.amazonaws.com
 * 
 * @see HiveServer
 * 
 * @see
 */
public class JdbcFacade {

	public JdbcFacade(String host) {
		super();
		this.host = host;
	}

	public JdbcFacade() {
		super();
		this.host = "localhost";
	}

	private String host;

	private Connection con;

	public void connect() throws Exception {
		System.out
				.println("Is this running:  ssh -o ServerAliveInterval=10 -i ~/.ssh/enrado2.pem  -N -L 10000:localhost:10000 hadoop@ec2-54-235-51-113.compute-1.amazonaws.com");
		Class.forName("com.amazon.hive.jdbc4.HS2Driver");
		con = DriverManager.getConnection("jdbc:hive2://" + this.host
				+ ":10000/default");

		// Test connection
		DatabaseMetaData dmd = con.getMetaData();
		ResultSet rs = dmd.getTables(null, null, null, null);
		Logger.log("Found tables:");
		while (rs.next()) {
			Logger.log("\t" + rs.getString("TABLE_NAME"));
		}
	}
}
