package com.nxt.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private List<OrderItem> items = new ArrayList<>();

	public void addItem(OrderItem item) {
		this.items.add(item);
	}

	public OrderItem removeItem(OrderItem item) {
		return this.removeItem(item);
	}

	public List<OrderItem> getList() {
		return items;
	}

	public Order() {
	}

	public Order(List<OrderItem> items) {
		this.items.addAll(items);
	}
}
