package com.spokostudios.views;

import com.spokostudios.entities.Appointment;
import com.spokostudios.entities.Contact;
import com.spokostudios.services.LocalizationService;
import com.spokostudios.services.dbservice.DBService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.HashMap;

public class ReportsController {
	private DBService dbs;
	private LocalizationService ls;

	@FXML private VBox typeBox;
	@FXML private VBox monthBox;
	@FXML private ChoiceBox<Contact> reportContactField;
	@FXML private TableView<Appointment> appointmentTable;
	@FXML private TableColumn<Appointment, String> startColumn;
	@FXML private TableColumn<Appointment, String> endColumn;

	@FXML
	private void initialize() throws SQLException {
		dbs = DBService.getInstance();
		ls = LocalizationService.getInstance();

		HashMap<String, Integer> typesCount = dbs.getTotalByType();
		typesCount.forEach((String key, Integer value) -> {
			Label label = new Label(key+": "+String.valueOf(value));
			typeBox.getChildren().add(label);
		});

		HashMap<String, Integer> monthsCount = dbs.getTotalByMonth();
		monthsCount.forEach((String key, Integer value) -> {
			Label label = new Label(key+": "+String.valueOf(value));
			monthBox.getChildren().add(label);
		});

		reportContactField.setItems(dbs.getContacts());
		reportContactField.setOnAction((ActionEvent event) -> {
			ChoiceBox<Contact> source = (ChoiceBox) event.getSource();
			Contact contact = source.getValue();

			try {
				appointmentTable.setItems(dbs.Appointments().get(contact));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			appointmentTable.refresh();
		});

		setTableCells();
	}

	private void setTableCells(){
		startColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
				String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getStartInUTC());
				SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
				return ssp;
			}
		});

		endColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
				String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getEndInUTC());
				SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
				return ssp;
			}
		});
	}
}
