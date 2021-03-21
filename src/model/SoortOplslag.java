package model;

/**
 * Deze klasse slaat alle soorten opslag op, alsook hun beschrijving en klasseNaam.

 * @version 1.0
 */
public enum SoortOplslag {
    xls ("Excel", "database.ExcelAdapter"),
    txt ("Text", "database.ArtikelTekstLoadSave");

    private final String omschrijving;
    private final String klasseNaam;

    /**
     * Deze methode stelt de omschrijving en klassenaam in.
     * @param omschrijving omschrijving.
     * @param klasseNaam klasse naam.

     */
    SoortOplslag(String omschrijving, String klasseNaam) {
        this.omschrijving = omschrijving;
        this.klasseNaam = klasseNaam;
    }

    /**
     * Deze methode returnt de beschrijving van de opslag.
     * @return de beschrijving.

     */
    public String getOmschrijving() { return omschrijving; }

    /**
     * Deze methode returnt de klasse naam van de opslag.
     * @return de klasse naam.

     */
    public String getKlasseNaam() { return klasseNaam; }
}
