package com.nxt.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nxt.demo.model.Drink;

@SpringBootTest
public class DrinkRepositoryTest {
	@Autowired
	private DrinkRepository drinkRepository;

	@Test
	public void testDrinkList() {
		assertEquals(29, drinkRepository.getAllDrinks().size());
	}

	@Test
	public void testPriceList() {
		assertEquals(64, drinkRepository.getAllPrices().size());
	}

	@Test
	public void testFindDrinkByShortName() {
		List<Drink> list = drinkRepository.findDrinkByShortName("cb-ccb");

		assertNotNull(list);
		assertEquals(1, list.size());
		Drink drink = list.get(0);
		assertEquals("cb-ccb", drink.getShortName());
		assertEquals("classic cold brew", drink.getName());
	}
}
