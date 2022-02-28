package com.spokostudios.entities;

/**
 * A contact object
 */
public class Contact {
	private int id;
	private String name;
	private String email;

	public Contact(int id, String name, String email){
		this.id = id;
		this.name = name;
		this.email = email;
	}

	/**
	 * @return The contact ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The contact name
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The contact name
	 */
	public String toString(){
		return name;
	}
}
