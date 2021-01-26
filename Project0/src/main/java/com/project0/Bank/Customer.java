package com.project0.Bank;

import java.util.ArrayList;

public class Customer extends User {

	UserAccount usr;
	ArrayList<BankAccount> acntlist; // Create an ArrayList object
	
	public Customer(int id, String username, String status) {
		super();
		usr = new UserAccount(id,username,status,"customer");
		acntlist = new ArrayList<BankAccount>();
	}
	
	@Override
	public void setupUser() {
		System.out.println("createUser -- Customer");
	}

	@Override
	public BankAccount viewAccount() {
		System.out.println("display selected bank account");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void approveReject() {
		System.out.println("approve/reject posted transfer");
		// TODO Auto-generated method stub
	}

	@Override
	public void storeAccounts() {
		System.out.println("get list of customer's accounts into list");
		// TODO Auto-generated method stub
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
		return "Customer [usr=" + usr + ", acntlist=" + acntlist + "]";
	};
	
}
