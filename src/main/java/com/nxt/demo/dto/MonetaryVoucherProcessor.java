package com.nxt.demo.dto;

import java.util.List;

import com.nxt.demo.model.Voucher;

public class MonetaryVoucherProcessor extends VoucherProcessor {

	private Double monetaryValue;

	public MonetaryVoucherProcessor(VoucherChecker checker, List<VoucherRuleChecker> ruleCheckers, Voucher voucher) {
		super(checker, ruleCheckers);
		this.monetaryValue = voucher.getValue();
	}

	@Override
	public double calculateDiscount(Invoice invoice) {
		if (checkAgainstRules(invoice)) {
			return monetaryValue;
		}

		return 0;

	}

}
