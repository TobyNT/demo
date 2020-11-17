package com.nxt.demo.repository;

import java.util.List;
import java.util.Optional;

import com.nxt.demo.model.Drink;
import com.nxt.demo.model.DrinkPrice;
import com.nxt.demo.model.Size;

public interface DrinkRepository {
	List<Drink> getAllDrinks();

	List<DrinkPrice> getAllPrices();

	List<Drink> findDrinkByShortName(String shortName);

	Optional<Drink> getDrinkByShortName(String shortName);

	Optional<DrinkPrice> getPrice(String shortName, Size size);
}
