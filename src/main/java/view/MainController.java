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
			//Scene scene = new Scene(customerScreen, 800, 500);
			Scene scene = new Scene(customerScreen);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(screenTitle);
			stage.show();
			
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
		LoadScene("RegisterRoomServiceScreen.fxml", "Register room service");
	}
	
	public void ClickInsertBooking() {
		LoadScene("RegisterBookingScreen.fxml", "Register booking");
	}
	
	public void ClickInsertConsumption() {
		LoadScene("RegisterConsumptionScreen.fxml", "Register consumption");
	}
	
	public void ClickInsertRating() {
		LoadScene("RegisterRatingScreen.fxml", "Register rating");
	}
	
}
