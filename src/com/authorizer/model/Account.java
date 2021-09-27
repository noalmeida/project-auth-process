package com.authorizer.model;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    
	@JsonProperty("flow-process")
	private String flowProcess;
	@JsonProperty("active-card")
	private boolean activeCard;
	@JsonProperty("available-limit")
	private int avaliableLimit;
	@JsonProperty("transaction")
	private List<Transactions> transactions;
	
	
	
	
	public String getFlowProcess() {
		return flowProcess;
	}

	public void setFlowProcess(String flowProcess) {
		this.flowProcess = flowProcess;
	}

	public boolean isActiveCard() {
		return activeCard;
	}

	public void setActiveCard(boolean activeCard) {
		this.activeCard = activeCard;
	}

	public int getAvaliableLimit() {
		return avaliableLimit;
	}

	public void setAvaliableLimit(int avaliableLimit) {
		this.avaliableLimit = avaliableLimit;
	}

	public List<Transactions> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(flowProcess, activeCard, avaliableLimit, transactions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return flowProcess == other.flowProcess && activeCard == other.activeCard && avaliableLimit == other.avaliableLimit
				&& Objects.equals(transactions, other.transactions);
	}

	@Override
	public String toString() {
		return "Account [flowProcess=" + flowProcess + ", activeCard=" + activeCard + ", avaliableLimit=" + avaliableLimit + ", transactions="
				+ transactions + "]";
	}

	public Account() {
		super();
	}

	public Account(boolean activeCard, int avaliableLimit, List<Transactions> transactions, String flowProcess) {
		super();
		this.flowProcess = flowProcess;
		this.activeCard = activeCard;
		this.avaliableLimit = avaliableLimit;
		this.transactions = transactions;
	}

}
