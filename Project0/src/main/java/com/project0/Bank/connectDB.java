package com.project0.Bank;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CREATE PENDING NEW USER AND MATCHING PENDING BANKING ACCOUNT
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SET ACCOUNT USING USERNAME
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int set_account_w_user(String username, int balance) {
		int uid;
		CallableStatement cstmt;
		
		//System.out.println("Called connectDB set_new_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL set_account_w_name(?,?,?)");
			cstmt.setInt(1,balance);
			cstmt.setString(2,username);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			uid = cstmt.getInt(3);
			//System.out.println("ID of account is : " + uid);
			return uid;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET BANK ACCOUNTS USING USERNAME
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<BankAccount> get_accounts_w_name(String username) {
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<BankAccount> accts = new ArrayList<BankAccount>();
		
		try(Connection connection = dbconnect()){
			String stmt = "	SELECT a.account_id,a.balance,a.status"
					+ "	FROM user_accounts ua RIGHT JOIN accounts a ON ua.account_id = a.account_id"
					+ "	INNER JOIN users u ON ua.user_id = u.user_id"
					+ "	WHERE u.user_name = ?;";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setString(1,username);
			boolean isResultSet = pstmt.execute();
			if(isResultSet) {rs = pstmt.executeQuery();}
			else {return null;}
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
				accts.add(new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3)));
	        }
			return accts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET BANK ACCOUNT USING ACCOUNT ID
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static BankAccount get_account_w_aid(int id) {
		PreparedStatement pstmt;
		ResultSet rs;
		BankAccount ba = new BankAccount(0,0,"");
		
		//System.out.println("Called connectDB set_new_user()");
		try(Connection connection = dbconnect()){
			String stmt = "	SELECT a.account_id,a.balance,a.status FROM accounts a WHERE a.account_id = ?";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,id);
			boolean isResultSet = pstmt.execute();
			if(isResultSet) {rs = pstmt.executeQuery();}
			else {return null;}
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getString(2));
	        	ba= new BankAccount(id,rs.getInt(1),rs.getString(2));
	        }
        	return ba;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SET DEPOSIT AMOUNT TO BANK ACCOUNT USING ACCOUNT ID
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int set_account_w_deposit(int id, int balance) {
		CallableStatement cstmt;
		int transaction_id = 0;
		BankAccount ba = new BankAccount(0,0,"");
		
		//System.out.println("Called connectDB set_new_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL deposit(?,?,?)");
			cstmt.setInt(1,id);
			cstmt.setInt(2,balance);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			transaction_id = cstmt.getInt(3);
			//System.out.println("Transaction id: " + transaction_id); 
        	return transaction_id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SET DEPOSIT AMOUNT TO BANK ACCOUNT USING ACCOUNT ID
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int set_account_w_withdraw(int id, int amount) {
		CallableStatement cstmt;
		int transaction_id = 0;
		
		//System.out.println("Called connectDB set_new_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL withdraw(?,?,?)");
			cstmt.setInt(1,id);
			cstmt.setInt(2,amount);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			transaction_id = cstmt.getInt(3);
			System.out.println("Transaction id: " + transaction_id); 
        	return transaction_id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET ACCOUNTS WITH PENDING TRANSFERS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<BankAccount> get_accounts_w_pending_transfer(int id) {
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<BankAccount> accts = new ArrayList<BankAccount>();
		String stmt;
		
		//System.out.println("Called connectDB set_new_user()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT DISTINCT a.account_id, a.balance, a.status "
					+ "FROM accounts a INNER JOIN transaction_logs t ON t.src_account = a.account_id "
					+ "WHERE t.status = 'pending' AND t.src_account = ?";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
				accts.add(new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3)));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accts;
	}
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET ACTIVE ACCOUNT EXISTANCE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static BankAccount get_account_existance(int id) {
		PreparedStatement pstmt;
		ResultSet rs;
		String stmt;
		BankAccount ba = null;
		
		//System.out.println("Called connectDB set_new_user()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT a.account_id, a.balance, a.status "
					+ "FROM accounts a WHERE a.status = 'active' AND account_id = ?";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
	        	ba = new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ba;
	}
}
