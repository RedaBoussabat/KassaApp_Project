package controller;


import model.Artikel;
import model.Observer;
import model.SoortObserver;
import model.Winkel;
import view.panels.LogPane;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import static model.SoortObserver.LOG;

/**
 * Deze klasse fungeert als controller tussen de Log tab en de winkel
 *

 */
public class LogController extends Observer {

    private Winkel winkel;
    private LogPane view;
    private ArrayList<Artikel> artikels;

    /**
     * Deze methode maakt een instantie aan van een Logcontroller adhv de winkel
     * @param winkel de winkel.

     */

    public LogController(Winkel winkel) {
        super(winkel);
        this.winkel = winkel;
        winkel.registerObserver(this, LOG);
    }


    /**
     * Deze methode update de log tab.
     * @param logs de lijst van artikels die verkocht werden.
     * @param soort het soort update.

     */
    @Override
    public void update(ArrayList<Artikel> logs, Enum soort) {
        Object[] res = new Object[5];
        res[0] = berekenTotaal(logs);
        try {
            res[1] = winkel.getKorting().berekenKorting(logs);
        } catch (NullPointerException e) {
            res[1] = 0.0;
        }
        res[2] = (Double) res[0] - (Double) res[1];
        res[3] = LocalDate.now().toString();
        res[4] = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        view.updateLog(res);
    }

    /**
     * Deze methode berekent het totaal van de meegegeven artikels.
     * @param logs de meegegeven artikels.
     * @return de totale prijs.

     */
    private Double berekenTotaal(ArrayList<Artikel> logs) {
        Double res = 0.0;
        for (Artikel a : logs) {
            res += a.getVerkoopprijs();
        }
        return res;
    }

    public void setView(LogPane logPane) {
        this.view = logPane;
    }

    public void toonLogs(){
        winkel.toonLogs();
    }

}
