package com.project0.Bank;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Session {
	
	static int direction;
	
	public static User startPrompts() {
		direction = initialPrompt();
		if(direction == 1) {
			System.out.println("New user requesting an account...");
			newUserPrompt();
		}else if(direction == 2) {
			//System.out.println("Returning user to log in ...");
			return retUserPrompt();
		}else if(direction == -1) {
			System.out.println("Error, explicit issue from dialog...");
		}else{
			System.out.println("Error, falling through prompts...");
		}
		//System.out.println("Direction: " + direction + ", Response: " + response);
		return null;
	}
	
	private static int initialPrompt() {
		
	    int retInt = 0;
	    System.out.println("WELCOME TO BANK OF TRENTON LOCATED IN REVATURE, NJ.");
	    System.out.println("__TO BEGIN THE SERVICES, PLEASE MAKE A SELECTION__");
	    System.out.println("---Create New Account [1]:");
	    System.out.println("---Log In To Your Account [2]:");
	    System.out.println("Any other selection will restart the input process...");
	    do{
	    	retInt = cleanScan.getInt();
			if(retInt == 1) {
		    	return 1; //Return selection for new account, 1
		    }else if(retInt == 2) {
		    	return 2;
		    }else {
	    	System.out.printf("...invalid selection, try again...");
		    }
	    }while((retInt < 1) & (retInt > 2));
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	private static void newUserPrompt() {
		String username;
		String password;
		int balance;
		int retVal;
		String status;
		int uid;
		
	    do { //Loop while the retVal is not y or 0
			System.out.println("__PLEASE ENTER NEW CUSTOMER ACCOUNT DETAILS__");
		    System.out.printf("---UserName: ");
		    do {username = cleanScan.getStr(); //Wait for input
		    	status = connectDB.get_status_w_name(username); // return status if username exists)
		    	if(status!=null) {System.out.printf("...username already taken, please try again...");}
		    }while(status!=null);		
		    System.out.printf("---Password: ");
		    password = cleanScan.getStr(); //Wait for input
			System.out.printf("---Starting Balance: ");
			do {balance = cleanScan.getInt(); //Wait for input
				if(balance == 0) {System.out.printf("...invalid amount, try again...");}
			}while(balance == 0);
		    
		    System.out.println("Input [1] to Confirm Details for New Account Request? Or, Press [0] to Go Back...");
		    System.out.println("Any other integer selection will restart the input process...");
    		retVal = cleanScan.getInt();
			if(retVal == 1) { //check for y, to confirm
				uid = connectDB.set_new_user(username,password,balance); 
				if(uid > 0){
					System.out.println("Request Sent. Returning to Opening Menu...");
					//return uid;
				}else {
					System.out.println("...issue with creation, restarting input...");
					retVal = 2; //Set retVal to above 1 to keep in the loop
				}
		    }else if(retVal == 0){ //check for 0, to return to previous menu
				System.out.println("Returning Back to Opening Menu...");
				//return 0; //returning a null as no instance is required
		    }
	    }while(retVal > 1);
	    //return -1; //return -1 if dropping out of proper return or the loop
	}
	
	private static User retUserPrompt() {
		String username;
		String password;
		int retInt;
		String act_type;
		String status;
		int uid;
		User user = null;
		
	    do{//Loop while the retVal is not y or 0
	    	System.out.println("__ENTER USER CREDENTIALS__");
		    System.out.printf("---UserName: ");
		    do {username = cleanScan.getStr(); //Wait for input
		    	status = connectDB.get_status_w_name(username); // return status if username exists)
		    	if(status==null) {System.out.printf("...username doesn't exist, please try again...");}
		    }while(status==null);	
		    System.out.printf("---Password: ");
		    password = cleanScan.getStr(); //Wait for input
		    
		    System.out.println("Input [1] to Confirm Submission? Or, Press [0] to Go Back...");
		    System.out.println("Any other integer selection will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 1) { //check for 1, to confirm
				act_type = connectDB.get_type_w_name(username);
				uid = connectDB.get_id_w_name_pw(username,password);
				if(act_type.equals("employee")) {
					//System.out.printf("Logging into employee account...");
					user = new Employee(uid,username,status);
					//System.out.println(user);
					return user;
				}else if (act_type.equals("customer")) {
					//System.out.printf("Logging into customer account...");
					//user = new Customer(uid,username,status);
					System.out.println(user);
					return user;
				}else {
					System.out.printf("...issue pulling up results, try again...");
					retInt = 2;
				}
		    }else if(retInt == 0){ //check for 0, to return to previous menu
				System.out.println("Returning Back to Opening Menu...");
				return null;
		    }else {
		    	System.out.printf("...invalid selection, try again...");
			}
	    }while(retInt > 1);
	    return null; //return null if dropping out of proper return or the loop
	}
	
	public static User loggedIn(User user) {
		String type = user.getType();
		int retInt;
		if (type.equals("customer")) {
			retInt = customerPrompt(user);
		}else if(type.equals("employee")) {
			retInt = employeePrompt(user);
		}else {
			System.out.println("...Error, type following out of conditions...");
		}
		return null;
	}
	

	private static int customerPrompt(User user) { 
		int retInt;
		String name = "";
		
//-->edit query database for user name
		System.out.println("WELCOME " + name + ". HOW MAY WE HELP YOU TODAY?");
		
	    do{
		    System.out.println("__PLEASE MAKE A SELECTION__");
		    System.out.println("---Apply For A New Banking Account [1]:");
		    System.out.println("---View Your Accounts [2]:");
		    System.out.println("\n");
		    System.out.println("Press [0] to Log Out. Any other selection will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 1) { // Apply For A New Banking Account
				System.out.println("Navigating to Application Menu...");
//-->edit		Send Request to database
				return 1;
			}else if(retInt == 2){ //View Customer Accounts
				System.out.println("Navigating to Account Viewing Menu...");
				return 2;
			}else if(retInt == 0){ //check for 0, to return to previous menu
				System.out.println("Logging out...");
				return 0;
			}else {
		    	System.out.printf("...invalid selection, try again...");
			}
	    }while(retInt > 2);
	   
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	

	private static int employeePrompt(User user) {
		int retInt;
		String name = "";
		
//-->edit query database to get the username of account
	    System.out.println("WELCOME BACK, " + name + ".");
	    
	    System.out.println("__MAKE A SELECTION FROM THE MENU__");
	    System.out.println("---Approve/Reject A Customer Account [1]:");
	    System.out.println("---Approve/Reject A Banking Account Application [2]:");
	    System.out.println("---View A Customer's Accounts [3]:");
	    System.out.println("---View Transaction Logs [4]:");
	    System.out.println("\n");
	    System.out.println("Press [0] to log out...");
	    do{
	    	retInt = cleanScan.getInt();
			if(retInt == 1) {
		    	return 1;
		    }else if(retInt == 2) {
		    	return 2;
		    }else if(retInt == 3) {
		    	return 3;
		    }else if(retInt == 4) {
		    	return 4;
		    }else if(retInt == 0) {
		    	return 0;
		    }else {
	    	System.out.printf("...invalid selection, try again...");
		    }
	    }while((retInt < 0) | (retInt > 4));
	    
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	
	
}

