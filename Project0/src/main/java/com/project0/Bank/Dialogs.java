package com.project0.Bank;

import java.util.Scanner;

public class Dialogs {
	//private static Scanner scanner = new Scanner(System.in);  // Create a Scanner object

	/*Initial Service Selection Prompt
	 * Input: N/A
	 * Output: ????
	 * */
	public static int initSelectPrompt() {
	    int retInt = 0;
	    System.out.println("WELCOME TO BANK OF TRENTON LOCATED IN REVATURE, NJ.");
	    System.out.println("__TO BEGIN SERVICE, PLEASE MAKE A SELECTION__");
	    System.out.println("---Create New Account [1]:");
	    System.out.println("---Log In To Your Account [2]:");
	    System.out.println("Any other selection will restart the input process...");
	    do{
	    	retInt = cleanScan.getInt();
			if(retInt == 1) {
		    	return 1;
		    }else if(retInt == 2) {
		    	return 2;
		    }else {
	    	System.out.printf("...invalid selection, try again...");
		    }
	    }while((retInt != 1) & (retInt != 2));
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	/*Create New Customer Prompt
	 * Input: N/A
	 * Output: ????
	 * */
	public static int newUserPrompt() {
		String username;
		String password;
		int balance;
		char retVal = ' ';
		
	    do { //Loop while the retVal is not y or 0
			System.out.println("__PLEASE ENTER NEW CUSTOMER ACCOUNT DETAILS__");
		    System.out.println("---UserName:");
		    username = cleanScan.getStr(); //Wait for input
		    System.out.println("---Password:");
		    password = cleanScan.getStr(); //Wait for input
			System.out.println("---Starting Balance:");
			do {balance = cleanScan.getInt(); //Wait for input
				if(balance == 0) {System.out.printf("...invalid amount, try again...");}
			}while(balance == 0);
		    
		    System.out.println("Input [y] to Confirm Details for New Account Request? Or, Press [0] to Go Back...");
		    System.out.println("Any other selection will restart the input process...");
    		retVal = cleanScan.getChar();
			if(retVal == 'y') { //check for y, to confirm
				System.out.println("Request Sent. Returning to Opening Menu...");
				//Send Request to database
//-->edit 		return 1;
		    }else if(retVal == '0'){ //check for 0, to return to previous menu
				System.out.println("Returning Back to Opening Menu...");
//-->edit 		return 0;
		    }
	    }while((retVal != 'y') | (retVal != '0'));
	    return -1; //return -1 if dropping out of proper return or the loop
	}

	/*Log-in Prompt - For logging into account
	 * Input: N/A
	 * Output: ????
	 * */
	public static int retUserPrompt() {
		String username;
		String password;
		char retVal = ' ';
		
	    do{//Loop while the retVal is not y or 0
	    	System.out.println("__ENTER USER CREDENTIALS__");
		    System.out.println("---UserName:");
		    username = cleanScan.getStr(); //Wait for input
		    System.out.println("---Password:");
		    password = cleanScan.getStr(); //Wait for input
		    //Re-prompt if necessary
		    
		    System.out.println("Input [y] to Confirm Submission? Or, Press [0] to Go Back...");
		    System.out.println("Any other selection will restart the input process...");
			retVal = cleanScan.getChar();
			if(retVal == 'y') { //check for y, to confirm
				System.out.println("Request Sent. Returning to Opening Menu...");
				//Send Request to database
//-->edit 		return 1;
		    }else if(retVal == '0'){ //check for 0, to return to previous menu
				System.out.println("Returning Back to Opening Menu...");
//-->edit 		return 0;
		    }else {
		    	System.out.printf("...invalid selection, try again...");
			}
	    }while((retVal != 'y') | (retVal != '0'));
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	/*Customer Prompt - Set of choices for Customer
	 * Input: Integer username, password (potentially)
	 * Output: Integer, indicating choice of Customer - 1(new account), 2(view), 0(back)
	 * */
	public static int customerPrompt(int user, int password) { 
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
	
	/*Account Application Prompt - Prompts for creating a new account application
	 * Input: N/A
	 * Output: ????
	 * */
	public static int newAccountPrompt() {
		int balance;
		char retChar;
		
	    do{ 
		System.out.println("__PLEASE SUBMIT DETAILS FOR NEW ACCOUNT APPLICATION__");
		System.out.println("---Starting Balance:");
		do {balance = cleanScan.getInt(); //Wait for input
			if(balance == 0) {System.out.printf("...invalid amount, try again...");}
		}while(balance == 0);
	   
	    System.out.println("Input [y] to Confirm Submission?");
	    System.out.println(" Or, Press [1] to Go Back. Press [0] to Log Out...");
	    System.out.println("Any other selection will restart the input process...");
		retChar = cleanScan.getChar(); //Wait for input
		
		if(retChar == 'y') { 
			System.out.println("Submitting Request for New Account. Returning to Previous Menu...");
//-->edit	Send Request to database
//-->edit	return balance;
		}else if(retChar == '1'){ 
			System.out.println("Returning Back to Previous Menu...");
//-->edit	return 1;
		}else if(retChar == '0'){ 
			System.out.println("Logging out...");
//-->edit	return 0;
		}else {
	    	System.out.printf("...invalid selection, try again...");
		}
	    }while(retChar !='y'|retChar !='0'|retChar !='1');
	    
	    return -1;
	}

	/*Account View Prompt - Select which specific banking account to focus on
	 * Input: ???
	 * Output: ????
	 * */
	public static int accountFocusPrompt(int[] acts,int[] pends) {
		int retView;
		int retChar;
		
		do{
		System.out.println("__PLEASE TYPE IN YOUR ACCOUNT TO VIEW__");
		for(int i=0;i<acts.length;i++) { //Display the list of accounts
			System.out.println("---Account# " + acts[i] + " [" + i + "]:");
		}
		if(pends[0] != 0) {
			System.out.printf("Note: Account# ");
			for(int j=0;j<acts.length;j++) {System.out.printf("[" + pends[j] + "]");} 
			System.out.println("have pending transfer(s) awaiting your review");
		}
		do {retView = cleanScan.getInt(); //Wait for input, valid it date it is between
			if(retView > acts.length) {System.out.printf("...invalid selection, try again...");}
		}while(retView > acts.length);
		
	    System.out.println("Input [y] to Confirm View Selection?");
	    System.out.println(" Or, Press [1] to Go Back. Press [0] to Log Out...");
	    System.out.println("Any other selection will restart the input process...");
		retChar = cleanScan.getChar(); //Wait for input
		
		if(retChar == 'y') { 
			System.out.println("Navigating to Account Menu...");
//-->edit	Send Request to database
//-->edit	return retView;
		}else if(retChar == '1'){ 
			System.out.println("Returning Back to Previous Menu...");
//-->edit	return 1;
		}else if(retChar == '0'){ 
			System.out.println("Logging out....");
//-->edit	return 0;
		}else {
	    	System.out.printf("...invalid selection, try again...");
		}
	    }while((retChar !='y')|(retChar !='0')|(retChar !='1'));
		
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	/*Customer Action Prompt - Set of choices for Customer Accounts
	 * Input: ???
	 * Output: ????
	 * */
	public static int customerActionPrompt() {
		int retInt;
		do {
		    System.out.println("__MAKE A SELECTION__");
		    System.out.println("---Deposit To An Account [1]:");
		    System.out.println("---Withdraw From Account [2]:");
		    System.out.println("---Post/Accept Transfer Funds [3]:");
		    System.out.println("\n");
		    System.out.println("Press [4] to Log Out. Press [0] to Log Out...");
		    System.out.println("Any other selection will restart the input process...");
		    retInt = cleanScan.getInt(); //Wait for input
		    
			if(retInt == 1) { 			
				System.out.println("Navigating to Deposit Menu...");
//-->edit		return 1;
			}else if(retInt == 2){ 
				System.out.println("Navigating to Withdraw Menu...");
//-->edit		return 2;
			}else if(retInt == 3){ 
				System.out.println("Navigating to Transfer Menu...");
//-->edit		return 3;
			}else if(retInt == 0){ 
				System.out.println("Returning Back to Previous Menu...");
//-->edit		return 0;
			}else if(retInt == 4){ 
				System.out.println("Logging out...");
//-->edit		return 4;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
	    }while(retInt > 5);
	
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	
	/*Account Deposit Prompt 
	 * Input: Integer, account number | Integer, balance amount
	 * Output: ????
	 * */
	public static int despositPrompt(int account, int balance) {
		int retInt;
		char retChar;
		
		System.out.println("Account#: " + account); 
		do {
			System.out.println("__PLEASE INDICATE HOW MUCH TO DEPOSIT__");
			do {retInt = cleanScan.getInt(); //Wait for input
				if(retInt == 0) {System.out.printf("...invalid amount, try again...");}
			}while(retInt == 0);
			
		    System.out.println("Input [y] to Confirm Deposit...");
		    System.out.println("Or, Press [0] to Go Back to Previous Menu. Press [1] to Log Out...");
		    System.out.println("Any other key will restart the input process...");
		    retChar = cleanScan.getChar();
			if(retChar == 'y') { 			
				System.out.println("Deposit has been made. Returning to previous menu.");
//-->edit		Access to database
//-->edit		return 1;
			}else if(retChar == '0'){ 
				System.out.println("Returning Back to Previous Menu...");
//-->edit		return 0;
			}else if(retChar == '1'){ 
				System.out.println("Logging out...");
//-->edit		return 1;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
		}while((retChar != 'y')|(retChar != '0')|(retChar != '1'));

	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	/*Account Withdraw Prompt
	 * Input: Integer, account number | Integer, balance amount
	 * Output: ????
	 * */
	public static int withdrawPrompt(int account, int balance) {
		int retInt;
		int retVal;
		
		System.out.println("Account#: " + account); 
		do {
			System.out.println("__PLEASE INDICATE HOW MUCH TO WITHDRAW__");
			do {retInt = cleanScan.getInt(); //Wait for input
				if(retInt == 0) {System.out.printf("...invalid amount, try again...");}
				if(retInt > balance) {System.out.println("Withdraw has been made. Returning to previous menu.");}
			}while((retInt != 0)|(retInt > balance));
			
		    System.out.println("Input [2] to Confirm Withdrawal...");
		    System.out.println("Or, Press [0] to Go Back to Previous Menu. Press [1] to Log Out...");
		    System.out.println("Any other key will restart the input process...");
		    retVal = cleanScan.getInt();
			if(retVal == 2) { 			
				System.out.println("Withdrawal has been made. Returning to previous menu.");
//-->edit		Access to database
//-->edit		return 1;
			}else if(retVal == 0){ 
				System.out.println("Returning Back to Previous Menu...");
//-->edit		return 0;
			}else if(retVal == 1){ 
				System.out.println("Logging out...");
//-->edit		return 1;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
		}while(retVal > 2);

	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	/*Account Transfer Prompts - Conditional prompt for actioning pending transfer requests, Post a transfer request
	 * Input: int, account number | int, from - posted transfer account number | int, balance amount
	 * Output: ????
	 * */
	public static int TransferPrompts(int account, int balance) {
		int retInt;
		int retVal = 0;
		char retChar;
		int pending = 0;

//-->edit Check database for account list of pending transfers
		System.out.println("Account#: " + account);
		do {
			if(pending != 0) { //Following Selection Prompt only appears with pending action are required
				System.out.println("__YOU HAVE PENDING TRANSFER REQUESTS TO ACTION__");
				System.out.println("__PLEASE INDICATE WHETHER__");
				System.out.println("-You would like to post a Transfer request [1]");
				System.out.println("-You would like to action the pending requests [2]");
				System.out.println("Or, Press [3] to Go Back to Previous Menu. Press [0] to Log Out...");
				do {retInt = cleanScan.getInt(); //Wait for input
					if(retInt > 4) {System.out.printf("...invalid selection, try again...");}
				}while(retInt > 4);
	
				if (retInt == 1){ //Post transfer, calling internal method postTransferPrompt
					retVal = postTransferPrompt(account, balance);
//-->edit			return retVal;
				}else if(retInt == 2) { //Action pending accounts, calling internal method acceptTransferPrompt
					pending = acceptTransferPrompt(account, balance);
					retVal = postTransferPrompt(account, balance);
//-->edit			//Any additional actions
				}else if (retInt == 3){
					System.out.println("Returning Back to Previous Menu...");
//-->edit			return 3;
				}else if (retInt == 0){
					System.out.println("Logging out...");
//-->edit			return 0;
				}
			}else {
				retVal = postTransferPrompt(account, balance);
//-->edit		return retVal;
			}
		}while((retVal < 0) | (retVal > 4));
		
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	/*Accept Transfer Prompt - For actioning pending transfer requests
	 * Input: int, account number | int, balance amount
	 * Output: retInt, return the number of pending requests
	 * */
	private static int acceptTransferPrompt(int account, int balance) {
		int retInt;
		
		int [] from = {0}; //-->edit
//-->edit Check database for pending transfer requests, return an array of transfers into from array
		System.out.println("__YOU HAVE PENDING REQUESTS TO ACTION__");
		for(int i=0;i<from.length;i++) {
			System.out.println("Account" + from[i] + "has sent you a money Transfer. Please advise if you will:");
			System.out.println("-Accept the Transfer Request [0]");
			System.out.println("-Reject the Transfer Request [1]");	
			System.out.println("-Temporarily Dismiss the Request [2]");	
			do {
				retInt = cleanScan.getInt();
				if(retInt == '0'){ 
					System.out.println("Accepting the Transfer...");
//-->edit			//Access the database to accept transfer
				}else if(retInt == '1'){ 
					System.out.println("Rejecting the Transfer...");
//-->edit			//Access the database to reject transfer
				}else if(retInt == '2'){ 
					System.out.println("Dismissing the Request Temporarily...");
				}else {
					System.out.printf("...invalid selection, try again...");
				}
			}while(retInt > 2);
		}
		//-->edit Check database for pending transfer requests, return an array of transfers into from array
		retInt = from.length;
		return retInt;
	}
	
	/*Account Transfer Prompt (post) - Post a transfer request
	 * Input: int, account number | int, balance amount
	 * Output: retInt, return the number if success
	 * */
	private static int postTransferPrompt(int account, int balance) {
		String user;
		int act;
		int [] acts = {0,0,0};
		int valid = 0;
		int retInt;
		
		do{
			System.out.println("__PLEASE PROVIDE USERNAME AND SELECT ACCOUNT TO POST TRANSFER REQUEST__");
		    System.out.println("---UserName:");
		    do {
		    	user = cleanScan.getStr();
//-->edit		//Check database for account, if it exists, return account number, else return 0
		    	if(valid == 0) {System.out.println("...invalid user, try again...");}
		    }
		    while(valid == 0);
//-->edit		//Check database for list of accounts
		    System.out.println("__Select Account__");
		    for(int i=0;i<acts.length;i++) {//Display List of Customer Accounts
		    	System.out.println("---Account #: " + acts[i] + " [" + i + "]:");
		    }
		    do {act = cleanScan.getInt();
		    	if(act > acts.length) {System.out.printf("...invalid selection, try again...");}
		    }while(act > acts.length);
	    	
		    System.out.println("Input [2] to Confirm the Transfer.");
		    System.out.println("Or, Press [0] to Go Back to Previous Menu. Press [1] to Log Out...");
		    System.out.println("Any other key will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 2) { 			
				System.out.println("A transfer as been posted. Returning to previous menu...");
//-->edit			Access to database
//-->edit			return 3;
			}else if(retInt == 0){ 
				System.out.println("Returning Back to Previous Menu...");
//-->edit			return 3;
			}else if(retInt == 1){ 
				System.out.println("Logging out...");
//-->edit			return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
		}while(retInt > 2);
		
		return -1;
	}
	
	/*Employee Prompt - Set of choices for Employee
	 * Input: Array, username, password (potentially)
	 * Output: Integer, indicating choice of employee 
	 * */
	public static int employeePrompt(int user, int password) {
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
	
	/*Approve/Reject User Prompt - Prompt for employee to approve/reject user
	 * Input: N/A
	 * Output: retInt, return the number if success
	 * */
	public static int reviewUserPrompt() {
		int user;
		int retInt;
		int [] acts = {0,0,0};
		
		System.out.println("__PLEASE SELECT USER REQUEST TO APPROVE OR REJECT__");
		do{
//-->edit 	query database for list of user that are pending approval, store in acts[]
			for(int i=0;i<acts.length;i++) {
				System.out.println("--User Account #: " + acts[i] + " [" + i + "]:");
			}
			do {retInt = cleanScan.getInt();
				if(retInt>acts.length) {System.out.printf("...invalid selection, try again...");}
		    }while(retInt>acts.length);
			user = acts[retInt]; //store selected account in user variable
			
		    System.out.println("Select [1] to confirm User Account request. Press [2] to reject.");
		    System.out.println("Or, Press [3] to Go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other key will cancel selection, and restart the selection process...");
		    retInt = cleanScan.getInt();
	    	
			if(retInt == 1) { 			
				System.out.println("Approving the User Account. Returning to previous menu...");
//-->edit			Access to database
//-->edit			return 3;
			}else if(retInt == 2){ 
				System.out.println("Rejecting the User Account. Returning to previous menu...");
//-->edit			Access to database
//-->edit			return 3;
			}else if(retInt == 3){ 
				System.out.println("Returning Back to Previous Menu...");
//-->edit			return 3;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
//-->edit			return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
		}while(retInt > 3);
		
		return -1;
	}
	

	/*Approve/Reject Banking Account Prompt - Prompt for employee to approve/reject banking account
	 * Input: N/A
	 * Output: retInt, return the number if success
	 * */
	public static int reviewAccountPrompt() {
		int retInt;
		int [] acts = {0,0,0};
		int user = 0;
		int balance  = 0;
		
		System.out.println("__PLEASE SELECT BANKING ACCOUNT APPLICATION TO APPROVE OR REJECT__");
		do{
//-->edit 	query database for list of accounts that are pending approval, store in acts[]
			for(int i=0;i<acts.length;i++) {
//-->edit 		query database for user/balance based on acts[i], store in user/balance vars
//-->edit 		user = result set [0]
//-->edit 		balance = result set [1]
				System.out.println("-- User Account #: " + user + "(" + acts[i]  + "), $" 
						+ balance + " [" + i + "]:");
			}
			do {retInt = cleanScan.getInt();
				if(retInt>acts.length) {System.out.printf("...invalid selection, try again...");}
		    }while(retInt>acts.length);
			user = acts[retInt]; //store selected account in user variable
			
		    System.out.println("Select [1] to approve Banking Account application. Press [2] to reject.");
		    System.out.println("Or, Press [3] to Go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other key will cancel selection, and restart the selection process...");
		    retInt = cleanScan.getInt();
	    	
			if(retInt == 1) { 			
				System.out.println("Approving the Banking Account. Returning to previous menu...");
//-->edit			Access to database, approve the bank account
//-->edit			return 3;
			}else if(retInt == 2){ 
				System.out.println("Rejecting the Banking Account. Returning to previous menu...");
//-->edit			Access to database, approve the bank account
//-->edit			return 3;
			}else if(retInt == 3){ 
				System.out.println("Returning Back to Previous Menu...");
//-->edit			return 3;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
//-->edit			return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
		}while(retInt > 3);
		
		return -1;
	}
	

	/*View User Prompt - Prompt for employee to view user account
	 * Input: N/A
	 * Output: retInt, return the number if success
	 * */
	public static int viewUserPrompt() {
		String user;
		int acnt = 0;
		int balance = 0;
		int retChar;
		int [] acts = {0,0,0};
		int valid = 0;
		
		do{
			System.out.println("__PLEASE PROVIDE USERNAME TO VIEW__");
		    System.out.println("---UserName:");
		    do {
		    	user = cleanScan.getStr();
//-->edit		//Check database for account, if it exists, return account number, else return 0
		    	if(valid == 0) {System.out.println("...invalid user, try again...");}
		    }
		    while(valid == 0);
//-->edit	//Check database for list of accounts, store in acts[]
//-->edit	//Check database for user_id, store in acnt
		    System.out.println("__DISPLAYING ACCOUNT DETAILS FOR: " + user + " (" + acnt + ")__");
			for(int i=0;i<acts.length;i++) {
//-->edit		//Check database for balance for account acts[i], store in balance
				System.out.println("--Account #: " + acts[i] + ", Balance: $" + balance);
			}
		    System.out.println("\n\n\n\n\n\n\n\n\n");
		    System.out.println("Or, Press [1] to Go Back to Previous Menu. Press [2] to Log Out...");
		    System.out.println("Any other key to input another user to view...");
		    retChar = cleanScan.getChar();
	    	
			if(retChar == '1') { 			
				System.out.println("Approving the User Account. Returning to previous menu...");
//-->edit			Access to database
//-->edit			return 3;
			}else if(retChar == '0'){ 
				System.out.println("Rejecting the User Account. Returning to previous menu...");
//-->edit			Access to database
//-->edit			return 0;
			}
		}while((retChar != '1')|(retChar != '0'));
		
		return -1;
	}
	

	/*View Transaction Logs Prompt - Prompt for employee to view all transaction logs
	 * Input: N/A
	 * Output: retInt, return the number if success
	 * */
	public static int viewLogsPrompt() {
		int retChar;
		int [] logs = {0,0,0};
		int src = 0;
		int dsn = 0;
		int amount = 0;
		String timestamp = "";
		String status = "";
		
		do{
//-->edit	Access to database, to pull all transaction logs, store in logs[]
			
			for(int i=0;i<logs.length;i++) {
			    System.out.println(timestamp + ": Account #" + src + "posted a transfer of $" + amount 
			    		+ " to Account #" + dsn + ". The Request is " + status +".");
			}
			
		    System.out.println("\n\n\n\n\n\n\n\n\n");
		    System.out.println("Or, Press [1] to Go Back to Previous Menu. Press [2] to Log Out...");
		    System.out.println("Any other key to refresh and view logs again...");
		    retChar = cleanScan.getChar();
	    	
			if(retChar == '1') { 			
				System.out.println("Approving the User Account. Returning to previous menu...");
//-->edit			Access to database
//-->edit			return 3;
			}else if(retChar == '0'){ 
				System.out.println("Rejecting the User Account. Returning to previous menu...");
//-->edit			Access to database
//-->edit			return 0;
			}
		}while((retChar != '1')|(retChar != '0'));
		
		return -1;
	}
}
