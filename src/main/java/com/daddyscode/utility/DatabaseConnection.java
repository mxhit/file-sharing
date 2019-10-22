package com.daddyscode.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.daddyscode.constant.Credentials;

public class DatabaseConnection implements Credentials {
	private static DatabaseConnection database;
	Connection connection;
	
	public static DatabaseConnection getDatabase() {
		if (database == null) {
			database = new DatabaseConnection();
		}
		
		return database;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null || connection.isClosed()) {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PWD);
		}
		
		return connection;
	}
}
