package view.panels;

import controller.StockController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Artikel;

import java.util.ArrayList;

/**
 * Deze pane is gebruikt om artikels te tonen van de stock.

 * @version 1.0
 */
public class ProductOverviewPane extends GridPane {
	private TableView<Artikel> table = new TableView<>();
	private ObservableList<Artikel> artikels = FXCollections.observableArrayList();

	/**
	 * Deze methode maakt een overview pane aan met een gekoppelde controller.
	 * @param controller dde controller.

	 */
	public ProductOverviewPane(StockController controller) {
		this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

		TableColumn code = new TableColumn("Artikel code");
		TableColumn beschrijving = new TableColumn("Artikel beschrijving");
		TableColumn groep = new TableColumn("Artikel groep");
		TableColumn prijs = new TableColumn("Eenheidsprijs");
		TableColumn voorraad = new TableColumn("Actuele voorraad");

		code.setMinWidth(150);
		beschrijving.setMinWidth(150);
		groep.setMinWidth(125);
		prijs.setMinWidth(125);
		voorraad.setMinWidth(125);

		table.setId("my-table");

		code.setCellValueFactory(new PropertyValueFactory<>("code"));
		beschrijving.setCellValueFactory(new PropertyValueFactory<>("omschrijving"));
		groep.setCellValueFactory(new PropertyValueFactory<>("artikelGroep"));
		prijs.setCellValueFactory(new PropertyValueFactory<>("verkoopprijs"));
		voorraad.setCellValueFactory(new PropertyValueFactory<>("actueleVoorraad"));

        controller.setStock(this);
		this.add(new Label("Products:"), 0, 0, 1, 1);
		controller.toonArtikelen();

		table.setItems(artikels);
		table.getColumns().addAll(code,beschrijving, groep, prijs, voorraad);
		this.add(table,0,1);
	}

	/**
	 * Deze methode updatet de stock.
	 * @param artikels de artikels die doorgegeven worden als stock.

	 */
	public void updateStockView(ArrayList<Artikel> artikels) {
	    this.artikels.clear();
        this.artikels.addAll(artikels);
    }

    public void sell(ArrayList artikelArrayList){
		artikels = FXCollections.observableArrayList(artikelArrayList);
		this.table.setItems(artikels);
	}

}
