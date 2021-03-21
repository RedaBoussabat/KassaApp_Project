package view;


import controller.InstellingenController;
import controller.KassaController;
import controller.LogController;
import controller.StockController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.Winkel;
import view.panels.InstellingenPane;
import view.panels.KassaPane;
import view.panels.LogPane;
import view.panels.ProductOverviewPane;

public class KassaMainPane extends BorderPane {
	public KassaMainPane(Winkel w2){

	    Winkel winkel = w2;

	    KassaController controller = new KassaController(winkel);

        KassaPane kassaPane = new KassaPane(controller);
	    TabPane tabPane = new TabPane();
        Tab kassaTab = new Tab("Kassa",kassaPane);

        StockController stockController = new StockController(winkel);
        ProductOverviewPane productOverviewPane = new ProductOverviewPane(stockController);

        InstellingenController instellingenController = new InstellingenController(winkel);
        InstellingenPane instellingenPane = new InstellingenPane(instellingenController);
        instellingenController.initKassaBonSettings();

        LogController logController = new LogController(winkel);
        LogPane logPane = new LogPane(logController);

        Tab artikelTab = new Tab("Artikelen",productOverviewPane);
        Tab instellingTab = new Tab("Instellingen",instellingenPane);
        Tab logTab = new Tab("Log",logPane);
        tabPane.getTabs().add(kassaTab);
        tabPane.getTabs().add(artikelTab);
        tabPane.getTabs().add(instellingTab);
        tabPane.getTabs().add(logTab);
	    this.setCenter(tabPane);
	}
}
