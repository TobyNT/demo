package com.nxt.demo.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.nxt.demo.model.Drink;
import com.nxt.demo.model.Voucher;

public class PercentageVoucherProcessor extends VoucherProcessor {

	private Double percentageValue;

	public PercentageVoucherProcessor(VoucherChecker checker, List<VoucherRuleChecker> ruleCheckers, Voucher voucher) {
		super(checker, ruleCheckers);
		this.percentageValue = voucher.getValue();
	}

	@Override
	public double calculateDiscount(Invoice invoice) {
		List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
		List<Drink> drinks = invoiceItems.stream().map(item -> item.getDrinkPrice().getDrink())
				.collect(Collectors.toList());

		List<Drink> eligibleDrinksForDiscount = checker.filter(drinks);
		if (checkAgainstRules(invoice)) {
			List<InvoiceItem> eligibleItems = invoiceItems.stream()
					.filter(item -> eligibleDrinksForDiscount.contains(item.getDrinkPrice().getDrink()))
					.collect(Collectors.toList());
			double subtotal = eligibleItems.stream().mapToDouble(item -> item.getSubtotal()).sum();
			return percentageValue * subtotal / 100;
		}

		return 0;
	}

}
