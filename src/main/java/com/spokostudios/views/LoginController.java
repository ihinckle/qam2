package com.spokostudios.views;

import com.spokostudios.App;
import com.spokostudios.services.LocalizationService;
import com.spokostudios.services.dbservice.DBService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class LoginController {
	private LocalizationService ls;
	private DBService dbs;

	@FXML private Text regionText;
	@FXML private Text errorText;
	@FXML private HBox errorBox;
	@FXML private Label userLabel;
	@FXML private Label passwordLabel;
	@FXML private Label regionLabel;
	@FXML private Button loginButton;
	@FXML private Button errorButton;
	@FXML private TextField userField;
	@FXML private PasswordField passwordField;

	@FXML
	protected void initialize(){
		ls = LocalizationService.getInstance();
		Locale locale = ls.getLocale();

		regionText.setText(locale.getDisplayCountry());

		if(ls.useDefaults()){
			errorText.setText("No localization file found. Defaulting to english.");
			errorBox.setVisible(true);

			return;
		}

		userLabel.setText(ls.getText("login.userLabel"));
		passwordLabel.setText(ls.getText("login.passwordLabel"));
		regionLabel.setText(ls.getText("login.regionLabel"));
		loginButton.setText(ls.getText("login.loginButton"));
	}

	@FXML
	private void login(){
		boolean loginSuccessful = false;
		
		try{
			dbs = DBService.getInstance();
		}catch(SQLException e){
			errorBox.setVisible(true);
			errorText.setText("Failed to connect to database. Please contact a system administrator.");
		}

		String user = userField.getText();
		String password = passwordField.getText();

		try{
			loginSuccessful = dbs.login(user, password);
		}catch(SQLException e){
			errorBox.setVisible(true);
			errorText.setText("Failed to query the database. Please contact a system administrator.");
		}
		
		if(!loginSuccessful){
			errorBox.setVisible(true);
			errorText.setText("User and/or Password are incorrect.");
			return;
		}

		try{
			App.setRoot("dashboard");
		}catch(IOException e){
			errorBox.setVisible(true);
			errorText.setText("Application error. Please contact a system administrator.");
		}
	}

	@FXML
	private void closeError(){
		errorBox.setVisible(false);
		errorText.setText(null);
	}
}
