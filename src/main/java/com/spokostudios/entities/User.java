package com.spokostudios.entities;

public class User {
	int id;
	String username;

	public User(int id, String username){
		this.id = id;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public String toString(){
		return username;
	}
}
