package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import model.Consumption;

public class ConsumptionDAO extends AbstractDAO {
	
	public ConsumptionDAO() throws SQLException {
		super();
	}

	public void insertConsumption(Consumption consumption) throws SQLException {
		String insertConsumptionSQL = "INSERT INTO Consumo values(default, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(insertConsumptionSQL);
		pst.setInt(1, consumption.getIdBill());
		pst.setFloat(2, consumption.getValue());
		pst.setString(3, consumption.getDescription());
		
		pst.execute();
		connection.commit();
		pst.close();
		
		System.out.println("> Successfully added consumption!");
	}
	
	public Consumption selectConsumptionById(int id) throws SQLException {
		String selectConsumptionSQL = "SELECT c.idConsumo, c.idConta, c.valor, c.descricao FROM Consumo c WHERE c.idConsumo = ?";
		PreparedStatement pst = connection.prepareStatement(selectConsumptionSQL);
		pst.setInt(1, id);
		
		ResultSet resultSet = pst.executeQuery();
		
		Consumption consumption = new Consumption();
		if (resultSet.next()) {
			consumption.setValue(resultSet.getFloat("valor"));
			consumption.setDescription(resultSet.getString("descricao"));
			consumption.setIdConsumption(resultSet.getInt("idConsumo"));
			consumption.setIdBill(resultSet.getInt("idConta"));
		}
		
		connection.commit();
		pst.close();
		
		return consumption;
	}
	
	public List<Consumption> selectConsumptionsByBooking(Booking booking) throws SQLException {
		String selectConsumptionSQL = "SELECT c.idConsumo, c.idConta, c.valor, c.descricao FROM Reserva r, Conta k, Consumo c WHERE r.idReserva = ? and k.idConta = r.idConta and c.idConta = k.idConta";
		PreparedStatement pst = connection.prepareStatement(selectConsumptionSQL);
		pst.setInt(1, booking.getIdBooking());
		
		ResultSet resultSet = pst.executeQuery();
		
		List<Consumption> consumptions = new ArrayList<Consumption>();
		while(resultSet.next()) {
			Consumption consumption = new Consumption(resultSet.getFloat("valor"), resultSet.getString("descricao"));
			consumption.setIdConsumption(resultSet.getInt("idConsumo"));
			consumption.setIdBill(resultSet.getInt("idConta"));
			
			consumptions.add(consumption);
		}
		
		connection.commit();
		pst.close();
		
		return consumptions;
	}
	
	public void updateConsumption(Consumption newConsumption) throws SQLException {
		String updateConsumptionSQL = "UPDATE Consumo SET valor = ? WHERE idConsumo = ?";
		PreparedStatement pst = connection.prepareStatement(updateConsumptionSQL);
		pst.setInt(1, newConsumption.getIdConsumption());
		
		pst.execute();
		
		connection.commit();
		pst.close();
	}
	
	public void deleteConsumption(Consumption consumption) throws SQLException {
		String deleteConsumptionSQL = "DELETE FROM Consumo WHERE idConsumo = ?";
		PreparedStatement pst = connection.prepareStatement(deleteConsumptionSQL);
		pst.setInt(1, consumption.getIdConsumption());
		
		pst.execute();
		
		connection.commit();
		pst.close();
	}
}
