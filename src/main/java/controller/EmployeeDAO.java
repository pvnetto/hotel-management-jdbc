package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDAO extends AbstractDAO {

	private PersonDAO personDAO;
	
	public EmployeeDAO() throws SQLException {
		personDAO = new PersonDAO();
	}
	
	public void insertEmployee(Employee employee) throws SQLException {
		int personID = personDAO.insertPerson(employee);
		
		String fisicaSQL = "INSERT INTO Fisica values(default, ?, ?, ?, ?)";
		PreparedStatement pst = connection.prepareStatement(fisicaSQL, Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, personID);
		pst.setString(2, employee.getCpf());
		pst.setString(3, employee.getRg());
		pst.setDate(4, employee.getBirthDate());
		pst.execute();
		
		ResultSet resultSet = pst.getGeneratedKeys();
		resultSet.next();
		int fisicaID = resultSet.getInt(1);
		
		String empregadoSQL = "INSERT INTO Funcionario values(default, ?, ?)";
		pst = connection.prepareStatement(empregadoSQL);
		pst.setInt(1, personID);
		pst.setInt(2, fisicaID);
		pst.execute();
		
		connection.commit();
		pst.close();
		System.out.println("> Successfully inserted " + employee.getName() + " as an employee!");
	}
	
	public List<Employee> selectAllEmployees() throws SQLException {
		String selectAllEmployeesSQL = "SELECT e.idFuncionario, e.idPessoa, e.idFisica, p.nome, p.email, f.cpf, f.rg, f.dataNascimento FROM Funcionario e, Fisica f, Pessoa p WHERE e.idPessoa = p.idPessoa and e.idFisica = f.idFisica";
		PreparedStatement pst = connection.prepareStatement(selectAllEmployeesSQL);
		
		ResultSet resultSet = pst.executeQuery();
		
		List<Employee> employees = new ArrayList<Employee>();
		while(resultSet.next()) {
			Employee employee = new Employee();
			employee.setIdEmployee(resultSet.getInt("idFuncionario"));
			employee.setIdPerson(resultSet.getInt("idPessoa"));
			employee.setIdPhysicalPerson(resultSet.getInt("idFisica"));
			employee.setName(resultSet.getString("nome"));
			employee.setEmail(resultSet.getString("email"));
			employee.setCpf(resultSet.getString("cpf"));
			employee.setRg(resultSet.getString("rg"));
			employee.setBirthDate(resultSet.getDate("dataNascimento"));
			
			employees.add(employee);
		}
		
		return employees;
	}
	
	public Employee selectEmployeeByCPF(String cpf) throws SQLException {
		Employee employee = new Employee();
		
		String selectEmployeeSQL = "SELECT e.idFuncionario, e.idPessoa, e.idFisica FROM Funcionario e, Fisica f WHERE f.cpf = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(selectEmployeeSQL);
		preparedStatement.setString(1, cpf);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		int pessoaID = resultSet.getInt("idPessoa");
		int fisicaID = resultSet.getInt("idFisica");
		employee.setIdEmployee(resultSet.getInt("idFuncionario"));
		employee.setIdPerson(pessoaID);
		employee.setIdPhysicalPerson(fisicaID);
		
		String selectPessoa = "SELECT p.nome, p.email FROM Pessoa p WHERE p.idPessoa = ?";
		preparedStatement = connection.prepareStatement(selectPessoa);
		preparedStatement.setInt(1, pessoaID);
		resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		employee.setName(resultSet.getString("nome"));
		employee.setEmail(resultSet.getString("email"));
		
		String selectFisica = "SELECT f.cpf, f.rg, f.dataNascimento FROM Fisica f WHERE f.idFisica = ?";
		preparedStatement = connection.prepareStatement(selectFisica);
		preparedStatement.setInt(1, fisicaID);
		resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		employee.setCpf(resultSet.getString("cpf"));
		employee.setRg(resultSet.getString("rg"));
		employee.setBirthDate(resultSet.getDate("dataNascimento"));
		
		connection.commit();
		preparedStatement.close();
		return employee;
	}
	
}
