package com.nxt.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.nxt.demo.model.Drink;

public class BlackListVoucherChecker implements VoucherChecker {
	private List<String> blackList = new ArrayList<>();

	public BlackListVoucherChecker(List<String> types) {
		blackList.addAll(types);
	}

	@Override
	public List<Drink> filter(List<Drink> drinks) {
		return drinks.stream().filter(drink -> !blackList.contains(drink.getType())).collect(Collectors.toList());
	}
}
