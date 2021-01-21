package com.project0.Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
	
	static Connection connection;
	
	public static Connection dbconnect() {
		//Connection connection;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/dvdrental",
					"postgres",
					"postgrespassword"
					);
		} catch (SQLException e) {
			System.out.println("Unable to connect");
			e.printStackTrace();
		}
		return connection;
	}
}
