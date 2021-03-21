package view.panels;

import controller.KassaController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import model.Artikel;
import view.KlantView;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.*;
import java.util.ArrayList;


public class KassaPane extends GridPane {
    private TableView table;
    private Button btnHold, btnResume, btnCancel, btnSell, btnafsluit;
    private TextField artikelCodeField;
    private Label error = new Label("Niet bestaande code"), totaal, totaleKorting, teBetalenBedrag;
    private KassaController controller;
    private KlantView klantView;
    private ObservableList<Artikel> artikels = FXCollections.observableArrayList();

    public KassaPane(KassaController controller) {
        this.controller = controller;
        controller.setView(this);
        setTable();
    }

    private void setTable() {
        this.setPrefHeight(150);
        this.setPrefWidth(300);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);
        this.add(new Label("Artikel code:"), 0, 1, 1, 1);
        artikelCodeField = new TextField();
        this.add(artikelCodeField, 1, 1, 1, 1);
        table = new TableView<>();
        table.setId("my-table");
        //table.setPrefWidth(REMAINING);
        this.add(error, 0, 0, 1, 1);
        error.setVisible(false);
        btnHold = new Button("Zet een verkoop op hold");
        btnResume = new Button("Resume hold");
        btnSell = new Button("BETAALD");
        btnSell.setDisable(true);
        btnCancel = new Button("Verkoop annuleren");
        btnResume.setDisable(true);
        btnafsluit = new Button("Sluit af!");

        totaleKorting = new Label("Totale korting");
        totaleKorting.setVisible(false);
        teBetalenBedrag = new Label("Te betalen bedrag");
        teBetalenBedrag.setVisible(false);


