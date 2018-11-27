package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Room;
import model.RoomType;

public class RoomDAO extends AbstractDAO {
	
	public RoomDAO() throws SQLException {
		super();
	}

	public void insertRoomType(RoomType roomType) throws SQLException {
		String insertRoomTypeSQL = "INSERT INTO TipoQuarto VALUES(default, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRoomTypeSQL);
		pst.setFloat(1, roomType.getDailyPrice());
		pst.execute();
		connection.commit();
		pst.close();
	}
	
	public void insertRoom(Room room) throws SQLException {
		String insertRoomSQL = "INSERT INTO Quarto VALUES(default, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRoomSQL);
		
		pst.setInt(1, room.getIdRoomType());
		pst.setInt(2, room.getRoomNumber());
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
