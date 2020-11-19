package com.nxt.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxt.demo.dto.DiscountItem;
import com.nxt.demo.dto.Invoice;
import com.nxt.demo.dto.InvoiceItem;
import com.nxt.demo.dto.VoucherProcessor;
import com.nxt.demo.exception.OrderItemFormatException;
import com.nxt.demo.exception.VoucherException;
import com.nxt.demo.exception.VoucherNotFoundException;
import com.nxt.demo.model.DrinkPrice;
import com.nxt.demo.model.Size;
import com.nxt.demo.model.Voucher;
import com.nxt.demo.repository.DrinkRepository;
import com.nxt.demo.repository.VoucherRepository;

@Service
public class BillingService {

	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private VoucherProcessorFactory voucherProcessorFactory;

	private Map<String, VoucherProcessor> voucherProcessorMap = new HashMap<>();

//	@PostConstruct
//	private void postConstruct() {
//		initVoucherProcessors();
//	}
//	
//	void initVoucherProcessors() {
//		List<Voucher> voucher
//	}

	public Invoice bill(List<String> items, List<String> voucherIds) {
		validateOrderItems(items);
		validateVoucherIDs(voucherIds);

		Invoice invoice = new Invoice();
		List<InvoiceItem> invoiceItems = createInvoiceItems(items);
		invoice.setInvoiceItems(invoiceItems);

		List<DiscountItem> discountItems = createDiscountItems(invoice, voucherIds);
		invoice.setDiscountItems(discountItems);

		return invoice;
	}

	private void validateOrderItems(List<String> items) {
		if (items == null) {
			throw new IllegalArgumentException("Order items null");
		}

		for (String item : items) {
			if (StringUtils.isBlank(item)) {
				throw new OrderItemFormatException("illegal item format");
			}

			String[] parts = item.split("\\|");
			if (parts.length != 3) {
				throw new OrderItemFormatException("illegal item format");
			}
		}
	}

	private void validateVoucherIDs(List<String> voucherIDs) {
		if (voucherIDs == null) {
			throw new IllegalArgumentException("Voucher items null");
		}

		boolean isInvalid = voucherIDs.stream().anyMatch(voucherId -> StringUtils.isBlank(voucherId));
		if (isInvalid) {
			throw new VoucherException("invalid voucher id");
		}

	}

	private List<InvoiceItem> createInvoiceItems(List<String> items) {
		List<InvoiceItem> invoiceItems = new ArrayList<>();
		for (String item : items) {
			String[] parts = item.split("\\|");

			String drinkShortName = parts[0];
			Size size = Size.of(parts[1]);
			Integer amount = Integer.valueOf(parts[2]);

			Optional<DrinkPrice> drinkPrice = drinkRepository.getPrice(drinkShortName, size);

			drinkPrice.ifPresent(d -> {
				InvoiceItem invoiceItem = new InvoiceItem(d, amount);
				invoiceItems.add(invoiceItem);
			});
		}

		return invoiceItems;
	}

	private List<DiscountItem> createDiscountItems(Invoice invoice, List<String> voucherIds) {
		List<DiscountItem> discountItems = new ArrayList<>();
		for (String voucherId : voucherIds) {
			VoucherProcessor processor = getOrCreateVoucherProcessor(voucherId);
			double value = processor.calculateDiscount(invoice);
			discountItems.add(new DiscountItem(voucherId, value));
		}
		return discountItems;
	}

	private VoucherProcessor getOrCreateVoucherProcessor(String voucherId) {
		Optional<Voucher> voucherOptional = voucherRepository.findVoucherById(voucherId);
		Voucher voucher = voucherOptional
				.orElseThrow(() -> new VoucherNotFoundException("voucher not found: " + voucherId));

		VoucherProcessor processor = voucherProcessorMap.get(voucherId);
		if (processor == null) {
			processor = voucherProcessorFactory.createVoucherProcessor(voucher);
			voucherProcessorMap.put(voucherId, processor);
		}

		return processor;
	}
}
