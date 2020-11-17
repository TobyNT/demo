package com.nxt.demo.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nxt.demo.model.Drink;
import com.nxt.demo.model.DrinkPrice;
import com.nxt.demo.model.Size;

@Service
public class DrinkRepositoryImpl implements DrinkRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(DrinkRepositoryImpl.class);

	private static final String RESOURCE_FILE = "static/drinkprice.properties";

	private Map<String, Drink> drinkMap = new HashMap<>();
	private Map<String, List<DrinkPrice>> priceMap = new HashMap<>();

	public DrinkRepositoryImpl() throws IOException {
		Properties props = loadFromFile();
		init(props);
	}

	private Properties loadFromFile() throws IOException {
		Properties props = new Properties();
		InputStream inStream = getClass().getClassLoader().getResourceAsStream(RESOURCE_FILE);
		if (inStream != null) {
			props.load(inStream);
		} else {
			throw new FileNotFoundException(String.format("Resource file '%s' not found", RESOURCE_FILE));
		}
		return props;
	}

	private void init(Properties properties) {
		Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement();
			String value = properties.getProperty(key);

			String[] names = key.split(",");
			String[] values = value.split(",");

			List<Size> providedSizes = Arrays.stream(values).map(DrinkRepositoryImpl::extractSize)
					.collect(Collectors.toList());
			List<Double> providedPrices = Arrays.stream(values).map(DrinkRepositoryImpl::extractPrice)
					.collect(Collectors.toList());

			for (String name : names) {
				Drink drink = Drink.parse(name);

				Drink oldValue = drinkMap.put(drink.getShortName(), drink);
				if (oldValue != null) {
					LOGGER.warn("Drink short name duplicate found: %s", drink.getShortName());
				}

				List<DrinkPrice> prices = priceMap.get(drink.getShortName());
				if (prices == null) {
					prices = new ArrayList<>();
					priceMap.put(drink.getShortName(), prices);
				}

				for (int i = 0; i < values.length; ++i) {
					DrinkPrice drinkPrice = new DrinkPrice(drink, providedSizes.get(i), providedPrices.get(i));
					prices.add(drinkPrice);
				}
			}
		}
	}

	private static Size extractSize(String value) {
		return Size.of(value.substring(0, 1));
	}

	private static Double extractPrice(String value) {
		String numAsString = value.substring(1, value.length());
		return Double.valueOf(numAsString);
	}

	@Override
	public List<Drink> getAllDrinks() {
		return new ArrayList<Drink>(drinkMap.values());
	}

	@Override
	public List<DrinkPrice> getAllPrices() {
		return priceMap.values().stream().flatMap(l -> l.stream()).collect(Collectors.toList());
	}

	@Override
	public List<Drink> findDrinkByShortName(String shortName) {
		Drink drink = drinkMap.get(shortName);

		if (drink == null) {
			return Collections.emptyList();
		}

		List<Drink> list = new ArrayList<>();
		list.add(drink);

		return list;
	}

	@Override
	public Optional<Drink> getDrinkByShortName(String shortName) {
		Drink drink = drinkMap.get(shortName);

		return Optional.ofNullable(drink);
	}

	public Optional<DrinkPrice> getPrice(String shortName, Size size) {
		List<DrinkPrice> prices = priceMap.get(shortName);

		return prices.stream().filter(price -> price.getSize() == size).findFirst();
	}

}
