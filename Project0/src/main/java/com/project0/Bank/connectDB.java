package com.project0.Bank;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Timestamp;
import java.util.ArrayList;

public class connectDB {
	
	//static Connection connection;
	
	public static Connection dbconnect() throws SQLException {
		//Connection connection;
			//System.out.println("Connecting to PostgreSQL database...");
			return DriverManager.getConnection(
					"jdbc:postgresql://localhost/bank",
					"postgres",
					"postgrespassword"
					);
	}
	
	public static String get_status_w_name(String username) {
		String status = null;
		CallableStatement cstmt = null;
		
		//System.out.println("Called connectDB get_status_w_name()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL get_status_w_name(?,?)");
			cstmt.setString(1,username);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			cstmt.setString(2, null);
			cstmt.execute();
			status = cstmt.getString(2);
			//System.out.println("Status of account is : "+ status);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return status;
	}
	

	public static String get_type_w_name(String username) {
		String act_type = null;
		CallableStatement cstmt = null;
		
		//System.out.println("Called connectDB get_type_w_name()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL get_type_w_name(?,?)");
			cstmt.setString(1,username);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			cstmt.setString(2, null);
			cstmt.execute();
			act_type = cstmt.getString(2);
			//System.out.println("Type of account is : "+ act_type);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		return act_type;
	}
	
