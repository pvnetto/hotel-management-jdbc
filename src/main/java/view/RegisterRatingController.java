package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.event.ChangeListener;

import controller.CustomerDAO;
import controller.RatingDAO;
import controller.RoomDAO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import model.Customer;
import model.Rating;
import model.Room;

public class RegisterRatingController implements Initializable {

	@FXML
	private ChoiceBox<String> customerBox;
	@FXML
	private ChoiceBox<Integer> roomNumberBox;
	@FXML
	private ChoiceBox<Integer> starsBox;
	@FXML
	private TextArea commentTxt;
	
	private CustomerDAO customerDAO;
	private RoomDAO roomDAO;
	private RatingDAO ratingDAO;
	
	public void initialize(URL location, ResourceBundle resources) {
		try {
			customerDAO = new CustomerDAO();
			roomDAO = new RoomDAO();
			ratingDAO = new RatingDAO();
			
			starsBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
			
			customerBox.setItems(getCustomersCPF());
			if(customerBox.getItems().size() > 0) {
				customerBox.setValue(customerBox.getItems().get(0));
			}
			
			updateRoomForm();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		customerBox.setOnAction(event -> {
			try {
				updateRateableRooms(event);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	public void registerRating() throws SQLException {
		Rating rating = new Rating(starsBox.getValue(), commentTxt.getText());
		Customer ratingCustomer = customerDAO.selectCustomerByCPF(customerBox.getValue());
		Room ratedRoom = roomDAO.selectRoomByNumber(roomNumberBox.getValue());
		rating.setIdCustomer(ratingCustomer.getIdCustomer());
		rating.setIdRoom(ratedRoom.getIdRoom());
		ratingDAO.insertRating(rating);
		resetForm();
	}
	
	@FXML
	public void updateRateableRooms(ActionEvent event) throws SQLException {
		updateRoomForm();
	}
	
	private void updateRoomForm() throws SQLException {
		Customer customer = customerDAO.selectCustomerByCPF(customerBox.getValue());
		
		roomNumberBox.setItems(getRoomNumbers(customer.getIdCustomer()));
		if(roomNumberBox.getItems().size() > 0) {
			roomNumberBox.setValue(roomNumberBox.getItems().get(0));
		}
	}
	
	private ObservableList<Integer> getRoomNumbers(int customerID) throws SQLException{
		ObservableList<Integer> numbers = FXCollections.observableArrayList();
		List<Room> rooms = roomDAO.selectAllRoomsRentedByCustomer(customerID);
		
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
	
	private void resetForm() {
		commentTxt.setText("");
	}
	
}
