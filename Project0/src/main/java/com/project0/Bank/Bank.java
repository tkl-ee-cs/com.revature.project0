package com.project0.Bank;

//import java.sql.Connection;

public class Bank {
	
	public static void main(String[] args) {
		
		User usr;
		while(true) {
			usr = Session.startPrompts();
			Session.loggedIn(usr);
		}
	}
}
