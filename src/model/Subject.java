package model;

import java.util.ArrayList;

/**
 * Deze interface beschrijft de methodes om met Observers om te gaan (registreren, verwijderen en notifyen).

 * @version 1.0
 */
public interface Subject {
    /**
     * Deze methode registreert een observer ahv een type.
     * @param observer observer die geregistreerd wordt
     * @param type type observer

     */
    void registerObserver(Observer observer, SoortObserver type);

    /**
     * Deze methode verwijdert een observer zodat deze geen updates meer krijgt.
     * @param observer de observer die verwijderd wordt.

     */
    void removeObserver(Observer observer);

    /**
     * Deze methode notifieert de observers wanneer er veranderingen zijn.
     * @param type het type observer dat genotified wordt.

     */
    void notifyObservers(SoortObserver type, ArrayList<Artikel> artikels);
}
