package com.spokostudios.entities;

/**
 * A user object
 */
public class User {
	int id;
	String username;

	public User(int id, String username){
		this.id = id;
		this.username = username;
	}

	/**
	 * Gets the user ID
	 * @return The user ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the username
	 * @return The userame
	 */
	public String toString(){
		return username;
	}
}
