package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Person;

public class PersonDAO extends AbstractDAO {
	
	public PersonDAO() throws SQLException {
		super();
	}

	// Prepares the statement and returns the ID, but doesn't complete the commit
	public int insertPerson(Person person) throws SQLException {
		String pessoaSQL = "INSERT INTO Pessoa values(default, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(pessoaSQL, Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, person.getName());
		pst.setString(2, person.getEmail());
		//pst.setString(2, customer.getCpf());
		pst.execute();
		
		ResultSet resultSet = pst.getGeneratedKeys();
		resultSet.next();
		int personID = resultSet.getInt(1);
		person.setIdPerson(personID);
		
		insertAddress(person);
		insertPhone(person);
		
		return personID;
	}
	
	private void insertAddress(Person person) throws SQLException {
		String enderecoSQL = "INSERT INTO Endereco values(default, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(enderecoSQL);
		pst.setInt(1, person.getIdPerson());
		pst.setString(2, person.getAddress().getStreet());
		pst.setString(3, person.getAddress().getBorough());
		pst.setString(4, person.getAddress().getCep());
		pst.setInt(5, person.getAddress().getNumber());
		pst.setString(6, person.getAddress().getAddress2());
		pst.execute();
	}
	
	private void insertPhone(Person person) throws SQLException {
		String foneSQL = "INSERT INTO Fone values(default, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(foneSQL);
		pst.setInt(1, person.getIdPerson());
		pst.setString(2, person.getPhone().getTelephoneNumber());
		pst.execute();
	}
	
	
}
