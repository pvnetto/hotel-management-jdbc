package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Customer;

public class CustomerDAO extends AbstractDAO {

	private PersonDAO personDAO;
	
	public CustomerDAO() throws SQLException {
		super();
		personDAO = new PersonDAO();
	}
	
	public void insertCustomer(Customer customer) throws SQLException{
		int personID = personDAO.insertPerson(customer);
		
		String fisicaSQL = "INSERT INTO Fisica values(default, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(fisicaSQL, Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, personID);
		pst.setString(2, customer.getCpf());
		pst.setString(3, customer.getRg());
		pst.setDate(4, customer.getBirthDate());
		pst.execute();
		
		ResultSet resultSet = pst.getGeneratedKeys();
		resultSet.next();
		int fisicaID = resultSet.getInt(1);
		
		String clienteSQL = "INSERT INTO Cliente values(default, ?, ?)";
		pst = connection.prepareStatement(clienteSQL);
		pst.setInt(1, personID);
		pst.setInt(2, fisicaID);
		pst.execute();
		
		connection.commit();
		pst.close();
		System.out.println("> Successfully inserted " + customer.getName() + " as a customer!");
	}
	
	public Customer selectCustomerByCPF(String cpf) throws SQLException {
		Customer customer = new Customer();
		String selectCustomerByCpfSQL = "SELECT c.idCliente FROM Cliente c, Fisica f WHERE c.idPessoa = f.idPessoa and f.cpf = ?";
		PreparedStatement pst = connection.prepareStatement(selectCustomerByCpfSQL);
		pst.setString(1, cpf);
		
		ResultSet resultSet = pst.executeQuery();
		resultSet.next();
		
		customer.setIdCustomer(resultSet.getInt("idCliente"));
		
		connection.commit();
		pst.close();
		
		return customer;
	}
	
}
