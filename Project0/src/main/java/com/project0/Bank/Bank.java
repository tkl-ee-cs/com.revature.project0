package com.project0.Bank;

import java.sql.Connection;

public class Bank {
	public static void main(String[] args) {
		//Start Introduction Dialog
		//Call Method to start prompts
		Dialogs.initSelectPrompt();
		
		try{
			Connection connection = connectDB.dbconnect();
			System.out.println("Connected to PostgreSQL database!");
			System.out.println(connection);
		}catch(Exception e) {
			System.out.println("Connection Error...");
	        e.printStackTrace();
		}
	}
}
