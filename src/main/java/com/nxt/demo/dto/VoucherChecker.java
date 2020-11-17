package com.nxt.demo.dto;

import java.util.List;

import com.nxt.demo.model.Drink;

public interface VoucherChecker {
	public List<Drink> filter(List<Drink> drinks);
}
