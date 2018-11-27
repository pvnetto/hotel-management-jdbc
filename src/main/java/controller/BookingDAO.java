package controller;

import java.awt.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Booking;

public class BookingDAO extends AbstractDAO {
	
	public BookingDAO() throws SQLException {
		super();
	}

	public void insertBooking(Booking booking) throws SQLException {
		String insertBookingSQL = "INSERT INTO Reserva values(default, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertBookingSQL);
		pst.setInt(1, booking.getIdCustomer());
		pst.setInt(2, booking.getIdRoom());
		pst.setDate(3, booking.getStartDate());
		pst.setDate(4, booking.getEndDate());
		
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Succesfully inserted booking!");
	}
	
	public Booking selectBookingByDateAndRoomNumber(int roomNumber, Date currentDate) throws SQLException {
		String selectBooking = "SELECT r.idReserva FROM Reserva r, Quarto q WHERE r.idQuarto = q.idQuarto and q.numero = ? and ? BETWEEN r.dataInicio and r.dataFim";
		PreparedStatement pst = connection.prepareStatement(selectBooking);
		pst.setInt(1, roomNumber);
		pst.setDate(2, currentDate);
		
		ResultSet resultSet = pst.executeQuery();
		resultSet.next();
		
		Booking booking = new Booking();
		booking.setIdBooking(resultSet.getInt("idReserva"));
		
		connection.commit();
		pst.close();
		
		return booking;
	}
	
}
