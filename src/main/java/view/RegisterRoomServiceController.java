package view;

import java.net.URL;
import java.security.Provider.Service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;

import controller.EmployeeDAO;
import controller.RoomDAO;
import controller.RoomServiceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import model.Employee;
import model.Room;
import model.RoomService;
import model.ServiceStatus;

public class RegisterRoomServiceController implements Initializable {
	
	@FXML
	private ChoiceBox<Integer> roomNumberBox;
	@FXML
	private ChoiceBox<String> employeeBox;
	@FXML
	private ChoiceBox<ServiceStatus> statusBox;
	@FXML
	private TextArea descriptionTxt;
	
	private EmployeeDAO employeeDAO;
	private RoomDAO roomDAO;
	private RoomServiceDAO roomServiceDAO;

	public void initialize(URL location, ResourceBundle resources) {
		try {
			employeeDAO = new EmployeeDAO();
			roomDAO = new RoomDAO();
			roomServiceDAO = new RoomServiceDAO();
			
			roomNumberBox.setItems(getRoomNumbers());
			if(roomNumberBox.getItems().size() > 0) {
				roomNumberBox.setValue(roomNumberBox.getItems().get(0));
			}
			
			statusBox.setItems(getStatusTypes());
			if(statusBox.getItems().size() > 0) {
				statusBox.setValue(statusBox.getItems().get(0));
			}
			
			employeeBox.setItems(getEmployeesCPF());
			if(employeeBox.getItems().size() > 0) {
				employeeBox.setValue(employeeBox.getItems().get(0));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerService() throws SQLException {
		RoomService roomService = new RoomService(roomDAO.selectRoomByNumber(roomNumberBox.getValue()),
												  employeeDAO.selectEmployeeByCPF(employeeBox.getValue()),
												  descriptionTxt.getText(),
												  statusBox.getValue());
		roomServiceDAO.insertRoomService(roomService);
		
		resetForm();
	}
	
	private ObservableList<Integer> getRoomNumbers() throws SQLException{
		ObservableList<Integer> numbers = FXCollections.observableArrayList();
		List<Room> rooms = roomDAO.selectAllRooms();
		
		for (Room room : rooms) {
			numbers.add(room.getRoomNumber());
		}
		
		return numbers;
	}
	
	private ObservableList<ServiceStatus> getStatusTypes() {
		ObservableList<ServiceStatus> statusTypes = FXCollections.observableArrayList();
		List<ServiceStatus> statusList = new ArrayList<ServiceStatus>(EnumSet.allOf(ServiceStatus.class));
		
		for (ServiceStatus serviceStatus : statusList) {
			statusTypes.add(serviceStatus);
		}
		
		return statusTypes;
	}
	
	private ObservableList<String> getEmployeesCPF() throws SQLException {
		ObservableList<String> employeesCPF = FXCollections.observableArrayList();
		List<Employee> employees = employeeDAO.selectAllEmployees();
		
		
		for (Employee employee : employees) {
			employeesCPF.add(employee.getCpf());
		}
		
		return employeesCPF;
	}
	
	private void resetForm() {
		descriptionTxt.setText("");
	}
	
}
