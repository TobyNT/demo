package com.nxt.demo.dto;

import com.nxt.demo.model.DrinkPrice;

public class InvoiceItem {
	private DrinkPrice drinkPrice;
	private int amount;
	private Double subtotal;

	public DrinkPrice getDrinkPrice() {
		return drinkPrice;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public Double getPrice() {
		return drinkPrice.getPrice();
	}

	public int getAmount() {
		return amount;
	}

	public InvoiceItem(DrinkPrice drinkPrice, int amount) {
		this.drinkPrice = drinkPrice;
		this.amount = amount;
		this.subtotal = drinkPrice.getPrice() * amount;
	}
}
