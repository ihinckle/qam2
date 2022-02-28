package com.spokostudios.views;

import com.spokostudios.entities.Country;
import com.spokostudios.entities.Customer;
import com.spokostudios.entities.Division;
import com.spokostudios.services.LocalizationService;
import com.spokostudios.services.dbservice.DBService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class CustomersController {
	private DBService dbs;
	private LocalizationService ls;

	@FXML private TableView customersTable;
	@FXML private ComboBox<Division> divisionDropdown;
	@FXML private ComboBox<Country> countryDropdown;
	@FXML private TextField nameField;
	@FXML private TextField addressField;
	@FXML private TextField postalField;
	@FXML private TextField phoneField;
	@FXML private Button addButton;
	@FXML private Button updateButton;
	@FXML private Button deleteButton;
	@FXML private Button clearButton;
	@FXML private Label idLabel;
	@FXML private Label nameLabel;
	@FXML private Label addressLabel;
	@FXML private Label divisionLabel;
	@FXML private Label countryLabel;
	@FXML private Label postalLabel;
	@FXML private Label phoneLabel;
	@FXML private TableColumn<Customer, String> nameColumn;
	@FXML private TableColumn<Customer, String> addressColumn;
	@FXML private TableColumn<Customer, String> divisionColumn;
	@FXML private TableColumn<Customer, String> countryColumn;
	@FXML private TableColumn<Customer, String> postalColumn;
	@FXML private TableColumn<Customer, String> phoneColumn;

	private ObservableList<Division> divisions;
	private Customer selectedCustomer;
	private DashboardController dashboardController = DashboardController.getInstance();

	@FXML
	private void initialize(){
		try {
			dbs = DBService.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ls =LocalizationService.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		nameColumn.setText(ls.getText("customers.nameColumn"));
		addressColumn.setText(ls.getText("customers.addressColumn"));
		divisionColumn.setText(ls.getText("customers.divisionColumn"));
		countryColumn.setText(ls.getText("customers.countryColumn"));
		postalColumn.setText(ls.getText("customers.postalColumn"));
		phoneColumn.setText(ls.getText("customers.phoneColumn"));
		nameLabel.setText(ls.getText("customers.nameColumn"));
		addressLabel.setText(ls.getText("customers.addressColumn"));
		divisionLabel.setText(ls.getText("customers.divisionColumn"));
		countryLabel.setText(ls.getText("customers.countryColumn"));
		postalLabel.setText(ls.getText("customers.postalColumn"));
		phoneLabel.setText(ls.getText("customers.phoneColumn"));
		addButton.setText(ls.getText("create"));
		updateButton.setText(ls.getText("update"));
		deleteButton.setText(ls.getText("delete"));
		clearButton.setText(ls.getText("clear"));

		ObservableList<Country> countries = null;
		try {
			countries = dbs.getCountries();
		} catch (SQLException e) {
			dashboardController.displayError("customers.failedCountries");
			e.printStackTrace();
		}
		try {
			divisions= dbs.getDivisions();
		} catch (SQLException e) {
			dashboardController.displayError("customers.failedDivisions");
			e.printStackTrace();
		}

		countryDropdown.setItems(countries);
		/**
		 * This lambda is an event handler. I like them.
		 */
		countryDropdown.setOnAction((ActionEvent event) ->{
			ComboBox<Country> source = (ComboBox) event.getSource();
			Country sourceValue = source.getValue();

			if(sourceValue == null){
				return;
			}

			int countryId = sourceValue.getId();

			divisionDropdown.setItems(divisions.filtered(division -> division.getCountryId() == countryId));
		});


		ObservableList<Country> finalCountries = countries;
		/**
		 * This lambda remains to have access to the countries variable above.
		 */
		customersTable.setRowFactory(customersTable -> {
			TableRow<Customer> row = new TableRow<>();

			row.setOnMouseClicked((MouseEvent event) -> {
				TableRow<Customer> source = (TableRow<Customer>) event.getSource();
				selectedCustomer = source.getItem();

				if(selectedCustomer == null){
					return;
				}

				idLabel.setText("ID: " + selectedCustomer.getId());
				nameField.setText(selectedCustomer.getName());
				addressField.setText(selectedCustomer.getAddress());
				postalField.setText(selectedCustomer.getPostalCode());
				phoneField.setText(selectedCustomer.getPhone());
				countryDropdown.setValue(Country.filterCountryByName(finalCountries, selectedCustomer.getCountry()));
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

	/**
	 * Sets up the object and sends it to the database service to create a customer
	 */
	@FXML
	private void addCustomerBtnClick(){
		Division division = divisionDropdown.getValue();

		Customer customer = new Customer(nameField.getText(),
										 addressField.getText(),
										 postalField.getText(),
										 phoneField.getText(),
										 division.getName());

		try {
			dbs.addCustomer(customer);
			reset();
		} catch (SQLException e) {
			dashboardController.displayError("customers.failedAdd");
			e.printStackTrace();
		}
	}

	/**
	 * Prepares the object and sends it to the database to update a customer
	 */
	@FXML
	private void updateCustomerBtnClick(){
		Customer customer = new Customer(selectedCustomer.getId(),
										 nameField.getText(),
										 addressField.getText(),
										 postalField.getText(),
										 phoneField.getText(),
										 divisionDropdown.getValue().getName(),
										 countryDropdown.getValue().getName());

		try {
			dbs.updateCustomer(customer);
			clearBtnClick();
		} catch (SQLException e) {
			dashboardController.displayError("customers.failedUpdate");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the ID of a customer object to be deleted and sends it to the database service
	 */
	@FXML
	private void deleteCustomerBtnClick(){
		try {
			dbs.deleteCustomer(selectedCustomer.getId());
			clearBtnClick();

			new Alert(Alert.AlertType.CONFIRMATION, "Deleted " + selectedCustomer.getName(), ButtonType.CLOSE).show();
		} catch (SQLException e) {
			dashboardController.displayError("customers.failedDelete");
			e.printStackTrace();
		}
	}

	/**
	 * Cleans up the form and buttons
	 */
	@FXML
	private void clearBtnClick(){
		addButton.setVisible(true);
		updateButton.setVisible(false);
		deleteButton.setVisible(false);
		clearButton.setVisible(false);

		reset();
	}

	/**
	 * Populates the table with customers
	 */
	private void populateTable(){
		try {
			customersTable.setItems(dbs.getCustomers());
		} catch (SQLException e) {
			dashboardController.displayError("customers.failedCustomers");
			e.printStackTrace();
		}
	}

	/**
	 * Resets the data in the form
	 */
	private void reset(){
		populateTable();

		selectedCustomer = null;

		idLabel.setText("ID: ");
		nameField.setText(null);
		addressField.setText(null);
		postalField.setText(null);
		phoneField.setText(null);
		countryDropdown.setValue(null);
		divisionDropdown.setValue(null);
	}
}