	public static int get_id_w_name_pw(String username,String password) {
		int uid = 0;
		CallableStatement cstmt = null;
		
		//System.out.println("Called connectDB get_id_w_name_pw()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL get_id_w_name_pw(?,?,?)");
			cstmt.setString(1,username);
			cstmt.setString(2,password);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			uid = cstmt.getInt(3);
			//System.out.println("Type of account is : "+ act_type);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return uid;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CREATE PENDING NEW USER AND MATCHING PENDING BANKING ACCOUNT
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int set_new_user(String username,String password, int balance) {
		int uid = -1;
		CallableStatement cstmt = null;
		
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
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return uid;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SET ACCOUNT USING USERNAME
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int set_account_w_user(String username, int balance) {
		int uid = -1;
		CallableStatement cstmt = null;
		
		//System.out.println("Called connectDB set_account_w_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL set_account_w_name(?,?,?)");
			cstmt.setInt(1,balance);
			cstmt.setString(2,username);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			uid = cstmt.getInt(3);
			//System.out.println("ID of account is : " + uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return uid;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET BANK ACCOUNTS USING USERNAME
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<BankAccount> get_accounts_w_name(String username) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankAccount> accts = new ArrayList<BankAccount>();

		//System.out.println("Called connectDB get_accounts_w_name()");
		try(Connection connection = dbconnect()){
			String stmt = "	SELECT a.account_id,a.balance,a.status"
					+ "	FROM user_accounts ua RIGHT JOIN accounts a ON ua.account_id = a.account_id"
					+ "	INNER JOIN users u ON ua.user_id = u.user_id"
					+ "	WHERE u.user_name = ?;";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setString(1,username);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
				accts.add(new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3)));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return accts;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET BANK ACCOUNT USING ACCOUNT ID
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static BankAccount get_account_w_aid(int id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BankAccount ba = null;
		
		//System.out.println("Called connectDB get_account_w_aid()");
		try(Connection connection = dbconnect()){
			String stmt = "	SELECT a.account_id,a.balance,a.status FROM accounts a WHERE a.account_id = ?";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getString(2));
	        	ba= new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return ba;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SET DEPOSIT AMOUNT TO BANK ACCOUNT USING ACCOUNT ID
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int set_account_w_deposit(int id, int balance) {
		CallableStatement cstmt = null;
		int transaction_id = 0;
		
		//System.out.println("Called connectDB set_account_w_deposit()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL deposit(?,?,?)");
			cstmt.setInt(1,id);
			cstmt.setInt(2,balance);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			transaction_id = cstmt.getInt(3);
			//System.out.println("Transaction id: " + transaction_id); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		return transaction_id;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SET DEPOSIT AMOUNT TO BANK ACCOUNT USING ACCOUNT ID
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int set_account_w_withdraw(int id, int amount) {
		CallableStatement cstmt = null;
		int transaction_id = 0;
		
		//System.out.println("Called connectDB set_account_w_withdraw()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL withdraw(?,?,?)");
			cstmt.setInt(1,id);
			cstmt.setInt(2,amount);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.setInt(3, 0);
			cstmt.execute();
			transaction_id = cstmt.getInt(3);
			//System.out.println("Transaction id: " + transaction_id); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return transaction_id;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CONFIRM ACCOUNT WITH PENDING TRANSFER
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<BankAccount> confirm_account_w_pending_transfer(int account_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankAccount> accts = new ArrayList<BankAccount>();
		String stmt;
		
		//System.out.println("Called connectDB confirm_account_w_pending_transfer()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT DISTINCT a.account_id, a.balance, a.status "
					+ "FROM accounts a INNER JOIN transaction_logs t ON t.dest_account = a.account_id "
					+ "WHERE t.status = 'pending' AND t.dest_account = ?";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,account_id);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
				accts.add(new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3)));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return accts;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET ACTIVE ACCOUNT EXISTANCE
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static BankAccount get_account_existance(int id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String stmt;
		BankAccount ba = null;
		
		//System.out.println("Called connectDB get_account_existance()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT a.account_id, a.balance, a.status "
					+ "FROM accounts a WHERE a.status = 'active' AND account_id = ?";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
	        	ba = new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return ba;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET ACCOUNTS POSTING TRANSFER TO DESTINATION ACCOUNT
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<BankAccount> get_accounts_posting_transfer(int des_account) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankAccount> accts = new ArrayList<BankAccount>();
		String stmt;
		
		//System.out.println("Called connectDB get_accounts_posting_transfer()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT DISTINCT account_id, balance, accounts.status "
					+ "FROM accounts INNER JOIN transaction_logs ON account_id=src_account "
					+ "WHERE transaction_logs.status = 'pending' AND dest_account=?";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,des_account);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
				accts.add(new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3)));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return accts;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET LIST OF TRANSACTIONS FOR PENDING POSTED TRANSFERS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<Transaction> get_transaction_pending(int des_account) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Transaction> tids = new ArrayList<Transaction>();
		String stmt;
		
		//System.out.println("Called connectDB get_transaction_pending()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT transaction_id,src_account,dest_account, status, amount, act, created_at "
					+ "FROM transaction_logs "
					+ "WHERE transaction_logs.status = 'pending' AND dest_account=? ";
			pstmt = connection.prepareStatement(stmt);
			pstmt.setInt(1,des_account);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
//				System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)
//				+" "+rs.getString(4)+" "+rs.getInt(5)+" "+rs.getString(6)+" "+rs.getTimestamp(7));
	        	tids.add(new Transaction(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),
	        			rs.getInt(5),rs.getString(6),rs.getTimestamp(7)));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return tids;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//POST TRANSFER REQUEST 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int post_transfer(int src,int dst,int amount) {
		CallableStatement cstmt = null;
		int transaction_id = 0;
		
		//System.out.println("Called connectDB post_transfer()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL post_transfer(?,?,?,?)");
			cstmt.setInt(1,src);
			cstmt.setInt(2,dst);
			cstmt.setInt(3,amount);
			cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
			cstmt.setInt(4, 0);
			cstmt.execute();
			transaction_id = cstmt.getInt(4);
			//System.out.println("Transaction id: " + transaction_id); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return transaction_id;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ACCEPT TRANSFER REQUEST 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void accept_transfer(int tid) {
		CallableStatement cstmt = null;
		
		//System.out.println("Called connectDB accept_transfer()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL finalize_transfer(?)");
			cstmt.setInt(1,tid);
			cstmt.execute();
			//System.out.println("Transaction successfully accepted...); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//REJECT TRANSFER REQUEST 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void reject_transfer(int tid) {
		CallableStatement cstmt = null;
		
