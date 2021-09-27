package com.authorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreated {
	@JsonProperty("account")
	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account accountCreated) {
		this.account = accountCreated;
	}

	@Override
	public String toString() {
		return "Operations [account=" + account + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountCreated other = (AccountCreated) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}


	public AccountCreated() {
		super();
	}

	public AccountCreated(Account account) {
		this.account = account;

	}

}
