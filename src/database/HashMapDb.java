package database;

import model.Artikel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Deze klasse is een in memory databank in de vorm van een HashMap.

 * @version 1.0
 */
public class HashMapDb implements DbBehaviour {

    private HashMap<String, Artikel> db;

    /**
     * Deze methode maakt een instantie van deze klasse aan.

     */
    public HashMapDb() {
        this.db = new HashMap<>();
    }

    /**
     * Deze methode zoekt een artikel op uit de db.
     * @param code de code van het artikel.
     * @return het artikel.

     */
    @Override
    public Artikel search(String code) {
        return this.db.get(code);
    }

    /**
     * Deze methode laadt alle artikels van het locale geheugen (HashMap) in naar een ArrayList van Artikels.
     * @return de ArrayList van artikels.

     */
    @Override
    public ArrayList<Artikel> load() {
        ArrayList<Artikel> res = new ArrayList<>();
        res.addAll(db.values());
        return res;
    }

    /**
     * Deze methode schrijft artikels weg naar het locale geheugen (de HashMap).
     * @param artikels de ArrayList van artikels om op te slaan.

     */
    @Override
    public void save(ArrayList<Artikel> artikels) {
        for (Artikel artikel : artikels) {
            db.put(artikel.getCode(), artikel);
        }
    }
}