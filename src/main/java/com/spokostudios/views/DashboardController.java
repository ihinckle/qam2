package com.spokostudios.views;

import com.spokostudios.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;

import java.io.IOException;

public class DashboardController {
	@FXML private SubScene viewPane;

	@FXML
	private void navigate(ActionEvent e) throws IOException {
		Button btn = (Button) e.getSource();
		String id = btn.getId();

		String template = null;
		switch (id){
			case "customersButton":
				template = "customers";
				break;
			case "appointmentsButton":
				template = "appointments";
				break;
		}

		viewPane.setRoot(loadFXML(template));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
		return fxmlLoader.load();
	}
}
