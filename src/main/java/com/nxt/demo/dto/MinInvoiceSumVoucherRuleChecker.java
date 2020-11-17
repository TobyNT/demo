package com.nxt.demo.dto;

public class MinInvoiceSumVoucherRuleChecker implements VoucherRuleChecker {
	public static final String NAME = "MinInvoiceSum";
	private double minimumValue;

	public MinInvoiceSumVoucherRuleChecker(double value) {
		this.minimumValue = value;
	}

	@Override
	public boolean isApplicable(Invoice invoice) {
		return minimumValue <= invoice.getSum();
	}

}
