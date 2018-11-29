package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import model.Room;
import model.RoomType;

public class RoomDAO extends AbstractDAO {
	
	public RoomDAO() throws SQLException {
		super();
	}

	public void insertRoomType(RoomType roomType) throws SQLException {
		String insertRoomTypeSQL = "INSERT INTO TipoQuarto VALUES(default, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertRoomTypeSQL);
		pst.setFloat(1, roomType.getDailyPrice());
		pst.setString(2, roomType.getDescription());
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Room Type " + roomType.getDescription() + " was succesfully added!");
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
	
	public List<Room> selectAllRoomsRentedByCustomer(int customerID) throws SQLException{
		List<Room> rooms = new ArrayList<Room>();
		
		String selectRoomsSQL = "SELECT q.idQuarto, q.idTipoQuarto, q.numero FROM Quarto q, Reserva r WHERE r.idQuarto = q.idQuarto and r.idCliente = ?";
		PreparedStatement pst = connection.prepareStatement(selectRoomsSQL);
		pst.setInt(1, customerID);
		ResultSet resultSet = pst.executeQuery();
		
		while(resultSet.next()) {
			Room room = new Room();
			room.setIdRoom(resultSet.getInt("idQuarto"));
			room.setIdRoomType(resultSet.getInt("idTipoQuarto"));
			room.setRoomNumber(resultSet.getInt("numero"));
			rooms.add(room);
		}
		
		connection.commit();
		pst.close();
		
		return rooms;
	}
	
	public RoomType selectRoomTypeByDescription(String description) throws SQLException {
		String selectRoomTypeSQL = "SELECT t.idTipoQuarto, t.valorDiaria, t.descricao FROM TipoQuarto t WHERE t.descricao = ?";
		PreparedStatement pst = connection.prepareStatement(selectRoomTypeSQL);
		pst.setString(1, description);
		
		ResultSet resultSet = pst.executeQuery();
		resultSet.next();
		
		RoomType roomType = new RoomType(resultSet.getFloat("valorDiaria"), resultSet.getString("descricao"));
		roomType.setIdRoomType(resultSet.getInt("idTipoQuarto"));
		
		return roomType;
	}
	
	public List<Room> selectAllRooms() throws SQLException {
		List<Room> rooms = new ArrayList<Room>();
		
		String selectRoomsSQL = "SELECT q.idQuarto, q.idTipoQuarto, q.numero FROM Quarto q";
		PreparedStatement pst = connection.prepareStatement(selectRoomsSQL);
		ResultSet resultSet = pst.executeQuery();
		
		while(resultSet.next()) {
			Room room = new Room();
			room.setIdRoom(resultSet.getInt("idQuarto"));
			room.setIdRoomType(resultSet.getInt("idTipoQuarto"));
			room.setRoomNumber(resultSet.getInt("numero"));
			rooms.add(room);
		}
		
		connection.commit();
		pst.close();
		
		return rooms;
	}
	
	public List<Room> selectAllBookedRooms() throws SQLException {
		List<Room> rooms = new ArrayList<Room>();
		
		String selectRoomsSQL = "SELECT q.idQuarto, q.idTipoQuarto, q.numero FROM Quarto q, Reserva r WHERE r.idQuarto = q.idQuarto";
		PreparedStatement pst = connection.prepareStatement(selectRoomsSQL);
		ResultSet resultSet = pst.executeQuery();
		
		while(resultSet.next()) {
			Room room = new Room();
			room.setIdRoom(resultSet.getInt("idQuarto"));
			room.setIdRoomType(resultSet.getInt("idTipoQuarto"));
			room.setRoomNumber(resultSet.getInt("numero"));
			rooms.add(room);
		}
		
		connection.commit();
		pst.close();
		
		return rooms;
	}
	
	public List<RoomType> selectRoomTypes() throws SQLException {
		List<RoomType> roomTypes = new ArrayList<RoomType>();
		
		String selectRoomTypesSQL = "SELECT t.idTipoQuarto, t.valorDiaria, t.descricao FROM TipoQuarto t";
		PreparedStatement pst = connection.prepareStatement(selectRoomTypesSQL);
		ResultSet resultSet = pst.executeQuery();
		
		while(resultSet.next()) {
			RoomType roomType = new RoomType(resultSet.getFloat("valorDiaria"), resultSet.getString("descricao"));
			roomType.setIdRoomType(resultSet.getInt("idTipoQuarto"));
			roomTypes.add(roomType);
		}
		
		connection.commit();
		pst.close();
		
		return roomTypes;
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
