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
	 * Gets the contact ID
	 * @return The contact ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the contact name
	 * @return The contact name
	 */
	public String getName(){
		return name;
	}

	/**
	 * Returns the contact name
	 * @return The contact name
	 */
	public String toString(){
		return name;
	}
}
