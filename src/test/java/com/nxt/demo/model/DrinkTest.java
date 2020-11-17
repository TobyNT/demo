package com.nxt.demo.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DrinkTest {
	@Test
	public void testParse() {
		Drink drink = Drink.parse("vc-vietnamese_coffee");

		assertEquals("vc-vc", drink.getShortName());
		assertEquals("vietnamese coffee", drink.getName());
	}

	@Test
	public void testParseExceptionWithInvalidDrinkName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Drink.parse("vc-"));

		assertEquals("invalid format", exception.getMessage());
	}

	@Test
	public void testParseExceptionWithInvalidTypeName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Drink.parse("-vietnamese_coffee"));

		assertEquals("blank", exception.getMessage());
	}

	@Test
	public void testToString() {
		Drink drink = Drink.parse("ch-chocolate_ice_blended");
		assertEquals("Drink{ShortName=ch-cib,Name=chocolate ice blended}", drink.toString());
	}
}
