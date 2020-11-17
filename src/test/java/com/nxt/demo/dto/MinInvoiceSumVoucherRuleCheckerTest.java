package com.nxt.demo.dto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MinInvoiceSumVoucherRuleCheckerTest {
	@Test
	public void testIsApplicable() {
		Invoice invoice = mock(Invoice.class);
		when(invoice.getSum()).thenReturn(55000D);

		MinInvoiceSumVoucherRuleChecker checker = new MinInvoiceSumVoucherRuleChecker(49000);
		assertTrue(checker.isApplicable(invoice));
	}

	@Test
	public void testIsApplicableWhenTheInvoiceSumLessThanLimitValue() {
		Invoice invoice = mock(Invoice.class);
		when(invoice.getSum()).thenReturn(35000D);

		MinInvoiceSumVoucherRuleChecker checker = new MinInvoiceSumVoucherRuleChecker(49000);
		assertFalse(checker.isApplicable(invoice));
	}
}
