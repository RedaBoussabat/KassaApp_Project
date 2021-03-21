package model;

import controller.KassaController;

import java.util.ArrayList;

/**
 * Deze interface dient als template voor alle kortingen.

 * @version 1.0
 */
public interface Korting {

    /**
     * Deze methode returnt de totale korting.
     * @param artikels de controller waar de cart te vinden is.
     * @return de totale korting.

     */
    double berekenKorting(ArrayList<Artikel> artikels);

    /**
     * Deze methode stelt de percentage korting in.
     * @param percent het percentage in te stellen.

     */
    void setPercent(double percent);

    /**
     * Deze methode stelt de additional in.
     * @param additional de extra info.

     */
    void setAdditional(Object additional);

    default double berekenTotaal(ArrayList<Artikel> artikels) {
        double res = 0;
        for (Artikel a : artikels) {
            res += a.getVerkoopprijs();
        }
        return res;
    }
}