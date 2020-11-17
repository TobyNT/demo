package com.nxt.demo.dto;

public class DiscountItem {
	private String voucherId;
	private double value;

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public DiscountItem(String voucherId, double value) {
		this.voucherId = voucherId;
		this.value = value;
	}
}
