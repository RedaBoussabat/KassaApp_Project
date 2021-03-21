package database;

import model.Artikel;
import java.util.ArrayList;

/**
 * Deze interface dient als strategy om eenvoudig een andere type database te kiezen.

 * @version 1.0
 */
//TODO: input @ runtime changen.
public interface DbBehaviour {
    /**
     * Deze methode leest artikels in van de database.
     * @return Arraylist van Artikels.

     */
    ArrayList<Artikel> load();
    /**
     * Deze methode slaat artikels op.
     * @param artikels de ArrayList van artikels om op te slaan.

     */
    void save(ArrayList<Artikel> artikels);

    /**
     * Deze methode zoekt een artikel uit de db.
     * @param code de code van het artikel.
     * @return het artikel.

     */
    Artikel search(String code);
}
