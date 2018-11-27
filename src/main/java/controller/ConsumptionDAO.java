package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
