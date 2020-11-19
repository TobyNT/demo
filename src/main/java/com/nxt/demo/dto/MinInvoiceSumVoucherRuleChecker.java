package com.nxt.demo.dto;

public class MinInvoiceSumVoucherRuleChecker implements VoucherRuleChecker {
	public static final String NAME = "MinInvoiceSum";
	private double minimumValue;

	public MinInvoiceSumVoucherRuleChecker(double value) {
		this.minimumValue = validateMinValue(value);
	}

	private double validateMinValue(double value) {
		if (value < 0) {
			throw new IllegalArgumentException("invalid min invoice value");
		}

		return value;
	}

	@Override
	public boolean isApplicable(Invoice invoice) {
		return minimumValue <= invoice.getSum();
	}

}
