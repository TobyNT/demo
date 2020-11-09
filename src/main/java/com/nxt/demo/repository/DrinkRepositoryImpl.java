package com.nxt.demo.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nxt.demo.model.Drink;
import com.nxt.demo.model.DrinkPrice;
import com.nxt.demo.model.Size;

@Service
public class DrinkRepositoryImpl implements DrinkRepository {
	private static final String RESOURCE_FILE = "static/drinkprice.properties";
	private List<Drink> drinkList = new ArrayList<>();
	private List<DrinkPrice> priceList = new ArrayList<>();

//	{
//		Drink drink;
//		drink = new Drink("vietnamese coffee");
//		drinkList.add(drink);
//		priceList.add(new DrinkPrice(drink, Size.SMALL, 29000D));
//
//		drinkList.add(new Drink("vietnamese coffee with condensed milk"));
//		drinkList.add(new Drink("vietnamese white coffee"));
//
//		drinkList.add(new Drink("americano"));
//		drinkList.add(new Drink("cappuccino"));
//		drinkList.add(new Drink("caramel macchiato"));
//		drinkList.add(new Drink("espresso"));
//		drinkList.add(new Drink("latte"));
//		drinkList.add(new Drink("mocha"));
//
//		drinkList.add(new Drink("classic coldbrew"));
//		drinkList.add(new Drink("coldbrew macchiato"));
//		drinkList.add(new Drink("coldbrew with raspberry"));
//
//		drinkList.add(new Drink("peach tea mania"));
//		drinkList.add(new Drink("oolong tea with lychee"));
//
//		drinkList.add(new Drink("jasmine tea macchiato"));
//		drinkList.add(new Drink("black tea macchiato"));
//	}

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
				Drink drink = new Drink(name);

				drinkList.add(drink);

				for (int i = 0; i < values.length; ++i) {
					DrinkPrice drinkPrice = new DrinkPrice(drink, providedSizes.get(i), providedPrices.get(i));
					priceList.add(drinkPrice);
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
		return new ArrayList<Drink>(drinkList);
	}

	@Override
	public List<DrinkPrice> getAllPrices() {
		return new ArrayList<DrinkPrice>(priceList);
	}

}
