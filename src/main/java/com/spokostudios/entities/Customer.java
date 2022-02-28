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
	 * Gets the customer ID
	 * @return The customer ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the customer name
	 * @return The customer name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the customer address
	 * @return The customer address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Gets the customer postal code
	 * @return The customer postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Gets the customer phone number
	 * @return The customer phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Gets the local area that the customer lives in
	 * @return The local area the customer lives in
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * Gets the country that the customer lives in
	 * @return The country the customer lives in
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Returns the customers ID and name
	 * @return The customer's ID and the customer's name
	 */
	public String toString(){
		return id + ": " + name;
	}
}
