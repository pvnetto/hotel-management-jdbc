package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import controller.CustomerDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Address;
import model.Customer;
import model.Phone;

public class RegisterCustomerController implements Initializable {
	
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
	
	private CustomerDAO customerDAO;
	
	public void initialize(URL location, ResourceBundle resources) {
		try {
			customerDAO = new CustomerDAO();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerCustomer() {
		try {
			Customer customer = new Customer();
			customer.setName(nameTxt.getText());
			customer.setEmail(emailTxt.getText());
			customer.setCpf(cpfTxt.getText());
			customer.setRg(rgTxt.getText());
			customer.setBirthDate(java.sql.Date.valueOf(birthDatePicker.getValue()));
			customer.setAddress(new Address(streetTxt.getText(), boroughTxt.getText(), Integer.parseInt(numberTxt.getText()), cepTxt.getText(), address2Txt.getText()));
			customer.setPhone(new Phone(phoneTxt.getText()));
			customerDAO.insertCustomer(customer);
			
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
		phoneTxt.setText("");
	}
	
}
