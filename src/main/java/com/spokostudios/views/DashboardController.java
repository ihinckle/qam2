package com.spokostudios.views;

import com.spokostudios.services.ViewsService;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class DashboardController {
	private ViewsService vs;

	@FXML private HBox container;
	@FXML private SubScene viewPane;

	@FXML
	private void initialize() throws IOException {
		vs = ViewsService.getInstance();

		viewPane.heightProperty().bind(container.heightProperty());
		container.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			viewPane.setWidth(container.getWidth()-200);
		});

		viewPane.setRoot(vs.loadFXML("home"));
	}

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
			case "reportsButton":
				template = "reports";
				break;
		}

		viewPane.setRoot(vs.loadFXML(template));
	}
}
