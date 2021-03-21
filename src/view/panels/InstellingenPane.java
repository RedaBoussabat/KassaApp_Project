package view.panels;

import controller.InstellingenController;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Deze pane wordt gebruikt voor de instellingen.

 * @version 1.0
 */
public class InstellingenPane extends GridPane {

    private RadioButton btnExcel, btnTxt;
    private ComboBox comboBox;
    private TextField comboBoxText;
    private TextField additional;
    private Button saveKorting;
    private Label input = new Label("Inlezen uit:");
    private Label korting = new Label("Korting:");
    private Label percent = new Label("%  ");
    private Label euro = new Label("€  ");
    private Label kassabon = new Label("Kassabon:");
    private CheckBox headerCustom, headerTime;
    private CheckBox footerZonderKorting,footerExclusiefBTW, footerAfsluitLijn;
    private TextField inputHeader = new TextField();


    /**
     * Deze methode maakt een pane aan en koppelt deze aan een controller.
     * @param instellingenController de controller.

     */
    public InstellingenPane(InstellingenController instellingenController) {
        percent.setVisible(false);
        euro.setVisible(false);
        this.comboBoxText = new TextField("Vul hier het gewenste percentage in.");
        this.additional = new TextField("drempel");
        this.saveKorting = new Button("Save");
        instellingenController.setView(this);
        btnExcel = new RadioButton("Excel");
        btnTxt = new RadioButton("Txt");
        ToggleGroup group = new ToggleGroup();
        btnTxt.setToggleGroup(group);
        btnExcel.setToggleGroup(group);
        ComboBox comboBox = new ComboBox();
        Label placeholder = new Label("kies een korting");
        comboBox.setPlaceholder(placeholder);
        comboBox.getItems().add("Groepkorting");
        comboBox.getItems().add("Drempelkorting");
        comboBox.getItems().add("Duurstekorting");
        comboBox.setOnAction(event -> {
            String newVal = (String) comboBox.getValue();
            this.setCombobox(newVal);
        });


        instellingenController.setStandard();

        btnTxt.setOnAction(event -> {
            instellingenController.setTxt();
        });

        btnExcel.setOnAction(event -> {
            instellingenController.setExcel();
        });
        this.add(input,0,0);
        this.add(korting, 1,0);
        this.add(comboBox, 1, 1);
        this.add(btnExcel, 0, 1);
        this.add(btnTxt, 0, 2);
        this.add(this.comboBoxText, 1, 2);
        this.add(percent,2,2);
        this.add(this.additional, 1, 3);
        this.comboBoxText.setVisible(false);
        this.additional.setVisible(false);
        this.add(euro,2,3);
        this.add(this.saveKorting, 1, 5);
        this.saveKorting.setVisible(false);
        this.saveKorting.setOnAction(event -> {
            instellingenController.createKorting((String) comboBox.getValue(), this.comboBoxText.getText(), this.additional.getText());
        });
        this.add(kassabon,3,0);
        Label header = new Label("\tHeaders:");
        this.add(header,3,1);
        headerCustom = new CheckBox("Schrijf een custom header");
        this.add(inputHeader,3,3);
        inputHeader.setVisible(false);
        headerCustom.setOnAction(event -> {
            if (headerCustom.isSelected()) {
                inputHeader.setText("");
                headerCustom.setText("Gebruik deze custom header");
                this.inputHeader.setVisible(true);
            } else {
                this.headerCustom.setText("Schrijf een custom header");
                this.inputHeader.setVisible(false);
            }
        });
        this.add(headerCustom,3,2);
        headerTime = new CheckBox("Toon de tijd van de aankoop");
        this.add(headerTime,3,4);
        this.add(new Label("\tFooter:"),3,5);
        footerAfsluitLijn = new CheckBox("Dank u voor uw aankopen bij ons.");
        footerExclusiefBTW = new CheckBox("Print de prijs zonder BTW af.");
        footerZonderKorting = new CheckBox("Print de prijs zonder korting af.");
        this.add(footerAfsluitLijn,3,8);
        this.add(footerExclusiefBTW,3,7);
        this.add(footerZonderKorting, 3, 6);
        Button saveKassaBon = new Button("Sla deze instellingen op.");
        saveKassaBon.setOnAction(event -> {
                instellingenController.saveKassaBonSettings();
        });
        this.add(saveKassaBon, 3,9);
    }

