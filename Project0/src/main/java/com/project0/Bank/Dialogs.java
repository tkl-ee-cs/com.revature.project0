package com.project0.Bank;

import java.util.Scanner;

public class Dialogs {
	//Start First Prompts
	//Create User
	//Sign In
	private static Scanner scanner = new Scanner(System.in);  // Create a Scanner object

	//Initial Service Selection Prompt
	public static int initSelectPrompt() {
		String retString = "";
	    System.out.println("__MAKE A SELECTION <1/2>__");
	    System.out.println("---Create New Account [1]:");
	    System.out.println("---Log In To Your Account [2]:");
	    //int retVal = scanner.nextInt(); //Wait for input
	    //Perform Checks
	    int retInt = 0;
	    
	    while((retInt != 1) & (retInt != 2)) {
	    	retInt = cleanScan.getInt();
	    		
			if(retInt == 1) {
		    	return 1;
		    }else if(retInt == 2) {
		    	return 2;
		    }
	    	System.out.printf("...invalid choice, try again...");
	    }
	    return -1;
	}
	
	//Create New Customer Prompt
	public static int newUserPrompt() {
		String username;
		String password;
		int balance;

		char retVal = ' ';
		
	    while((retVal != 'y') & (retVal != '0')) { //Loop while the retVal is not y or 0
			System.out.println("__PLEASE ENTER NEW CUSTOMER ACCOUNT DETAILS__");
		    System.out.println("---UserName:");
		    username = cleanScan.getStr(); //Wait for input
		    System.out.println("---Password:");
		    password =  cleanScan.getStr(); //Wait for input
			System.out.println("---Starting Balance:");
			balance =  cleanScan.getInt(); //Wait for input
		    
		    System.out.println("Input [y] to Confirm Details for New Account Request? Or, Press [0] to Go Back...");
		    System.out.println("Input [Any Other Key] to Restart the Submission Process...");
		    
    		retVal = cleanScan.getChar();
			if(retVal == 'y') { //check for y, to confirm
				System.out.println("Request Sent. Returning to Opening Menu...");
				//Send Request to database
				return 1;
		    }else if(retVal == '0'){ //check for 0, to return to previous menu
				System.out.println("Returning Back to Opening Menu...");
				return 0;
		    }
	    }
	    return -1;
	}

