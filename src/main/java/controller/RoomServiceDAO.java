package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.RoomService;

public class RoomServiceDAO extends AbstractDAO {
	
	public RoomServiceDAO() throws SQLException {
		super();
	}

	public void insertRoomService(RoomService roomService) throws SQLException {		
		String insertRoomServiceSQL = "INSERT INTO Servico VALUES(default, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRoomServiceSQL);
		pst.setInt(1, roomService.getRoom().getIdRoom());
		pst.setInt(2, roomService.getEmployee().getIdEmployee());
		pst.setString(3, roomService.getDescription());
		pst.setString(4, roomService.getServiceStatus().name());
		
		pst.execute();
		connection.commit();
		pst.close();
		System.out.println("> Room Service successfully added to room #" + roomService.getRoom().getRoomNumber() + ", employee " + roomService.getEmployee().getName());
	}
	
	public void viewPendingServices() throws SQLException {
		String viewPendingServicesSQL = "SELECT * FROM vwServicosPendentes";
		PreparedStatement pst = connection.prepareStatement(viewPendingServicesSQL);
		
		ResultSet resultSet = pst.executeQuery();
		
		System.out.println("> Listing pending services: ");
		System.out.println("=========");
		while(resultSet.next()) {
			System.out.println("Quarto #" + resultSet.getInt("NumeroQuarto"));
			System.out.println("Descrição: " + resultSet.getString("Descrição"));
			System.out.println("Status: " + resultSet.getString("Status"));
			System.out.println("=========");
		}
		
		connection.commit();
		pst.close();
		
		System.out.println("> Finished printing vwServicosPendentes");
	}
	
}
