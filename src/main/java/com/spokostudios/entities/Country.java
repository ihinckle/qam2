package com.spokostudios.entities;

import javafx.collections.ObservableList;

/**
 * A country object
 */
public class Country {
	private int id;
	private String name;

	public Country(int id, String name){
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the country ID
	 * @return The country ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the country name
	 * @return The country name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets a country from the desired name
	 * @param countries
	 * @param name
	 * @return A country
	 */
	public static Country filterCountryByName(ObservableList<Country> countries, String name){
		return countries.filtered(country -> country.getName().equals(name)).get(0);
	}

	/**
	 * Returns the country name
	 * @return The country name
	 */
	@Override
	public String toString(){
		return name;
	}
}
