package com.authorizer.utils;

public enum ViolationsTypes {

	
	INSUFFICIENT_LIMIT("insufficient-limit"),
	ACCOUNTALREADY_INITIALIZED("accountalready-initialized"),
	ACCOUNT_NOTINITIALIZED("account-notinitialized"),
	CARD_NOT_ACTIVE("card-not-active"),
	HIGHFREQUENCY_SMALL_INTERVAL("highfrequency-small-interval"),
	DOUBLE_TRANSACTION("double-transaction");

	
	
	private String valor;
	
	ViolationsTypes(String valor) {
		this.valor = valor;
	}
	
	 public String getValor() {
		return valor;
	}
	
	
}
