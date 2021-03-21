package model;

/**
 * Deze klasse stelt een abstracte rekening voor.

 * @version 1.0
 */
public abstract class RekeningAbstract {
    String description = "ongekende productenreeks";

    /**
     * Deze methode haalt de beschrijving op.
     * @return
     */
    public String getDescription () {
        return description;
    }

}
