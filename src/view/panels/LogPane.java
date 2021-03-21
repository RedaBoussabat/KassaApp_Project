package view.panels;

import controller.LogController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Artikel;
import javafx.scene.layout.GridPane;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Deze pane zal de in de log tab aantonen wat er verkocht is geweest
 *

 */

public class LogPane extends GridPane {

    private TableView<Object[]> tableView = new TableView<>();
    private ObservableList<Object[]> logs = FXCollections.observableArrayList();


    /**
     * Deze methode maakt een overview pane aan gekoppelde aan een controller met een gelijkaardige naam
     *
     * @param controller

     */
    public LogPane(LogController controller) {

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        TableColumn<Object[], String> datum = new TableColumn<>("Datum verkoop");
        TableColumn<Object[], String> tijdstip = new TableColumn<>("Tijdstip verkoop");
        TableColumn<Object[], String> totaalbedrag = new TableColumn<>("Totaal Bedrag");
        TableColumn<Object[], String> korting = new TableColumn<>("Korting");
        TableColumn<Object[], String> teBetalen = new TableColumn<>("Te betalen");

        datum.setMinWidth(125);
        tijdstip.setMinWidth(125);
        totaalbedrag.setMinWidth(125);
        korting.setMinWidth(125);
        teBetalen.setMinWidth(125);

        tableView.setId("my-table");

        datum.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[3]));
        tijdstip.setCellValueFactory(cell -> new SimpleStringProperty((String) cell.getValue()[4]));
        totaalbedrag.setCellValueFactory(cell -> new SimpleStringProperty(String.format("€ %.2f" ,(cell.getValue()[0]))));
        korting.setCellValueFactory(cell -> new SimpleStringProperty(String.format("€ %.2f" ,(cell.getValue()[1]))));
        teBetalen.setCellValueFactory(cell -> new SimpleStringProperty(String.format("€ %.2f" ,(cell.getValue()[2]))));

        controller.setView(this);
        this.add(new Label("Logs"),0,0,1,1);
        controller.toonLogs();

        tableView.setItems(logs);
        tableView.getColumns().addAll(datum, tijdstip, totaalbedrag, korting, teBetalen);
        this.add(tableView, 0,1);
    }

    /**
     * Deze methode updatet de logs
     * @param artikels zijn de informatie over voor de verkopen
     * @author Boussabat Reda
     */
    public void updateLog(Object[] artikels) {
        this.logs.addAll(artikels);
    }
}
