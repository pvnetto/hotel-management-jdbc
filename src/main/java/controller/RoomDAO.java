package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Room;

public class RoomDAO {
	
	private Connection connection;
	
	public RoomDAO() throws SQLException {
		connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
	}
	
	public void insertRoom(Room room) throws SQLException {
		String insertRoomSQL = "INSERT INTO Quarto VALUES(default, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRoomSQL);
		
		pst.setInt(1, room.getRoomNumber());
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Room #" + room.getRoomNumber() + " was inserted successfully.");
	}
	
	public Room selectRoomByNumber(int roomNumber) throws SQLException {
		String selectRoomSQL = "SELECT q.idQuarto, q.numero FROM Quarto q WHERE q.numero = ?";
		PreparedStatement pst = connection.prepareStatement(selectRoomSQL);
		pst.setInt(1, roomNumber);
		
		Room room = new Room();
		ResultSet resultSet = pst.executeQuery();
		resultSet.next();
		
		room.setIdRoom(resultSet.getInt("idQuarto"));
		room.setRoomNumber(resultSet.getInt("numero"));
		
		return room;
	}
	
}
