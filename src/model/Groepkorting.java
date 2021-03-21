package model;

import controller.KassaController;

import java.util.ArrayList;

/**
 * Deze klasse implementeert de 'Korting' interface. Specifieker zorgt deze klasse voor de groepkorting.

 * @version 1.0
 */
public class Groepkorting implements Korting {
    private Double percent;
    private String additional;

    /**
     * Deze methode returnt de totale korting.
     * @param artikels de controller waar de cart te vinden is.
     * @return de totale korting.

     */
    @Override
    public double berekenKorting(ArrayList<Artikel> artikels) {
        double res = 0;
        for (Artikel artikel : artikels) {
            if (artikel.getArtikelGroep().equals(additional)) {
                res += artikel.getVerkoopprijs()*(percent/100);
            }
        }
        return res;
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
        this.additional = (String)additional;
    }
}