		//System.out.println("Called connectDB reject_transfer()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL reject_transfer(?)");
			cstmt.setInt(1,tid);
			cstmt.execute();
			//System.out.println("Transaction rejected...); 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
	}
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET LIST OF PENDING USERS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<UserAccount> get_pending_users() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UserAccount> usrs = new ArrayList<UserAccount>();
		String stmt;
		
		//System.out.println("Called connectDB get_pending_users()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT user_id, user_name, account_type FROM users WHERE "
					+ "status = 'pending' AND account_type = 'customer';";
			pstmt = connection.prepareStatement(stmt);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
	        	usrs.add(new UserAccount(rs.getInt(1),rs.getString(2),"pending",rs.getString(3)));
	        }
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		return usrs;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ACCEPT/REJECT USER ACCOUNT REQUEST 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int accept_reject_user(String user_name,String action) {
		CallableStatement cstmt = null;
		int uid = 0;
		
		//System.out.println("Called connectDB accept_reject_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL approve_reject_customer(?,?,?)");
			cstmt.setString(1,user_name);
			cstmt.setString(2,action);
			cstmt.setInt(3,0);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.execute();
			uid = cstmt.getInt(3);
			//System.out.println("User id: " + uid); 
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return uid;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET ACCOUNTS POSTING TRANSFER TO DESTINATION ACCOUNT
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<BankAccount> get_pending_accounts() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankAccount> accts = new ArrayList<BankAccount>();
		String stmt;
		
		//System.out.println("Called connectDB get_accounts_posting_transfer()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT DISTINCT account_id, balance, accounts.status "
					+ "FROM accounts WHERE accounts.status = 'pending'";
			pstmt = connection.prepareStatement(stmt);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
				//System.out.println("row: "+ rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3));
				accts.add(new BankAccount(rs.getInt(1),rs.getInt(2),rs.getString(3)));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return accts;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ACCEPT/REJECT BANK ACCOUNT APPLICATION 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int accept_reject_account(int account_id,String action) {
		CallableStatement cstmt = null;
		int uid = 0;
		
		//System.out.println("Called connectDB accept_reject_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL approve_reject_application(?,?,?)");
			cstmt.setInt(1,account_id);
			cstmt.setString(2,action);
			cstmt.setInt(3,0);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.execute();
			uid = cstmt.getInt(3);
			System.out.println("User id: " + uid); 
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return uid;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET USER ACCOUNT FROM USERNAME 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static UserAccount get_user_w_name(String user_name) {
		CallableStatement cstmt = null;
		UserAccount ua = null;
		
		//System.out.println("Called connectDB accept_reject_user()");
		try(Connection connection = dbconnect()){
			cstmt = connection.prepareCall("CALL get_user_w_name(?,?,?,?)");
			cstmt.setString(1,user_name);
			cstmt.setInt(2,0);
			cstmt.setString(3,null);
			cstmt.setString(4,null);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.execute();
			//System.out.println("User id: " + cstmt.getInt(2) + ": " + cstmt.getString(3) + " " + cstmt.getString(4)); 
			ua = new UserAccount(cstmt.getInt(2),user_name,cstmt.getString(3),cstmt.getString(4));
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
		}
		
		return ua;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GET ALL TRANSACTION LOGS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<Transaction> get_transaction_logs() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Transaction> logs = new ArrayList<Transaction>();
		String stmt;
		
		//System.out.println("Called connectDB get_accounts_posting_transfer()");
		try(Connection connection = dbconnect()){
			stmt = "SELECT transaction_id, src_account,dest_account, status, amount, act, created_at "
					+ "FROM transaction_logs WHERE dest_account IS NOT NULL";
			pstmt = connection.prepareStatement(stmt);
			rs = pstmt.executeQuery();
	        while (rs.next()) {
//				System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)
//				+" "+rs.getString(4)+" "+rs.getInt(5)+" "+rs.getString(6)+" "+rs.getTimestamp(7));
	        	logs.add(new Transaction(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),
	        			rs.getInt(5),rs.getString(6),rs.getTimestamp(7)));
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {};
		}
		
		return logs;
	}
}


