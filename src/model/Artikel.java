package model;

/**
 * Deze klasse beschrijft een artikel.

 * @version 1.1
 */
public class Artikel implements Comparable<Artikel>{

    private String code, omschrijving, artikelGroep;
    private double verkoopprijs;
    private int actueleVoorraad;

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Deze methode toont de code van een artikel.
     * @return de code van een artikel.

     */
    public String getCode() {
        return code;
    }

    /**
     * Deze methode zet de code van een artikel.
     * @param code de code van een artikel.

     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Deze methode toont de beschrijving van een artikel.
     * @return de beschrijving van een artikel.

     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Deze methode zet de beschrijving van een artikel.
     * @param omschrijving de beschrijving van een artikel.

     */
    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Deze methode toont de artikel groep van een artikel.
     * @return de artikel groep van een artikel.

     */
    public String getArtikelGroep() {
        return artikelGroep;
    }

    /**
     * Deze methode zet de artikel groep van een artikel.
     * @param artikelGroep de artikelgroep van een artikel.

     */
    public void setArtikelGroep(String artikelGroep) {
        this.artikelGroep = artikelGroep;
    }

    /**
     * Deze methode toont de verkoopprijs van een artikel.
     * @return de verkoopprijs van een artikel.

     */
    public double getVerkoopprijs() {
        return verkoopprijs;
    }

    /**
     * Deze methode zet de verkoopprijs van een artikel.
     * @param verkoopprijs de verkoopprijs van een artikel.

     */
    public void setVerkoopprijs(double verkoopprijs) {
        if (verkoopprijs < 0) throw new IllegalArgumentException("Prijs moet meer dan € 0.00");
        else this.verkoopprijs = verkoopprijs;
    }

    /**
     * Deze methode toont de actuele voorraad van een artikel.
     * @return de actuele voorraad van een artikel.

     */
    public int getActueleVoorraad() {
        return actueleVoorraad;
    }

    /**
     * Deze methode zet de actuele voorraad van een artikel.
     * @param actueleVoorraad de actuele voorraad van een artikel.

     */
    public void setActueleVoorraad(int actueleVoorraad) {
        if (actueleVoorraad <= 0) throw new IllegalArgumentException("Voorraad moet een positief geheel getal zijn.");
        else this.actueleVoorraad = actueleVoorraad;
    }

    /**
     * Deze methode maakt een artikel aan ahv volgende parameters.
     * @param code de code van een artikel.
     * @param omschrijving de beschrijving van een artikel.
     * @param artikelGroep de artikel groep van een artikel.
     * @param verkoopprijs de verkoopprijs van een artikel.
     * @param actueleVoorraad de actuele voorraad van een artikel.

     */
    public Artikel(String code, String omschrijving, String artikelGroep, double verkoopprijs, int actueleVoorraad) {
        setCode(code);
        setOmschrijving(omschrijving);
        setArtikelGroep(artikelGroep);
        setVerkoopprijs(verkoopprijs);
        setActueleVoorraad(actueleVoorraad);
    }

    /**
     * @param o een tweede artikel waarmee vergeleken wordt.

     */
    @Override
    public int compareTo(Artikel o) {
        return this.getOmschrijving().compareTo(o.getOmschrijving());
    }

    @Override
    public boolean equals(Object o){
        Artikel a = (Artikel) o;
        if (a.getOmschrijving().equals(this.getOmschrijving())) return true;
        return false;
    }
}
