package model;

import controller.KassaController;

import java.util.ArrayList;
/**
 * Deze klasse implementeert de 'Korting' interface. Specifieker zorgt deze klasse voor de duurstekorting.

 * @version 1.0
 */
public class Duurstekorting implements Korting {
    private double percent;
    private Artikel additional;

    /**
     * Deze methode returnt de totale korting.
     * @param artikels de controller waar de cart te vinden is.
     * @return de totale korting.

     */
    @Override
    public double berekenKorting(ArrayList<Artikel> artikels) {
        Artikel duurste = new Artikel("XXX","dummy","grdf",0.01,1);
        for (Artikel artikel : artikels) {
            if (duurste.getVerkoopprijs() < artikel.getVerkoopprijs()) {
                duurste = artikel;
            }
        }
        return duurste.getVerkoopprijs()*(percent/100);
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
        this.additional = (Artikel)additional;
    }
}