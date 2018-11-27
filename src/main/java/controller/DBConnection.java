package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Handles connection to a postgresql database.
public class DBConnection {
	// The URL, user and pass MUST match the ones specified in pgAdmin.
	public static String url = "jdbc:postgresql://localhost/hotel_bd";
	public static String user = "postgres";
	public static String pass = "postgres";
	
	private static Connection connection;
	
	// Gets the current connection to the database, or creates one if there isn't.
	public static Connection getConnection() throws SQLException {
		if(connection == null) {
			connection = Connect();
		}
		return connection;
	}
	
	// Creates and returns a new Connection using the url, user and pass.
	private static Connection Connect() throws SQLException {
		try {
			connection = DriverManager.getConnection(url, user, pass);
			System.out.println("> Database connected!");
		}
		catch (SQLException ex) {
			System.out.println("> Error connecting to the database...");
		}
		
		return connection;
	}
	
}
