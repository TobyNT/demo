package com.nxt.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
	// TODO: Issue #2 Rename 'sum' to 'subtotal'
	private double sum = 0;
	private List<InvoiceItem> invoiceItems = new ArrayList<>();
	private List<DiscountItem> discountItems = new ArrayList<>();
	private double discount = 0;

	public double getSum() {
		return sum;
	}

	public double getTotal() {
		return sum - discount;
	}

	public double getDiscount() {
		return discount;
	}

	public List<InvoiceItem> getInvoiceItems() {
		return new ArrayList<>(invoiceItems);
	}

	public void setDiscount(double value) {
		if (value < 0) {
			throw new IllegalArgumentException("invalid discount value");
		}
		discount = value;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = new ArrayList<>(invoiceItems);
		this.sum = 0;

		for (InvoiceItem item : invoiceItems) {
			sum += item.getSubtotal();
		}
	}

	public void setDiscountItems(List<DiscountItem> discountItems) {
		this.discountItems = new ArrayList<>(discountItems);
		this.discount = 0;

		for (DiscountItem item : discountItems) {
			discount += item.getValue();
		}
	}

}
