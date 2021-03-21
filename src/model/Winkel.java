package model;

import controller.KassaController;
import database.*;

import javax.xml.stream.FactoryConfigurationError;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static model.SoortObserver.*;

/**
 * Deze klasse beschrijft een winkel met 1 kassa.

 * @version 1.0
 */
public class Winkel implements Subject {

    private DbBehaviour db;
    private Map<SoortObserver,List<Observer>> observers;
    private Properties properties;
    private LoadSave loadSave;
    private LoadSaveFactory loadSaveFactory;
    private Korting korting;
    private KortingFactory kortingFactory;
    private Rekening rekening;
    private RekeningFactory rekeningFactory;
    private Winkelwagen[] winkelwagens = new Winkelwagen[2];
    private Winkelwagen current;
    private Winkelwagen hold;

    /**
     * Deze methode is gebruikt om de databank te zetten.
     * @param db de databank

     */
    public void setDb(DbBehaviour db) {
        this.db = db;
    }

    /**
     * Deze methode stelt de observers in, gegeven een lijst van Observers.
     * @param observers de lijst van Observers

     */
    public void setObservers(Map<SoortObserver, List<Observer>> observers) {
        this.observers = observers;
    }

    /**
     * Deze methode geeft de databank terug.
     * @return de databank.

     */
    public DbBehaviour getDb() {
        return db;
    }

    /**
     *Deze methode geeft de Map van Observers en hun soort door.
     * @return de Map van SoortOberver, lijst van Observers

     */
    public Map<SoortObserver, List<Observer>> getObservers() {
        return observers;
    }

