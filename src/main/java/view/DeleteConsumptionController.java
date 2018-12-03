package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.BookingDAO;
import controller.ConsumptionDAO;
import controller.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import model.Booking;
import model.Consumption;
import model.Customer;

public class DeleteConsumptionController implements Initializable {
	
	@FXML
	private ChoiceBox<String> customerBox;
	@FXML
	private ChoiceBox<Integer> bookingBox;
	@FXML
	private ChoiceBox<Integer> consumptionBox;
	
	private CustomerDAO customerDAO;
	private BookingDAO bookingDAO;
	private ConsumptionDAO consumptionDAO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			customerDAO = new CustomerDAO();
			bookingDAO = new BookingDAO();
			consumptionDAO = new ConsumptionDAO();
			
			customerBox.setItems(getCustomersCPF());
			if(customerBox.getItems().size() > 0) {
				customerBox.setValue(customerBox.getItems().get(0));
			}
			
			updateBookingForm();
			updateConsumptionForm();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		customerBox.setOnAction(event -> {
			try {
				updateBookingsBox(event);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		consumptionBox.setOnAction(event -> {
			try {
				updateConsumptionBox(event);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	public void deleteConsumption() throws SQLException {
		Consumption consumption = consumptionDAO.selectConsumptionById(consumptionBox.getValue());
		consumptionDAO.deleteConsumption(consumption);
		
		customerBox.setItems(getCustomersCPF());
		if(customerBox.getItems().size() > 0) {
			customerBox.setValue(customerBox.getItems().get(0));
		}
		
		updateBookingForm();
		updateConsumptionForm();
	}
	
	private ObservableList<String> getCustomersCPF() throws SQLException {
		ObservableList<String> customersCPF = FXCollections.observableArrayList();
		List<Customer> customers = customerDAO.selectAllCustomers();
		
		for (Customer customer : customers) {
			customersCPF.add(customer.getCpf());
		}
		
		return customersCPF;
	}
	
	@FXML
	public void updateBookingsBox(ActionEvent event) throws SQLException {
		updateBookingForm();
	}
	
	private void updateBookingForm() throws SQLException {
		Customer customer = customerDAO.selectCustomerByCPF(customerBox.getValue());
		
		bookingBox.setItems(getBookingsID(customer));
		if(bookingBox.getItems().size() > 0) {
			bookingBox.setValue(bookingBox.getItems().get(0));
		}
	}
	
	private ObservableList<Integer> getBookingsID(Customer customer) throws SQLException{
		ObservableList<Integer> numbers = FXCollections.observableArrayList();
		List<Booking> bookings = bookingDAO.selectBookingsByCustomer(customer);
		
		for (Booking booking : bookings) {
			numbers.add(booking.getIdBooking());
		}
		
		return numbers;
	}
	
	@FXML
	public void updateConsumptionBox(ActionEvent event) throws SQLException {
		updateConsumptionForm();
	}
	
	private void updateConsumptionForm() throws SQLException {
		Booking booking = bookingDAO.selectBookingByID(bookingBox.getValue());

		consumptionBox.setItems(getConsumptionsID(booking));
		if(consumptionBox.getItems().size() > 0) {
			consumptionBox.setValue(consumptionBox.getItems().get(0));
		}
	}
	
	private ObservableList<Integer> getConsumptionsID(Booking booking) throws SQLException{
		ObservableList<Integer> numbers = FXCollections.observableArrayList();
		List<Consumption> consumptions = consumptionDAO.selectConsumptionsByBooking(booking);
		
		for (Consumption consumption : consumptions) {
			numbers.add(consumption.getIdConsumption());
		}
		
		return numbers;
	}

}
