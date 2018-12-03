package controller;

import java.awt.print.Book;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import model.Customer;

public class BookingDAO extends AbstractDAO {
	
	public BookingDAO() throws SQLException {
		super();
	}

	public void insertBooking(Booking booking) throws SQLException {
		String insertBookingSQL = "INSERT INTO Reserva values(default, ?, ?, null, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertBookingSQL);
		pst.setInt(1, booking.getIdCustomer());
		pst.setInt(2, booking.getIdRoom());
		//pst.setInt(3, booking.getIdBill());
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
	
	public List<Booking> selectBookingsByCustomer(Customer customer) throws SQLException {
		String selectBookingsSQL = "SELECT r.idReserva, r.idCliente, r.idConta, r.idQuarto, r.dataInicio, r.dataFim FROM Reserva r WHERE r.idCliente = ?";
		PreparedStatement pst = connection.prepareStatement(selectBookingsSQL);
		pst.setInt(1, customer.getIdCustomer());
		
		ResultSet resultSet = pst.executeQuery();
		
		List<Booking> bookings = new ArrayList<Booking>();
		
		while(resultSet.next()) {
			Booking booking = new Booking();
			booking.setIdBooking(resultSet.getInt("idReserva"));
			booking.setIdCustomer(resultSet.getInt("idCliente"));
			booking.setIdBill(resultSet.getInt("idConta"));
			booking.setIdRoom(resultSet.getInt("idQuarto"));
			booking.setStartDate(resultSet.getDate("dataInicio"));
			booking.setEndDate(resultSet.getDate("dataFim"));
			
			bookings.add(booking);
		}
		
		connection.commit();
		pst.close();
		
		return bookings;
	}
	
	public Booking selectBookingByID(int bookingID) throws SQLException {
		String selectBookingSQL = "SELECT r.idReserva, r.idCliente, r.idConta, r.idQuarto, r.dataInicio, r.dataFim FROM Reserva r WHERE r.idReserva = ?";
		PreparedStatement pst = connection.prepareStatement(selectBookingSQL);
		pst.setInt(1, bookingID);
		
		ResultSet resultSet = pst.executeQuery();
		
		Booking booking = new Booking();
		if(resultSet.next()) {
			booking.setIdBooking(resultSet.getInt("idReserva"));
			booking.setIdCustomer(resultSet.getInt("idCliente"));
			booking.setIdBill(resultSet.getInt("idConta"));
			booking.setIdRoom(resultSet.getInt("idQuarto"));
			booking.setStartDate(resultSet.getDate("dataInicio"));
			booking.setEndDate(resultSet.getDate("dataFim"));
		}
		
		return booking;
	}
	
}