    /**
     * Deze methode maakt een isntantiatie van deze klasse aan.

     */
    public Winkel() {
        this.db = new HashMapDb();
        RekeningFactory rekeningFactory = RekeningFactory.getInstance();
        LoadSaveFactory factory = LoadSaveFactory.getInstance();
        KortingFactory kortingFactory = KortingFactory.getInstance();
        this.properties = new Properties();
        try {
            InputStream is = new FileInputStream("src/bestanden/instellingen.xml");
            properties.loadFromXML(is);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadSave = factory.createLoadSave(properties);
        this.observers = new HashMap<>();
        observers.put(STOCK,new ArrayList<>());
        observers.put(ARTIKELINSCANNEN,new ArrayList<>());
        observers.put(DELETEARTIKEL,new ArrayList<>());
        observers.put(RESUME,new ArrayList<>());
        observers.put(HOLD, new ArrayList<>());
        observers.put(ANNULEER, new ArrayList<>());
        observers.put(LOG, new ArrayList<>());
        rekeningFactory.setWinkel(this);
        current = new Winkelwagen(this);
        hold = new Winkelwagen(this);
        winkelwagens[0] = current;
        winkelwagens[1] = hold;
    }

    /**
     * Deze methode stelt de properties in.
     * @param properties de properties om in te stellen.

     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Deze methode haalt de properties op.
     * @return de properties.

     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Deze methode haalt de huidge stock op en notifieert de observers.

     */
    public void toonStock() {
        Object inputObj = properties.get("input");
        String input = (String) inputObj;
        ArrayList<Artikel> artikels = loadSave.load("src/bestanden/artikel."+input);
        db.save(artikels);
        notifyObservers(STOCK, artikels);
    }

    /**
     * Deze methode registreert de observers aan de hand van hun type.
     * @param observer observer die geregistreerd wordt
     * @param type type observer

     */
    @Override
    public void registerObserver(Observer observer, SoortObserver type) {
        observers.get(type).add(observer);
    }

    /**
     * Deze methode zorgt ervoor dat een observer niet meer de updates van de winkel ontvangt.
     * @param observer de observer die verwijderd wordt.

     */
    @Override
    public void removeObserver(Observer observer) {
        if (observers.get(STOCK).contains(observer)) observers.get(STOCK).remove(observer);
        if (observers.get(ARTIKELINSCANNEN).contains(observer)) observers.get(STOCK).remove(observer);
    }

    /**
     * Deze methode notifies de observers aan de hand van hun type.
     * @param type het type observer dat genotified wordt.

     */
    @Override
    public void notifyObservers(SoortObserver type, ArrayList<Artikel> artikels) {
        List<Observer> obs = observers.get(type);
        if (obs == null)  {
            throw new IllegalArgumentException("Ongeldig type observer");
        }
        for (Observer observer : obs) {
            observer.update(artikels, type);
        }
    }

    /**
     * Deze methode stelt de korting in.
     * @param k de input voor korting aan te maken.

     */
    public void setKorting(String k) {
        this.korting = KortingFactory.getInstance().create(k);
    }

    /**
     * Deze methode haalt de korting op.
     * @return de korting.

     */
    public Korting getKorting() {
        return this.korting;
    }

    /**
     * Deze methode print de rekening uit.

     */
    public void printRekening() {
        RekeningAbstract r = RekeningFactory.getInstance().create(properties);
        System.out.println("\n\n\n\n*************************************\n"+r.getDescription()+"\n*************************************");
    }

    /**
     * Deze methode zet een verkoop op hold.
     *

     */
    public void putOnHold() {
        Winkelwagen temp = this.current;
        this.current = this.hold;
        this.hold = temp;
    }

    /**
     * Deze methode de verkoop van hold terug op de voorgrond.
     *

     */
    public void resume() {
        this.current = hold;
        this.hold = new Winkelwagen(this);
    }

    /**
     * Deze methode regelt de verkoop in de winkel (winkelwagens).

     */
    public void sell(){
        if (getOpHold()) resume();
        else this.current = new Winkelwagen(this);
    }

    /**
     * Deze methode cancelt de verkoop.

     */
    public void cancel(){
        this.current = new Winkelwagen(this);
    }

    public Winkelwagen getCurrent() {
        return current;
    }

    public Winkelwagen getHold() {
        return hold;
    }

    public void setHold(Winkelwagen winkelwagen) {
        this.hold = winkelwagen;
    }

    /**
     * Deze methode past de stock aan.

     */
    public void pasStockAan(){
        ArrayList<Artikel> weg = new ArrayList<>(current.getAll());
        ArrayList<Artikel> current = this.getDb().load();
        for (Artikel a: weg) {
            downStock(a, current);
        }
        db.save(current);
        Object inputObj = properties.get("input");
        String input = (String) inputObj;
        loadSave.save(current,"src/bestanden/artikel."+input);
    }

    /**
     * Deze methode verlaagt de stock van het huidige artikel met 1.
     * @param a het huidige artikel.
     * @param current alle meegegeven artikels.
     * @throws IllegalArgumentException wanneer er geen artikel meer in stock is.

     */
    private void downStock(Artikel a, ArrayList<Artikel> current) throws IllegalArgumentException {
        if (current.contains(a)) {
            try {
                int i = findIndex(current,a);
                if (current.get(i).getActueleVoorraad() <= 0) throw new IllegalArgumentException("Artikel is niet meer in stock");
                current.get(i).setActueleVoorraad(current.get(i).getActueleVoorraad()-1);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Artikel is niet meer in stock");
            }
        } else throw new IllegalArgumentException("Artikel is niet meer in stock");
    }

    /**
     * Deze methode zoekt de index van een artikel. Rewrite omdat deze niet werkt ahv indexOf().
     * @param current alle meegegevens artikels.
     * @param a het gezochte artikel.
     * @return de index van het artikel.
     * @throws IllegalArgumentException wanneer het artikel niet gevonden is.
     */
    private int findIndex(ArrayList<Artikel> current, Artikel a) throws IllegalArgumentException {
        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).equals(a)) return i;
        } throw new IllegalArgumentException("Niet gevonden.");
    }

    public void toonLogs(){

    }

    /**
     * Deze methode geeft weer of er een verkoop op hold staat

     * @return al dan niet een verkoop op hold staat.
     */
    public boolean getOpHold() {
        return hold.getAll().size() == 0;
    }
}
