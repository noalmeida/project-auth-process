package com.authorizer.model;


import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transactions {

	@JsonProperty("merchant")
	String merchant;
	@JsonProperty("amount")
	int amout;
	@JsonProperty("time")
	String time;

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public int getAmout() {
		return amout;
	}

	public void setAmout(int amout) {
		this.amout = amout;
	}

	public String getTime() {
		return time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amout, merchant, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transactions other = (Transactions) obj;
		return amout == other.amout && Objects.equals(merchant, other.merchant) && Objects.equals(time, other.time);
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Transactions [merchant=" + merchant + ", amout=" + amout + ", time=" + time + "]";
	}

	public Transactions() {
		super();
	}

	public Transactions(String merchant, int amout, String time) {
		super();
		this.merchant = merchant;
		this.amout = amout;
		this.time = time;
	}

}
