package controller;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO {
	protected Connection connection;
	
	public AbstractDAO() throws SQLException {
		connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
	}
	
}
