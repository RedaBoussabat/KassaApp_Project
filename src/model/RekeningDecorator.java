package model;

import controller.KassaController;

/**
 * Deze klasse stelt de decorator voor van de rekening.

 * @version 1.0
 */
public abstract class RekeningDecorator extends RekeningAbstract {

    /**
     * Deze methode haalt de description op.
     * @return de description.

     */
    public abstract String getDescription();
}