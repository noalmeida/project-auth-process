package com.authorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Violations {
	
	@JsonProperty("violationType")
	private String violationType;

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public Violations(String violationType) {
		super();
		this.violationType = violationType;
	}

	public Violations() {
		super();
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((violationType == null) ? 0 : violationType.hashCode());
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
		Violations other = (Violations) obj;
		if (violationType == null) {
			if (other.violationType != null)
				return false;
		} else if (!violationType.equals(other.violationType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Violations [violationType=" + violationType + ", getViolationType()=" + getViolationType()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

//	@JsonProperty("insufficient-limit")
//	private boolean insufficientlimit;
//
//	@JsonProperty("accountalready-initialized")
//	private boolean accountalreadyInitialized;
//
//	@JsonProperty("account-notinitialized")
//	private boolean accountNotInitialized;
//
//	@JsonProperty("card-not-active")
//	private boolean cardNotActive;
//
//	@JsonProperty("highfrequency-small-interval")
//	private boolean highfrequencySmalinterval;
//
//	@JsonProperty("double-transaction")
//	private boolean doubleTransaction;
//
//	public boolean isInsufficientlimit() {
//		return insufficientlimit;
//	}
//
//	public void setInsufficientlimit(boolean insufficientlimit) {
//		this.insufficientlimit = insufficientlimit;
//	}
//
//	public boolean isAccountalreadyInitialized() {
//		return accountalreadyInitialized;
//	}
//
//	public void setAccountalreadyInitialized(boolean accountalreadyInitialized) {
//		this.accountalreadyInitialized = accountalreadyInitialized;
//	}
//
//	public boolean isAccountNotInitialized() {
//		return accountNotInitialized;
//	}
//
//	public void setAccountNotInitialized(boolean accountNotInitialized) {
//		this.accountNotInitialized = accountNotInitialized;
//	}
//
//	public boolean isCardNotActive() {
//		return cardNotActive;
//	}
//
//	public void setCardNotActive(boolean cardNotActive) {
//		this.cardNotActive = cardNotActive;
//	}
//
//	public boolean isHighfrequencySmalinterval() {
//		return highfrequencySmalinterval;
//	}
//
//	public void setHighfrequencySmalinterval(boolean highfrequencySmalinterval) {
//		this.highfrequencySmalinterval = highfrequencySmalinterval;
//	}
//
//	public boolean isDoubleTransaction() {
//		return doubleTransaction;
//	}
//
//	public void setDoubleTransaction(boolean doubleTransaction) {
//		this.doubleTransaction = doubleTransaction;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + (accountNotInitialized ? 1231 : 1237);
//		result = prime * result + (accountalreadyInitialized ? 1231 : 1237);
//		result = prime * result + (cardNotActive ? 1231 : 1237);
//		result = prime * result + (doubleTransaction ? 1231 : 1237);
//		result = prime * result + (highfrequencySmalinterval ? 1231 : 1237);
//		result = prime * result + (insufficientlimit ? 1231 : 1237);
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Violations other = (Violations) obj;
//		if (accountNotInitialized != other.accountNotInitialized)
//			return false;
//		if (accountalreadyInitialized != other.accountalreadyInitialized)
//			return false;
//		if (cardNotActive != other.cardNotActive)
//			return false;
//		if (doubleTransaction != other.doubleTransaction)
//			return false;
//		if (highfrequencySmalinterval != other.highfrequencySmalinterval)
//			return false;
//		if (insufficientlimit != other.insufficientlimit)
//			return false;
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		return "Violations [insufficientlimit=" + insufficientlimit + ", accountalreadyInitialized="
//				+ accountalreadyInitialized + ", accountNotInitialized=" + accountNotInitialized + ", cardNotActive="
//				+ cardNotActive + ", highfrequencySmalinterval=" + highfrequencySmalinterval + ", doubleTransaction="
//				+ doubleTransaction + "]";
//	}
//
//	public Violations() {
//		super();
//	}
//
//	public Violations(boolean insufficientlimit, boolean accountalreadyInitialized, boolean accountNotInitialized,
//			boolean cardNotActive, boolean highfrequencySmalinterval, boolean doubleTransaction) {
//		super();
//		this.insufficientlimit = insufficientlimit;
//		this.accountalreadyInitialized = accountalreadyInitialized;
//		this.accountNotInitialized = accountNotInitialized;
//		this.cardNotActive = cardNotActive;
//		this.highfrequencySmalinterval = highfrequencySmalinterval;
//		this.doubleTransaction = doubleTransaction;
//	}


}
