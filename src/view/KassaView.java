package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Winkel;

public class KassaView {
	private Stage stage = new Stage();		
		
	public KassaView(Winkel winkel){
		stage.setTitle("KASSA VIEW");
		stage.setResizable(false);		
		stage.setX(20);
		stage.setY(20);
		Group root = new Group();
		Scene scene = new Scene(root, 750, 500);
		scene.getStylesheets().add("application/instellingen.css");
		BorderPane borderPane = new KassaMainPane(winkel);
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(borderPane);

		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}
}
