package application;

/*
 * 
 * Program Launch to MainController
 * 
 */


import controller.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Main extends Application {
	
	public static MainViewController mainViewController;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view\\MainView.fxml"));
			Pane root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Shadow FTP");
			primaryStage.setOnCloseRequest(event -> Platform.exit());
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("\\pictures\\app_icon.png"));
			primaryStage.show();
			
			mainViewController = loader.getController();
		
		} catch(Exception e) {
				e.printStackTrace();
		}
	}
}
