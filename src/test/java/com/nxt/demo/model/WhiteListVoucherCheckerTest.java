package com.nxt.demo.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.nxt.demo.dto.VoucherChecker;
import com.nxt.demo.dto.VoucherCheckerFactory;
import com.nxt.demo.dto.WhiteListVoucherChecker;

public class WhiteListVoucherCheckerTest {

	@Test
	public void testFilterWhenAllGetMatched() {
		VoucherChecker checker = VoucherCheckerFactory.produce("[vc,mc,cb]");
		assertTrue(checker instanceof WhiteListVoucherChecker);

		String[] array = { "vc-vietnamese_coffee", "vc-vietnamese_coffee_with_condensed_milk", "mc-expresso",
				"mc-latte" };
		List<Drink> drinkList = Arrays.stream(array).map(s -> Drink.parse(s)).collect(Collectors.toList());

		List<Drink> matches = checker.filter(drinkList);
		assertArrayEquals(new String[] { "vc-vc", "vc-vcwcm", "mc-e", "mc-l" },
				matches.stream().map(drink -> drink.getShortName()).toArray(size -> new String[size]));
	}

	@Test
	public void testFilterWhenHavingSomeUnmatchDrinks() {
		VoucherChecker checker = VoucherCheckerFactory.produce("[vc,mc,cb]");
		assertTrue(checker instanceof WhiteListVoucherChecker);

		String[] array = { "vc-vietnamese_coffee", "ch-chocolate", "mc-expresso", "mc-latte" };
		List<Drink> drinkList = Arrays.stream(array).map(s -> Drink.parse(s)).collect(Collectors.toList());

		List<Drink> matches = checker.filter(drinkList);
		assertArrayEquals(new String[] { "vc-vc", "mc-e", "mc-l" },
				matches.stream().map(drink -> drink.getShortName()).toArray(size -> new String[size]));
	}

	@Test
	public void testFilterWhenNoMatch() {
		VoucherChecker checker = VoucherCheckerFactory.produce("[vc,mc,cb]");
		assertTrue(checker instanceof WhiteListVoucherChecker);

		String[] array = { "ch-chocolate" };
		List<Drink> drinkList = Arrays.stream(array).map(s -> Drink.parse(s)).collect(Collectors.toList());

		List<Drink> matches = checker.filter(drinkList);
		assertArrayEquals(new String[0],
				matches.stream().map(drink -> drink.getShortName()).toArray(size -> new String[size]));
	}
}
