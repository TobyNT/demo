package com.nxt.demo.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpirationVoucherRuleCheckerTest {
	@Test
	public void testIsApplicableWhenTodayIsFirstValidDate() {
		LocalDate validFrom = LocalDate.of(2020, 8, 12);
		LocalDate validTo = LocalDate.of(2020, 11, 12);
		LocalDate now = LocalDate.of(2020, 8, 12);

		ExpirationVoucherRuleChecker checker = new ExpirationVoucherRuleChecker(validFrom, validTo) {
			protected LocalDate getToday() {
				return now;
			};
		};

		assertTrue(checker.isApplicable(null));
	}

	@Test
	public void testIsApplicableWhenTodayIsLastValidDate() {

		LocalDate validFrom = LocalDate.of(2020, 8, 12);
		LocalDate validTo = LocalDate.of(2020, 11, 12);
		LocalDate now = LocalDate.of(2020, 11, 12);

		ExpirationVoucherRuleChecker checker = new ExpirationVoucherRuleChecker(validFrom, validTo) {
			protected LocalDate getToday() {
				return now;
			};
		};

		assertTrue(checker.isApplicable(null));

	}

	@Test
	public void testIsApplicable() {
		LocalDate validFrom = LocalDate.of(2020, 8, 12);
		LocalDate validTo = LocalDate.of(2020, 11, 12);
		LocalDate now = LocalDate.of(2020, 9, 1);

		ExpirationVoucherRuleChecker checker = new ExpirationVoucherRuleChecker(validFrom, validTo) {
			protected LocalDate getToday() {
				return now;
			};
		};

		assertTrue(checker.isApplicable(null));
	}

	@Test
	public void testIsApplicableWhenExpiry() {
		LocalDate validFrom = LocalDate.of(2020, 8, 12);
		LocalDate validTo = LocalDate.of(2020, 11, 12);
		LocalDate now = LocalDate.of(2020, 12, 1);

		ExpirationVoucherRuleChecker checker = new ExpirationVoucherRuleChecker(validFrom, validTo) {
			protected LocalDate getToday() {
				return now;
			};
		};

		assertFalse(checker.isApplicable(null));
	}
}
