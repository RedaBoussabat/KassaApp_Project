package controller;

import javafx.util.Pair;
import model.Artikel;
import model.Korting;
import model.Observer;

import model.Winkel;
import view.KlantView;

import java.util.ArrayList;

import static model.SoortObserver.*;

/**
 * Deze klasse is de controller tussen winkel en klantview
 */
public class KlantController extends Observer {

    private Winkel winkel;
    private KlantView view;
    private double totaal = 0;

    /**
     * Deze methode maakt een instantie aan van een klantController
     *
     * @param winkel de winkel die gebruikt wordt
     */
    public KlantController(Winkel winkel) {
        super(winkel);
        setWinkel(winkel);
        winkel.registerObserver(this, ARTIKELINSCANNEN);
        winkel.registerObserver(this, DELETEARTIKEL);
        winkel.registerObserver(this, HOLD);
        winkel.registerObserver(this, RESUME);
        winkel.registerObserver(this, ANNULEER);
    }

    public void setWinkel(Winkel winkel) {
        this.winkel = winkel;
    }

    /**
     * Deze methode stelt de view in.
     *
     * @param view de view
     */
    public void setView(KlantView view) {
        this.view = view;
    }

    /**
     * Deze methode stelt het totale bedrag in.
     *
     * @param total het totale bedrag
     */
    public void setTotaal(double total) {
        this.totaal = total;
    }

    /**
     * Deze methode haalt het totale bedrag op.
     *
     * @return het totale bedrag.
     */
    public double getTotaal() {
        return totaal;
    }

    /**
     * Deze methode updatet de view van kassa.
     *
     * @param artikels de artikels die doorgegeven worden.

     */
    @Override
    public void update(ArrayList<Artikel> artikels, Enum soort) {
        double totaalS = 0;
        double tebetalen = 0;
        double totaleKorting = 0;
        ArrayList<Artikel> beepboop = null;
        if (soort.equals(ARTIKELINSCANNEN)) {
            try {
                if (artikels == null) {
                    throw new NullPointerException("Dit artikel bestaat niet.");
                }
                totaalS = this.berekenTotaal(artikels);
                this.setTotaal(totaalS);
                addArtikel(artikels.get(artikels.size() - 1));
                beepboop = artikels;
                if (this.getKorting() != null) {
                    totaleKorting = winkel.getKorting().berekenKorting(beepboop);
                    tebetalen = totaalS-totaleKorting;
                }
                else{
                    tebetalen = totaalS;
                }

                totaalS = (double) Math.round(totaalS * 100.0) / 100.0;
                tebetalen = (double) Math.round(tebetalen * 100.0) / 100.0;
                totaleKorting = (double) Math.round(totaleKorting * 100.0) / 100.0;
                this.view.setTotaal(totaalS);
                this.view.setTeBetalenBedrag(tebetalen);
                this.view.setTotaleKorting(totaleKorting);
            } catch (NullPointerException ignored) {

            }
        } else if (soort.equals(DELETEARTIKEL)) {
            ArrayList<Pair<Artikel, Integer>> artikelsKlantPairs = view.getArtikelsKlant();
            ArrayList<Artikel> artikelsKlant = toArrayList(artikelsKlantPairs);
            Artikel a = artikels.get(0);
            deleteArtikel(a);
            artikelsKlant.remove(a);
            totaalS = this.berekenTotaal(artikelsKlant);
            this.setTotaal(totaalS);
            beepboop = artikelsKlant;
            if (this.getKorting() != null) {
                totaleKorting = winkel.getKorting().berekenKorting(beepboop);
                tebetalen = totaalS-totaleKorting;
            }
            else{
                tebetalen = totaalS;
            }

            totaalS = (double) Math.round(totaalS * 100.0) / 100.0;
            tebetalen = (double) Math.round(tebetalen * 100.0) / 100.0;
            totaleKorting = (double) Math.round(totaleKorting * 100.0) / 100.0;
            this.view.setTotaal(totaalS);

            this.view.setTeBetalenBedrag(tebetalen);
            this.view.setTotaleKorting(totaleKorting);
        }
        else if (soort.equals(HOLD)) {
            view.reset();
            this.view.setTotaal(0);
            this.view.setTeBetalenBedrag(0);
            this.view.setTotaleKorting(0);
        } else if (soort.equals(RESUME)) {
            ArrayList<Pair<Artikel, Integer>> pairs = artikelsToPair(artikels);
            totaalS = this.berekenTotaal(artikels);
            this.setTotaal(totaalS);
            beepboop = artikels;
            if (this.getKorting() != null) {
                totaleKorting = winkel.getKorting().berekenKorting(beepboop);
                tebetalen = totaalS-totaleKorting;
            }
            else{
                tebetalen = totaalS;
            }

            totaalS = (double) Math.round(totaalS * 100.0) / 100.0;
            tebetalen = (double) Math.round(tebetalen * 100.0) / 100.0;
            totaleKorting = (double) Math.round(totaleKorting * 100.0) / 100.0;
            this.view.setTotaal(totaalS);
            this.view.setTeBetalenBedrag(tebetalen);
            this.view.setTotaleKorting(totaleKorting);

            view.resume(pairs);
        } else if (soort.equals(ANNULEER)) {
            view.reset();
            totaalS = 0;
            tebetalen = 0;
            totaleKorting = 0;
            this.view.setTotaal(totaalS);
            this.view.setTeBetalenBedrag(tebetalen);
            this.view.setTotaleKorting(totaleKorting);
        }
    }

