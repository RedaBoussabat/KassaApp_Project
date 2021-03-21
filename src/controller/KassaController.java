package controller;

import model.*;
import view.panels.KassaPane;
import view.panels.LogPane;
import view.panels.ProductOverviewPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import static model.SoortObserver.*;

/**
 * Deze klasse is de controller tussen winkel en view.
 */
public class KassaController extends Observer {

    private Verkoop verkoop;
    private Winkel winkel;
    private KassaPane view;
    private ProductOverviewPane productOverviewPane;
    private LogPane logPaneview;

    /**
     * Deze methode maakt een isntantie aan van een kassaController.
     *
     * @param winkel de winkel die gebruikt wordt.

     */
    public KassaController(Winkel winkel) {
        super(winkel);
        setWinkel(winkel);
        winkel.registerObserver(this, SoortObserver.ARTIKELINSCANNEN);
        winkel.registerObserver(this, DELETEARTIKEL);
        winkel.registerObserver(this, STOCK);
        verkoop = new Verkoop(winkel);
    }

    /**
     * Deze methode stelt de winkel in.
     *
     * @param winkel de winkel.

     */
    public void setWinkel(Winkel winkel) {
        this.winkel = winkel;
    }

    /**
     * Deze methode stelt de view in.
     *
     * @param view de view.

     */
    public void setView(KassaPane view) {
        this.view = view;
    }

    /**
     * Deze methode stelt het totale bedrag in.
     *
     * @param totaal het totale bedrag

     */
    public void setTotaal(double totaal) {
        winkel.getCurrent().setTotaal(totaal);
    }

    /**
     * Deze methode haalt het totale bedrag op.
     *
     * @return het totale bedrag.

     */
    public double getTotaal() {
        return winkel.getCurrent().getTotaal();
    }

    /**
     * Deze methode scant een item in in de winkel.
     *
     * @param code de code van het artikel dat ingescand wordt.

     */
    public void scan(String code) {
        winkel.getCurrent().scan(code);
        view.getArtikelCodeField().clear();
    }


    /**
     * Deze methode updatet de view van kassa.
     *
     * @param artikels de artikels die doorgegeven worden.

     */
    @Override
    public void update(ArrayList<Artikel> artikels, Enum soort) {
        try {
            if (artikels == null) {
                throw new NullPointerException("Dit artikel bestaat niet.");
            }
            double totaalS = 0;
            double totaleKorting = 0;
            double tebetalen = 0;

            if (soort.equals(ARTIKELINSCANNEN)) {
                totaalS = this.berekenTotaal(artikels);
                this.view.setArtikels(artikels);

            }
            else if (soort.equals(DELETEARTIKEL)) {
                totaalS = this.berekenTotaal(getAll());
                this.view.setArtikels(getAll());

            }
            Properties properties = winkel.getProperties();
            this.setTotaal(totaalS);

            if (this.getKorting() != null) {
                totaleKorting = this.getKorting().berekenKorting(this.getAll());



                totaalS = totaalS;
            }
            tebetalen = totaalS-totaleKorting;
            totaalS = (double) Math.round(totaalS * 100.0) / 100.0;
            tebetalen = (double) Math.round(tebetalen * 100.0) / 100.0;
            totaleKorting = (double) Math.round(totaleKorting * 100.0) / 100.0;
            this.view.setTotaal(totaalS);
            this.view.setTeBetalenBedrag(tebetalen);
            this.view.setTotaleKorting(totaleKorting);
        } catch (NullPointerException e) {
            this.view.getError().setText(e.getMessage());
            this.view.getError().setVisible(true);
        }
    }

    /**
     * Deze methode berekent het totaal van de producten zonder korting in rekening te brengen van de huidige winklewagen.
     * @param artikels de artikels van de huidige winkelwagen.
     * @return de totale prijs van de items in de winkelwagen.

     */
    private double berekenTotaal(ArrayList<Artikel> artikels) {
        double res = 0;
        for (Artikel a : artikels) {
            res += a.getVerkoopprijs();
        }
        return res;
    }

