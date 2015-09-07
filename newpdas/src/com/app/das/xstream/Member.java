package com.app.das.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Member {

	private String accountNo;
	private String password;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
