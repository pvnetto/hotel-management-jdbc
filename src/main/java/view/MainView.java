package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView extends Application {
	
	private Stage first;
	private BorderPane mainScreen;
	
	@Override
	public void start(Stage primaryStage) {
		first = primaryStage;
		first.setTitle("Hotel management system");
		initMain();
	}
	
	private void initMain() {
		try {
			// Loading the FXML file and casting it to a BorderPane
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainView.class.getResource("MainScreen.fxml"));
			mainScreen = (BorderPane) loader.load();
			
			// Showing the scene that contains the layout
			Scene scene = new Scene(mainScreen, 800, 600);
			first.setScene(scene);
			first.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getFirst() {
		return first;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
