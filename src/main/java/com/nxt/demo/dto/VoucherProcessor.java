package com.nxt.demo.dto;

import java.util.List;

public abstract class VoucherProcessor {

	/**
	 * Includes: [vc,ft] Excludes: ![vc,ft]
	 */
	protected VoucherChecker checker;

	protected List<VoucherRuleChecker> ruleCheckers;

	public VoucherProcessor(VoucherChecker checker, List<VoucherRuleChecker> ruleCheckers) {
		this.checker = checker;
		this.ruleCheckers = ruleCheckers;
	}

	protected boolean checkAgainstRules(Invoice invoice) {
		for (VoucherRuleChecker checker : ruleCheckers) {
			if (!checker.isApplicable(invoice)) {
				return false;
			}
		}
		return true;
	}

	public abstract double calculateDiscount(Invoice invoice);
}
