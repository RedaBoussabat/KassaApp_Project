package model;

/**
 * Deze enum beschrijft de soorten kortingen.

 * @version 1.0
 */
public enum SoortKorting {
    Groepkorting("Groepkorting", "model.Groepkorting"),
    Drempelkorting("Drempelkorting", "model.Drempelkorting"),
    Duurstekorting("Duurstekorting", "model.Duurstekorting");

    private final String omschrijving;
    private final String klasseNaam;

    /**
     * Deze methode maakt een entry in de enum.
     * @param omschrijving de beschrijving van de soort korting.
     * @param klasseNaam de klassenaam van de soort korting.

     */
    private SoortKorting(String omschrijving, String klasseNaam) {
        this.omschrijving = omschrijving;
        this.klasseNaam = klasseNaam;
    }

    /**
     * Deze methode haalt de omschrijving van de soort korting op.
     * @return de beschrijving van de korting.

     */
    public String getOmschrijving() {
        return this.omschrijving;
    }

    /**
     * Deze methode haalt de klassenaam op.
     * @return de klassenaam op van de soort korting.

     */
    public String getKlasseNaam() {
        return this.klasseNaam;
    }
}