package com.project0.Bank;

//import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.ListIterator;

public class Session {
	
	static int direction;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//START PROMPT METHOD - START OF THE ACTUAL PROGRAM
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static User startPrompts() {
		direction = initialPrompt();
		if(direction == 1) {
			//System.out.println("New user requesting an account...");
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
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//INITIAL PROMPT METHOD - MAIN MENU FOR USERS NOT LOGGED IN
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int initialPrompt() {
		
	    int retInt;
		System.out.println("---------------------------------------------------------");
		System.out.println("---------------------------------------------------------");
	    System.out.println("WELCOME TO BANK OF REVATURE LOCATED IN TRENTON, NJ.");
	    System.out.println("__TO BEGIN THE SERVICES, PLEASE MAKE A SELECTION__");
	    System.out.println("---Create New Account [1]:");
	    System.out.println("---Log In To Your Account [2]:");
	    System.out.println("Any other selection will restart the input process...");
	    do{
	    	retInt = cleanScan.getInt();
			if(retInt == 1) {
				System.out.println("---------------------------------------------------------");
		    	return 1; //Return selection for new account, 1
		    }else if(retInt == 2) {
				System.out.println("---------------------------------------------------------");
		    	return 2;
		    }else {
		    	System.out.printf("...invalid selection, try again...");
	    		retInt = -1;
		    }
	    }while((retInt < 1) | (retInt > 2));
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//NEW USER PROMPT METHOD - MENU FOR SUBMITTING A USER ACCOUNT FOR APPROVAL
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void newUserPrompt() {
		String username;
		String password;
		int balance;
		int retVal = -1;
		String status;
		int uid;

	    System.out.println("---------------------------------------------------------");
	    do { //Loop while the retVal is not 1 or 0
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
					System.out.println("---------------------------------------------------------");
				}else {
					System.out.println("...issue with creation, restarting input...");
					retVal = -1; //Set retVal to keep in the loop
				}
		    }else if(retVal == 0){ //check for 0, to return to previous menu
				System.out.println("Returning Back to Opening Menu...");
				System.out.println("---------------------------------------------------------");
		    }
	    }while((retVal > 1) | (retVal < 0));
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//RETURNING USER PROMPT METHOD - MENU FOR LOGGING IN
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static User retUserPrompt() {
		String username;
		String password;
		int retInt = -1;
		String act_type;
		String status;
		int uid;
		User user = null;
		System.out.println("---------------------------------------------------------");
	    do{//Loop while the retVal is not y or 0
	    	System.out.println("__ENTER USER CREDENTIALS__");
	    	do {
		    	do {
			    	do {
			    		System.out.printf("---UserName: ");
			    		username = cleanScan.getStr(); //Wait for input
				    	System.out.printf("---Password: ");
				    	password = cleanScan.getStr(); //Wait for input
				    	status = connectDB.get_status_w_name(username); // check if username exists
				    	if(status==null) {System.out.println("...username doesn't exist, please try again...");}
				    }while(status==null);
			    	if(status.equals("pending")) {System.out.println("...account is pending, please try another account...");}
			    }while(status.equals("pending"));
				uid = connectDB.get_id_w_name_pw(username,password);
				if(uid == 0) {System.out.println("...incorrect log in info, please try another account...");}
	    	}while(uid == 0);
		    	
		    System.out.println("Input [1] to Confirm Submission? Or, Press [0] to Go Back...");
		    System.out.println("Any other integer selection will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 1) { //check for 1, to confirm
				act_type = connectDB.get_type_w_name(username);
				if(act_type.equals("employee")) {
					//System.out.printf("Logging into employee account...");
					user = new Employee(uid,username,status);
					//System.out.println(user);
					System.out.println("---------------------------------------------------------");
					return user;
				}else if (act_type.equals("customer")) {
					//System.out.printf("Logging into customer account...");
					user = new Customer(uid,username,status);
					//System.out.println(user);
					System.out.println("---------------------------------------------------------");
					return user;
				}else {
					System.out.printf("...issue pulling up results, try again...");
					retInt = -1;
				}
		    }else if(retInt == 0){ //check for 0, to return to previous menu
				System.out.println("Returning Back to Opening Menu...");
				System.out.println("---------------------------------------------------------");
				return null;
		    }else {
		    	System.out.println("...invalid selection, try again...");
		    	retInt = -1;
			}
	    }while((retInt < 0)|(retInt > 1));
	    return null; //return null if dropping out of proper return or the loop
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//USER PROMPT METHOD - CONTROLS WHICH MENU IS SEEN - EMPLOYEE VS CUSTOMER
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void loggedIn(User user) {
		String type = user.getType();
		int retInt; //potentially additional logic based on returned values
		if (type.equals("customer")) {
			retInt = customerPrompt(user);
		}else if(type.equals("employee")) {
			retInt = employeePrompt(user);
		}else {
			System.out.println("...Error, falling out of conditions...");
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CUSTOMER PROMPT METHOD - FIRST MENU FOR CUSTOMER SELECTIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int customerPrompt(User user) { 
		int retInt;
		int retVal = 0;

	    System.out.println("---------------------------------------------------------");
		System.out.println("WELCOME " + user.getUsername() + ". HOW MAY WE HELP YOU TODAY?");
	    do{
		    System.out.println("__PLEASE MAKE A SELECTION__");
		    System.out.println("---Apply For A New Banking Account [1]:");
		    System.out.println("---View Your Accounts [2]:");
		    System.out.println("Press [0] to Log Out. Any other selection will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 1) { // Apply For A New Banking Account
				//System.out.println("Navigating to Application Menu...");
				retVal = newAccountPrompt(user);
				if(retVal == 1) {retInt = -1;} //successful account request, set retInt to -1 to stay in loop
				else if(retVal == 0){return 0;} //return a 0 to indicate logging out
			}else if(retInt == 2){ //View Customer Accounts
				System.out.println("Navigating to Account Viewing Menu...");
				System.out.println("---------------------------------------------------------");
				retVal = accountFocusPrompt(user);
				if(retVal == 1) {retInt = -1;} //successful account request, set retInt to -1 to stay in loop
				else if(retVal == 0){return 0;} //return a 0 to indicate logging out
			}else if(retInt == 0){ //check for 0, to return to previous menu
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
		    	System.out.println("...invalid selection, try again...");
		    	retVal = -1;
			}
	    }while((retInt > 2) | (retInt < 0)); 
	   
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//NEW BANKING ACCOUNT PROMPT METHOD - MENU FOR SUBMITTING A BANK APPLICATION REQUEST
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int newAccountPrompt(User user) {
		int balance;
		int retInt ;
		int retVal;

	    System.out.println("---------------------------------------------------------");
	    do{ 
			System.out.println("__PLEASE SUBMIT DETAILS FOR NEW ACCOUNT APPLICATION__");
			System.out.println("---Starting Balance:");
			do {balance = cleanScan.getInt(); //Wait for input
				if(balance == 0) {System.out.printf("...invalid amount, try again...");}
			}while(balance == 0);
		   
		    System.out.println("Input [2] to Confirm Submission?");
		    System.out.println(" Or, Press [1] to Go Back. Press [0] to Log Out...");
		    System.out.println("Any other integer selection will restart the input process...");
			retInt = cleanScan.getInt(); //Wait for input
			
			if(retInt == 2) { 
				System.out.println("Submitting Request for New Account. Returning to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				retVal = connectDB.set_account_w_user(user.getUsername(),balance);
				if(retVal < 1) {
					System.out.println("...issue submitting request, try again");
					retInt = -1; //set retInt to -1 to stay in loop
				}else {return 1;}  //return the value 1 in order to indicate successful creation, passing back to calling method
			}else if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1; //return the value 1 passing back to calling method
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0; //return the value 0 passing back to calling method to ask for log out
			}else {
		    	System.out.printf("...invalid selection, try again...");
		    	retInt = -1;
			}
	    }while((retInt > 2) | (retInt < 0));
	    
	    return -1;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//VIEW BANK ACCOUNTS PROMPT METHOD - MENU FOR VIEWING ACCOUNTS/SELECTING ACCOUNT
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int accountFocusPrompt(User user) {
		int retView = 0;
		int retInt;
		int retVal;
		
		ArrayList<BankAccount> accts = connectDB.get_accounts_w_name(user.getUsername());
		ArrayList<BankAccount> pends = new ArrayList<BankAccount>();
		//for(int i=0;i<accts.size();i++) { System.out.println("---Account# " + accts.get(i).getAccount_id());} 
		
		ArrayList<BankAccount> active = new ArrayList<BankAccount>();
		for(BankAccount a:accts) { //identify all active accounts
			if(a.getStatus().equals("active")){
				active.add(a);
				pends.addAll(connectDB.confirm_account_w_pending_transfer(a.getAccount_id()));
			}
		}
		System.out.println("---------------------------------------------------------");
		do{System.out.println("__PLEASE TYPE IN YOUR ACCOUNT TO VIEW__");
		if(active.size() < 1) {
			System.out.println("---No Active accounts located---");
		}else {
		for(int j=0;j<active.size();j++) { //Display the list of accounts
			System.out.println("---Account# " + active.get(j).getAccount_id() + ", $" + active.get(j).getBalance() + " [" + j + "]:");
		}
		
		if(pends.size() > 0) {
			System.out.printf("Note: Account(s) ");
			for(int j=0;j<pends.size();j++) {System.out.printf("[" + pends.get(j).getAccount_id() + "]");} 
			System.out.println(" have pending transfer(s) awaiting your review");
		}
		do {retView = cleanScan.getInt(); //Wait for input, valid it date it is between
			if(retView >= active.size()) {System.out.printf("...invalid selection, try again...");}
		}while(retView >= active.size());
		
		System.out.println("Input [2] to Confirm View Selection?");
		}
	    System.out.println("Press [1] to Go Back. Press [0] to Log Out...");
	    System.out.println("Any other integer selection will restart the input process...");
	    
		retInt = cleanScan.getInt(); //Wait for input
		if(retInt == 2) { 
			if(active.size()< 1) {
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}
			System.out.println("Navigating to Account Menu...");
			System.out.println("---------------------------------------------------------");
			retVal = customerActionPrompt(active.get(retView));
			if(retVal == 1) {retInt = -1;}
			if(retVal == 0) {return 0;}
		}else if(retInt == 1){ 
			System.out.println("Returning Back to Previous Menu...");
			System.out.println("---------------------------------------------------------");
			return 1;
		}else if(retInt == 0){ 
			System.out.println("Logging out....");
			System.out.println("---------------------------------------------------------");
			return 0;
		}else {
	    	System.out.printf("...invalid selection, try again...");
		}
	    }while((retInt < 0)|(retInt > 2));
		
	    return -1;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CUSTOMER ACCOUNT ACTIONS PROMPT METHOD - SELECTION MENU FOR PERFORMING ACTIONS ON ACCOUNTS - DEPOSIT/WITHDRAW/TRASFER
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int customerActionPrompt(BankAccount ba) {
		int retInt;
		int retVal;
		BankAccount ba2 = ba;
		
		System.out.println("---------------------------------------------------------");
		do {
		    System.out.println("__MAKE A SELECTION__");
		    System.out.println("---Deposit To An Account [2]:");
		    System.out.println("---Withdraw From Account [3]:");
		    System.out.println("---Post/Accept Transfer Funds [4]:");
		    System.out.println("Press [1] to go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other selection will restart the input process...");
		    retInt = cleanScan.getInt(); //Wait for input
		    
			if(retInt == 2) { 			
				System.out.println("Navigating to Deposit Menu...");
				System.out.println("---------------------------------------------------------");
				retVal = despositPrompt(ba2);
				if(retVal == 2) {
					ba2 = connectDB.get_account_w_aid(ba.getAccount_id());
					retInt = -1; //set retInt to stay in loop to stay in loop
				}else if(retVal == 1) {
					retInt = -1; //set retInt to stay in loop to stay in loop
				}else if(retVal == 0) {
					return 0;
				}
			}else if(retInt == 3){ 
				System.out.println("Navigating to Withdraw Menu...");
				System.out.println("---------------------------------------------------------");
				retVal = withdrawPrompt(ba2);
				if(retVal == 2) {
					ba2 = connectDB.get_account_w_aid(ba.getAccount_id());
					retInt = -1; //set retInt to stay in loop to stay in loop
				}else if(retVal == 1) {
					retInt = -1; //set retInt to stay in loop to stay in loop
				}else if(retVal == 0) {
					return 0;
				}
			}else if(retInt == 4){ 
				System.out.println("Navigating to Transfer Menu...");
				System.out.println("---------------------------------------------------------");
				retVal = TransferPrompts(ba2); 
				if(retVal == 2) {
					retInt = -1; //set retInt to stay in loop to stay in loop
				}else if(retVal == 1) {
					retInt = -1; //set retInt to stay in loop to stay in loop
				}else if(retVal == 0) {
					return 0;
				}
			}else if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
	    }while((retInt > 4) | (retInt < 0));
	
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CUSTOMER DEPOSIT PROMPT METHOD 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int despositPrompt(BankAccount ba) {
		int retVal;
		int retInt;
		int actid = ba.getAccount_id();
		int balance = ba.getBalance();
		
		System.out.println("---------------------------------------------------------");
		do {
			balance = connectDB.get_account_balance(actid);
			
			System.out.println("Account#: " + actid + " -- $" + balance); 
			System.out.println("__PLEASE INDICATE HOW MUCH TO DEPOSIT__");
			do {retVal = cleanScan.getInt(); //Wait for input
				if(retVal == 0) {System.out.printf("...invalid amount, try again...");}
			}while(retVal == 0);
			
		    System.out.println("Input [2] to Confirm Deposit...");
		    System.out.println("Or, Press [1] to Go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other integer key will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 2) { 			
				retVal = connectDB.set_account_w_deposit(actid,retVal);
				if(retVal > 0) {
					System.out.println("Deposit has been made. Returning to previous menu.");
					System.out.println("---------------------------------------------------------");
					return 2;
				}else if(retVal==0){
					System.out.println("Issue arose with deposit, returning Back to Previous Menu...");
					System.out.println("---------------------------------------------------------");
					return 1;
				}else {System.out.println("Fatal deposit issue, returning Back to Previous Menu...");}
			}else if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
				retInt = -1;
			}
		}while((retInt > 2) | (retInt < 0));

	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CUSTOMER WITHDRAW PROMPT METHOD 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int withdrawPrompt(BankAccount ba) {
		int retVal;
		int retInt;
		int actid = ba.getAccount_id();
		int balance = ba.getBalance();
		BankAccount ba2;

		System.out.println("---------------------------------------------------------");
		do {
			balance = connectDB.get_account_balance(actid);
			
			System.out.println("Account#: " + actid + " -- $" + balance); 
			System.out.println("__PLEASE INDICATE HOW MUCH TO WITHDRAW__");
			do {retVal = cleanScan.getInt(); //Wait for input
				ba2 = connectDB.get_account_w_aid(actid);
				balance = ba2.getBalance();
				if(retVal == 0) {System.out.printf("...invalid amount, try again...");}
				if(retVal > balance) {System.out.println("...amount requested larger than funds, try again...");}
			}while((retVal == 0)|(retVal > balance));
			
		    System.out.println("Input [2] to Confirm Withdrawal...");
		    System.out.println("Or, Press [1] to Go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other key will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 2) { 			
				retVal = connectDB.set_account_w_withdraw(actid,retVal);
				if(retVal > 0) {
					System.out.println("Withdrawal has been made. Returning to previous menu.");
					System.out.println("---------------------------------------------------------");
					return 2;
				}else if(retVal==0){
					System.out.println("Issue arose with withdrawal, returning Back to Previous Menu...");
					System.out.println("---------------------------------------------------------");
					return 1;
				}else {System.out.println("Fatal deposit issue, returning Back to Previous Menu...");}
			}else if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
			}
		}while((retInt > 2) | (retInt < 0));

	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CUSTOMER TRANSFER PROMPT METHOD 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int TransferPrompts(BankAccount ba) {
		int retInt;
		int retVal = 0;

		int actid = ba.getAccount_id();
		int balance = ba.getBalance();
		//BankAccount ba2;
		ArrayList<BankAccount> accts;
		
		System.out.println("---------------------------------------------------------");
		do {
			System.out.println("Account#: " + actid + " -- $" + balance); 
			accts = connectDB.confirm_account_w_pending_transfer(ba.getAccount_id());
			//for(int i=0;i<accts.size();i++) { System.out.println("---Account# " + accts.get(i).getAccount_id());} //Display the list of accounts
			
			//Selection Prompts in conditionals only appears when pending action are required
			if(accts.size() > 0) { System.out.println("NOTE: YOU HAVE PENDING TRANSFER REQUEST(S) TO ACTION__");}
			System.out.println("__PLEASE INDICATE WHETHER__");
			System.out.println("---You would like to post a Transfer request [2]");
			if(accts.size() > 0) { System.out.println("---You would like to action the pending requests [3]");}
			System.out.println("Or, Press [1] to Go Back to Previous Menu. Press [0] to Log Out...");
			do {retInt = cleanScan.getInt(); //Wait for input
				if(retInt > 3) {System.out.printf("...invalid selection, try again...");}
			}while(retInt > 3);

			if (retInt == 2){ //Post transfer, calling internal method postTransferPrompt
				System.out.println("Navigating Transfer Post Menu...");
				System.out.println("---------------------------------------------------------");
				retVal = postTransferPrompt(ba);
				if(retVal == 2) {return 1;}
				else if(retVal == 1) {return 1;}
				else if(retVal == 0) {return 0;}
			}else if(retInt == 3) { //Action pending accounts, calling internal method acceptTransferPrompt
				if(accts.size() > 0) { 
					retVal = acceptTransferPrompt(ba);
					System.out.println("Navigating Pending Transfers Menu...");
					System.out.println("---------------------------------------------------------");
					return retVal;
				}else {
					System.out.printf("...invalid selection, try again...");
					retInt = -1;
				}
			}else if (retInt == 1){
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if (retInt == 0){
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else{
				System.out.printf("...invalid selection, try again...");
				retInt = -1;
			}
		}while((retInt < 0) | (retInt > 3));
		
	    return -1; //return -1 if dropping out of proper return or the loop
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// POST TRANSFER REQUEST TO ANOTHER ACCOUNT PROMPT
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int postTransferPrompt(BankAccount ba) {
		//String user;
		int amount,balance;
		int src = ba.getAccount_id();
		int dst;
		int valid = 0;
		int retInt, retVal;
		BankAccount ba2 = null;

		System.out.println("---------------------------------------------------------");
		do{
			System.out.println("__PLEASE PROVIDE INTENDED ACCOUNT TO POST TRANSFER REQUEST__");
		    System.out.printf("--Account#");
		    do {
		    	dst = cleanScan.getInt();
		    	//Check database for account, if it exists, return account number, else return 0
		    	if((dst < 1)|(dst == src)) {System.out.println("...invalid input, try again...");}
		    	else {
		    		ba2 = connectDB.get_account_existance(dst);
			    	if (ba2 != null) {valid = 1;}
			    	else{System.out.println("...invalid user, try again...");}
		    	}
		    }while((dst < 1)|(valid==0)|(dst==src));
		    
			System.out.println("__PLEASE INDICATE HOW MUCH YOU WISH TO TRANSFER__");
			do {amount = cleanScan.getInt(); //Wait for input
				ba2 = connectDB.get_account_w_aid(src);
				balance = ba2.getBalance();
				if(amount <= 0) {System.out.printf("...invalid amount, try again...");}
				if(amount > balance) {System.out.println("...amount requested larger than funds, try again...");}
			}while((amount == 0)|(amount > balance));
//			System.out.println("__PLEASE PROVIDE USERNAME AND SELECT ACCOUNT TO POST TRANSFER REQUEST__");
//		    System.out.println("---UserName:");
//		    do {
//		    	user = cleanScan.getStr();
//		    	//Check database for account, if it exists, return account number, else return 0
//		    	if(valid == 0) {System.out.println("...invalid user, try again...");}
//		    }
//		    while(valid == 0);
//		    //Check database for list of accounts
//		    System.out.println("__Select Account__");
//		    for(int i=0;i<acts.length;i++) {//Display List of Customer Accounts
//		    	System.out.println("---Account #: " + acts[i] + " [" + i + "]:");
//		    }
//		    do {act = cleanScan.getInt();
//		    	if(act > acts.length) {System.out.printf("...invalid selection, try again...");}
//		    }while(act > acts.length);
	    	
		    System.out.println("Input [2] to Confirm the Transfer.");
		    System.out.println("Or, Press [0] to Go Back to Previous Menu. Press [1] to Log Out...");
		    System.out.println("Any other key will restart the input process...");
		    retInt = cleanScan.getInt();
			if(retInt == 2) { 			
				retVal = connectDB.post_transfer(src,dst,amount);
				if(retVal > 0) {
					System.out.println("A transfer as been posted. Returning to previous menu...");
					System.out.println("---------------------------------------------------------");
					return 2;
				}else if(retVal==0){
					System.out.println("Issue arose with transfer, returning Back to Previous Menu...");
					System.out.println("---------------------------------------------------------");
					return 1;
				}else {
					System.out.printf("Issue arose with transfer, try again...");
					retInt = -1;
				}
			}else if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
				retInt = -1;
			}
		}while((retInt < 0) | (retInt > 2));
		
		return -1;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ACCEPT TRANSFER REQUESTS 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int acceptTransferPrompt(BankAccount ba) {
		int retInt = -1;
		int retVal = -1;
		BankAccount ba2;
		//ArrayList<BankAccount> accts = new ArrayList<BankAccount>();
		ArrayList<Transaction> tLogs = new ArrayList<Transaction>();

		System.out.println("---------------------------------------------------------");
		do {
			//Get all accounts that have posted transfers to this account
			tLogs = connectDB.get_transaction_pending(ba.getAccount_id());
			
			if(tLogs.size() > 0) {
				System.out.println("__YOU HAVE PENDING REQUEST(S) TO ACTION__");
				for(Transaction i:tLogs) {
					System.out.println("Account# " + i.getSrc() 
					+ " has posted a transfer of $" + i.getAmount() + ". Please advise if you will:");
					System.out.println("---Accept the Transfer Request [0]");
					System.out.println("---Reject the Transfer Request [1]");	
					System.out.println("---Temporarily Dismiss the Request [2]");	
					do {
						retInt = cleanScan.getInt();
						if(retInt == 0){ 
							ba2 = connectDB.get_account_w_aid(i.getSrc());
							if(ba2.getBalance()<i.getAmount()) {
								System.out.println("Not enought funds to fulfill transfer...");
							}else {
								System.out.println("Accepting the Transfer...");
								System.out.println("---------------------------------------------------------");
								connectDB.accept_transfer(i.getTransaction_id());
							}
						}else if(retInt == 1){ 
							System.out.println("Rejecting the Transfer...");
							System.out.println("---------------------------------------------------------");
							connectDB.reject_transfer(i.getTransaction_id());
						}else if(retInt == 2){ 
							System.out.println("Dismissing the Request Temporarily...");
							System.out.println("---------------------------------------------------------");
						}else {
							System.out.printf("...invalid selection, try again...");
							retInt = -1;
						}
					}while((retInt > 2)|(retInt < 0));
				}
			}else {System.out.println("__YOU HAVE HANDLED ALL PENDING REQUEST(S)__");}
			
		    System.out.println("Press [1] to Go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other integer key will restart the input process...");
			retVal = cleanScan.getInt();
			if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
				retVal = -1;
			}
		}while((retVal > 1)|(retVal < 0));
		
		return -1;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//EPLOYEE PROMPT METHOD - FIRST MENU FOR EMPLOYEE SELECTIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int employeePrompt(User user) {
		int retInt;
		int retVal = -1;
		String name = user.getUsername();

		System.out.println("---------------------------------------------------------");
	    System.out.println("WELCOME BACK, " + name + ".");
	    do{
		    System.out.println("__MAKE A SELECTION FROM THE MENU__");
		    System.out.println("---Approve/Reject A Customer Account [1]:");
		    System.out.println("---Approve/Reject A Banking Account Application [2]:");
		    System.out.println("---View A Customer's Accounts [3]:");
		    System.out.println("---View Transaction Logs [4]:");
		    System.out.println("Or, press [0] to Log Out. Any other integer key will restart the input process...");

	    	retInt = cleanScan.getInt();
			if(retInt == 1) {
				retVal = reviewUserPrompt(user);
				if(retVal == 1) {retInt = -1;} //keep in loop as this is the previous menu
				else if(retVal == 0) {return 0;}
		    }else if(retInt == 2) {
				retVal = reviewAccountPrompt(user);
				if(retVal == 1) {retInt = -1;} //keep in loop as this is the previous menu
				else if(retVal == 0) {return 0;}
		    }else if(retInt == 3) {
		    	retVal = viewUserPrompt(user); 
				if(retVal == 1) {retInt = -1;} //keep in loop as this is the previous menu
				else if(retVal == 0) {return 0;}
		    }else if(retInt == 4) {
		    	retVal = viewLogsPrompt(user); 
				if(retVal == 1) {retInt = -1;} //keep in loop as this is the previous menu
				else if(retVal == 0) {return 0;}
		    }else if(retInt == 0) {
		    	return 0;
		    }else {
	    	System.out.printf("...invalid selection, try again...");
		    }
	    }while((retInt < 0) | (retInt > 4));
	    
	    return -1;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//REVIEW PENDING USER ACCOUNT REQUESTS - PROMPT FOR ACTIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int reviewUserPrompt(User u) {
		int retInt,j;
		ArrayList<UserAccount> acts;
		UserAccount user;
		int retVal = 0;

		System.out.println("---------------------------------------------------------");
		do{
			System.out.println("__PLEASE SELECT PENDING USER APPLICATION TO APPROVE OR REJECT__");
			acts = connectDB.get_pending_users();
			j = 0;
			for(UserAccount i:acts) {
				System.out.println("--- [" + j++ + "] User Account Request from: " + i.getUser_name());
			}

			do {retInt = cleanScan.getInt();
				if(retInt>=acts.size()) {System.out.printf("...invalid selection, try again...");}
		    }while(retInt>=acts.size());
			user = acts.get(retInt); //store selected account in user variable
			
		    System.out.println("---[2] to Approve User Account request:");
		    System.out.println("---[3] to Reject User Account request:");
		    System.out.println("Or, Press [1] to Go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other key will cancel selection, and restart the selection process...");
		    retInt = cleanScan.getInt();
	    	
			if(retInt == 2) {
				retVal = connectDB.accept_reject_user(user.getUser_name(),"active");
				if(retVal > 0) {
					System.out.println("Approving User Account. Returning to previous menu...");
					System.out.println("---------------------------------------------------------");
					return 1;
				}
				else {
					System.out.printf("...issue arose, try again...");
					retInt = -1;
				}
			}else if(retInt == 3){ 
				retVal = connectDB.accept_reject_user(user.getUser_name(),"rejected");
				if(retVal > 0) {
					System.out.println("Rejecting User Account. Returning to previous menu...");
					System.out.println("---------------------------------------------------------");
					return 1;
				}
				else {
					System.out.printf("...issue arose, try again...");
					retInt = -1;
				}
			}else if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
				retInt = -1;
			}
		}while((retInt > 3)|(retInt < 0));
		
		return -1;
		}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//REVIEW BANK ACCOUNT REQUESTS - PROMPT FOR ACTIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int reviewAccountPrompt(User user) {
		int retInt, j;
		int retVal;
		ArrayList<BankAccount> acts = new ArrayList<BankAccount>();
		BankAccount ba;
		
		System.out.println("---------------------------------------------------------");
		do{
			System.out.println("__PLEASE SELECT BANKING ACCOUNT APPLICATION TO APPROVE OR REJECT__");
			acts = connectDB.get_pending_accounts();
			j = 0;
			for(BankAccount i:acts) {
				System.out.println("--- [" + j++ + "] Bank Account " + i.getAccount_id() + " pending with balance $" + i.getBalance());
			}
			
			do {retInt = cleanScan.getInt();
				if(retInt>=acts.size()) {System.out.printf("...invalid selection, try again...");}
		    }while(retInt>=acts.size());
			ba = acts.get(retInt); //store selected account in user variable
			
		    System.out.println("---[2] to Approve Bank Account application:");
		    System.out.println("---[3] to Reject Bank Account application:");
		    System.out.println("Or, Press [1] to Go Back to Previous Menu. Press [0] to Log Out...");
		    System.out.println("Any other key will cancel selection, and restart the selection process...");
		    retInt = cleanScan.getInt();
	    	
			if(retInt == 2) {
				retVal = connectDB.accept_reject_account(ba.getAccount_id(),"active");
				if(retVal > 0) {
					System.out.println("Approving the Banking Account. Returning to previous menu...");
					System.out.println("---------------------------------------------------------");
					return 1;
				}
				else {
					System.out.printf("...issue arose, try again...");
					retInt = -1;
				}
			}else if(retInt == 3){ 
				retVal = connectDB.accept_reject_account(ba.getAccount_id(),"rejected");
				if(retVal > 0) {
					System.out.println("Rejecting the Banking Account. Returning to previous menu...");
					System.out.println("---------------------------------------------------------");
					return 1;
				}
				else {
					System.out.printf("...issue arose, try again...");
					retInt = -1;
				}
			}else if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}else {
				System.out.printf("...invalid selection, try again...");
				retInt = -1;
			}
		}while((retInt > 3)|(retInt <0));
		
		return -1;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int viewUserPrompt(User usr) {
		String name;
		int retInt;
		ArrayList<BankAccount> acts = new ArrayList<BankAccount>();
		UserAccount ua;
	    String status;

		System.out.println("---------------------------------------------------------");
		do{
			System.out.println("__PLEASE PROVIDE USERNAME TO VIEW__");
		    System.out.printf("---UserName:");
			do {
		    	name = cleanScan.getStr();
		    	status = connectDB.get_status_w_name(name); // return status if username exists
		    	if(status==null) {System.out.printf("...invalid input, try again...");}
		    }while((name.isEmpty())|(status==null));
		    
			ua = connectDB.get_user_w_name(name);
		    System.out.println("DISPLAYING " + " DETAILS:"); 
		    System.out.println("__" + ua.getType().toUpperCase() + " " + ua.getStatus().toUpperCase() 
		    + "--" + ua.getUser_name() + " ID# " + ua.getUser_id()  + "__");
		    
			acts = connectDB.get_accounts_w_name(name); //Check database for list of accounts, store in acts
			if(acts.size() < 1) {System.out.println("No accounts located...");}
			else {
				for(BankAccount ba:acts) {
					System.out.println("--"+"Account  ("+  ba.getStatus() + ") #: " + ba.getAccount_id() + ", Balance: $" + ba.getBalance());
				}
			}
		    System.out.println("");
		    System.out.println("When viewing is complete, press [1] to Go Back to Previous Menu.");
		    System.out.println("Or, press [0] to Log Out...");
		    System.out.println("Any other key to input another user to view...");
		    retInt = cleanScan.getInt();
	    	
		    if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}
		}while((retInt > 1)|(retInt < 0));
		
		return -1;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//VIEW TRANSACTION LOGS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int viewLogsPrompt(User user) {
		int retInt;
		ArrayList<Transaction> logs;
		
		System.out.println("---------------------------------------------------------");
		do{
			logs = connectDB.get_transaction_logs();
			
			for(Transaction i:logs) {
			    System.out.println("At " + i.getCreated() + ": Account# " + i.getSrc() + " posted a transfer of $" + i.getAmount() 
			    		+ " to Account #" + i.getDst()  + "."); 
			    System.out.println("The Request is " + i.getStatus() +".");
			}
			
		    System.out.println("");
		    System.out.println("When viewing is complete, press [1] to Go Back to Previous Menu.");
		    System.out.println("Or, press [0] to Log Out...");
		    System.out.println("Any other key to refresh and view logs again...");
		    retInt = cleanScan.getInt();
	    	
		    if(retInt == 1){ 
				System.out.println("Returning Back to Previous Menu...");
				System.out.println("---------------------------------------------------------");
				return 1;
			}else if(retInt == 0){ 
				System.out.println("Logging out...");
				System.out.println("---------------------------------------------------------");
				return 0;
			}
		}while((retInt > 1)|(retInt < 0));
		
		return -1;
	}
}
