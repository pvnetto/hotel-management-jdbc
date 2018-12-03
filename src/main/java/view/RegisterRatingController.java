package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


import controller.BookingDAO;
import controller.CustomerDAO;
import controller.RatingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import model.Booking;
import model.Customer;
import model.Rating;

public class RegisterRatingController implements Initializable {

	@FXML
	private ChoiceBox<String> customerBox;
	@FXML
	private ChoiceBox<Integer> bookingBox;
	@FXML
	private ChoiceBox<Integer> starsBox;
	@FXML
	private TextArea commentTxt;
	@FXML
	private DatePicker ratingDatePicker;
	
	private CustomerDAO customerDAO;
	private BookingDAO bookingDAO;
	private RatingDAO ratingDAO;
	
	public void initialize(URL location, ResourceBundle resources) {
		try {
			customerDAO = new CustomerDAO();
			bookingDAO = new BookingDAO();
			ratingDAO = new RatingDAO();
			
			starsBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
			
			customerBox.setItems(getCustomersCPF());
			if(customerBox.getItems().size() > 0) {
				customerBox.setValue(customerBox.getItems().get(0));
			}
			
			updateBookingForm();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		customerBox.setOnAction(event -> {
			try {
				updateRateableBookings(event);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	public void registerRating() throws SQLException {
		Booking booking = bookingDAO.selectBookingByID(bookingBox.getValue());
		Rating rating = new Rating(starsBox.getValue(), commentTxt.getText());
		rating.setRatingDate(java.sql.Date.valueOf(ratingDatePicker.getValue()));
		rating.setIdBooking(booking.getIdBooking());
		
		ratingDAO.insertRating(rating);
		resetForm();
	}
	
	@FXML
	public void updateRateableBookings(ActionEvent event) throws SQLException {
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
	
	private ObservableList<String> getCustomersCPF() throws SQLException {
		ObservableList<String> customersCPF = FXCollections.observableArrayList();
		List<Customer> customers = customerDAO.selectAllCustomers();
		
		for (Customer customer : customers) {
			customersCPF.add(customer.getCpf());
		}
		
		return customersCPF;
	}
	
	private void resetForm() {
		commentTxt.setText("");
	}
	
}
