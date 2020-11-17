package com.nxt.demo.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nxt.demo.dto.ExpirationVoucherRuleChecker;
import com.nxt.demo.dto.MinInvoiceSumVoucherRuleChecker;
import com.nxt.demo.dto.VoucherRule;
import com.nxt.demo.dto.VoucherRuleChecker;
import com.nxt.demo.exception.VoucherRuleCheckerClassNotFound;
import com.nxt.demo.model.Voucher;

@Service
public class VoucherRuleCheckerFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoucherRuleCheckerFactory.class);

	private Map<String, Class<? extends VoucherRuleChecker>> klassMap = new HashMap<>();

	{
		klassMap.put(ExpirationVoucherRuleChecker.NAME, ExpirationVoucherRuleChecker.class);
		klassMap.put(MinInvoiceSumVoucherRuleChecker.NAME, MinInvoiceSumVoucherRuleChecker.class);
	}

	public Class getVoucherRuleCheckerClassByRuleName(String ruleName) {
		return klassMap.get(ruleName);
	}

	public List<VoucherRuleChecker> createRuleCheckers(Voucher voucher)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<VoucherRuleChecker> list = new ArrayList<>();
		for (VoucherRule rule : voucher.getRules()) {

			Class<?> checkerKlass = getVoucherRuleCheckerClassByRuleName(rule.getName());
			if (checkerKlass == null) {
				throw new VoucherRuleCheckerClassNotFound();
			}
			Constructor<?>[] constructors = checkerKlass.getConstructors();
			Constructor<?> constructor = constructors[0];
			Object[] args = getParamObjects(rule.getParams(), constructor.getParameters());

			VoucherRuleChecker checker = (VoucherRuleChecker) constructor.newInstance(args);
			list.add(checker);

		}
		return list;
	}

	private Object[] getParamObjects(Map<String, Object> valueMap, Parameter[] parameters) {
		Object[] objs = new Object[parameters.length];
		for (int i = 0; i < parameters.length; ++i) {
			Parameter param = parameters[i];
			Object value = valueMap.get(param.getName());
			if (value != null) {
				Class paramKlass = param.getType();
				if (paramKlass.equals(value.getClass())) {
					objs[i] = value;
					continue;
				} else {
					objs[i] = convertObjectToParamType(paramKlass, value);
				}
			}
		}
		return objs;
	}

	private Object convertObjectToParamType(Class paramKlass, Object value) {
		if (LocalDate.class.equals(paramKlass)) {
			return LocalDate.parse(value.toString());
		}
		return value;
	}

}