    /**
     * Deze methode stelt de combobox van de kortingen in.
     * @param in de input voor de korting.

     */
    private void setCombobox(String in) {
        percent.setVisible(true);
        euro.setVisible(false);
        switch (in) {
            case "Groepkorting":
                this.comboBoxText.setVisible(true);
                this.additional.setText("Groep");
                this.additional.setVisible(true);
                break;
            case "Drempelkorting":
                this.comboBoxText.setVisible(true);
                this.additional.setText("Drempel");
                this.additional.setVisible(true);
                this.euro.setVisible(true);
                break;
            case "Duurstekorting":
                this.comboBoxText.setVisible(true);
                this.additional.setVisible(false);
                break;
        }
        this.saveKorting.setVisible(true);
    }

    /**
     * Deze methode stelt de radiobutton in op excel bij inladen.

     */
    public void setExcelStandard() {
        btnTxt.setSelected(false);
        btnExcel.setSelected(true);
    }

    /**
     * Deze methode stelt de radiobutton in op txt bij inladen.

     */
    public void setTxtStandard() {
        btnExcel.setSelected(false);
        btnTxt.setSelected(true);
    }

    /**
     * Deze methode haalt de headerTime text op.
     * @return de headerTime text.

     */
    public String getHeaderTime() {
        if (headerTime.isSelected()) return headerTime.getText();
        else return null;
    }

    /**
     * Deze methode haalt de footerZonderKorting text op.
     * @return de footerZonderKorting text.

     */
    public String getFooterZonderKorting() {
        if (footerZonderKorting.isSelected()) return footerZonderKorting.getText();
        return null;
    }

    /**
     * Deze methode haalt de footerExclusiefBTW text op.
     * @return de footerExclusiefBTW text.

     */
    public String getFooterExclusiefBTW() {
        if (footerExclusiefBTW.isSelected()) return footerExclusiefBTW.getText();
        else return null;
    }

    /**
     * Deze methode haalt de footerAfsluitlijn text op.
     * @return de footerAfsluilijn text.

     */
    public String getFooterAfsluitLijn() {
        if (footerAfsluitLijn.isSelected()) return footerAfsluitLijn.getText();
        else return null;
    }

    /**
     * Deze methode haalt de input van de customHeader op.
     * @return de input van de customHeader.

     */
    public String getInputKassaBon() {
        if (headerCustom.isSelected()) return inputHeader.getText();
        else return null;
    }

    /*public void setCustomHeader(boolean b, String text) {
        headerCustom.setSelected(b);
        inputHeader.setVisible(true);
        inputHeader.setText(text);
    }*/

    /**
     * Deze methode stelt de header checkbox van tijd in.
     * @param b of de checkbox al dan niet aangevinkt is.

     */
    public void setHeaderTijd(boolean b) {
        headerTime.setSelected(b);
    }

    /**
     * Deze methode stelt de footer checkbox van de btw in.
     * @param b of de checkbox al dan niet aangevinkt is.

     */
    public void setFooterBTW(boolean b) {
        footerExclusiefBTW.setSelected(b);
    }

    /**
     * Deze methode stelt de checkbox van de footer ivm korting in.
     * @param b of de checkbox al dan niet aangevinkt is.

     */
    public void setFooterKorting(boolean b) {
        footerZonderKorting.setSelected(b);
    }

    /**
     * Deze methode stelt de checkbox van de footer van de afsluitlijn in.
     * @param b of de checkbox al dan niet aangevinkt is.

     */
    public void setFooterAfsluitlijn(boolean b) {
        footerAfsluitLijn.setSelected(b);
    }


    /**
     * Deze methode stelt de checkbox van de custom header in en roept de tekst op..
     * @param b of de checkbox al dan niet aangevinkt is.

     */
    public void setCustomHeader(boolean b, String customHeader) {
        this.headerCustom.setSelected(b);
        this.inputHeader.setText(customHeader);
        this.inputHeader.setVisible(b);
    }
}
