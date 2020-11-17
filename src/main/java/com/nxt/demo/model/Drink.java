package com.nxt.demo.model;

import java.util.Arrays;

import org.apache.logging.log4j.util.Strings;

public class Drink {
	private String type;
	private String shortName;
	private String name;

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public String getShortName() {
		return this.shortName;
	}

	private Drink(String name, String shortName, String type) {
		this.name = validateName(name);
		this.shortName = shortName;
		this.type = type;
	}

	private static String validateName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("null");
		}

		if (Strings.isBlank(name)) {
			throw new IllegalArgumentException("blank");
		}

		return name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ShortName=").append(this.shortName).append(",");
		builder.append("Name=").append(this.name);
		return String.format("Drink{%s}", builder.toString());
	}

	/**
	 * Format {type}-{name separated by _}
	 * 
	 * @param s
	 * @return
	 */
	public static Drink parse(String s) {
		String[] parts = s.split("-");

		if (parts.length != 2) {
			throw new IllegalArgumentException("invalid format");
		}

		String type = validateName(parts[0]);
		String name = validateName(parts[1]);

		String[] words = name.split("_");
		String shortName = type + "-" + Arrays.stream(words).filter(word -> word.length() > 0).reduce("",
				(result, word) -> result + word.charAt(0));

		return new Drink(name.replaceAll("_", " "), shortName, type);
	}

}
