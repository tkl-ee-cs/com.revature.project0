package com.project0.Bank;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
	
	static Connection connection;
	
	public static Connection dbconnect() throws SQLException {
		//Connection connection;
			//System.out.println("Connecting to PostgreSQL database...");
			return DriverManager.getConnection(
					"jdbc:postgresql://localhost/bank",
					"postgres",
					"postgrespassword"
					);
	}
	
	public static void dbdisconnect() {
		//Connection connection;
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String get_status_w_name(String username) {
		String status = null;
		CallableStatement cstmt;
		
		//System.out.println("Called connectDB get_status_w_name()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL get_status_w_name(?,?)");
			cstmt.setString(1,username);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			cstmt.setString(2, null);
			cstmt.execute();
			status = cstmt.getString(2);
			//System.out.println("Status of account is : "+ status);
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	

	public static String get_type_w_name(String username) {
		String act_type = null;
		CallableStatement cstmt;
		
		//System.out.println("Called connectDB get_type_w_name()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL get_type_w_name(?,?)");
			cstmt.setString(1,username);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			cstmt.setString(2, null);
			cstmt.execute();
			act_type = cstmt.getString(2);
			//System.out.println("Type of account is : "+ act_type);
			return act_type;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return act_type;
	}
	
	public static int get_id_w_name_pw(String username,String password) {
		int uid = 0;
		CallableStatement cstmt;
		
		//System.out.println("Called connectDB get_type_w_name()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL get_id_w_name_pw(?,?,?)");
			cstmt.setString(1,username);
			cstmt.setString(2,password);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			uid = cstmt.getInt(3);
			//System.out.println("Type of account is : "+ act_type);
			return uid;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uid;
	}
	
	public static int set_new_user(String username,String password, int balance) {
		int uid;
		CallableStatement cstmt;
		
		//System.out.println("Called connectDB set_new_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL set_account_wo_user(?,?,?,?,?,?)");
			cstmt.setInt(1,balance);
			cstmt.setString(2,username);
			cstmt.setString(3,password);
			cstmt.setString(4,"customer");
			cstmt.setString(5,"pending");
			cstmt.registerOutParameter(6, java.sql.Types.INTEGER);
			cstmt.setInt(6, 0);
			cstmt.execute();
			uid = cstmt.getInt(6);
			//System.out.println("ID of account is : " + uid);
			return uid;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