        artikelCodeField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                error.setVisible(false);
                controller.scan(artikelCodeField.getText());
            }
        });

        Button scanButton = new Button("Scan");
        scanButton.setOnAction(event -> {
            error.setVisible(false);
            controller.scan(artikelCodeField.getText());
        });
        this.add(scanButton, 2, 1, 1, 1);

        Button deleteButton = new Button("Verwijder");
        deleteButton.setOnAction(event -> {
            error.setVisible(false);
            if (table.getSelectionModel().getSelectedCells().isEmpty()) {
                controller.verwijderArtikelByInput(artikelCodeField.getText());
            }
            else {
                Artikel artikel = (Artikel) table.getSelectionModel().getSelectedItem();
                controller.verwijderArtikel(artikel);
            }
        });
        this.add(deleteButton, 3,1,1,1);


        TableColumn omschrijvingCol = new TableColumn<>("Beschrijving");
        omschrijvingCol.setCellValueFactory(new PropertyValueFactory("omschrijving"));
        table.getColumns().add(omschrijvingCol);
        omschrijvingCol.setPrefWidth(245);
        TableColumn<Artikel, String> prijsCol = new TableColumn<>("Prijs");
        prijsCol.setCellValueFactory(cell -> new SimpleStringProperty(String.format("€ %.2f",cell.getValue().getVerkoopprijs())));
        table.getColumns().add(prijsCol);
        prijsCol.setPrefWidth(245);
        TableColumn<Artikel, String> aantal = new TableColumn("Aantal");
        aantal.setCellValueFactory(cell -> new SimpleStringProperty("1"));
        table.getColumns().add(aantal);
        aantal.setPrefWidth(245);
        this.add(table, 0, 4, 5, 6);
        this.add(btnHold,1,11);
        this.add(btnResume,2,11);

        this.add(btnafsluit,1,12);

        btnafsluit.setOnAction(event ->{
            sluitAf();
            btnafsluit.setDisable(true);
            btnSell.setDisable(false);


        });
        this.add(btnSell,2,12);
        this.add(btnCancel,3,12);

        btnResume.setOnAction(event ->  {
            controller.resume();
        });

        btnHold.setOnAction(event -> {
            controller.putOnHold();
        });

        btnSell.setOnAction(event -> {
            controller.sell();
            sluitAf();
            btnafsluit.setDisable(false);
            btnSell.setDisable(true);
        });

        btnCancel.setOnAction(event -> {
            controller.cancel();
            btnafsluit.setDisable(false);
            btnSell.setDisable(true);
            teBetalenBedrag.setVisible(false);
            totaleKorting.setVisible(false);
        });

        table.setItems(artikels);
        totaal = new Label();
        totaal.setText("Totale bedrag: € 0,00");
        this.add(totaal, 0,11);
        this.add(totaleKorting,0,12);
        this.add(teBetalenBedrag,0,13);
        btnSell.setOnAction(event -> {
            controller.sell();
        });

    }

    /**
     * Deze methode stelt de label van het totale bedrag in.
     * @param totaalS het totale bedrag.

     */
    public void setTotaal(double totaalS) {
        totaal.setText(String.format("Totale bedrag: € %.2f",totaalS));
    }


    /**
     * Deze methode stelt de label van de totale korting in.
     * @param tk de totale korting.

     */
    public void setTotaleKorting(double tk){
    totaleKorting.setText(String.format("Totale korting: € %.2f", tk));
    }

    /**
     * Deze methode stelt de label van de totale korting in.
     * @param tbb de totale korting.

     */
    public void setTeBetalenBedrag(double tbb){
        teBetalenBedrag.setText(String.format("Te betalen bedrag: € %.2f", tbb));
    }

    /**
     * Deze methode maakt de labels tebetalenbedrag en totalekorting zichtbaar met de bijhorende bedragen.

     */

    public void sluitAf(){
        error.setVisible(false);
        if(teBetalenBedrag.isVisible()){
            teBetalenBedrag.setVisible(false);
            totaleKorting.setVisible(false);
        }
        else{
            teBetalenBedrag.setVisible(true);
            totaleKorting.setVisible(true);
        }

        //klantView.sluitAf();
    }

    /**
     * Deze methode updatet de view en zet een error indien nodig.
     * @param artikel het artikel dat ingescand is.

     */
    public void addArtikel(Artikel artikel) {
        error.setVisible(false);
        this.artikels.add(artikel);
    }

    /**
     * Deze methode haalt de label error op.
     * @return de error label.

     */
    public Label getError() {
        return error;
    }

    /**
     * Deze methode haalt de textfield waarmee we artikels inscannen op.
     * @return het scan veld.

     */
    public TextField getArtikelCodeField() {
        return artikelCodeField;
    }

    /**
     * Deze methode haalt alle artikels uit de winkelmand op.
     * @return de artikels van de winkelmand.

     */
    public ArrayList<Artikel> getAll() {
        return new ArrayList<>(artikels);
    }

    /**
     * Deze methode handelt het visuele van het op hold zetten van een aankoop.
     * Deze methode reset de view.

     */
    public void reset() {
        artikels = FXCollections.observableArrayList();
        btnHold.setDisable(true);
        btnResume.setDisable(false);
        this.table.setItems(artikels);
        this.setTotaal(0);
    }

    /**
     * Deze methode reset de view na betaald te zijn.

     */
    public void resetVerkoop() {
        artikels = FXCollections.observableArrayList();
        this.table.setItems(artikels);
        this.setTotaal(0);
    }

    /**
     * Deze methode handelt het visuele van herstellen van de on hold aankoop.
     * @param artikelArrayList de artikels uit de winkelwagen die op hold staat.

     */
    public void resume(ArrayList<Artikel> artikelArrayList) {
        artikels = FXCollections.observableArrayList(artikelArrayList);
        this.table.setItems(artikels);
        btnHold.setDisable(false);
        btnResume.setDisable(true);
    }

    /**
     * Deze methode stelt de artikels in.
     * @param artikels de artikels.

     */
    public void setArtikels(ArrayList<Artikel> artikels) {
        this.artikels = FXCollections.observableArrayList(artikels);
        table.setItems(this.artikels);
    }

}
