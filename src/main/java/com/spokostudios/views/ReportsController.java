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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.HashMap;

public class ReportsController {
	private DBService dbs;
	private LocalizationService ls;
	private DashboardController dashboardController = DashboardController.getInstance();

	@FXML private VBox typeBox;
	@FXML private VBox monthBox;
	@FXML private ChoiceBox<Contact> reportContactField;
	@FXML private TableView<Appointment> appointmentTable;
	@FXML private TableColumn<Appointment, String> startColumn;
	@FXML private TableColumn<Appointment, String> endColumn;
	@FXML private VBox usLocales;
	@FXML private VBox ukLocales;
	@FXML private VBox canadaLocales;
	@FXML private Tab countTab;
	@FXML private Tab appointmentTab;
	@FXML private Tab customerTab;
	@FXML private Label typeLabel;
	@FXML private Label monthLabel;
	@FXML private Label contactLabel;
	@FXML private TableColumn<Appointment, String> titleColumn;
	@FXML private TableColumn<Appointment, String> typeColumn;
	@FXML private TableColumn<Appointment, String> descriptionColumn;
	@FXML private TableColumn<Appointment, String> customerColumn;
	@FXML private Label usLabel;
	@FXML private Label ukLabel;
	@FXML private Label canadaLabel;

	@FXML
	private void initialize(){
		try {
			dbs = DBService.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ls = LocalizationService.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		countTab.setText(ls.getText("reports.countTab"));
		appointmentTab.setText(ls.getText("reports.appointmentTab"));
		customerTab.setText(ls.getText("reports.customerTab"));
		typeLabel.setText(ls.getText("reports.typeLabel"));
		monthLabel.setText(ls.getText("reports.monthLabel"));
		contactLabel.setText(ls.getText("reports.contactLabel"));
		titleColumn.setText(ls.getText("reports.titleColumn"));
		typeColumn.setText(ls.getText("reports.typeColumn"));
		descriptionColumn.setText(ls.getText("reports.descriptionColumn"));
		startColumn.setText(ls.getText("reports.startColumn"));
		endColumn.setText(ls.getText("reports.endColumn"));
		customerColumn.setText(ls.getText("reports.customerColumn"));
		usLabel.setText(ls.getText("reports.usLabel"));
		ukLabel.setText(ls.getText("reports.ukLabel"));
		canadaLabel.setText(ls.getText("reports.canadaLabel"));

		HashMap<String, Integer> typesCount = null;
		try {
			typesCount = dbs.getTotalByType();
		} catch (SQLException e) {
			dashboardController.displayError("reports.totalsError");
			e.printStackTrace();
		}

		/**
		 * A forEach. Easy lambda
		 */
		typesCount.forEach((String key, Integer value) -> {
			Label label = new Label(key+": "+String.valueOf(value));
			typeBox.getChildren().add(label);
		});

		HashMap<String, Integer> monthsCount = null;
		try {
			monthsCount = dbs.getTotalByMonth();
		} catch (SQLException e) {
			dashboardController.displayError("reports.totalsError");
			e.printStackTrace();
		}

		/**
		 * A forEach. Easy lambda
		 */
		monthsCount.forEach((String key, Integer value) -> {
			Label label = new Label(key+": "+String.valueOf(value));
			monthBox.getChildren().add(label);
		});

		try {
			reportContactField.setItems(dbs.getContacts());
		} catch (SQLException e) {
			dashboardController.displayError("reports.contactsError");
			e.printStackTrace();
		}

		/**
		 * An event. Easy lambda
		 */
		reportContactField.setOnAction((ActionEvent event) -> {
			ChoiceBox<Contact> source = (ChoiceBox) event.getSource();
			Contact contact = source.getValue();

			try {
				appointmentTable.setItems(dbs.Appointments().get(contact));
			} catch (SQLException e) {
				dashboardController.displayError("reports.appointmentsError");
				e.printStackTrace();
			}
			appointmentTable.refresh();
		});

		HashMap<Integer, HashMap> customerLocations = null;
		try {
			customerLocations = dbs.getCustomersTotalByLocation();
		} catch (SQLException e) {
			dashboardController.displayError("reports.customerLocationsError");
			e.printStackTrace();
		}

		/**
		 * A forEach. Easy lambda
		 */
		customerLocations.forEach((Integer id, HashMap map) -> {
			HashMap<String, Integer> typedMap = map;

			/**
			 * A forEach. Easy lambda
			 * Though now we are getting nested. Any more complicated I'd refactor
			 */
			typedMap.forEach((String locale, Integer count) -> {
				Label label = new Label(locale+": "+String.valueOf(count));

				switch (id){
					case 1:
						usLocales.getChildren().add(label);
						break;
					case 2:
						ukLocales.getChildren().add(label);
						break;
					case 3:
						canadaLocales.getChildren().add(label);
						break;
				}
			});
		});

		setTableCells();
	}

	/**
	 * Sets the table cells to be able to display a nicely readable human time.
	 */
	private void setTableCells(){
		/**
		 * Creates its own override. I did not want to polute the rest of the controller context
		 */
		startColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
				String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getStartInUTC());
				SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
				return ssp;
			}
		});

		/**
		 * Creates its own override. I did not want to polute the rest of the controller context
		 */
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
