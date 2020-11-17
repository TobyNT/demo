package com.nxt.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.nxt.demo.dto.ExpirationVoucherRuleChecker;
import com.nxt.demo.dto.Invoice;

@SpringBootTest
public class BillingServiceTest {

	@Autowired
	private BillingService service;

	@SpyBean
	private VoucherRuleCheckerFactory voucherRuleCheckerFactory;

	@Test
	public void testBill() {
		Invoice invoice = service.bill(Arrays.asList("vc-vwc|M|2", "ma-mm|L|1"), Collections.emptyList());
		assertEquals(70000 + 59000, invoice.getTotal());
	}

	public static class FakeExpirationVoucherRuleChecker extends ExpirationVoucherRuleChecker {
		public FakeExpirationVoucherRuleChecker(LocalDate validFrom, LocalDate validTo) {
			super(validFrom, validTo);
		}

		@Override
		protected LocalDate getToday() {
			return LocalDate.of(2020, 11, 06);
		}
	}

	@Test
	public void testBillWith10PercentDiscount() {
		Mockito.when(voucherRuleCheckerFactory.getVoucherRuleCheckerClassByRuleName("Expiration"))
				.thenReturn(FakeExpirationVoucherRuleChecker.class);

		Invoice invoice = service.bill(Arrays.asList("vc-vwc|M|2"), Arrays.asList("p-112020-1"));

		assertEquals(70000 - 7000, invoice.getTotal());
	}

	@Test
	public void testBillWithDiscountOf10000() {
		Invoice invoice = service.bill(Arrays.asList("ma-btm|L|1", "ma-mm|L|2"), Arrays.asList("m-000000-2"));

		double expectedBlackTeaMacchiatoTotal = 55000;
		double expectedMatchaMacchiatoTotal = 59000 * 2;
		double expectedTotal = expectedBlackTeaMacchiatoTotal + expectedMatchaMacchiatoTotal - 10000;

		assertEquals(expectedTotal, invoice.getTotal());

	}
}
