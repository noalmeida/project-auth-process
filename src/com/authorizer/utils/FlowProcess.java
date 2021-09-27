package com.authorizer.utils;

public enum FlowProcess {
	
	ACCOUNT_CREATION("CREATION"),
	AUTHORIZER("AUTHORIZER");

    
	private String valor;
	
	 FlowProcess(String valor) {
		this.valor = valor;
	}
	
	 public String getValor() {
		return valor;
	}

}
