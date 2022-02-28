package com.spokostudios.entities;

import javafx.collections.ObservableList;

public class Country {
	private int id;
	private String name;

	public Country(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Country filterCountryByName(ObservableList<Country> countries, String name){
		return countries.filtered(country -> country.getName().equals(name)).get(0);
	}

	@Override
	public String toString(){
		return name;
	}
}
