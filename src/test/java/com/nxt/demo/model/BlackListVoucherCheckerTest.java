package com.nxt.demo.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.nxt.demo.dto.BlackListVoucherChecker;
import com.nxt.demo.dto.VoucherChecker;
import com.nxt.demo.dto.VoucherCheckerFactory;

public class BlackListVoucherCheckerTest {
	@Test
	public void testIsValidWhenAllGetMatched() {
		VoucherChecker checker = VoucherCheckerFactory.produce("![vc,mc,cb]");
		assertTrue(checker instanceof BlackListVoucherChecker);

		String[] array = { "mt-matcha_ice_blended", "ch-chocolate" };
		List<Drink> drinkList = Arrays.stream(array).map(s -> Drink.parse(s)).collect(Collectors.toList());

		List<Drink> matches = checker.filter(drinkList);
		assertArrayEquals(new String[] { "mt-mib", "ch-c" },
				matches.stream().map(drink -> drink.getShortName()).toArray(size -> new String[size]));
	}

	@Test
	public void testIsValidWhenHavingSomeUnmatchDrinks() {
		VoucherChecker checker = VoucherCheckerFactory.produce("![vc,mc,cb]");
		assertTrue(checker instanceof BlackListVoucherChecker);

		String[] array = { "mt-matcha_ice_blended", "ch-chocolate", "vc-vietnamese_coffee" };
		List<Drink> drinkList = Arrays.stream(array).map(s -> Drink.parse(s)).collect(Collectors.toList());

		List<Drink> matches = checker.filter(drinkList);
		assertArrayEquals(new String[] { "mt-mib", "ch-c" },
				matches.stream().map(drink -> drink.getShortName()).toArray(size -> new String[size]));
	}

	@Test
	public void testFilterWhenNoMatch() {
		VoucherChecker checker = VoucherCheckerFactory.produce("![vc,mc,cb]");
		assertTrue(checker instanceof BlackListVoucherChecker);

		String[] array = { "vc-vietnamese_coffee" };
		List<Drink> drinkList = Arrays.stream(array).map(s -> Drink.parse(s)).collect(Collectors.toList());

		List<Drink> matches = checker.filter(drinkList);
		assertArrayEquals(new String[0],
				matches.stream().map(drink -> drink.getShortName()).toArray(size -> new String[size]));
	}
}
