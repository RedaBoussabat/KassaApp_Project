package model;

import controller.KassaController;

/**
 * Deze klasse wordt gebruikt om een algemene dankboodschap aan het kassa ticket toe te voegen.

 * @version 1.0
 */
public class FooterBericht extends RekeningDecorator {

    private RekeningAbstract rekening;

    /**
     * Deze methode maakt een instance van de klasse aan ahv de originele rekening.
     * @param rekening de originele rekening

     */
    public FooterBericht(RekeningAbstract rekening) {
        this.rekening = rekening;
    }

    /**
     * Deze methode haalt de beschrijving op en voegt de footer toe.
     * @return de beschrijving.
     */
    @Override
    public String getDescription() {
        return rekening.getDescription() + "\nDank u voor uw aankoop bij ons.";
    }
}
