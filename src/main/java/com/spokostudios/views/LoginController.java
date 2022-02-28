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
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
	private LocalizationService ls;
	private DBService dbs;
	private Logger logger = Logger.getLogger(LoginController.class.getName());

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
		try {
			ls = LocalizationService.getInstance();
		} catch (Exception e) {
			errorBox.setVisible(true);
			errorText.setText(e.getMessage());
			e.printStackTrace();
		}

		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
		FileHandler fh = null;
		try {
			fh = new FileHandler("login_activity.txt");
		} catch (IOException e) {
			displayError("applicationError");
			e.printStackTrace();
		}
		logger.addHandler(fh);

		Locale locale = ls.getLocale();

		regionText.setText(locale.getDisplayCountry());

		userLabel.setText(ls.getText("login.userLabel"));
		passwordLabel.setText(ls.getText("login.passwordLabel"));
		regionLabel.setText(ls.getText("login.regionLabel"));
		loginButton.setText(ls.getText("login.loginButton"));
		errorButton.setText(ls.getText("errorButton"));
	}

	/**
	 * Attempt to log in the user.
	 */
	@FXML
	private void login(){
		boolean loginSuccessful = false;

		try{
			dbs = DBService.getInstance();
		}catch(SQLException e){
			displayError(ls.getText("applicationError"));
		}

		String user = userField.getText();
		String password = passwordField.getText();

		try{
			loginSuccessful = dbs.login(user, password);
		}catch(SQLException e){
			displayError("applicationError");
		}
		
		if(!loginSuccessful){
			logger.warning("Login attempt: "+userField.getText()+" | "+LocalDateTime.now().toString()+" | Failed");
			displayError("login.loginerror");
			return;
		}

		try{
			logger.info("Login attempt: "+userField.getText()+" | "+LocalDateTime.now().toString()+" | Successful");
			App.setRoot("dashboard");
		}catch(IOException e){
			displayError("applicationError");
			e.printStackTrace();
		}
	}

	/**
	 * Closes an error message
	 */
	@FXML
	private void closeError(){
		errorBox.setVisible(false);
		errorText.setText(null);
	}

	/**
	 * Display an error message associated with the login page
	 * @param message The key of the localization message to display
	 */
	private void displayError(String message){
		errorBox.setVisible(true);
		errorText.setText(ls.getText(message));
	}
}
