package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.BillDAO;
import controller.BookingDAO;
import controller.CustomerDAO;
import controller.RoomDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Bill;
import model.Booking;
import model.Customer;
import model.Room;

public class RegisterBookingController implements Initializable {
	
	@FXML
	private ChoiceBox<Integer> roomNumberBox;
	@FXML
	private ChoiceBox<String> customerBox;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	
	private RoomDAO roomDAO;
	private CustomerDAO customerDAO;
	private BookingDAO bookingDAO;
	private BillDAO billDAO;
	
	public void initialize(URL location, ResourceBundle resources) {
		try {
			roomDAO = new RoomDAO();
			customerDAO = new CustomerDAO();
			bookingDAO = new BookingDAO();
			billDAO = new BillDAO();
			
			roomNumberBox.setItems(getRoomNumbers());
			if(roomNumberBox.getItems().size() > 0) {
				roomNumberBox.setValue(roomNumberBox.getItems().get(0));
			}
			
			customerBox.setItems(getCustomersCPF());
			if(customerBox.getItems().size() > 0) {
				customerBox.setValue(customerBox.getItems().get(0));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerBooking() throws SQLException {
		//Bill bill = new Bill();
		//bill.setTotalValue(0.0f);
		//int billID = billDAO.insertBill(bill);
		
		Booking booking = new Booking();
		//booking.setIdBill(billID);
		booking.setStartDate(java.sql.Date.valueOf(startDatePicker.getValue()));
		booking.setEndDate(java.sql.Date.valueOf(endDatePicker.getValue()));
		
		Room room = roomDAO.selectRoomByNumber(roomNumberBox.getValue());
		Customer bookingCustomer = customerDAO.selectCustomerByCPF(customerBox.getValue());
		booking.setIdRoom(room.getIdRoom());
		booking.setIdCustomer(bookingCustomer.getIdCustomer());
		
		bookingDAO.insertBooking(booking);
	}
	
	private ObservableList<Integer> getRoomNumbers() throws SQLException{
		ObservableList<Integer> numbers = FXCollections.observableArrayList();
		List<Room> rooms = roomDAO.selectAllRooms();
		
		for (Room room : rooms) {
			numbers.add(room.getRoomNumber());
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
	
}
