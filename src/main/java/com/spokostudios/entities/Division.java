package com.spokostudios.entities;

import javafx.collections.ObservableList;

public class Division {
	private int id;
	private String name;
	private int countryId;

	public Division(int id, String name, int countryId){
		this.id = id;
		this.name = name;
		this.countryId = countryId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCountryId() {
		return countryId;
	}

	public static Division filterDivisionByName(ObservableList<Division> divisions, String name){
		return divisions.filtered(division -> division.getName().equals(name)).get(0);
	}

	@Override
	public String toString(){
		return name;
	}
}
