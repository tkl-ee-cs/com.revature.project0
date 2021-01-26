package com.project0.Bank;

import java.util.ArrayList;

public class Employee extends User {
	
	UserAccount usr;
	ArrayList<BankAccount> acntlist; // Create an ArrayList object
	
	public Employee(int id, String username, String status) {
		super();
		usr = new UserAccount(id,username,status,"employee");
		acntlist = new ArrayList<BankAccount>();
	}
	
	@Override
	public void setupUser() {
		System.out.println("createUser -- Employee");
	}
	
	@Override
	public BankAccount viewAccount() {
		System.out.println("display selected customer selected bank account");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void approveReject() {
		System.out.println("approve/reject posted transfer");
		System.out.println("approve/reject posted transfer");
		// TODO Auto-generated method stub
		
	}
	
	public UserAccount getUsr() {
		return usr;
	}

	@Override
	public void storeAccounts() {
		System.out.println("display list customers accounts");
		
	}

	public String getStatus() {
		return usr.getStatus();
	}
	
	public String getType() {
		return usr.getType();
	}

	public String getUsername() {
		return usr.getUser_name();
	}
	
	@Override
	public String toString() {
		return "Employee [usr=" + usr + ", acntlist=" + acntlist + "]";
	}

}
