package com.authorizer.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OperationsConsumed {

	@JsonProperty("account")
	private Account account;

	@JsonProperty("violations")
	private List<Violations> violations;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Violations> getViolations() {
		return violations;
	}

	public void setViolations(List<Violations> violations) {
		this.violations = violations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((violations == null) ? 0 : violations.hashCode());
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
		OperationsConsumed other = (OperationsConsumed) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (violations == null) {
			if (other.violations != null)
				return false;
		} else if (!violations.equals(other.violations))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OperationsConsumed [account=" + account + ", violations=" + violations + "]";
	}

	
	public OperationsConsumed() {
		super();
	}
	public OperationsConsumed(Account account, List<Violations> violations) {
		super();
		this.account = account;
		this.violations = violations;
	}

}
