package com.nxt.demo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxt.demo.dto.MonetaryVoucherProcessor;
import com.nxt.demo.dto.PercentageVoucherProcessor;
import com.nxt.demo.dto.VoucherChecker;
import com.nxt.demo.dto.VoucherCheckerFactory;
import com.nxt.demo.dto.VoucherProcessor;
import com.nxt.demo.dto.VoucherRuleChecker;
import com.nxt.demo.model.Voucher;

@Service
public class VoucherProcessorFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(VoucherProcessorFactory.class);
	public static final String TYPE_PERCENTAGE = "percentage";
	public static final String TYPE_MONETARY = "monetary";

	@Autowired
	private VoucherRuleCheckerFactory voucherRuleCheckerFactory;

	public VoucherProcessor createVoucherProcessor(Voucher voucher) {
		VoucherChecker checker = VoucherCheckerFactory.produce(voucher.getExpression());
		List<VoucherRuleChecker> ruleCheckers = Collections.emptyList();
		try {
			ruleCheckers = voucherRuleCheckerFactory.createRuleCheckers(voucher);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			LOGGER.error("Failed to create Voucher Processor due to failure of instantiating Voucher Rule Checker");
			throw new RuntimeException("VoucherProcessor instantiation failure");
		}

		switch (voucher.getType()) {
		case TYPE_PERCENTAGE:
			return new PercentageVoucherProcessor(checker, ruleCheckers, voucher);
		case TYPE_MONETARY:
			return new MonetaryVoucherProcessor(checker, ruleCheckers, voucher);
		default:
			throw new RuntimeException("Unknown voucher type " + voucher.getType());
		}

	}
}
