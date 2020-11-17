package com.nxt.demo.dto;

public interface VoucherRuleChecker {
	boolean isApplicable(Invoice invoice);
}
