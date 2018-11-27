package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import model.Bill;

public class BillDAO extends AbstractDAO {
	
	public BillDAO() throws SQLException {
		super();
	}

	public void insertBill(Bill bill) throws SQLException {
		String insertBillSQL = "INSERT INTO Conta values(default, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertBillSQL);
		pst.setInt(1, bill.getIdBooking());
		pst.setFloat(2, bill.getTotalValue());
		
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Successfully inserted the bill!");
	}
	
	public Bill selectBillByDateAndRoomNumber(int roomNumber, Date date) throws SQLException {
		String selectBillSQL = "SELECT c.idConta"
							 + " FROM Conta c, Reserva r, Quarto q"
							 + " WHERE r.idQuarto = q.idQuarto and q.numero = ? and ? BETWEEN r.dataInicio and r.dataFim and c.idReserva = r.idReserva";
		PreparedStatement pst = connection.prepareStatement(selectBillSQL);
		pst.setInt(1, roomNumber);
		pst.setDate(2, date);
		
		ResultSet resultSet = pst.executeQuery();
		resultSet.next();
		
		Bill bill = new Bill();
		bill.setIdBill(resultSet.getInt("idConta"));
		
		connection.commit();
		pst.close();
		
		return bill;
	}
	
}
