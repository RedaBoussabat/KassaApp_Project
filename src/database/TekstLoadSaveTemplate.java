package database;

import model.Artikel;
import java.util.ArrayList;

/**
 * Deze klasse is een template klasse om artikels in te lezen uit een file en op te slaan.

 * @version 1.0
 */
public abstract class TekstLoadSaveTemplate implements LoadSave{
    /**
     * Deze methode leest artikels in via een input file.
     * @param filepath pad naar de input file.
     * @return Arraylist van Artikels.

     */
    public abstract ArrayList<Artikel> load(String filepath);

    /**
     * Deze methode slaat artikels op.
     * @param artikels de ArrayList van artikels om op te slaan.
     * @param filepath pad naar de output file.

     */
    public abstract void save(ArrayList<Artikel> artikels, String filepath);

    /**
     * Deze methode laadt artikels in via een tekst file en schrijft ze daarna weg.
     * @param filepath pad naar de in en output file.

     */
    public abstract void loadAndSave(String filepath);
}
