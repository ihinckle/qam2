package com.spokostudios.views;

import com.spokostudios.entities.Country;
import com.spokostudios.entities.Customer;
import com.spokostudios.entities.Division;
import com.spokostudios.services.DBService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CustomersController {
	private DBService dbs;

	@FXML private TableView customersTable;
	@FXML private ChoiceBox<Division> divisionDropdown;
	@FXML private ChoiceBox<Country> countryDropdown;
	@FXML private TextField nameField;
	@FXML private TextField addressField;
	@FXML private TextField postalField;
	@FXML private TextField phoneField;
	@FXML private Button addButton;
	@FXML private Button updateButton;
	@FXML private Button deleteButton;
	@FXML private Button clearButton;

	private ObservableList<Division> divisions;
	private Customer selectedCustomer;

	@FXML
	private void initialize() throws SQLException {
		dbs = DBService.getInstance();

		ObservableList<Country> countries = dbs.getCountries();
		divisions= dbs.getDivisions();

		countryDropdown.setItems(countries);
		countryDropdown.setOnAction((ActionEvent event) ->{
			ChoiceBox source = (ChoiceBox) event.getSource();

			Country sourceValue = (Country) source.getValue();

			if(sourceValue == null){
				return;
			}

			int countryId = sourceValue.getId();

			divisionDropdown.setItems(divisions.filtered(division -> division.getCountryId() == countryId));
		});

		customersTable.setRowFactory(customersTable -> {
			TableRow<Customer> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				TableRow<Customer> source = (TableRow<Customer>) event.getSource();
				selectedCustomer = source.getItem();

				nameField.setText(selectedCustomer.getName());
				addressField.setText(selectedCustomer.getAddress());
				postalField.setText(selectedCustomer.getPostalCode());
				phoneField.setText(selectedCustomer.getPhone());
				countryDropdown.setValue(Country.filterCountryByName(countries, selectedCustomer.getCountry()));
				divisionDropdown.setValue(Division.filterDivisionByName(divisions, selectedCustomer.getDivision()));

				addButton.setVisible(false);
				updateButton.setVisible(true);
				deleteButton.setVisible(true);
				clearButton.setVisible(true);
			});

			return row;
		});

		populateTable();
	}

	@FXML
	private void addCustomerBtnClick() throws SQLException {
		Division division = divisionDropdown.getValue();

		Customer customer = new Customer(nameField.getText(),
										 addressField.getText(),
										 postalField.getText(),
										 phoneField.getText(),
										 division.getName());

		dbs.addCustomer(customer);

		reset();
	}

	@FXML
	private void updateCustomerBtnClick() throws SQLException {
		Customer customer = new Customer(selectedCustomer.getId(),
										 nameField.getText(),
										 addressField.getText(),
										 postalField.getText(),
										 phoneField.getText(),
										 divisionDropdown.getValue().getName(),
										 countryDropdown.getValue().getName());

		dbs.updateCustomer(customer);

		reset();
	}

	@FXML
	private void deleteCustomerBtnClick() throws SQLException {
		dbs.deleteCustomer(selectedCustomer.getId());

		reset();
	}

	@FXML
	private void clearBtnClick() throws SQLException {
		addButton.setVisible(true);
		updateButton.setVisible(false);
		deleteButton.setVisible(false);
		clearButton.setVisible(false);

		reset();
	}

	private void populateTable() throws SQLException {
		customersTable.setItems(dbs.getCustomers());
	}

	private void reset() throws SQLException {
		populateTable();

		nameField.setText("");
		addressField.setText("");
		postalField.setText("");
		phoneField.setText("");
		countryDropdown.setValue(null);
		divisionDropdown.setValue(null);
	}
}
