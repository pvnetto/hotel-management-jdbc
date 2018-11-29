package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.RoomDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Room;
import model.RoomType;

public class RegisterRoomController implements Initializable {

	@FXML
	private TextField roomValueTxt;
	@FXML
	private TextField roomDescriptionTxt;
	@FXML
	private ChoiceBox<String> roomTypeBox;
	@FXML
	private TextField roomNumberTxt;
	
	private RoomDAO roomDAO;
	
	public void initialize(URL location, ResourceBundle resources) {		
		try {
			roomDAO = new RoomDAO();
			setRoomTypeChoiceBox();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerRoomClick() throws SQLException {
		Room room = new Room();
		RoomType selectedType = roomDAO.selectRoomTypeByDescription(roomTypeBox.getValue());
		room.setIdRoomType(selectedType.getIdRoomType());
		room.setRoomNumber(Integer.parseInt(roomNumberTxt.getText()));
		
		roomDAO.insertRoom(room);
		
		resetRoomForm();
	}
	
	private void resetRoomForm() {
		roomNumberTxt.setText("");
	}
	
	public void registerRoomTypeClick() {
		try {
			RoomType roomType = new RoomType(Float.parseFloat(roomValueTxt.getText()), roomDescriptionTxt.getText());
			roomDAO.insertRoomType(roomType);
			setRoomTypeChoiceBox();
			resetRoomTypeForm();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setRoomTypeChoiceBox() throws SQLException {
		roomTypeBox.setItems(getRoomDescriptions());
		
		if (roomTypeBox.getItems().size() > 0) {
			roomTypeBox.setValue(roomTypeBox.getItems().get(0));
		}
	}
	
	private void resetRoomTypeForm() {
		roomValueTxt.setText("");
		roomDescriptionTxt.setText("");
	}
	
	private ObservableList<String> getRoomDescriptions () throws SQLException {
		ObservableList<String> descriptions = FXCollections.observableArrayList();
		List<RoomType> roomTypes = roomDAO.selectRoomTypes();
		
		for (RoomType roomType : roomTypes) {
			descriptions.add(roomType.getDescription());
		}
		
		return descriptions;
	}
	
	
}
