

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UpdatedGUI extends Application {

	private Stage pStage;
	private BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.pStage = primaryStage;
		this.pStage.setTitle("Craigslist WebScraper");
		try {
			loadMainLayout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private void loadMainLayout() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(UpdatedGUI.class.getResource("view\\MainView.fxml"));
		root = loader.load();
		Scene scene = new Scene(root);
		pStage.setScene(scene);
		pStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
