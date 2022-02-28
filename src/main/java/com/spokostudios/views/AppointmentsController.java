package com.spokostudios.views;

import com.spokostudios.entities.Appointment;
import com.spokostudios.entities.Contact;
import com.spokostudios.entities.Customer;
import com.spokostudios.entities.TimeOption;
import com.spokostudios.entities.User;
import com.spokostudios.services.LocalizationService;
import com.spokostudios.services.dbservice.DBService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentsController {
    private DBService dbs;
    private LocalizationService ls;

    @FXML private TableView<Appointment> weekTable;
    @FXML private TableView<Appointment> monthTable;
    @FXML private TableColumn<Appointment, String> weekStartColumn;
    @FXML private TableColumn<Appointment, String> weekEndColumn;
    @FXML private TableColumn<Appointment, String> monthStartColumn;
    @FXML private TableColumn<Appointment, String> monthEndColumn;
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private TextField locationField;
    @FXML private TextField typeField;
    @FXML private ComboBox<Contact> contactField;
    @FXML private ComboBox<TimeOption> startTimeField;
    @FXML private ComboBox<TimeOption> endTimeField;
    @FXML private ComboBox<Customer> customerField;
    @FXML private ComboBox<User> userField;
    @FXML private DatePicker dateField;
    @FXML private Button createButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private TextField monthLabel;
    @FXML private TextField weekLabel;

    private Appointment selectedAppointment;
    private LocalDateTime month;
    private LocalDateTime week;

    @FXML
    private void initialize() throws SQLException {
        setServices();

        month = LocalDateTime.now();
        String monthText = String.valueOf(month.getYear())+"-"+month.getMonth().toString();
        monthLabel.setText(monthText);

        week = LocalDateTime.now();
        LocalDateTime weeksEnd = week.plusDays(6);
        String weekText = week.getMonth().toString()+"/"+String.valueOf(week.getDayOfMonth())+"-"+weeksEnd.getMonth().toString()+"/"+String.valueOf(weeksEnd.getDayOfMonth());
        weekLabel.setText(weekText);

        populateTables(month);
        customerField.setItems(dbs.getCustomers());
        contactField.setItems(dbs.getContacts());
        userField.setItems(dbs.getUsers());

        setTableRow();

        setTableCells();

        setTimeOptions();
    }

    @FXML
    private void create() throws Exception {
        LocalDate date = dateField.getValue();
        LocalTime startTime = startTimeField.getValue().getTimeInUTC().toLocalTime();
        LocalTime endTime = endTimeField.getValue().getTimeInUTC().toLocalTime();

        ZonedDateTime start = ZonedDateTime.of(date, startTime, ZoneId.of("UTC"));
        ZonedDateTime end = ZonedDateTime.of(date, endTime, ZoneId.of("UTC"));

        Appointment appointment = new Appointment(titleField.getText(),
                descriptionField.getText(),
                locationField.getText(),
                contactField.getValue(),
                typeField.getText(),
                start,
                end,
                customerField.getValue(),
                userField.getValue());

        dbs.Appointments().create(appointment);

        reset();
    }

    @FXML
    private void update() throws Exception {
        dbs.Appointments().update(selectedAppointment);

        reset();
    }

    @FXML
    void delete() throws SQLException {
        dbs.Appointments().delete(selectedAppointment.getId());

        reset();

        new Alert(Alert.AlertType.CONFIRMATION, "Deleted appointment ID:"+selectedAppointment.getId()+" Type: "+selectedAppointment.getType(), ButtonType.CLOSE).show();
    }

    @FXML
    private void clearSelection() throws SQLException {
        createButton.setVisible(true);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        clearButton.setVisible(false);

        reset();
    }

    private void setServices() throws SQLException {
        dbs = DBService.getInstance();
        ls = LocalizationService.getInstance();
    }

    private void setTableRow(){
        weekTable.setRowFactory(weekTable -> {
            TableRow<Appointment> row = new TableRow<>();

            row.setOnMouseClicked((MouseEvent event) -> {
                TableRow<Appointment> source = (TableRow<Appointment>) event.getSource();
                selectedAppointment = source.getItem();

                titleField.setText(selectedAppointment.getTitle());
                descriptionField.setText(selectedAppointment.getDescription());
                locationField.setText(selectedAppointment.getLocation());
                typeField.setText(selectedAppointment.getType());
                contactField.setValue(selectedAppointment.getContact());
                startTimeField.setValue(selectedAppointment.getStartTimeOption());
                endTimeField.setValue(selectedAppointment.getEndTimeOption());
                customerField.setValue(selectedAppointment.getCustomer());
                dateField.setValue(selectedAppointment.getDate());
                userField.setValue(selectedAppointment.getUser());

                createButton.setVisible(false);
                updateButton.setVisible(true);
                deleteButton.setVisible(true);
                clearButton.setVisible(true);
            });

            return row;
        });
    }

    private void setTableCells(){
        weekStartColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getStartInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });

        weekEndColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getEndInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });

        monthStartColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getStartInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });

        monthEndColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getEndInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });
    }

    private void setTimeOptions(){
        ObservableList<TimeOption> startTimes = FXCollections.observableArrayList();
        for(int i=8; i<17; i++){
            startTimes.add(new TimeOption(i));
        }
        startTimeField.setItems(startTimes);

        startTimeField.setOnAction((ActionEvent event) -> {
            ComboBox<TimeOption> source = (ComboBox) event.getSource();
            TimeOption option = source.getValue();

            if(option == null){
                endTimeField.setItems(null);
                return;
            }

            int timeInt = option.getTimeInt();
            ObservableList<TimeOption> endTimes = FXCollections.observableArrayList();
            for(int i=timeInt+1; i<=17; i++){
                endTimes.add(new TimeOption(i));
            }
            endTimeField.setItems(endTimes);
        });
    }

    private void populateTables(LocalDateTime month) throws SQLException {
        weekTable.setItems(dbs.Appointments().getWeek(week));
        monthTable.setItems(dbs.Appointments().getMonth(month));
    }

    private void reset() throws SQLException {
        titleField.setText(null);
        descriptionField.setText(null);
        locationField.setText(null);
        typeField.setText(null);
        contactField.setValue(null);
        startTimeField.setValue(null);
        endTimeField.setValue(null);
        customerField.setValue(null);
        dateField.setValue(null);

        populateTables(month);
    }

    @FXML
    private void nextMonth() throws SQLException {
        month = month.plusMonths(1);
        monthLabel.setText(String.valueOf(month.getYear())+"-"+month.getMonth().toString());
        ObservableList<Appointment> appointments = dbs.Appointments().getMonth(month);
        if(appointments.size() > 0) {
            monthTable.setVisible(true);
            monthTable.setItems(appointments);
        }else{
            monthTable.setVisible(false);
        }
    }

    @FXML
    private void previousMonth() throws SQLException {
        month = month.minusMonths(1);
        monthLabel.setText(String.valueOf(month.getYear())+"-"+month.getMonth().toString());
        ObservableList<Appointment> appointments = dbs.Appointments().getMonth(month);
        if(appointments.size() > 0) {
            monthTable.setVisible(true);
            monthTable.setItems(appointments);
        }else {
            monthTable.setVisible(false);
        }
    }

    @FXML
    private void nextWeek() throws SQLException {
        week = week.plusDays(7);
        LocalDateTime weeksEnd = week.plusDays(6);
        weekLabel.setText(week.getMonth().toString()+"/"+String.valueOf(week.getDayOfMonth())+"-"+weeksEnd.getMonth().toString()+"/"+String.valueOf(weeksEnd.getDayOfMonth()));
        ObservableList<Appointment> appointments = dbs.Appointments().getWeek(week);
        if(appointments.size() > 0) {
            weekTable.setVisible(true);
            weekTable.setItems(appointments);
        }else{
            weekTable.setVisible(false);
        }
    }

    @FXML
    private void previousWeek() throws SQLException {
        week = week.minusDays(7);
        LocalDateTime weeksEnd = week.plusDays(6);
        weekLabel.setText(week.getMonth().toString()+"/"+String.valueOf(week.getDayOfMonth())+"-"+weeksEnd.getMonth().toString()+"/"+String.valueOf(weeksEnd.getDayOfMonth()));
        ObservableList<Appointment> appointments = dbs.Appointments().getWeek(week);
        if(appointments.size() > 0) {
            weekTable.setVisible(true);
            weekTable.setItems(appointments);
        }else{
            weekTable.setVisible(false);
        }
    }
}
