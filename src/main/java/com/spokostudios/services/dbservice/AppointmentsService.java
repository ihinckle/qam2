package com.spokostudios.services.dbservice;

import com.spokostudios.entities.Appointment;
import com.spokostudios.entities.Contact;
import com.spokostudios.entities.Customer;
import com.spokostudios.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AppointmentsService {
	private static Connection connection;

	private static final String GETSTATEMENT = "SELECT Appointment_ID,Title,Description,Location,appointments.Contact_ID,Contact_Name,Email,Type,Start,End,appointments.Customer_ID,Customer_Name,appointments.User_ID,User_Name FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID INNER JOIN users ON appointments.User_ID = users.User_ID";
	private static final String GETFORCONTACTSTATEMENT = "SELECT Appointment_ID,Title,Description,Location,appointments.Contact_ID,Contact_Name,Email,Type,Start,End,appointments.Customer_ID,Customer_Name,appointments.User_ID,User_Name FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID INNER JOIN users ON appointments.User_ID = users.User_ID WHERE appointments.Contact_ID=?";
	private static final String GETFORMONTHSTATEMENT = "SELECT Appointment_ID,Title,Description,Location,Type,Start,End,appointments.Customer_ID,Customer_Name,appointments.User_ID,User_Name,appointments.Contact_ID,Contact_Name,Email FROM appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID INNER JOIN users ON appointments.User_ID=users.User_ID WHERE DATE_FORMAT(Start, '%Y%m') = DATE_FORMAT(?, '%Y%m')";
	private static final String GETFORWEEKSTATEMENT = "SELECT Appointment_ID,Title,Description,Location,Type,Start,End,appointments.Customer_ID,Customer_Name,appointments.User_ID,User_Name,appointments.Contact_ID,Contact_Name,Email FROM appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID INNER JOIN users ON appointments.User_ID = users.User_ID WHERE DATE_FORMAT(Start, '%Y%m%d') >= DATE_FORMAT(?, '%Y%m%d') AND DATE_FORMAT(Start, '%Y%m%d') < DATE_FORMAT(? + INTERVAL 6 DAY, '%Y%m%d')";
	private static final String CREATESTATEMENT = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATESTATEMENT = "UPDATE appointments SET Title=?,Description=?,Location=?,Type=?,Start=?,End=?,Customer_ID=?,User_ID=?,Contact_ID=? WHERE Appointment_ID=?";
	private static final String DELETESTATEMENT = "DELETE FROM appointments WHERE Appointment_ID=?";
	private static final String DELETEOFCUSTOMERSTATEMENT = "DELETE FROM appointments WHERE Customer_ID=?";
	private static final String GETUPCOMINGSTATEMENT = "SELECT Appointment_ID,Title,Description,Location,Type,Start,End,appointments.Customer_ID,Customer_Name,appointments.User_ID,User_Name,appointments.Contact_ID,Contact_Name,Email FROM appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID INNER JOIN users ON appointments.User_ID = users.User_ID WHERE Start > NOW() AND Start < (NOW() + INTERVAL 15 MINUTE )";
	private static final String CHECKCONFLICTSTATEMENT = "SELECT * FROM appointments WHERE Customer_ID=? AND (Start < ? AND End > ?) OR (Start < ?)";

	AppointmentsService(Connection connection){
		this.connection = connection;
	}

	public static ObservableList<Appointment> get() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GETSTATEMENT);
		ResultSet rs = statement.executeQuery();

		ObservableList<Appointment> appointments = FXCollections.observableArrayList();

		while(rs.next()){
			LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
			ZonedDateTime startUTC = startDateTime.atZone(ZoneId.of("UTC"));

			LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
			ZonedDateTime endUTC = endDateTime.atZone(ZoneId.of("UTC"));

			Contact contact = new Contact(rs.getInt("appointments.Contact_ID"),
					rs.getString("Contact_Name"),
					rs.getString("Email"));

			Customer customer = new Customer(rs.getInt("appointments.Customer_ID"),
					rs.getString("Customer_Name"));

			User user = new User(rs.getInt("appointments.User_ID"), rs.getString("User_Name"));

			Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
					rs.getString("Title"),
					rs.getString("Description"),
					rs.getString("Location"),
					contact,
					rs.getString("Type"),
					startUTC,
					endUTC,
					customer,
					user);

			appointments.add(appointment);
		}

		return appointments;
	}

	public static ObservableList<Appointment> get(Contact contact) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GETFORCONTACTSTATEMENT);
		statement.setInt(1, contact.getId());
		ResultSet rs = statement.executeQuery();

		ObservableList<Appointment> appointments = FXCollections.observableArrayList();

		while(rs.next()){
			LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
			ZonedDateTime startUTC = startDateTime.atZone(ZoneId.of("UTC"));

			LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
			ZonedDateTime endUTC = endDateTime.atZone(ZoneId.of("UTC"));

			Customer customer = new Customer(rs.getInt("appointments.Customer_ID"),
					rs.getString("Customer_Name"));

			User user = new User(rs.getInt("appointments.User_ID"), rs.getString("User_Name"));

			Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
					rs.getString("Title"),
					rs.getString("Description"),
					rs.getString("Location"),
					contact,
					rs.getString("Type"),
					startUTC,
					endUTC,
					customer,
					user);

			appointments.add(appointment);
		}

		return appointments;
	}

	public static ObservableList<Appointment> getMonth(LocalDateTime date) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GETFORMONTHSTATEMENT);
		statement.setTimestamp(1, Timestamp.valueOf(date));
		ResultSet rs = statement.executeQuery();

		ObservableList<Appointment> appointments = FXCollections.observableArrayList();

		while(rs.next()){
			LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
			ZonedDateTime startUTC = startDateTime.atZone(ZoneId.of("UTC"));

			LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
			ZonedDateTime endUTC = endDateTime.atZone(ZoneId.of("UTC"));

			Contact contact = new Contact(rs.getInt("appointments.Contact_ID"),
					rs.getString("Contact_Name"),
					rs.getString("Email"));

			Customer customer = new Customer(rs.getInt("appointments.Customer_ID"),
					rs.getString("Customer_Name"));

			User user = new User(rs.getInt("appointments.User_ID"), rs.getString("User_Name"));

			Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
					rs.getString("Title"),
					rs.getString("Description"),
					rs.getString("Location"),
					contact,
					rs.getString("Type"),
					startUTC,
					endUTC,
					customer,
					user);

			appointments.add(appointment);
		}

		return appointments;
	}

	public static ObservableList<Appointment> getWeek(LocalDateTime date) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GETFORWEEKSTATEMENT);
		statement.setTimestamp(1, Timestamp.valueOf(date));
		statement.setTimestamp(2, Timestamp.valueOf(date));
		ResultSet rs = statement.executeQuery();

		ObservableList<Appointment> appointments = FXCollections.observableArrayList();

		while(rs.next()){
			LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
			ZonedDateTime startUTC = startDateTime.atZone(ZoneId.of("UTC"));

			LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
			ZonedDateTime endUTC = endDateTime.atZone(ZoneId.of("UTC"));

			Contact contact = new Contact(rs.getInt("appointments.Contact_ID"),
					rs.getString("Contact_Name"),
					rs.getString("Email"));

			Customer customer = new Customer(rs.getInt("appointments.Customer_ID"),
					rs.getString("Customer_Name"));

			User user = new User(rs.getInt("appointments.User_ID"), rs.getString("User_Name"));

			Appointment appointment = new Appointment(rs.getInt("Appointment_ID"),
					rs.getString("Title"),
					rs.getString("Description"),
					rs.getString("Location"),
					contact,
					rs.getString("Type"),
					startUTC,
					endUTC,
					customer,
					user);

			appointments.add(appointment);
		}

		return appointments;
	}

	public void create(Appointment appointment) throws Exception {
		if(checkForConflict(appointment)){
			throw new Exception();
		}

		PreparedStatement statement = connection.prepareStatement(CREATESTATEMENT);
		statement.setString(1, appointment.getTitle());
		statement.setString(2, appointment.getDescription());
		statement.setString(3, appointment.getLocation());
		statement.setString(4, appointment.getType());
		statement.setTimestamp(5, Timestamp.valueOf(appointment.getStartInUTC().toLocalDateTime()));
		statement.setTimestamp(6, Timestamp.valueOf(appointment.getEndInUTC().toLocalDateTime()));
		statement.setInt(7, appointment.getCustomerId());
		statement.setInt(8, appointment.getUser().getId());
		statement.setInt(9, appointment.getContact().getId());

		statement.executeUpdate();
	}

	public void update(Appointment appointment) throws Exception {
		if(checkForConflict(appointment)){
			throw new Exception();
		}

		PreparedStatement statement = connection.prepareStatement(UPDATESTATEMENT);
		statement.setString(1, appointment.getTitle());
		statement.setString(2, appointment.getDescription());
		statement.setString(3, appointment.getLocation());
		statement.setString(4, appointment.getType());
		statement.setTimestamp(5, Timestamp.valueOf(appointment.getStartInUTC().toLocalDateTime()));
		statement.setTimestamp(6, Timestamp.valueOf(appointment.getEndInUTC().toLocalDateTime()));
		statement.setInt(7, appointment.getCustomerId());
		statement.setInt(8, appointment.getUser().getId());
		statement.setInt(9, appointment.getContact().getId());
		statement.setInt(10, appointment.getId());

		statement.executeUpdate();
	}

	public void delete(int id) throws SQLException{
		PreparedStatement statement = connection.prepareStatement(DELETESTATEMENT);
		statement.setInt(1, id);

		statement.executeUpdate();
	}

	public void deleteOfCustomer(int id) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(DELETEOFCUSTOMERSTATEMENT);
		statement.setInt(1, id);
		statement.executeUpdate();
	}

	public Appointment getUpcoming() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(GETUPCOMINGSTATEMENT);
		ResultSet rs = statement.executeQuery();

		if(!rs.next()){
			return null;
		}

		Contact contact = new Contact(rs.getInt("appointments.Contact_ID"),
				rs.getString("Contact_Name"),
				rs.getString("Email"));

		LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
		ZonedDateTime startUTC = startDateTime.atZone(ZoneId.of("UTC"));

		LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
		ZonedDateTime endUTC = endDateTime.atZone(ZoneId.of("UTC"));

		Customer customer = new Customer(rs.getInt("appointments.Customer_ID"),
				rs.getString("Customer_Name"));

		User user = new User(rs.getInt("appointments.User_ID"), rs.getString("User_Name"));

		return new Appointment(rs.getInt("Appointment_ID"),
				rs.getString("Title"),
				rs.getString("Description"),
				rs.getString("Location"),
				contact,
				rs.getString("Type"),
				startUTC,
				endUTC,
				customer,
				user);
	}

	private Boolean checkForConflict(Appointment appointment) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(CHECKCONFLICTSTATEMENT);
		statement.setInt(1, appointment.getCustomerId());
		statement.setTimestamp(2, Timestamp.valueOf(appointment.getStartInUTC().toLocalDateTime()));
		statement.setTimestamp(3, Timestamp.valueOf(appointment.getEndInUTC().toLocalDateTime()));
		statement.setTimestamp(4, Timestamp.valueOf(appointment.getEndInUTC().toLocalDateTime()));
		ResultSet rs = statement.executeQuery();

		if(rs.next()){
			return true;
		}

		return false;
	}
}
