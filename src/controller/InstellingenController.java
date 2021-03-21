package controller;

import javafx.beans.property.Property;
import model.*;
import view.panels.InstellingenPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Deze klasse fungeert als controller tussen de instellingen tab en de winkel.
 *

 * @version 1.0
 */
public class InstellingenController {

    private Winkel winkel;
    private InstellingenPane view;

    /**
     * Deze methode maakt een instantie aan van een isntellingencontroller ahv de winkel.
     *
     * @param winkel de winkel.

     */
    public InstellingenController(Winkel winkel) {
        this.winkel = winkel;
    }

    /**
     * Deze methode stelt de input in als txt.
     *

     */
    public void setTxt() {
        Properties properties = winkel.getProperties();
        FileOutputStream os = null;
        try {
            File prop = new File("src/bestanden/instellingen.xml");
            os = new FileOutputStream(prop);
            properties.setProperty("input", "txt");
            properties.storeToXML(os, "");
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode stelt de input in als een excel file.
     *

     */
    public void setExcel() {
        Properties properties = winkel.getProperties();
        FileOutputStream os = null;
        try {
            File prop = new File("src/bestanden/instellingen.xml");
            os = new FileOutputStream(prop);
            properties.setProperty("input", "xls");
            properties.storeToXML(os, "");
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deze methode stelt de radiobutton in ahv de properties.
     *

     */
    public void setStandard() {
        Object inputObj = winkel.getProperties().get("input");
        String input = (String) inputObj;
        SoortOplslag soortOplslag = SoortOplslag.valueOf(input);
        String beschrijving = soortOplslag.getOmschrijving();
        if (beschrijving.equals("Excel")) {
            view.setExcelStandard();
        } else if (beschrijving.equals("Text")) {
            view.setTxtStandard();
        }
    }

    /**
     * Deze methode stelt de view in.
     *
     * @param instellingenPane de view.

     */
    public void setView(InstellingenPane instellingenPane) {
        this.view = instellingenPane;
    }

    /**
     * Deze methode maakt de korting aan.
     *
     * @param type       de locatie van de klasse
     * @param percent    het percentage van korting
     * @param additional extra info; groep of drempel

     */
    public void createKorting(String type, String percent, String additional) {
        this.winkel.setKorting(type);
        this.winkel.getKorting().setPercent(Double.parseDouble(percent));
        if (this.winkel.getKorting() instanceof Groepkorting) {
            this.winkel.getKorting().setAdditional(additional);
        } else if (this.winkel.getKorting() instanceof Drempelkorting) {
            this.winkel.getKorting().setAdditional(Double.parseDouble(additional));
        } else if (this.winkel.getKorting() instanceof Duurstekorting) {
            this.winkel.getKorting().setAdditional(new Artikel("X", "Dummy", "gr1", 0.01, 1));
        }
    }

    /**
     * Deze methode haalt de kassabon instellingen op.
     * @return een lijst van de properties.

     */
    public String[] getKassaBonSettings() {
        String[] res = new String[5];
        if (view.getInputKassaBon() != null) res[0] = view.getInputKassaBon();
        else res[0] = "";
        if (view.getHeaderTime() != null) res[1] = view.getHeaderTime();
        else res[1] = "";
        if (view.getFooterExclusiefBTW() != null) res[2] = view.getFooterExclusiefBTW();
        else res[2] = "";
        if (view.getFooterZonderKorting() != null) res[3] = view.getFooterZonderKorting();
        else res[3] = "";
        if (view.getFooterAfsluitLijn() != null) res[4] = view.getFooterAfsluitLijn();
        else res[4] = "";
        return res;
    }

    /**
     * Deze methode slaat de instellingen van de kassabon op.

     */
    public void saveKassaBonSettings() {
        String[] aangevinkt = getKassaBonSettings();
        Properties properties = winkel.getProperties();
        if (aangevinkt[0] != null) properties.setProperty("headerCustom",aangevinkt[0]);
        if (aangevinkt[1] != null) properties.setProperty("headerTijd", aangevinkt[1]);
        if (aangevinkt[2] != null) properties.setProperty("footerBTW", aangevinkt[2]);
        if (aangevinkt[3] != null) properties.setProperty("footerKorting", aangevinkt[3]);
        if (aangevinkt[4] != null) properties.setProperty("footerAfsluitlijn", aangevinkt[4]);
        FileOutputStream os = null;
        try {
            File prop = new File("src/bestanden/instellingen.xml");
            os = new FileOutputStream(prop);
            properties.storeToXML(os, "");
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        winkel.setProperties(properties);
    }

    /**
     * Deze methode initialiseert de kassabon instellingen bij het opstarten.

     */
    public void initKassaBonSettings() {
        Properties properties = winkel.getProperties();
        String customHeader = properties.getProperty("headerCustom");
        String headerTijd = properties.getProperty("headerTijd");
        String footerBTW = properties.getProperty("footerBTW");
        String footerKorting = properties.getProperty("footerKorting");
        String footerAfsluilijn = properties.getProperty("footerAfsluitlijn");
        if (!customHeader.equals("")) {
            view.setCustomHeader(true,customHeader);
        } if (!headerTijd.equals("")) {
            view.setHeaderTijd(true);
        } if (!footerBTW.equals("")) {
            view.setFooterBTW(true);
        } if (!footerKorting.equals("")) {
            view.setFooterKorting(true);
        } if (!footerAfsluilijn.equals("")) {
            view.setFooterAfsluitlijn(true);
        }
        FileOutputStream os = null;
        try {
            File prop = new File("src/bestanden/instellingen.xml");
            os = new FileOutputStream(prop);
            properties.storeToXML(os, "");
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        winkel.setProperties(properties);
    }
}
