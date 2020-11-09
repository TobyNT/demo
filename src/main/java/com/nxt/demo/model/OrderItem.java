package com.nxt.demo.model;

public class OrderItem {
	private Drink drink;
	private Size size;
	private int amount;

	public Drink getDrink() {
		return drink;
	}

	public void setDrink(Drink drink) {
		this.drink = drink;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public OrderItem(Drink drink, Size size, int amount) {
		this.drink = drink;
		this.size = size;
		this.amount = amount;
	}
}
