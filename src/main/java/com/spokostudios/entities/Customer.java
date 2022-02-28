package com.spokostudios.entities;

public class Customer {
	private int id;
	private String name;
	private String address;
	private String postalCode;
	private String phone;
	private String division;
	private String country;

	public Customer(int id,
					String name,
					String address,
					String postalCode,
					String phone,
					String division,
					String country){
		this(name,address,postalCode,phone,division);
		this.id = id;
		this.country = country;
	}

	public Customer(String name,
					String address,
					String postalCode,
					String phone,
					String division){
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.phone = phone;
		this.division = division;
	}

	public Customer(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public String getDivision() {
		return division;
	}

	public String getCountry() {
		return country;
	}

	public String toString(){
		return id + ": " + name;
	}
}
