package application;

import database.ExcelLoadSaveStrategy;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Artikel;
import model.Winkel;
import view.KassaView;
import view.KlantView;

import java.util.ArrayList;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Winkel winkel = new Winkel();
		KassaView kassaView = new KassaView(winkel);
		KlantView klantView = new KlantView(winkel);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
