package com.spokostudios.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBService {
	private static DBService DBServiceInstance;
	private static Connection connection;

	private static final String LOGINSTATEMENT = "SELECT User_Id, User_Name FROM users WHERE User_Name = ? AND Password = ?";

	private DBService() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule", "sqlUser", "Passw0rd!");
	}

	public static DBService getInstance() throws SQLException {
		if(DBServiceInstance == null){
			DBServiceInstance = new DBService();
		}

		return DBServiceInstance;
	}

	public  boolean login(String user, String password) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(LOGINSTATEMENT);
		statement.setString(1, user);
		statement.setString(2, password);
		ResultSet rs = statement.executeQuery();

		if(rs.next()){
			return true;
		}

		return false;
	}
}
