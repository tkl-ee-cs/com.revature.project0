package com.project0.Bank;

//import java.sql.Connection;

public class Bank {
	
	public static void main(String[] args) {
		
		connectDB.log.info("Start of Program");
		
		User usr;
		while(true) {
			usr = Session.startPrompts();
			Session.loggedIn(usr);
		}
	}
}
