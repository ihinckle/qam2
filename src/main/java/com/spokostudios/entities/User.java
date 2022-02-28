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
	 * @return The user ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The userame
	 */
	public String toString(){
		return username;
	}
}
