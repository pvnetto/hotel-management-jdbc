package controller;

import java.util.ArrayList;
import java.util.List;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Admin;

public class AdminDAO {
	
	private Connection connection;
	private PersonDAO personDAO;
	
	public AdminDAO() throws SQLException {
		connection = DBConnection.getConnection();
		connection.setAutoCommit(false);
		
		personDAO = new PersonDAO();
	}
	
	public void insertAdmin(Admin admin) throws SQLException {
		int personID = personDAO.insertPerson(admin);
		
		String juridicaSQL = "INSERT INTO Juridica values(default, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(juridicaSQL, Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, personID);
		pst.setString(2, admin.getCompanyName());
		pst.setString(3, admin.getCnpj());
		pst.execute();
		
		ResultSet resultSet = pst.getGeneratedKeys();
		resultSet.next();
		int juridicaID = resultSet.getInt(1);
		
		
		String adminSQL = "INSERT INTO Adm values(default, ?, ?)";
		pst = connection.prepareStatement(adminSQL);
		pst.setInt(1, personID);
		pst.setInt(2, juridicaID);
		pst.execute();
		
		connection.commit();
		pst.close();
		System.out.println("> Successfully inserted " + admin.getName() + " as admin!");
	}
	
	// TODO List all attributes
	public List<Admin> listAdmins() throws SQLException{
		PreparedStatement pst = null;
		ResultSet resultSet = null;
		
		String listPersonNameSQL = "SELECT a.idAdm, p.nome FROM Pessoa p, Adm a WHERE p.idPessoa = a.idPessoa";
		pst = connection.prepareStatement(listPersonNameSQL);
		
		List<Admin> admins = new ArrayList<Admin>();
		resultSet = pst.executeQuery();
		
		while(resultSet.next()) {
			Admin admin = new Admin();
			admin.setIdAdmin(resultSet.getInt("idAdm"));
			admin.setName(resultSet.getString("nome"));
			admins.add(admin);
		}
		
		return admins;
	}
	
}
