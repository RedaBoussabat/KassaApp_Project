package model;

import java.util.ArrayList;

/**
 * Deze klasse is de abstracte klasse van iedere Observer binnen dit project.

 * @version 1.0
 */
public abstract class Observer {

    private Winkel subject;

    /**
     * Contructor van de klasse.
     * @param subject de winkel.

     */
    public Observer(Winkel subject) {
        this.subject = subject;
    }

    /**
     * Deze methode updatet de stock, de winkelwagen van de klant en de kassa.

     */
    public abstract void update(ArrayList<Artikel> artikels, Enum soort);

}