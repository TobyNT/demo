package com.nxt.demo.dto;

import java.time.LocalDate;

public class ExpirationVoucherRuleChecker implements VoucherRuleChecker {
	public static final LocalDate MAX_VALID_DATE = LocalDate.MAX;
	public static final String NAME = "Expiration";

	private LocalDate validFrom;
	private LocalDate validTo;

	public ExpirationVoucherRuleChecker(LocalDate validFrom, LocalDate validTo) {
		this.validFrom = validateValidFrom(validFrom);
		this.validTo = validateValidTo(validTo);
	}

	private LocalDate validateValidFrom(LocalDate validFrom) {
		if (validFrom == null) {
			return getToday();
		}
		return validFrom;
	}

	private LocalDate validateValidTo(LocalDate validTo) {
		if (validTo == null) {
			return MAX_VALID_DATE;
		}

		if (validFrom.isAfter(validTo)) {
			throw new IllegalArgumentException("invalid voucher expire date");
		}

		return validTo;
	}

	@Override
	public boolean isApplicable(Invoice invoice) {
		LocalDate now = getToday();
		return validFrom.compareTo(now) <= 0 && now.compareTo(validTo) <= 0;
	}

	protected LocalDate getToday() {
		return LocalDate.now();
	}

}