    /**
     * Deze methode zet een lijst van artikels om naar een lijst van paren.
     *
     * @param artikels een lijst van artikels.
     * @return een lijst van paren.

     */
    public static ArrayList<Pair<Artikel, Integer>> artikelsToPair(ArrayList<Artikel> artikels) {
        ArrayList<Pair<Artikel, Integer>> res = new ArrayList<>();
        for (Artikel a : artikels) {
            Pair<Artikel, Integer> oldPair = getPairWithList(a, res);
            if (oldPair == null) {
                res.add(new Pair<>(a,1));
            }  else {
                int i = res.indexOf(oldPair);
                res.remove(oldPair);
                res.add(i,new Pair<>(a,oldPair.getValue()+1));
            }
        }
        return res;
    }

    /**
     * Deze methode haalt een pair op van een lijst van paren uit een gegeven list ahv een artikel.
     *
     * @param a   een artikel.
     * @param res een lijst
     * @return een paar.

     */
    public static Pair<Artikel, Integer> getPairWithList(Artikel a, ArrayList<Pair<Artikel, Integer>> res) {
        for (Pair pair : res) {
            if (pair.getKey().equals(a)) {
                return pair;
            }
        }
        return null;
    }

    /**
     * Deze methode zet een lijst van paren om naar een lijst van artikels.
     *
     * @param artikelsKlantPairs een lijst van paren.
     * @return een lijst van artikels.

     */
    private ArrayList<Artikel> toArrayList(ArrayList<Pair<Artikel, Integer>> artikelsKlantPairs) {
        ArrayList<Artikel> artikels = new ArrayList<>();
        for (Pair<Artikel, Integer> pair : artikelsKlantPairs) {
            for (int i = 0; i < pair.getValue(); i++) {
                artikels.add(pair.getKey());
            }
        }
        return artikels;
    }

    /**
     * Deze methode berekent het totaal ahv de lijst van artikels.
     *
     * @param artikels de lijst van artikels.
     * @return de totale prijs
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
     * Deze methode haalt het artikel op als het in de klant zijn winkelwagen staat.
     *
     * @param artikel het artikel.
     * @return het artikel.

     */
    public Artikel getArtikel(Artikel artikel) {
        Artikel res = null;
        ArrayList<Pair<Artikel, Integer>> artikelsKlant = view.getArtikelsKlant();
        for (Pair pair : artikelsKlant) {
            Artikel a = (Artikel) pair.getKey();
            if (a == artikel) res = a;
        }
        return res;
    }

    /**
     * @param artikel
     */
    public void pasAantalAan(Artikel artikel) {
        Pair oldPair = getPair(artikel);
        int current = (int) oldPair.getValue();
        Pair<Artikel, Integer> newPair = new Pair<>(artikel, current + 1);
        ArrayList<Pair<Artikel, Integer>> artikelsKlant = view.getArtikelsKlant();
        int i = artikelsKlant.indexOf(oldPair);
        artikelsKlant.remove(oldPair);
        artikelsKlant.add(i, newPair);
        view.setArtikelsKlant(artikelsKlant);
    }

    /**
     * Deze methode haalt een pair op ahv een artikel.
     *
     * @param artikel het artikel van het pair.
     * @return het pair.

     */
    private Pair<Artikel, Integer> getPair(Artikel artikel) {
        ArrayList<Pair<Artikel, Integer>> artikelsKlant = view.getArtikelsKlant();
        for (Pair pair : artikelsKlant) {
            if (pair.getKey().equals(artikel)) {
                return pair;
            }
        }
        return null;
    }

    /**
     * Deze methode haalt het aantal artikels x van de huidige klant op.
     *
     * @param artikel het artikel x
     * @return het aantal artikels x.

     */
    private int getAantal(Artikel artikel) {
        ArrayList<Pair<Artikel, Integer>> artikelsKlant = view.getArtikelsKlant();
        int res = 0;
        for (Pair pair : artikelsKlant) {
            Artikel a = (Artikel) pair.getValue();
            if (a == artikel) res = (int) pair.getValue();
        }
        return res;
    }

    /**
     * Deze methode voegt een artikel toe aan de artikellijst van de klant.
     *
     * @param artikel het artikel toe te voegen aan de lijst van de klant.

     */
    public void addArtikel(Artikel artikel) {
        ArrayList<Pair<Artikel, Integer>> artikelsKlant = view.getArtikelsKlant();
        if (getArtikel(artikel) != null) {
            pasAantalAan(artikel);
        } else {
            Pair<Artikel, Integer> newPair = new Pair<>(artikel, 1);
            artikelsKlant.add(newPair);
            view.setArtikelsKlant(artikelsKlant);
        }
        view.refresh();
    }

    /**
     * Deze methode verwijdert een artikel uit de view.
     *
     * @param artikel het artikel dat verwijdert dient te worden.

     */
    public void deleteArtikel(Artikel artikel) {
        ArrayList<Pair<Artikel, Integer>> artikelsKlant = view.getArtikelsKlant();
        if (getPair(artikel).getValue() == 1) {
            removePair(artikel, artikelsKlant);
        } else {
            Pair<Artikel, Integer> newPair = new Pair<>(artikel, getPair(artikel).getValue() - 1);
            int i = artikelsKlant.indexOf(getPair(artikel));
            artikelsKlant.remove(getPair(artikel));
            artikelsKlant.add(i,newPair);
            view.setArtikelsKlant(artikelsKlant);
        }
        view.refresh();
    }

    /**
     * Deze methode haalt een pair uit de view ahv het artikel.
     *
     * @param artikel       het artikel
     * @param artikelsKlant de artikels uit de view.

     */
    private void removePair(Artikel artikel, ArrayList<Pair<Artikel, Integer>> artikelsKlant) {
        Pair<Artikel, Integer> oldPair = getPair(artikel);
        int i = artikelsKlant.indexOf(oldPair);
        artikelsKlant.remove(oldPair);
        view.setArtikelsKlant(artikelsKlant);
    }
}
