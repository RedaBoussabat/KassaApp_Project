package model;

import controller.KassaController;

import java.util.ArrayList;

/**
 * Deze klasse implementeert de 'Korting' interface. Specifieker zorgt deze klasse voor de drempelkorting.

 * @version 1.0
 */
public class Drempelkorting implements Korting {
    private double percent;
    private double additional;

    /**
     * Deze methode returnt de totale korting.
     * @param artikels de controller waar de cart te vinden is.
     * @return de totale korting.

     */
    @Override
    public double berekenKorting(ArrayList<Artikel> artikels) {
        if (berekenTotaal(artikels) > additional) return berekenTotaal(artikels)*(percent/100);
        else return 0;
    }

    /**
     * Deze methode stelt de percentage korting in.
     * @param percent het percentage in te stellen.

     */
    @Override
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * Deze methode stelt de additional in.
     * @param additional de extra info.

     */
    @Override
    public void setAdditional(Object additional) {
        this.additional = (Double)additional;
    }
}