package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.RoomService;

public class RoomServiceDAO {
	
	private Connection connection;
	
	public RoomServiceDAO() throws SQLException {
		connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
	}
	
	public void insertRoomService(RoomService roomService) throws SQLException {		
		String insertRoomServiceSQL = "INSERT INTO Servico VALUES(default, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRoomServiceSQL);
		pst.setInt(1, roomService.getRoom().getIdRoom());
		pst.setInt(2, roomService.getAdmin().getIdAdmin());
		pst.setInt(3, roomService.getEmployee().getIdPerson());
		pst.setInt(4, roomService.getEmployee().getIdPhysicalPerson());
		pst.setInt(5, roomService.getEmployee().getIdEmployee());
		pst.setString(6, roomService.getDescription());
		
		pst.execute();
		connection.commit();
		pst.close();
		System.out.println("> Room Service successfully added to room #" + roomService.getRoom().getRoomNumber() + ", employee " + roomService.getEmployee().getName());
	}
	
}
