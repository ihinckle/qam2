package com.spokostudios.views;

import com.spokostudios.services.LocalizationService;
import com.spokostudios.services.ViewsService;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {
	private ViewsService vs = ViewsService.getInstance();
	private LocalizationService ls;
	private static DashboardController instance;

	@FXML private HBox container;
	@FXML private SubScene viewPane;
	@FXML private Button customersButton;
	@FXML private Button appointmentsButton;
	@FXML private Button reportsButton;
	@FXML private Button errorButton;
	@FXML private VBox errorBox;
	@FXML private TextArea errorLabel;

	@FXML
	private void initialize(){
		instance = this;

		try {
			ls = LocalizationService.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPane.heightProperty().bind(container.heightProperty());
		/**
		 * Super simple listener to set the size of the subscene with the parent.
		 * Why not lambda?
		 */
		container.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
			viewPane.setWidth(container.getWidth()-200);
		});

		try {
			viewPane.setRoot(vs.loadFXML("home"));
		} catch (IOException e) {
			displayError("applicationError");
			e.printStackTrace();
		}

		customersButton.setText(ls.getText("dashboard.customersButton"));
		appointmentsButton.setText(ls.getText("dashboard.appointmentsButton"));
		reportsButton.setText(ls.getText("dashboard.reportsButton"));
		errorButton.setText(ls.getText("errorButton"));
	}

	/**
	 * Navigates to another subscene
	 * @param e The event triggered by the button click
	 */
	@FXML
	private void navigate(ActionEvent e){
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

		try {
			viewPane.setRoot(vs.loadFXML(template));
		} catch (IOException ex) {
			displayError("applicationError");
			ex.printStackTrace();
		}
	}

	/**
	 * Display an error on the side to the user
	 * @param message The key of the localization message to be displayed
	 */
	public void displayError(String message){
		errorBox.setVisible(true);
		errorLabel.setText(ls.getText(message));
	}

	/**
	 * Dismisses an error
	 */
	@FXML
	private void closeError(){
		errorBox.setVisible(false);
		errorLabel.setText(null);
	}

	/**
	 * We created a static instance of this controller to be passed
	 * to other controllers to be able to easily display error messages
	 * @return The instance of this controller
	 */
	public static DashboardController getInstance(){
		return instance;
	}
}
