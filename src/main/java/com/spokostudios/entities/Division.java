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
	 * @return The division ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The division name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The ID of the country the division resides in
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * Gets a divsion from the desired name
	 * @param divisions
	 * @param name
	 * @return A division
	 */
	public static Division filterDivisionByName(ObservableList<Division> divisions, String name){
		/**
		 * A very simple lambda to return a desired object
		 */
		return divisions.filtered(division -> division.getName().equals(name)).get(0);
	}

	/**
	 * @return The division name
	 */
	@Override
	public String toString(){
		return name;
	}
}