	//Log-in Prompt - For logging into account
	public static void retUserPrompt() {

		System.out.println("__ENTER USER CREDENTIALS__");
	    System.out.println("---UserName:");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    System.out.println("---Password:");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    
	    System.out.println("Confirm Log in Detail? <y/n>");
	    System.out.println("Or, Press [0] to Go Back...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    
		//perform needed operations
		//Send Request to database
		//wait for response to check for valid credentials
			//If valid cred, next step - valid message
				// System.out.println("Successfully logged in.");
			//If invalid cred, reprompt with error msg (or send back to initial selection)
				// System.out.println("Incorrect username/password.");
	    //control may return to calling method for next step
	}
	
	//Customer Prompt - Set of choices for Customer
	public static void customerPrompt() {
	    System.out.println("WELCOME");
	    System.out.println("__MAKE A SELECTION__");
	    System.out.println("---Apply For A New Banking Account [1]:");
	    System.out.println("---View Your Accounts [2]:");
	    //System.out.println("---Accept Pending Transfer Funds [5]:");
	    System.out.println("\n");
	    System.out.println("Press [0] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	}
	
	//Account Application Prompt - Prompts for creating a new account application
	public static void newAccountPrompt() {
		System.out.println("__PLEASE SUBMIT DETAILS FOR NEW ACCOUNT APPLICATION__");
		System.out.println("---Starting Balance:");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	   
	    System.out.println("Confirm sending application request? <y/n>");
	    System.out.println("Or, Press [0] to Go Back...");
	    System.out.println("Or, Press [1] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    
		//perform needed operations
		//Send Request to database
		//control to return to calling method
	}

	//Account View Prompt - Focus on specific 
	public static void accountFocusPrompt() {
		System.out.println("__PLEASE TYPE IN YOUR ACCOUNT TO VIEW__");
		//Display List of Customer Accounts
		//Display message for each customer account that has a pending transfer
		//System.out.println("Note: Account# [x,y,z,...] have pending transfer(s) awaiting your review");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
		
	    System.out.println("Confirm view? <y/n>");
	    System.out.println("Or, Press [0] to Go Back...");
	    System.out.println("Or, Press [1] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    
		//perform needed operations
		//Send Request to database
		//control to return to calling method
	}
	
	//Customer Action Prompt - Set of choices for Customer Accounts
	public static void customerActionPrompt() {
	    System.out.println("__MAKE A SELECTION__");
	    System.out.println("---Deposit To An Account [1]:");
	    System.out.println("---Withdraw From Account [2]:");
	    System.out.println("---Post Transfer Funds [3]:");
	    //System.out.println("---Accept Pending Transfer Funds [4]:");
	    System.out.println("\n");
	    System.out.println("Press [0] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	}
	
	
	//Account Deposit Prompt 
	public static void despositPrompt() {
		//System.out.println("Account#: ..."); //Include account number
		System.out.println("__PLEASE INDICATE HOW MUCH TO DEPOSIT__");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
		
	    System.out.println("Confirm deposit? <y/n>");
	    System.out.println("Or, Press [0] to Go Back...");
	    System.out.println("Or, Press [1] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    
		//perform needed operations
	    System.out.println("Deposit has been made. Returning to previous menu.");
		//Send Request to database
		//control to return to calling method
	}
	
	//Account Withdraw Prompt
	public static void withdrawPrompt() {
		//System.out.println("Account#: ..."); //Include account number
		System.out.println("__PLEASE INDICATE HOW MUCH TO WITHDRAW__");
		
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
		
	    System.out.println("Confirm withdraw? <y/n>");
	    System.out.println("Or, Press [0] to Go Back...");
	    System.out.println("Or, Press [1] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    

		//perform needed operations
	    System.out.println("Insufficient funds. Please submit another amount.");
	    System.out.println("Withdraw has been made. Returning to previous menu.");
		//Send Request to database
		//control to return to calling method
	}
	
	//Account Transfer (Post) Prompt
	public static void postTransferPrompt() {
		//System.out.println("Account#: ..."); //Include account number
		System.out.println("__PLEASE PROVIDE USERNAME AND ACCOUNT TO TRANSFER FUNDS__");
	    System.out.println("---UserName:");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    System.out.println("---Account:");
		//Display List of Customer Accounts
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
		
	    System.out.println("Confirm transer? <y/n>");
	    System.out.println("Or, Press [0] to Go Back...");
	    System.out.println("Or, Press [1] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    
	    System.out.println("A transfer as been posted. Returning to previous menu.");
		//perform needed operations
		//Send Request to database
		//control to return to calling method
	}
	//Account Transfer (Post) Prompt
	public static void acceptTransferPrompt() {
		//System.out.println("Account#: "); //Include account number
		System.out.println("__PLEASE PROVIDE USERNAME AND ACCOUNT TO TRANSFER FUNDS__");
	    System.out.println("---UserName:");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    System.out.println("---Account:");
		//Display List of Customer Accounts
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
		
	    System.out.println("Confirm transer? <y/n>");
	    System.out.println("Or, Press [0] to Go Back...");
	    System.out.println("Or, Press [1] to Log Out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	    
	    System.out.println("A transfer as been posted. Returning to previous menu.");
		//perform needed operations
		//Send Request to database
		//control to return to calling method
	}
	
	//Employee Prompt - Set of choices for Employee
	public static void employeePrompt() {
	    System.out.println("WELCOME");
	    System.out.println("__MAKE A SELECTION <1/2/3/4>__");
	    System.out.println("---Approve/Reject A Customer Account [1]:");
	    System.out.println("---Approve/Reject An Account Application [2]:");
	    System.out.println("---View A Customer's Accounts [3]:");
	    System.out.println("---View Transaction Logs [4]:");
	    System.out.println("\n");
	    System.out.println("Press [0] to log out...");
	    //Wait for input
	    //Perform Checks
	    //Re-prompt if necessary
	}
	
	//Simple simulate Clear Console Prompt - pushing text off screen using newlines
	public static void clearPrompt() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
	
	private char cleanScan(String sc,String scType) {
		
		
		return '1';
	}
	
}
