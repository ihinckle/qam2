package com.spokostudios.services;

import com.spokostudios.entities.Country;
import com.spokostudios.entities.Customer;
import com.spokostudios.entities.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBService {
	private static DBService DBServiceInstance;
	private static Connection connection;

	private static final String LOGINSTATEMENT = "SELECT COUNT(*) FROM users WHERE User_Name = ? AND Password = ?";
	private static final String GETCUSTOMERSSTATEMENT = "SELECT customers.Customer_ID,customers.Customer_Name,customers.Address,customers.Postal_Code,customers.Phone,first_level_divisions.Division,c.Country FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries c on first_level_divisions.COUNTRY_ID = c.Country_ID";
	private static final String ADDCUSTOMERSTATEMENT = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
	private static final String UPDATECUSTOMERSTATEMENT = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
	private static final String DELETECUSTOMERSTATEMENT = "DELETE FROM customers where Customer_ID = ?";
	private static final String COUNTRIESSTATEMENT = "SELECT Country_ID, Country FROM countries";
	private static final String DIVISIONSSTATEMENT = "SELECT Division_ID, Division, COUNTRY_ID FROM first_level_divisions";
	private static final String DIVISIONIDSTATEMENT = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";

	private DBService() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule", "sqlUser", "Passw0rd!");
	}

	public static DBService getInstance() throws SQLException {
		if(DBServiceInstance == null){
			DBServiceInstance = new DBService();
		}

		return DBServiceInstance;
	}

	public boolean login(String user, String password) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(LOGINSTATEMENT);
		statement.setString(1, user);
		statement.setString(2, password);
		ResultSet rs = statement.executeQuery();

		if(rs.next()){
			return true;
		}

		return false;
	}

	public ObservableList<Customer> getCustomers() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GETCUSTOMERSSTATEMENT);
		ResultSet rs = statement.executeQuery();

		ObservableList<Customer> customers = FXCollections.observableArrayList();

		while(rs.next()){
			Customer customer = new Customer(rs.getInt("customers.Customer_ID"),
											 rs.getString("customers.Customer_Name"),
											 rs.getString("customers.Address"),
											 rs.getString("customers.Postal_Code"),
											 rs.getString("customers.Phone"),
											 rs.getString("first_level_divisions.Division"),
											 rs.getString("c.Country"));
			customers.add(customer);
		}

		return customers;
	}

	public void addCustomer(Customer customer) throws SQLException {
		int divisionId;

		PreparedStatement divistionIdStatement = connection.prepareStatement(DIVISIONIDSTATEMENT);
		divistionIdStatement.setString(1, customer.getDivision());
		ResultSet dsrs = divistionIdStatement.executeQuery();
		dsrs.next();
		divisionId = dsrs.getInt("Division_ID");

		PreparedStatement statement = connection.prepareStatement(ADDCUSTOMERSTATEMENT);
		statement.setString(1, customer.getName());
		statement.setString(2, customer.getAddress());
		statement.setString(3, customer.getPostalCode());
		statement.setString(4, customer.getPhone());
		statement.setInt(5, divisionId);

		statement.executeUpdate();
	}

	public void updateCustomer(Customer customer) throws SQLException {
		int divisionId;

		PreparedStatement divistionIdStatement = connection.prepareStatement(DIVISIONIDSTATEMENT);
		divistionIdStatement.setString(1, customer.getDivision());
		ResultSet dsrs = divistionIdStatement.executeQuery();
		dsrs.next();
		divisionId = dsrs.getInt("Division_ID");

		PreparedStatement statement = connection.prepareStatement(UPDATECUSTOMERSTATEMENT);
		statement.setString(1, customer.getName());
		statement.setString(2, customer.getAddress());
		statement.setString(3, customer.getPostalCode());
		statement.setString(4, customer.getPhone());
		statement.setInt(5, divisionId);
		statement.setInt(6, customer.getId());
		statement.executeUpdate();
	}

	public void deleteCustomer(int id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DELETECUSTOMERSTATEMENT);
		statement.setInt(1, id);
		statement.executeUpdate();
	}

	public ObservableList<Country> getCountries() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(COUNTRIESSTATEMENT);
		ResultSet rs = statement.executeQuery();

		ObservableList<Country> countries = FXCollections.observableArrayList();
		while(rs.next()){
			Country country = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
			countries.add(country);
		}

		return countries;
	}

	public ObservableList<Division> getDivisions() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DIVISIONSSTATEMENT);
		ResultSet rs = statement.executeQuery();

		ObservableList<Division> divisions = FXCollections.observableArrayList();

		while(rs.next()){
			Division division = new Division(rs.getInt("Division_ID"),
											 rs.getString("Division"),
											 rs.getInt("COUNTRY_ID"));
			divisions.add(division);
		}

		return divisions;
	}
}
