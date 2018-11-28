package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import controller.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Address;
import model.Employee;
import model.Phone;

public class RegisterEmployeeController implements Initializable {
	@FXML
	private TextField nameTxt;
	@FXML
	private TextField emailTxt;
	@FXML
	private TextField cpfTxt;
	@FXML
	private TextField rgTxt;
	@FXML
	private DatePicker birthDatePicker;
	@FXML
	private TextField streetTxt;
	@FXML
	private TextField boroughTxt;
	@FXML
	private TextField numberTxt;
	@FXML
	private TextField cepTxt;
	@FXML
	private TextField address2Txt;
	@FXML
	private TextField phoneTxt;
	
	private EmployeeDAO employeeDAO;
	
	public void initialize(URL location, ResourceBundle resources) {
		try {
			employeeDAO = new EmployeeDAO();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerEmployee() {
		try {
			Employee employee = new Employee();
			employee.setName(nameTxt.getText());
			employee.setEmail(emailTxt.getText());
			employee.setCpf(cpfTxt.getText());
			employee.setRg(rgTxt.getText());
			employee.setBirthDate(java.sql.Date.valueOf(birthDatePicker.getValue()));
			employee.setAddress(new Address(streetTxt.getText(), boroughTxt.getText(), Integer.parseInt(numberTxt.getText()), cepTxt.getText(), address2Txt.getText()));
			employee.setPhone(new Phone(phoneTxt.getText()));
			employeeDAO.insertEmployee(employee);
			
			resetFields();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void resetFields() {
		nameTxt.setText("");
		emailTxt.setText("");
		cpfTxt.setText("");
		rgTxt.setText("");
		streetTxt.setText("");
		boroughTxt.setText("");
		numberTxt.setText("");
		cepTxt.setText("");
		address2Txt.setText("");
	}
}
