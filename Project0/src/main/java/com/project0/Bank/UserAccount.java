package com.project0.Bank;

public class UserAccount {

	int user_id;
	String user_name;
	String status;
	String type;
	
	public UserAccount(int user_id, String user_name, String status, String type) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.status = status;
		this.type = type;
	}

	public String getUser_name() {
		return user_name;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UserAccount [user_id=" + user_id + ", user_name=" + user_name + ", status=" + status + ", type=" + type
				+ "]";
	}

}
