package model;

import controller.KassaController;

/**
 * Deze klasse wordt gebruikt om een footer toe te voegen aan de rekening.

 * @version 1.0
 */
public class FooterZonderKorting extends RekeningDecorator {

    private RekeningAbstract rekening;
    private double totaal, korting;

    /**
     * Deze methode maakt een instance aan van de klasse.
     * @param rekening de originele rekening.
     * @param korting de totale korting.
     * @param totaal het totale bedrag.

     */
    public FooterZonderKorting(RekeningAbstract rekening, double korting, double totaal) {
        this.rekening = rekening;
        this.totaal = totaal;
        this.korting = korting;
    }

    /**
     * Deze methode haalt de beschrijving op en voegt een footer toe.
     * @return de kassabon.

     */
    @Override
    public String getDescription() {
        return rekening.getDescription() + String.format("\nExclusief korting: € %.2f",totaal) + String.format("\nKorting: € %.2f",korting);
    }
}
