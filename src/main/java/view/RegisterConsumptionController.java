package view;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.BillDAO;
import controller.ConsumptionDAO;
import controller.RoomDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Bill;
import model.Consumption;
import model.Room;

public class RegisterConsumptionController implements Initializable {
	
	@FXML
	private ChoiceBox<Integer> roomNumberBox;
	@FXML
	private TextField valueTxt;
	@FXML
	private DatePicker consumptionDatePicker;
	@FXML
	private TextArea descriptionTxt;
	
	private BillDAO billDAO;
	private ConsumptionDAO consumptionDAO;
	private RoomDAO roomDAO;

	public void initialize(URL location, ResourceBundle resources) {
		try {
			billDAO = new BillDAO();
			consumptionDAO = new ConsumptionDAO();
			roomDAO = new RoomDAO();
			
			roomNumberBox.setItems(getRoomNumbers());
			if(roomNumberBox.getItems().size() > 0) {
				roomNumberBox.setValue(roomNumberBox.getItems().get(0));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerConsumption() {
		try {
			Consumption consumption = new Consumption(Float.parseFloat(valueTxt.getText()), descriptionTxt.getText());
			Bill consumptionBill = billDAO.selectBillByDateAndRoomNumber(roomNumberBox.getValue(), java.sql.Date.valueOf(consumptionDatePicker.getValue()));
			consumption.setIdBill(consumptionBill.getIdBill());
			
			consumptionDAO.insertConsumption(consumption);
			
			resetForm();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private ObservableList<Integer> getRoomNumbers() throws SQLException{
		ObservableList<Integer> numbers = FXCollections.observableArrayList();
		List<Room> rooms = roomDAO.selectAllBookedRooms();
		
		for (Room room : rooms) {
			numbers.add(room.getRoomNumber());
		}
		
		return numbers;
	}
	
	private void resetForm() {
		valueTxt.setText("");
		descriptionTxt.setText("");
	}
	
}
