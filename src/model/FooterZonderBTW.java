package model;

import controller.KassaController;

/**
 * Deze klasse wordt gebruikt om een footer (BTW) aan de rekening toe te voegen.

 * @version 1.0
 */
public class FooterZonderBTW extends RekeningDecorator {

    private RekeningAbstract rekening;
    private double totaal;

    /**
     * Deze methode maakt een instantie van de klasse aan.
     * @param rekening de originele rekening.
     * @param totaal het totale bedrag van de rekening.

     */
    public FooterZonderBTW(RekeningAbstract rekening, Double totaal) {
        this.rekening = rekening;
        this.totaal = totaal;
    }

    /**
     * Deze methode haaalt de beschrijving op en voegt de footer toe.
     * @return de beschrijving.

     */
    @Override
    public String getDescription() {
        return rekening.getDescription() + String.format("\nExclusief BTW: € %.2f", totaal*0.94) + String.format("\nBTW: € %.2f", totaal*0.06);
    }
}
