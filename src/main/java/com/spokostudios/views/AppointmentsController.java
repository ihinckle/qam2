package com.spokostudios.views;

import com.spokostudios.entities.Appointment;
import com.spokostudios.entities.Country;
import com.spokostudios.entities.Customer;
import com.spokostudios.entities.Division;
import com.spokostudios.services.DBService;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class AppointmentsController {
    private DBService dbs;

    @FXML private TableView<Appointment> weekTable;
    @FXML private TableView<Appointment> monthTable;

    private Appointment selectedAppointment;

    @FXML
    private void initialize() throws SQLException {
        dbs = DBService.getInstance();

        weekTable.setItems(dbs.getAppointments());
        monthTable.setItems(dbs.getAppointments());

        weekTable.setRowFactory(weekTable -> {
            TableRow<Customer> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                TableRow<Appointment> source = (TableRow<Appointment>) event.getSource();
                selectedAppointment = source.getItem();

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
    }
}
