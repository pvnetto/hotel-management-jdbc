package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController {
	
	private void LoadScene(String fxmlPath, String screenTitle) {
		try {
			// Loading the FXML file and casting it to a BorderPane
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainView.class.getResource(fxmlPath));
			BorderPane customerScreen = (BorderPane) loader.load();
			
			// Showing the scene that contains the layout
			Scene scene = new Scene(customerScreen, 600, 400);
			Stage customerStage = new Stage();
			customerStage.setScene(scene);
			customerStage.setTitle(screenTitle);
			customerStage.show();
			
			scene.setRoot(customerScreen);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ClickInsertCustomer() {
		LoadScene("RegisterCustomerScreen.fxml", "Register customer");
	}
	
	public void ClickInsertEmployee() {
		LoadScene("RegisterEmployeeScreen.fxml", "Register employee");
	}
	
	
	public void ClickInsertRoom() {
		LoadScene("RegisterRoomScreen.fxml", "Register room");
	}
	
	public void ClickInsertRoomService() {
		
	}
	
	public void ClickInsertBooking() {
		
	}
	
	public void ClickInsertConsumption() {
		
	}
	
	public void ClickInsertRating() {
		
	}
	
}
