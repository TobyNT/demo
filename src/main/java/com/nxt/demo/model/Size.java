package com.nxt.demo.model;

public enum Size {
	SMALL, MEDIUM, LARGE;

	public static Size of(String abbreviation) {
		if (abbreviation == null) {
			throw new IllegalArgumentException("Invalid abbreviation");
		}

		switch (abbreviation) {
		case "S":
			return SMALL;
		case "M":
			return MEDIUM;
		case "L":
			return LARGE;
		}

		throw new IllegalArgumentException("Invalid abbreviation");
	}
}
