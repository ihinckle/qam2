package com.spokostudios.views;

import com.spokostudios.entities.Appointment;
import com.spokostudios.services.LocalizationService;
import com.spokostudios.services.dbservice.DBService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class HomeController {
	private DBService dbs;
	private LocalizationService ls;

	@FXML private Label appointmentNotice;

	@FXML
	private void initialize() throws SQLException {
		dbs = DBService.getInstance();
		ls = LocalizationService.getInstance();

		Appointment upcomingAppointment = dbs.Appointments().getUpcoming();

		if(upcomingAppointment != null){
			appointmentNotice.setText("Upcoming appointment: ID-"+upcomingAppointment.getId()+" Start-"+ls.formattedDateFromUTC(upcomingAppointment.getStartInUTC())+".");
		}else{
			appointmentNotice.setText("No upcoming appointments.");
		}
	}
}
