package com.nxt.demo.model;

public class DrinkPrice {
	private Drink drink;
	private Size size;
	private double price;

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public DrinkPrice(Drink drink, Size size, double price) {
		this.drink = drink;
		this.size = size;
		this.price = price;
	}

	public DrinkPrice(Drink drink, Size size) {
		this(drink, size, 0D);
	}
}
