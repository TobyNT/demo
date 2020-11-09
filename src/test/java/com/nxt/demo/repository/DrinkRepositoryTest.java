package com.nxt.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DrinkRepositoryTest {
	@Autowired
	private DrinkRepository drinkRepository;

	@Test
	public void testDrinkList() {
		assertEquals(3, drinkRepository.getAllDrinks().size());
	}

	@Test
	public void testPriceList() {
		assertEquals(9, drinkRepository.getAllPrices().size());
	}
}
