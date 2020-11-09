package com.nxt.demo.repository;

import java.util.List;

import com.nxt.demo.model.Drink;
import com.nxt.demo.model.DrinkPrice;

public interface DrinkRepository {
	List<Drink> getAllDrinks();

	List<DrinkPrice> getAllPrices();
}
