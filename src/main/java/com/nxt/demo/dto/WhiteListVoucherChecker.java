package com.nxt.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.nxt.demo.model.Drink;

public class WhiteListVoucherChecker implements VoucherChecker {

	private List<String> whiteList = new ArrayList<>();

	public WhiteListVoucherChecker(List<String> types) {
		whiteList.addAll(types);
	}

	@Override
	public List<Drink> filter(List<Drink> drinks) {
		return drinks.stream().filter(drink -> whiteList.contains(drink.getType())).collect(Collectors.toList());
	}

}
