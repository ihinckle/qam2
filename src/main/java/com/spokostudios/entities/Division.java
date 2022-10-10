package com.spokostudios.entities;

import javafx.collections.ObservableList;

/**
 * A division object
 */
public class Division {
	private int id;
	private String name;
	private int countryId;

	public Division(int id, String name, int countryId){
		this.id = id;
		this.name = name;
		this.countryId = countryId;
	}

	/**
	 * Gets the division ID
	 * @return The division ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the division name
	 * @return The division name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the country ID that the division belongs to
	 * @return The ID of the country the division resides in
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * Gets a divsion from the desired name
	 *
	 * Uses a lambda to filter by a single division name
	 *
	 * @param divisions
	 * @param name
	 * @return A division
	 */
	public static Division filterDivisionByName(ObservableList<Division> divisions, String name){
		return divisions.filtered(division -> division.getName().equals(name)).get(0);
	}

	/**
	 * Returns the division name
	 * @return The division name
	 */
	@Override
	public String toString(){
		return name;
	}
}
