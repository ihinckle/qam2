package com.spokostudios.entities;

/**
 * A customer object
 */
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

	/**
	 * @return The customer ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The customer name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The customer address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return The customer postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @return The customer phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return The local area the customer lives in
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @return The country the customer lives in
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return The customer's ID and the customer's name
	 */
	public String toString(){
		return id + ": " + name;
	}
}
