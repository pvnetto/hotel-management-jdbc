package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import model.Bill;

public class BillDAO extends AbstractDAO {
	
	public BillDAO() throws SQLException {
		super();
	}

	public int insertBill(Bill bill) throws SQLException {
		String insertBillSQL = "INSERT INTO Conta values(default, ?)";
		PreparedStatement pst = connection.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS);
		pst.setFloat(1, bill.getTotalValue());
		pst.execute();
		
		ResultSet resultSet = pst.getGeneratedKeys();
		resultSet.next();
		
		int billID = resultSet.getInt(1);
		
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Successfully inserted the bill!");
		return billID;
	}
	
	public Bill selectBillByDateAndRoomNumber(int roomNumber, Date date) throws SQLException {
		String selectBillSQL = "SELECT c.idConta"
							 + " FROM Conta c, Reserva r, Quarto q"
							 + " WHERE r.idQuarto = q.idQuarto and q.numero = ? and ? BETWEEN r.dataInicio and r.dataFim and c.idConta = r.idConta";
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
