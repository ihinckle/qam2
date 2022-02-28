package com.spokostudios.views;

import com.spokostudios.entities.Appointment;
import com.spokostudios.services.LocalizationService;
import com.spokostudios.services.dbservice.DBService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

/**
 * I got carried away with this controller. It was supposed to have
 * everying in the reports view as well, but it became easier to give
 * the reports their own page
 */
public class HomeController {
	private DBService dbs;
	private LocalizationService ls;

	@FXML private Label appointmentNotice;

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

		DashboardController dashboardController = DashboardController.getInstance();

		Appointment upcomingAppointment = null;
		try {
			upcomingAppointment = dbs.Appointments().getUpcoming();
		} catch (SQLException e) {
			dashboardController.displayError("home.failedUpcoming");
			e.printStackTrace();
		}

		if(upcomingAppointment != null){
			appointmentNotice.setText(String.format(ls.getText("home.upcomingAppointment"), upcomingAppointment.getId(), ls.formattedDateFromUTC(upcomingAppointment.getStartInUTC())));
		}else{
			appointmentNotice.setText(ls.getText("home.noUpcoming"));
		}
	}
}
