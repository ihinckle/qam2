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
    private DashboardController dashboardController = DashboardController.getInstance();

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
    @FXML private Tab weekTab;
    @FXML private Tab monthTab;
    @FXML private Button previousWeekButton;
    @FXML private Button previousMonthButton;
    @FXML private Button nextWeekButton;
    @FXML private Button nextMonthButton;
    @FXML private TableColumn<Appointment, String> weekTitleColumn;
    @FXML private TableColumn<Appointment, String> weekDescriptionColumn;
    @FXML private TableColumn<Appointment, String> weekLocationColumn;
    @FXML private TableColumn<Appointment, String> weekContactColumn;
    @FXML private TableColumn<Appointment, String> weekTypeColumn;
    @FXML private TableColumn<Appointment, String> weekCustomerColumn;
    @FXML private TableColumn<Appointment, String> weekUserColumn;
    @FXML private TableColumn<Appointment, String> monthTitleColumn;
    @FXML private TableColumn<Appointment, String> monthDescriptionColumn;
    @FXML private TableColumn<Appointment, String> monthLocationColumn;
    @FXML private TableColumn<Appointment, String> monthContactColumn;
    @FXML private TableColumn<Appointment, String> monthTypeColumn;
    @FXML private TableColumn<Appointment, String> monthCustomerColumn;
    @FXML private TableColumn<Appointment, String> monthUserColumn;
    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label dateLabel;
    @FXML private Label typeLabel;
    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label contactLabel;
    @FXML private Label customerLabel;
    @FXML private Label userLabel;
    @FXML private Label idLabel;

    private Appointment selectedAppointment;
    private LocalDateTime month;
    private LocalDateTime week;

    @FXML
    private void initialize(){
        setServices();

        weekTab.setText(ls.getText("appointments.weekTab"));
        monthTab.setText(ls.getText("appointments.monthTab"));
        previousWeekButton.setText(ls.getText("appointments.previousButton"));
        previousMonthButton.setText(ls.getText("appointments.previousButton"));
        nextWeekButton.setText(ls.getText("appointments.nextButton"));
        nextMonthButton.setText(ls.getText("appointments.nextButton"));
        weekTitleColumn.setText(ls.getText("appointments.title"));
        weekDescriptionColumn.setText(ls.getText("appointments.description"));
        weekLocationColumn.setText(ls.getText("appointments.location"));
        weekContactColumn.setText(ls.getText("appointments.contact"));
        weekTypeColumn.setText(ls.getText("appointments.type"));
        weekStartColumn.setText(ls.getText("appointments.start"));
        weekEndColumn.setText(ls.getText("appointments.end"));
        weekCustomerColumn.setText(ls.getText("appointments.customer")+" ID");
        weekUserColumn.setText(ls.getText("appointments.user")+" ID");
        monthTitleColumn.setText(ls.getText("appointments.title"));
        monthDescriptionColumn.setText(ls.getText("appointments.description"));
        monthLocationColumn.setText(ls.getText("appointments.location"));
        monthContactColumn.setText(ls.getText("appointments.contact"));
        monthTypeColumn.setText(ls.getText("appointments.type"));
        monthStartColumn.setText(ls.getText("appointments.start"));
        monthEndColumn.setText(ls.getText("appointments.end"));
        monthCustomerColumn.setText(ls.getText("appointments.customer")+" ID");
        monthUserColumn.setText(ls.getText("appointments.user")+" ID");
        titleLabel.setText(ls.getText("appointments.title"));
        descriptionLabel.setText(ls.getText("appointments.description"));
        locationLabel.setText(ls.getText("appointments.location"));
        contactLabel.setText(ls.getText("appointments.contact"));
        typeLabel.setText(ls.getText("appointments.type"));
        startLabel.setText(ls.getText("appointments.start"));
        endLabel.setText(ls.getText("appointments.end"));
        customerLabel.setText(ls.getText("appointments.customer"));
        userLabel.setText(ls.getText("appointments.user"));
        dateLabel.setText(ls.getText("appointments.date"));
        createButton.setText("create");
        updateButton.setText("update");
        deleteButton.setText("delete");
        clearButton.setText("clear");

        month = LocalDateTime.now();
        String monthText = String.valueOf(month.getYear())+"-"+month.getMonth().toString();
        monthLabel.setText(monthText);

        week = LocalDateTime.now();
        LocalDateTime weeksEnd = week.plusDays(6);
        String weekText = week.getMonth().toString()+"/"+String.valueOf(week.getDayOfMonth())+"-"+weeksEnd.getMonth().toString()+"/"+String.valueOf(weeksEnd.getDayOfMonth());
        weekLabel.setText(weekText);

        populateTables();
        try {
            customerField.setItems(dbs.getCustomers());
        } catch (SQLException e) {
            dashboardController.displayError("customers.failedCustomers");
            e.printStackTrace();
        }
        try {
            contactField.setItems(dbs.getContacts());
        } catch (SQLException e) {
            dashboardController.displayError("appointments.contactsFailed");
            e.printStackTrace();
        }
        try {
            userField.setItems(dbs.getUsers());
        } catch (SQLException e) {
            dashboardController.displayError("appointments.UsersFailed");
            e.printStackTrace();
        }

        setTableRow();

        setTableCells();

        setTimeOptions();
    }

    /**
     * Prepare the object and send it to the database service to create an appointment
     */
    @FXML
    private void create(){
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

        try{
            dbs.Appointments().create(appointment);
            reset();
        }catch(SQLException e){
            dashboardController.displayError("appointments.createFailed");
        }catch(Exception e){
            dashboardController.displayError("appointments.creationConflict");
            e.printStackTrace();
        }
    }

    /**
     * Prepare the object and send it to the database service to update and appointment
     */
    @FXML
    private void update(){
        LocalDate date = dateField.getValue();
        LocalTime startTime = startTimeField.getValue().getTimeInUTC().toLocalTime();
        LocalTime endTime = endTimeField.getValue().getTimeInUTC().toLocalTime();

        ZonedDateTime start = ZonedDateTime.of(date, startTime, ZoneId.of("UTC"));
        ZonedDateTime end = ZonedDateTime.of(date, endTime, ZoneId.of("UTC"));

        Appointment appointment = new Appointment(selectedAppointment.getId(),
                titleField.getText(),
                descriptionField.getText(),
                locationField.getText(),
                contactField.getValue(),
                typeField.getText(),
                start,
                end,
                customerField.getValue(),
                userField.getValue());

        try {
            dbs.Appointments().update(appointment);
            clearSelection();
        } catch (Exception e) {
            dashboardController.displayError("appointments.updateFailed");
            e.printStackTrace();
        }
    }

    /**
     * Gets the ID of an appointment to delete and sends it to the database service
     * Notifies the user after a successful deletion.
     */
    @FXML
    void delete(){
        try {
            dbs.Appointments().delete(selectedAppointment.getId());
            clearSelection();
            String alertString = String.format(ls.getText("appointments.deletedMessage") ,selectedAppointment.getId(), selectedAppointment.getType());
            new Alert(Alert.AlertType.CONFIRMATION, alertString, ButtonType.CLOSE).show();
        } catch (SQLException e) {
            dashboardController.displayError("appointments.deleteFailed");
            e.printStackTrace();
        }
    }

    /**
     * Resets the add/update form and buttons
     */
    @FXML
    private void clearSelection(){
        createButton.setVisible(true);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        clearButton.setVisible(false);

        reset();
    }

    /**
     * Bootstraps the services to be used
     */
    private void setServices(){
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
    }

    /**
     * Sets up the mouse click action on a row
     */
    private void setTableRow(){
        weekTable.setRowFactory(weekTable -> {
            TableRow<Appointment> row = new TableRow<>();

            /**
             * I wanted to keep all the logic togther. It is already in its own method
             * for singular purpose
             */
            row.setOnMouseClicked((MouseEvent event) -> {
                TableRow<Appointment> source = (TableRow<Appointment>) event.getSource();
                selectedAppointment = source.getItem();

                if(selectedAppointment == null){
                    return;
                }

                idLabel.setText("ID: "+selectedAppointment.getId());
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

    /**
     * Sets up the Start and End table cells to have a custom string instead of
     * the default ZonedDateTime.toString()
     */
    private void setTableCells(){
        /**
         * These lambas remain because each one creates its own override.
         */
        weekStartColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getStartInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });

        /**
         * These lambas remain because each one creates its own override.
         */
        weekEndColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getEndInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });

        /**
         * These lambas remain because each one creates its own override.
         */
        monthStartColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getStartInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });

        /**
         * These lambas remain because each one creates its own override.
         */
        monthEndColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointment) {
                String formatteddate = ls.formattedDateFromUTC(appointment.getValue().getEndInUTC());
                SimpleStringProperty ssp = new SimpleStringProperty(formatteddate);
                return ssp;
            }
        });
    }

    /**
     * Sets up the time selection in the form. Will not allow an end time
     * to be selected until a start time has been chosen.
     */
    private void setTimeOptions(){
        ObservableList<TimeOption> startTimes = FXCollections.observableArrayList();
        for(int i=8; i<17; i++){
            startTimes.add(new TimeOption(i));
        }
        startTimeField.setItems(startTimes);

        /**
         * I do most event handlers as lambdas out of personal habit.
         */
        startTimeField.setOnAction((ActionEvent event) -> {
            ComboBox<TimeOption> source = (ComboBox) event.getSource();
            TimeOption option = source.getValue();

            endTimeField.setItems(null);

            int timeInt = option.getTimeInt();
            ObservableList<TimeOption> endTimes = FXCollections.observableArrayList();
            for(int i=timeInt+1; i<=17; i++){
                endTimes.add(new TimeOption(i));
            }
            endTimeField.setItems(endTimes);
        });
    }

    /**
     * Populates the tables with data:
     * on load, on create, on update, on delete, and on page change
     */
    private void populateTables(){
        try {
            weekTable.setItems(dbs.Appointments().getWeek(week));
        } catch (SQLException e) {
            dashboardController.displayError("appointments.weekFailed");
            e.printStackTrace();
        }
        try {
            monthTable.setItems(dbs.Appointments().getMonth(month));
        } catch (SQLException e) {
            dashboardController.displayError("appointments.monthFailed");
            e.printStackTrace();
        }
    }

    /**
     * Resets the forms and tables. Will remain on whichever week or month
     * that has been selected.
     */
    private void reset(){
        selectedAppointment = null;

        idLabel.setText("ID: ");
        titleField.setText(null);
        descriptionField.setText(null);
        locationField.setText(null);
        typeField.setText(null);
        contactField.setValue(null);
        startTimeField.setValue(null);
        endTimeField.setValue(null);
        customerField.setValue(null);
        dateField.setValue(null);
        userField.setValue(null);

        populateTables();
    }

    /**
     * Move the table forward a month
     */
    @FXML
    private void nextMonth(){
        month = month.plusMonths(1);
        monthLabel.setText(String.valueOf(month.getYear())+"-"+month.getMonth().toString());
        reset();
    }

    /**
     * Move the table backwards a month
     */
    @FXML
    private void previousMonth(){
        month = month.minusMonths(1);
        monthLabel.setText(String.valueOf(month.getYear())+"-"+month.getMonth().toString());
        reset();
    }

    /**
     * Move the table up a week
     */
    @FXML
    private void nextWeek(){
        week = week.plusDays(7);
        LocalDateTime weeksEnd = week.plusDays(6);
        weekLabel.setText(week.getMonth().toString()+"/"+String.valueOf(week.getDayOfMonth())+"-"+weeksEnd.getMonth().toString()+"/"+String.valueOf(weeksEnd.getDayOfMonth()));
        reset();
    }

    /**
     * Move the table back a week
     */
    @FXML
    private void previousWeek(){
        week = week.minusDays(7);
        LocalDateTime weeksEnd = week.plusDays(6);
        weekLabel.setText(week.getMonth().toString()+"/"+String.valueOf(week.getDayOfMonth())+"-"+weeksEnd.getMonth().toString()+"/"+String.valueOf(weeksEnd.getDayOfMonth()));
        reset();
    }
}
