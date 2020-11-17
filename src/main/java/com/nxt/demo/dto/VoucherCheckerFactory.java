package com.nxt.demo.dto;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class VoucherCheckerFactory {
	public static VoucherChecker produce(String expression) {
		if (StringUtils.isBlank(expression)) {
			throw new IllegalArgumentException("invalid expression");
		}

		String nospaceExpression = expression.replaceAll("\\s", "");
		int mark = 0;
		if (nospaceExpression.startsWith("!")) {
			mark = -1;
		} else if (nospaceExpression.startsWith("[")) {
			mark = 1;
		}

		if (!nospaceExpression.endsWith("]")) {
			throw new IllegalArgumentException("invalid expression");
		}

		if (mark == 0) {
			throw new IllegalArgumentException("invalid expression");
		}

		if (mark < 0) {
			String stringValue = nospaceExpression.substring(2, nospaceExpression.length() - 1);
			if (StringUtils.isEmpty(stringValue)) {
				throw new IllegalArgumentException("invalid expression");
			}
			String[] values = stringValue.split(",");
			if (ArrayUtils.isEmpty(values)) {
				throw new IllegalArgumentException("invalid expression");
			}
			return new BlackListVoucherChecker(Arrays.asList(values));
		} else {
			String stringValue = nospaceExpression.substring(1, nospaceExpression.length() - 1);
			if (StringUtils.isEmpty(stringValue)) {
				throw new IllegalArgumentException("invalid expression");
			}
			String[] values = stringValue.split(",");
			if (ArrayUtils.isEmpty(values)) {
				throw new IllegalArgumentException("invalid expression");
			}
			return new WhiteListVoucherChecker(Arrays.asList(values));
		}

	}

}