    /**
     * Deze methode haalt de korting op.
     *
     * @return de korting

     */
    public Korting getKorting() {
        return this.winkel.getKorting();
    }

    /**
     * Deze methode haalt alle artikels uit de winkelmand op.
     *
     * @return een ArrayList van Artikels.

     */
    public ArrayList<Artikel> getAll() {
        return winkel.getCurrent().getAll();
    }

    /**
     * Deze methode haalt de view op.
     *
     * @return de view.

     */
    public KassaPane getView() {
        return view;
    }

    /**
     * Deze methode zet een verkoop op hold.
     *

     */
    public void putOnHold() {
        verkoop.setToOnHold();
        this.winkel.putOnHold();
        view.reset();
        view.setTotaal(winkel.getCurrent().getTotaal());
        view.setTotaleKorting(0);
        view.setTeBetalenBedrag(0);
        winkel.notifyObservers(HOLD,new ArrayList<>(0));
    }

    /**
     * Deze methode de verkoop van hold terug op de voorgrond.
     *

     */
    public void resume() {
        double totaleKorting = 0;
        double tebetalen = 0;
        this.winkel.resume();
        view.resume(winkel.getCurrent().getAll());
        double totaal = winkel.getCurrent().getTotaal();
        Properties properties = winkel.getProperties();
        if (this.getKorting() != null) {
            totaleKorting = this.getKorting().berekenKorting(this.getAll());
            tebetalen = totaal - totaleKorting;
            properties.setProperty("footerKorting",String.valueOf(totaleKorting));
        }
        totaal = (double) Math.round(totaal * 100.0) / 100.0;

        tebetalen = (double) Math.round(tebetalen * 100.0) / 100.0;
        totaleKorting = (double) Math.round(totaleKorting * 100.0) / 100.0;
        this.view.setTotaal(totaal);

        this.view.setTeBetalenBedrag(tebetalen);
        this.view.setTotaleKorting(totaleKorting);
        winkel.setHold(new Winkelwagen(winkel));
        winkel.notifyObservers(RESUME, winkel.getCurrent().getAll());
        properties.setProperty("totaal",String.valueOf(this.berekenTotaal(winkel.getCurrent().getAll())));
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
     * Deze functie annuleert de huidige verkoop en reset het beeld.

     */
    public void cancel(){
        winkel.cancel();
        winkel.notifyObservers(ANNULEER, new ArrayList<>(0));
        this.view.resetVerkoop();
    }

    /**
     * Deze functie moet de verkoop eindigen en de rekening uitprinten

     */
    public void sell(){
        ArrayList<Artikel> res = winkel.getCurrent().getAll();
        verkoop.sold();
        winkel.printRekening();
        winkel.pasStockAan();
        this.winkel.sell();
        view.resetVerkoop();
        winkel.notifyObservers(STOCK, winkel.getCurrent().getAll());
        winkel.notifyObservers(ANNULEER, new ArrayList<>(0));
        winkel.notifyObservers(LOG, res);
        this.setTotaal(0);
        this.view.setTotaal(0);
    }

    /**
     * Deze methode verwijderd het artikel ahv huidig artikel
     *
     * @param code het huidige artikel

     */
    public void verwijderArtikel(Artikel code) {
        winkel.getCurrent().deleteArtikel(code);
        view.getArtikelCodeField().clear();
    }

    /**
     * Deze methode verwijdert een artikel uit de winkelwagen ahv het invoerveld.
     *
     * @param artikelCode de code van het artikel dat verwijderd dient te worden.

     */
    public void verwijderArtikelByInput(String artikelCode) {
        try {
            Artikel a = winkel.getCurrent().get(artikelCode);
            verwijderArtikel(a);
        } catch (IllegalArgumentException e) {
            view.getError().setText(e.getMessage());
            view.getError().setVisible(true);
        }
    }

}
