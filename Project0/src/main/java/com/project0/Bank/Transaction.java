package com.project0.Bank;

import java.sql.Timestamp;

public class Transaction {
	int transaction_id;
	int src;
	int dst;
	String status;
	int amount;
	String action;
	Timestamp created;
	
	public Transaction(int transaction_id, int src, int dst, String status, int amount, String action,
			Timestamp created) {
		super();
		this.transaction_id = transaction_id;
		this.src = src;
		this.dst = dst;
		this.status = status;
		this.amount = amount;
		this.action = action;
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public int getSrc() {
		return src;
	}

	public int getDst() {
		return dst;
	}

	public String getAction() {
		return action;
	}

	public Timestamp getCreated() {
		return created;
	}
	
	
}
